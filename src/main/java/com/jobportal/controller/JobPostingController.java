package com.jobportal.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.jobportal.service.CompanyService;
import com.jobportal.service.IndustryService;
import com.jobportal.service.JobPostingService;
import com.jobportal.service.LocationService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

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

	@GetMapping("/all")
	public ResponseEntity<List<JobPosting>> getAllJobPostings() {
		List<JobPosting> jpList = jpServ.getAllJobPostings();
		if (jpList.size() == 0) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<>(jpList, HttpStatus.OK);
		}
	}

//	@GetMapping("/title/{title}")
//	public ResponseEntity<List<JobPosting>> getJobPostingBy(@PathVariable("title") String title) {
//		List<JobPosting> jpList = jpServ.getJobPostingsByTitle(title);
//		if(jpList.size()==0) {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<>(jpList, HttpStatus.OK);
//		}
//	}
//	
//	@GetMapping("/location/{locationName}")
//	public ResponseEntity<List<JobPosting>> getJobPostingByLocation(@PathVariable("locationName") String locationName) {
//		Location location = locServ.getLocationByName(locationName);
//		List<JobPosting> jpList = jpServ.getJobPostingsByLocationId(location);
//		if(jpList.size()==0) {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<>(jpList, HttpStatus.OK);
//		}
//	}
//	
//	@GetMapping("/industry/{industryId}")
//	public ResponseEntity<List<JobPosting>> getJobPostingByIndustry(@PathVariable("industryId") int industryId) {
//		Industry industry = indServ.getIndustryById(industryId);
//		List<JobPosting> jpList = jpServ.getJobPostingsByIndustryId(industry);
//		if(jpList.size()==0) {
//			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//		} else {
//			return new ResponseEntity<>(jpList, HttpStatus.OK);
//		}
//	}
//	

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
	
	@PostMapping()
	public ResponseEntity<String> insertJobPosting(@RequestBody LinkedHashMap lhMap) {
		
		Location loc = new Location();
		Industry ind = new Industry();
		try {
			loc = locServ.getLocationByName((String)lhMap.get("location_name"));
			ind = indServ.getIndustryByName((String) lhMap.get("industry_name"));
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		
		if(loc==null) {
			loc = new Location((String)lhMap.get("location_name"));
			locServ.insertLocation(loc);
			
		}
		
		if(ind==null) {
			ind = new Industry((String) lhMap.get("industry_name"));
			indServ.insertIndustry(ind);
		}
		
		JobPosting jp = new JobPosting(new User((int)lhMap.get("poster_id")),
				new Timestamp(System.currentTimeMillis()), (String) lhMap.get("title"),
				(String) lhMap.get("description"),
				loc,
				ind,
				new Company((int)lhMap.get("company_id")));
		jpServ.insertJobPosting(jp);
		return new ResponseEntity<>("Job Posting successfully created", HttpStatus.CREATED);
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
		if (jp == null) {
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
