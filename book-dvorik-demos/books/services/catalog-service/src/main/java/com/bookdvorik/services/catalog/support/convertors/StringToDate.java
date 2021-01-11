package com.bookdvorik.services.catalog.support.convertors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by v.hovanski on 25.08.15.
 */
@Component
public class StringToDate implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        if(s == null)
            return null;
        try{
            return new Date(Long.parseLong(s));
        } catch (Throwable t){
            throw new IllegalArgumentException(t);
        }
    }
}
