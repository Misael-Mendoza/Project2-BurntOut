package com.jobportal.controller;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.exception.JobPostingNotFoundException;
import com.jobportal.model.Application;
import com.jobportal.model.ApplicationStatus;
import com.jobportal.model.JobPosting;
import com.jobportal.model.User;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.ApplicationStatusService;
import com.jobportal.service.JobPostingService;
import com.jobportal.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/application")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class ApplicationController {
	
	private ApplicationService appServ;
	private ApplicationStatusService appStatusServ;
	private UserService userServ;
	private JobPostingService jpServ;
	
	@GetMapping("/all")
	public ResponseEntity<List<Application>> getAllJobPostings() {
		List<Application> appList = appServ.getAllApplications();
		if(appList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(appList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Application> getJobPostingBy(@PathVariable("id") int id) {
		Application app = appServ.getApplicationById(id);
		if(app == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(app, HttpStatus.OK);
		}
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<List<Application>> getApplicationsByUser(@PathVariable("username") String username) {
		User user = userServ.getUserByUsername(username);
		List<Application> appList = appServ.getApplicationByApplicantId(user);
		
		if(appList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(appList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/jobposting/{id}")
	public ResponseEntity<List<Application>> getApplicationsByJobPosting(@PathVariable("id") int jpId) throws JobPostingNotFoundException {
		JobPosting jp = jpServ.findByPrimaryKey(jpId);
		List<Application> appList = appServ.getApplicationByPostingId(jp);
		
		if(appList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(appList, HttpStatus.OK);
		}
	}
	
	@PostMapping()
	public ResponseEntity<String> insertApplication(@RequestBody LinkedHashMap appMap) throws NumberFormatException, JobPostingNotFoundException {
		User user = userServ.getUserByUsername((String)appMap.get("username"));
		JobPosting jp = jpServ.findByPrimaryKey(Integer.parseInt((String)appMap.get("postingId")));
		ApplicationStatus appStatus = appStatusServ.getStatusByStatus("Pending");
		
		Application app = new Application(
				user, 
				jp ,
				new Timestamp(System.currentTimeMillis()), 
				(Blob)appMap.get("blobTest"),
				appStatus);
		appServ.insertApplication(app);
		
		return new ResponseEntity<> ("Job Posting successfully created", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplication(@PathVariable("id") Integer id) {
		Application app = new Application();
		try {
			app = appServ.getApplicationById(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(app == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			try {
				appServ.deleteApplication(app);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<>("Application successfully deleted", HttpStatus.OK);
		}
	}

}
