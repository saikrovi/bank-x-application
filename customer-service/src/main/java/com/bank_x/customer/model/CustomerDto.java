package com.bank_x.customer.model;

import com.bank_x.customer.entity.Customer;

import lombok.Data;

@Data
public class CustomerDto {
	
	private Customer customer;
	private AccountsOpenResponse bankAccounts;

}
