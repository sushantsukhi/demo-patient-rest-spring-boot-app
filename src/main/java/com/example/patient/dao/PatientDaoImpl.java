package com.example.patient.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.patient.interceptor.NoLogging;
import com.example.patient.model.Patient;

@NoLogging
@Repository
public class PatientDaoImpl implements PatientDao {

	private static Map<Integer, Patient> map = new HashMap<Integer, Patient>();
	private static final String NAME = "name";
	private static final String AGE = "age";
	private static final String DOCTOR = "doctor";

	static {
		map.put(1, new Patient(1, "Sushant", "35", "Dr Jha"));
		map.put(2, new Patient(2, "Siyan", "22", "Dr Nanda"));
		map.put(3, new Patient(3, "Prashant", "33", "Dr Lahoti"));
		map.put(4, new Patient(4, "Sushant", "47", "Dr Sinha"));
		map.put(5, new Patient(5, "Nitin", "25", "Dr Jha"));
		map.put(6, new Patient(6, "Suyog", "22", "Dr Lahoti"));
		map.put(7, new Patient(7, "Sushant", "30", "Dr Lahoti"));
	}

	@Override
	public Patient addPatient(Patient patient) {
		Patient addedPatient = map.put(patient.getId(), patient);
		return addedPatient;
	}

	@Override
	public Patient updatePatient(Patient patient) {
		Patient updatedPatient = map.put(patient.getId(), patient);
		return updatedPatient;
	}

	@Override
	public boolean deletePatient(int id) {
		boolean removed = map.entrySet().removeIf(patient -> (patient.getKey() == id));
		return removed;
	}

	@Override
	public Patient getPatient(int id) {
		Patient pat = null;
		Optional<Patient> optionalPatient = map.entrySet().stream().filter(patient -> (patient.getKey() == id))
				.map(patient -> patient.getValue()).findAny();
		if (optionalPatient.isPresent()) {
			pat = optionalPatient.get();
		}
		return pat;
	}

	@Override
	public List<Patient> getPatient(String queryParam, String identifier) {
		String[] queryParamArray = queryParam.split(",");
		String[] identifierArray = identifier.split(",");

		List<Patient> patientList = null;
		if (identifierArray.length == 1) {
			if (NAME.equalsIgnoreCase(identifierArray[0])) {
				patientList = map.entrySet().stream()
						.filter(patient -> queryParamArray[0].equalsIgnoreCase(patient.getValue().getName()))
						.map(patient -> patient.getValue()).collect(Collectors.toList());
			} else if (AGE.equalsIgnoreCase(identifierArray[0])) {
				patientList = map.entrySet().stream()
						.filter(patient -> queryParamArray[0].equalsIgnoreCase(patient.getValue().getAge()))
						.map(patient -> patient.getValue()).collect(Collectors.toList());
			} else if (DOCTOR.equalsIgnoreCase(identifierArray[0])) {
				patientList = map.entrySet().stream()
						.filter(patient -> queryParamArray[0].equalsIgnoreCase(patient.getValue().getDoctor()))
						.map(patient -> patient.getValue()).collect(Collectors.toList());
			}
		} else if (identifierArray.length == 2) {
			if (NAME.equalsIgnoreCase(identifierArray[0]) && AGE.equalsIgnoreCase(identifierArray[1])) {
				patientList = map.entrySet().stream()
						.filter(patient -> queryParamArray[0].equalsIgnoreCase(patient.getValue().getName()))
						.filter(patient -> queryParamArray[1].equalsIgnoreCase(patient.getValue().getAge()))
						.map(patient -> patient.getValue()).collect(Collectors.toList());
			} else if (NAME.equalsIgnoreCase(identifierArray[0]) && DOCTOR.equalsIgnoreCase(identifierArray[1])) {
				patientList = map.entrySet().stream()
						.filter(patient -> queryParamArray[0].equalsIgnoreCase(patient.getValue().getName()))
						.filter(patient -> queryParamArray[1].equalsIgnoreCase(patient.getValue().getDoctor()))
						.map(patient -> patient.getValue()).collect(Collectors.toList());
			} else if (AGE.equalsIgnoreCase(identifierArray[0]) && DOCTOR.equalsIgnoreCase(identifierArray[1])) {
				patientList = map.entrySet().stream()
						.filter(patient -> queryParamArray[0].equalsIgnoreCase(patient.getValue().getAge()))
						.filter(patient -> queryParamArray[1].equalsIgnoreCase(patient.getValue().getDoctor()))
						.map(patient -> patient.getValue()).collect(Collectors.toList());
			}
		} else if (identifierArray.length == 3) {
			patientList = map.entrySet().stream()
					.filter(patient -> queryParamArray[0].equalsIgnoreCase(patient.getValue().getName()))
					.filter(patient -> queryParamArray[1].equalsIgnoreCase(patient.getValue().getAge()))
					.filter(patient -> queryParamArray[2].equalsIgnoreCase(patient.getValue().getDoctor()))
					.map(patient -> patient.getValue()).collect(Collectors.toList());
		}
		return patientList;
	}

	@Override
	public List<Patient> getAllPatients() {
		List<Patient> patientList = new ArrayList<Patient>(map.values());
		return patientList;
	}

}
