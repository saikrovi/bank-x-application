package com.bank_x.account.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transaction {
	
	private String txnId;
	private LocalDateTime timestamp;
	private Long accNo;
	private String txnType;
	private Double txnAmount;
	private Double availBal;
	private String refNo;

}
