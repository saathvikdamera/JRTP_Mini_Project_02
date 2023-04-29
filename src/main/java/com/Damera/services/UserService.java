package com.Damera.services;

import com.Damera.binding.LoginForm;
import com.Damera.binding.SignupForm;
import com.Damera.binding.UnlockForm;
import com.Damera.entities.UserDtlsEntity;

public interface UserService {

	String createUser(SignupForm form);
	
	String unlockAccount(UnlockForm form);
	
	String resetPassword(String email);
	
	String loginUser(LoginForm form);

}
