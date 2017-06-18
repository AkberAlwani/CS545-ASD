package org.rentframework.logger;

import java.text.SimpleDateFormat;
import java.util.Date;



public class LoggerImpl implements Logger {

	public LoggerImpl() {

	}

	@Override
	public String warn(String message) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return "WARN[" + dateFormat.format(date) + "] " + message;
	}

	@Override
	public String debug(String message) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return "DEBUG[" + dateFormat.format(date) + "] " + message;
	}

	@Override
	public String info(String message) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return "INFO[" + dateFormat.format(date) + "] " + message;
	}

	@Override
	public String error(String message) {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		return "ERROR[" + dateFormat.format(date) + "] " + message;
	}

}
