package com.protip.proTipServices.model;

/**
 * Class for setting token for user login
 */
public class TokenSet {

    private final String token;

    /**
     * Instantiates a new Token set.
     *
     * @param token    the token
     */
    public TokenSet(String token) {
        this.token = token;
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public String getToken() {
        return token;
    }
}
