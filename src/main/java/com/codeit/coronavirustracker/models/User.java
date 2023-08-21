package com.codeit.coronavirustracker.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Patient> patients;

    // getters and setters
    public User() {
        // Empty constructor required by JPA
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    // getters and setters

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }


    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }



}
