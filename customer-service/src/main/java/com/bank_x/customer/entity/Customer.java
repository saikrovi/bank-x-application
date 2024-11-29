package com.bank_x.customer.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "customers_tab")
public class Customer {
	
	@Id
	@GeneratedValue(generator="cust_id_gen", strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="cust_id_gen", sequenceName="cust_id_seq", initialValue=1000000, allocationSize=1)
	private Long customerId;
	private String name;
	private String email;

}
