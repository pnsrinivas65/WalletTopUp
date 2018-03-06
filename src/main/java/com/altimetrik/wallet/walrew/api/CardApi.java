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

import com.altimetrik.wallet.walrew.api.service.CardApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Card;

@RestController
@RequestMapping(value = "/api")
public class CardApi {

	@Autowired
	private CardApiService service;

	@RequestMapping(value = "/card", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Card>> findAllCard() throws NotFoundException {

		return new ResponseEntity<List<Card>>(service.findAllCard(), HttpStatus.OK);

	}

	@RequestMapping(value = "/card", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Card> updateCard(@RequestBody Card card) throws NotFoundException {

		return new ResponseEntity<Card>(service.updateCard(card), HttpStatus.OK);

	}

	@RequestMapping(value = "/card", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Card> addCard(@RequestBody Card card) throws NotFoundException {

		return new ResponseEntity<Card>(service.addCard(card), HttpStatus.OK);

	}

	@RequestMapping(value = "/card/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Card> findByIdCard(@PathVariable("id") Integer id) throws NotFoundException {

		return new ResponseEntity<Card>(service.findByIdCard(id), HttpStatus.OK);

	}

	@RequestMapping(value = "/card/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCard(@PathVariable("id") Integer id) throws NotFoundException {

		service.deleteCard(id);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
