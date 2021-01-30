package com.jobportal.controller;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobportal.Project2Application;
import com.jobportal.exception.JobPostingNotFoundException;
import com.jobportal.mail.ApplicationAlertMail;
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

/**
 * This class act as a RESTFul controller that exposes endpoints for websites to use involving Applications.
 * The only role of this class is prepare data for the DAO and service layer
 * @author darie
 */
@RestController
@RequestMapping(value = "/application")
@CrossOrigin(origins = "*")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
public class ApplicationController {
	
	private ApplicationService appServ;
	private ApplicationStatusService appStatusServ;
	private UserService userServ;
	private JobPostingService jpServ;
	
	/**
	 * Gets all the applications on the database.
	 * @return List of applications with OK status code if it succeeds, else it returns null and a 404 error.
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Application>> getAllJobPostings() {
		List<Application> appList = appServ.getAllApplications();
		if(appList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(appList, HttpStatus.OK);
		}
	}
	

	/**
	 * Gets one application by application id
	 * @param id - Id of the application
	 * @return application if it is found and OK status code, otherwise return a null value with a 404 code
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Application> getJobPostingBy(@PathVariable("id") int id) {
		Application app = appServ.getApplicationById(id);
		if(app == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(app, HttpStatus.OK);
		}
	}
	
	/**
	 * Get all applications belong to a certain username
	 * @param username - username to find applications by on the database.
	 * @return - all applications for this user with an OK code, other return a 404 code
	 */
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
	
	
	/**
	 * Get all applications that belong to a certain job posting by that job postings Id
	 * @param jpId - id of the job posting
	 * @return a list of applications and OK status code if it was found, return 404 not found if it can't find it
	 * @throws JobPostingNotFoundException - throw an exception if it could not be found
	 */
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
	
	/**
	 * Posts a job application with the information passed in through the linkedhashmap
	 * @param appMap - Map of the applications form.
	 * @return success message and created status code if it succeeds, otherwise return 404 and error message
	 * @throws NumberFormatException
	 * @throws JobPostingNotFoundException
	 */
	@PostMapping()
	public ResponseEntity<String> insertApplication(@RequestBody LinkedHashMap appMap) 
			throws NumberFormatException, JobPostingNotFoundException {
		User user = userServ.getUserByUsername((String)appMap.get("username"));
		ApplicationStatus appStatus = appStatusServ.getStatusByStatus("Pending");
		JobPosting jp = jpServ.findByPrimaryKey(Integer.parseInt((String)appMap.get("posting_id")));
		try {
			ApplicationAlertMail.sendApplicationAlert(jp.getPosterId().getEmail(), jp.getTitle(), String.valueOf(jp.getPostingId()));			
		}catch(Exception e) {
			e.printStackTrace();
			Project2Application.log.info("[insertApplication] Error creating application");
			return new ResponseEntity<>("Error Creating Application Alert", HttpStatus.NOT_FOUND);
		}
		Application app = new Application(
				user, 
				jp,
				new Timestamp(System.currentTimeMillis()), 
				(Blob)appMap.get("blobTest"),
				appStatus);
		appServ.insertApplication(app);
		
		Project2Application.log.info("[insertApplication] Application successfully created");
		return new ResponseEntity<> ("Application successfully created", HttpStatus.CREATED);
	}
	
	/**
	 * Deletes an application by its Id
	 * @param id - id of application to delete
	 * @return - success message if it was successfully deleted with ok status code, otherwise return 404 status code
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteApplication(@PathVariable("id") Integer id) {
		Application app = new Application();
		try {
			app = appServ.getApplicationById(id);
		} catch (Exception e) {
			Project2Application.log.info("[deleteApplication] Error retreiving application to delete");
			e.printStackTrace();
		}
		
		if(app == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			try {
				appServ.deleteApplication(app);
			} catch (Exception e) {
				Project2Application.log.info("[deleteApplication] Application could not be deleted");
				e.printStackTrace();
			}
			Project2Application.log.info("[deleteApplication] Application successfully deleted");
			return new ResponseEntity<>("Application successfully deleted", HttpStatus.OK);
		}
	}
	
	/**
	 * Changes the status of an application to Accepted
	 * @param id of application to accept
	 * @return success message with ok status code if it was accepted, if it fails return 404.
	 */
	@PostMapping("/approve/{id}")
	public ResponseEntity<String> approveApplication(@PathVariable("id") Integer id) {
		Application app = new Application();
		try {
			app = appServ.getApplicationById(id);
		} catch (Exception e) {
			Project2Application.log.info("[approveApplication] Error retreiving application to approve");
			e.printStackTrace();
		}
		
		if(app == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			try {
				app.setStatusId(appStatusServ.getStatusByStatus("Approved"));
				appServ.updateApplication(app);
			} catch (Exception e) {
				Project2Application.log.info("[approveApplication] Error approving application");
				e.printStackTrace();
			}
			Project2Application.log.info("[approveApplication] Application successfully approved");
			return new ResponseEntity<>("Application successfully approved", HttpStatus.OK);
		}
	}
	
	/**
	 * Changes the status of an application to rejected
	 * @param id of application to reject
	 * @return success message with ok status code if it was rejected, if it fails return 404.
	 */
	@PostMapping("/reject/{id}")
	public ResponseEntity<String> denyApplication(@PathVariable("id") Integer id) {
		Application app = new Application();
		try {
			app = appServ.getApplicationById(id);
		} catch (Exception e) {
			Project2Application.log.info("[denyApplication] Error retreiving application to reject");
			e.printStackTrace();
		}
		
		if(app == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			try {
				app.setStatusId(appStatusServ.getStatusByStatus("Rejected"));
				appServ.updateApplication(app);
			} catch (Exception e) {
				Project2Application.log.info("[denyApplication] Error rejecting application");
				e.printStackTrace();
			}
			Project2Application.log.info("[denyApplication] Application successfully rejected");
			return new ResponseEntity<>("Application successfully rejected", HttpStatus.OK);
		}
	}

}
