package com.jobportal.model;

import java.sql.Date;
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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Company")
public class Company {
	@Id
	@Column(name = "company_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int companyId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@OneToMany(mappedBy = "companyId", fetch = FetchType.LAZY)
	private List <JobPosting> jobList = new ArrayList<>();
	
	@OneToMany(mappedBy = "companyId", fetch = FetchType.LAZY)
	private List<User> UserList = new ArrayList<>();

	public Company(int companyId) {
		super();
		this.companyId = companyId;
	}
	
	

}
