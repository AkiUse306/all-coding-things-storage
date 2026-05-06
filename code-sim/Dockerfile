# Build stage
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src

# Copy csproj files and restore
COPY ["src/AI.CodeAssistant.API/AI.CodeAssistant.API.csproj", "src/AI.CodeAssistant.API/"]
COPY ["src/AI.CodeAssistant.Application/AI.CodeAssistant.Application.csproj", "src/AI.CodeAssistant.Application/"]
COPY ["src/AI.CodeAssistant.Domain/AI.CodeAssistant.Domain.csproj", "src/AI.CodeAssistant.Domain/"]
COPY ["src/AI.CodeAssistant.Infrastructure/AI.CodeAssistant.Infrastructure.csproj", "src/AI.CodeAssistant.Infrastructure/"]
RUN dotnet restore "src/AI.CodeAssistant.API/AI.CodeAssistant.API.csproj"

# Copy everything else and build
COPY . .
WORKDIR "/src/src/AI.CodeAssistant.API"
RUN dotnet build "AI.CodeAssistant.API.csproj" -c Release -o /app/build

# Publish
RUN dotnet publish "AI.CodeAssistant.API.csproj" -c Release -o /app/publish

# Runtime stage
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app
COPY --from=build /app/publish .
ENTRYPOINT ["dotnet", "AI.CodeAssistant.API.dll"]