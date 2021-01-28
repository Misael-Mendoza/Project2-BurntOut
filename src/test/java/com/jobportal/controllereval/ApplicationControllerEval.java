package com.jobportal.controllereval;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jobportal.controller.ApplicationController;
import com.jobportal.model.Application;
import com.jobportal.model.ApplicationStatus;
import com.jobportal.model.JobPosting;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.ApplicationStatusService;
import com.jobportal.service.JobPostingService;
import com.jobportal.service.UserService;

@SpringBootTest
public class ApplicationControllerEval {
	
	@Mock
	private ApplicationService appServ;
	@Mock private ApplicationStatusService appStatusServ;
	@Mock private UserService userServ;
	@Mock private JobPostingService jpServ;
	
	@InjectMocks
	private ApplicationController appCon;
	
	@Mock private User user;
	@Mock private UserRole userRole;
	@Mock private ApplicationStatus appStat;
	@Mock private Blob blob;
	@Mock private JobPosting jobPost;
	@Mock private Timestamp time;
	
	private List<Application> appList = new ArrayList<>();
	private Application app;
	private MockMvc mock;

	@BeforeEach
	public void setUp() throws Exception {
		mock = MockMvcBuilders.standaloneSetup(appCon).build();
		app = new Application(user, jobPost, time, blob, appStat);
		appList.add(app);
		
		doNothing().when(appServ).insertApplication(app);
		doNothing().when(appServ).updateApplication(app);
		doNothing().when(appServ).deleteApplication(app);
		
		when(appServ.getAllApplications()).thenReturn(appList);
		when(appServ.getApplicationById(anyInt())).thenReturn(app);
		when(userServ.getUserByUsername(anyString())).thenReturn(user);
		when(appServ.getApplicationByApplicantId(user)).thenReturn(appList);
		when(jpServ.findByPrimaryKey(anyInt())).thenReturn(jobPost);
		when(appServ.getApplicationByPostingId(jobPost)).thenReturn(appList);
		when(appStatusServ.getStatusByStatus(anyString())).thenReturn(appStat);
	}
	
	@Test 
	public void testGetAllAppsTest() throws Exception {
		mock.perform(get("/application/all"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].applicationId", is(appList.get(0).getApplicationId())));
	}
	
	@Test 
	public void testGetAppByIdTest() throws Exception {
		mock.perform(get("/application/{id}", app.getApplicationId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.applicationId", is(app.getApplicationId())));
	}
	
	@Test 
	public void testGetAppByUser() throws Exception {
		mock.perform(get("/application/user/{username}", "username"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].applicationId", is(appList.get(0).getApplicationId())));
	}
	
	@Test 
	public void testGetAppByPostingId() throws Exception {
		mock.perform(get("/application/jobposting/{id}", app.getPostingId().getPostingId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$[0].applicationId", is(appList.get(0).getApplicationId())));
	}
	
//	@Test 
//	public void testPostApplication() throws Exception {
//		mock.perform(post("/application/")
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(new ObjectMapper().writeValueAsString(appMap)))
//		.andExpect(status().isCreated())
//		.andExpect(jsonPath("$").value("Application successfully created"));
//	}
	
	@Test 
	public void testDeleteApplication() throws Exception {
		mock.perform(delete("/application/{id}", app.getApplicationId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").value("Application successfully deleted"));
	}
	

	@Test 
	public void testApproveApplication() throws Exception {
		mock.perform(post("/application/approve/{id}", app.getApplicationId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").value("Application successfully updated"));
	}
	
	@Test 
	public void testDenyApplication() throws Exception {
		mock.perform(post("/application/reject/{id}", app.getApplicationId()))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").value("Application successfully updated"));
	}
	
	
//
//	@Test 
//	public void postFoodTest() throws Exception {
//		mock.perform(post("/foods/validate")
//		.contentType(MediaType.APPLICATION_JSON)
//		.content(new ObjectMapper().writeValueAsString(food)))
//		.andExpect(status().isCreated())
//		.andExpect(jsonPath("$").value("Resource created"));
//	}
}
