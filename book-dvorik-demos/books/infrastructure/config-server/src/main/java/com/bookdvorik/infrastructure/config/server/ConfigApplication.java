package com.bookdvorik.infrastructure.config.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.TimeZone;

@SpringBootApplication
@EnableConfigServer
public class ConfigApplication {

	@Value("${dvorik.timezone:UTC}")
	private String TIME_ZONE;

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}


	@Bean
	public ServletContextListener executorListener() {
		return new ServletContextListener(){

			@Override
			public void contextInitialized(ServletContextEvent servletContextEvent) {
				TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
			}

			@Override
			public void contextDestroyed(ServletContextEvent servletContextEvent) {

			}
		};
	}

}
