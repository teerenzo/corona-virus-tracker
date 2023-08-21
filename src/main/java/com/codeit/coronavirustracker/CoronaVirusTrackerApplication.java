package com.codeit.coronavirustracker;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeit.coronavirustracker.models.Patient;
import com.codeit.coronavirustracker.models.User;

import jakarta.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


@SpringBootApplication
@Controller
public class CoronaVirusTrackerApplication {

	@Autowired
    private AuthService authService;


	private final PatientRepostory patientRepostory;
	private final UserRepository userRepository;

	public CoronaVirusTrackerApplication(PatientRepostory patientRepostory,UserRepository userRepository) {
		this.patientRepostory = patientRepostory;
		this.userRepository=userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(CoronaVirusTrackerApplication.class, args);
	}

    // @RequestMapping("/patients")
	// public List<Patient> getPatients() {
	// 	return patientRepostory.findAll();
	// }

	// record PatientRecord(String name, int cases, String status, String phoneNumber) {}

	// @PostMapping("/patients")
	// public Patient addPatient(@RequestBody PatientRecord patientRecord) {
	// 	Patient patient = new Patient(null, patientRecord.name(), patientRecord.cases(), patientRecord.status(), patientRecord.phoneNumber());
		
	// 	return patientRepostory.save(patient);
	// }

	// @RequestMapping("/patients/{id}")
	// public Patient getPatient(@PathVariable("id") Integer id) {
	// 	return patientRepostory.findById(id).orElse(null);
	// }

	// @RequestMapping("/patients/{id}/status")
	// public String getPatientStatus(@PathVariable("id") Integer id) {
	// 	return patientRepostory.findById(id).orElse(null).getStatus();
	// }

	// @DeleteMapping("/patients/{id}")
	// public void deletePatient(@PathVariable("id") Integer id) {
	// 	patientRepostory.deleteById(id);
	// }

	@GetMapping("/patients")
    public String listItems(Model model,Principal principal) {
		if (principal == null) {
            // User is not authenticated, redirect to the login page
            return "redirect:/login";
        }
        List<Patient> items = patientRepostory.findAll();
        model.addAttribute("items", items);
        return "items";
    }

	@GetMapping("/patients/new")
	public String newItem(Model model) {
	
		model.addAttribute("patient", new Patient());
		return "registerPatient";
	}

	@PostMapping("/patients")
	public String saveItem(@ModelAttribute("patient") Patient patient) {
		patientRepostory.save(patient);
		return "redirect:/patients";
	}

	@GetMapping("/patients/{id}/edit")
	public String editItem(@PathVariable("id") Integer id, Model model) {
		Patient patient = patientRepostory.findById(id).orElse(null);
		System.out.println(patient.getName());
		model.addAttribute("patient", patient);
		return "editPatient";
	}

	@PostMapping("/patients/{id}")
	public String updateItem(@PathVariable("id") Integer id, @ModelAttribute("patient") Patient patient) {

		Patient patientToUpdate = patientRepostory.findById(id).orElse(null);
		patientToUpdate.setName(patient.getName());
		patientToUpdate.setCases(patient.getCases());
		patientToUpdate.setStatus(patient.getStatus());
		patientToUpdate.setPhoneNumber(patient.getPhoneNumber());
		patientRepostory.save(patientToUpdate);
		return "redirect:/patients";
	}

	@GetMapping("/patients/{id}/delete")
	public String deleteItem(@PathVariable("id") Integer id) {
		patientRepostory.deleteById(id);
		return "redirect:/patients";
	}


	// authentication

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String login(@ModelAttribute("user") User user, Model model) {
		User  user1= authService.authenticate(user.getUsername(), user.getPassword());

        if (user1 != null) {
            // Authentication successful, redirect to the dashboard
			// set security loggin
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			// Add user roles to the authorities collection (replace ROLE_USER with your actual role names)
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
			// You can add additional authorities if needed, e.g., based on user permissions
			
			// Create the Authentication token with the username, password, and authorities
			Authentication token = new UsernamePasswordAuthenticationToken(user1.getUsername(), user1.getPassword(), authorities);
			// token.setDetails(user1);
			
			// Set the Authentication token as authenticated
			token.setAuthenticated(true);
			SecurityContextHolder.getContext().setAuthentication(token);
			
            return "redirect:/patients";

        } else {
            // Authentication failed, show an error message
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@PostMapping("/register")
	public String register(@ModelAttribute("user") User user) {
		userRepository.save(user);
		return "redirect:/login";
	}










}
