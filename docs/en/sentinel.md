# Sentinel Traffic Governance

## What

Sentinel is an open-source traffic governance component from Alibaba. This project integrates [Sentinel](https://github.com/shiyindaxiaojie/Sentinel) to implement rate limiting, circuit breaking, and system protection.

![](https://cdn.jsdelivr.net/gh/shiyindaxiaojie/cdn/sentinel/sentinel-dashboard-overview-custom.png)

## Why

- Flow Control: QPS or concurrency-based rate limiting to protect system stability
- Circuit Breaking: Automatic circuit breaking on service anomalies to prevent cascading failures
- System Protection: Adaptive protection based on system load
- Real-time Monitoring: Dashboard for real-time traffic and rule visualization

## How

### 1. Deploy Sentinel Dashboard

```bash
# Download and start
java -Dserver.port=8090 -jar sentinel-dashboard-1.8.6.jar
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
spring:
  cloud:
    sentinel:
      enabled: true              # Switch
      http-method-specify: true  # RESTful compatibility
      eager: true                # Immediate refresh to Dashboard
      transport:
        dashboard: localhost:8090
```

### 3. Nacos Rule Persistence

```yaml
spring:
  cloud:
    sentinel:
      datasource:
        flow:
          nacos:
            server-addr: ${spring.cloud.nacos.config.server-addr}
            namespace: ${spring.cloud.nacos.config.namespace}
            groupId: sentinel
            dataId: ${spring.application.name}-flow-rule
            rule-type: flow
            data-type: json
```

### 4. Using Annotations

```java
@SentinelResource(
    value = "getUserById",
    blockHandler = "getUserByIdBlockHandler",
    fallback = "getUserByIdFallback"
)
public User getUserById(Long id) {
    return userRepository.findById(id);
}

// Block handler
public User getUserByIdBlockHandler(Long id, BlockException ex) {
    return new User("Rate limited default user");
}

// Fallback handler
public User getUserByIdFallback(Long id, Throwable ex) {
    return new User("Fallback default user");
}
```

## Cases

### Case 1: QPS Flow Control Rule

Configure `{app-name}-flow-rule` in Nacos:

```json
[
  {
    "resource": "getUserById",
    "limitApp": "default",
    "grade": 1,
    "count": 100,
    "strategy": 0,
    "controlBehavior": 0
  }
]
```

Parameters:
- `grade`: 1=QPS, 0=Concurrency
- `count`: Threshold
- `controlBehavior`: 0=Fast Fail, 1=Warm Up, 2=Rate Limiter

### Case 2: Circuit Breaking Rule

```json
[
  {
    "resource": "getUserById",
    "grade": 0,
    "count": 5,
    "timeWindow": 10,
    "minRequestAmount": 5,
    "statIntervalMs": 1000,
    "slowRatioThreshold": 0.5
  }
]
```

Parameters:
- `grade`: 0=Slow Call Ratio, 1=Error Ratio, 2=Error Count
- `timeWindow`: Circuit break duration (seconds)
- `slowRatioThreshold`: Slow call ratio threshold

### Case 3: Global Exception Handler

```java
@Component
public class SentinelBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, 
                       BlockException e) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("{\"code\":429,\"message\":\"Too many requests\"}");
    }
}
```

## Pitfalls

1. **Rule Persistence**: Dashboard rules are lost after restart, use Nacos for persistence
2. **RESTful Compatibility**: Enable `http-method-specify` to distinguish HTTP methods
3. **Resource Name**: `@SentinelResource` value must match the resource in rules
4. **Exception Handling**: `blockHandler` and `fallback` method signatures must match

## Best Practices

1. Use Nacos for rule persistence to prevent rule loss
2. Configure flow control and circuit breaking rules for core APIs
3. Set reasonable circuit breaking thresholds to avoid false positives
4. Regularly review Dashboard monitoring data and adjust rules
