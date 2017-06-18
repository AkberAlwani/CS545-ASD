/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */
package controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.rentframework.logger.Logger;
import org.rentframework.core.OrderRecordEntry;
import org.rentframework.logger.ConsoleLogger;
import org.rentframework.logger.LoggerImpl;
import org.rentframework.middlelayer.OrderTransactionManager;
import org.rentframework.middlelayer.TransactionManager;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Vehicle;
import model.OrderCart;
import utils.DialogHelper;

/**
 * @author Akber Ali
 *
 */
public class OrderPaymentController extends Application implements Initializable {

	@FXML
	private Label lblCustomerName;

	@FXML
	private TextField txtTotalRentalFee;

	@FXML
	private TextField dueDateTxt;

	@FXML
	private VBox dueDateVBox;

	@FXML
	private TableColumn<Vehicle, String> nameCol;

	@FXML
	private TableColumn<Vehicle, String> modelCol;

	@FXML
	private TableColumn<Vehicle, Integer> yearCol;

	@FXML
	private TableColumn<Vehicle, Double> feeCol;

	@FXML
	private TableColumn<Vehicle, Number> quantityCol;

	@FXML
	private TableView<Vehicle> cartTable;
	ObservableList<Vehicle> cartObservable = FXCollections.observableArrayList();
	private Logger logger;
	public static OrderCart cart = null;

	public OrderPaymentController() {
		logger = new ConsoleLogger(new LoggerImpl());
	}

	public OrderPaymentController(OrderCart cartCar) {
		cart = cartCar;
		logger = new ConsoleLogger(new LoggerImpl());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/OrderPaymentForm.fxml"));
		Scene scene = new Scene(root);
		// scene.getStylesheets().add("view/style.css");
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setResizable(true);
		primaryStage.setTitle("Total Fee Payment");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	protected void btnCancelAction(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	protected void btnPayAction(ActionEvent event) throws Exception {
		ObservableList<Vehicle> cars = cartTable.getItems();
		LocalDate dueDate = ((DatePicker) dueDateVBox.getChildren().get(0)).getValue();
		int customerId = cart.getCustomerId();

		List<OrderRecordEntry> entries = new ArrayList<>();
		long diff = getTotalDays(LocalDate.now(), dueDate);
		if (diff < 0) {
			DialogHelper.toast("Due date can not be past date", AlertType.WARNING);
			
		} else if (diff > 0) {
			for (Vehicle car : cars) {

				OrderRecordEntry entry = new OrderRecordEntry();
				entry.setOrderDate(LocalDate.now());
				entry.setDueDate(dueDate);
				entry.setCustomerId(customerId);
				entry.setProductId(car.getProductId());
				entry.setQuantity(car.getQuantity());
				double fee = car.getDailyFee()* car.getQuantity() * diff;
				entry.setDailyFee(fee);
				entries.add(entry);
			}

			TransactionManager txn = new OrderTransactionManager();
			txn.proceedTransaction(entries, Vehicle.class);
			DialogHelper.toast("Order record saved successfully!", AlertType.INFORMATION);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		cartTable.setEditable(true);
		if (cart != null) {

			DatePicker checkInDatePicker = new DatePicker();
			checkInDatePicker.setValue(LocalDate.now());
			dueDateVBox.getChildren().add(checkInDatePicker);

			lblCustomerName.setText(cart.getCustomerName());
			getTableData();
			txtTotalRentalFee.setText("" + getTotalFee());
		}
	}

	private void getTableData() {
		cartTable.getItems().clear();
		cart.getCars().forEach(new Consumer<Vehicle>() {
			@Override
			public void accept(Vehicle t) {
				t.setQuantity(1);
			}
		});
		cartObservable.addAll(cart.getCars());
		populateTable();
	}

	private void populateTable() {

		nameCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("productName"));
		nameCol.setSortType(SortType.ASCENDING);
		modelCol.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
		yearCol.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("year"));
		feeCol.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("dailyFee"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<Vehicle, Number>("quantity"));

		quantityCol.setCellFactory(new Callback<TableColumn<Vehicle, Number>, TableCell<Vehicle, Number>>() {
			@Override
			public TableCell<Vehicle, Number> call(TableColumn<Vehicle, Number> cell) {
				return new IntegerEditingCell();
			}
		});
		cartTable.setItems(cartObservable);

	}

	public class IntegerEditingCell extends TableCell<Vehicle, Number> {

		private final TextField textField = new TextField();
		private final Pattern intPattern = Pattern.compile("-?\\d+");

		public IntegerEditingCell() {
			textField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
				if (!isNowFocused) {
					processEdit();
				}
			});
			textField.setOnAction(event -> processEdit());
		}

		private void processEdit() {
			String text = textField.getText();
			if (intPattern.matcher(text).matches() && Integer.parseInt(text) > 0) {
				commitEdit(Integer.parseInt(text));
			} else {
				cancelEdit();
			}
		}

		@Override
		public void updateItem(Number value, boolean empty) {
			super.updateItem(value, empty);
			if (empty) {
				setText(null);
				setGraphic(null);
			} else if (isEditing()) {
				setText(null);
				textField.setText(value.toString());
				setGraphic(textField);
			} else {
				setText(value.toString());
				setGraphic(null);
			}
		}

		@Override
		public void startEdit() {
			super.startEdit();
			Number value = getItem();
			if (value != null) {
				textField.setText(value.toString());
				setGraphic(textField);
				setText(null);
			}
		}

		@Override
		public void cancelEdit() {
			super.cancelEdit();
			setText(getItem().toString());
			setGraphic(null);
		}

		@Override
		public void commitEdit(Number value) {

			super.commitEdit(value);
			((Vehicle) this.getTableRow().getItem()).setQuantity(value.intValue());
			txtTotalRentalFee.setText("" + getTotalFee());
		}
	}

	private double getTotalFee() {

		ObservableList<Vehicle> cars = cartTable.getItems();
		double totalFee = 0.0;
		for (Vehicle car : cars) {
			totalFee += car.getDailyFee()* car.getQuantity();
		}

		LocalDate dueDate = ((DatePicker) dueDateVBox.getChildren().get(0)).getValue();
		return totalFee * getTotalDays(LocalDate.now(), dueDate);
	}

	private long getTotalDays(LocalDate start, LocalDate end) {
		long diff = ChronoUnit.DAYS.between(start, end) + 1;
		if (diff < 0) {
			DialogHelper.toast("Due date can not be past date", AlertType.WARNING);
			return 0;
		}
		return diff;
	}

}
