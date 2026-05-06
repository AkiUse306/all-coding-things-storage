FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build
WORKDIR /src
COPY ./EconomyService.csproj ./
RUN dotnet restore ./EconomyService.csproj
COPY . ./
RUN dotnet publish ./EconomyService.csproj -c Release -o /app/publish

FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS runtime
WORKDIR /app
COPY --from=build /app/publish ./
ENTRYPOINT ["dotnet", "EconomyService.dll"]
