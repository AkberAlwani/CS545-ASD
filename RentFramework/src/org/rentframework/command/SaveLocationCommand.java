package org.rentframework.command;
/*
 * Author: Akber
 */

import org.rentframework.core.Location;
import org.rentframework.core.LocationFacade;
import org.rentframework.core.LocationFacadeImpl;
import org.rentframework.command.Command;

public class SaveLocationCommand implements Command {

	private Location location;
	private LocationFacade facade;

	public SaveLocationCommand(Location location) {
		this.location = location;
		this.facade = new LocationFacadeImpl();
	}

	@Override
	public boolean execute() {
		int affectedRows = facade.saveLocation(location);
		return affectedRows == 1 ? true : false;
	}

	@Override
	public boolean undo() {
		int affectedRows = facade.removeLocation(location);
		return affectedRows == 1 ? true : false;

	}

}
