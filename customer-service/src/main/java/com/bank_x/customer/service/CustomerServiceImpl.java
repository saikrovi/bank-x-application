package com.bank_x.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank_x.customer.entity.Customer;
import com.bank_x.customer.exception.CustomerNotFoundException;
import com.bank_x.customer.repositoty.CustomerRepository;

@Service
public class CustomerServiceImpl implements ICustomerService {
	
	@Autowired private CustomerRepository repository;

	@Override
	public Customer createCustomer(Customer customer) {
		return repository.save(customer);
	}

	@Override
	public Customer getCustomer(Long customerId) {
		return repository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found...!"));
	}

	@Override
	public String getCustName(Long accNo) {
		return getCustomer(accNo).getName();
	}

}
