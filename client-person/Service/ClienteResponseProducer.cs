using System.Text;
using System.Text.Json;
using client_person.Config;
using client_person.Dto;
using Microsoft.Extensions.Options;
using RabbitMQ.Client;

namespace client_person.Service.Impl
{
    public class ClienteResponseProducer
    {
        private readonly RabbitMQSettings _settings;

        public ClienteResponseProducer(IOptions<RabbitMQSettings> options)
        {
            _settings = options.Value;
        }

        public async Task EnviarClienteAsync(ClienteDto dto, IChannel channel)
        {
            try
            {
                await channel.ExchangeDeclareAsync(
                   exchange: _settings.ResponseExchange!,
                   type: ExchangeType.Direct,
                   durable: true
               );

                await channel.QueueDeclareAsync(
                    queue: _settings.ResponseQueue!,
                    durable: true,
                    exclusive: false,
                    autoDelete: false
                );

                await channel.QueueBindAsync(
                    queue: _settings.ResponseQueue!,
                    exchange: _settings.ResponseExchange!,
                    routingKey: _settings.ResponseRoutingKey!
                );

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
            }
            catch (Exception ex)
            {
                Console.WriteLine($"❌ Error al procesar mensaje: {ex.Message}");
            }

        }
    }

}