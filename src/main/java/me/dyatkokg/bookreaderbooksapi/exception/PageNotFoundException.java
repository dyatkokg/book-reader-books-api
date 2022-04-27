package me.dyatkokg.bookreaderbooksapi.exception;

public class PageNotFoundException extends RuntimeException {

    public PageNotFoundException() {
        super("This page was not found");
    }

    public PageNotFoundException(String id) {
        super(String.format("Page with number %s was not found", id));
    }
}
