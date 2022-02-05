<img src="https://cdn.jsdelivr.net/gh/shiyindaxiaojie/images/readme/icon.png" align="right" />

[license-apache2.0]:https://www.apache.org/licenses/LICENSE-2.0.html
[github-action]:https://github.com/eden-lab/eden-demo-layer/actions
[sonarcloud-dashboard]:https://sonarcloud.io/dashboard?id=eden-lab_eden-demo-layer

# 分层架构

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/images/readme/language-java-blue.svg) [![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/images/readme/license-apache2.0-red.svg)][license-apache2.0] [![](https://github.com/eden-lab/eden-demo-layer/workflows/build/badge.svg)][github-action] [![](https://sonarcloud.io/api/project_badges/measure?project=eden-lab_eden-demo-layer&metric=alert_status)][sonarcloud-dashboard]

本项目使用分层架构构建，分层架构是《阿里巴巴Java开发手册》推荐使用的一种面向数据模型的架构风格，默认上层依赖于下层，例如 Web 层依赖 Service 层、Service 层又依赖 DAO 
层，在垂直业务领域能够满足单一职责原则，通过 Maven 多模块化的开发模式，可以帮助降低复杂应用场景的系统熵值，提升系统开发和运维效率。

> 参考文档请查看 [WIKI](https://github.com/eden-lab/eden-demo-layer/wiki) 。

## 组件构成

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/images/eden-demo-layer/component.png)

* **eden-demo-layer-api**：API层，对外以 jar 包的形式提供接口
* **eden-demo-layer-dao**：数据持久层，与底层 MySQL、Elasticsearch、MongoDB 等进行数据交互。
* **eden-demo-layer-service**：业务逻辑服务层
* **eden-demo-layer-manager**：通用业务处理层，对第三方平台进行接口封装，对 Service 层通用能力的下沉，如缓存方案、中间件通用处理，与 DAO 层交互，对多个 DAO 的组合复用。
* **eden-demo-layer-web**：请求处理层，对访问控制进行转发，各类基本参数校验，或者不复用的业务简单处理等
* **eden-demo-layer-start**：程序启动入口

## 运行流程

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/images/eden-demo-layer/sequence.png)

## 如何构建

* master 分支对应的是 `eden-demo-layer 2.4.x`，最低支持 JDK 1.8。
* 1.5.x 分支对应的是 `eden-demo-layer 1.5.x`，最低支持 JDK 1.8。
* 2.4.x 分支对应的是 `eden-demo-layer 2.4.x`，最低支持 JDK 1.8。

分层架构使用 Maven 来构建，最快的使用方式是将本项目 `git clone` 到本地，然后执行以下命令：

```bash
./mvnw install
```

执行完毕后，项目将被安装到本地 Maven 仓库。

## 如何使用

### 运行应用

- 在 `项目` 目录下运行 `mvn install`（如果不想运行测试，可以加上 `-DskipTests` 参数）。
- 进入 `eden-demo-layer-start` 目录，执行 `mvn spring-boot:run` 或者启动 `Application` 类。运行成功的话，可以看到 `Spring Boot` 启动成功的界面。
- 生成的应用中，已经实现了一个简单的 `Rest` 请求，可以在浏览器中输入 http://localhost:8080/api/users/1 进行测试。

> 请注意，我们已经把常用的依赖纳入 eden-dependencies 管理，不建议带版本号覆盖原有的依赖。

## 版本规范

项目的版本号格式为 x.x.x 的形式，其中 x 的数值类型为数字，从 0 开始取值，且不限于 0~9 这个范围。项目处于孵化器阶段时，第一位版本号固定使用 0，即版本号为 0.x.x 的格式。

由于 `Spring Boot 1.5.x` 和 `Spring Boot 2.4.x` 在架构层面有很大的变更，因此我们采取跟 Spring Boot 版本号一致的版本:

* 1.5.x 版本适用于 `Spring Boot 1.5.x`
* 2.4.x 版本适用于 `Spring Boot 2.4.x`
