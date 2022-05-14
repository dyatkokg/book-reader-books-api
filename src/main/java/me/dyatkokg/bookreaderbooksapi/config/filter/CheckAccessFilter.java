package me.dyatkokg.bookreaderbooksapi.config.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.dyatkokg.bookreaderbooksapi.entity.Book;
import me.dyatkokg.bookreaderbooksapi.exception.BookNotFoundException;
import me.dyatkokg.bookreaderbooksapi.exception.NotAuthorizedException;
import me.dyatkokg.bookreaderbooksapi.repository.BookRepository;
import me.dyatkokg.bookreaderbooksapi.service.TokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        String[] splitURI = requestURI.split("/");
        String id = splitURI[splitURI.length - 1];
        if (!(id.equals("all") || id.equals("upload") || id.equals("grant"))) {
            try {
                Book byId = repository.findById(id).orElseThrow(BookNotFoundException::new);
                if (!byId.getHasAccess().contains(providerSubject)) {
                    throw new NotAuthorizedException();
                }
            } catch (BookNotFoundException | NotAuthorizedException e) {
                e.printStackTrace();
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(request, response);
    }
}