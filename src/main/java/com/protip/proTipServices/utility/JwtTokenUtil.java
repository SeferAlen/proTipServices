package com.protip.proTipServices.utility;

import com.protip.proTipServices.exceptions.TokenExpiredException;
import com.protip.proTipServices.model.Login;
import com.protip.proTipServices.model.ProTipUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Utility method for json web token related actions
 */
public class JwtTokenUtil implements Serializable {
    private static final String TOKEN_NULL = "Token must not be null";
    private static final String CLAIMS_RESOLVER_NULL = "Claims resolver function must not be null";
    private static final String LOGIN_NULL = "Login must not be null";
    private static final String PRO_TIP_USER_NULL = "ProTipUser must not be null";
    private static final String CLAIM_ROLE = "Role";
    private static final String CLAIM_VALIDITY_DATE = "ProTipUserValidityDate";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final int TOKEN_EXPIRATION = 5000;
    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private static String jwtSecret = "proTipServicesSeferAlen";

    /**
     * Method for getting username from token claims
     *
     * @param token {@link String} the token
     * @return {@link String}      the username
     */
    public static String getUsernameFromToken(final String token) {
        Objects.requireNonNull(token, TOKEN_NULL);

        return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * Method for getting expiration date from token claims
     *
     * @param token {@link String} the token
     * @return {@link Date}      the expiration date
     */
    public static Date getExpirationDateFromToken(final String token) {
        Objects.requireNonNull(token, TOKEN_NULL);

        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * Generic method for getting claim from token
     *
     * @param token          {@link String}              the token
     * @param claimsResolver {@link Function<Claims, T>} the function
     * @return {@link T} the claim
     */
    public static <T> T getClaimFromToken(final String token, final Function<Claims, T> claimsResolver) {
        Objects.requireNonNull(token, TOKEN_NULL);
        Objects.requireNonNull(claimsResolver, CLAIMS_RESOLVER_NULL);

        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Method for getting all claims from token
     *
     * @param token {@link String} the token
     * @return {@link Claims} the claims
     */
    public static Claims getAllClaimsFromToken(final String token) {
        Objects.requireNonNull(token, TOKEN_NULL);

        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    /**
     * Method for decoding token
     *
     * @param token {@link String} the token
     * @return {@link Claims} the claims
     */
    public static Claims decodeJWT(final String token) {
        Objects.requireNonNull(token, TOKEN_NULL);

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(jwtSecret))
                .parseClaimsJws(token).getBody();

        return claims;
    }

    /**
     * Method for validating token
     *
     * @param token {@link String} the token
     */
    public static void validateToken(final String token) throws TokenExpiredException {
        Objects.requireNonNull(token, TOKEN_NULL);

        if(isTokenExpired(token)) throw new TokenExpiredException("Token " + token + " is expired");
        decodeJWT(token);
    }

    /**
     * Method for generating token from login and user data
     *
     * @param userLogin  {@link Login}      the login
     * @param proTipUser {@link ProTipUser} the proTipUser data
     * @return {@link String} the token
     */
    public static String generateToken(final Login userLogin, final ProTipUser proTipUser) {
        Objects.requireNonNull(userLogin, LOGIN_NULL);
        Objects.requireNonNull(proTipUser, PRO_TIP_USER_NULL);

        final Date proTipUserValidityDate = proTipUser.getProTipUserValidityDate();
        final String formatedDate = new SimpleDateFormat(DATE_FORMAT).format(proTipUserValidityDate);

        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_ROLE, userLogin.getRole().getName());
        claims.put(CLAIM_VALIDITY_DATE, formatedDate);

        return doGenerateToken(claims, userLogin.getUsername());
    }

    /**
     * Method for checking ProTipUser validity date from token
     *
     * @param token {@link String} the token
     * @return {@link boolean}     the validity status
     */
    public static boolean checkProTipUserValidityDate(final String token) {
        Objects.requireNonNull(token, TOKEN_NULL);

        /// TODO: Work in progress
        final Claims claims = decodeJWT(token);

        final Date validityDate = (Date) claims.get(CLAIM_VALIDITY_DATE);
        final Date now = new Date();
        final String today = new SimpleDateFormat(DATE_FORMAT).format(now);
        final Date nowFormated = new Date(today);
        final Date expiration = getExpirationDateFromToken(token);
        final String registerDateFormated = new SimpleDateFormat(DATE_FORMAT).format(expiration);

        return true;
    }

    /**
     * Method for checking is token expired
     *
     * @param token {@link String} the token
     * @return {@link boolean}     the token expire status
     */
    private static Boolean isTokenExpired(final String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Method for token generation
     *
     * @param claims {@link Map<String, Object>} the claims
     * @param subject {@link String} the token
     * @return {@link String} the token with claims
     */
    private static String doGenerateToken(final Map<String, Object> claims, final String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * TOKEN_EXPIRATION))
            .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }
}
