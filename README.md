<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/icon.png" align="right" />

# Layered Architecture

[![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/language-java-blue.svg)](https://github.com/shiyindaxiaojie/eden-demo-layer)
[![Build Status](https://github.com/shiyindaxiaojie/eden-demo-layer/actions/workflows/maven-ci.yml/badge.svg?branch=main)](https://github.com/shiyindaxiaojie/eden-demo-layer/actions)
[![License](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/readme/license-apache2.0-red.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![SonarCloud](https://sonarcloud.io/api/project_badges/measure?project=shiyindaxiaojie_eden-demo-layer&metric=alert_status)](https://sonarcloud.io/dashboard?id=shiyindaxiaojie_eden-demo-layer)

<p>
  <strong>A Classic Data-Model Oriented Architecture</strong>
</p>

[简体中文](./README-zh-CN.md) | English

---

## 📖 Introduction

**Layered Architecture** is a data-model oriented architectural style recommended by the *Alibaba Java Development Manual*. It separates concerns by having upper layers depend on lower layers (e.g., `Web` -> `Service` -> `DAO`). This improved modularity through Maven helps reduce system entropy in complex scenarios and enhances development and maintenance efficiency.

## 🏗️ Architecture

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/eden-demo-layer/component.png" alt="Architecture Diagram" width="100%" />
</div>

### Component Overview

| Component | Description |
|-----------|-------------|
| **eden-demo-layer-api** | API Layer. Exports interfaces as a `jar` package. |
| **eden-demo-layer-dao** | Persistence Layer. Interacts with `MySQL`, `Elasticsearch`, `MongoDB`, etc. |
| **eden-demo-layer-service** | Service Layer. Core business logic. |
| **eden-demo-layer-manager** | Manager Layer. Handles 3rd-party integrations, common service logic (caching, middleware), and `DAO` composition. |
| **eden-demo-layer-web** | Web Layer. Handles request forwarding, parameter validation, and simple view logic. |
| **eden-demo-layer-start** | Startup Module. Manages application configuration and delivery. |

## 🚀 Getting Started

### Prerequisites

Since `Spring Boot 2.4.x` and `3.0.x` vary significantly, we maintain matching branches:

- **Branch 2.4.x**: For `Spring Boot 2.4.x` (Min JDK 1.8)
- **Branch 2.7.x**: For `Spring Boot 2.7.x` (Min JDK 11)
- **Branch 3.0.x**: For `Spring Boot 3.0.x` (Min JDK 17)

### Installation

Clone the repository and install it to your local Maven repository. This project depends on [eden-architect](https://github.com/shiyindaxiaojie/eden-architect).

```bash
git clone https://github.com/shiyindaxiaojie/eden-demo-layer.git
cd eden-demo-layer
mvn install -T 4C
```

### Usage

#### Quick Start

The project defaults to the `dev` environment. All external component dependencies are disabled by default for easy startup.

1.  Run `mvn install` (add `-DskipTests` to skip tests).
2.  Enter `eden-demo-layer-start` directory and run `mvn spring-boot:run` or start the `LayerApplication` class.
3.  Test the demo API: [http://localhost:8082/api/users/1](http://localhost:8082/api/users/1).
4.  Accessing the root [http://localhost:8082](http://localhost:8082) will show a 404 page (frontend not included).

<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/common/404.png" alt="404 Page" width="500" />

#### Configuration

-   **Nacos**: Enable in `bootstrap-dev.yml` for service discovery and config management.
-   **Database**: Default is H2. Modify `application-dev.yml` to use MySQL.
-   **Other Components**: Enable Redis, RocketMQ, etc., via `xxx.enabled` properties.

## 📦 Deployment

### FatJar
```bash
mvn -T 4C clean package
java -Dserver.port=8082 -jar target/eden-demo-layer-start.jar
```

### Docker
```bash
docker build -f docker/Dockerfile -t eden-demo-layer:{tag} .
```

### Helm
```bash
helm install eden-demo-layer ./helm
```

## 📅 Versioning

We follow Semantic Versioning `x.y.z`.

## 📝 Changelog

Please see [CHANGELOG.md](https://github.com/shiyindaxiaojie/eden-demo-layer/blob/main/CHANGELOG.md).

## 📄 License

This project is licensed under the [Apache-2.0 License](https://www.apache.org/licenses/LICENSE-2.0.html).
