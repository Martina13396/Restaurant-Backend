package com.example.restaurant.config;

import com.example.restaurant.controller.vm.ExceptionResponse;
import com.example.restaurant.helper.BundleMessage;
import com.example.restaurant.service.bundlemessage.BundleMessageService;
import com.example.restaurant.service.dto.ExceptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionConfig {

    private BundleMessageService bundleMessageService;
    @Autowired
    public ExceptionConfig(BundleMessageService bundleMessageService) {
        this.bundleMessageService = bundleMessageService;
    }

    @ExceptionHandler(Exception.class)
public ResponseEntity <ExceptionDto> handleException(Exception exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDto(
                        bundleMessageService.getBundleMessageArEn(exception.getMessage())
                )
        );
}

    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity <ExceptionDto>handleException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldErrors().get(0);
        String error = fieldError.getDefaultMessage();
        BundleMessage bundleMessage = bundleMessageService.getBundleMessageArEn(error);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ExceptionDto(bundleMessage));

}
}