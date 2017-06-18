package utils;

import javafx.scene.control.TextField;
import model.FormException;

/**
 * @author Akber
 *
 */
public class Validator {
	public static void validateNumber(TextField field) throws FormException {

		if (!field.getText().toString().matches("^[1-9]\\d*(\\.\\d+)?$"))
			throw new FormException(field.getId() + " field must be numeric");
	}

	public static void validateBlank(TextField field) throws FormException {
		if (field.getText().toString().isEmpty()) {
			throw new FormException("One or more fields are empty");
		}
	}

	public static void validateEmail(TextField field) throws FormException {
		if (!field.getText().toString().matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"))
			throw new FormException(field.getId() + " field must have valid email");

	}
}
