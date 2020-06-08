package com.example.patient.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.patient.exception.CustomException;
import com.example.patient.model.Patient;
import com.example.patient.service.PatientService;

public class PatientControllerTest1 {

	@InjectMocks
	PatientController controller;

	@Mock
	PatientService service;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getAllPatientsTest() {
		List<Patient> list = new ArrayList<Patient>();
		Patient p1 = new Patient(1, "Sushant", "Dev", "sushant@gmail.com");
		Patient p2 = new Patient(2, "Siyan", "IT", "siyan@gmail.com");
		Patient p3 = new Patient(3, "Prashant", "HR", "prashant@gmail.com");
		Patient p4 = new Patient(4, "Sushant", "IT", "sushant1@gmail.com");
		Patient p5 = new Patient(5, "Nitin", "DevOps", "nitin@gmail.com");
		Patient p6 = new Patient(6, "Suyog", "Dev", "suyog@gmail.com");
		Patient p7 = new Patient(7, "Sushant", "IT", "sushant2@gmail.com");

		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
		list.add(p5);
		list.add(p6);
		list.add(p7);

		when(service.getAllPatients()).thenReturn(list);

		// test
		ResponseEntity<List<Patient>> patientList = controller.getAllPatients();

		assertEquals(7, patientList.getBody().size());
		verify(service, times(1)).getAllPatients();
	}

	@Test
	public void getPatientByIdTest() throws CustomException {
		Patient p = new Patient(2, "Siyan", "22", "Dr Nanda");
		when(service.getPatient(2)).thenReturn(p);

		ResponseEntity<Patient> response = controller.getPatient(2);

		Patient patient = response.getBody();

		assertEquals("Siyan", patient.getName());
		assertEquals("22", patient.getAge());
		assertEquals("Dr Nanda", patient.getDoctor());
		verify(service, times(1)).getPatient(2);
	}

	@Test
	public void getPatientByNameTest() throws Exception {
		List<Patient> list = new ArrayList<Patient>();
		Patient p1 = new Patient(1, "Sushant", "Dev", "sushant@gmail.com");
		Patient p4 = new Patient(4, "Sushant", "IT", "sushant1@gmail.com");
		Patient p7 = new Patient(7, "Sushant", "IT", "sushant2@gmail.com");

		list.add(p1);
		list.add(p4);
		list.add(p7);

		when(service.getPatient("sushant", "name")).thenReturn(list);

		ResponseEntity<List<Patient>> response = controller.findPatient(Optional.of("sushant"), Optional.empty(),
				Optional.empty());

		assertEquals(3, response.getBody().size());
		verify(service, times(1)).getPatient("sushant", "name");
	}

	@Test
	public void getPatientByAgeTest() throws Exception {
		List<Patient> list = new ArrayList<Patient>();
		Patient p2 = new Patient(2, "Siyan", "22", "Dr Nanda");
		Patient p4 = new Patient(6, "Suyog", "22", "Dr Lahoti");
		
		list.add(p2);
		list.add(p4);

		when(service.getPatient("22", "age")).thenReturn(list);

		ResponseEntity<List<Patient>> response = controller.findPatient(Optional.empty(), Optional.of("22"),
				Optional.empty());

		assertEquals(2, response.getBody().size());
		verify(service, times(1)).getPatient("22", "age");
	}

	@Test
	public void getPatientByDoctorTest() throws Exception {
		List<Patient> list = new ArrayList<Patient>();
		Patient p1 = new Patient(1, "Sushant", "35", "Dr Jha");
		Patient p2 = new Patient(5, "Nitin", "25", "Dr Jha");
		list.add(p1);
		list.add(p2);

		when(service.getPatient("Dr Jha", "doctor")).thenReturn(list);

		ResponseEntity<List<Patient>> response = controller.findPatient(Optional.empty(), Optional.empty(),
				Optional.of("Dr Jha"));

		assertEquals(2, response.getBody().size());
		verify(service, times(1)).getPatient("Dr Jha", "doctor");
	}

	@Test
	public void addPatientTest() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Patient patient = new Patient("Siyan", "IT", "siyan@gmail.com");
		when(service.addPatient(patient)).thenReturn(patient);

		ResponseEntity<Patient> response = controller.addPatient(patient);

		System.out.println(response);
		Patient p = response.getBody();
		assertEquals("Siyan", p.getName());
		verify(service, times(1)).addPatient(p);
	}

	@Test
	public void updatePatientTest() throws Exception {
		Patient patient = new Patient(9, "Siyan", "IT", "siyan@gmail.com");
		when(service.updatePatient(patient)).thenReturn(patient);

		ResponseEntity<Patient> response = controller.updatePatient(patient);

		System.out.println(response);
		Patient p = response.getBody();
		assertEquals("Siyan", p.getName());
		verify(service, times(1)).updatePatient(p);
	}

}
