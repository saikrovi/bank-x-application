package com.bank_x.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "accounts_tab")
public class Account {

	@Id
	@GeneratedValue(generator = "acc_no_gen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "acc_no_gen", sequenceName = "acc_no_seq", allocationSize = 1, initialValue = 1000000000)
	private Long accountNo;
	private Long customerId;
	private String accountType;
	private Double balance;
	private String password;

}
