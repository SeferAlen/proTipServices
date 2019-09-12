package com.protip.proTipServices.model;

import java.util.Date;
import java.util.UUID;

public class User {

    private final UUID idUser;
    private final String firstName;
    private final String lastName;
    private final Date dateOfBirth;

    public User(UUID idUser, String firstName, String lastName, Date dateOfBirth) {
        this.idUser = idUser;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
