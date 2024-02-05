package com.ing.mortgagecheck.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static com.ing.mortgagecheck.constants.Constants.*;


/**
 * Security Configuration to disable Cross origin forgery
 */
@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.csrf(csrfConfigurer ->
                csrfConfigurer.ignoringRequestMatchers(mvcMatcherBuilder.pattern(API_URL_GET),mvcMatcherBuilder.pattern(API_URL_POST),
                        PathRequest.toH2Console()));
        http.csrf(csrf->csrf.disable());

        http.headers(headersConfigurer ->
                headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers(new AntPathRequestMatcher(API_URL_GET, HttpMethod.GET.toString())).hasAnyRole(ADMIN)
                        .requestMatchers(new AntPathRequestMatcher(API_URL_POST, HttpMethod.POST.toString())).hasAnyRole(ADMIN)
                        .requestMatchers(PathRequest.toH2Console()).authenticated()
                        .requestMatchers(mvcMatcherBuilder.pattern(ACTUATOR)).hasAnyRole(SUPER_USER)
                        .anyRequest().permitAll()
        );

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}