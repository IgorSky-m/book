package com.bookdvorik.services.catalog.support.query.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I.Shadryn on 30.12.2015.
 */
public class ValidationErrorException extends RuntimeException {

    private List<StructuredError> exps;

    public ValidationErrorException() {
        super("error.validator.default");
    }

    public ValidationErrorException(StructuredError structuredEx) {
        super("error.validator.default");
        addError(structuredEx);
    }

    public ValidationErrorException(List<StructuredError> structuredExs) {
        super("error.validator.default");
        addErrors(structuredExs);
    }

    public ValidationErrorException(String message) {
        super(message);
    }

    public ValidationErrorException(String message, Throwable e) {
        super(message, e);
    }

    public List<StructuredError> getErrors() {
        if (exps == null) this.exps = new ArrayList<StructuredError>();
        return exps;
    }

    public void setErrors(List<StructuredError> ex) {
        this.exps = ex;
    }

    public void addError(StructuredError ex) {
        if (exps == null) this.exps = new ArrayList<StructuredError>();
        this.exps.add(ex);
    }

    public void addErrors(List<StructuredError> ex) {
        if (exps == null) this.exps = new ArrayList<StructuredError>();
        this.exps.addAll(ex);
    }

    @Override
    public String toString() {
        if (getErrors().size() > 0) {
            StringBuilder message = new StringBuilder();
            for (StructuredError error : getErrors()) {
                message.append(error.toString()).append("\n");
            }
            return message.toString();
        } else {
            return super.toString();
        }
    }

    public static ValidationErrorException addError(ValidationErrorException collector, String field, String errorMessage){
        if(collector == null)
            collector = new ValidationErrorException();

        collector.addError(new StructuredError(field, errorMessage));

        return collector;

    }

    public static ValidationErrorException addError(ValidationErrorException collector, ValidationErrorException newError){
        if(collector == null)
            collector = new ValidationErrorException();

        if(newError != null && newError.getErrors() != null)
            collector.addErrors(newError.getErrors());

        return collector;

    }
}
