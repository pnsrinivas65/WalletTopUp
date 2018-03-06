package com.altimetrik.wallet.walrew.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altimetrik.wallet.walrew.model.Audit;




@Repository
public interface AuditRepository extends JpaRepository<Audit, Integer>{

}



