package com.protip.proTipServices.model;

/**
 * Class for setting token for user login
 */
public class TokenSet {

    private final String token;
    private final String username;

    /**
     * Instantiates a new Token set.
     *
     * @param token    the token
     * @param username the login
     */
    public TokenSet(String token, String  username) {
        this.token = token;
        this.username = username;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }
}
