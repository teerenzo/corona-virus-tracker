package com.codeit.coronavirustracker.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Patient {

    @Id
    @SequenceGenerator(
        name = "patient_id_sequence",
        sequenceName = "patient_id_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        generator = "patient_id_sequence",
        strategy = GenerationType.AUTO

    )
    
    private Integer id;
    private String name;
    private int cases;
    private String status;
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

     // Default constructor
     public Patient() {
        // Empty constructor required by JPA
    }

    public Patient(Integer id,String name, int cases, String status, String phoneNumber) {
        this.name = name;
        this.cases = cases;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
