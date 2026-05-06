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

app.MapGet("/health", () => Results.Ok(new { status = "SocialService healthy" }));

app.MapGet("/social/friends/{playerId}", (int playerId) =>
{
    // Mock friends list - in real app, load from database
    return Results.Ok(new
    {
        playerId = playerId,
        friends = new[] { new { id = 2, name = "FriendOne" }, new { id = 3, name = "FriendTwo" } }
    });
});

app.MapPost("/social/friend/add", (FriendRequest request) =>
{
    // Mock friend addition - in real app, update relationships
    return Results.Ok(new { message = $"Friend request sent to player {request.FriendId}" });
});

app.MapGet("/social/chat/{channelId}/messages", (string channelId) =>
{
    // Mock chat messages - in real app, load recent messages
    return Results.Ok(new
    {
        channelId = channelId,
        messages = new[] { new { sender = "Player1", content = "Hello!", timestamp = DateTime.UtcNow } }
    });
});

app.MapPost("/social/chat/{channelId}/send", (string channelId, ChatMessage message) =>
{
    // Mock message sending - in real app, broadcast to channel subscribers
    return Results.Ok(new { message = "Message sent" });
});

app.Run();

public record FriendRequest(int PlayerId, int FriendId);
public record ChatMessage(string Content);
