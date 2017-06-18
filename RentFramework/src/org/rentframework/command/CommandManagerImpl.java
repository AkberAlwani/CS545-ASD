package org.rentframework.command;

import java.util.List;
import java.util.Stack;

import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.Customer;
import org.rentframework.core.Product;
import org.rentframework.middlelayer.SysUser;

public class CommandManagerImpl implements CommandManager {

	private Stack<Command> commands = new Stack<Command>();
	private Command currentCommand = null;

	@Override
	public boolean saveCustomer(Customer customer) {

		currentCommand = new SaveCustomerCommand(customer);

		if (currentCommand.execute()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveProduct(Product product) {

		currentCommand = new SaveProductCommand(product);
		if (currentCommand.execute()) {
			// commands.push(currentCommand);
			return true;
		}
		return false;
	}

	@Override
	public boolean saveUser(SysUser user) {

		currentCommand = new SaveUserCommand(user);
		if (currentCommand.execute()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean saveCheckoutRecords(List<OrderRecordEntry> orderRecordEntries) {

		commands.clear();
		for (OrderRecordEntry entry : orderRecordEntries) {
			currentCommand = new ReturnCommand(entry);
			if (currentCommand.execute()) {
				commands.push(currentCommand);
				System.out.println("executed");
			} else {
				System.out.println("not executed");
				while (commands.size() > 0) {
					currentCommand = commands.pop();
					currentCommand.undo();

				}
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean checkReturnRecords(List<OrderRecordEntry> returnEntries) {

		commands.clear();
		for (OrderRecordEntry entry : returnEntries) {
			currentCommand = new OrderCommand(entry);
			System.out.println("before execution");
			if (currentCommand.execute()) {
				System.out.println("executing");
				commands.push(currentCommand);
			} else {
				while (commands.size() > 0) {
					currentCommand = commands.pop();
					currentCommand.undo();

				}
				return false;
			}
		}
		return true;
	}

	
}
