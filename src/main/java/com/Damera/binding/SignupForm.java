package com.Damera.binding;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignupForm {

	@NotBlank(message = "Name should not be empty")
	private String name;
	
	@NotBlank(message = "Email should not be empty")
	private String email;
	
	@NotNull(message = "phone number is mandator")
	//@Size(min = 10,max = 13,message = "Invalid Phone Number" )
	private Long phno;
}
