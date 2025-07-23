using System.Net;
using System.Text.Json;
using client_person.Models.Dto;
using Microsoft.AspNetCore.Diagnostics;
using static client_person.Exceptions.CustomExceptions;

namespace client_person.Exceptions
{
    public class GlobalExceptionHandler : IExceptionHandler
    {
        private readonly ILogger<GlobalExceptionHandler> _logger;

        public GlobalExceptionHandler(ILogger<GlobalExceptionHandler> logger)
        {
            _logger = logger;
        }

        public async ValueTask<bool> TryHandleAsync(HttpContext httpContext, Exception exception, CancellationToken cancellationToken)
        {
            _logger.LogError(exception, "Unhandled exception occurred: {Message}", exception.Message);

            httpContext.Response.ContentType = "application/json";

            var statusCode = exception switch
            {
                NotFoundException => (int)HttpStatusCode.NotFound,
                ConflictException => (int)HttpStatusCode.Conflict,
                ValidationException => (int)HttpStatusCode.BadRequest,
                UnauthorizedException => (int)HttpStatusCode.Unauthorized,
                ForbiddenException => (int)HttpStatusCode.Forbidden,
                InvalidOperationException => (int)HttpStatusCode.BadRequest,
                BadHttpRequestException => (int)HttpStatusCode.BadRequest,
                JsonException => (int)HttpStatusCode.BadRequest, // para errores de enums
                _ => (int)HttpStatusCode.InternalServerError
            };

            httpContext.Response.StatusCode = statusCode;

            var errorResponse = new ErrorResponse
            {
                StatusCode = statusCode,
                Title = exception.GetType().Name,
                Message = exception.Message
            };

            await httpContext.Response.WriteAsJsonAsync(errorResponse, cancellationToken);
            return true;
        }
    }
}