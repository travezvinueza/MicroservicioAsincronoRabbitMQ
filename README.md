# Programación Reactiva con RabbitMQ

Proyecto realizado bajo mvc con arquitetcura de microservicios para clientes y sus movimientos!

## Tecnologías Utilizadas

- .Net: Para la construcción del primer microservicio de cliente-persona.
- Spring Boot: Para la construcción del segundo microservicio de cuenta-movimientos .
- RabbitMQ: Para la comunicacion asincrona.
- PostgreSQL y MySQL: Bases de datos relacionales para el almacenamiento de datos.
- Docker: Para la contenerización y despliegue en cualquier entorno.
- ModelMapper y Riok.Mapperly: Para mapear objetos de un modelo a otro.
- OpenAPI: Para la documentación de la API.

# Configuración

```
### Pasos que se realizo para generar los tests unitarios y de integración del primer microservicio cliente-persona desarrollado con .Net y C#
- Generar el proyecto test en la raiz => dotnet new xunit -o client-person.Tests
- Ingresar al proyecto => cd client-person.Tests
- Dentro del proyecto hacer referencia => dotnet add reference ../client-person/client-person.csproj

### Instalar paquetes
- Mockea => dotnet add package Moq
- Guarda los datos en memoria => dotnet add package Microsoft.EntityFrameworkCore.InMemory

### Comandos test
- Comando básico para ejecutar todos los tests => dotnet test
- El mismo comando pero con más detalle en la salida => dotnet test --logger "console;verbosity=detailed"
- Ejecuta todos los test de tu clase => dotnet test --filter "FullyQualifiedName~ClientServiceTest"
- Ejecuta un test especifico => dotnet test --filter "FullyQualifiedName~ClientControllersTest.Update_ExistingId_ReturnsOk"
```

El proyecto tiene swagger para la documentacion de endpoints.

- El primer servicio se deplega en el puerto 8080 

```
  http://localhost:8080/swagger-ui/index.html#/
```

- El segundo servicio se deplega en el puerto 8081

```
  http://localhost:8081/swagger-ui/index.html#/
```

## Imagenes
<img width="1024" height="1024" alt="micro" src="https://github.com/user-attachments/assets/90346e69-cf34-44d6-a9b5-28c0be61b488" />
