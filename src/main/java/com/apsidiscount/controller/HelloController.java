package com.apsidiscount.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HelloController {

	@GetMapping("/hello")
	public String sayHello(@RequestParam(required=false, defaultValue="Spring MVC") String nom, Model model) {
		model.addAttribute("nom", nom);
		return "hello";
	}
	
	@GetMapping("/personne")
	public String afficherPersonne(@ModelAttribute("personne") PersonneDto personne) {
		return "personne";
	}
}
