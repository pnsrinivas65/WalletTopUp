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

import com.altimetrik.wallet.walrew.api.service.RewardpointApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Rewardpoint;

@RestController
@RequestMapping(value = "/api")
public class RewardpointApi {

	@Autowired
	private RewardpointApiService service;

	@RequestMapping(value = "/rewardpoint", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<List<Rewardpoint>> findAllRewardpoint() throws NotFoundException {

		return new ResponseEntity<List<Rewardpoint>>(service.findAllRewardpoint(), HttpStatus.OK);

	}

	@RequestMapping(value = "/rewardpoint", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.PUT)
	public ResponseEntity<Rewardpoint> updateRewardpoint(@RequestBody Rewardpoint rewardpoint)
			throws NotFoundException {

		return new ResponseEntity<Rewardpoint>(service.updateRewardpoint(rewardpoint), HttpStatus.OK);

	}

	@RequestMapping(value = "/rewardpoint", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.POST)
	public ResponseEntity<Rewardpoint> addRewardpoint(@RequestBody Rewardpoint rewardpoint) throws NotFoundException {

		return new ResponseEntity<Rewardpoint>(service.addRewardpoint(rewardpoint), HttpStatus.OK);

	}

	@RequestMapping(value = "/rewardpoint/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.GET)
	public ResponseEntity<Rewardpoint> findByIdRewardpoint(@PathVariable("id") Integer id) throws NotFoundException {

		return new ResponseEntity<Rewardpoint>(service.findByIdRewardpoint(id), HttpStatus.OK);

	}

	@RequestMapping(value = "/rewardpoint/{id}", produces = { "application/json" }, consumes = {
			"application/json" }, method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteRewardpoint(@PathVariable("id") Integer id) throws NotFoundException {

		service.deleteRewardpoint(id);
		return new ResponseEntity<Void>(HttpStatus.OK);

	}

}
