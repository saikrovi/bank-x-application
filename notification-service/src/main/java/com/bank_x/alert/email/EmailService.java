package com.bank_x.alert.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bank_x.alert.model.TransactionAlert;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	
	@Autowired private JavaMailSender sender;
	
	public void sendTxnAlert(TransactionAlert alert) throws MessagingException {
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, false);
		helper.setSubject("Bank-X: Transaction Alert");
		helper.setTo(alert.getCustEmail());
		helper.setText("Dear "+alert.getCustName()+",\r\n"
				+ "\r\n"
				+ "A Transaction has been made on your account. Please find the details below:\r\n"
				+ "\r\n"
				+ "Transaction Details:\r\n"
				+ "\r\n"
				+ "Date: "+alert.getDate()+"\r\n"
				+ "Time: "+alert.getTime()+"\r\n"
				+ "Type: "+alert.getType()+"\r\n"
				+ "Amount: "+alert.getTxnAmount()+"\r\n"
				+ "Account Number: "+alert.getAccNo()+" (last 4 digits)\r\n"
				+ "Reference/Description: "+alert.getDesc()+"\r\n"
				+ "Available Balance: "+alert.getAvailBal()+"\r\n"
				+ "If you have not authorized this transaction or need further assistance, please contact us immediately.\r\n"
				+ "\r\n"
				+ "Thank you,\r\n"
				+ "Bank-X\r\n"
				+ "\r\n"
				+ "");
		sender.send(message);
	}

}
