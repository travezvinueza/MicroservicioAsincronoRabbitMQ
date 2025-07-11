using client_person.Models;

namespace client_person.Repository
{
    public interface IClienteRepository
    {
        Task<Cliente> GetByIdentificationAsync(string identification);
        Task<Cliente> GetByFullNameAsync(string fullName);
    }
}