package org.rentframework.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import org.rentframework.utils.ConfigProperties;
import org.rentframework.utils.ConfigPropertiesImpl;

public class FileLogger extends LoggerDecorator {

	private ConfigProperties config;

	private FileOutputStream logWriter;


	public FileLogger(Logger logger) {
		super(logger);
		config = new ConfigPropertiesImpl("logger.properties");
		String fileName = config.readProperty("log.file");

		// it will create new file if not exists and append the log
		File logFile = new File(fileName);
		if (!logFile.exists()) {
			try {
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			logWriter = new FileOutputStream(logFile, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String warn(String message) {
		logToFile(message);
		return super.warn(message);
	}

	@Override
	public String debug(String message) {
		logToFile(message);
		return super.debug(message);

	}

	@Override
	public String info(String message) {
		logToFile(message);
		return super.info(message);
	}

	@Override
	public String error(String message) {
		logToFile(message);
		return super.error(message);
	}

	private void logToFile(String message) {
		if (logWriter != null) {
			try {
				logWriter.write(message.getBytes());
				logWriter.flush();
				logWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
