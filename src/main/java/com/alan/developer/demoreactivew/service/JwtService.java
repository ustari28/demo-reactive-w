package com.alan.developer.demoreactivew.service;

import com.alan.developer.demoreactivew.model.UserCredentials;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class JwtService {

    public String getJwt(final UserCredentials userCredentials) {
        String mySecret = "mysecret";
        String jwtId = "myJwtId";
        String issuer = "AlanSystems";
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN");
        return Jwts.builder()
                .setId(jwtId)
                .setIssuer(issuer)
                .setSubject(userCredentials.getUsername())
                .claim("authorities", authorities)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1360000))
                .signWith(SignatureAlgorithm.HS256, mySecret)
                .compact();


    }
}
