package com.altimetrik.wallet.walrew.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.wallet.walrew.api.service.RewardpointApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Rewardpoint;
import com.altimetrik.wallet.walrew.model.User;
import com.altimetrik.wallet.walrew.model.repository.RewardpointRepository;

@Service
@Transactional
public class RewardpointApiServiceImpl implements RewardpointApiService {

	@Autowired

	private RewardpointRepository rewardpointRepository;

	@Override
	public List<Rewardpoint> findAllRewardpoint() throws NotFoundException {

		return rewardpointRepository.findAll();

	}

	@Override
	public Rewardpoint updateRewardpoint(Rewardpoint rewardpoint) throws NotFoundException {

		return rewardpointRepository.save(rewardpoint);

	}

	@Override
	public Rewardpoint addRewardpoint(Rewardpoint rewardpoint) throws NotFoundException {

		return rewardpointRepository.save(rewardpoint);

	}

	@Override
	public Rewardpoint findByIdRewardpoint(Integer id) throws NotFoundException {

		return rewardpointRepository.findOne(id);

	}

	@Override
	public void deleteRewardpoint(Integer id) throws NotFoundException {

	}

	@Override
	public Rewardpoint findByUser(User user) throws NotFoundException {
		// TODO Auto-generated method stub
		return rewardpointRepository.findByUser(user);
	}

}
