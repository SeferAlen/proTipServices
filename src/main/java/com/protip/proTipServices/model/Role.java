package com.protip.proTipServices.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
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
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private Set<Login> login;

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
