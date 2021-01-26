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

	public Industry(int industryId) {
		super();
		this.industryId = industryId;
	}

	public Industry(String industryName) {
		super();
		this.industryName = industryName;
	}
	
	
	
	
}
