package com.jobportal.model;

import java.sql.Date;

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

@Entity
@Table(name="BLOG")
public class Blog {
	
	@Id
	@Column(name="blog_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int blogId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="owner_id")
	private User ownerId;
	
	@Column(name="blog_title")
	private String blogTitle;
	
	@Column(name="blog_date")
	private Date date;
	
	@Column(name="blog_content")
	private String blogContent;

	
}
