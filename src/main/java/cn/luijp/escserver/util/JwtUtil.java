package cn.luijp.escserver.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${esc.jwt-secret}")
    private String JwtSecret;

    public String generateToken(Map<String, String> payload){
        try{
            Algorithm algorithm = Algorithm.HMAC256(JwtSecret);
            Date date = new Date();
            date.setTime(date.getTime() + 3600L * 24 * 365 * 1000);
            return JWT.create().withPayload(payload).withExpiresAt(date).sign(algorithm);
        }catch (Exception ex){
            return null;
        }
    }

    public DecodedJWT verify(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(JwtSecret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        }catch (Exception ex){
            return null;
        }
    }
}
