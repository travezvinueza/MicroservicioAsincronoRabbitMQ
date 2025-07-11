using client_person.Config;
using client_person.Data;
using client_person.Mapper;
using client_person.Repository;
using client_person.Repository.Impl;
using client_person.Service;
using client_person.Service.Impl;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();

builder.Services.AddDbContext<DatabaseContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddTransient<IClienteService, ClientesServiceImpl>();
builder.Services.AddHostedService<ClienteRequestConsumer>();
builder.Services.AddSingleton<ClientMapper>();
builder.Services.AddScoped<IClienteRepository, ClienteRepositoryImpl>();

builder.Services.Configure<RabbitMQSettings>(
    builder.Configuration.GetSection("RabbitMQ"));


// Add services to the container.
// Learn more about configuring OpenAPI at https://aka.ms/aspnet/openapi
builder.Services.AddOpenApi();
builder.Services.AddSwaggerGen();
var app = builder.Build();

app.Use(async (context, next) =>
{
   if (context.Request.Path == "/")
   {
       context.Response.Redirect("/swagger/index.html", permanent: true);
       return;
   }

   await next();
});

using (var scope = app.Services.CreateScope())
{
    var dbContext = scope.ServiceProvider.GetRequiredService<DatabaseContext>();
    await dbContext.Database.MigrateAsync();
}

app.UseSwagger();
app.UseSwaggerUI();

app.UseHttpsRedirection();
app.MapControllers();


await app.RunAsync();