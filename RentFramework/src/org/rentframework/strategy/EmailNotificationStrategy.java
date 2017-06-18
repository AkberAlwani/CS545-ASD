package org.rentframework.strategy;

import org.rentframework.factory.Person;
import org.rentframework.middlelayer.EmailMessage;
import org.rentframework.middlelayer.EmailMessageSender;
import org.rentframework.middlelayer.Message;

public class EmailNotificationStrategy implements NotificationStrategy {

	private Message message;

	public EmailNotificationStrategy() {
		this.message = new EmailMessage(new EmailMessageSender());
	}

	@Override
	public void sendNotification(String message, Person person) {

		this.message.setBody(message);
		this.message.setPerson(person);
		this.message.send();
	}

}
