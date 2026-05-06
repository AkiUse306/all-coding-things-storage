using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();
builder.Services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme)
    .AddJwtBearer(options =>
    {
        options.TokenValidationParameters = new TokenValidationParameters
        {
            ValidateIssuer = true,
            ValidateAudience = true,
            ValidateLifetime = true,
            ValidIssuer = builder.Configuration["Jwt:Issuer"] ?? "acities",
            ValidAudience = builder.Configuration["Jwt:Audience"] ?? "acities",
            IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(builder.Configuration["Jwt:Key"] ?? "supersecretkey"))
        };
    });
builder.Services.AddAuthorization();

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseAuthentication();
app.UseAuthorization();

app.MapGet("/health", () => Results.Ok(new { status = "AuthService healthy" }));

app.MapPost("/auth/login", (LoginRequest request) =>
{
    // Simple mock authentication - in real app, verify against database
    if (request.Username == "player" && request.Password == "password")
    {
        var claims = new[] { new Claim(ClaimTypes.Name, request.Username) };
        var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes("supersecretkey"));
        var creds = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);
        var token = new JwtSecurityToken(
            issuer: "acities",
            audience: "acities",
            claims: claims,
            expires: DateTime.Now.AddHours(1),
            signingCredentials: creds
        );
        return Results.Ok(new { token = new JwtSecurityTokenHandler().WriteToken(token) });
    }
    return Results.Unauthorized();
});

app.MapPost("/auth/register", (RegisterRequest request) =>
{
    // Mock registration - in real app, save to database
    return Results.Created("/auth/register", new { message = $"User {request.Username} registered" });
});

app.MapGet("/auth/profile", (ClaimsPrincipal user) =>
{
    return Results.Ok(new { username = user.Identity?.Name });
}).RequireAuthorization();

app.Run();

public record LoginRequest(string Username, string Password);
public record RegisterRequest(string Username, string Password, string Email);
