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
