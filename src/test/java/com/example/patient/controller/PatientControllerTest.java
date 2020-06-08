package com.example.patient.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.patient.model.Patient;
import com.example.patient.service.PatientService;

//@RunWith(SpringRunner.class)
@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class PatientControllerTest {

	@InjectMocks
	PatientController patientController;

	@Mock
	PatientService patientService;

	private RestTemplate restTemplate = new RestTemplate();
	private String baseUrl = "http://localhost:5000/v1/";

	@Test
	public void testGetPatientListSuccess() throws URISyntaxException {
		URI uri = new URI(baseUrl + "patients");

		ResponseEntity<Patient[]> response = restTemplate.getForEntity(uri, Patient[].class);
		List<Patient> asList = Arrays.asList(response.getBody());

		Assert.assertEquals(200, response.getStatusCodeValue());
		// Assert.assertEquals(8, asList.size());
	}

	@Test
	public void testAddPatientWithFailure() throws URISyntaxException {
		URI uri = new URI(baseUrl + "patient");
		Patient patient = new Patient("Prashant", "33", "Dr Lahoti");

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Patient> request = new HttpEntity<>(patient, headers);

		try {
			restTemplate.postForEntity(uri, request, Patient.class);
		} catch (HttpClientErrorException e) {
			Assert.assertEquals(406, e.getRawStatusCode());
		}
	}

	@Test
	public void testAddPatientWithSuccess() throws URISyntaxException {
		URI uri = new URI(baseUrl + "patient");
		Patient patient = new Patient("Nishant", "33", "Dr Lahoti");

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Patient> request = new HttpEntity<>(patient, headers);

		ResponseEntity<Patient> result = restTemplate.postForEntity(uri, request, Patient.class);
		System.out.println(result.getStatusCodeValue());
		Assert.assertEquals(201, result.getStatusCodeValue());
	}

}
