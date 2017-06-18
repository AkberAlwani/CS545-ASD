/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */
package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author OWNER
 *
 */
public class DialogHelper {

	public static void toast(String msg, AlertType alertType) {
		Alert alert = new Alert(alertType);
		alert.setTitle(alertType.name());
		alert.setHeaderText(msg);
		alert.showAndWait();
	}

}
