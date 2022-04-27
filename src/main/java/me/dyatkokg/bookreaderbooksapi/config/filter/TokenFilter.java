package me.dyatkokg.bookreaderbooksapi.config.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.google.common.net.HttpHeaders;
import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.exception.NotAuthorizedException;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static org.apache.logging.log4j.util.Strings.isEmpty;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith("Bearer ")) {
          throw new NotAuthorizedException();
        }
        final String token = header.split(" ")[1].trim();
        if (!tokenProvider.validateToken(token)) {
         throw new JWTVerificationException("Token is not valid!");
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    //    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String rawToken = request.getHeader("Authorization");
//        String token = rawToken.substring(7);
//
//        if (tokenProvider.validateToken(token)) {
//            Authentication authentication
//                    = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } else {
//            filterChain.doFilter(request, response);
//        }
//    }
}
