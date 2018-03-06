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

import com.altimetrik.wallet.walrew.api.service.AuditApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Audit;

@RestController
@RequestMapping(value = "/api")
public class AuditApi {

	@Autowired
	private AuditApiService service;

	@RequestMapping(value = "/audit", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Audit>> findAllAudit() throws NotFoundException {

		return new ResponseEntity<List<Audit>>(service.findAllAudit(), HttpStatus.OK);

	}

	@RequestMapping(value = "/audit", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Audit> updateAudit(@RequestBody Audit audit) throws NotFoundException {

		return new ResponseEntity<Audit>(service.updateAudit(audit), HttpStatus.OK);

	}

	@RequestMapping(value = "/audit", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Audit> addAudit(@RequestBody Audit audit) throws NotFoundException {

		return new ResponseEntity<Audit>(service.addAudit(audit), HttpStatus.OK);

	}

	@RequestMapping(value = "/audit/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Audit> findByIdAudit(@PathVariable("id") Integer id) throws NotFoundException {

		return new ResponseEntity<Audit>(service.findByIdAudit(id), HttpStatus.OK);

	}

	@RequestMapping(value = "/audit/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAudit(@PathVariable("id") Integer id) throws NotFoundException {

		service.deleteAudit(id);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
