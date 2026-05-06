using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.MapGet("/health", () => Results.Ok(new { status = "GameStateService healthy" }));

app.MapGet("/world/state", () =>
{
    // Mock world state - in real app, load from database/cache
    return Results.Ok(new
    {
        timestamp = DateTime.UtcNow,
        players = new[] { new { id = 1, name = "PlayerOne", position = new { x = 100, y = 100 } } },
        npcs = new[] { new { id = 2, name = "Shopkeeper", position = new { x = 300, y = 200 } } },
        buildings = new[] { new { id = 3, type = "residential", position = new { x = 600, y = 300 } } }
    });
});

app.MapPost("/player/{id}/position", (int id, PositionUpdate update) =>
{
    // Mock position update - in real app, validate and save to database
    return Results.Ok(new { message = $"Player {id} position updated to ({update.X}, {update.Y})" });
});

app.MapGet("/player/{id}/inventory", (int id) =>
{
    // Mock inventory - in real app, load from database
    return Results.Ok(new
    {
        playerId = id,
        items = new[] { new { name = "Wallet", quantity = 1 }, new { name = "Keys", quantity = 1 } }
    });
});

app.Run();

public record PositionUpdate(float X, float Y);
