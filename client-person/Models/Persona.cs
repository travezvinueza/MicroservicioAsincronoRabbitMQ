using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using client_person.Enum;

namespace client_person.Models
{
    public class Persona
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public long id { get; set; }

        [Required]
        public string? fullName { get; set; }
        
        [Required]
        [EnumDataType(typeof(GenderPerson))]
        public GenderPerson genderPerson { get; set; }

        [Required]
        public int age { get; set; }

        [Required]
        public string? identification { get; set; }

        [Required]
        public string? address { get; set; }

        [Required]
        public string? phone { get; set; }

        public DateTime creationDate { get; set; }
    }
}