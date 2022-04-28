package me.dyatkokg.bookreaderbooksapi.exception;

import org.springframework.security.core.AuthenticationException;

public class NotAuthorizedException extends AuthenticationException {

    public NotAuthorizedException() {
        super("User is not authorized");
    }
}
