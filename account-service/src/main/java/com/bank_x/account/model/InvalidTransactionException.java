package com.bank_x.account.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class InvalidTransactionException extends RuntimeException {

	private static final long serialVersionUID = 8648883394546961812L;
	
	public InvalidTransactionException() {
		super();
	}
	
	public InvalidTransactionException(String message) {
		super(message);
	}

}
