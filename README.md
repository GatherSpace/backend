# GatherSpace

**GatherSpace** is a virtual workspace platform that enables real-time, immersive collaboration through proximity-based voice/video communication and customizable digital environments. It aims to replicate the fluidity of physical office interactions within a remote-first architecture.

## Overview

GatherSpace introduces a new paradigm for distributed teamwork. Instead of rigid scheduled meetings, users interact by moving their avatars in shared virtual spaces. Voice and video communication are dynamically triggered based on proximity, enabling seamless, spontaneous collaboration.

## Architecture

GatherSpace follows a modular architecture, leveraging the **Spring Boot** framework for its backend services. The project adheres to domain-driven design, separating configuration, features, and utility layers.

```
gatherspace/
├── docker-compose.yml       # Local Docker setup
├── dockerfile               # Dockerfile for service container
├── pom.xml                  # Maven build file
├── mvnw*                    # Maven wrapper scripts
├── src/
│   ├── main/
│   │   ├── java/com/hari/gatherspace/
│   │   │   ├── config/                   # JWT, CORS, WebSocket, Security configs
│   │   │   ├── features/
│   │   │   │   ├── auth/                # Authentication APIs
│   │   │   │   ├── avatar/              # Avatar management
│   │   │   │   ├── element/             # Spatial elements in maps
│   │   │   │   ├── map/                 # Maps and layouts
│   │   │   │   ├── space/               # Room/space orchestration
│   │   │   │   ├── user/                # User management
│   │   │   │   └── usersession/         # Session persistence & token validation
│   │   │   └── GatherspaceApplication.java
│   │   └── resources/
│   │       ├── application.properties   # Shared config
│   │       ├── application-dev.properties
│   │       └── ...
│   └── test/
│       └── java/com/hari/gatherspace/
│           └── GatherspaceApplicationTests.java
└── README.md
```

## Technologies

| Layer                  | Technology                |
|------------------------|---------------------------|
| Backend Framework      | Spring Boot (Java)        |
| API Documentation      | OpenAPI 3.0               |
| Real-Time Communication| WebSockets                |
| Authentication         | JWT                       |
| Database (Dev)         | H2                        |
| Containerization       | Docker                    |

## API Interface

The service exposes REST endpoints as defined in its OpenAPI specification. The interface includes:

- **Auth**: `/api/signup`, `/api/login`, `/api/refresh-token`
- **Users & Sessions**: `/api/usersessions`, `/api/user/metadata`, `/api/user/avatars`
- **Spaces**: `/api/space`, `/api/space/{spaceId}`, `/api/maps`, `/api/elements`
- **Admin Capabilities**: Creation of elements, maps, avatars via `/api/admin/*`

You can generate clients or use Swagger UI for interacting with these endpoints during development.

## Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+
- Docker (optional for local orchestration)

### Build and Run

```bash
git clone https://github.com/GatherSpace/backend.git
cd backend/gatherspace
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

This starts the service on `http://localhost:8080` using an in-memory H2 database.

### API Documentation

Once the service is running, navigate to:

```
http://localhost:8080/swagger-ui/index.html
```

To view interactive API documentation powered by Springdoc OpenAPI.

## Roadmap

The following major features and improvements are under active planning and development:

| Feature / Milestone                             | Status       | Target Release |
|--------------------------------------------------|--------------|----------------|
| WebRTC Integration (Video & Voice Chat)          | In Progress  | v1.1           |
| Proximity-based Chat Enhancements                | Planned      | v1.2           |
| Application-wide Refactoring                     | Planned      | v1.2           |
| Extensive Test Coverage and Unit Testing         | Planned      | v1.3           |
| CI/CD Build Pipelines and PR Review Automation   | Planned      | v1.4           |
| Developer Automation (Pre-commit Hooks, Scripts) | Planned      | v1.4           |
| Slack / Trello Integration                       | In Design    | v1.5           |
| OAuth2 / OpenID Connect Authentication           | Planned      | v1.5           |
| Interactive Whiteboards in Virtual Rooms         | Planned      | v1.6           |
| Room Theme Customization                         | Planned      | v1.6           |

> Note: Releases follow semantic versioning. Each milestone is tracked in the issue tracker and updated continuously.

## Contribution Guidelines

We welcome contributions from the community.

### Steps to Contribute

1. Fork the repository
2. Create a feature branch:
   ```bash
   git checkout -b feature/<issue><name>
   ```
3. Commit changes:
   ```bash
   git commit -m "[add|fix|feat|wip]: <message>"
   ```
4. Push to your fork and open a Pull Request.

Refer to `CONTRIBUTING.md` for code style, review guidelines, and contributor agreement.

## License

GatherSpace is licensed under the MIT License. See [LICENSE](LICENSE) for full terms.
