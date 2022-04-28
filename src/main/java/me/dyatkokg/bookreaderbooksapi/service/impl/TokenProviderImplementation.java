package me.dyatkokg.bookreaderbooksapi.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProviderImplementation implements TokenProvider {

    private static final String SIGNATURE = "x7HktL7QmQwR7Ii98AU3eACZ70arilFhCIgz54IYEOMCL0UIl7MWCBXeEzyQ6syQmWXWJACQzOWkrWd2DBnibCbENjd6fV7m073e27KkERwBSrmSX0hv28P8rP6LSXY1xs24Fr6w3Se7amJ9fRSPfIL0cXdujrtUUPjPp8M1M6SKJezzOMkSKZtihTKRscQrBjcTPJWs";

    public void validateToken(String token) {
        DecodedJWT verified = JWT.require(Algorithm.HMAC256(SIGNATURE))
                .withIssuer("book-app").build().verify(token);
    }

    public String getSubject(String token) {
        return JWT.decode(token.substring(7)).getSubject();
    }

}


