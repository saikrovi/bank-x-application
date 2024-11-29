package com.bank_x.txn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, value = HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3461319348711076215L;
	
	public TransactionNotFoundException() {
		super();
	}
	
	public TransactionNotFoundException(String message) {
		super(message);
	}

}
