package org.rentframework.strategy;

import org.rentframework.factory.Person;

public interface NotificationStrategy {

	void sendNotification(String message, Person person);
}
