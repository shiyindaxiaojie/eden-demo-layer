# Jaeger Distributed Tracing

## What

Jaeger is a CNCF open-source distributed tracing system. This project integrates OpenTracing + Jaeger for microservice call chain tracing.

## Why

- Standardized: Based on OpenTracing standard, replaceable tracing backend
- Visualization: Provides intuitive call chain visualization interface
- Performance analysis: Analyze inter-service call latency and bottlenecks
- Cloud native: CNCF graduated project, integrates well with Kubernetes ecosystem

## How

### 1. Deploy Jaeger

```bash
# Docker method (All-in-One)
docker run -d --name jaeger \
  -p 5775:5775/udp \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 14268:14268 \
  -p 14250:14250 \
  jaegertracing/all-in-one:1.35
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
opentracing:
  jaeger:
    service-name: ${spring.application.name}
    enabled: true              # Switch
    log-spans: true            # Log output Span
    enable-b3-propagation: true  # Support B3 format propagation
    enable-w3c-propagation: true # Support W3C format propagation
    const-sampler:
      decision: true           # Full sampling
    probabilistic-sampler:
      sampling-rate: 1         # Sampling rate 100%
  spring:
    cloud:
      async:
        enabled: false         # Disable async config (conflicts with higher Spring versions)
```

### 3. Custom Span

```java
@Autowired
private Tracer tracer;

public void doSomething() {
    Span span = tracer.buildSpan("doSomething").start();
    try (Scope scope = tracer.activateSpan(span)) {
        // Add tags
        span.setTag("user.id", userId);
        span.setTag("order.id", orderId);
        
        // Business logic
        
    } catch (Exception e) {
        span.setTag("error", true);
        span.log(Map.of("error.message", e.getMessage()));
        throw e;
    } finally {
        span.finish();
    }
}
```

## Cases

### Case 1: HTTP Request Auto-tracing

Framework auto-instruments, no manual coding required. Visit `http://localhost:16686` to view Jaeger UI.

### Case 2: Cross-service Tracing

```java
// Service A calls Service B
@Autowired
private RestTemplate restTemplate;

public String callServiceB() {
    // RestTemplate auto-propagates TraceId
    return restTemplate.getForObject("http://service-b/api/data", String.class);
}
```

### Case 3: Async Task Tracing

```java
@Autowired
private Tracer tracer;

public void asyncTask() {
    Span span = tracer.activeSpan();
    CompletableFuture.runAsync(() -> {
        try (Scope scope = tracer.activateSpan(span)) {
            // Continue tracing in async task
        }
    });
}
```

## Pitfalls

1. **Sampling rate**: Don't use 100% sampling rate in production, affects performance
2. **Async config**: `opentracing.spring.cloud.async.enabled` conflicts with higher Spring versions
3. **Span closure**: Ensure Span is properly closed, otherwise data is incomplete
4. **Storage backend**: Use Elasticsearch or Cassandra for production storage

## Best Practices

1. Use probabilistic sampling in production, adjust rate based on traffic
2. Add custom tags for critical business, easier problem location
3. Use B3 or W3C format for cross-system tracing
4. Combine with logging system, correlate logs through TraceId
