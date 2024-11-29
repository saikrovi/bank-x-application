package com.bank_x.account.model;

import lombok.Data;

@Data
public class MoneyDepositResponse {
	
	private Long accountNo;
	private Double previousBalance;
	private Double amountCredited;
	private Double interestCredited;
	private Double txnCharges;
	private Double currentBalance;
	private String txnId;
	private String refNo;
	private String status;

}
