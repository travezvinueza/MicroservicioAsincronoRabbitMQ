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
        public string? fullName { get; set; }
        public GenderPerson genderPerson { get; set; }
        public int age { get; set; }
        [Required]
        public string? identification { get; set; }
        public string? address { get; set; }
        public string? phone { get; set; }
        public DateTime creationDate { get; set; }
    }
}