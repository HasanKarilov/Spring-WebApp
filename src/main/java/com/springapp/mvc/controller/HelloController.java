package com.springapp.mvc.controller;

import com.springapp.mvc.objects.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class HelloController {

	@RequestMapping(value = "/getDate", method = RequestMethod.GET)
	public String getaDateTime(Model model){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:MM:ss");
		String str = dateFormat.format(date);

		model.addAttribute("date", str);
		return "datetime";
	}

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView registration() {

		return new ModelAndView("registration", "user", new User());
	}

	@RequestMapping(value = "/check-user", method = RequestMethod.POST)
	public String checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		model.addAttribute("user", user);
		return "check-user";
	}

	@RequestMapping(value = "/failed", method = RequestMethod.GET)
	public ModelAndView failed(){
		return new ModelAndView("login-fail", "failmessage", "Login failed!");
	}
}