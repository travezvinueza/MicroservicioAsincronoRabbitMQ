using client_person.Dto;
using client_person.Models;
using Riok.Mapperly.Abstractions;

namespace client_person.Mapper
{
    [Mapper]
    public partial class ClientMapper
    {
        public partial ClienteDto MapClientToDto(Cliente cliente);
        public partial Cliente MapDtoToClient(ClienteDto clienteDto);
    }

}