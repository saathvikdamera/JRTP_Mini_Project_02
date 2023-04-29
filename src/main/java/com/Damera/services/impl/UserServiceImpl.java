package com.Damera.services.impl;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.Damera.binding.LoginForm;
import com.Damera.binding.SignupForm;
import com.Damera.binding.UnlockForm;
import com.Damera.entities.UserDtlsEntity;
import com.Damera.repository.UserRepository;
import com.Damera.services.UserService;
import com.Damera.utils.EmailUtils;
import com.Damera.utils.PwdUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;

	@Autowired
	private PwdUtils pwdUtils;

	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private HttpSession session;

	@Override
	public String createUser(SignupForm form) {

		UserDtlsEntity entity = repo.findByEmail(form.getEmail());

		if(entity != null) {
			if(entity.getAccountStatus().equalsIgnoreCase("locked")) {
				return "Your account need to be unlocked";
			}
			else if(entity.getAccountStatus().equalsIgnoreCase("unlocked")) {
				return "Email already exists ,Please login !";
			}
		}

		UserDtlsEntity user = new UserDtlsEntity();
		BeanUtils.copyProperties(form, user);

		String pwd = pwdUtils.generatePwd(6);

		StringBuffer sb = new StringBuffer("");

		sb.append("<h1>Hey, "+form.getName()+"</h1>");

		sb.append("<h3>Unlock your account with this temporary password</h3>");

		sb.append("<br>");

		sb.append("<p>Temporary password : <B>" + pwd +"</B> </p>");

		sb.append("<br>");

		sb.append("<a href=\"http://localhost:8080/unlock?mail="+form.getEmail()+"\">Click here to unlock your account </a>");

		/*
		String mailText ="Hey,"+form.getName()+".Your Temporary Password : "+ pwd +
				". Unlock your account with Temporary Password ."+ 
				"http://localhost:8080/unlock?mail="+form.getEmail();
		 */

		emailUtils.sendEmail(form.getEmail(),"Unlock your account ",String.valueOf(sb));

		user.setAccountStatus("Locked");
		user.setPassword(pwd);

		repo.save(user);

		return "Mail sent to " + form.getEmail();
	}

	@Override
	public String unlockAccount(UnlockForm form) {

		UserDtlsEntity user = repo.findByEmail(form.getEmail());

		if(!form.getTempPassword().equals(user.getPassword())) {
			return "Temporary Password is not matching";
		}

		if(!form.getNewPassword().equals(form.getConfirmPassword())) {
			return "Password not matching";
		}

		user.setPassword(form.getNewPassword());
		user.setAccountStatus("Unlocked");

		repo.save(user);

		return "Account unlocked";
	}

	@Override
	public String resetPassword(String email) {
		UserDtlsEntity user = repo.findByEmail(email);
		if(user == null) {
			return "Account doesn't exist with this email";
		}

		//String pwd = pwdUtils.generatePwd(6);

		StringBuffer sb = new StringBuffer("");

		sb.append("<h1>Hey, "+user.getName()+"</h1>");

		sb.append("<h4>Your Password</h4>");

		sb.append("<B>" + user.getPassword() +"</B>");

		sb.append("<br>");
		
		sb.append("login to your account with this password.");

		//sb.append("<a href=\"http://localhost:8080/unlock?mail="+form.getEmail()+"\">Click here to unlock your account </a>");

		emailUtils.sendEmail(email, "Reset password",String.valueOf(sb));

		//user.setPassword(pwd);
		repo.save(user);

		return "Check your email";
	}

	@Override
	public String loginUser(LoginForm form) {
		UserDtlsEntity entity = repo.findByEmailAndPassword(form.getEmail(),form.getPassword());
		
		if(entity == null) {
			return "Invalid credentials";
		}
		
		if(entity.getAccountStatus().equalsIgnoreCase("locked")) {
			return "Your account need to be unlocked";
		}
		
		session.setAttribute("userID", entity.getUserId());
		
		return "success";
	}

}
