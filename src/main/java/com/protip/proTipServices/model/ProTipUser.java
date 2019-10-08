package com.protip.proTipServices.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "users")
public class ProTipUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idUser;
    @NotNull
    @Column(name = "firstName")
    private String firstName;
    @NotNull
    @Column(name = "lastName")
    private String lastName;
    @NotNull
    @Email(message = "Email not valid")
    @Column(name = "email")
    private String email;
    @Column(name = "dateOfBirth")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;

    public ProTipUser() {}

    public ProTipUser(@NotNull String firstName, @NotNull String lastName, @NotNull @Email String email, Date dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return firstName + lastName;
    }
}
