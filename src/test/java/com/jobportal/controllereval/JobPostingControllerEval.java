package com.jobportal.controllereval;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.anyString;


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

import com.jobportal.controller.JobPostingController;
import com.jobportal.model.Company;
import com.jobportal.model.Industry;
import com.jobportal.model.JobPosting;
import com.jobportal.model.Location;
import com.jobportal.model.User;
import com.jobportal.model.UserRole;
import com.jobportal.service.CompanyService;
import com.jobportal.service.IndustryService;
import com.jobportal.service.JobPostingService;
import com.jobportal.service.LocationService;

@SpringBootTest
public class JobPostingControllerEval {

	@Mock
	private JobPostingService jpServ;
	@Mock
	private CompanyService compServ;
	@Mock
	private LocationService locServ;
	@Mock
	private IndustryService indServ;
	
	
	@InjectMocks
	private JobPostingController jpCon;
	
	private MockMvc mock;
	
	
	private JobPosting job;
	private Company comp;
	private List<JobPosting> jobList = new ArrayList<JobPosting>();
	

	@BeforeEach
	public void setUp() throws Exception{
		UserRole userRole = new UserRole(1);
		User user = new User(1, userRole);
		Timestamp time = new Timestamp(System.currentTimeMillis());
		Location loc = new Location(1,"Miami");
		Industry ind = new Industry(1, "IT");
	    comp = new Company(1, "Revature");
		
		
		job = new JobPosting(user, time, "Title", "Description", loc, ind, comp);
		jobList.add(job);
		mock = MockMvcBuilders.standaloneSetup(jpCon).build();
		doNothing().when(jpServ).insertJobPosting(job);
		doNothing().when(jpServ).deleteJobPosting(job);
		when(jpServ.getAllJobPostings()).thenReturn(jobList);
		when(jpServ.findByPrimaryKey(job.getPostingId())).thenReturn(job);
		when(jpServ.getJobPostingsByCompanyId(comp)).thenReturn(jobList);
		doNothing().when(jpServ).deleteJobPosting(job);
		when(locServ.getLocationByName(anyString())).thenReturn(loc);
		when(indServ.getIndustryByName(anyString())).thenReturn(ind);
		when(compServ.getCompanyByName(anyString())).thenReturn(comp);
		when(compServ.getCompanyById(comp.getCompanyId())).thenReturn(comp);
		when(jpServ.getJobPostingByPostingId(job.getPostingId())).thenReturn(job);
	}
	
	@Test
	public void getAllJobs() throws Exception{
		mock.perform(get("/jobpostings/all")).andExpect(status().isOk());
	}
	
	@Test 
	public void deleteJobPostingTest() throws Exception{
		mock.perform(delete("/jobpostings/{id}",job.getPostingId())).andExpect(status().isOk()).andExpect(jsonPath("$").value("Job Posting successfully deleted"));
	}
	
	@Test 
	public void testGetJobPostingByCompanyName() throws Exception{
		mock.perform(get("/jobpostings/company/name/{companyName}", comp.getCompanyName())).andExpect(status().isOk());	
	}
	
	@Test 
	public void testGetJobPostingByCompanyId() throws Exception{
		mock.perform(get("/jobpostings/company/{companyId}", comp.getCompanyId())).andExpect(status().isOk());	
	}
	
	@Test
	public void testGetJobPostingById() throws Exception{
		mock.perform(get("/jobpostings/posting-id/{id}", job.getPostingId())).andExpect(status().isAccepted());
	}
	
	//Need to send as JSON that has the values it is expecting. 
//	@Test
//	public void postJobPostingTest() throws Exception{
//		mock.perform(post("/jobpostings/").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().writeValueAsString(job)))
//		.andExpect(status().isCreated()).andExpect(jsonPath("$").value("Job Posting successfully created"));
//	}
	
}
