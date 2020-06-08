package com.example.patient.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.patient.dao.PatientDao;
import com.example.patient.model.Patient;
import com.example.patient.service.PatientServiceImpl;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class PatientServiceTest {

	@InjectMocks
	PatientServiceImpl patientServiceImpl;

	@Mock
	PatientDao patientDao;

	// @Test
	public void testAddPatient() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		// when(patientService.addPatient(any(Patient.class))).thenReturn(true);

		Patient patient = new Patient(1, "Sushant", "Dev", "sushant@gmail.com");
		// patientServiceImpl.addPatient(patient);

		// assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		// assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/1");
	}

	@Test
	public void testFindAll() {
		// given
		Patient patient1 = new Patient(1, "Sushant", "Dev", "abc@gmail.com");
		Patient patient2 = new Patient(2, "Siyan", "IT", "xyz@gmail.com");
		// Patients patients = new Patients();
		Map<Integer, Patient> map = new HashMap<Integer, Patient>();
		map.put(1, patient1);
		map.put(2, patient2);
		List<Patient> values = new ArrayList<Patient>(map.values());

		when(patientDao.getAllPatients()).thenReturn(values);

		// when
		List<Patient> allPatients = patientServiceImpl.getAllPatients();

		// then
		assertThat(allPatients.size()).isEqualTo(2);
		// This is to verify how many times patientService is called
		// verify(patientService, times(1)).getAllPatients();

		assertThat(allPatients.get(0).getName()).isEqualTo(patient1.getName());
		assertThat(allPatients.get(1).getName()).isEqualTo(patient2.getName());
	}

	@Test
	public void testGetPatient() {
		// given
		Patient patient1 = new Patient(1, "Sushant", "Dev", "sushant@gmail.com");
		Patient patient2 = new Patient(4, "Sushant", "IT", "sushant1@gmail.com");
		Patient patient3 = new Patient(7, "Sushant", "IT", "sushant2@gmail.com");

		// Patients patients = new Patients();
		Map<Integer, Patient> map = new HashMap<Integer, Patient>();
		map.put(4, patient2);
		map.put(7, patient3);
		List<Patient> values = new ArrayList<Patient>(map.values());

		when(patientDao.getPatient("name,IT", "Sushant")).thenReturn(values);

		// when
		List<Patient> allPatients = patientServiceImpl.getPatient("name,IT", "Sushant");

		System.out.println("Patient size: " + allPatients.size());
		// then
		assertThat(allPatients.size()).isEqualTo(2);
		// This is to verify how many times patientService is called
		// verify(patientService, times(1)).getAllPatients();

		assertThat(allPatients.get(0).getName()).isEqualTo(patient1.getName());
		assertThat(allPatients.get(1).getName()).isEqualTo(patient2.getName());
	}

}
