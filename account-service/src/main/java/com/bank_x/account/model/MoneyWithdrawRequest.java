package com.bank_x.account.model;

import lombok.Data;

@Data
public class MoneyWithdrawRequest {
	
	private Long accountNo;
	private Double amount;
	private String txnPassword;

}
