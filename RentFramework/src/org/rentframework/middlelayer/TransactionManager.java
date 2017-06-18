
package org.rentframework.middlelayer;

import java.util.List;

import org.rentframework.core.OrderRecordEntry;


public abstract class TransactionManager {
	public final void proceedTransaction(List<OrderRecordEntry> orderRecordEntries, Class<?> productClass) {
		List<OrderRecordEntry> dailyFeeorFine = calculateFeeOrFine(orderRecordEntries, productClass);
		processOrderRecord(orderRecordEntries);
		try {
			sendNotification(orderRecordEntries, productClass);
		} catch (Exception ex) {

		}

		printBill(dailyFeeorFine);
	}

	protected abstract List<OrderRecordEntry> calculateFeeOrFine(
			List<OrderRecordEntry> orderRecordEntries, Class<?> productClass);

	protected abstract void processOrderRecord(List<OrderRecordEntry> orderRecordEntries);
	protected abstract void sendNotification(List<OrderRecordEntry> orderRecordEntries, Class<?> productClass);
	protected void printBill(List<OrderRecordEntry> orderRecordEntries) {
		StringBuilder builder = new StringBuilder();

		builder.append("\n\n---------------------------------------\n");
		builder.append("========Rental System Bill=========\n");
		for (OrderRecordEntry orderRecordEntry : orderRecordEntries) {
			builder.append("Your total Rental Fee is : " + orderRecordEntry.getDailyFee());
			builder.append("\nYour total Rental Fine is : " + orderRecordEntry.getDailyFine());
		}
		builder.append("\n---------------------------------------\n");

		System.out.println(builder.toString());
	}
}
