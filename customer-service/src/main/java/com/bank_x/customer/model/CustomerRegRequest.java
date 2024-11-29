package com.bank_x.customer.model;

import com.bank_x.customer.entity.Customer;

import lombok.Data;

@Data
public class CustomerRegRequest {
	
	private Customer customer;
	private String currentAccTxnPwd;
	private String savingsAccTxnPwd;

}
