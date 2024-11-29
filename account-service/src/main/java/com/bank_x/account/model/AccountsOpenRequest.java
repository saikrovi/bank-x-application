package com.bank_x.account.model;

import lombok.Data;

@Data
public class AccountsOpenRequest {
	
	private Long customerId;
	private String curAccTxnPwd;
	private String savAccTxnPwd;

}
