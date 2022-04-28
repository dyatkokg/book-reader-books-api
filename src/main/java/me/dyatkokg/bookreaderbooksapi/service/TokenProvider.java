package me.dyatkokg.bookreaderbooksapi.service;

public interface TokenProvider {

    void  validateToken(String token);

    String getSubject(String token);
}
