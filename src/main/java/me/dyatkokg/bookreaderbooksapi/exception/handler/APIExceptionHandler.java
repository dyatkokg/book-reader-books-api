package me.dyatkokg.bookreaderbooksapi.exception.handler;

import lombok.extern.slf4j.Slf4j;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.exception.IncorrectFileTypeException;
import me.dyatkokg.bookreaderbooksapi.exception.PageNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler({IncorrectFileTypeException.class})
    public ResponseEntity<Object> handleFileType(IncorrectFileTypeException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Data being loaded is of the wrong type. Try again!");
    }

    @ExceptionHandler({BookNotFoundException.class})
    public ResponseEntity<Object> handleBookNotFound(BookNotFoundException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT.ordinal()).body("Book was not found. Try again!");
    }

    @ExceptionHandler({PageNotFoundException.class})
    public ResponseEntity<Object> handlePageNotFound(PageNotFoundException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Page was not found. Try again!");
    }
}
