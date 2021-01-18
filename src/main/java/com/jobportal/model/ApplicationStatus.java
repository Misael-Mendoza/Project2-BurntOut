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
import com.jobportal.model.Application;

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
@Table(name = "application_status")
public class ApplicationStatus {

		@Id
		@Column(name = "status_id")
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int statusID;

		@Column(name = "status_name", nullable=false)
		private String status;

		
		@OneToMany(mappedBy = "statusId", fetch = FetchType.LAZY)
		private List<Application> appList = new ArrayList<Application>();
		
		
}
