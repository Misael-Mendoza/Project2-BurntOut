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
import javax.persistence.Transient;

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
@Table(name = "application")
public class Application {
	
	@Id
	@Column(name = "application_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int applicationId;
	
	@JoinColumn(name="applicant_id")
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JsonIgnore
	private User applicantId;
	
	@JoinColumn(name="posting_id")
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JsonIgnore
	private JobPosting postingId;
	
	@Column(name = "app_date", nullable=false)
	private Timestamp appDate;
	
	@Column(name = "resume")
	private Blob resume;
	
	@JoinColumn(name="status_id")
	@ManyToOne(cascade=CascadeType.MERGE, fetch=FetchType.LAZY)
	@JsonIgnore
	private ApplicationStatus statusId;
	
	@Transient private String applicantFirstName;
	@Transient private String applicantLastName;
	@Transient private String jobPostingTitle;
	@Transient private int jobPostingId;
	@Transient private String applicationStatus;

	public int getApplicationId() {
		setUpFields();
		return applicationId;
	}
	
	public void setUpFields() {
		this.applicantFirstName = applicantId.getFirstName();
		this.applicantLastName = applicantId.getLastName();
		this.jobPostingTitle = postingId.getTitle();
		this.applicationStatus = statusId.getStatus();
		this.jobPostingId = postingId.getPostingId();
	}
	
	public Application(User applicantId, JobPosting postingId, Timestamp appDate, Blob resume,
			ApplicationStatus statusId) {
		super();
		this.applicantId = applicantId;
		this.postingId = postingId;
		this.appDate = appDate;
		this.resume = resume;
		this.statusId = statusId;
	}
	
}
