package com.novardis.education.exceptions;

import com.novardis.education.dto.ExceptionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalException.class)
    public ResponseEntity<?> callInternalException(InternalException ex){
        ExceptionResponseDto exceptionResponseDto = new ExceptionResponseDto();
        exceptionResponseDto.setTimestamp(new Date().toString());
        exceptionResponseDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
        exceptionResponseDto.setMessage(ex.getMessage());
        return new ResponseEntity<>(exceptionResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
