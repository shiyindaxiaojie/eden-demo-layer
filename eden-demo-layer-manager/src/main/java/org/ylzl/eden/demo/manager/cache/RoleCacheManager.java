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
import org.ylzl.eden.demo.dao.database.dataobject.PermissionDO;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 角色权限缓存管理
 * <p>《阿里巴巴Java开发手册》Manager 层：对缓存的封装</p>
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.14
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class RoleCacheManager {

	private static final String ROLE_PERMISSIONS_KEY_PREFIX = "role:permissions:";
	private static final String USER_PERMISSIONS_KEY_PREFIX = "user:permissions:";
	private static final long CACHE_EXPIRE_HOURS = 2;

	private final RedisTemplate<String, Object> redisTemplate;

	/**
	 * 获取角色权限缓存
	 */
	@SuppressWarnings("unchecked")
	public List<PermissionDO> getRolePermissions(Long roleId) {
		String key = ROLE_PERMISSIONS_KEY_PREFIX + roleId;
		Object cached = redisTemplate.opsForValue().get(key);
		if (cached != null) {
			log.debug("从缓存获取角色权限: roleId={}", roleId);
			return (List<PermissionDO>) cached;
		}
		return null;
	}

	/**
	 * 设置角色权限缓存
	 */
	public void setRolePermissions(Long roleId, List<PermissionDO> permissions) {
		String key = ROLE_PERMISSIONS_KEY_PREFIX + roleId;
		redisTemplate.opsForValue().set(key, permissions, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
		log.debug("设置角色权限缓存: roleId={}", roleId);
	}

	/**
	 * 删除角色权限缓存
	 */
	public void evictRolePermissions(Long roleId) {
		String key = ROLE_PERMISSIONS_KEY_PREFIX + roleId;
		redisTemplate.delete(key);
		log.debug("删除角色权限缓存: roleId={}", roleId);
	}

	/**
	 * 获取用户权限缓存
	 */
	@SuppressWarnings("unchecked")
	public List<PermissionDO> getUserPermissions(Long userId) {
		String key = USER_PERMISSIONS_KEY_PREFIX + userId;
		Object cached = redisTemplate.opsForValue().get(key);
		if (cached != null) {
			log.debug("从缓存获取用户权限: userId={}", userId);
			return (List<PermissionDO>) cached;
		}
		return null;
	}

	/**
	 * 设置用户权限缓存
	 */
	public void setUserPermissions(Long userId, List<PermissionDO> permissions) {
		String key = USER_PERMISSIONS_KEY_PREFIX + userId;
		redisTemplate.opsForValue().set(key, permissions, CACHE_EXPIRE_HOURS, TimeUnit.HOURS);
		log.debug("设置用户权限缓存: userId={}", userId);
	}

	/**
	 * 删除用户权限缓存
	 */
	public void evictUserPermissions(Long userId) {
		String key = USER_PERMISSIONS_KEY_PREFIX + userId;
		redisTemplate.delete(key);
		log.debug("删除用户权限缓存: userId={}", userId);
	}
}
