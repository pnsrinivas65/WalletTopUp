package com.altimetrik.wallet.walrew.api.service;

import java.util.List;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.User;
import com.altimetrik.wallet.walrew.model.Wallet;

public interface WalletApiService {

	List<Wallet> findAllWallet() throws NotFoundException;

	Wallet updateWallet(Wallet wallet) throws NotFoundException;

	Wallet addWallet(Wallet wallet) throws NotFoundException;

	Wallet findByIdWallet(Integer id) throws NotFoundException;

	Wallet findbyUserId(User user) throws NotFoundException;
	
	Wallet findbyWalletName(String walletName) throws NotFoundException;
	
	void deleteWallet(Integer id) throws NotFoundException;

}
