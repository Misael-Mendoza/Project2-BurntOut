package com.jobportal.controller;

import java.sql.Timestamp;
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
import com.jobportal.model.Company;
import com.jobportal.model.Industry;
import com.jobportal.model.JobPosting;
import com.jobportal.model.Location;
import com.jobportal.model.User;
import com.jobportal.service.JobPostingService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/jobpostings")
@AllArgsConstructor(onConstructor=@___(@Autowired))
@NoArgsConstructor
public class JobPostingController {
	
	private JobPostingService jpServ;
	
	@GetMapping("/all")
	public ResponseEntity<List<JobPosting>> getAllJobPostings(){
		List<JobPosting> jpList = jpServ.getAllJobPostings();
		if(jpList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/title/{title}")
	public ResponseEntity<List<JobPosting>> getJobPostingBy(@PathVariable("title") String title) {
		List<JobPosting> jpList = jpServ.getJobPostingsByTitle(title);
		if(jpList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/location/{location}")
	public ResponseEntity<List<JobPosting>> getJobPostingBy(@PathVariable("location") Location locationId) {
		List<JobPosting> jpList = jpServ.getJobPostingsByLocationId(locationId);
		if(jpList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/industry/{industry}")
	public ResponseEntity<List<JobPosting>> getJobPostingBy(@PathVariable("industry") Industry industryId) {
		List<JobPosting> jpList = jpServ.getJobPostingsByIndustryId(industryId);
		if(jpList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}
	
	@GetMapping("/company/{company}")
	public ResponseEntity<List<JobPosting>> getJobPostingBy(@PathVariable("company") Company companyId) {
		List<JobPosting> jpList = jpServ.getJobPostingsByCompanyId(companyId);
		if(jpList.size()==0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}
	
	@PostMapping()
	public ResponseEntity<String> insertJobPosting(@RequestBody LinkedHashMap lhMap) {
		JobPosting jp = new JobPosting((User)lhMap.get("posterId"), 
				(Timestamp) lhMap.get("date"),
				(String)lhMap.get("title"), 
				(String)lhMap.get("description"),
				(Location)lhMap.get("locationId"),
				(Industry)lhMap.get("industryId"),
				(Company)lhMap.get("companyId")
				);
		jpServ.insertJobPosting(jp);
		return new ResponseEntity<> ("Job Posting successfully created", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJobPosting(@PathVariable("id") Integer id) {
		JobPosting jp = new JobPosting();
		try {
			jp = jpServ.findByPrimaryKey(id);
		} catch (JobPostingNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(jp == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			try {
				jpServ.deleteJobPosting(jp);
			} catch (JobPostingNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<>("Job Posting successfully deleted", HttpStatus.OK);
		}
	}
}
