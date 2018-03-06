package com.altimetrik.wallet.walrew.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.altimetrik.wallet.walrew.api.service.SmscodesApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Smscodes;

@Controller
@RequestMapping(value = "/api")
public class SmscodesApi {

	@Autowired
	private SmscodesApiService service;

	@RequestMapping(value = "/smscodes", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Smscodes>> findAllSmscodes() throws NotFoundException {

		return new ResponseEntity<List<Smscodes>>(service.findAllSmscodes(), HttpStatus.OK);

	}

	@RequestMapping(value = "/smscodes", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Smscodes> updateSmscodes(@RequestBody Smscodes smscodes) throws NotFoundException {

		return new ResponseEntity<Smscodes>(service.updateSmscodes(smscodes), HttpStatus.OK);

	}

	@RequestMapping(value = "/smscodes", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Smscodes> addSmscodes(@RequestBody Smscodes smscodes) throws NotFoundException {

		return new ResponseEntity<Smscodes>(service.addSmscodes(smscodes), HttpStatus.OK);

	}

	@RequestMapping(value = "/smscodes/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Smscodes> findByIdSmscodes(@PathVariable("id") Integer id) throws NotFoundException {

		return new ResponseEntity<Smscodes>(service.findByIdSmscodes(id), HttpStatus.OK);

	}

	@RequestMapping(value = "/smscodes/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteSmscodes(@PathVariable("id") Integer id) throws NotFoundException {

		service.deleteSmscodes(id);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
