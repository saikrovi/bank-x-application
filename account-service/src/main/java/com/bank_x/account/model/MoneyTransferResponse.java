package com.bank_x.account.model;

import lombok.Data;

@Data
public class MoneyTransferResponse {
	
	private Long hostAccountNo;
	private Double amount;
	private Long beneAccountNo;
	private String hostAccTxnId;
	private String beneAccTxnId;
	private String refNo;
	private String status;

}
