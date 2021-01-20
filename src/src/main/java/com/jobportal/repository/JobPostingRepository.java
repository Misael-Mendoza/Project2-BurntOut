package com.jobportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jobportal.model.Company;
import com.jobportal.model.Industry;
import com.jobportal.model.JobPosting;
import com.jobportal.model.Location;

@Repository
public interface JobPostingRepository extends JpaRepository <JobPosting, Integer> {
	public List<JobPosting> findAll();
	public List<JobPosting> findByTitle(String title);
	public List<JobPosting> findByLocationId(Location locationId);
	public List<JobPosting> findByCompanyId(Company companyId);
	public List<JobPosting> findByIndustryId(Industry industryId);
	public JobPosting findByPostingId(int id);
	//public List<JobPosting> findByTagIn(List<Tag> tagsList);

}
