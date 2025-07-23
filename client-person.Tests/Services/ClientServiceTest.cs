using Xunit;
using Microsoft.EntityFrameworkCore;
using client_person.Data;
using client_person.Service.Impl;
using client_person.Models;
using client_person.Dto;
using client_person.Mapper;
using client_person.Enum;
using BCrypt.Net;
using System.Threading.Tasks;
using static client_person.Exceptions.CustomExceptions;

namespace client_person.Tests.Services
{
    public class ClientServiceTest
    {
        private static DatabaseContext GetDbContext()
        {
            var options = new DbContextOptionsBuilder<DatabaseContext>()
                .UseInMemoryDatabase(databaseName: System.Guid.NewGuid().ToString())
                .Options;

            return new DatabaseContext(options);
        }

        [Fact]
        public async Task AddClient_ShouldAddClientToDatabase()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            var clienteDto = new ClienteDto
            {
                fullName = "Test Client",
                genderPerson = GenderPerson.MASCULINO,
                age = 30,
                identification = "1722722343",
                address = "La Floresta",
                phone = "0979317536",
                password = "password",
                state = true
            };

            var result = await service.CreateAsync(clienteDto);

            // Assert
            Assert.NotNull(result);
            Assert.Equal("Test Client", result.fullName);
            Assert.Equal(GenderPerson.MASCULINO, result.genderPerson);
            Assert.Equal(30, result.age);
            Assert.Equal("1722722343", result.identification);
            Assert.Equal("La Floresta", result.address);
            Assert.Equal("0979317536", result.phone);
            Assert.True(BCrypt.Net.BCrypt.Verify("password", result.password));
            Assert.True(result.state);
        }

        //Crear un cliente con identificación que ya existe
        [Fact]
        public async Task CreateAsync_ShouldThrowException_WhenIdentificationExists()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            var existing = new Cliente
            {
                fullName = "Cliente Existente",
                genderPerson = GenderPerson.MASCULINO,
                age = 28,
                identification = "7777777777",
                address = "Direccion",
                phone = "0990000000",
                password = BCrypt.Net.BCrypt.HashPassword("pass"),
                state = true
            };

            context.Clientes.Add(existing);
            await context.SaveChangesAsync();

            var duplicateDto = new ClienteDto
            {
                fullName = "Cliente Duplicado",
                genderPerson = GenderPerson.FEMENINO,
                age = 25,
                identification = "7777777777", // misma cédula
                address = "Nueva direccion",
                phone = "0991111111",
                password = "otro",
                state = true
            };

            // Act & Assert
            await Assert.ThrowsAsync<ConflictException>(() => service.CreateAsync(duplicateDto));
        }

        [Fact]
        public async Task GetAllAsync_ShouldReturnAllClients()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            context.Clientes.Add(new Cliente
            {
                fullName = "Cliente 1",
                genderPerson = GenderPerson.MASCULINO,
                age = 25,
                identification = "1111111111",
                address = "Direccion 1",
                phone = "0991111111",
                password = BCrypt.Net.BCrypt.HashPassword("123456"),
                state = true
            });

            context.Clientes.Add(new Cliente
            {
                fullName = "Cliente 2",
                genderPerson = GenderPerson.FEMENINO,
                age = 28,
                identification = "2222222222",
                address = "Direccion 2",
                phone = "0992222222",
                password = BCrypt.Net.BCrypt.HashPassword("abcdef"),
                state = true
            });

            await context.SaveChangesAsync();

            // Act
            var result = await service.GetAllAsync(limit: 10, lastId: 0);

            // Assert
            Assert.Equal(2, result.Count);
            Assert.Contains(result, c => c.identification == "1111111111");
            Assert.Contains(result, c => c.identification == "2222222222");
        }

        [Fact]
        public async Task GetByIdAsync_ShouldReturnCorrectClient()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            var cliente = new Cliente
            {
                fullName = "Cliente X",
                genderPerson = GenderPerson.MASCULINO,
                age = 35,
                identification = "3333333333",
                address = "Direccion X",
                phone = "0993333333",
                password = BCrypt.Net.BCrypt.HashPassword("secure"),
                state = true
            };

            context.Clientes.Add(cliente);
            await context.SaveChangesAsync();

            // Act
            var result = await service.GetByIdAsync(cliente.id);

            // Assert
            Assert.NotNull(result);
            Assert.Equal("Cliente X", result.fullName);
            Assert.Equal("3333333333", result.identification);
        }

        [Fact]
        public async Task DeleteAsync_ShouldDeleteClient()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            var cliente = new Cliente
            {
                fullName = "Cliente Delete",
                genderPerson = GenderPerson.FEMENINO,
                age = 40,
                identification = "4444444444",
                address = "Direccion Delete",
                phone = "0994444444",
                password = BCrypt.Net.BCrypt.HashPassword("delete"),
                state = true
            };

            context.Clientes.Add(cliente);
            await context.SaveChangesAsync();

            // Act
            var result = await service.DeleteAsync(cliente.id);

            // Assert
            Assert.True(result);
            Assert.Null(await context.Clientes.FindAsync(cliente.id));
        }

        // Intentar eliminar un cliente que no existe
        [Fact]
        public async Task DeleteAsync_ShouldReturnFalse_WhenClientNotFound()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            // Act
            var result = await service.DeleteAsync(999); // ID inexistente

            // Assert
            Assert.False(result);
        }

        [Fact]
        public async Task UpdateAsync_ShouldUpdateClient()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            var cliente = new Cliente
            {
                fullName = "Cliente Original",
                genderPerson = GenderPerson.MASCULINO,
                age = 29,
                identification = "5555555555",
                address = "Direccion Original",
                phone = "0995555555",
                password = BCrypt.Net.BCrypt.HashPassword("original"),
                state = true
            };

            context.Clientes.Add(cliente);
            await context.SaveChangesAsync();

            var updateDto = new ClienteDto
            {
                fullName = "Cliente Actualizado",
                genderPerson = GenderPerson.FEMENINO,
                age = 30,
                identification = "5555555555",
                address = "Direccion Nueva",
                phone = "0999999999",
                password = "nuevo_password",
                state = false
            };

            // Act
            var result = await service.UpdateAsync(cliente.id, updateDto);

            // Assert
            Assert.NotNull(result);
            Assert.Equal("Cliente Actualizado", result.fullName);
            Assert.Equal(GenderPerson.FEMENINO, result.genderPerson);
            Assert.Equal(30, result.age);
            Assert.Equal("Direccion Nueva", result.address);
            Assert.Equal("0999999999", result.phone);
            Assert.True(BCrypt.Net.BCrypt.Verify("nuevo_password", result.password));
            Assert.False(result.state);
        }

        // Intentar actualizar un cliente que no existe
        [Fact]
        public async Task UpdateAsync_ShouldReturnNull_WhenClientNotFound()
        {
            // Arrange
            var context = GetDbContext();
            var mapper = new ClientMapper();
            var service = new ClientesServiceImpl(context, mapper);

            var updateDto = new ClienteDto
            {
                fullName = "Cliente Inexistente",
                genderPerson = GenderPerson.MASCULINO,
                age = 20,
                identification = "9999999999",
                address = "Direccion",
                phone = "0991234567",
                password = "clave",
                state = true
            };

            // Act
            var result = await service.UpdateAsync(999, updateDto); // ID inexistente

            // Assert
            Assert.Null(result);
        }

    }

}