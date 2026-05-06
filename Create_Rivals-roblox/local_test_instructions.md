# Local Test Instructions (Roblox + C# Backend)

## 1) Run C# backend
1. Install .NET SDK 8.0+.
2. In terminal:
   ```bash
   cd backend
   dotnet run
   ```
3. Confirm: `http://localhost:5000/status` returns JSON.

## 2) Setup Roblox game in Studio
1. Open your place and run `RobloxProject/OneClickSetup.lua` or `StudioBootstrap.lua` to create objects.
2. Copy code from `RobloxProject/` files into Studio scripts and modules.
3. Add `ExternalCoreIntegration.lua` in ServerScriptService and require it in `GameInit`.
4. Enable HTTP requests in Game Settings.

## 3) Test integration
1. Play in Roblox Studio.
2. On player join, server should call backend for matchmake and score update.
3. Check output in Studio for `Matchmade` and `Updated external score` logs.
4. If failures, confirm backend is running and URL is `http://localhost:5000`.

## 4) Quick API check
Use curl:
```bash
curl http://localhost:5000/status
curl http://localhost:5000/player/123
curl -X POST http://localhost:5000/player/123/score -H "Content-Type: application/json" -d '{"Add": 10}'
```

## 5) Next steps
- Replace backend in production with a secure, scaled core (Azure/AWS/GCP). 
- For Unreal, use HTTP/WebSocket integration from Unreal to this backend, then link to Roblox via backend matchmaking API.
