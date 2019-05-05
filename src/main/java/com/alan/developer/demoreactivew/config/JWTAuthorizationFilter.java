package com.alan.developer.demoreactivew.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.java.Log;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log
//@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";
    private final String SECRET = "mysecret";
    private final OrRequestMatcher matcher;

    public JWTAuthorizationFilter() {
        matcher = new OrRequestMatcher(new AntPathRequestMatcher("/**"));
    }

    public JWTAuthorizationFilter(final String paths) {
        if (paths.isEmpty()) {
            matcher = new OrRequestMatcher(new AntPathRequestMatcher("/**"));
        } else {
            matcher = new OrRequestMatcher(Arrays.stream(paths.split(",")).map(
                    x -> new AntPathRequestMatcher(x.trim())
            ).collect(Collectors.toList()));
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (this.matcher.matches(httpServletRequest)) {
            log.info("Filtering requests");
            String authHeader = httpServletRequest.getHeader(HEADER);
            if (existJwt(authHeader)) {
                log.fine("exist jwt");
                setUpCredential(authHeader);
            } else {
                SecurityContextHolder.clearContext();
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            log.info("No filtering");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }

    }

    private boolean existJwt(String header) {
        if (Strings.hasText(header) && header.startsWith(PREFIX)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private boolean validateJwt(String token) {
        String jwtToken = token.replace(PREFIX, "");
        return Jwts.parser().setSigningKey(SECRET.getBytes(Charset.forName("UTF-8")))
                .isSigned(jwtToken);
    }

    private void setUpCredential(String token) {
        String jwtToken = token.replace(PREFIX, "");
        log.info("token->" + jwtToken);
        Claims auth = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwtToken).getBody();
        List<Map<String, String>> authorities = (List<Map<String, String>>) auth.get("authorities");
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(auth.getSubject(),
                null,
                authorities.stream().map(m -> new SimpleGrantedAuthority(m.get("authority"))).collect(Collectors.toList()));

        SecurityContextHolder.getContext().setAuthentication(credentials);
    }
}
