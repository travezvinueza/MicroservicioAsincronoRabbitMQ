using client_person.Config;
using Microsoft.Extensions.Options;
using System.Text;
using RabbitMQ.Client;
using client_person.Repository;
using client_person.Models;
using client_person.Mapper;
using client_person.Dto;
using System.Text.Json;
using RabbitMQ.Client.Events;

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

            await channel.ExchangeDeclareAsync(
                      exchange: _settings.ResponseExchange!,
                       type: ExchangeType.Direct,
                       durable: true
                );

            await channel.QueueDeclareAsync(
                    queue: _settings.RequestQueue!,
                    durable: true,
                    exclusive: false,
                    autoDelete: false,
                    arguments: null
                );

            await channel.QueueBindAsync(
                  queue: _settings.ResponseQueue!,
                  exchange: _settings.ResponseExchange!,
                  routingKey: _settings.ResponseRoutingKey!
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

                    var dto = clienteDb != null ? mapper.MapClientToDto(clienteDb) : new ClienteDto { identification = "NOT_FOUND", fullName = "NOT_FOUND" };

                    var json = JsonSerializer.Serialize(dto);
                    var body = Encoding.UTF8.GetBytes(json);
                    var props = new BasicProperties
                    {
                        Persistent = true,
                        ContentType = "application/json"
                    };

                    await channel.BasicPublishAsync(
                        exchange: _settings.ResponseExchange!,
                        routingKey: _settings.ResponseRoutingKey!,
                        mandatory: true,
                        basicProperties: props,
                        body: body
                    );

                    Console.WriteLine($"✅ Cliente enviado: {dto.identification} - {dto.fullName} : {json}");
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