package me.dyatkokg.bookreaderbooksapi.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProviderImplementation implements TokenProvider {

    @Value("${token.signature}")
    private String signature;

    public void validateToken(String token) {
        DecodedJWT verified = JWT.require(Algorithm.HMAC256(signature))
                .withIssuer("book-app").build().verify(token);
    }

    public String getSubject(String token) {
        return JWT.decode(token).getSubject();
    }

}


