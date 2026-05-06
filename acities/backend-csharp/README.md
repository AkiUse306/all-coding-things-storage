# backend-csharp

ASP.NET Core microservices for the acities backend.

## Services

- `AuthService` - JWT authentication, OAuth hooks, account management.
- `GameStateService` - Persistent world state, player inventory, world synchronization.
- `EconomyService` - Supply/demand simulation, marketplace, currency flow.
- `SocialService` - Friends, chat, events, moderation hooks.

## Getting Started

```bash
cd backend-csharp
dotnet restore
dotnet build
```

Each service is isolated and can be deployed independently.
