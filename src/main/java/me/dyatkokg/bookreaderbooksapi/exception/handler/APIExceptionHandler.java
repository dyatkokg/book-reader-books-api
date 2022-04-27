package me.dyatkokg.bookreaderbooksapi.exception.handler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.exception.IncorrectFileTypeException;
import me.dyatkokg.bookreaderbooksapi.exception.NotAuthorizedException;
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

    @ExceptionHandler({NotAuthorizedException.class})
    public ResponseEntity<Object> handleNotAuthorized(NotAuthorizedException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authorized");
    }

    @ExceptionHandler({TokenExpiredException.class})
    public ResponseEntity<Object> handleTokenNotValid(TokenExpiredException exception) {
        log.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token has expired!");
    }
}
