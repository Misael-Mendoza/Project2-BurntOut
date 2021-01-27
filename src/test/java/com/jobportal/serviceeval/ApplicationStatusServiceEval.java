package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.Application;
import com.jobportal.model.ApplicationStatus;
import com.jobportal.repository.ApplicationStatusRepository;
import com.jobportal.service.ApplicationStatusService;

@SpringBootTest
public class ApplicationStatusServiceEval {
	
	@Mock
	private ApplicationStatusRepository appSRepo;

	@InjectMocks
	private ApplicationStatusService appSServ;

	private ApplicationStatus appStat;

	@BeforeEach
	public void setUp() throws Exception {

		appStat = new ApplicationStatus();
		when(appSServ.getStatusById(0)).thenReturn(appStat);
		when(appSServ.getStatusById(1)).thenReturn(null);
		when(appSServ.getStatusByStatus("Pending")).thenReturn(appStat);
		when(appSServ.getStatusByStatus("No status!")).thenReturn(null);
	}

	// --------- Tests for getStatusById()
	@Test
	public void testgetStatusByIdSuccess() {
		assertEquals(appSServ.getStatusById(0), appStat);
	}

	public void testgetStatusByIdFailure() {
		assertEquals(appSServ.getStatusById(1), null);
	}
	
	// --------- Tests for getStatusByStatus()
	@Test
	public void testGetStatusByStatusSuccess() {
		assertEquals(appSServ.getStatusByStatus("Pending"), appStat);
	}
	
	@Test
	public void testGetStatusByStatusFailure() {
		assertEquals(appSServ.getStatusByStatus("No status!"), null);
	}
	
}
