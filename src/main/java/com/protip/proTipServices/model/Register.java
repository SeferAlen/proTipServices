package com.protip.proTipServices.model;

public class Register {

    private ProTipUser proTipUser;
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
