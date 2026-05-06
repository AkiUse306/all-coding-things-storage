using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.DependencyInjection;
using RivalBackend.Services;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddControllers();
builder.Services.AddSingleton<PlayerStatsService>();
builder.Services.AddSingleton<MatchmakingService>();

var app = builder.Build();
app.UseRouting();
app.UseAuthorization();
app.UseEndpoints(endpoints => endpoints.MapControllers());
app.MapGet("/status", () => Results.Ok(new { status = "ok", now = DateTime.UtcNow }));

app.Run("http://localhost:5000");
