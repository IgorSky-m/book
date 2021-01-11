package com.bookdvorik.services.catalog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class CatalogServiceApplication {
	private static final Logger logger = LoggerFactory.getLogger(CatalogServiceApplication.class);

	@Value("${dvorik.timezone:UTC}")
	private String TIME_ZONE;


	public static void main(String[] args) {
		try{
			SpringApplication.run(CatalogServiceApplication.class, args);
		} catch (Exception e){
			logger.error("Error of START APP", e);
		}
	}

	@Bean
	public ServletContextListener executorListener() {
		return new ServletContextListener(){

			@Override
			public void contextInitialized(ServletContextEvent servletContextEvent) {
				TimeZone.setDefault(TimeZone.getTimeZone(TIME_ZONE));
				Locale.setDefault(new Locale("ru"));
			}

			@Override
			public void contextDestroyed(ServletContextEvent servletContextEvent) {

			}
		};
	}

}
