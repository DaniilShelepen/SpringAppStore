package com.daniil.courses.exceptions;

import com.daniil.courses.repositories.ManagerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler({StoreItemIsNotFound.class})
    public ResponseEntity<Object> handlerStoreItemIsNotFound(StoreItemIsNotFound exception) {
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                exception,
                serverError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, serverError);
    }

    @ExceptionHandler({AddressIsNotFound.class})
    public ResponseEntity<Object> handlerAddressIsNotFound(AddressIsNotFound exception) {
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                exception,
                serverError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException, serverError);
    }

    @ExceptionHandler({UserNotFound.class})
    public ResponseEntity<Object> handlerUserNotFound(UserNotFound exception){
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                exception,
                serverError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,serverError);
    }

    @ExceptionHandler({UserAlreadyExist.class})
    public ResponseEntity<Object> handlerUserAlreadyExist(UserAlreadyExist exception){
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                exception,
                serverError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,serverError);
    }

    @ExceptionHandler({ManagerNotFound.class})
    public ResponseEntity<Object> handlerManagerNotFound(ManagerNotFound exception){
        HttpStatus serverError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiException apiException = new ApiException(
                exception.getMessage(),
                exception,
                serverError,
                ZonedDateTime.now(ZoneId.of("Z"))
        );
        return new ResponseEntity<>(apiException,serverError);
    }
}
