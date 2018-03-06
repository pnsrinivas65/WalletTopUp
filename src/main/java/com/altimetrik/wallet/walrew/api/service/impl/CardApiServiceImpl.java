package com.altimetrik.wallet.walrew.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.wallet.walrew.api.service.CardApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Card;
import com.altimetrik.wallet.walrew.model.Wallet;
import com.altimetrik.wallet.walrew.model.repository.CardRepository;

@Service
@Transactional
public class CardApiServiceImpl implements CardApiService {

	@Autowired

	private CardRepository cardRepository;

	@Override
	public List<Card> findAllCard() throws NotFoundException {

		return cardRepository.findAll();

	}

	@Override
	public Card updateCard(Card card) throws NotFoundException {

		return cardRepository.save(card);

	}

	@Override
	public Card addCard(Card card) throws NotFoundException {

		return cardRepository.save(card);

	}

	@Override
	public Card findByIdCard(Integer id) throws NotFoundException {

		return cardRepository.findOne(id);

	}

	@Override
	public void deleteCard(Integer id) throws NotFoundException {

	}

	@Override
	public Card findByWallet(Wallet wallet) throws NotFoundException {
		return cardRepository.findByWallet(wallet);
	}

}
