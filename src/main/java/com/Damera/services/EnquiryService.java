package com.Damera.services;

import java.util.List;

import com.Damera.binding.DashboardResponse;
import com.Damera.binding.EnquiryForm;
import com.Damera.binding.EnquirySearchCriteria;
import com.Damera.entities.StudentEnqEntity;

public interface EnquiryService {
	
	List<String> getCourseNames();
	
	List<String> getEnquiryStatus();
	
	DashboardResponse getDashboardData(Integer userId);
	
	Boolean saveEnquiry(EnquiryForm form,Integer userId);
	
	List<StudentEnqEntity> getEnquiries(Integer userId,EnquirySearchCriteria search);
	
	StudentEnqEntity getStudentEnq(Integer eId);

}
