package com.bookdvorik.services.catalog.support.query.exceptions;

/**
 * Created by I.Shadryn on 27.04.2017.
 */
public class StructuredError {

    private String message;
    private String field;

    private StructuredError(){
    }

    public StructuredError(String field, String message) {
        this.message = message;
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }

}
