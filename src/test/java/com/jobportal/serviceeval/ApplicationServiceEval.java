package com.jobportal.serviceeval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobportal.model.Application;
import com.jobportal.model.ApplicationStatus;
import com.jobportal.model.JobPosting;
import com.jobportal.model.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.service.ApplicationService;

@SpringBootTest
public class ApplicationServiceEval {
	
	@Mock
	private ApplicationRepository appRepo;

	@InjectMocks
	private ApplicationService appServ;

	@Mock
	private User user;

	@Mock
	private User user1;

	@Mock
	private JobPosting jobPost;
	
	@Mock
	private JobPosting jobPost1;

	@Mock
	private Timestamp time;

	@Mock
	private Blob blob;

	@Mock
	private ApplicationStatus appStatus;

	private Application app;
	private List<Application> appList = new ArrayList<>();

	@BeforeEach
	public void setUp() throws Exception {

		app = new Application();
		when(appServ.getAllApplications()).thenReturn(appList);
		when(appServ.getApplicationById(0)).thenReturn(app);
		when(appServ.getApplicationById(1)).thenReturn(null);
		when(appServ.getApplicationByApplicantId(user)).thenReturn(appList);
		when(appServ.getApplicationByApplicantId(user1)).thenReturn(null);
		when(appServ.getApplicationByPostingId(jobPost)).thenReturn(appList);
		when(appServ.getApplicationByPostingId(jobPost1)).thenReturn(null);
	}

	// --------- Tests for getAllApplications()
	@Test
	public void testFindAllSuccess() {
		assertEquals(appServ.getAllApplications(), appList);
	}

	public void testFindAllFailure() {
		appList = null;
		assertEquals(appServ.getAllApplications(), null);
	}
	
	// --------- Tests for getApplicationById()
	@Test
	public void testGetApplicationByIdSuccess() {
		assertEquals(appServ.getApplicationById(0), app);
	}
	
	@Test
	public void testGetApplicationByIdFailure() {
		assertEquals(appServ.getApplicationById(1), null);
	}
	
	// --------- Tests for getApplicationByApplicantId()
	@Test
	public void testGetApplicationByApplicantIdSuccess() {
		assertEquals(appServ.getApplicationByApplicantId(user), appList);
	}
	
	@Test
	public void testGetApplicationByApplicantIdFailure() {
		assertEquals(appServ.getApplicationByApplicantId(user1), null);
	}
	
	// --------- Tests for getApplicationByJobPostingId()
	@Test
	public void testGetApplicationByPostingIdSuccess() {
		assertEquals(appServ.getApplicationByPostingId(jobPost), appList);
	}
	
	@Test
	public void testGetApplicationByPostingIdFailure() {
		assertEquals(appServ.getApplicationByPostingId(jobPost1), null);
	}

}
