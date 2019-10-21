package com.protip.proTipServices.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

/**
 * Class for user login
 */
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
    @ManyToOne
    @JoinColumn
    private Role role;

    /**
     * Instantiates a new Login.
     */
    public Login() {
    }

    /**
     * Instantiates a new Login.
     *
     * @param username the username
     * @param password the password
     * @param user     the user
     * @param role     the role
     */
    public Login(final String username,
                 final String password,
                 final ProTipUser user,
                 final Role role) {
        this.username = username;
        this.password = password;
        this.user = user;
        this.role = role;
    }

    /**
     * Gets id login.
     *
     * @return the id login
     */
    public UUID getIdLogin() {
        return idLogin;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
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

    /**
     * Gets user.
     *
     * @return the user
     */
    public ProTipUser getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(ProTipUser user) {
        this.user = user;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
