package org.rentframework.command;

public interface Command {
	boolean execute();
	boolean undo();
}
