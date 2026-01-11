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

package org.ylzl.eden.demo.manager.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.ylzl.eden.demo.dao.database.dataobject.UserDO;

import java.util.concurrent.TimeUnit;

/**
 * 用户缓存管理
 * <p>《阿里巴巴Java开发手册》Manager 层：对缓存的封装，预处理返回结果及转化异常信息</p>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class UserCacheManager {

	private static final String USER_CACHE_KEY_PREFIX = "user:";
	private static final long USER_CACHE_EXPIRE_HOURS = 24;

	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * 获取用户缓存
	 *
	 * @param userId 用户ID
	 * @return 用户信息
	 */
	public UserDO getUser(Long userId) {
		String key = buildUserKey(userId);
		Object cached = redisTemplate.opsForValue().get(key);
		if (cached != null) {
			log.debug("从缓存获取用户信息: userId={}", userId);
			return (UserDO) cached;
		}
		return null;
	}

	/**
	 * 设置用户缓存
	 *
	 * @param user 用户信息
	 */
	public void setUser(UserDO user) {
		if (user == null || user.getId() == null) {
			return;
		}
		String key = buildUserKey(user.getId());
		redisTemplate.opsForValue().set(key, user, USER_CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
		log.debug("设置用户缓存: userId={}", user.getId());
	}

	/**
	 * 删除用户缓存
	 *
	 * @param userId 用户ID
	 */
	public void evictUser(Long userId) {
		String key = buildUserKey(userId);
		redisTemplate.delete(key);
		log.debug("删除用户缓存: userId={}", userId);
	}

	/**
	 * 判断用户缓存是否存在
	 *
	 * @param userId 用户ID
	 * @return 是否存在
	 */
	public boolean hasUser(Long userId) {
		String key = buildUserKey(userId);
		return Boolean.TRUE.equals(redisTemplate.hasKey(key));
	}

	/**
	 * 构建用户缓存Key
	 *
	 * @param userId 用户ID
	 * @return 缓存Key
	 */
	private String buildUserKey(Long userId) {
		return USER_CACHE_KEY_PREFIX + userId;
	}
}
