package com.bank_x.account.service;

import org.apache.hc.client5.http.auth.InvalidCredentialsException;

import com.bank_x.account.entity.Account;
import com.bank_x.account.model.MoneyDepositRequest;
import com.bank_x.account.model.MoneyDepositResponse;
import com.bank_x.account.model.MoneyWithdrawRequest;
import com.bank_x.account.model.MoneyWithdrawResponse;

public interface IAccountService {

	public Account createAccount(Account account);
	public Account getAccount(Long accountNo);
	
	public Long getCustomerId(Long accountNo);
	public String getAccountType(Long accountNo);

	public MoneyDepositResponse depositMoney(MoneyDepositRequest depositRequest);
	public MoneyWithdrawResponse withdrawMoney(MoneyWithdrawRequest withdrawRequest) throws InvalidCredentialsException;
	
}
