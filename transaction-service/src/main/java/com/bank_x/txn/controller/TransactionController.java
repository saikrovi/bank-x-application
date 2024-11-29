package com.bank_x.txn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank_x.txn.entity.Transaction;
import com.bank_x.txn.model.Customer;
import com.bank_x.txn.model.TransactionAlert;
import com.bank_x.txn.service.ITransactionService;

@RestController
@RequestMapping("/txn")
public class TransactionController {
	
	@Autowired private ITransactionService service;
	@Autowired private RestTemplate restTemplate;
	
	private String accUrl = "http://localhost:9002/account";
	private String cusUrl = "http://localhost:9001/customers";
	private String notUrl = "http://localhost:9004/alert";
	
	@PostMapping
	public ResponseEntity<String> recordTxn(@RequestBody Transaction txn) {
		txn = service.recordTransaction(txn);
		
		Long custId = restTemplate.getForEntity(accUrl+"/"+txn.getAccNo()+"/custId", Long.class).getBody();
		Customer customer = restTemplate.getForEntity(cusUrl+"/"+custId, Customer.class).getBody();
		
		TransactionAlert alert = new TransactionAlert();
		alert.setCustName(customer.getName());
		alert.setCustEmail(customer.getEmail());
		alert.setDate(txn.getTimestamp().toLocalDate());
		alert.setTime(txn.getTimestamp().toLocalTime());
		alert.setType(txn.getTxnType());
		alert.setTxnAmount(txn.getTxnAmount());
		alert.setAccNo(txn.getAccNo());
		alert.setTxnId(txn.getTxnId());
		alert.setRefNo(txn.getRefNo());
		alert.setDesc(null);
		alert.setAvailBal(txn.getAvailBal());
		restTemplate.postForEntity(notUrl+"/txn", alert, null);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(txn.getTxnId());
	}
	
	@GetMapping("/{txnId}")
	public ResponseEntity<Transaction> retrieveTxn(@PathVariable String txnId) {
		return ResponseEntity.ok(service.getTransaction(txnId));
	}
	
	@GetMapping("/refno/{refNo}")
	public ResponseEntity<List<Transaction>> getTxnByRefNo(@PathVariable String refNo) {
		return ResponseEntity.ok(service.getTxnByRefNo(refNo));
	}

}
