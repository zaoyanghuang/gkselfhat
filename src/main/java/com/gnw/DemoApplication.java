package com.gnw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)//多表回滚
@EnableAsync //开启异步任务
@SpringBootApplication
@EnableWebSecurity
public class DemoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		// 注册自定义事件监听器
		//context.addApplicationListener(new SendDataListener());
		// 启动上下文
		//context.refresh();
		// 发布事件，事件源为Context
		//context.publishEvent(new SendDataEvent(context));
		// 结束
		//context.close();

	}
}
