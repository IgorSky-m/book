package com.bookdvorik.services.catalog.support.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.jackson.JacksonEncoder;

public class CustomJacksonEncoder extends JacksonEncoder {
    public CustomJacksonEncoder(ObjectMapper mapper) {
        super(mapper);
    }
}
