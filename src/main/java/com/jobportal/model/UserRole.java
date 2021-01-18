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
import com.jobportal.model.User;

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
	
}
