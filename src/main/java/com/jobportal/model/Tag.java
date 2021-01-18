package com.jobportal.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tags")
public class Tag {
		
		@Id
		@Column(name = "tag_id")
		@GeneratedValue(strategy = GenerationType.AUTO)
		private int userRoleID;
		
		@Column(name = "tag_name")
		private String userRole;

		public String getUserRole() {
			return userRole;
		}

		public void setUserRole(String userRole) {
			this.userRole = userRole;
		}

		public int getUserRoleID() {
			return userRoleID;
		}

		@Override
		public String toString() {
			return "Tags [userRoleID=" + userRoleID + ", userRole=" + userRole + "]";
		}

}
