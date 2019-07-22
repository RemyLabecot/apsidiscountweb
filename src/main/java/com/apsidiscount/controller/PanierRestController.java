package com.apsidiscount.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apsidiscount.entity.Article;
import com.apsidiscount.exceptions.ArticleInconnuException;
import com.apsidiscount.exceptions.ClientInconnuException;
import com.apsidiscount.exceptions.StockInsuffisantException;
import com.apsidiscount.service.ClientService;

@CrossOrigin
@RestController
public class PanierRestController {
	
	@Autowired
	private ClientService clientService;
	
	
	@PutMapping(produces="application/json", path="/api/panier/client/{idClient}/article/{idArticle}")
	public Article addArticlesInPanier(@PathVariable long idClient, @PathVariable long idArticle) throws ClientInconnuException, ArticleInconnuException, StockInsuffisantException {
		Article article = clientService.ajouterArticleDansPanier(idClient, idArticle);
		return article;
	}
	
	@DeleteMapping(path="/api/panier/client/{idClient}/article/{idArticle}")
	public void deleteArticleFromPanier(@PathVariable long idClient, @PathVariable long idArticle) throws ClientInconnuException, ArticleInconnuException {
		
		clientService.deleteArticleFromPanier(idClient, idArticle);
	}
}
