package com.protip.proTipServices.model;

import javax.validation.Valid;
import javax.validation.constraints.Size;

public class Register {

    @Valid
    private ProTipUser proTipUser;
    @Size(min = 7, message = "Password must be at least 7 characters long")
    private String password;

    public Register(ProTipUser proTipUser, String password) {
        this.proTipUser = proTipUser;
        this.password = password;
    }

    public ProTipUser getProTipUser() {
        return proTipUser;
    }

    public void setProTipUser(ProTipUser proTipUser) {
        this.proTipUser = proTipUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
