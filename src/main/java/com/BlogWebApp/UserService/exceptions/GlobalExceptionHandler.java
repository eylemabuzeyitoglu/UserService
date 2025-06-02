package com.BlogWebApp.UserService.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex){
        logger.warn("Kullanıcı bulunamadı hatası: {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<String> handleUserCreationException(UserCreationException ex){
        logger.warn("Kullanıcı yaratırken hata oluştu : {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUpdateException.class)
    public ResponseEntity<String> handlerUserUpdateException(UserUpdateException ex){
        logger.warn("Kullanıcı güncellenirken hata oluştu : {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex){
        logger.error("Beklenmedik bir hata oluştu: {}", ex.getMessage(), ex);
        return new ResponseEntity<>("Bir hata oluştu: " + ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
