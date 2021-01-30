package com.jobportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobportal.model.Tag;

/**
 * This interface acts as a way to query the database, it's only role is to do queries involving tags.
 * It uses Spring Data to construct its queries
 * @author darie
 */
public interface TagRepository extends JpaRepository<Tag, Integer>{

	public Tag findByTagId(int tagId);
	
}
