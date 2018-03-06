package com.altimetrik.wallet.walrew.api.service;

import com.altimetrik.wallet.walrew.model.User;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import java.util.List;

public interface UserApiService {

	List<User> findAllUser() throws NotFoundException;

	User updateUser(User user) throws NotFoundException;

	User addUser(User user) throws NotFoundException;

	User findByIdUser(Integer id) throws NotFoundException;

	User findByMobileno(String mobileno) throws NotFoundException;
	
	User findByUsername(String userName) throws NotFoundException;
	
	void deleteUser(Integer id) throws NotFoundException;

}
