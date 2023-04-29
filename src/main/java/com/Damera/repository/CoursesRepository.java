package com.Damera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Damera.entities.Courses;

public interface CoursesRepository extends JpaRepository<Courses, Integer> {
	
	@Query("select distinct(courseName) from Courses")
	List<String> getCourses();

}
