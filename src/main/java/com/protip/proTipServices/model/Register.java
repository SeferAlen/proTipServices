package com.protip.proTipServices.model;

import javax.validation.Valid;
import javax.validation.constraints.Size;

/**
 * Class for user registration
 */
public class Register {

    @Valid
    private ProTipUser proTipUser;
    @Size(min = 7, message = "Password must be at least 7 characters long")
    private String password;

    /**
     * Instantiates a new Register.
     *
     * @param proTipUser the pro tip user
     * @param password   the password
     */
    public Register(ProTipUser proTipUser, String password) {
        this.proTipUser = proTipUser;
        this.password = password;
    }

    /**
     * Gets pro tip user.
     *
     * @return the pro tip user
     */
    public ProTipUser getProTipUser() {
        return proTipUser;
    }

    /**
     * Sets pro tip user.
     *
     * @param proTipUser the pro tip user
     */
    public void setProTipUser(ProTipUser proTipUser) {
        this.proTipUser = proTipUser;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
