/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ylzl.eden.demo.manager.mq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ylzl.eden.common.mq.MessageQueueConsumer;
import org.ylzl.eden.common.mq.MessageQueueListener;
import org.ylzl.eden.common.mq.consumer.Acknowledgement;
import org.ylzl.eden.common.mq.model.Message;

import java.util.List;

/**
 * 用户消息消费者
 * <p>《阿里巴巴Java开发手册》Manager 层职责：</p>
 * <ul>
 *     <li>消息队列的封装（RocketMQ、Kafka 等）</li>
 *     <li>消息消费者的统一管理</li>
 *     <li>消息格式转换与异常处理</li>
 *     <li>消息幂等性保障与重试机制</li>
 * </ul>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@RequiredArgsConstructor
@Slf4j
@MessageQueueListener(topic = "demo-layer-user") // 该注解会触发消息消费
public class UserConsumer implements MessageQueueConsumer {

	/**
	 * 消费用户相关消息
	 * <p>消息处理流程：</p>
	 * <ol>
	 *     <li>接收消息并解析</li>
	 *     <li>执行业务逻辑处理</li>
	 *     <li>确认消息消费成功</li>
	 * </ol>
	 *
	 * @param messages 消息列表
	 * @param ack      消息确认器
	 */
	@Override
	public void consume(List<Message> messages, Acknowledgement ack) {
		log.info("消费用户消息: count={}", messages.size());
		try {
			for (Message message : messages) {
				// TODO: 实际业务处理逻辑
				// 1. 解析消息内容
				// 2. 执行业务操作（如：用户数据同步、状态更新等）
				// 3. 记录处理日志
				log.debug("处理消息: {}", message);
			}
			// 消息处理成功，确认消费
			ack.acknowledge();
		} catch (Exception e) {
			log.error("消息消费失败: {}", e.getMessage(), e);
			// 消费失败时不确认，消息将被重新投递
			throw e;
		}
	}
}
