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

app.MapGet("/health", () => Results.Ok(new { status = "EconomyService healthy" }));

app.MapGet("/economy/market", () =>
{
    // Mock market data - in real app, calculate from supply/demand algorithms
    return Results.Ok(new
    {
        timestamp = DateTime.UtcNow,
        commodities = new[]
        {
            new { name = "Food", price = 10.5, supply = 100, demand = 80 },
            new { name = "Fuel", price = 25.0, supply = 50, demand = 70 },
            new { name = "Housing", price = 500.0, supply = 20, demand = 30 }
        }
    });
});

app.MapPost("/economy/trade", (TradeRequest request) =>
{
    // Mock trade processing - in real app, update player inventory and market
    return Results.Ok(new { message = $"Traded {request.Quantity} {request.Item} for {request.Quantity * 10.0} currency" });
});

app.MapGet("/player/{id}/balance", (int id) =>
{
    // Mock balance - in real app, load from database
    return Results.Ok(new { playerId = id, balance = 1000.0 });
});

app.Run();

public record TradeRequest(string Item, int Quantity);
