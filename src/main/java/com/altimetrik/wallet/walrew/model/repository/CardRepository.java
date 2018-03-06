package com.altimetrik.wallet.walrew.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.wallet.walrew.model.Card;
import com.altimetrik.wallet.walrew.model.Wallet;




@Repository
public interface CardRepository extends JpaRepository<Card, Integer>{

	Card findByWallet(Wallet wallet);

}



