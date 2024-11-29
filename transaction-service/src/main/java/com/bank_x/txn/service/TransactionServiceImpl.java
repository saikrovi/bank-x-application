package com.bank_x.txn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank_x.txn.entity.Transaction;
import com.bank_x.txn.exception.TransactionNotFoundException;
import com.bank_x.txn.repository.TransactionRepository;

@Service
public class TransactionServiceImpl implements ITransactionService {
	
	@Autowired private TransactionRepository repository;

	@Override
	public Transaction recordTransaction(Transaction txn) {
		return repository.save(txn);
	}

	@Override
	public Transaction getTransaction(String txnId) {
		return repository.findById(txnId).orElseThrow(()->new TransactionNotFoundException("Transaction not found...!"));
	}

	@Override
	public List<Transaction> getTxnByRefNo(String refNo) {
		List<Transaction> txns = repository.getTxnByRefNo(refNo);
		if(txns!=null) return null;
		else throw new TransactionNotFoundException("Transaction not found...!");
	}

}
