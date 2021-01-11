package com.bookdvorik.infrastructure.admin.server;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.TimeZone;

@SpringBootApplication
@EnableAdminServer
@EnableDiscoveryClient
public class AdminServerApplication {

	@Value("${edir.timezone:UTC}")
	private String TIME_ZONE;


	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}

	@Bean
	public ServletContextListener listener() {
		return new ServletContextListener() {
			@Override
			public void contextInitialized(ServletContextEvent sce) {
				TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
			}

			@Override
			public void contextDestroyed(ServletContextEvent sce) {

			}
		};
	}
}
