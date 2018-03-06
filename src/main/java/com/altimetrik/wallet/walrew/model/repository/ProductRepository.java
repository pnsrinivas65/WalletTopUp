package com.altimetrik.wallet.walrew.model.repository;

import com.altimetrik.wallet.walrew.model.*;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}



