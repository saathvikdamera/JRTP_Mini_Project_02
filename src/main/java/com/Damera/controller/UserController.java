package com.Damera.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Damera.binding.LoginForm;
import com.Damera.binding.SignupForm;
import com.Damera.binding.UnlockForm;
import com.Damera.services.UserService;

@Controller
public class UserController {


	@Autowired
	private UserService service;

	@GetMapping("/login")
	public String loadLogin(Model model) {
		model.addAttribute("login", new LoginForm());
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Validated @ModelAttribute("login") LoginForm form,Model model) {

		String status = service.loginUser(form);

		if(status.contains("success")) {
			return "redirect:/dashboard";
		}else {
			model.addAttribute("error", status);
			return "login";
		}

	}

	@GetMapping("/signup")
	public String loadCreate(Model model) {
		model.addAttribute("signup",new SignupForm());
		return "signup";
	}

	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("signup")@Validated SignupForm form,BindingResult result,Model model) {
		if(result.hasErrors()) {
			return "signup";
		}

		String message = service.createUser(form);
		model.addAttribute("status", message);

		return "signup";
	}

	@GetMapping("/unlock")
	public String loadUnlock(@RequestParam("mail")String email,Model model) {
		UnlockForm form = new UnlockForm();
		form.setEmail(email);
		model.addAttribute("unlock",form);
		model.addAttribute("mail", email);
		return "unlock";
	}

	@PostMapping("/unlock")
	public String unlockAccount(@Validated UnlockForm form,Model model) {
		model.addAttribute("unlock",new UnlockForm());
		String message = service.unlockAccount(form);

		if(message.equals("Account unlocked")) {
			//model.addAttribute("login", new LoginForm());
			model.addAttribute("msg", message);
			return "unlock";
		}else {
			model.addAttribute("unlock", new UnlockForm());
			model.addAttribute("msg",message);
			return "unlock";
		}
	}

	@GetMapping("/forgot")
	public String loadForgotPwd(Model model) {
		return "forgotpwd";
	}

	@PostMapping("/forgot")
	public String resetPassword(@RequestParam("email")String email,Model model) {

		String resetPassword = service.resetPassword(email);
		model.addAttribute("msg", resetPassword);
		return "forgotpwd";
	}

}
