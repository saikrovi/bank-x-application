package com.bank_x.account.model;

import lombok.Data;

@Data
public class MoneyDepositRequest {
	
	private Long accountNo;
	private Double amount;

}
