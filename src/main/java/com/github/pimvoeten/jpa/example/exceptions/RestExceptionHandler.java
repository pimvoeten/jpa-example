package com.github.pimvoeten.jpa.example.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler {
    private static final String UNEXPECTED_ERROR = "Exception.unexpected";
    private final MessageSource messageSource;

    @Autowired
    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorMessage> handleExceptions(Exception ex, Locale locale) {
//        String errorMessage = messageSource.getMessage(UNEXPECTED_ERROR, null, locale);
//        log.error("", ex);
//        return new ResponseEntity<>(new ErrorMessage(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorMessage> handleArgumentNotValidException(MethodArgumentNotValidException ex, Locale locale) {
//        BindingResult result = ex.getBindingResult();
//        List<String> errorMessages = result.getAllErrors()
//                .stream()
//                .map(objectError -> messageSource.getMessage(objectError, locale))
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(new ErrorMessage(errorMessages), HttpStatus.BAD_REQUEST);
//    }

}
