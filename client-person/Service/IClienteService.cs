using client_person.Dto;

namespace client_person.Service
{
    public interface IClienteService
    {
        Task<List<ClienteDto>> GetAllAsync();
        Task<ClienteDto?> GetByIdAsync(long id);
        Task<ClienteDto> CreateAsync(ClienteDto dto);
        Task<ClienteDto?> UpdateAsync(long id, ClienteDto dto);
        Task<bool> DeleteAsync(long id);
        
    }
}