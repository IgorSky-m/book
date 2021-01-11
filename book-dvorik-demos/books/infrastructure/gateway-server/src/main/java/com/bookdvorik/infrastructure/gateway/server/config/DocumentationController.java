package com.bookdvorik.infrastructure.gateway.server.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {


    @Autowired
    private SwaggerServicesConfig config;

    @Override
    public List get() {
        List<SwaggerResource>  swaggerResources = new ArrayList<>();
        for(SwaggerServicesConfig.SwaggerService swaggerService : config.getServices()){
            swaggerResources.add(swaggerResource(swaggerService.getName(), swaggerService.getUrl(), swaggerService.getVersion()));
        }

        return swaggerResources;
    }


    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
