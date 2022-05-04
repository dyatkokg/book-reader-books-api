package me.dyatkokg.bookreaderbooksapi.config.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Slf4j
public class FilterUtils {

    public static String getTokenFromRequest(HttpServletRequest request){
        String authorization = request.getHeader("Authorization");
        return Objects.requireNonNull(authorization);
    }
}
