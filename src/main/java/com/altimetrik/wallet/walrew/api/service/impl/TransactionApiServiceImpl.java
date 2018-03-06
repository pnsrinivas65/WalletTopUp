package com.altimetrik.wallet.walrew.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.wallet.walrew.api.service.TransactionApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Transaction;
import com.altimetrik.wallet.walrew.model.repository.TransactionRepository;

@Service
@Transactional
public class TransactionApiServiceImpl implements TransactionApiService {
		  	  
	  @Autowired
	
  	  private TransactionRepository  transactionRepository ;
	   
  
  
				
			
      @Override
      public List<Transaction> findAllTransaction()
      throws NotFoundException {
      
        
  		
  		
  			return transactionRepository.findAll();
  		
  		
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public Transaction updateTransaction(Transaction transaction)
      throws NotFoundException {
      
         
        	return transactionRepository.save(transaction);
  		
  		
  		
  		
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public Transaction addTransaction(Transaction transaction)
      throws NotFoundException {
      
         
        	return transactionRepository.save(transaction);
  		
  		
  		
  		
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public Transaction findByIdTransaction(Integer id)
      throws NotFoundException {
      
        
  		
  		
  		
  		
  		  return transactionRepository.findOne(id); 
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public void deleteTransaction(Integer id)
      throws NotFoundException {
      
        
  		
  		
  		
  		
  		
  		 
			
			
  		
  		
  		
  }
  
}
