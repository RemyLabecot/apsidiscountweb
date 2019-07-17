package com.apsidiscount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.apsidiscount.entity.Article;
import com.apsidiscount.exceptions.ArticleInconnuException;
import com.apsidiscount.service.ArticleService;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@ExceptionHandler(ArticleInconnuException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public String handleArticleInconnuException(ArticleInconnuException e, Model model) {
		model.addAttribute("exception", e);
		return "error";
	}
	
	@GetMapping("/article/{id}")
	public String afficher(@PathVariable long id, Model model) throws ArticleInconnuException {
		Article article = articleService.getById(id);
		model.addAttribute("article", article);
		return "article";
	}
	
	@PostMapping("article/{id}")
	public String modifier(@PathVariable long id, @RequestParam String designation, @RequestParam String description, Model model) throws ArticleInconnuException {
		Article article = articleService.modifier(id, designation, description);
		model.addAttribute("article", article);
		model.addAttribute("message", "modifications enregistrée");
		return "article";
	}
}
