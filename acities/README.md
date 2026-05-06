# acities

acities is a professional full-stack monorepo for a persistent open-world life simulation platform. It combines a Java-based game client, ASP.NET Core backend microservices, high-performance native engine modules, and a modern web administration platform.

## Repository Overview

This repository is organized into the following core components:

- `/client-java` — Java game client with OpenGL rendering, ECS architecture, core simulation systems, and multiplayer foundations.
- `/backend-csharp` — ASP.NET Core microservices for authentication, persistent world state, economy simulation, and social systems.
- `/engine-cpp` — High-performance C++ modules for physics, pathfinding, and large-scale simulation workloads.
- `/web-platform` — React / Next.js frontend for dashboards, marketplace, account management, and administrative tooling.

## Design Principles

- Modular, scalable architecture with clear separation of concerns
- System-driven world simulation instead of scripted gameplay
- Production-oriented project structure with Docker and CI/CD support
- Clean code practices aligned with SOLID and domain-driven design
- Native performance and interoperability where required

## Key Capabilities

- Procedural city generation with districts, buildings, and road networks
- Building interiors with room layouts and descriptions
- Individual NPC configurations loaded from JSON files
- Real-time multiplayer with server-authoritative services
- Dynamic economy, NPC simulation, and emergent world systems
- Cross-platform build support for Java, .NET, C++, and web
- Dockerized deployment and GitHub Actions validation

## Getting Started

1. Review the README in each subproject for implementation details.
2. Build the Java client:
   ```bash
   cd client-java
   gradle clean build
   ```
3. Build the backend services:
   ```bash
   cd backend-csharp
   dotnet restore
   dotnet build
   ```
4. Run the web platform:
   ```bash
   cd web-platform
   npm install
   npm run dev
   ```
5. Start the local service stack:
   ```bash
   docker-compose up --build
   ```

## Documentation and Support

- `docs/architecture.md` — High-level architecture and service contract overview
- `docker-compose.yml` — Local orchestration for backend and web services
- `.github/workflows/ci.yml` — Continuous integration for Java, .NET, and web builds

## Status

This repository currently provides a production-ready architectural scaffold and service surface. Core systems are structured for rapid expansion into a full AAA-quality open-world simulation.
