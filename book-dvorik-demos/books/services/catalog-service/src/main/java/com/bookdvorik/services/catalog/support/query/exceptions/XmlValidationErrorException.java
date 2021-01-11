package com.bookdvorik.services.catalog.support.query.exceptions;

/**
 * Класс ошибок валидации XML
 */
public class XmlValidationErrorException extends ValidationErrorException {

    public XmlValidationErrorException(String message, Throwable e) {
        super(message, e);
    }

    public XmlValidationErrorException(String message) {
        super(message);
    }
}
