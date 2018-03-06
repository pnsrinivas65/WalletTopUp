package com.altimetrik.wallet.walrew.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.wallet.walrew.api.service.UserApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.User;
import com.altimetrik.wallet.walrew.model.repository.UserRepository;

@Service
@Transactional
public class UserApiServiceImpl implements UserApiService {

	@Autowired

	private UserRepository userRepository;

	@Override
	public List<User> findAllUser() throws NotFoundException {

		return userRepository.findAll();

	}

	@Override
	public User updateUser(User user) throws NotFoundException {

		return userRepository.save(user);

	}

	@Override
	public User addUser(User user) throws NotFoundException {

		return userRepository.save(user);

	}

	@Override
	public User findByIdUser(Integer id) throws NotFoundException {

		return userRepository.findOne(id);

	}

	@Override
	public void deleteUser(Integer id) throws NotFoundException {

	}

	@Override
	public User findByMobileno(String mobile) throws NotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByMobileno(mobile);
	}

	@Override
	public User findByUsername(String userName) throws NotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(userName);
	}

}
