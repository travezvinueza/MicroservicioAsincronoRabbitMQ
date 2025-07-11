using System.Text.Json.Serialization;
using client_person.Enum;

namespace client_person.Dto
{
    [JsonUnmappedMemberHandling(JsonUnmappedMemberHandling.Disallow)]
    public class ClienteDto
    {
        public long id { get; set; }
        public DateTime creationDate { get; set; }
        public string? fullName { get; set; }
        public GenderPerson genderPerson { get; set; }
        public int age { get; set; }
        public string? identification { get; set; }
        public string? address { get; set; }
        public string? phone { get; set; }
        public string? password { get; set; }
        public bool state { get; set; }
        
    }
}