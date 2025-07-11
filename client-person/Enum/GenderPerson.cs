using System.Text.Json.Serialization;

namespace client_person.Enum
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum GenderPerson
    {
        MASCULINO,
        FEMENINO,
        OTRO
    }
}