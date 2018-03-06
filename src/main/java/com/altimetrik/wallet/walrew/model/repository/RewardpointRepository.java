package com.altimetrik.wallet.walrew.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.wallet.walrew.model.Rewardpoint;
import com.altimetrik.wallet.walrew.model.User;

@Repository
public interface RewardpointRepository extends JpaRepository<Rewardpoint, Integer> {

	Rewardpoint findByUser(User user);

}
