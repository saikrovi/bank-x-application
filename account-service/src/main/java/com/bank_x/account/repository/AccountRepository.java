package com.bank_x.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bank_x.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT customerId FROM Account WHERE accountNo=:accountNo")
	public Long getCustomerIdByAccountNo(Long accountNo);

	@Query("SELECT balance FROM Account WHERE accountNo=:accountNo")
	public Double getAccountBalanceByAccountNo(Long accountNo);
	
	@Query("SELECT accountType FROM Account WHERE accountNo=:accountNo")
	public String getAccountTypeByAccountNo(Long accountNo);

	@Query("UPDATE Account SET balance=:balance WHERE accountNo=:accountNo")
	public Integer updateBalanceByAccountNo(Long accountNo, Double balance);

	@Query("SELECT password FROM Account WHERE accountNo=:accountNo")
	public String getPasswordByAccountNo(Long accountNo);

}
