package com.bank_x.customer.service;

import com.bank_x.customer.entity.Customer;

public interface ICustomerService {
	
	public Customer createCustomer(Customer customer);
	public Customer getCustomer(Long customerId);
	
	public String getCustName(Long accNo);

}
