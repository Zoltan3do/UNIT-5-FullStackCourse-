package manu_barone.ViaggiAziendali.tools;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import manu_barone.ViaggiAziendali.entities.Dipendente;
import manu_barone.ViaggiAziendali.exceptions.UnothorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWT {

    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Dipendente dipendent) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
                .subject(dipendent.getUsername())
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token);
        } catch (Exception e) {
            throw new UnothorizedException("Problemi con il token!");
        }
    }

    public String getIdFromToken(String accessToken){
        return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(accessToken).getPayload().getSubject();
    }

}
