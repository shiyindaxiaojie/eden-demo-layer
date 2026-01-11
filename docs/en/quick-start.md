# Quick Start

## What

Layered Architecture is a data-model oriented architectural style recommended by the *Alibaba Java Development Manual*. This project is a best practice example based on Layered Architecture, integrating commonly used enterprise-level components and configurations.

## Why

- Classic layered architecture reduces system complexity
- Upper layers depend on lower layers, satisfying single responsibility principle
- Out-of-the-box component integration solutions
- Complete configuration examples and documentation

## How

### Requirements

- JDK 1.8+ (2.4.x branch) / JDK 11+ (2.7.x branch) / JDK 17+ (3.0.x branch)
- Maven 3.6+
- Git

### Clone Project

```bash
git clone https://github.com/shiyindaxiaojie/eden-demo-layer.git
cd eden-demo-layer
```

### Build Project

```bash
# Install dependency project
git clone https://github.com/shiyindaxiaojie/eden-architect.git
cd eden-architect && mvn install -T 4C -DskipTests

# Build this project
cd eden-demo-layer && mvn install -T 4C -DskipTests
```

### Start Application

```bash
cd eden-demo-layer-start
mvn spring-boot:run
```

Or run `LayerApplication.java` directly.

### Verify Startup

Visit [http://localhost:8082/api/users/1](http://localhost:8082/api/users/1) to verify the API.

## Cases

### Case 1: Local Development Environment

Default configuration uses H2 in-memory database, no additional dependencies required:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:db;DB_CLOSE_ON_EXIT=TRUE
    driver-class-name: org.h2.Driver
```

### Case 2: Connect to MySQL Database

Modify `application-dev.yml`:

```yaml
spring:
  datasource:
    username: root
    password: your_password
    url: jdbc:mysql://localhost:3306/demo?useSSL=false&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## Pitfalls

1. **Dependency Issue**: Must install `eden-architect` project first
2. **Port Conflict**: Default port is 8082, modify `server.port` if conflicted
3. **JDK Version**: Choose the correct JDK version based on branch

## Best Practices

1. Use H2 in-memory database for quick startup in development
2. Enable components on demand to avoid unnecessary dependencies
3. Use Liquibase to manage database changes
