package com.altimetrik.wallet.walrew.api.service;

import java.util.List;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Smscodes;

public interface SmscodesApiService {

	List<Smscodes> findAllSmscodes() throws NotFoundException;

	Smscodes updateSmscodes(Smscodes smscodes) throws NotFoundException;

	Smscodes addSmscodes(Smscodes smscodes) throws NotFoundException;

	Smscodes findByIdSmscodes(Integer id) throws NotFoundException;

	void deleteSmscodes(Integer id) throws NotFoundException;

}
