namespace client_person.Config
{
    public class RabbitMQSettings
    {
        public string? Host { get; set; }
        public string? UserName { get; set; }
        public string? Password { get; set; }
        public string? RequestQueue { get; set; }
        public string? ResponseQueue { get; set; }
        public string? ResponseExchange { get; set; }
        public string? ResponseRoutingKey { get; set; }
        
    }
}