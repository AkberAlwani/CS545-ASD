package org.rentframework.command;

import org.rentframework.core.UserFacade;
import org.rentframework.core.UserFacadeImpl;
import org.rentframework.command.Command;
import org.rentframework.middlelayer.SysUser;

public class SaveUserCommand implements Command {

	private UserFacade facade;
	private SysUser user;

	public SaveUserCommand(SysUser user) {
		this.user = user;
		this.facade = new UserFacadeImpl();
	}

	@Override
	public boolean execute() {
		int affectedRows = facade.saveUser(user);
		return affectedRows == 1 ? true : false;
	}

	@Override
	public boolean undo() {
		int affectedRows = facade.removeUser(user);
		return affectedRows == 1 ? true : false;
	}

}

