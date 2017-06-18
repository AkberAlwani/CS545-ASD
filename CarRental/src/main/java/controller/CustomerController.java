/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.rentframework.command.CommandManager;
import org.rentframework.command.CommandManagerImpl;
import org.rentframework.core.CustomerFacade;
import org.rentframework.core.CustomerFacadeImpl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Address;
import model.AppCustomer;
import model.FormException;
import utils.DialogHelper;
import utils.Validator;
import view.MainForm;

@SuppressWarnings("unused")
public class CustomerController extends Application implements Initializable {

	@FXML
	private TextField txtFirstName;
	@FXML
	private TextField txtiddleName;
	@FXML
	private TextField txtLastName;
	@FXML
	private TextField txtEmail;
	@FXML
	private TextField txtPhoneNumber;
	@FXML
	private TextField txtStreet;
	@FXML
	private TextField txtCity;
	@FXML
	private TextField txtZipCode;
	@FXML
	private TextField txtState;

	@FXML
	private Text txtErrorMessag;

	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;

	private Stage primaryStage;
	
	private Stage rootStage = new Stage();

	private CommandManager commandmgr;
	public static int customerId;
	public static int addressId ;
	private CustomerListController manageController;

	public CustomerController() {

		this.commandmgr = new CommandManagerImpl();
	}

	public CustomerController(int customerId,int addressId) {
		CustomerController.customerId = customerId;
		CustomerController.addressId = addressId;

		this.commandmgr = new CommandManagerImpl();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.rootStage = primaryStage;
		this.primaryStage.setTitle("Add Customer Form");
		try {
			FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("CustomerForm.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
			Scene scene = new Scene(page);
			Stage ps = new Stage();
			ps.setScene(scene);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@FXML
	protected void addCustomer(ActionEvent event) throws Exception {

		try {
			AppCustomer customer = new AppCustomer();
			customer.setFirstName(txtFirstName.getText());
			customer.setMiddleName(txtiddleName.getText());
			customer.setLastName(txtLastName.getText());
			customer.setEmail(txtEmail.getText());
			customer.setPhone(txtPhoneNumber.getText());
			Address userAddress = new Address(txtStreet.getText(), txtCity.getText(),
					Integer.parseInt(txtZipCode.getText().trim()), txtStreet.getText());
			
			if (customerId > 0){	//if it is edit
                userAddress.setAddressId(addressId);
				customer.setPersonId(customerId);
			}			
			customer.setAddress(userAddress);
			this.commandmgr.saveCustomer(customer);
			((Node) (event.getSource())).getScene().getWindow().hide();
			backToMaageController();
		} catch (FormException ex) {
			DialogHelper.toast(ex.getMessage(), AlertType.ERROR);
		}

	}

	/**
	 * Action will be performed when the user click cancel button
	 * 
	 * @throws Exception
	 */
	@FXML
	protected void cancelHandler(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	private void initCustomerInfo() {

		try {
			CustomerFacade custFacade = new CustomerFacadeImpl();
			ResultSet result = custFacade.getCustomerById(customerId);
			while (result.next()) {
				txtFirstName.setText(result.getString("firstName"));
				txtiddleName.setText(result.getString("middleName"));
				txtLastName.setText(result.getString("lastName"));
				txtEmail.setText(result.getString("email"));
				txtPhoneNumber.setText(result.getString("phone"));
				ResultSet address = custFacade.getAddressByCustomerId(customerId, Address.class);
				while (address.next()) {
					addressId = address.getInt("addressId");
					txtStreet.setText(address.getString("streetAddress"));
					txtCity.setText(address.getString("city"));
					txtZipCode.setText("" + address.getInt("zipCode"));
					txtState.setText(address.getString("state"));
				}

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (customerId > 0)
			initCustomerInfo();

	}

	private void backToMaageController() throws Exception {
		manageController = new CustomerListController();
		Stage stage = new Stage();
		manageController.start(stage);
	}

}
