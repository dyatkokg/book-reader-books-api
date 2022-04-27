package me.dyatkokg.bookreaderbooksapi.service;

public interface TokenProvider {

    boolean validateToken(String token);
}
