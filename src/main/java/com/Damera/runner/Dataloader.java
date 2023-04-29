package com.Damera.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.Damera.entities.Courses;
import com.Damera.entities.EnquiryStatus;
import com.Damera.repository.CoursesRepository;
import com.Damera.repository.EnquiryStatusRepo;

@Component
public class Dataloader implements ApplicationRunner {
	
	@Autowired
	private CoursesRepository courseRepo;
	
	@Autowired
	private EnquiryStatusRepo enquiryRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		courseRepo.deleteAll();
		
		Courses c1 = new Courses();
		c1.setCourseName("Java Fullstack");
		
		Courses c2 = new Courses();
		c2.setCourseName("Python Fullstack");
		
		Courses c3 = new Courses();
		c3.setCourseName("Devops");
		
		Courses c4 = new Courses();
		c4.setCourseName("Aws");
		
		courseRepo.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		enquiryRepo.deleteAll();
		
		EnquiryStatus s1 = new EnquiryStatus();
		s1.setStatusName("New");
		
		EnquiryStatus s2 = new EnquiryStatus();
		s2.setStatusName("Enrolled");
		
		EnquiryStatus s3 = new EnquiryStatus();
		s3.setStatusName("Lost");
		
		enquiryRepo.saveAll(Arrays.asList(s1,s2,s3));
		
	}

}
