package com.example.demo.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.*;
import com.example.demo.repository.*;
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PatientController {
	@Autowired
	PatientRepository patientRepository;
	@GetMapping("/patients")
	public ResponseEntity<List<Patient>> getAllPatients(@RequestParam(required = false) String title) {
		try {
			List<Patient> patients = new ArrayList<Patient>();
			if (title == null)
				patientRepository.findAll().forEach(patients::add);
			else
				patientRepository.findByPatientNameContaining(title).forEach(patients::add);
			if (patients.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(patients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable("id") long id) {
		Optional<Patient> patientData = patientRepository.findById(id);
		if (patientData.isPresent()) {
			return new ResponseEntity<>(patientData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping("/patients")
	public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
		try {
			Patient _patient = patientRepository
					.save(new Patient(patient.getPatientName(), patient.getDescription(), false));
			return new ResponseEntity<>(_patient, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/patients/{id}")
	public ResponseEntity<Patient> updatePatient(@PathVariable("id") long id, @RequestBody Patient patient) {
		Optional<Patient> patientData = patientRepository.findById(id);
		if (patientData.isPresent()) {
			Patient _patient = patientData.get();
			_patient.setPatientName(patient.getPatientName());
			_patient.setDescription(patient.getDescription());
			_patient.setVaccinated(patient.isVaccinated());
			return new ResponseEntity<>(patientRepository.save(_patient), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@DeleteMapping("/patients/{id}")
	public ResponseEntity<HttpStatus> deletePatient(@PathVariable("id") long id) {
		try {
			patientRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/patients")
	public ResponseEntity<HttpStatus> deleteAllPatients() {
		try {
			patientRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/patients/published")
	public ResponseEntity<List<Patient>> findByPublished() {
		try {
			List<Patient> patients = patientRepository.findByVaccinated(true);
			if (patients.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(patients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}