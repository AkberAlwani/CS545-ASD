/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */

package org.rentframework.middlelayer;

import org.rentframework.factory.Person;
import org.rentframework.strategy.NotificationStrategy;


public class Notifier {

	private NotificationStrategy notificationStrategy;

	
	public Notifier(NotificationStrategy notificationStrategy) {
		this.notificationStrategy = notificationStrategy;
	}
	public void notifyPerson(String message, Person person) {
		notificationStrategy.sendNotification(message, person);
	}
}
