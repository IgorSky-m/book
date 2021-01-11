package com.bookdvorik.services.catalog.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bookdvorik.services.catalog.support.feign.CustomJacksonDecoder;
import com.bookdvorik.services.catalog.support.feign.CustomJacksonEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public CustomJacksonDecoder customJacksonDecoder(@Autowired @Qualifier("objectMapper") ObjectMapper mapper){
        return new CustomJacksonDecoder(mapper);
    }

    @Bean
    public CustomJacksonEncoder customJacksonEncoder(@Autowired @Qualifier("objectMapper") ObjectMapper mapper){
        return new CustomJacksonEncoder(mapper);
    }
}
