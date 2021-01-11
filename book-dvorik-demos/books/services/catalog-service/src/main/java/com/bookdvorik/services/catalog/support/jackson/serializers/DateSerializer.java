package com.bookdvorik.services.catalog.support.jackson.serializers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.Date;

/**
 * Created by v.hovanski on 16.07.15.
 */
public class DateSerializer extends StdSerializer<Date> {

    public DateSerializer() { super(Date.class); }
    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeObject(value == null ? null : value.getTime());
    }
}
