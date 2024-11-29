package com.bank_x.account.service;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank_x.account.entity.Account;
import com.bank_x.account.exception.AccountNotFoundException;
import com.bank_x.account.exception.InsufficientBalanceException;
import com.bank_x.account.model.MoneyDepositRequest;
import com.bank_x.account.model.MoneyDepositResponse;
import com.bank_x.account.model.MoneyWithdrawRequest;
import com.bank_x.account.model.MoneyWithdrawResponse;
import com.bank_x.account.repository.AccountRepository;

@Service
public class AccountServiceImpl implements IAccountService {

	@Autowired private AccountRepository repository;

	@Override
	public Account createAccount(Account account) {
		return repository.save(account);
	}

	@Override
	public Account getAccount(Long accountNo) {
		return repository.findById(accountNo).orElseThrow(() -> new AccountNotFoundException("Account not found...!"));
	}

	@Override
	public Long getCustomerId(Long accountNo) {
		Long accNo = repository.getCustomerIdByAccountNo(accountNo);
		if (accNo != null)
			return accNo;
		else
			throw new AccountNotFoundException("Account not found...!");
	}
	
	@Override
	public String getAccountType(Long accountNo) {
		String accountType = repository.getAccountTypeByAccountNo(accountNo);
		if(accountType!=null)
			return accountType;
		else
			throw new AccountNotFoundException("Account not found...!");
	}

	@Override
	public MoneyDepositResponse depositMoney(MoneyDepositRequest depositRequest) {
		Account account = getAccount(depositRequest.getAccountNo());
		// Variables
		Double prevBal = account.getBalance();
		Double amount = depositRequest.getAmount();
		Double interest = 0.0;
		Double txnChrgs = (0.05 * amount) / 100;
		if (account.getAccountType().equals("SAVINGS"))
			interest = (0.5 * amount) / 100;
		Double curBal = prevBal + amount + interest - txnChrgs;
		// Update balance
		account.setBalance(curBal);
		// Save
		createAccount(account);
		// Response
		MoneyDepositResponse depositResponse = new MoneyDepositResponse();
		depositResponse.setAccountNo(depositRequest.getAccountNo());
		depositResponse.setPreviousBalance(prevBal);
		depositResponse.setInterestCredited(interest);
		depositResponse.setTxnCharges(txnChrgs);
		depositResponse.setCurrentBalance(curBal);
		depositResponse.setAmountCredited(depositRequest.getAmount());
		depositResponse.setStatus("SUCCESS");
		return depositResponse;
	}

	@Override
	public MoneyWithdrawResponse withdrawMoney(MoneyWithdrawRequest withdrawRequest)
			throws InvalidCredentialsException {
		Account account = getAccount(withdrawRequest.getAccountNo());
		Double prevBal = account.getBalance();
		Double amount = withdrawRequest.getAmount();
		Double txnChrg = (0.05 * amount) / 100;
		
		if(withdrawRequest.getTxnPassword().equals(account.getPassword())) {
			if (prevBal >= (amount + txnChrg)) {
				Double currBal = prevBal - (amount + txnChrg);
				
				account.setBalance(currBal);
				createAccount(account);

				MoneyWithdrawResponse withdrawResponse = new MoneyWithdrawResponse();
				withdrawResponse.setAccountNo(withdrawRequest.getAccountNo());
				withdrawResponse.setPreviousBalance(prevBal);
				withdrawResponse.setAmountDebited(amount);
				withdrawResponse.setTxnChrgs(txnChrg);
				withdrawResponse.setCurrentBalance(currBal);
				withdrawResponse.setStatus("SUCCESS");

				return withdrawResponse;
			} else {
				throw new InsufficientBalanceException("Insufficient balance...!");
			}
		} else {
			throw new InvalidCredentialsException("Incorrect password...!");
		}
	}

}
