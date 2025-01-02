package com.ai.calorieTrackerApp.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class JwtUtils {
    private JwtUtils(){}
    private static final SecretKey secretKey=Jwts.SIG.HS256.key().build();
    private static final String ISSUER="coding_streams_auth_server";

    public static boolean validateToken(String jwtToken) {
        return parseToken(jwtToken).isPresent();
    }

    private static Optional<Claims> parseToken(String jwtToken) {
        var jwtParser=Jwts.parser()
                .verifyWith(secretKey)
                .build();

        try{
            return Optional.of(jwtParser.parseSignedClaims(jwtToken)
                    .getPayload());
        }catch (JwtException | IllegalArgumentException e){
            log.error("JWT Exception occured");
        }
        return Optional.empty();
    }

    public static Optional<String> getUsernameFromToken(String jwtToken) {
        var claimsOptional= parseToken(jwtToken);
        return claimsOptional.map(Claims::getSubject);
    }

    public static String generateToken(String emailId) {

        var currentDate=new Date();
        var expDate= DateUtils.addMinutes(currentDate,60);
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(emailId)
                .signWith(secretKey)
                .issuedAt(currentDate)
                .expiration(expDate)
                .compact();
    }
}
