package me.dyatkokg.bookreaderbooksapi.config;

import lombok.RequiredArgsConstructor;
import me.dyatkokg.bookreaderbooksapi.config.filter.CheckAccessFilter;
import me.dyatkokg.bookreaderbooksapi.config.filter.TokenFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    private final TokenFilter tokenFilter;
    private final CheckAccessFilter accessFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .addFilterBefore(tokenFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(accessFilter,TokenFilter.class)
                .authorizeRequests()
                .antMatchers("/books/**").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
