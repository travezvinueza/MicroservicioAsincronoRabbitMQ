using client_person.Models;
using Microsoft.EntityFrameworkCore;

namespace client_person.Data
{
    public class DatabaseContext : DbContext
    {
        public DatabaseContext(DbContextOptions<DatabaseContext> options) : base(options)
        {

        }
        public DbSet<Cliente> Clientes { get; set; }
        public DbSet<Persona> Personas { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {

            modelBuilder.Entity<Persona>().ToTable("Personas");
            modelBuilder.Entity<Cliente>().ToTable("Clientes");

            modelBuilder.Entity<Persona>()
               .HasIndex(p => p.identification)
               .IsUnique();

            modelBuilder.Entity<Persona>()
               .Property(p => p.creationDate)
               .HasDefaultValueSql("NOW()");

            modelBuilder.Entity<Cliente>()
               .Property(c => c.state)
               .HasDefaultValue(true);

            base.OnModelCreating(modelBuilder);
        }

    }

}
