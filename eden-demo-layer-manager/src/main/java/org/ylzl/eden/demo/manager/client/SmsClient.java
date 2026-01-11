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

package org.ylzl.eden.demo.manager.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 短信服务客户端
 * <p>《阿里巴巴Java开发手册》Manager 层：对第三方平台封装的层，预处理返回结果及转化异常信息</p>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class SmsClient {

	/**
	 * 发送短信验证码
	 *
	 * @param phone 手机号
	 * @param code  验证码
	 * @return 是否发送成功
	 */
	public boolean sendVerificationCode(String phone, String code) {
		log.info("发送短信验证码: phone={}", phone);
		// 实际实现示例（阿里云短信）:
		// try {
		//     SendSmsRequest request = new SendSmsRequest();
		//     request.setPhoneNumbers(phone);
		//     request.setSignName("您的签名");
		//     request.setTemplateCode("SMS_XXXXX");
		//     request.setTemplateParam("{\"code\":\"" + code + "\"}");
		//     SendSmsResponse response = acsClient.getAcsResponse(request);
		//     return "OK".equals(response.getCode());
		// } catch (ClientException e) {
		//     log.error("发送短信失败: {}", e.getMessage());
		//     throw new ThirdPartyException("SMS-001", "短信发送失败");
		// }
		return true;
	}

	/**
	 * 发送通知短信
	 *
	 * @param phone   手机号
	 * @param content 短信内容
	 * @return 是否发送成功
	 */
	public boolean sendNotification(String phone, String content) {
		log.info("发送通知短信: phone={}", phone);
		// 实际实现
		return true;
	}

	/**
	 * 批量发送短信
	 *
	 * @param phones  手机号列表
	 * @param content 短信内容
	 * @return 发送成功数量
	 */
	public int batchSend(java.util.List<String> phones, String content) {
		log.info("批量发送短信: count={}", phones.size());
		// 实际实现
		return phones.size();
	}
}
