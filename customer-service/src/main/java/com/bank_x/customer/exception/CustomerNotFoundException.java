package com.bank_x.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND)
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1801103028204075493L;
	
	public CustomerNotFoundException() {
		super();
	}
	
	public CustomerNotFoundException(String message) {
		super(message);
	}

}
