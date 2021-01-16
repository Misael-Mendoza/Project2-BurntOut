package com.jobportal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
