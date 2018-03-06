package com.altimetrik.wallet.walrew.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.wallet.walrew.api.service.WalletApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.User;
import com.altimetrik.wallet.walrew.model.Wallet;
import com.altimetrik.wallet.walrew.model.repository.WalletRepository;

@Service
@Transactional
public class WalletApiServiceImpl implements WalletApiService {

	@Autowired
	private WalletRepository walletRepository;

	@Override
	public List<Wallet> findAllWallet() throws NotFoundException {

		return walletRepository.findAll();

	}

	@Override
	public Wallet updateWallet(Wallet wallet) throws NotFoundException {

		return walletRepository.save(wallet);

	}

	@Override
	public Wallet addWallet(Wallet wallet) throws NotFoundException {

		return walletRepository.save(wallet);

	}

	@Override
	public Wallet findByIdWallet(Integer id) throws NotFoundException {

		return walletRepository.findOne(id);

	}

	@Override
	public void deleteWallet(Integer id) throws NotFoundException {

	}
	
	
	public Wallet findbyUserId(User user) throws NotFoundException {
		return walletRepository.findByUser(user);
	}

	@Override
	public Wallet findbyWalletName(String walletName) throws NotFoundException {
		return walletRepository.findByWalletname(walletName);
	}

}
