package com.bank_x.account.model;

import lombok.Data;

@Data
public class MoneyTransferRequest {
	
	private Long hostAccountNo;
	private Double amount;
	private String txnPassword;
	private Long beneAccountNo;

}
