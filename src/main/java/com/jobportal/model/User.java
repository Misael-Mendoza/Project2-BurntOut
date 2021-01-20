package com.jobportal.model;

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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
@Table(name="users")
public class User {
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int userId;
	
	@Column(name="first_name", nullable=false)
	private String firstName;

	@Column(name="last_name", nullable=false)
	private String lastName;
	
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	@Column(name="username", unique=true, nullable=false)
	private String username;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column (name = "salt")
	private String salt;
	
	@JoinColumn(name="role_id")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private UserRole userRole;
	
	@JoinColumn(name="company_id")
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private Company companyId;
	
	@OneToMany(mappedBy="ownerId", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Blog> blogList = new ArrayList<>();
	
	@OneToMany(mappedBy="applicantId", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<Application> applicationList = new ArrayList<>();
	
	@OneToMany(mappedBy="posterId", fetch=FetchType.LAZY)
	@JsonIgnore
	private List<JobPosting> jobPostingList = new ArrayList<>();
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JsonIgnore
	private List<User> followerList = new ArrayList<>();

	public User(String firstName, String lastName, String email, String username, String password, String salt, UserRole userRole,
			Company companyId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.userRole = userRole;
		this.companyId = companyId;
	}

}
