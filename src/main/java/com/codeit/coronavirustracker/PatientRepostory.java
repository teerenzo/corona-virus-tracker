package com.codeit.coronavirustracker;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codeit.coronavirustracker.models.Patient;

public interface PatientRepostory extends JpaRepository<Patient, Integer> {
    
}
