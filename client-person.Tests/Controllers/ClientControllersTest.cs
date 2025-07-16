using Xunit;
using Moq;
using client_person.Controllers;
using client_person.Models;
using client_person.Dto;
using client_person.Service;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using client_person.Enum;

namespace client_person.Tests.Controllers
{
    public class ClientControllersTest
    {
        private readonly Mock<IClienteService> _clienteServiceMock;
        private readonly ClienteController _controller;

        public ClientControllersTest()
        {
            _clienteServiceMock = new Mock<IClienteService>();
            _controller = new ClienteController(_clienteServiceMock.Object);
        }

        [Fact] 
        public async Task GetAll_ReturnsOkResult_WithListOfClientes()
        {
            var clientes = new List<ClienteDto>
            {
               new ClienteDto { id = 1, fullName = "Cliente 1", genderPerson = GenderPerson.MASCULINO, age = 30, identification = "1234567890", address = "Address 1", phone = "1234567890", password = "password1", state = true },
               new ClienteDto { id = 2, fullName = "Cliente 2", genderPerson = GenderPerson.FEMENINO, age = 25, identification = "0987654321", address = "Address 2", phone = "0987654321", password = "password2", state = true }
            };

            _clienteServiceMock.Setup(service => service.GetAllAsync()).ReturnsAsync(clientes);

            var result = await _controller.GetAll();
            Assert.Equal(clientes, result.Value);

        }

        [Fact] 
        public async Task GetById_ExistingId_ReturnsOkWithCliente()
        {
            var cliente = new ClienteDto { id = 1, fullName = "Cliente 1", genderPerson = GenderPerson.MASCULINO, age = 30, identification = "1234567890", address = "Address 1", phone = "1234567890", password = "password1", state = true };

            _clienteServiceMock.Setup(service => service.GetByIdAsync(1)).ReturnsAsync(cliente);

            var result = await _controller.GetById(1);
            Assert.Equal(cliente, result.Value);
        }

        [Fact]
        public async Task GetById_ReturnsNotFound_WhenDoesNotExist()
        {
            _clienteServiceMock.Setup(service => service.GetByIdAsync(99)).ReturnsAsync((ClienteDto)null);

            var result = await _controller.GetById(99);

            Assert.IsType<NotFoundResult>(result.Result);
        }

        [Fact] 
        public async Task Create_ValidCliente_ReturnsOk()
        {
            var dto = new ClienteDto { fullName = "Nuevo Cliente", genderPerson = GenderPerson.MASCULINO, age = 30, identification = "1234567890", address = "Nueva Dirección", phone = "1234567890", password = "password123", state = true };
            _clienteServiceMock.Setup(service => service.CreateAsync(dto)).ReturnsAsync(dto);

            var result = await _controller.Create(dto);

            var okResult = Assert.IsType<OkObjectResult>(result.Result);
            var returnValue = Assert.IsType<ClienteDto>(okResult.Value);
            Assert.Equal(dto.fullName, returnValue.fullName);
        }

        [Fact] 
        public async Task Create_InvalidModel_ReturnsBadRequest()
        {
            _controller.ModelState.AddModelError("fullName", "Required");

            var result = await _controller.Create(new ClienteDto());

            var badRequest = Assert.IsType<BadRequestObjectResult>(result.Result);
            Assert.IsType<SerializableError>(badRequest.Value);
        }

        [Fact]
        public async Task Update_ExistingId_ReturnsOk()
        {
            var dto = new ClienteDto { id = 1, fullName = "Actualizado", genderPerson = GenderPerson.MASCULINO, age = 31, identification = "1234567890", address = "Dirección Actualizada", phone = "1234567890", password = "newpassword", state = true };
            _clienteServiceMock.Setup(service => service.UpdateAsync(1, dto)).ReturnsAsync(dto);

            var result = await _controller.Update(1, dto);

            var okResult = Assert.IsType<OkObjectResult>(result.Result);
            var returnValue = Assert.IsType<ClienteDto>(okResult.Value);
            Assert.Equal("Actualizado", returnValue.fullName);
        }

        [Fact]
        public async Task Update_NonExistingId_ReturnsNotExist()
        {
            var dto = new ClienteDto { id = 99, fullName = "No Existe", genderPerson = GenderPerson.MASCULINO, age = 30, identification = "1234567890", address = "Dirección No Existe", phone = "1234567890", password = "password123", state = true };
            _clienteServiceMock.Setup(service => service.UpdateAsync(99, dto)).ReturnsAsync((ClienteDto)null);

            var result = await _controller.Update(99, dto);

            Assert.IsType<NotFoundResult>(result.Result);
        }

        [Fact]
        public async Task Delete_ExistingId_ReturnsNoContent()
        {
            _clienteServiceMock.Setup(service => service.DeleteAsync(1)).ReturnsAsync(true);

            var result = await _controller.Delete(1);

            Assert.IsType<NoContentResult>(result);
        }

        [Fact]
        public async Task DeleteClient_ReturnsNotFound_WhenClientDoesNotExist()
        {
            _clienteServiceMock.Setup(service => service.DeleteAsync(99)).ReturnsAsync(false);

            var result = await _controller.Delete(99);

            Assert.IsType<NotFoundResult>(result);
        }

    }
}
