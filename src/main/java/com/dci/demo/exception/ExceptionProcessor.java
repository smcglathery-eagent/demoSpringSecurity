package com.dci.demo.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@ControllerAdvice
public class ExceptionProcessor {

    private final static Logger log = LogManager.getLogger(ExceptionProcessor.class);

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo resourceNotFoundExceptionHandler(HttpServletRequest request, ResourceNotFoundException resourceNotFoundException) {

        String messageId = "error.global.invalidpath";
        String exceptionMessage = resourceNotFoundException.getMessage();
        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorInfo constraintViolationException(HttpServletRequest request, ConstraintViolationException constraintViolationException) {

        String messageId = "error.resttemplate.4xx";

        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        StringBuilder violationMessage = new StringBuilder();
        for (ConstraintViolation violation : violations) {
            violationMessage.append(violation.getMessage());
        }

        return generateErrorInfo(request, messageId, violationMessage.toString());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorInfo methodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException methodArgumentNotValidException) {

        String messageId = "error.resttemplate.4xx";

        StringBuilder errorMessages = new StringBuilder();

        List<ObjectError> errors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        errors.forEach(error -> {
            if (errorMessages.length() > 1) {
                errorMessages.append("; " + error.getDefaultMessage());
            } else {
                errorMessages.append(error.getDefaultMessage());
            }
        });

        return generateErrorInfo(request, messageId, errorMessages.toString());
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorInfo httpMessageNotReadableException(HttpServletRequest request, HttpMessageNotReadableException httpMessageNotReadableException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = httpMessageNotReadableException.getMessage();
        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(JsonMappingException.class)
    public ErrorInfo jsonMappingException(HttpServletRequest request, JsonMappingException jsonMappingException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = jsonMappingException.getMessage();
        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorInfo parseException(HttpServletRequest request, ParseException parseException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = parseException.getMessage();
        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ErrorInfo UnrecognizedPropertyException(HttpServletRequest request, UnrecognizedPropertyException unrecognizedPropertyException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = unrecognizedPropertyException.getMessage();
        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorInfo usernameNotFoundException(HttpServletRequest request, UsernameNotFoundException usernameNotFoundException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = usernameNotFoundException.getMessage();

        log.debug(usernameNotFoundException.getMessage());

        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorInfo disabledException(HttpServletRequest request, DisabledException disabledException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = disabledException.getMessage();

        log.debug(disabledException.getMessage());

        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ErrorInfo badCredentialsException(HttpServletRequest request, BadCredentialsException badCredentialsException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = badCredentialsException.getMessage();

        log.debug(badCredentialsException.getMessage());

        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo emptyResultDataAccessException(HttpServletRequest request, EmptyResultDataAccessException emptyResultDataAccessException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = emptyResultDataAccessException.getMessage();

        log.debug(emptyResultDataAccessException.getMessage());

        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorInfo multipartException(HttpServletRequest request, MultipartException multipartException) {

        String messageId = "error.resttemplate.4xx";
        String exceptionMessage = multipartException.getMessage();

        log.debug(multipartException.getMessage());

        return generateErrorInfo(request, messageId, exceptionMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorInfo genericException(HttpServletRequest request, Exception exception) {

        String messageId = "error.global.generic";
        String exceptionMessage = exception.getMessage();
        exception.printStackTrace();
        return generateErrorInfo(request, messageId, exceptionMessage);
    }


    private ErrorInfo generateErrorInfo(HttpServletRequest request, String errorMessageId, String exceptionMessage) {

        String errorURL = request.getRequestURL().toString();
        Locale locale = LocaleContextHolder.getLocale();

        String errorMessage = null;

        try {
            errorMessage = messageSource.getMessage(errorMessageId, null, locale);
        } catch (NoSuchMessageException e) {
            e.printStackTrace();
        }

        return new ErrorInfo(errorURL, errorMessage, exceptionMessage);
    }
}