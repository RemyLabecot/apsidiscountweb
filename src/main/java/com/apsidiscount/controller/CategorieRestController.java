package com.apsidiscount.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apsidiscount.entity.Categorie;
import com.apsidiscount.service.CategorieService;

@CrossOrigin
@RestController
public class CategorieRestController {
	
	@Autowired
	private CategorieService categorieService;
	
	@GetMapping(produces="application/json", path="/api/categories")
	public List<Categorie> getCategories() {
		return this.categorieService.getCategories();
	}
}
