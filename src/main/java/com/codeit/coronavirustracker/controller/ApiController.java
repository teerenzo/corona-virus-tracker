package com.codeit.coronavirustracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.codeit.coronavirustracker.PatientRepostory;
import com.codeit.coronavirustracker.models.Patient;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private PatientRepostory patientRepostory;

    @GetMapping("/patients")
    public List<Patient> getPatients() {
        return patientRepostory.findAll();
    }

    @PostMapping("/patients")
    public Patient addPatient(@RequestBody Patient patient) {
        return patientRepostory.save(patient);
    }

    @GetMapping("/patients/{id}")
    public Patient getPatient(@PathVariable("id") Integer id) {
        return patientRepostory.findById(id).orElse(null);
    }

    @DeleteMapping("/patients/{id}")
    public void deletePatient(@PathVariable("id") Integer id) {
        patientRepostory.deleteById(id);
    }

    // Add other REST API endpoints as needed
}
