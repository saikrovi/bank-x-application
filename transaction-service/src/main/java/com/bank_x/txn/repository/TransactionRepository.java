package com.bank_x.txn.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank_x.txn.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
	
	@Query("SELECT t FROM Transaction t WHERE refNo=:refNo")
	public List<Transaction> getTxnByRefNo(String refNo);

}
