package com.jobportal.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "Job_Posting")
public class JobPosting {
	@Id
	@Column(name = "posting_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int postingId;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "poster_id")
	@JsonIgnore
	private User posterId;
	
	@Column (name = "date", nullable = false)
	@JsonIgnore
	private Timestamp date;
	
	@Column(name = "title", nullable = false)
	private String title;
	
	@Column (name = "description", nullable = false)
	private String description;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "location_id")
	@JsonIgnore
	private Location locationId;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "industry_id")
	@JsonIgnore
	private Industry industryId;
	
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id")
	@JsonIgnore
	private Company companyId;
	
	@OneToMany(mappedBy="postingId", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Application> applicationList = new ArrayList<>();
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Tag> tagsList = new ArrayList<>();
	
	@Transient private int poster_id;
	@Transient private String locationName;
	@Transient private String companyName;
	@Transient private String industryName;
	
	public int getPostingId() {
		setUpFields();
		return postingId;
	}
	
	public void setUpFields() {
		this.locationName = locationId.getLocationName();
		this.industryName = industryId.getIndustryName();
		this.companyName = companyId.getCompanyName();
		this.poster_id = posterId.getUserId();
	}

	public JobPosting(User posterId, Timestamp date, String title, String description, Location locationId,
			Industry industryId, Company companyId, List<Application> applicationList, List<Tag> tagsList) {
		super();
		this.posterId = posterId;
		this.date = date;
		this.title = title;
		this.description = description;
		this.locationId = locationId;
		this.industryId = industryId;
		this.companyId = companyId;
		this.applicationList = applicationList;
		this.tagsList = tagsList;
		setUpFields();
	}

	

	public JobPosting(User posterId, Timestamp date, String title, String description, Location locationId,
			Industry industryId, Company companyId) {
		super();
		this.posterId = posterId;
		this.date = date;
		this.title = title;
		this.description = description;
		this.locationId = locationId;
		this.industryId = industryId;
		this.companyId = companyId;
		setUpFields();
	}

	@Override
	public String toString() {
		return "JobPosting [postingId=" + postingId + ", posterId=" + posterId.getFirstName() + " "
				+ posterId.getLastName() + ", date=" + date + ", title=" + title + ", description=" + description
				+ ", locationName=" + locationName + ", companyName=" + companyName + ", industryName=" + industryName
				+ "]";
	}
	
 	

//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		JobPosting other = (JobPosting) obj;
//		if (company != other.company)
//			return false;
//		if (date == null) {
//			if (other.date != null)
//				return false;
//		} else if (!date.equals(other.date))
//			return false;
//		if (description == null) {
//			if (other.description != null)
//				return false;
//		} else if (!description.equals(other.description))
//			return false;
//		if (industry != other.industry)
//			return false;
//		if (location != other.location)
//			return false;
//		if (posterId != other.posterId)
//			return false;
//		if (postingId != other.postingId)
//			return false;
//		if (title == null) {
//			if (other.title != null)
//				return false;
//		} else if (!title.equals(other.title))
//			return false;
//		return true;
//	}
}
