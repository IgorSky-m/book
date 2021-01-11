package com.bookdvorik.services.catalog.support.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by I.Shadryn on 14.11.2016.
 */
public class JsonObjectMapperFactory {

    private static class SingletonObjectMapper{
        static final ObjectMapper instance = new ObjectMapper();
    }

    /**
     * Получить инстанс Jackson маппера
     * @return экземпляр маппера
     */
    public static ObjectMapper getInstance() {
        return SingletonObjectMapper.instance;
    }
}
