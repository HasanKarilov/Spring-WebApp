package com.springapp.mvc.controller;

import com.springapp.mvc.objects.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@SessionAttributes("user")
public class HelloController {

	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView registration(HttpSession session) {
		User user = new User("Userator", "Userator password");
		/*
		OR
		user.setName("Userator");
		user.setPassword("Userator password");
		 */
		return new ModelAndView("registration", "user", user);
	}

	@RequestMapping(value = "/check-user", method = RequestMethod.POST)
	public ModelAndView checkUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model, ModelMap modelMap) {
		// Model model & ModelMap modelMap это два совершенно одинаковых обьекта
		System.out.println(model);
		System.out.println(modelMap);

		ModelAndView modelAndView = new ModelAndView();

		RedirectView redirectView = new RedirectView("check-user");
		redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);

		modelAndView.setView(redirectView);
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/check-user", method = RequestMethod.GET)
	public String chekUserGet(){
		return "check-user";
	}

	@RequestMapping(value = "/failed", method = RequestMethod.GET)
	public ModelAndView failed(SessionStatus sessionStatus){
		sessionStatus.setComplete();
		return new ModelAndView("login-fail", "failmessage", "Login failed! (catch by Interceptor)");
	}

	@RequestMapping(value = "/getDate", method = RequestMethod.GET)
	public String getaDateTime(@ModelAttribute("user") User user, Model model){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:MM:ss");
		String str = dateFormat.format(date);

		model.addAttribute("date", str);
		return "datetime";
	}
}