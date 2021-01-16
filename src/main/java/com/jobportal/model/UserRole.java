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

import com.jobportal.model.User;

@Entity
@Table(name = "user_roles")
public class UserRole {
	
	@Id
	@Column(name = "user_role_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userRoleID;
	
	@Column(name = "user_role")
	private String userRole;
	
	@OneToMany(mappedBy="userRole", fetch=FetchType.LAZY)
	private List<User> userList = new ArrayList<User>();
	
	public UserRole() {
		// TODO Auto-generated constructor stub
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

//	public List<User> getUserList() {
//		return userList;
//	}
//
//	public void setUserList(List<User> userList) {
//		this.userList = userList;
//	}

	public int getUserRoleID() {
		return userRoleID;
	}

//	@Override
//	public String toString() {
//		return "UserRole [userRoleID=" + userRoleID + ", userRole=" + userRole + ", userList=" + userList + "]";
//	}
//	
	
}
