package life.trevino.contacts.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    private final String SECRET_KEY = "secret";

    private Claims extractAllClaims(String token) {
        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claims;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        T applied = claimsResolver.apply(claims);
        return applied;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        Boolean expired = extractExpiration(token).before(new Date());
        return expired;
    }

    public String createToken(Map<String, Object> claims, String subject) {
        Calendar exp = Calendar.getInstance();
        exp.add(Calendar.HOUR, 10);
        Date expDate = exp.getTime();
        String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date())
                .setExpiration(expDate).signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
        return token;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String token = createToken(claims, userDetails.getUsername());
        return token;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        Boolean tokenExpired = isTokenExpired(token);
        Boolean tokenValid = userDetails.getUsername().equalsIgnoreCase(username) && !tokenExpired;
        return tokenValid;
    }

}
