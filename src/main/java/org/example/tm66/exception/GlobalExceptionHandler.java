package org.example.tm66.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAllOtherExceptions(Exception ex,
                                                      HttpServletRequest request
    ) {
        log.error("Ошибка", ex);
        return ResponseEntity.internalServerError().body("Что-то пошло не так тут: " + request.getServletPath());
    }

}
