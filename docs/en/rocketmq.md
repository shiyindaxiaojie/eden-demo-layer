# RocketMQ Message Queue

## What

Apache RocketMQ is Alibaba's open-source distributed message middleware. This project integrates RocketMQ for async messaging, peak shaving, and more.

## Why

- High performance: Single machine supports millions of message backlog
- High availability: Supports master-slave sync, multi-replica mechanism
- Rich features: Supports ordered messages, delayed messages, transactional messages
- Alibaba ecosystem: Seamless integration with Dubbo, Nacos, etc.

## How

### 1. Install RocketMQ

```bash
# Docker method
docker run -d --name rmqnamesrv -p 9876:9876 apache/rocketmq:4.9.4 sh mqnamesrv
docker run -d --name rmqbroker -p 10911:10911 -p 10909:10909 \
  -e "NAMESRV_ADDR=host.docker.internal:9876" \
  apache/rocketmq:4.9.4 sh mqbroker
```

### 2. Enable Configuration

Modify `application-dev.yml`:

```yaml
rocketmq:
  enabled: true # Switch
  name-server: localhost:9876
  producer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    send-message-timeout: 3000
    retry-times-when-send-failed: 2
    retry-times-when-send-async-failed: 2
  consumer:
    namespace: ${spring.profiles.active}
    group: ${spring.application.name}
    pull-batch-size: 500
    consume-message-batch-max-size: 100
    consume-mode: CONCURRENTLY
    consume-thread-min: 8
    consume-thread-max: 64
    consume-timeout: 15
```

### 3. Send Messages

```java
@Autowired
private RocketMQTemplate rocketMQTemplate;

// Sync send
public void sendSync(String topic, String message) {
    rocketMQTemplate.syncSend(topic, message);
}

// Async send
public void sendAsync(String topic, String message) {
    rocketMQTemplate.asyncSend(topic, message, new SendCallback() {
        @Override
        public void onSuccess(SendResult sendResult) {
            log.info("Send success: {}", sendResult);
        }
        @Override
        public void onException(Throwable e) {
            log.error("Send failed", e);
        }
    });
}
```

### 4. Consume Messages

```java
@Component
@RocketMQMessageListener(
    topic = "demo-topic",
    consumerGroup = "demo-consumer-group"
)
public class DemoConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("Received message: {}", message);
        // Business processing
    }
}
```

## Cases

### Case 1: Delayed Messages

```java
// Delay levels: 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
rocketMQTemplate.syncSend("demo-topic", 
    MessageBuilder.withPayload("Delayed message").build(), 
    3000,  // Timeout
    3);    // Delay level 3 = 10s
```

### Case 2: Ordered Messages

```java
// Producer: Same hashKey messages go to same queue
rocketMQTemplate.syncSendOrderly("order-topic", message, orderId);

// Consumer: Use ordered consume mode
@RocketMQMessageListener(
    topic = "order-topic",
    consumerGroup = "order-consumer",
    consumeMode = ConsumeMode.ORDERLY
)
public class OrderConsumer implements RocketMQListener<String> {
    // ...
}
```

## Pitfalls

1. **Idempotent consumption**: Messages may be delivered multiple times, consumers must be idempotent
2. **Consume timeout**: Too short `consume-timeout` causes duplicate consumption
3. **Ordered messages**: Ordered consumption blocks queue, handle exceptions carefully
4. **Namespace**: Use different namespaces for different environments

## Best Practices

1. Use sync send for message reliability
2. Implement idempotent consumers with unique ID deduplication
3. Set reasonable consumer thread count to avoid backlog
4. Use namespace to isolate messages across environments
