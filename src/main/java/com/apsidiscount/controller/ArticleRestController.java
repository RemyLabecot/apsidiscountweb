package com.apsidiscount.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.apsidiscount.dao.ArticleDAO;
import com.apsidiscount.entity.Article;
import com.apsidiscount.exceptions.ArticleInconnuException;
import com.apsidiscount.service.ArticleService;

@CrossOrigin
@RestController
public class ArticleRestController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ArticleDAO articleDao;
	
	@ExceptionHandler(ArticleInconnuException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorResponse handleArticleInconnuException(ArticleInconnuException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		return errorResponse;
	}

	@GetMapping(produces="application/json", path="/api/article/{id}")
	public ResponseEntity<Article> getById(@PathVariable long id) throws ArticleInconnuException {
		
		Article article = articleService.getById(id);
		return ResponseEntity.ok().body(article);
	}
	
	@PostMapping(consumes="application/json", produces="application/json", path="/api/article")
	public ResponseEntity<Article> create(@RequestBody Article article, UriComponentsBuilder uriBuilder) throws URISyntaxException {
		Article article1 = articleDao.create(article);
		URI location = uriBuilder.path("/api/article/{id}").buildAndExpand(article.getId()).toUri();
		return ResponseEntity.created(location).body(article1);
	}
	
	@GetMapping(produces="application/json", path="/api/articles")
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> articles = articleDao.getArticles();
		return ResponseEntity.ok().body(articles);
	}
}
