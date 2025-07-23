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
        public async Task<ActionResult<List<ClienteDto>>> GetAll(int limit = 10, int lastId = 0)
        {
            var clientes = await _clienteService.GetAllAsync(limit, lastId);
            return Ok(clientes);
        }

        // GET: api/cliente/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ClienteDto>> GetById(long id)
        {
            var cliente = await _clienteService.GetByIdAsync(id);
            if (cliente == null)
                return NotFound();

            return cliente;
        }

        // POST: api/cliente
        [HttpPost]
        public async Task<ActionResult<ClienteDto>> Create([FromBody] ClienteDto dto)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            var created = await _clienteService.CreateAsync(dto);
            return Ok(created);
        }

        // PUT: api/cliente/5
        [HttpPut("{id}")]
        public async Task<ActionResult<ClienteDto>> Update(long id, [FromBody] ClienteDto dto)
        {
            var updated = await _clienteService.UpdateAsync(id, dto);
            if (updated == null)
                return NotFound();

            return Ok(updated);
        }

        // DELETE: api/cliente/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> Delete(long id)
        {
            var deleted = await _clienteService.DeleteAsync(id);
            if (!deleted)
                return NotFound();

            return NoContent();
        }

    }
}
