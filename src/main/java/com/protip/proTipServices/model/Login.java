package com.protip.proTipServices.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "login")
public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idLogin;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private ProTipUser user;

    public Login() {
    }

    public Login(String username, String password, ProTipUser user) {
        this.username = username;
        this.password = password;
        this.user = user;
    }

    public UUID getIdLogin() {
        return idLogin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProTipUser getUser() {
        return user;
    }

    public void setUser(ProTipUser user) {
        this.user = user;
    }
}
