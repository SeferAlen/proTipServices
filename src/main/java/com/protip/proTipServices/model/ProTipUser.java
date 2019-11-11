package com.protip.proTipServices.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

/**
 * Class for basic user type in application
 */
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
    @NotNull
    @Column(name = "proTipUserValidityDate")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date proTipUserValidityDate;
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private Set<Message> message;

    /**
     * Instantiates a new Pro tip user.
     */
    public ProTipUser() {
    }

    /**
     * Instantiates a new Pro tip user.
     *
     * @param firstName              the first name
     * @param lastName               the last name
     * @param email                  the email
     * @param dateOfBirth            the date of birth
     * @param proTipUserValidityDate the pro tip user validity date
     */
    public ProTipUser(@NotNull String firstName,
                      @NotNull String lastName,
                      @NotNull @Email(message = "Email not valid") String email,
                      Date dateOfBirth,
                      @NotNull Date proTipUserValidityDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.proTipUserValidityDate = proTipUserValidityDate;
    }

    /**
     * Gets id user.
     *
     * @return the id user
     */
    public UUID getIdUser() {
        return idUser;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets pro tip user validity date.
     *
     * @return the pro tip user validity date
     */
    public Date getProTipUserValidityDate() {
        return proTipUserValidityDate;
    }

    /**
     * Sets pro tip user validity date.
     *
     * @param proTipUserValidityDate the pro tip user validity date
     */
    public void setProTipUserValidityDate(Date proTipUserValidityDate) {
        this.proTipUserValidityDate = proTipUserValidityDate;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
