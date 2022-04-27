package me.dyatkokg.bookreaderbooksapi.exception;

public class BookNotFoundException extends RuntimeException{

    public BookNotFoundException() {
        super("Book was not found");
    }

    public BookNotFoundException(String id) {
        super(String.format("Book with id %s was not found", id));
    }
}
