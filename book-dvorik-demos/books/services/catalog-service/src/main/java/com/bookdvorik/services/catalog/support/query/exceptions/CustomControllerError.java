package com.bookdvorik.services.catalog.support.query.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by I.Shadryn on 27.04.2017.
 */
public class CustomControllerError {
    private String typeError;
    private Object errors;

    private CustomControllerError() {
    }

    public CustomControllerError(String error) {
        this.typeError = error;
    }

    @JsonProperty("errors")
    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    @JsonProperty("logref")
    public String getTypeError() {
        return typeError;
    }
}
