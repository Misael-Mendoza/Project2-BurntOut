package com.jobportal.controller;

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
import com.jobportal.model.Company;
import com.jobportal.model.Industry;
import com.jobportal.model.JobPosting;
import com.jobportal.model.Location;
import com.jobportal.service.CompanyService;
import com.jobportal.service.IndustryService;
import com.jobportal.service.JobPostingService;
import com.jobportal.service.LocationService;
import com.jobportal.service.UserService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * This class act as a RESTFul controller that exposes endpoints for websites to use involving Job Postings.
 * The only role of this class is prepare data for the DAO and service layer
 * @author darie
 */
@RestController
@RequestMapping(value = "/jobpostings")
@CrossOrigin(origins = "*")
@AllArgsConstructor(onConstructor = @___(@Autowired))
@NoArgsConstructor
public class JobPostingController {

	private JobPostingService jpServ;
	private CompanyService compServ;
	private LocationService locServ;
	private IndustryService indServ;
	private UserService uServ;

	/**
	 * Returns all job postings
	 * @return  a list of job postings with an OK status code or null if it couldn't get any with a 404 status code. 
	 */
	@GetMapping("/all")
	public ResponseEntity<List<JobPosting>> getAllJobPostings() {
		List<JobPosting> jpList = jpServ.getAllJobPostings();
		if (jpList.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}

	
	/**
	 * Passes a company name and returns a list of job postings by that company
	 * @param companyName - Company to look for in the job postings returned
	 * @return list of job postings by company with OK status code, if it doesn't find any returns null and 404 code.
	 */
	@GetMapping("/company/name/{companyName}")
	public ResponseEntity<List<JobPosting>> getJobPostingByCompanyName(
			@PathVariable("companyName") String companyName) {
		Company company = compServ.getCompanyByName(companyName);
		List<JobPosting> jpList = jpServ.getJobPostingsByCompanyId(company);
		if (jpList.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}

	/**
	 * Passes a company ID and returns a list of job postings by that company
	 * @param companyId - Company id of company matching job postings
	 * @return list of job postings by company with OK status code, if it doesn't find any returns null and 404 code.
	 */
	@GetMapping("/company/{companyId}")
	public ResponseEntity<List<JobPosting>> getJobPostingByCompany(@PathVariable("companyId") int companyId) {
		Company company = compServ.getCompanyById(companyId);
		List<JobPosting> jpList = jpServ.getJobPostingsByCompanyId(company);
		if (jpList.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}
	
	
	/**
	 * Gets one Job posting by posting id
	 * @param id - Id of the job posting
	 * @return job posting if it is found and ACCEPTED code, otherwise return a null value with a 404 code
	 */
	@GetMapping("/posting-id/{id}")
	public ResponseEntity<JobPosting> getJobPostingById(@PathVariable("id") int id) {
		JobPosting jp = jpServ.getJobPostingByPostingId(id);
		if(jp!=null) {
			return new ResponseEntity<>(jp, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	
	/**
	 * Attempts to insert a new job posting with the form information is is passed in the body
	 * @param lhMap - LinkedHashedMap of the form values for creating a job posting - poster_id, title, description, location_name, industry_name, company_name)
	 * @return created status code with a success message, else returns a 500 error.
	 */
	@PostMapping()
	public ResponseEntity<String> insertJobPosting(@RequestBody LinkedHashMap lhMap) {
		
		Location loc = new Location();
		Industry ind = new Industry();
		//Check to see if the location and industry already exist
		try {
			loc = locServ.getLocationByName((String)lhMap.get("location_name"));
			ind = indServ.getIndustryByName((String) lhMap.get("industry_name"));
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		//If location doesn't exist, create it
		if(loc==null) {
			loc = new Location((String)lhMap.get("location_name"));
			locServ.insertLocation(loc);
		}
		
		//If industry doesn't exist, create it
		if(ind==null) {
			ind = new Industry((String) lhMap.get("industry_name"));
			indServ.insertIndustry(ind);
		}
		
		//Create and insert the job posting item
		JobPosting jp = new JobPosting(uServ.getUserByUserId((int)lhMap.get("poster_id")),
				new Timestamp(System.currentTimeMillis()), (String) lhMap.get("title"),
				(String) lhMap.get("description"),
				loc,
				ind,
				new Company((int)lhMap.get("company_id")));
		jpServ.insertJobPosting(jp);
		Project2Application.log.info("[insertJobPosting] Job Posting successfully created");
		return new ResponseEntity<>("Job Posting successfully created", HttpStatus.CREATED);
	}


	/**
	 * Deletes a job posting with the job posting ID it is passed
	 * @param id - id of job posting to delete
	 * @return success message upon deletion with 404 status code, otherwise return 404 status code with null
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJobPosting(@PathVariable("id") Integer id) {
		JobPosting jp = new JobPosting();
		try {
			jp = jpServ.findByPrimaryKey(id);
		} catch (JobPostingNotFoundException e) {
			
			e.printStackTrace();
		}
		if (jp == null) {
			
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			try {
				jpServ.deleteJobPosting(jp);
			} catch (JobPostingNotFoundException e) {
				
				e.printStackTrace();
			}
			Project2Application.log.info("[deleteJobPosting] Job Posting successfully deleted");
			return new ResponseEntity<>("Job Posting successfully deleted", HttpStatus.OK);
		}
	}

	/**
	 * Gets a company name and returns its ID in the database
	 * @param companyName - name of the company
	 * @return the id if it is found with an OK Status code, otherwise return 404 not found.
	 */
	@GetMapping("/company-id/{companyName}")
	public ResponseEntity<Integer> getCompanyIdByCompanyName(
			@PathVariable("companyName") String companyName) {
		Company company = compServ.getCompanyByName(companyName);
		if (company==null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			
		} else {
			return new ResponseEntity<>(company.getCompanyId(), HttpStatus.OK);
		}
	}
}
