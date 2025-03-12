package sansan.auth.Config;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtTokenProvider {
    @JsonIgnore
    private static final String SECRET_KEY = "eGlhbmdxdWFuZzk2OTk=";
    @Value("${sansan.time-session}")
    private int timeSession;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + timeSession)) // 1 giờ
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public  Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}
