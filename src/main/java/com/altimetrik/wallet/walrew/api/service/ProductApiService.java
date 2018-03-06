package com.altimetrik.wallet.walrew.api.service;

import com.altimetrik.wallet.walrew.model.Product;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import java.util.List;

public interface ProductApiService {

	List<Product> findAllProduct() throws NotFoundException;

	Product updateProduct(Product product) throws NotFoundException;

	Product addProduct(Product product) throws NotFoundException;

	Product findByIdProduct(Integer id) throws NotFoundException;

	void deleteProduct(Integer id) throws NotFoundException;

}
