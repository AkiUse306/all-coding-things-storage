using System.Net.Http;
using System.Text;
using System.Text.Json;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.StaticFiles;
using Microsoft.Extensions.FileProviders;

var builder = WebApplication.CreateBuilder();
builder.WebHost.UseUrls("http://localhost:3000");

builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowAll", policy =>
    {
        policy.AllowAnyOrigin().AllowAnyMethod().AllowAnyHeader();
    });
});

var app = builder.Build();
app.UseCors("AllowAll");

// Serve static files from wwwroot
var wwwrootPath = Path.Combine(Directory.GetCurrentDirectory(), "wwwroot");
if (!Directory.Exists(wwwrootPath))
{
    Directory.CreateDirectory(wwwrootPath);
}

var fileProvider = new PhysicalFileProvider(wwwrootPath);
var staticFileOptions = new StaticFileOptions
{
    FileProvider = fileProvider,
    RequestPath = "",
    ContentTypeProvider = new FileExtensionContentTypeProvider()
};

app.UseStaticFiles(staticFileOptions);
app.UseRouting();

// Backend API proxy
const string backendUrl = "http://localhost:5000";
var httpClient = new HttpClient();

// Player endpoints
app.MapGet("/api/player/{id}", async (string id) =>
{
    try
    {
        var response = await httpClient.GetAsync($"{backendUrl}/player/{id}");
        var content = await response.Content.ReadAsStringAsync();
        return Results.Ok(JsonSerializer.Deserialize<object>(content));
    }
    catch (Exception ex)
    {
        return Results.BadRequest(new { error = ex.Message });
    }
});

app.MapPost("/api/player/{id}/score", async (string id, ScoreUpdate update) =>
{
    try
    {
        var json = JsonSerializer.Serialize(update);
        var request = new StringContent(json, Encoding.UTF8, "application/json");
        var response = await httpClient.PostAsync($"{backendUrl}/player/{id}/score", request);
        var content = await response.Content.ReadAsStringAsync();
        return Results.Ok(JsonSerializer.Deserialize<object>(content));
    }
    catch (Exception ex)
    {
        return Results.BadRequest(new { error = ex.Message });
    }
});

app.MapPost("/api/player/{id}/stats", async (string id, PlayerStatsUpdate update) =>
{
    try
    {
        var json = JsonSerializer.Serialize(update);
        var request = new StringContent(json, Encoding.UTF8, "application/json");
        var response = await httpClient.PostAsync($"{backendUrl}/player/{id}/stats", request);
        var content = await response.Content.ReadAsStringAsync();
        return Results.Ok(JsonSerializer.Deserialize<object>(content));
    }
    catch (Exception ex)
    {
        return Results.BadRequest(new { error = ex.Message });
    }
});

// Matchmaking endpoints
app.MapPost("/api/matchmake", async (MatchRequest req) =>
{
    try
    {
        var json = JsonSerializer.Serialize(req);
        var request = new StringContent(json, Encoding.UTF8, "application/json");
        var response = await httpClient.PostAsync($"{backendUrl}/matchmake", request);
        var content = await response.Content.ReadAsStringAsync();
        return Results.Ok(JsonSerializer.Deserialize<object>(content));
    }
    catch (Exception ex)
    {
        return Results.BadRequest(new { error = ex.Message });
    }
});

app.MapGet("/api/matchmake/queue", async () =>
{
    try
    {
        var response = await httpClient.GetAsync($"{backendUrl}/matchmake/queue");
        var content = await response.Content.ReadAsStringAsync();
        return Results.Ok(JsonSerializer.Deserialize<object>(content));
    }
    catch (Exception ex)
    {
        return Results.BadRequest(new { error = ex.Message });
    }
});

// Health check
app.MapGet("/api/health", () =>
{
    return Results.Ok(new { status = "ok", timestamp = DateTime.UtcNow });
});

// Default route - serve index.html
app.MapFallback(() => Results.File(Path.Combine(wwwrootPath, "index.html"), "text/html"));

Console.WriteLine("\n╔════════════════════════════════════════════╗");
Console.WriteLine("║  🚀 Local Dashboard - Create Rivals      ║");
Console.WriteLine("╚════════════════════════════════════════════╝\n");
Console.WriteLine("🌐 Dashboard:  http://localhost:3000");
Console.WriteLine("📡 Backend:    http://localhost:5000");
Console.WriteLine("\nServing static files from: " + wwwrootPath);
Console.WriteLine("\nPress Ctrl+C to stop\n");

await app.RunAsync();

public class ScoreUpdate
{
    public int Add { get; set; }
}

public class PlayerStatsUpdate
{
    public int? Score { get; set; }
    public int? Level { get; set; }
}

public class MatchRequest
{
    public string PlayerId { get; set; } = "";
}