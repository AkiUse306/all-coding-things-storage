# acities System Architecture

The acities platform is a monorepo combining client, backend, engine, and web platform components. It is designed for scalability, maintainability, and modular growth.

## Core Domains

- Game Client: Java-based 3D client with ECS, physics, AI, and multiplayer support.
- Backend Services: ASP.NET Core microservices for auth, world state, economy, and social systems.
- Engine Modules: C++ libraries for performance-critical systems such as physics and pathfinding.
- Web Platform: React / Next.js dashboard for account management, marketplace, analytics, and moderation.

## Service Contracts

- REST API for account management, service discovery, and admin operations.
- WebSocket API for real-time game updates, matchmaking, and chat.
- JNI bindings for C++ modules in the Java client.

## Deployment

- Dockerize each service for consistent environment and orchestration.
- Use Kubernetes or Docker Compose for local and production orchestration.
- CI/CD pipeline validates buildability for Java, .NET, and web components.
