using client_person.Data;
using client_person.Models;
using Microsoft.EntityFrameworkCore;

namespace client_person.Repository.Impl
{

    public class ClienteRepositoryImpl : IClienteRepository
    {
        private readonly DatabaseContext _context;

        public ClienteRepositoryImpl(DatabaseContext context)
        {
            _context = context ?? throw new ArgumentNullException(nameof(context));
        }

        public async Task<Cliente> GetByIdentificationAsync(string identification)
        {
            var cliente = await _context.Clientes
                 .FirstOrDefaultAsync(c => c.identification == identification);

            return cliente ?? throw new InvalidOperationException($"Cliente con identificación {identification} no encontrado.");
        }

        public async Task<Cliente> GetByFullNameAsync(string fullName)
        {
            var cliente = await _context.Clientes
                .FirstOrDefaultAsync(c => c.fullName == fullName);

            return cliente ?? throw new InvalidOperationException($"Cliente con nombre completo {fullName} no encontrado.");
        }

    }
}