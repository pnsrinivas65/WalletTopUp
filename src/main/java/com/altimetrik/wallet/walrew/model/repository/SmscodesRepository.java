package com.altimetrik.wallet.walrew.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.wallet.walrew.model.Smscodes;




@Repository
public interface SmscodesRepository extends JpaRepository<Smscodes, Integer>{

}



