package com.bank_x.customer.model;

import lombok.Data;

@Data
public class AccountsOpenRequest {
	
	private Long customerId;
	private String curAccTxnPwd;
	private String savAccTxnPwd;

}
