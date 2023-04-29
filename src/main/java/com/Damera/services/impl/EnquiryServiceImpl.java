package com.Damera.services.impl;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.Damera.binding.DashboardResponse;
import com.Damera.binding.EnquiryForm;
import com.Damera.binding.EnquirySearchCriteria;
import com.Damera.entities.StudentEnqEntity;
import com.Damera.entities.UserDtlsEntity;
import com.Damera.repository.CoursesRepository;
import com.Damera.repository.EnquiryStatusRepo;
import com.Damera.repository.StudentEnquiriesRepository;
import com.Damera.repository.UserRepository;
import com.Damera.services.EnquiryService;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private StudentEnquiriesRepository studentRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CoursesRepository courseRepo;

	@Autowired
	private EnquiryStatusRepo statusRepo;

	@Override
	public Boolean saveEnquiry(EnquiryForm form, Integer userId) {

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		UserDtlsEntity user = findById.get();

		if (form.getEId() != null) {
			
			StudentEnqEntity entity = studentRepo.findById(form.getEId()).get();
			entity.setClassMode(form.getClassMode());
			entity.setCourseName(form.getCourseName());
			entity.setEnquiryStatus(form.getEnquiryStatus());
			entity.setPhno(form.getPhno());
			entity.setStudentName(form.getStudentName());
			entity.setUser(user);
			
			entity.setUser(user);

			studentRepo.save(entity);

			return true;
		}

		StudentEnqEntity entity = new StudentEnqEntity();

		BeanUtils.copyProperties(form, entity);

		entity.setUser(user);

		studentRepo.save(entity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquiries(Integer userId, EnquirySearchCriteria search) {

		StudentEnqEntity entity = new StudentEnqEntity();

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		UserDtlsEntity user = findById.get();

		BeanUtils.copyProperties(search, entity);

		entity.setUser(user);

		Example<StudentEnqEntity> example = Example.of(entity);
		List<StudentEnqEntity> list = studentRepo.findAll(example);

		return list;
	}

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		Optional<UserDtlsEntity> findById = userRepo.findById(userId);
		UserDtlsEntity user = findById.get();

		List<StudentEnqEntity> enquiries = user.getLstOfEnquiries();
		long enrolledCount = enquiries.stream().filter(e -> e.getEnquiryStatus().equalsIgnoreCase("enrolled")).count();

		long lostCount = enquiries.stream().filter(e -> e.getEnquiryStatus().equalsIgnoreCase("lost")).count();

		long totalCount = enquiries.stream().count();

		DashboardResponse data = new DashboardResponse();

		data.setEnrolledEnquiries((int) enrolledCount);
		data.setLostEnquiries((int) lostCount);
		data.setNoOfEnquiries((int) totalCount);

		return data;
	}

	@Override
	public List<String> getCourseNames() {
		List<String> courses = courseRepo.getCourses();
		return courses;
	}

	@Override
	public List<String> getEnquiryStatus() {
		List<String> statusNames = statusRepo.getStatusNames();
		return statusNames;
	}

	@Override
	public StudentEnqEntity getStudentEnq(Integer eId) {
		Optional<StudentEnqEntity> findById = studentRepo.findById(eId);
		StudentEnqEntity entity = findById.get();
		return entity;
	}

}
