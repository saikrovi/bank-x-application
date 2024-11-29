package com.bank_x.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InsufficientBalanceException extends RuntimeException {

	private static final long serialVersionUID = -1197779054471977238L;
	
	public InsufficientBalanceException() {
		super();
	}
	
	public InsufficientBalanceException(String message) {
		super(message);
	}

}
