package com.example.patient.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.patient.dao.PatientDao;
import com.example.patient.model.Patient;

@Service
public class PatientServiceImpl implements PatientService {

	@Autowired
	private PatientDao patientDao;

	@Override
	public Patient addPatient(Patient patient) {
		Patient addedPatient = patientDao.addPatient(patient);
		return addedPatient;
	}

	@Override
	public Patient updatePatient(Patient patient) {
		Patient updatedPatient = patientDao.updatePatient(patient);
		return updatedPatient;
	}

	@Override
	public boolean deletePatient(int id) {
		boolean deleted = patientDao.deletePatient(id);
		return deleted;
	}

	@Override
	public Patient getPatient(int id) {
		return patientDao.getPatient(id);
	}

	@Override
	public List<Patient> getPatient(String queryParam, String identifier) {
		return patientDao.getPatient(queryParam, identifier);
	}

	@Override
	public List<Patient> getAllPatients() {
		return patientDao.getAllPatients();
	}

}
