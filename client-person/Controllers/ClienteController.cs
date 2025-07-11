using client_person.Dto;
using client_person.Service;
using Microsoft.AspNetCore.Mvc;

namespace client_person.Controllers
{
    [Route("api/v1/[controller]")]
    [ApiController]
    public class ClienteController : ControllerBase
    {
        private readonly IClienteService _clienteService;

        public ClienteController(IClienteService clienteService)
        {
            _clienteService = clienteService;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<ClienteDto>>> GetAll()
        {
            var clientes = await _clienteService.GetAllAsync();
            return Ok(clientes);
        }

        // GET: api/cliente/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ClienteDto>> GetById(int id)
        {
            var cliente = await _clienteService.GetByIdAsync(id);
            if (cliente == null)
                return NotFound($"Cliente con ID {id} no encontrado.");

            return Ok(cliente);
        }

        // POST: api/cliente
        [HttpPost]
        public async Task<ActionResult<ClienteDto>> Create([FromBody] ClienteDto dto)
        {
            var created = await _clienteService.CreateAsync(dto);
            return CreatedAtAction(nameof(GetById), new { id = created.id }, created);
        }

        // PUT: api/cliente/5
        [HttpPut("{id}")]
        public async Task<ActionResult<ClienteDto>> Update(int id, [FromBody] ClienteDto dto)
        {
            var updated = await _clienteService.UpdateAsync(id, dto);
            if (updated == null)
                return NotFound($"No se pudo actualizar. Cliente con ID {id} no encontrado.");

            return Ok(updated);
        }

        // DELETE: api/cliente/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(int id)
        {
            var deleted = await _clienteService.DeleteAsync(id);
            if (!deleted)
                return NotFound($"No se encontró el cliente con ID {id}.");

            return NoContent();
        }

    }
}
