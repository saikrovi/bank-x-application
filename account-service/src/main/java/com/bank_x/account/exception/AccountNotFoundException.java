package com.bank_x.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3657579131256318111L;
	
	public AccountNotFoundException() {
		super();
	}
	
	public AccountNotFoundException(String message) {
		super(message);
	}

}
