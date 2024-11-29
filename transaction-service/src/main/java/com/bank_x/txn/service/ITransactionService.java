package com.bank_x.txn.service;

import java.util.List;

import com.bank_x.txn.entity.Transaction;

public interface ITransactionService {
	
	public Transaction recordTransaction(Transaction txn);
	public Transaction getTransaction(String txnId);
	public List<Transaction> getTxnByRefNo(String refNo);

}
