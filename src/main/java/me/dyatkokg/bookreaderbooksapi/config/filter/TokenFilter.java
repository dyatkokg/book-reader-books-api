package me.dyatkokg.bookreaderbooksapi.config.filter;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = FilterUtils.getTokenFromRequest(request).substring(7);

            tokenProvider.validateToken(token);
            Authentication authentication
                    = new UsernamePasswordAuthenticationToken(token, null, Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            e.printStackTrace();
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }

}
