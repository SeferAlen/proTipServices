package com.protip.proTipServices.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "idRole")
    private UUID idRole;
    @NotNull
    @Column(name = "name")
    private final String name;
    @ManyToMany
    @JoinTable(
            name = "login_role",
            joinColumns = @JoinColumn(name = "idLogin"),
            inverseJoinColumns = @JoinColumn(name = "idRole"))
    private Set<Login> loginAccounts;

    public Role(@NotNull String name) {
        this.name = name;
    }

    public UUID getIdRole() {
        return idRole;
    }

    public String getName() {
        return name;
    }
}
