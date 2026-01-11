# Zipkin Distributed Tracing

## What

Zipkin is Twitter's open-source distributed tracing system. This project integrates Spring Cloud Sleuth + Zipkin for microservice call chain tracing.

## Why

- Spring ecosystem: Seamless integration with Spring Cloud
- Auto-instrumentation: Sleuth auto-adds tracing for HTTP, RPC, MQ, etc.
- Multiple reporting methods: Supports HTTP, Kafka, RabbitMQ reporting
- Lightweight: Simple deployment, low resource usage

## How

### 1. Deploy Zipkin

```bash
# Docker method
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin:2.23
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  sleuth:
    sampler:
      probability: 1           # Sampling rate 100%
    async:
      configurer:
        enabled: false         # Conflicts with higher Spring versions, disable
  zipkin:
    enabled: true              # Switch
    discovery-client-enabled: true
    locator:
      discovery:
        enabled: true
    sender:
      type: kafka              # Reporting method: web/kafka/rabbit
    kafka:
      topic: zipkin            # Kafka Topic
```

### 3. Kafka Reporting Configuration

```yaml
spring:
  kafka:
    producer:
      batch-size: 4096
      buffer-memory: 40960
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

### 4. Custom Span

```java
@Autowired
private Tracer tracer;

public void doSomething() {
    Span span = tracer.nextSpan().name("doSomething").start();
    try (Tracer.SpanInScope ws = tracer.withSpan(span)) {
        // Add tags
        span.tag("user.id", userId);
        
        // Business logic
        
    } catch (Exception e) {
        span.error(e);
        throw e;
    } finally {
        span.end();
    }
}
```

## Cases

### Case 1: HTTP Reporting

```yaml
spring:
  zipkin:
    enabled: true
    base-url: http://localhost:9411
    sender:
      type: web
```

### Case 2: Kafka Reporting (Recommended)

```yaml
spring:
  zipkin:
    enabled: true
    sender:
      type: kafka
    kafka:
      topic: zipkin
  kafka:
    bootstrap-servers: localhost:9092
```

Zipkin server configured for Kafka consumption:

```bash
docker run -d --name zipkin \
  -e KAFKA_BOOTSTRAP_SERVERS=localhost:9092 \
  -p 9411:9411 \
  openzipkin/zipkin:2.23
```

### Case 3: Log TraceId Correlation

```xml
<!-- logback-spring.xml -->
<pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%X{traceId}] [%thread] %-5level %logger{36} - %msg%n</pattern>
```

## Pitfalls

1. **Sampling rate**: Don't use 100% sampling rate in production
2. **Async config**: `spring.sleuth.async.configurer.enabled` conflicts with higher Spring versions
3. **Kafka config**: When using Kafka reporting, ensure Zipkin server also has Kafka configured
4. **Storage backend**: Use Elasticsearch or MySQL for production storage

## Best Practices

1. Use Kafka reporting to avoid HTTP reporting performance overhead
2. Set production sampling rate to 0.1 (10%) or lower
3. Configure log output TraceId for log correlation
4. Use Elasticsearch storage for large-scale data queries
