package com.Power_gym.backend.exception;

import com.Power_gym.backend.DTO.common.ResponseMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;


@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger ErrorLog = LogManager.getLogger("powerGymErrorLogs");

    @ExceptionHandler(value = LogException.class)
    public void logException(LogException ex){
        ErrorResponse errorResponseDto = new ErrorResponse();
        errorResponseDto.setDateTime(new Date());
        errorResponseDto.setStatusCode(200);
        errorResponseDto.setMessage(ex.getLocalizedMessage());
        ErrorLog.error(errorResponseDto + " : " + ex);
    }

    @ExceptionHandler(value = CustomException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleException(CustomException ex){
        ErrorResponse errorResponseDto = new ErrorResponse();
        errorResponseDto.setDateTime(new Date());
        errorResponseDto.setStatusCode(400);
        errorResponseDto.setMessage(ex.getLocalizedMessage());
        ErrorLog.error(errorResponseDto + " : " + ex);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.BAD_REQUEST.value(), errorResponseDto.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponseDto = new ErrorResponse();
        errorResponseDto.setDateTime(new Date());
        errorResponseDto.setStatusCode(500);
        errorResponseDto.setMessage("something went wrong !. please contact system administrator.");
        ErrorLog.error(errorResponseDto + " : " + ex);
        return new ResponseEntity<>(new ResponseMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorResponseDto.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
