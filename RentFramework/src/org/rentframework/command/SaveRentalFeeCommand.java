package org.rentframework.command;

import org.rentframework.command.Command;

public class SaveRentalFeeCommand implements Command {

	@Override
	public boolean execute() {
		return false;
	}

	@Override
	public boolean undo() {
		return false;
	}

}