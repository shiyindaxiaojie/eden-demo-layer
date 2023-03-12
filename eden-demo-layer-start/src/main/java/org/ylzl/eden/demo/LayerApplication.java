package org.ylzl.eden.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.ylzl.eden.spring.boot.bootstrap.SpringBootApplicationHelper;
import org.ylzl.eden.spring.framework.web.rest.annotation.EnableRestExceptionHandler;

/**
 * Spring Boot 引导类
 *
 * @author <a href="mailto:shiyindaxiaojie@gmail.com">gyl</a>
 * @since 2.4.13
 */
@EnableDiscoveryClient
@EnableRestExceptionHandler
@EnableTransactionManagement
@Slf4j
@SpringBootApplication
public class LayerApplication {

	/**
	 * 启动入口
	 *
	 * @param args 命令行参数
	 */
	public static void main(String[] args) {
		SpringBootApplicationHelper.run(LayerApplication.class, args, WebApplicationType.SERVLET);
	}
}
