package com.example.patient.dao;

import java.util.List;

import com.example.patient.model.Patient;

public interface PatientDao {

	Patient addPatient(Patient patient);

	Patient updatePatient(Patient patient);

	boolean deletePatient(int id);

	Patient getPatient(int id);
	
	List<Patient> getPatient(String queryParam, String identifier);

	List<Patient> getAllPatients();

}
