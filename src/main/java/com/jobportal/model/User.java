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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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
	
	@Transient String userRoleName;
	@Transient String companyName;
	
	public int getUserId() {
		setUpFields();
		return userId;
	}
	
	public User(String firstName, String lastName, String email, String username, String password, String salt,  UserRole userRole,
			Company companyId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.salt = salt;
		this.password = password;
		this.userRole = userRole;
		this.companyId = companyId;
		setUpFields();
	}

	public User(int userId) {
		super();
		this.userId = userId;
	}
	
	public User(int userId, UserRole userRole) {
		super();
		this.userId = userId;
		this.userRole = userRole;
	}
	
	public void setUpFields() {
		this.userRoleName = userRole.getUserRole();
		if(this.companyId == null) {
			this.companyName = null;
		}
		else {
			this.companyName = companyId.getCompanyName();
		}
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", username=" + username + ", password=" + password + ", userRoleName=" + userRoleName
				+ ", companyName=" + companyName + "]";
	}

	
	
	
	

}
