package com.jobportal.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Industry")
public class Industry {
	@Id
	@Column(name = "industry_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int industryId;
	
	@Column(name = "industry_name")
	private String industryName;
	
	@OneToMany(mappedBy = "industryId", fetch = FetchType.LAZY)
	private List <JobPosting> jobList = new ArrayList<>();
	

}
