package com.bank_x.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank_x.alert.email.EmailService;
import com.bank_x.alert.model.TransactionAlert;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/alert")
public class NotificationController {
	
	@Autowired private EmailService emailService;
	
	@PostMapping("/txn")
	public void sendTxnAlert(@RequestBody TransactionAlert alert) {
		try {
			emailService.sendTxnAlert(alert);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
