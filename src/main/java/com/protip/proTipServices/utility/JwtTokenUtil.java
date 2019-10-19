package com.protip.proTipServices.utility;

import com.protip.proTipServices.model.Login;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private static String secret = "proTipServicesSeferAlen";

    /**
     * Method for getting username from jwt
     */
    public static String getUsernameFromToken(final String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Method for getting expiration date from jwt
     */
    public static Date getExpirationDateFromToken(final String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public static String getPayload(final String token) {
        return Jwts.parser().setSigningKey(secret).parse(token).getBody().toString();
    }

    /**
     * Method for getting claim from jwt
     */
    public static <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method for getting all claims from jwt
     */
    private static Claims getAllClaimsFromToken(final String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public static Claims decodeJWT(final String jwt) {
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))
                .parseClaimsJws(jwt).getBody();

        return claims;
    }

    /**
     * Method for check jwt expiration
     */
    private static Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Method for generating jwt
     */
    public static String generateToken(final Login userLogin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Role", userLogin.getRole().getName());
        return doGenerateToken(claims, userLogin.getUsername());
    }

    /**
     * Method for generating jwt
     */
    private static String doGenerateToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
    }	//validate token

    /**
     * Method for validating jwt
     */
    public static Boolean validateToken(final String token, final Login userLogin) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userLogin.getUsername()) && !isTokenExpired(token));
    }
}
