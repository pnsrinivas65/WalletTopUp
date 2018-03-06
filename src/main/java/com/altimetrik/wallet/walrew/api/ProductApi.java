package com.altimetrik.wallet.walrew.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.altimetrik.wallet.walrew.api.service.ProductApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Product;

@RestController
@RequestMapping(value = "/api")
public class ProductApi {

	@Autowired
	private ProductApiService service;

	@RequestMapping(value = "/product", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Product>> findAllProduct() throws NotFoundException {

		return new ResponseEntity<List<Product>>(service.findAllProduct(), HttpStatus.OK);

	}

	@RequestMapping(value = "/product", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@RequestBody Product product) throws NotFoundException {

		return new ResponseEntity<Product>(service.updateProduct(product), HttpStatus.OK);

	}

	@RequestMapping(value = "/product", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Product> addProduct(@RequestBody Product product) throws NotFoundException {

		return new ResponseEntity<Product>(service.addProduct(product), HttpStatus.OK);

	}

	@RequestMapping(value = "/product/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Product> findByIdProduct(@PathVariable("id") Integer id) throws NotFoundException {

		return new ResponseEntity<Product>(service.findByIdProduct(id), HttpStatus.OK);

	}

	@RequestMapping(value = "/product/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) throws NotFoundException {

		service.deleteProduct(id);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
