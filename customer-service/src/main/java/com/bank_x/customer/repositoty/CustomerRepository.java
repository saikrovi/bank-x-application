package com.bank_x.customer.repositoty;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank_x.customer.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
