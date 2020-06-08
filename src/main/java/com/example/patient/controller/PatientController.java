package com.example.patient.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.patient.exception.CustomException;
import com.example.patient.exception.ErrorResponse;
import com.example.patient.model.Patient;
import com.example.patient.service.PatientService;

@RestController
@RequestMapping("v1")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@PostMapping(path = "/patient", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Patient> addPatient(@RequestBody Patient patient) {
		// @RequestHeader(name = "X-COM-LOCATION", required = false) String
		int size = patientService.getAllPatients().size();

		Patient existPatient = patientService.getPatient(size);
		if (patient.equals(existPatient)) {
			return new ResponseEntity<Patient>(existPatient, HttpStatus.NOT_ACCEPTABLE);
		}

		Integer id = size + 1;
		patient.setId(id);
		patientService.addPatient(patient);
		return new ResponseEntity<Patient>(patient, HttpStatus.CREATED);
	}

	@PutMapping(path = "/patient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Patient> updatePatient(@RequestBody Patient patient) {
		Patient updatedPatient = patientService.updatePatient(patient);
		if (updatedPatient == null) {
			return new ResponseEntity<Patient>(updatedPatient, HttpStatus.NOT_MODIFIED);
		}
		return new ResponseEntity<Patient>(patient, HttpStatus.ACCEPTED);
	}

	@DeleteMapping(path = "/patient")
	public ResponseEntity<ErrorResponse> deletePatient(@RequestParam String id) {
		boolean patientDeletedFlag = patientService.deletePatient(Integer.valueOf(id));
		if (!patientDeletedFlag) {
			return new ResponseEntity<ErrorResponse>(new ErrorResponse("Unable to delete patient ", null),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ErrorResponse>(new ErrorResponse("Patient has been deleted successfully ", null),
				HttpStatus.ACCEPTED);
	}

	// , headers = { "Content-Type=application/xml", "Accept=application/xml" }
	@GetMapping(path = "/patient")
	public ResponseEntity<List<Patient>> findPatient(@RequestParam("name") Optional<String> name,
			@RequestParam("age") Optional<String> age, @RequestParam("doctor") Optional<String> doctor) {
		// Map<String, String> allParams,
		// @RequestParam List<String> id
		// @RequestHeader("accept-language") String lang,
		List<Patient> patients = null;
		String queryParamsAndID = findQueryParamsAndIDs(name, age, doctor);
		if (queryParamsAndID != null) {
			String[] queryParamsAndIDArray = queryParamsAndID.split(";");
			patients = patientService.getPatient(queryParamsAndIDArray[0], queryParamsAndIDArray[1]);
		} else {
			return new ResponseEntity<List<Patient>>(patients, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	// , headers = { "Content-Type=application/xml", "Accept=application/xml" }
	@GetMapping(path = "/patient/{id}")
	public ResponseEntity<Patient> getPatient(@PathVariable int id) throws CustomException {
		// @RequestHeader("accept-language") String lang,
		Patient patient = patientService.getPatient(id);
		if (patient == null) {
			throw new CustomException("Patient does not exist with id: " + id);
		}
		return new ResponseEntity<Patient>(patient, HttpStatus.OK);
	}

	@GetMapping(path = "/patients", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Patient>> getAllPatients() {
		List<Patient> patients = patientService.getAllPatients();
		return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
	}

	private String findQueryParamsAndIDs(Optional<String> name, Optional<String> age, Optional<String> doctor) {
		int counter = 0;
		String queryParams = null;
		String queryIds = null;

		if (name.isPresent()) {
			counter++;
			if (age.isPresent()) {
				counter++;
			}
			if (doctor.isPresent()) {
				counter++;
				counter++;
			}
			if (counter == 1) {
				queryParams = name.get();
				queryIds = "name";
			}
			if (counter == 2) {
				queryParams = name.get() + "," + age.get();
				queryIds = "name,age";
			}
		} else if (age.isPresent()) {
			counter++;
			if (doctor.isPresent()) {
				counter++;
			}
			if (name.isPresent()) {
				counter++;
				counter++;
			}
			if (counter == 1) {
				queryParams = age.get();
				queryIds = "age";
			}
			if (counter == 2) {
				queryParams = age.get() + "," + doctor.get();
				queryIds = "age,doctor";
			}
		} else if (doctor.isPresent()) {
			counter++;
			if (name.isPresent()) {
				counter++;
			}
			if (age.isPresent()) {
				counter++;
				counter++;
			}
			if (counter == 1) {
				queryParams = doctor.get();
				queryIds = "doctor";
			}
			if (counter == 2) {
				queryParams = name.get() + "," + doctor.get();
				queryIds = "name,doctor";
			}
		}
		if (counter == 4) {
			queryParams = name.get() + "," + age.get() + "," + doctor.get();
			queryIds = "name,age,doctor";
		}
		if (queryParams == null) {
			return null;
		}
		return queryParams + ";" + queryIds;
	}
}
