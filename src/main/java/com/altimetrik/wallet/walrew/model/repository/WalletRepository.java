package com.altimetrik.wallet.walrew.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.wallet.walrew.model.User;
import com.altimetrik.wallet.walrew.model.Wallet;




@Repository
public interface WalletRepository extends JpaRepository<Wallet, Integer>{

	Wallet findByUser(User userId);

	Wallet findByWalletname(String walletName);

}



