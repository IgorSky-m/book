package com.bookdvorik.infrastructure.discovery.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.TimeZone;


@SpringBootApplication
@EnableEurekaServer
public class DiscoveryApplication {

    @Value("${dvorik.timezone:UTC}")
    private String TIME_ZONE;



	public static void main(String[] args) {
		SpringApplication.run(DiscoveryApplication.class, args);
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
