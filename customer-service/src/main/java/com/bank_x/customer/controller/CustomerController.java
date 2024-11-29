package com.bank_x.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank_x.customer.entity.Customer;
import com.bank_x.customer.model.AccountsOpenRequest;
import com.bank_x.customer.model.AccountsOpenResponse;
import com.bank_x.customer.model.CustomerDto;
import com.bank_x.customer.model.CustomerRegRequest;
import com.bank_x.customer.service.ICustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	
	@Autowired private ICustomerService service;
	@Autowired private RestTemplate restTemplate;
	
	private String accUrl = "http://localhost:9002/account";
//	private String notUtl = "http://localhost:9003/notification";
	
	@PostMapping
	public ResponseEntity<CustomerDto> onboardCustomer(@RequestBody CustomerRegRequest custRegRequest) {
		Customer customer = service.createCustomer(custRegRequest.getCustomer());
		
		AccountsOpenRequest openRequest = new AccountsOpenRequest();
		openRequest.setCustomerId(customer.getCustomerId());
		openRequest.setCurAccTxnPwd(custRegRequest.getCurrentAccTxnPwd());
		openRequest.setSavAccTxnPwd(custRegRequest.getSavingsAccTxnPwd());
		
		CustomerDto dto = new CustomerDto();
		dto.setCustomer(customer);
		dto.setBankAccounts(restTemplate.postForEntity(accUrl, openRequest, AccountsOpenResponse.class).getBody());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}
	
	@GetMapping("/{customerId}")
	public ResponseEntity<Customer> findCustomer(@PathVariable Long customerId) {
		return ResponseEntity.ok(service.getCustomer(customerId));
	}
	
	@GetMapping("/{custId}/custName")
	public ResponseEntity<String> getCustName(@PathVariable Long custId) {
		return ResponseEntity.ok(service.getCustName(custId));
	}

}
