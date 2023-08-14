package com.codeit.coronavirustracker;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeit.coronavirustracker.models.Patient;

@SpringBootApplication
@RestController
public class CoronaVirusTrackerApplication {

	private final PatientRepostory patientRepostory;

	public CoronaVirusTrackerApplication(PatientRepostory patientRepostory) {
		this.patientRepostory = patientRepostory;
	}

	public static void main(String[] args) {
		SpringApplication.run(CoronaVirusTrackerApplication.class, args);
	}

    @RequestMapping("/patients")
	public List<Patient> getPatients() {
		return patientRepostory.findAll();
	}

	record PatientRecord(String name, int cases, String status, String phoneNumber) {}

	@PostMapping("/patients")
	public Patient addPatient(@RequestBody PatientRecord patientRecord) {
		Patient patient = new Patient(null, patientRecord.name(), patientRecord.cases(), patientRecord.status(), patientRecord.phoneNumber());
		
		return patientRepostory.save(patient);
	}

	@RequestMapping("/patients/{id}")
	public Patient getPatient(@PathVariable("id") Integer id) {
		return patientRepostory.findById(id).orElse(null);
	}

	@RequestMapping("/patients/{id}/status")
	public String getPatientStatus(@PathVariable("id") Integer id) {
		return patientRepostory.findById(id).orElse(null).getStatus();
	}

	@DeleteMapping("/patients/{id}")
	public void deletePatient(@PathVariable("id") Integer id) {
		patientRepostory.deleteById(id);
	}





}
