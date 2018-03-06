package com.altimetrik.wallet.walrew.api.service;

import java.util.List;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Card;
import com.altimetrik.wallet.walrew.model.Wallet;

public interface CardApiService {

	List<Card> findAllCard() throws NotFoundException;

	Card updateCard(Card card) throws NotFoundException;

	Card addCard(Card card) throws NotFoundException;

	Card findByIdCard(Integer id) throws NotFoundException;
	
	Card findByWallet(Wallet wallet) throws NotFoundException;

	void deleteCard(Integer id) throws NotFoundException;

}
