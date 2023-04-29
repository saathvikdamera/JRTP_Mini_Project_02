package com.Damera.binding;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UnlockForm {
	
	@NotBlank(message = "This field should not be empty")
	private String tempPassword;
	
	@NotBlank(message = "This field should not be empty")
	private String newPassword;
	
	@NotBlank(message = "This field should not be empty")
	private String confirmPassword;
	
	private String email;

}
