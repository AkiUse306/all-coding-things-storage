var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddRazorPages();

builder.Services.AddSingleton(new AppInfo(
    Name: "Akincraft Hub",
    Description: "A C# ASP.NET Core portal for the Akincraft voxel engine project."));

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Error");
}

app.UseRouting();

app.UseAuthorization();

app.MapGet("/api/project", (AppInfo info) => Results.Json(new
{
    info.Name,
    info.Description,
    Repository = "https://github.com/AkiUse306/akincraft",
    Engine = "C++ Akincraft prototype",
    Website = "ASP.NET Core Razor Pages"
}));

app.MapStaticAssets();
app.MapRazorPages()
   .WithStaticAssets();

app.Run();

internal sealed record AppInfo(string Name, string Description);
