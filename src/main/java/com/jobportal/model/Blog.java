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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@Table(name="BLOG")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Blog {
	
	@Id
	@Column(name="blog_id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int blogId;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="owner_id")
	@JsonIgnore
	private User ownerId;
	
	@Column(name="blog_title")
	private String blogTitle;
	
	@Column(name="blog_date")
	private Date date;
	
	@Column(name="blog_content")
	private String blogContent;

	public Blog(User ownerId, String blogTitle, Date date, String blogContent) {
		super();
		this.ownerId = ownerId;
		this.blogTitle = blogTitle;
		this.date = date;
		this.blogContent = blogContent;
	}

	
}
