package com.example.patient.model;

import java.util.ArrayList;
import java.util.List;

public class Patients {
	private List<Patient> patientList;

	public List<Patient> getPatientList() {
		if (patientList == null) {
			patientList = new ArrayList<>();
		}
		return patientList;
	}

	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}
}