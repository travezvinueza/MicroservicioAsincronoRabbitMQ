using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace client_person.Models
{
    [Table("Clientes")]
    public class Cliente : Persona
    {
        [Required]
        public string? password { get; set; }

        public bool state { get; set; }

    }
}