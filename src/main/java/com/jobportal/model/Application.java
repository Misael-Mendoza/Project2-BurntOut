package com.jobportal.model;

import java.sql.Blob;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "application")
public class Application {
	
	@Id
	@Column(name = "application_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int applicationId;

	
	@JoinColumn(name="applicant_id")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private User applicantId;
	
	

	@JoinColumn(name="posting_id")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private JobPosting postingId;
	
	
	@Column(name = "app_date", nullable=false)
	private Timestamp appDate;
	@Column(name = "resume")
	private Blob resume;

	
	@JoinColumn(name="status_id")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private ApplicationStatus statusId;

}
