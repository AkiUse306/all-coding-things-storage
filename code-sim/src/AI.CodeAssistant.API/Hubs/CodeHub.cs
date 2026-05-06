using Microsoft.AspNetCore.SignalR;

namespace AI.CodeAssistant.API.Hubs;

public class CodeHub : Hub
{
    public async Task SendUpdate(string code)
    {
        await Clients.Others.SendAsync("ReceiveUpdate", code);
    }

    public async Task JoinProject(string projectId)
    {
        await Groups.AddToGroupAsync(Context.ConnectionId, projectId);
    }

    public async Task LeaveProject(string projectId)
    {
        await Groups.RemoveFromGroupAsync(Context.ConnectionId, projectId);
    }

    public async Task SendProjectUpdate(string projectId, string code)
    {
        await Clients.Group(projectId).SendAsync("ReceiveProjectUpdate", code);
    }
}