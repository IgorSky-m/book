package com.bookdvorik.infrastructure.gateway.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix="documentation.swagger")
public class SwaggerServicesConfig {
    private List<SwaggerService> services;

    public List<SwaggerService> getServices() {
        return services;
    }

    public void setServices(List<SwaggerService> services) {
        this.services = services;
    }

    public static class SwaggerService {
        private String name;
        private String url;
        private String version;

        public SwaggerService() {
        }

        public SwaggerService(String name, String url, String version) {
            this.name = name;
            this.url = url;
            this.version = version;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }
}
