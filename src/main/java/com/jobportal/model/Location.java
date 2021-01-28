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
@Table(name = "Location")
public class Location {
	@Id
	@Column(name = "location_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int locationId;
	
	@Column(name = "location_name")
	private String locationName;
	
	@OneToMany(mappedBy = "locationId", fetch = FetchType.LAZY)
	private List <JobPosting> jobList = new ArrayList<>();

	public Location(int locationId) {
		super();
		this.locationId = locationId;
	}

	public Location(String locationName) {
		super();
		this.locationName = locationName;
	}

	public Location(int locationId, String locationName) {
		super();
		this.locationId = locationId;
		this.locationName = locationName;
	}
	
	
}