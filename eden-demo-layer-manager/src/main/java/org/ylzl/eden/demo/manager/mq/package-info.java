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

/**
 * 消息队列处理层
 * <p>《阿里巴巴Java开发手册》Manager 层职责：</p>
 * <ul>
 *     <li>消息队列的封装（RocketMQ、Kafka 等）</li>
 *     <li>消息生产者与消费者的统一管理</li>
 *     <li>消息格式转换与异常处理</li>
 *     <li>消息幂等性保障与重试机制</li>
 * </ul>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
package org.ylzl.eden.demo.manager.mq;
