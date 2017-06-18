/**
 * Copyright 2016 the original author or authors.
 * 
 * Licensed under the MIT License (MIT);
 */
package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.rentframework.command.CommandManager;
import org.rentframework.command.CommandManagerImpl;
import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.OrderRecordFacade;
import org.rentframework.core.OrderRecordFacadeImpl;
import org.rentframework.core.CustomerFacade;
import org.rentframework.core.CustomerFacadeImpl;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.AppCustomer;
import model.Vehicle;
import model.OrderData;
import model.KeyValuePair;
import utils.DialogHelper;

/**
 * @author Akber
 *
 */
public class ReturnFormController extends Application implements Initializable {
	@FXML
	private ComboBox<KeyValuePair<Integer, String>> customerCombo;
	@FXML
	private TableView<OrderData> checkinRecordTable;
	@FXML
	private TableColumn<OrderData, String> name;
	@FXML
	private TableColumn<OrderData, String> model;
	@FXML
	private TableColumn<OrderData, Double> dailyFee;
	@FXML
	private TableColumn<OrderData, Double> dailyFine;
	@FXML
	private TableColumn<OrderData, Double> rentalFee;
	@FXML
	private TableColumn<OrderData, String> dueDate;

	private CustomerFacade facade;
	private OrderRecordFacade orderFacade;
	private ObservableList<KeyValuePair<Integer, String>> customerListObservable = FXCollections.observableArrayList();

	public ReturnFormController() {
		new CommandManagerImpl();
		facade = new CustomerFacadeImpl();
		orderFacade = new OrderRecordFacadeImpl();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("view/ReturnForm.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add("view/style.css");
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setResizable(true);
		primaryStage.setTitle("Vehicle Return Form");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void onCustomerSelected() {
		ObservableList<OrderRecordEntry> entries = FXCollections.observableArrayList();
		ObservableList<OrderData> data = FXCollections.observableArrayList();

		KeyValuePair<Integer, String> selectedCustomer = customerCombo.getSelectionModel().getSelectedItem();
		ResultSet recordEntries = orderFacade.getAllOrderRecordsByCustomer(selectedCustomer.getKey(),
				OrderRecordEntry.class, Vehicle.class);
		try {
			while (recordEntries.next()) {
				OrderData returnData = new OrderData();
				returnData.setModel(recordEntries.getString("model"));
				returnData.setProductName(recordEntries.getString("productName"));
				returnData.setDailyFee(recordEntries.getDouble("dailyFee"));
				returnData.setDailyFine(recordEntries.getDouble("dailyFine"));
				returnData.setDueDate(recordEntries.getDate("dueDate").toLocalDate());
				returnData.setOrderDate(recordEntries.getDate("orderDate").toLocalDate());
				returnData.setOrderId(recordEntries.getInt("orderId"));
				returnData.setQuantity(recordEntries.getInt("quantity"));
				returnData.setPersonId(recordEntries.getInt("personId"));
				returnData.setProductId(recordEntries.getInt("productId"));
				returnData.setReturned(recordEntries.getBoolean("isReturned"));

				if (!returnData.isReturned())
					data.add(returnData);
				// entries.add(orderRecordEntries);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		populateTable(data);
	}

	public void populateTable(ObservableList<OrderData> returnRecords) {
		name.setCellValueFactory(new PropertyValueFactory<OrderData, String>("productName"));
		model.setCellValueFactory(new PropertyValueFactory<OrderData, String>("model"));
		dailyFee.setCellValueFactory(new PropertyValueFactory<OrderData, Double>("dailyFee"));
		dailyFine.setCellValueFactory(new PropertyValueFactory<OrderData, Double>("dailyFine"));
		rentalFee.setCellValueFactory(new PropertyValueFactory<OrderData, Double>("rentalFee"));
		dueDate.setCellValueFactory(new PropertyValueFactory<OrderData, String>("dueDate"));

		if (returnRecords != null)
			checkinRecordTable.setItems(returnRecords);
	}

	
	public void initialize(URL location, ResourceBundle resources) {
//		ObservableList<Vehicle> customers = FXCollections.observableArrayList();
		try {
			ResultSet rs = facade.getAllCustomers(AppCustomer.class);
			while (rs.next()) {
				int id = rs.getInt("customerId");
				String firstName = rs.getString("firstName");
				String middleName = rs.getString("middleName");
				String lName = rs.getString("lastName");
				String content = firstName + " " + middleName + " " + lName;
				KeyValuePair<Integer, String> data = new KeyValuePair<Integer, String>();
				data.setKey(id);
				data.setValue(content);
				customerListObservable.add(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.customerCombo.setPromptText("--Choose for Return");
		this.customerCombo.setItems(customerListObservable);

		checkinRecordTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
	}

	@FXML
	protected void btnReturnHandler(ActionEvent event) throws Exception {

		ObservableList<OrderData> vehiclereturn = checkinRecordTable.getSelectionModel().getSelectedItems();
		for (OrderData checkin : vehiclereturn) {
			System.out.println(checkin.toString());
		}
		if (vehiclereturn.size() < 1) {
			DialogHelper.toast("Please, select a record!", AlertType.WARNING);
			return;
		}

		List<OrderRecordEntry> entries = new ArrayList<>();
		for (OrderData vehiclein : vehiclereturn) {
			OrderRecordEntry checkOutRecordEntry = new OrderRecordEntry();
			checkOutRecordEntry.setOrderId(vehiclein.getOrderId());
			checkOutRecordEntry.setCustomerId(vehiclein.getCustomerId());
			checkOutRecordEntry.setPersonId(vehiclein.getPersonId());
			checkOutRecordEntry.setQuantity(vehiclein.getQuantity());
			checkOutRecordEntry.setDailyFee(vehiclein.getRentalFee());
			checkOutRecordEntry.setDailyFine(vehiclein.getDailyFine());
			checkOutRecordEntry.setDueDate(vehiclein.getDueDate());
			checkOutRecordEntry.setReturnedDate(LocalDate.now());
			checkOutRecordEntry.setReturnedDate(vehiclein.getReturnedDate());
			checkOutRecordEntry.setReturned(true);
			checkOutRecordEntry.setProductId(vehiclein.getProductId());

			long loanDays = java.time.temporal.ChronoUnit.DAYS.between(checkOutRecordEntry.getDueDate(),
					LocalDate.now());
			System.out.println("TODAY: " + LocalDate.now());
			System.out.println("DUEDATE: " + checkOutRecordEntry.getDueDate());
			if (loanDays > 0) {
				double dailyFine = loanDays * vehiclein.getQuantity() * vehiclein.getDailyFine();
				checkOutRecordEntry.setDailyFine(dailyFine);
			}
			entries.add(checkOutRecordEntry);
		}

		Stage stage = new Stage();
		ReturnPaymentController controller = new ReturnPaymentController(entries);
		controller.start(stage);
	}

	@FXML
	protected void btnCancelHandler(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}
}
