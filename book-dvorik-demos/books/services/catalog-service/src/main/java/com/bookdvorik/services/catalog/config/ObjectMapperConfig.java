package com.bookdvorik.services.catalog.config;

import com.bookdvorik.services.catalog.support.jackson.serializers.DateDeserializer;
import com.bookdvorik.services.catalog.support.jackson.serializers.DateSerializer;
import com.bookdvorik.services.catalog.support.jackson.serializers.LocalDateDeserializer;
import com.bookdvorik.services.catalog.support.jackson.serializers.LocalDateSerializer;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.time.LocalDate;
import java.util.Date;

@Configuration
public class ObjectMapperConfig {

    /**
     * Получение синглтона маппера
     * @return синглтон маппера
     */
    @Bean
    public ObjectMapper objectMapper(){
        return JsonObjectMapperFactory.getInstance();
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder(ObjectMapper objectMapper){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        builder.serializerByType(Date.class, new DateSerializer());
        builder.serializerByType(LocalDate.class, new LocalDateSerializer());
        builder.deserializerByType(Date.class, new DateDeserializer());
        builder.deserializerByType(LocalDate.class, new LocalDateDeserializer());
        builder.featuresToEnable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        builder.configure(objectMapper);
        return builder;
    }

    private static class JsonObjectMapperFactory {

        private static class SingletonObjectMapper{
            static final ObjectMapper instance = new ObjectMapper();
        }

        /**
         * Получить инстанс Jackson маппера
         * @return
         */
        public static ObjectMapper getInstance() {
            return SingletonObjectMapper.instance;
        }
    }

}
