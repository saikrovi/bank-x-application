package com.bank_x.account.model;

import lombok.Data;

@Data
public class MoneyWithdrawResponse {
	
	private Long accountNo;
	private Double previousBalance;
	private Double amountDebited;
	private Double txnChrgs;
	private Double currentBalance;
	private String txnId;
	private String refNo;
	private String status;

}
