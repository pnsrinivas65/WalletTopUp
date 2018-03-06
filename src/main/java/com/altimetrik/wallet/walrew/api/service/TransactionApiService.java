package com.altimetrik.wallet.walrew.api.service;

import com.altimetrik.wallet.walrew.model.Transaction;

import com.altimetrik.wallet.walrew.exception.NotFoundException;
import java.util.List;

public interface TransactionApiService {

	List<Transaction> findAllTransaction() throws NotFoundException;

	Transaction updateTransaction(Transaction transaction) throws NotFoundException;

	Transaction addTransaction(Transaction transaction) throws NotFoundException;

	Transaction findByIdTransaction(Integer id) throws NotFoundException;

	void deleteTransaction(Integer id) throws NotFoundException;

}
