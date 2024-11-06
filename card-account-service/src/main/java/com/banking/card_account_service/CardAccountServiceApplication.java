package com.banking.card_account_service;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CardAccountServiceApplication {

	@Autowired
	private RabbitAdmin rabbitAdmin;

	@Autowired
	private Queue queue1;

	@Autowired
	private Queue queue2;

	@PostConstruct
	public void declareQueues() {
		rabbitAdmin.declareQueue(queue1);
		rabbitAdmin.declareQueue(queue2);
	}

	public static void main(String[] args) {
		SpringApplication.run(CardAccountServiceApplication.class, args);
	}

}
