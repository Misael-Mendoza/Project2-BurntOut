package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{

	public Tag findByTagId(int tagId);
	
}
