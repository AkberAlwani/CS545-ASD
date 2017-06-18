package org.rentframework.strategy;

import org.rentframework.factory.Person;
import org.rentframework.middlelayer.Message;
import org.rentframework.middlelayer.TextMessage;
import org.rentframework.middlelayer.TextMessageSender;

public class SMSNotificationStrategy implements NotificationStrategy {

	private Message message;

	public SMSNotificationStrategy() {
		this.message = new TextMessage(new TextMessageSender());
	}

	@Override
	public void sendNotification(String message, Person person) {

		this.message.setBody(message);
		this.message.setPerson(person);
		this.message.send();
	}

}