using client_person.Data;
using client_person.Dto;
using client_person.Mapper;
using Microsoft.EntityFrameworkCore;
using static client_person.Exceptions.CustomExceptions;
namespace client_person.Service.Impl
{

    public class ClientesServiceImpl : IClienteService
    {
        private readonly DatabaseContext _context;

        private readonly ClientMapper _mapper;
        public ClientesServiceImpl(DatabaseContext context, ClientMapper mapper)
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<ClienteDto> CreateAsync(ClienteDto dto)
        {
            var exists = await _context.Clientes.AnyAsync(c => c.identification == dto.identification);
            if (exists)
            {
                throw new ConflictException($"Ya existe un cliente con la identificación: {dto.identification}");
            }

            var entity = _mapper.MapDtoToClient(dto);
            entity.password = BCrypt.Net.BCrypt.HashPassword(dto.password);
            _context.Clientes.Add(entity);
            await _context.SaveChangesAsync();
            return _mapper.MapClientToDto(entity);
        }

        public async Task<bool> DeleteAsync(long id)
        {
            var cliente = await _context.Clientes.FindAsync(id);
            if (cliente == null)
                return false;

            _context.Clientes.Remove(cliente);
            await _context.SaveChangesAsync();
            return true;
        }

        public async Task<List<ClienteDto>> GetAllAsync(int limit = 10, int lastId = 0)
        {
            var clientes = await _context.Clientes
            .AsNoTracking()
            .Where(c => c.id > lastId)
            .OrderBy(c => c.id)
            .Take(limit)
            .ToListAsync();

            return clientes.Select(_mapper.MapClientToDto).ToList();
        }

        public async Task<ClienteDto?> GetByIdAsync(long id)
        {
            var cliente = await _context.Clientes.FindAsync(id);
            return cliente == null ? null : _mapper.MapClientToDto(cliente);
        }

        public async Task<ClienteDto?> UpdateAsync(long id, ClienteDto dto)
        {
            var cliente = await _context.Clientes.FindAsync(id);
            if (cliente == null)
                return null;

            cliente.fullName = dto.fullName;
            cliente.address = dto.address;
            cliente.phone = dto.phone;
            if (!string.IsNullOrWhiteSpace(dto.password))
            {
                cliente.password = BCrypt.Net.BCrypt.HashPassword(dto.password);
            }
            cliente.genderPerson = dto.genderPerson;
            cliente.identification = dto.identification;
            cliente.age = dto.age;
            cliente.state = dto.state;

            await _context.SaveChangesAsync();
            return _mapper.MapClientToDto(cliente);
        }
    }
}