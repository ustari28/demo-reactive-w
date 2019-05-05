package com.alan.developer.demoreactivew.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * private JWTAuthorizationFilter jwtAuthorizationFilter;
     * <p>
     * public WebSecurityConfig(JWTAuthorizationFilter jwtAuthorizationFilter) {
     * super();
     * this.jwtAuthorizationFilter = jwtAuthorizationFilter;
     * }
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //http.csrf().disable().authorizeRequests().antMatchers("/**").permitAll();
        // security base on token jwt

        http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/security/v1/token").permitAll()
                //.antMatchers(HttpMethod.GET, "/error").permitAll()
                .anyRequest().authenticated()
        .and()
                .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
        //http.addFilterAfter(jwtAuthorizationFilter, FilterChainProxy.class);
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
