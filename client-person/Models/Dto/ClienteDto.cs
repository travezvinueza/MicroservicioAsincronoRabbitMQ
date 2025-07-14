using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using client_person.Enum;

namespace client_person.Dto
{
    [JsonUnmappedMemberHandling(JsonUnmappedMemberHandling.Disallow)]
    public class ClienteDto
    {
        public long id { get; set; }
        public DateTime creationDate { get; set; }

        [Required(ErrorMessage = "El nombre es obligatorio.")]
        public string? fullName { get; set; }

        [Required(ErrorMessage = "El género es obligatorio.")]
        [EnumDataType(typeof(GenderPerson), ErrorMessage = "Género inválido.")]
        public GenderPerson genderPerson { get; set; }

        [Required(ErrorMessage = "La edad es obligatoria.")]
        [Range(0, 120, ErrorMessage = "La edad debe estar entre 0 y 120.")]
        public int age { get; set; }

        [Required(ErrorMessage = "La identificación es obligatoria.")]
        [RegularExpression(@"^\d{10}(\d{3})?$", ErrorMessage = "La identificación debe tener 10 o 13 dígitos numéricos.")]
        public string? identification { get; set; }
       
        [Required(ErrorMessage = "La dirección es obligatoria.")]
        public string? address { get; set; }
        
        [Required(ErrorMessage = "El teléfono es obligatorio.")]
        [Phone(ErrorMessage = "Número de teléfono inválido.")]
        public string? phone { get; set; }

        [Required(ErrorMessage = "La contraseña es obligatoria.")]
        [MinLength(6, ErrorMessage = "La contraseña debe tener al menos 6 caracteres.")]
        public string? password { get; set; }

        public bool state { get; set; }
        
    }
}