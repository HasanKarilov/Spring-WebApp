package com.springapp.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
public class HelloController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getaDateTime(Model model){
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy HH:MM:ss");
		String str = dateFormat.format(date);

		model.addAttribute("date", str);
		return "datetime";
	}
}