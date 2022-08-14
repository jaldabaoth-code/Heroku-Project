package com.wildcodeschool.cerebook.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please enter your username.")
    @NotNull(message = "Please enter your username.")
    @Size(min = 3, max = 45, message = "The size of your username should be more than 2 characters and less than 45.")
    @Column(unique = true)
    private String username;

    @NotNull(message = "Please enter your password.")
    @NotBlank(message = "Please enter your password.")
    @Size(min = 6, max = 64, message = "The size of your password should be more than 5 characters and less than 45.")
    private String password;

    @NotNull(message = "Please enter your firstname.")
    @NotBlank(message = "Please enter your firstname.")
    @Size(min = 2, max = 155, message = "The size of your password should have more than 1 character and less than 155.")
    private String firstName;

    @NotNull(message = "Please enter your lastname.")
    @NotBlank(message = "Please enter your lastname.")
    @Size(min = 2, max = 155, message = "The size of your password should have more than 1 character and less than 155.")
    private String lastName;

    @Email(message = "E-mail format not valid.")
    @NotEmpty(message = "Please enter email")
    @NotNull(message = "Please enter your lastname.")
    @Size(min = 2, max = 155, message = "The size of your email should have more than 1 character and less than 155.")
    private String email;

    private String role;
    private boolean enabled;

    @JsonBackReference
    @OneToOne(cascade = CascadeType.ALL)
    private CerebookUser cerebookUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    public CerebookUser getCerebookUser() {
        return cerebookUser;
    }

    public void setCerebookUser(CerebookUser cerebookUser) {
        this.cerebookUser = cerebookUser;
    }
}
