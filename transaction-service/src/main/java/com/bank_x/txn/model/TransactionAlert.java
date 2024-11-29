package com.bank_x.txn.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class TransactionAlert {
	
	private String custName;
	private String custEmail;
	private LocalDate date;
	private LocalTime time;
	private String type;
	private Double txnAmount;
	private Long accNo;
	private String txnId;
	private String refNo;
	private String desc;
	private Double availBal;

}
