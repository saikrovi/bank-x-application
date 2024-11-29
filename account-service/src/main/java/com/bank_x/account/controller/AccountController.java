package com.bank_x.account.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank_x.account.entity.Account;
import com.bank_x.account.model.AccountsOpenRequest;
import com.bank_x.account.model.AccountsOpenResponse;
import com.bank_x.account.model.InvalidTransactionException;
import com.bank_x.account.model.MoneyDepositRequest;
import com.bank_x.account.model.MoneyDepositResponse;
import com.bank_x.account.model.MoneyTransferRequest;
import com.bank_x.account.model.MoneyTransferResponse;
import com.bank_x.account.model.MoneyWithdrawRequest;
import com.bank_x.account.model.MoneyWithdrawResponse;
import com.bank_x.account.model.Transaction;
import com.bank_x.account.service.IAccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired private IAccountService service;
	@Autowired private RestTemplate restTemplate;
	
	private String txnUrl = "http://localhost:9003/txn";

	@PostMapping
	public ResponseEntity<AccountsOpenResponse> openAccounts(@RequestBody AccountsOpenRequest request) {
		AccountsOpenResponse response = new AccountsOpenResponse();

		Account curAccount = new Account();
		curAccount.setCustomerId(request.getCustomerId());
		curAccount.setAccountType("CURRENT");
		curAccount.setBalance(0d);
		curAccount.setPassword(request.getCurAccTxnPwd());
		// Create CURRENT account
		response.setCurAccountNo(service.createAccount(curAccount).getAccountNo());

		Account savAccount = new Account();
		savAccount.setCustomerId(request.getCustomerId());
		savAccount.setAccountType("SAVINGS");
		savAccount.setBalance(500d); // Account opening bonus
		savAccount.setPassword(request.getSavAccTxnPwd());
		// Create SAVINGS account
		response.setSavAccountNo(service.createAccount(savAccount).getAccountNo());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{accountNo}")
	public ResponseEntity<Account> getAccount(@PathVariable Long accountNo) {
		Account account = service.getAccount(accountNo);
		account.setPassword(null);

		return ResponseEntity.ok(account);
	}

	@PutMapping("/deposit")
	public ResponseEntity<MoneyDepositResponse> depositMoney(@RequestBody MoneyDepositRequest request) {
		
		String refNo = UUID.randomUUID().toString();
		
		MoneyDepositResponse response = service.depositMoney(request);
		
		Transaction txn = new Transaction();
		txn.setAccNo(request.getAccountNo());
		txn.setTxnType("CREDIT");
		txn.setTxnAmount(request.getAmount());
		txn.setTimestamp(LocalDateTime.now());
		txn.setRefNo(refNo);
		txn.setAvailBal(response.getCurrentBalance());
		
		response.setTxnId(restTemplate.postForEntity(txnUrl, txn, String.class).getBody());
		response.setRefNo(refNo);
		
		return ResponseEntity.ok(response);
	}

	@PostMapping("/withdraw")
	public ResponseEntity<MoneyWithdrawResponse> withdrawMoney(@RequestBody MoneyWithdrawRequest request)
			throws InvalidCredentialsException {
		String refNo = UUID.randomUUID().toString();
		
		MoneyWithdrawResponse response = service.withdrawMoney(request);
		
		Transaction txn = new Transaction();
		txn.setAccNo(request.getAccountNo());
		txn.setTxnType("DEBIT");
		txn.setTxnAmount(request.getAmount());
		txn.setTimestamp(LocalDateTime.now());
		txn.setRefNo(refNo);
		txn.setAvailBal(response.getCurrentBalance());
		
		response.setRefNo(refNo);
		response.setTxnId(restTemplate.postForEntity(txnUrl, request, String.class).getBody());
		return ResponseEntity.ok(response);
	}

	@PostMapping("/transfer")
	public ResponseEntity<MoneyTransferResponse> moneyTransfer(@RequestBody MoneyTransferRequest request)
			throws Exception {
		Long hostAccCustId = service.getCustomerId(request.getHostAccountNo());
		Long beneAccCustId = service.getCustomerId(request.getBeneAccountNo());
		String hostAccType = service.getAccountType(request.getHostAccountNo());

		if (hostAccCustId == beneAccCustId || hostAccType.trim().equals("CURRENT".trim())) {
			String refNo = UUID.randomUUID().toString();

			MoneyWithdrawRequest withdrawRequest = new MoneyWithdrawRequest();
			withdrawRequest.setAccountNo(request.getHostAccountNo());
			withdrawRequest.setAmount(request.getAmount());
			withdrawRequest.setTxnPassword(request.getTxnPassword());
			MoneyWithdrawResponse withdrawResponse = service.withdrawMoney(withdrawRequest);
			System.err.println("Money debited from host account");
			
			Transaction txn = new Transaction();
			txn.setTimestamp(LocalDateTime.now());
			txn.setTxnAmount(request.getAmount());
			txn.setRefNo(refNo);
			
			txn.setAccNo(request.getHostAccountNo());
			txn.setTxnType("DEBIT");
			txn.setAvailBal(withdrawResponse.getCurrentBalance());
			String hostAccTxnId = restTemplate.postForEntity(txnUrl, txn, String.class).getBody();

			MoneyDepositRequest depositRequest = new MoneyDepositRequest();
			depositRequest.setAccountNo(request.getBeneAccountNo());
			depositRequest.setAmount(request.getAmount());
			MoneyDepositResponse depositResponse = service.depositMoney(depositRequest);
			System.err.println("Money credited to bene account");
			
			txn.setAccNo(request.getBeneAccountNo());
			txn.setTxnType("CREDIT");
			txn.setAvailBal(depositResponse.getCurrentBalance());
			String bebeAccTxnId = restTemplate.postForEntity(txnUrl, txn, String.class).getBody();

			MoneyTransferResponse response = new MoneyTransferResponse();
			response.setHostAccountNo(request.getHostAccountNo());
			response.setBeneAccountNo(request.getBeneAccountNo());
			response.setAmount(request.getAmount());
			response.setRefNo(refNo);
			response.setHostAccTxnId(hostAccTxnId);
			response.setBeneAccTxnId(bebeAccTxnId);
			response.setStatus("STATUS");

			return ResponseEntity.ok(response);
		} else {
			System.err.println("Invalid transaction");
			throw new InvalidTransactionException("You can not transfer money from savings account...!");
		}

	}
	
	@GetMapping("/{accNo}/custId")
	public ResponseEntity<Long> getCustomerId(@PathVariable Long accNo) {
		return ResponseEntity.ok(service.getCustomerId(accNo));
	}

}
