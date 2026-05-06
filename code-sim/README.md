# AI Code Assistant

An enterprise-grade AI-powered code assistant website built with ASP.NET Core, featuring layered architecture, real-time collaboration, and AI integration.

## Features

- **Clean Architecture**: Domain, Application, Infrastructure, and Presentation layers
- **AI Code Analysis**: Integrate with OpenAI for code analysis and suggestions
- **Real-time Collaboration**: SignalR for live code editing
- **Authentication**: JWT-based authentication with ASP.NET Identity
- **Database**: SQL Server with Entity Framework Core
- **Scalable**: Designed for enterprise use

## Architecture

```
Presentation Layer (Web UI / API)
        ↓
Application Layer (Business Logic)
        ↓
Domain Layer (Core Models)
        ↓
Infrastructure Layer (DB, AI, External APIs)
```

## Getting Started

### Prerequisites

- .NET 8.0 SDK
- SQL Server (or LocalDB for development)

### Setup

1. Clone the repository
2. Navigate to the project directory
3. Restore packages: `dotnet restore`
4. Update connection string in `appsettings.json`
5. Run database migrations: `dotnet ef database update` (from Infrastructure project)
6. Run the API: `dotnet run --project src/AI.CodeAssistant.API`

### Docker

Build and run with Docker:

```bash
docker build -t ai-code-assistant .
docker run -p 8080:80 ai-code-assistant
```

## API Endpoints

- `POST /api/ai/analyze` - Analyze code with AI
- SignalR Hub: `/codehub` - Real-time collaboration

## Testing

Run unit tests: `dotnet test tests/AI.CodeAssistant.UnitTests`

Run integration tests: `dotnet test tests/AI.CodeAssistant.IntegrationTests`