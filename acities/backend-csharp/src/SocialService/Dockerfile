FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src
COPY ./SocialService.csproj ./
RUN dotnet restore ./SocialService.csproj
COPY . ./
RUN dotnet publish ./SocialService.csproj -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app
COPY --from=build /app/publish ./
ENTRYPOINT ["dotnet", "SocialService.dll"]
