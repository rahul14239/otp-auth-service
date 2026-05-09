package central.auth_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "mysecretkeymysecretkeymysecretkey123456";

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateAccessToken(String user_id){
        return Jwts.builder()
                .setSubject(user_id)
                .setIssuedAt(new Date())
                .setExpiration( new Date(
                        System.currentTimeMillis()
                                + 1000 * 60 * 15
                )).signWith(key, SignatureAlgorithm.HS256).compact();
    }

    public String generateRefreshToken(
            String uid
    ){

        return Jwts.builder()

                .setSubject(uid)

                .setIssuedAt(new Date())

                .setExpiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60 * 24 * 7
                        )
                )

                .signWith(
                        key,
                        SignatureAlgorithm.HS256
                )

                .compact();
    }

    public String extractUserId(String token){

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token){

        try{

            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;

        }catch (Exception e){

            return false;
        }
    }


}
