package com.bookdvorik.services.catalog.support.advices;

import com.bookdvorik.services.catalog.support.query.exceptions.CustomControllerError;
import com.bookdvorik.services.catalog.support.query.exceptions.StructuredError;
import com.bookdvorik.services.catalog.support.query.exceptions.ValidationErrorException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import net.nvcm.sugar.core.exceptions.EssenceNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by I.Shadryn on 15.11.2016.
 * Класс призванный отлавливать все ошибки которые не попали в try catch и выдавать человеческие ошибки, а не стектрэйс
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(GlobalControllerExceptionHandler.class);

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public VndErrors illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return new VndErrors("error", ex.getMessage() == null ? messageSource.getMessage("global.controller.exception.handler.illegal.argument.exception", null, LocaleContextHolder.getLocale()) :  messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(EssenceNotFound.class)
    public VndErrors notFoundExceptionHandler(EssenceNotFound ex) {
        return new VndErrors("error",  messageSource.getMessage(ex.getMessage() == null ? "global.controller.exception.handler.not.found.exception.handler" : ex.getMessage(), null, LocaleContextHolder.getLocale()));
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(UnsupportedOperationException.class)
    public VndErrors notFoundExceptionHandler(UnsupportedOperationException ex) {
        return new VndErrors("error",  ex.getMessage() == null ? messageSource.getMessage("global.controller.exception.handler.not.found.exception.handler.not.supported", null, LocaleContextHolder.getLocale()) : ex.getMessage());
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public VndErrors unknownExceptionHandler(Exception ex) {
        logger.error( messageSource.getMessage("global.controller.exception.handler.unknown.exception.handler", null, Locale.getDefault()), ex);
        return new VndErrors("error",  ex.getMessage() == null ? messageSource.getMessage("global.controller.exception.handler.unknown.exception.handler", null, LocaleContextHolder.getLocale()) : ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        Object error;
        if (ex.getCause() instanceof JsonEOFException) {
            error = this.parseJsonEOFException((JsonEOFException) ex.getCause());
        }
        else if (ex.getCause() instanceof InvalidFormatException) {
            error = this.parseInvalidFormatException((InvalidFormatException) ex.getCause());
        }
        else if (ex.getCause() instanceof JsonParseException){
            error = this.parseJsonParseException((JsonParseException) ex.getCause());
        }
        else if( ex.getCause() instanceof JsonMappingException){
            error = this.parseJsonMappingException((JsonMappingException)ex.getCause());
        }
        else {
            error = new VndErrors("error", ex.getMessage());
        }
        return error;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationErrorException.class)
    public Object validationErrorExceptionExceptionHandler(ValidationErrorException ex) {
        logger.debug("Ошибка валидации сущности", ex);
        if(ex.getErrors().size() > 0){
            CustomControllerError controllerError = new CustomControllerError("structured_error");
            controllerError.setErrors(ex.getErrors());
            return controllerError;
        }
        else
        {
            return new VndErrors("error", ex.getMessage());
        }
    }

    /**
     * Метод предназначен для парсинга эксепшена связанного с
     * некорректным значением не входящим в енум
     * @param invalidFormatException - эксепшен
     * @return структурированная ошибка
     */
    private StructuredError parseInvalidFormatException(InvalidFormatException invalidFormatException) {
        List<Object> errorArgs = Stream.of(invalidFormatException.getValue(), invalidFormatException.getPath().get(0).getFieldName()).collect(Collectors.toList());
        if (invalidFormatException.getTargetType().getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) invalidFormatException.getTargetType().getGenericSuperclass();
            if ("java.lang.Enum".equals(type.getRawType().getTypeName())) {
                errorArgs.add(Arrays.stream(invalidFormatException.getTargetType().getEnumConstants())
                        .map(Objects::toString)
                        .collect(Collectors.joining()));
            }
        }
        String fieldName = invalidFormatException.getPath().get(0).getFieldName();
        return new StructuredError(fieldName, messageSource.getMessage("errors.system.custom-error-3", null, LocaleContextHolder.getLocale())); //TODO поправить сообщение
    }

    /**
     * Метод предназначен для парсинга эксепшена связанного с
     * потерянной запятой в json, либо когда строка не является json
     * @param jsonParseException - эксепшен
     * @return структурированная ошибка
     */
    private StructuredError parseJsonParseException(JsonParseException jsonParseException) {
        String errorMessage = messageSource.getMessage("errors.system.custom-error-3", null, LocaleContextHolder.getLocale()); //TODO поправить сообщение
        String field = jsonParseException.getProcessor().getParsingContext().getCurrentName();
        return new StructuredError(field, errorMessage);
    }

    /**
     * Метод предназначен для парсинга эксепшена связанного с
     * некорректным значением в числовом типе данных, либо строкой, которая не имеет "{ }"
     * @param jsonMappingException - эксепшен
     * @return структурированная ошибка
     */
    private Object parseJsonMappingException(JsonMappingException jsonMappingException) {
        //TODO поправить сообщение
//        Object error;
//        if (jsonMappingException.getCause() instanceof NumberFormatException) {
//            String field = jsonMappingException.getPath().get(0).getFieldName();
//            error = new StructuredError(field, this.messageSource.getMessage("errors.system.custom-error-6",
//                    new Object[] {jsonMappingException.getOriginalMessage().replace("For input string:", ""), field}).getMessage());
//        } else if (jsonMappingException instanceof MismatchedInputException) {
//            error = new StructuredError("error", this.messageSource.getMessage("errors.system.custom-error-4", null).getMessage());
//        } else {
//            error = new VndErrors("error", jsonMappingException.getMessage());
//        }
        return null;
    }

    /**
     * Метод предназначен для парсинга эксепшена связанного с
     * незакрытым элементом в json, который не имеет "}"
     * @param jsonEOFException - эксепшен
     * @return структурированная ошибка
     */
    private StructuredError parseJsonEOFException(JsonEOFException jsonEOFException) {
        //TODO поправить сообщение
//        String errorMessage = this.structuredMessageSource.getMessage("errors.system.custom-error-4", null).getMessage();
//        String field = jsonEOFException.getProcessor().getParsingContext().getCurrentName();
//        if (jsonEOFException.getOriginalMessage().contains("expected close marker")) {
//            errorMessage = this.structuredMessageSource.getMessage("errors.system.custom-error-7", new Object[] {field}).getMessage();
//        }
//        return new StructuredError(field, errorMessage);

        return null;
    }
}
