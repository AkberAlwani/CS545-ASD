package org.rentframework.command;

import org.rentframework.core.Product;
import org.rentframework.core.ProductFacade;
import org.rentframework.core.ProductFacadeImpl;
import org.rentframework.command.Command;

public class SaveProductCommand implements Command {

	private Product product;
	private ProductFacade facade;

	public SaveProductCommand(Product product) {
		this.product = product;
		facade = new ProductFacadeImpl();
	}

	@Override
	public boolean execute() {
		int affectedRows = facade.saveProduct(product);
		return affectedRows == 1 ? true : false;
	}

	@Override
	public boolean undo() {
		int affectedRows = facade.removeProduct(product);
		return affectedRows == 1 ? true : false;
	}

}