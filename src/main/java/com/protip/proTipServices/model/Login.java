package com.protip.proTipServices.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "login")
public class Login implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idLogin")
    private UUID idLogin;
    @NotNull
    @Column(name = "username")
    private String username;
    @NotNull
    @Column(name = "password")
    private String password;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @Valid
    @JoinColumn(name = "id_user", nullable = false)
    private ProTipUser user;
    @ManyToMany(mappedBy = "loginAccounts")
    private Set<Role> roles;

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
