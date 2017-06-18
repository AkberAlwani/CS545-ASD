package org.rentframework.logger;

public interface Logger {

	String warn(String message);
	String debug(String message);
	String info(String message);
	String error(String message);
}
