package com.bookdvorik.services.catalog.support.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.jackson.JacksonDecoder;

public class CustomJacksonDecoder extends JacksonDecoder {
    public CustomJacksonDecoder(ObjectMapper mapper) {
        super(mapper);
    }
}
