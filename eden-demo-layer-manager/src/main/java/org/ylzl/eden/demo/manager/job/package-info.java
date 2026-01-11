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
 * 定时任务处理层
 * <p>《阿里巴巴Java开发手册》Manager 层职责：</p>
 * <ul>
 *     <li>定时任务的封装与调度（XXL-JOB、Quartz 等）</li>
 *     <li>离线数据处理、批量任务执行</li>
 *     <li>异步任务的统一管理</li>
 *     <li>任务执行结果的预处理与异常转化</li>
 * </ul>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
package org.ylzl.eden.demo.manager.job;
