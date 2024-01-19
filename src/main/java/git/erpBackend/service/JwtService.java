package git.erpBackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String SECRET_KEY;

    @Value("${jwt.expiration-time-seconds}")
    private long EXPIRATION_TIME_SECONDS;

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        return Jwts
                .builder()
                .setSubject(username)
                .setClaims(Map.of("username", username, "authorities", getAuthorities(authorities)))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * EXPIRATION_TIME_SECONDS))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String getAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<String> authoritiesSet =  authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
        return String.join(",", authoritiesSet);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public Claims getClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }
}
