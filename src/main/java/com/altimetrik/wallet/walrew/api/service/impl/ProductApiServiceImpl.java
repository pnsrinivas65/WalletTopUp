package com.altimetrik.wallet.walrew.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.altimetrik.wallet.walrew.api.service.ProductApiService;
import com.altimetrik.wallet.walrew.exception.NotFoundException;
import com.altimetrik.wallet.walrew.model.Product;
import com.altimetrik.wallet.walrew.model.repository.ProductRepository;

@Service
@Transactional
public class ProductApiServiceImpl implements ProductApiService {
		  	  
	  @Autowired
	
  	  private ProductRepository  productRepository ;
	   
  
  
				
			
      @Override
      public List<Product> findAllProduct()
      throws NotFoundException {
      
        
  		
  		
  			return productRepository.findAll();
  		
  		
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public Product updateProduct(Product product)
      throws NotFoundException {
      
         
        	return productRepository.save(product);
  		
  		
  		
  		
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public Product addProduct(Product product)
      throws NotFoundException {
      
         
        	return productRepository.save(product);
  		
  		
  		
  		
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public Product findByIdProduct(Integer id)
      throws NotFoundException {
      
        
  		
  		
  		
  		
  		  return productRepository.findOne(id); 
  		
  		
  		
  		
  		
  }
  
				
			
      @Override
      public void deleteProduct(Integer id)
      throws NotFoundException {
      
        
  		
  		
  		
  		
  		
  		 
			
			
  		
  		
  		
  }
  
}
