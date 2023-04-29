package com.Damera.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Damera.binding.DashboardResponse;
import com.Damera.binding.EnquiryForm;
import com.Damera.binding.EnquirySearchCriteria;
import com.Damera.entities.StudentEnqEntity;
import com.Damera.services.EnquiryService;

@Controller
public class EnquiryController {

	@Autowired
	private HttpSession session;

	@Autowired
	private EnquiryService service;
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();;
		return "redirect:/login";
	}

	
	@GetMapping("/dashboard")
	public String loadDashboard(Model model) {

		Integer userId = (Integer) session.getAttribute("userID");

		DashboardResponse data = service.getDashboardData(userId);

		model.addAttribute("enquiries", "Total Enquiries : " + data.getNoOfEnquiries());
		model.addAttribute("enrolled", "Enrolled : " + data.getEnrolledEnquiries());
		model.addAttribute("lost", "Lost : " + data.getLostEnquiries());

		return "dashboard";
	}

	
	@GetMapping("/enquiry")
	public String loadAddEnquiry(Model model) {

		getData(model);
		model.addAttribute("Enquiry", new EnquiryForm());

		return "add-enquiry";
	}

	
	private void getData(Model model) {
		List<String> courseNames = service.getCourseNames();

		List<String> enquiryStatus = service.getEnquiryStatus();

		model.addAttribute("courseNames", courseNames);
		model.addAttribute("statusNames", enquiryStatus);
	}

	
	@PostMapping("/enquiry")
	public String addEnquiry(@Validated @ModelAttribute("Enquiry") EnquiryForm form, Model model) {

		Integer userId = (Integer) session.getAttribute("userID");
		Boolean status = service.saveEnquiry(form, userId);

		if (status) {
			model.addAttribute("success", "Enquiry saved");
		} else {
			model.addAttribute("error", "Error occured while saving");

		}

		return "add-enquiry";
	}

	
	@GetMapping("/enquiries")
	public String loadEnquiryDetails(Model model) {

		Integer userId = (Integer) session.getAttribute("userID");
		List<StudentEnqEntity> enquiries = service.getEnquiries(userId, new EnquirySearchCriteria());

		getData(model);

		model.addAttribute("enquiries", enquiries);

		return "view-enquiries";
	}

	
	@GetMapping("/edit")
	public String editEnquiry(@RequestParam("id") Integer eId, Model model) {

		StudentEnqEntity entity = service.getStudentEnq(eId);

		EnquiryForm form = new EnquiryForm();

		BeanUtils.copyProperties(entity, form);

		getData(model);
		model.addAttribute("Enquiry", form);

		return "add-enquiry";
	}

	
	@GetMapping("/view-enquiries")
	public String viewEnquiries(@RequestParam String courseName,
			@RequestParam String status,
			@RequestParam String mode,
			Model model) {
		
		EnquirySearchCriteria search = new EnquirySearchCriteria();
		
		if(null != mode && !"".equals(mode)) {
			search.setClassMode(mode);
		}

		if(null != courseName && !"".equals(courseName)) {
			search.setCourseName(courseName);
		}
		
		if(null != status && !"".equals(status)) {
			search.setEnquiryStatus(status);
		}
		
		
		Integer userId = (Integer) session.getAttribute("userID");
		List<StudentEnqEntity> enquiries = service.getEnquiries(userId, search);

		model.addAttribute("enquiries", enquiries);

		System.out.println(search);
		
		return "filtered-enquiries";
	}
}
