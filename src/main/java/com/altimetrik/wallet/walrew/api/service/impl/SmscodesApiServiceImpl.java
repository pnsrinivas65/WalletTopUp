package com.altimetrik.wallet.walrew.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.wallet.walrew.api.service.SmscodesApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Smscodes;
import com.altimetrik.wallet.walrew.model.repository.SmscodesRepository;

@Service
@Transactional
public class SmscodesApiServiceImpl implements SmscodesApiService {

	@Autowired
	private SmscodesRepository smscodesRepository;

	@Override
	public List<Smscodes> findAllSmscodes() throws NotFoundException {

		return smscodesRepository.findAll();

	}

	@Override
	public Smscodes updateSmscodes(Smscodes smscodes) throws NotFoundException {

		return smscodesRepository.save(smscodes);

	}

	@Override
	public Smscodes addSmscodes(Smscodes smscodes) throws NotFoundException {

		return smscodesRepository.save(smscodes);

	}

	@Override
	public Smscodes findByIdSmscodes(Integer id) throws NotFoundException {

		return smscodesRepository.findOne(id);

	}

	@Override
	public void deleteSmscodes(Integer id) throws NotFoundException {

	}

}
