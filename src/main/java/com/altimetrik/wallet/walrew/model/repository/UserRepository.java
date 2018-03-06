package com.altimetrik.wallet.walrew.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.wallet.walrew.model.User;




@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
	
	public User findByMobileno(String mobileno);

	public User findByUsername(String mobileno);
	
}



