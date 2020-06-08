package com.example.patient.service;

import java.util.List;

import com.example.patient.model.Patient;

public interface PatientService {

	Patient addPatient(Patient patient);

	Patient updatePatient(Patient patient);

	boolean deletePatient(int id);

	Patient getPatient(int id);
	
	List<Patient> getPatient(String queryParam, String identifier);

	List<Patient> getAllPatients();
}
