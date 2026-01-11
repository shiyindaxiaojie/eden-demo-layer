<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/icon.png" align="right" />

# Layered Architecture

[![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/language-java-blue.svg)](https://github.com/shiyindaxiaojie/eden-demo-layer)
[![Build Status](https://github.com/shiyindaxiaojie/eden-demo-layer/actions/workflows/maven-ci.yml/badge.svg?branch=main)](https://github.com/shiyindaxiaojie/eden-demo-layer/actions)
[![License](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/license-apache2.0-red.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=shiyindaxiaojie_eden-demo-layer&metric=alert_status)](https://sonarcloud.io/dashboard?id=shiyindaxiaojie_eden-demo-layer)

<p>
  <strong>Classic Data-Model Oriented Layered Application Architecture</strong>
</p>

English | [简体中文](./README-zh-CN.md)

---

This project is built using the Layered Architecture. Layered Architecture is a data-model oriented architectural style recommended by the *Alibaba Java Development Manual*. It follows the principle that upper layers depend on lower layers (e.g., `Web` layer depends on `Service` layer, `Service` layer depends on `DAO` layer). This approach satisfies the single responsibility principle in vertical business domains. Through Maven multi-module development, it helps reduce system entropy in complex scenarios and improves development and operational efficiency. For more details, please check the [WIKI](https://github.com/shiyindaxiaojie/eden-demo-layer/wiki).

## Documentation

📚 For detailed component integration guides, please refer to the documentation:

- [English Documentation](./docs/en/README.md) - Component integration guides in English
- [中文文档](./docs/zh-CN/README.md) - 中文组件集成指南

The documentation covers:
- Quick Start Guide
- Registry & Configuration Center (Nacos)
- Cache Components (Redis)
- Data Source Components (MySQL, Liquibase, ShardingSphere, Dynamic-Datasource)
- Message Queue Components (RocketMQ, Kafka, Dynamic-MQ)
- Monitoring & Observability (CAT, Jaeger, Zipkin, Sentinel, Arthas)
- RPC Components (Dubbo, Dynamic-TP)
- Task Scheduling (XXL-Job)

## Component Structure

```mermaid
---
title: Alibaba Layered Application Architecture Component Diagram
---
flowchart TB
    %% Active Adapters - Top
    subgraph ActiveAdapters[" "]
        direction LR
        RPC_CLIENT(["«Active Adapter»<br/>RPC Client"])
        JOB{{"Job Scheduler"}}
        MQ_CONSUMER(["«Active Adapter»<br/>MQ Consumer"])
        APP_TERMINAL(["«Active Adapter»<br/>APP Terminal"])
    end

    %% Layered Architecture Core Components
    WEB["«Web Layer»<br/>eden-demo-layer-web"]
    START["«Bootstrap»<br/>eden-demo-layer-start"]
    SERVICE["«Service Layer»<br/>eden-demo-layer-service"]
    API["«API Layer»<br/>eden-demo-layer-api"]
    MANAGER["«Manager Layer»<br/>eden-demo-layer-manager"]
    DAO["«DAO Layer»<br/>eden-demo-layer-dao"]

    %% Interface Nodes
    rpc((rpc))
    http((http))

    %% Passive Drivers - Bottom
    subgraph PassiveDrivers[" "]
        direction LR
        THIRD_PARTY(["«Passive Driver»<br/>Third-party API"])
        MYSQL[("«Passive Driver»<br/>MySQL")]
        REDIS[("«Passive Driver»<br/>Redis")]
        MQ_PRODUCER[("«Passive Driver»<br/>MQ")]
        ES[("«Passive Driver»<br/>Elasticsearch")]
        MONGO[("«Passive Driver»<br/>MongoDB")]
    end

    %% Active Adapter Connections
    RPC_CLIENT -.->|Network Call| rpc
    rpc ---|RPC Interface| SERVICE
    APP_TERMINAL -.->|Frontend-Backend| http
    http ---|REST Interface| WEB
    RPC_CLIENT -.->|Code Integration| API
    JOB <-.->|Task Scheduling| WEB
    MQ_CONSUMER <-.->|Consume Messages| SERVICE

    %% Internal Component Connections
    START --> WEB
    WEB -->|Request Forwarding, Validation| SERVICE
    SERVICE -->|Implements Interface| API
    SERVICE -->|Business Logic| MANAGER
    MANAGER -->|Data Access| DAO

    %% Passive Driver Connections
    MANAGER -.->|API Call| THIRD_PARTY
    MANAGER -.->|Read/Write Cache| REDIS
    DAO -.->|Read/Write Data| MYSQL
    DAO -.->|Read/Write Index| ES
    DAO -.->|Read/Write Data| MONGO
    SERVICE -.->|Produce Messages| MQ_PRODUCER

    %% Style Definitions
    style WEB fill:#90EE90,stroke:#333,stroke-width:2px
    style START fill:#90EE90,stroke:#333,stroke-width:2px
    style SERVICE fill:#90EE90,stroke:#333,stroke-width:2px
    style API fill:#F0E68C,stroke:#333,stroke-width:2px
    style MANAGER fill:#90EE90,stroke:#333,stroke-width:2px
    style DAO fill:#90EE90,stroke:#333,stroke-width:2px
    
    style RPC_CLIENT fill:#87CEEB,stroke:#333,stroke-width:1px
    style JOB fill:#87CEEB,stroke:#333,stroke-width:1px
    style MQ_CONSUMER fill:#87CEEB,stroke:#333,stroke-width:1px
    style APP_TERMINAL fill:#87CEEB,stroke:#333,stroke-width:1px
    
    style THIRD_PARTY fill:#FFB6C1,stroke:#333,stroke-width:1px
    style MYSQL fill:#FFB6C1,stroke:#333,stroke-width:1px
    style REDIS fill:#FFB6C1,stroke:#333,stroke-width:1px
    style MQ_PRODUCER fill:#FFB6C1,stroke:#333,stroke-width:1px
    style ES fill:#FFB6C1,stroke:#333,stroke-width:1px
    style MONGO fill:#FFB6C1,stroke:#333,stroke-width:1px
    
    style rpc fill:#fff,stroke:#333
    style http fill:#fff,stroke:#333
```

* **eden-demo-layer-web**: Web Layer. Handles request forwarding, parameter validation, and simple non-reusable business logic.
* **eden-demo-layer-service**: Service Layer. Core business logic implementation.
* **eden-demo-layer-api**: API Layer. Exports interfaces as a `jar` package (DTOs, Service interfaces).
* **eden-demo-layer-manager**: Manager Layer. Common business processing layer for third-party integrations, common service capabilities (caching, middleware), and DAO composition/reuse.
* **eden-demo-layer-dao**: DAO Layer. Data persistence layer interacting with `MySQL`, `Elasticsearch`, `MongoDB`, etc.
* **eden-demo-layer-start**: Application bootstrap entry, unified management of application configuration and delivery.

## Runtime Flow

```mermaid
%%{init: {
  'theme': 'base',
  'themeVariables': {
    'primaryColor': '#90EE90',
    'primaryTextColor': '#000',
    'primaryBorderColor': '#333',
    'lineColor': '#333',
    'secondaryColor': '#87CEEB',
    'tertiaryColor': '#FFB6C1',
    'noteBkgColor': '#FFFACD',
    'noteTextColor': '#333',
    'noteBorderColor': '#DAA520',
    'actorBkg': '#87CEEB',
    'actorBorder': '#333',
    'actorTextColor': '#000',
    'signalColor': '#333',
    'signalTextColor': '#333'
  },
  'sequence': {
    'actorMargin': 50,
    'boxMargin': 10,
    'boxTextMargin': 5,
    'noteMargin': 10,
    'messageMargin': 35,
    'mirrorActors': true,
    'useMaxWidth': true
  }
}}%%
sequenceDiagram
    autonumber
    
    box rgb(135,206,235,0.3) Active Adapter
        participant A as Active Adapter
    end
    box rgb(144,238,144,0.3) Layered Application Architecture
        participant B as eden-demo-layer-web
        participant C as eden-demo-layer-service
        participant D as eden-demo-layer-manager
        participant E as eden-demo-layer-dao
    end
    box rgb(255,182,193,0.3) Passive Driver
        participant F as Passive Driver
    end

    Note over A,F: Scenario 1: HTTP Data Update Request
    A->>B: 1. Send write request
    B->>B: 2. Parameter validation
    B->>C: 3. Call Service layer
    C->>D: 4. Call Manager layer
    D->>E: 5. Call DAO layer
    E->>F: 6. Execute data write
    F-->>E: 
    E-->>D: 7. Return operation result
    D-->>C: 8. Return processing result
    C-->>B: 9. Assemble response data
    B-->>A: 10. Response

    Note over A,F: Scenario 2: HTTP Data Query Request
    A->>B: 11. Send read request
    B->>B: 12. Parameter validation
    B->>C: 13. Call Service layer
    C->>D: 14. Call Manager layer
    D->>E: 15. Call DAO layer
    E->>F: 16. Execute data read
    F-->>E: 
    E-->>D: 17. Return query data
    D-->>C: 18. Return processing result
    C-->>B: 19. Assemble response data
    B-->>A: 20. Response

    Note over A,F: Scenario 3: RPC Remote Call
    A->>C: 21. RPC call to Service
    C->>D: 22. Call Manager layer
    D->>E: 23. Call DAO layer
    E->>F: 24. Execute data operation
    F-->>E: 
    E-->>D: 25. Return operation result
    D-->>C: 26. Return processing result
    C-->>A: 27. RPC response
```

## How to Build

Due to significant architectural changes between `Spring Boot 2.4.x` and `Spring Boot 3.0.x`, we maintain branches aligned with Spring Boot versions:

* 2.4.x branch for `Spring Boot 2.4.x`, minimum JDK 1.8.
* 2.7.x branch for `Spring Boot 2.7.x`, minimum JDK 11.
* 3.0.x branch for `Spring Boot 3.0.x`, minimum JDK 17.

This project uses Maven for building. The quickest way to get started is to `git clone` to your local machine. To simplify unnecessary technical details, this project depends on [eden-architect](https://github.com/shiyindaxiaojie/eden-architect). Execute `mvn install -T 4C` in the project root directory to complete the build.

## How to Run

### Quick Start

This project is configured with a dev environment by default. For your convenience, all external component dependencies are disabled.

1. Run `mvn install` in the project directory (add `-DskipTests` parameter to skip tests).
2. Navigate to `eden-demo-layer-start` directory, execute `mvn spring-boot:run` or start the `LayerApplication` class. If successful, you'll see the Spring Boot startup screen.
3. A simple `RestController` interface is implemented in this application. Click [Demo API](http://localhost:8082/api/users/1) to test.
4. Since frontend-backend separation is the mainstream approach, please implement pages as needed. Accessing [http://localhost:8082](http://localhost:8082) will redirect to a 404 page.

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/common/404.png)

### Configuration Tuning

**Enable Service Registry and Configuration Management**: We recommend using `Nacos`. You can refer to [Nacos Quick Start](https://nacos.io/en-us/docs/quick-start.html) for quick setup. Modify the configuration file according to your Nacos address: [bootstrap-dev.yml](https://github.com/shiyindaxiaojie/eden-demo-layer/blob/main/eden-demo-layer-start/src/main/resources/config/bootstrap-dev.yml):

```yaml
spring:
  cloud:
    nacos:
      discovery: # Service Registry
        enabled: true # Disabled by default, enable as needed
      config: # Configuration Center
        enabled: true # Disabled by default, enable as needed
```

**Modify Default Data Source**: This project uses `H2` in-memory database by default, with `Liquibase` automatically initializing SQL scripts at startup. If you're using an external MySQL database, adjust the connection information here: [application-dev.yml](https://github.com/shiyindaxiaojie/eden-demo-layer/blob/main/eden-demo-layer-start/src/main/resources/config/application-dev.yml), and remove any `H2` related configurations.

```yaml
spring:
#  h2: # In-memory database
#    console:
#      enabled: true # Do not enable in production
#      path: /h2-console
#      settings:
#        trace: false
#        web-allow-others: false
  datasource: # Data source management
    username: 
    password: 
    url: jdbc:mysql://host:port/schema?rewriteBatchedStatements=true&useSSL=false&useOldAliasMetadataBehavior=true&useUnicode=true&serverTimezone=GMT%2B8
    driver-class-name: com.mysql.cj.jdbc.Driver
```

Additionally, this project includes usage examples for common components like `Redis` cache, `RocketMQ` message queue, and `ShardingSphere` database sharding, all disabled by default via `xxx.enabled`. You can enable configurations as needed to complete component integration.
