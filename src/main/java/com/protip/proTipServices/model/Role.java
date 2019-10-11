package com.protip.proTipServices.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * Class for defining user roles in application
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "idRole")
    private UUID idRole;
    @NotNull
    @Column(name = "name")
    private String name;
    @ManyToMany
    @JoinTable(
            name = "login_role",
            joinColumns = @JoinColumn(name = "idLogin"),
            inverseJoinColumns = @JoinColumn(name = "idRole"))
    private Set<Login> loginAccounts;

    /**
     * Instantiates a new Role.
     */
    public Role() {}

    /**
     * Instantiates a new Role.
     *
     * @param name the name
     */
    public Role(@NotNull String name) {
        this.name = name;
    }

    /**
     * Gets id role.
     *
     * @return the id role
     */
    public UUID getIdRole() {
        return idRole;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
