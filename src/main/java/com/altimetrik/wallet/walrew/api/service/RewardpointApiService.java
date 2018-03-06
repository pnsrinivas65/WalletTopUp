package com.altimetrik.wallet.walrew.api.service;

import java.util.List;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Rewardpoint;
import com.altimetrik.wallet.walrew.model.User;

public interface RewardpointApiService {

	List<Rewardpoint> findAllRewardpoint() throws NotFoundException;

	Rewardpoint updateRewardpoint(Rewardpoint rewardpoint) throws NotFoundException;

	Rewardpoint addRewardpoint(Rewardpoint rewardpoint) throws NotFoundException;

	Rewardpoint findByIdRewardpoint(Integer id) throws NotFoundException;

	Rewardpoint findByUser(User user) throws NotFoundException;

	void deleteRewardpoint(Integer id) throws NotFoundException;

}
