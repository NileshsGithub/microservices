package com.example.csv_reader.exception;

import org.hibernate.StaleObjectStateException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StaleObjectStateException.class)
    public ResponseEntity<ErrorResponse> handleStaleException(StaleObjectStateException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Data conflict: " + exception.getMessage(),
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "File Handling Error: " + exception.getMessage(),
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFileNotFoundException(FileNotFoundException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "File Not Found " + exception.getMessage(),
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchFileException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchFileException(NoSuchFileException exception){
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Invalid File Path" + exception.getMessage(),
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unexpected error occurred."+ exception.getMessage(),
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}