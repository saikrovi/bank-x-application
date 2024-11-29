package com.bank_x.txn.entity;

import java.time.LocalDateTime;
import java.util.Random;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "transactions_tab")
public class Transaction {
	
	@Id
	private String txnId;
	private LocalDateTime timestamp;
	private Long accNo;
	private String txnType;
	private Double txnAmount;
	private Double availBal;
	private String refNo;
	
	@PrePersist
	public void setTxnId() {
		if(txnId==null) {
			LocalDateTime date = LocalDateTime.now();
			txnId = "TXN"+date.getYear()+date.getMonthValue()+date.getDayOfMonth()+date.getHour()
					+date.getMinute()+date.getSecond()+new Random().nextInt(100, 999);
		}
	}

}
