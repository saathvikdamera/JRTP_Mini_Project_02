package com.Damera.binding;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class EnquiryForm {
	
	private Integer eId;
	
	@NotBlank(message = "Name should not be empty")
	private String studentName;
	
	@NotNull(message = "Phone number is mandatory")
	private Long phno;
	
	private String classMode;
	private String courseName;
	private String enquiryStatus;

}
