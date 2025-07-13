using client_person.Config;
using Microsoft.Extensions.Options;
using System.Text;
using RabbitMQ.Client;
using client_person.Repository;
using client_person.Models;
using client_person.Mapper;
using client_person.Dto;
using RabbitMQ.Client.Events;
using client_person.Service.Impl;

namespace client_person.Service
{
    public class ClienteRequestConsumer : BackgroundService
    {
        private readonly IServiceScopeFactory _scopeFactory;
        private readonly RabbitMQSettings _settings;
        private readonly ConnectionFactory _factory;

        public ClienteRequestConsumer(IServiceScopeFactory scopeFactory, IOptions<RabbitMQSettings> options)
        {
            _scopeFactory = scopeFactory;
            _settings = options.Value;

            _factory = new ConnectionFactory
            {
                HostName = _settings.Host!,
                UserName = _settings.UserName!,
                Password = _settings.Password!,
            };
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            
            var connection = await _factory.CreateConnectionAsync();
            var channel = await connection.CreateChannelAsync();

            await channel.QueueDeclareAsync(
                    queue: _settings.RequestQueue!,
                    durable: true,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null
                );

            var consumer = new AsyncEventingBasicConsumer(channel);
            bool EsIdentificacion(string input) => input.All(char.IsDigit);

            consumer.ReceivedAsync += async (model, ea) =>
            {
                var input = Encoding.UTF8.GetString(ea.Body.ToArray());

                try
                {
                    using var scope = _scopeFactory.CreateScope();

                    var repository = scope.ServiceProvider.GetRequiredService<IClienteRepository>();
                    var mapper = scope.ServiceProvider.GetRequiredService<ClientMapper>();
                    var responseProducer = scope.ServiceProvider.GetRequiredService<ClienteResponseProducer>();

                    Cliente? clienteDb;
                    if (EsIdentificacion(input.Trim('"')))
                    {
                        var identificacion = input.Trim('"');
                        clienteDb = await repository.GetByIdentificationAsync(identificacion);
                    }
                    else
                    {
                        var fullName = input.Trim('"');
                        clienteDb = await repository.GetByFullNameAsync(fullName);
                    }

                    ClienteDto dto = (clienteDb != null)
                       ? mapper.MapClientToDto(clienteDb)
                       : new ClienteDto();

                    await responseProducer.EnviarClienteAsync(dto, channel);
                    await channel.BasicAckAsync(ea.DeliveryTag, multiple: false);

                }
                catch (Exception ex)
                {
                    Console.WriteLine($"❌ Error al procesar mensaje: {ex.Message}");
                    await channel.BasicNackAsync(ea.DeliveryTag, multiple: false, requeue: false);
                }
            };

            await channel.BasicConsumeAsync(
                queue: _settings.RequestQueue!,
                autoAck: false,
                consumer: consumer
            );

            Console.WriteLine("🟢 Escuchando mensajes en RabbitMQ...");

            while (!stoppingToken.IsCancellationRequested)
            {
                await Task.Delay(1000, stoppingToken);
            }
        }
    }

}