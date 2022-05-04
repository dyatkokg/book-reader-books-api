package me.dyatkokg.bookreaderbooksapi.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.exception.NotAuthorizedException;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckAccessFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final BookRepository repository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenFromRequest = FilterUtils.getTokenFromRequest(request);
        String providerSubject = tokenProvider.getSubject(tokenFromRequest);
        String requestURI = request.getRequestURI();
        String id = requestURI.substring(7);
        if (!(id.equals("all") || id.equals("update"))) {
            Book byId = repository.findById(id).orElseThrow();
            if (providerSubject.equals(byId.getOwner())) {
                filterChain.doFilter(request, response);
            } else throw new NotAuthorizedException();
        }
        filterChain.doFilter(request, response);
    }
}