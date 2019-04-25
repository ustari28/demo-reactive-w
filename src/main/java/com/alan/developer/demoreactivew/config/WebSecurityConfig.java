package com.alan.developer.demoreactivew.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
        // security base on token jwt
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
/**
 @Bean CorsConfigurationSource corsConfigurationSource() {
 CorsConfiguration configuration = new CorsConfiguration();
 configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080","http://127.0.0.1:8082"));
 configuration.setAllowedMethods(Arrays.asList("GET","POST"));
 configuration.setAllowCredentials(true);
 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 source.registerCorsConfiguration("/sockejs/**", configuration);
 return source;
 }*/
}
