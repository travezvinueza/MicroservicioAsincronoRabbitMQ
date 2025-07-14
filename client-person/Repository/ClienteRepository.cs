using client_person.Data;
using client_person.Models;
using Microsoft.EntityFrameworkCore;

namespace client_person.Repository
{
    public interface IClienteRepository
    {
        Task<Cliente> GetByIdentificationAsync(string identification);
        Task<Cliente> GetByFullNameAsync(string fullName);
    }

    public class ClienteRepository : IClienteRepository
    {
        private readonly DatabaseContext _context;

        public ClienteRepository(DatabaseContext context)
        {
            _context = context;
        }

        public async Task<Cliente> GetByIdentificationAsync(string identification)
        {
            var cliente = await _context.Clientes
                .Where(c => c.identification!.Contains(identification))
                .FirstOrDefaultAsync();

            return cliente ?? throw new InvalidOperationException($"Cliente con identificación {identification} no encontrado.");
        }

        public async Task<Cliente> GetByFullNameAsync(string fullName)
        {
            var cliente = await _context.Clientes
               .Where(c => c.fullName!.Contains(fullName))
               .FirstOrDefaultAsync();

            return cliente ?? throw new InvalidOperationException($"Cliente con nombre completo {fullName} no encontrado.");
        }

    }
}