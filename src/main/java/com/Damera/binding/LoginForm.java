package com.Damera.binding;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {
 
	@NotBlank(message =  "email should not be null")
	private String email;
	
	@NotBlank(message =  "email should not be null")
	private String password;

}
