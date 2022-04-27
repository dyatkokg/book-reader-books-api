package me.dyatkokg.bookreaderbooksapi.exception;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException() {
        super("User is not authorized");
    }
}
