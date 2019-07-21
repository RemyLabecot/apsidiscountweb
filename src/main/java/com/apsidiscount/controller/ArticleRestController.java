package com.apsidiscount.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


import com.apsidiscount.entity.Article;
import com.apsidiscount.entity.Categorie;
import com.apsidiscount.entity.Client;
import com.apsidiscount.exceptions.ArticleInconnuException;
import com.apsidiscount.exceptions.ClientInconnuException;
import com.apsidiscount.exceptions.LoginAndPasswordException;
import com.apsidiscount.exceptions.StockInsuffisantException;
import com.apsidiscount.service.ArticleService;
import com.apsidiscount.service.CategorieService;
import com.apsidiscount.service.ClientService;

@CrossOrigin
@RestController
public class ArticleRestController {
	
	@Autowired
	private ArticleService articleService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private CategorieService categorieService;
	
	@ExceptionHandler(ArticleInconnuException.class)
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	public ErrorResponse handleArticleInconnuException(ArticleInconnuException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		return errorResponse;
	}
	
	@ExceptionHandler(LoginAndPasswordException.class)
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public ErrorResponse handleLoginAndPasswordException(LoginAndPasswordException e) {
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
		Article article1 = articleService.create(article);
		URI location = uriBuilder.path("/api/article/{id}").buildAndExpand(article.getId()).toUri();
		return ResponseEntity.created(location).body(article1);
	}
	
	@GetMapping(produces="application/json", path="/api/allArticles")
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> articles = articleService.getArticles();
		return ResponseEntity.ok().body(articles);
	}
	
	@DeleteMapping(path="/api/article/{id}")
	public void deleteArticle(@PathVariable long id) {
		articleService.delete(id);
	}
	
	@GetMapping(produces="application/json", path="/api/articles")
	public ResponseEntity<List<Article>> getArticlesWithSort() {
		List<Article> articles1 = articleService.getArticlesWithSort();
		return ResponseEntity.ok().body(articles1);
	}
	
	@GetMapping(produces="application/json", path="/api/allproducts")
	public ResponseEntity<List<Article>> getArticlesWithSortRCP() {
		List<Article> rcp = articleService.getArticlesWithSortRCP();
		return ResponseEntity.ok().body(rcp);
	}
	
	@PostMapping(consumes="application/json", produces="application/json", path="/api/client")
	public Client getClientByLoginAndPassword(@RequestBody ClientDto jsonLogin) throws LoginAndPasswordException {
		String email = jsonLogin.getEmail();
		String password = jsonLogin.getPassword();
		Client client = clientService.getClientByNameAndPassword(email, password);
		return client;
	}
	
	@PostMapping(produces="application/json", path="/api/panier")
	public Article addArticlesInPanier(@RequestBody Prout jsonIds) throws ClientInconnuException, ArticleInconnuException, StockInsuffisantException {
		long idClient = jsonIds.getIdClient();
		long idArticle = jsonIds.getIdArticle();
		Article article = clientService.ajouterArticleDansPanier(idClient, idArticle);
		return article;
	}
	
	@GetMapping(produces="application/json", path="/api/articles/{id}")
	public List<Article> getArticlesByIdClient(@PathVariable long id) {
		List<Article> articles = clientService.getArticlesByIdClient(id);
		return articles;
	}
	
	@DeleteMapping(path="/api/panier/client/{idClient}/article/{idArticle}")
	public void deleteArticleFromPanier(@PathVariable long idClient, @PathVariable long idArticle) throws ClientInconnuException, ArticleInconnuException {
		
		clientService.deleteArticleFromPanier(idClient, idArticle);
	}
	
	@GetMapping(produces="application/json", path="/api/categories")
	public List<Categorie> getCategories() {
		return this.categorieService.getCategories();
	}
	
	@GetMapping(produces="application/json", path="/api/categories/{nomCategorie}")
	public List<Article> getArticlesByCategorie(@PathVariable String nomCategorie) {
		return this.articleService.getArticlesByCategorie(nomCategorie);
	}
}
