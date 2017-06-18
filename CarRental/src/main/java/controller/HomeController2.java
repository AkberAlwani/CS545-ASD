/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */
package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.net.URL;


import java.util.ResourceBundle;

import org.rentframework.core.OrderRecordEntry;
import org.rentframework.core.OrderRecordFacade;
import org.rentframework.core.OrderProtectionProxy;
import org.rentframework.logger.Logger;
import org.rentframework.logger.ConsoleLogger;
import org.rentframework.logger.LoggerImpl;
import org.rentframework.utils.LoginRoles;
import org.rentframework.utils.LoginAccess;
import org.rentframework.utils.SessionCache;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Menu;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.KeyValuePair;

/**
 * @author Akber Ali
 *
 */

public class HomeController2 extends Application implements Initializable {

	private Logger logger;
	@FXML
	private MenuBar menubar;
	private OrderRecordFacade facade;
	@FXML
	private Menu carMenu;

	@FXML
	private Menu userMenu;
	@FXML
	private MenuItem mnuListUser;

	@FXML
	private MenuItem mnuAddUser;

	@FXML
	private Menu customerMenu;

	@FXML
	private Menu transactionMenu;

	@FXML
	private ImageView imageView;
	static Stage stage;
//	private ObservableList<String> monthNames = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
		Scene scene = new Scene(root,500,500);
		scene.getStylesheets().add("view/style.css");
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setResizable(true);
		primaryStage.setTitle("[Home] Car Rental System");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public HomeController2() {
		this.logger = new ConsoleLogger(new LoggerImpl());
		this.facade = new OrderProtectionProxy();
	}
	
	@FXML
	protected void listLocationMenuAction(ActionEvent event) throws Exception {
		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		UserController controller = new UserController();
		controller.start(stage);
	}
	
	@FXML
	protected void addLocationMenuAction(ActionEvent event) throws Exception {

		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		UserController controller = new UserController();
		controller.start(stage);
	}
	@FXML
	protected void listUsersMenuAction(ActionEvent event) throws Exception {

		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		UserController controller = new UserController();
		controller.start(stage);
	}

	@FXML
	protected void addUserMenuAction(ActionEvent event) throws Exception {

		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		UserController controller = new UserController();
		controller.start(stage);
	}

	@FXML
	protected void listCustomersMenuAction(ActionEvent event) throws Exception {

		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		CustomerListController controller = new CustomerListController();
		controller.start(stage);
	}

	@FXML
	protected void addCustomerMenuAction(ActionEvent event) throws Exception {
		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		CustomerController controller = new CustomerController();
		controller.start(stage);
	}

	@FXML
	protected void listVehicleMenuAction(ActionEvent event) throws Exception {
		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;
		Stage stage = new Stage();
		CarListController controller = new CarListController();
		controller.start(stage);
	}

	@FXML
	protected void addVehicleMenuAction(ActionEvent event) throws Exception {
		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		CarController controller = new CarController();
		controller.start(stage);
	}

	@FXML
	protected void checkoutMenuAction(ActionEvent event) throws Exception {
		Stage stage = new Stage();
		OrderController controller = new OrderController("Return Car(s) -Car Rental Management System");
		controller.start(stage);
	}

	@FXML
	protected void checkinMenuAction(ActionEvent event) throws Exception {
		Stage stage = new Stage();
		ReturnFormController controller = new ReturnFormController();
		controller.start(stage);
	}

	@FXML
	protected void logoutMenuAction(ActionEvent event) throws Exception {

		SessionCache session = SessionCache.getInstance();
		try {
			session.remove(LoginRoles.ADMIN);
			session.remove(LoginRoles.SALESMAN);
			session.remove(LoginRoles.LOGGED_IN);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		menubar.getScene().getWindow().hide();

//		stage = new Stage();
//		LoginController controller = new LoginController();
//		controller.start(stage);
		HomeController2.loadScene(this, event, "LoginForm.fxml");
	}


	@FXML
	protected void exitMenuAction(ActionEvent event) throws Exception {

		System.exit(0);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		if (LoginAccess.isSalesman()) {
			userMenu.setDisable(true);
			customerMenu.setDisable(true);
			carMenu.setDisable(true);
		} else if (LoginAccess.isAdmin()) {
			userMenu.setDisable(false);
			customerMenu.setDisable(false);
			carMenu.setDisable(false);
		}

	}

	public List<KeyValuePair<LocalDate, Integer>> getCheckoutDates() {

		List<KeyValuePair<LocalDate, Integer>> dates = new ArrayList<>();

		ResultSet rs = facade.getAllOrderRecords(OrderRecordEntry.class);
		try {
			while (rs.next()) {

				dates.add(new KeyValuePair<>(rs.getDate("orderDate").toLocalDate(), rs.getInt("quantity")));

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return dates;

	}

	private void checkUserAccess() {

		if (LoginAccess.isSalesman()) {
			userMenu.setDisable(true);
			customerMenu.setDisable(true);
			carMenu.setDisable(true);
		} else if (LoginAccess.isAdmin()) {
			userMenu.setDisable(false);
			customerMenu.setDisable(false);
			carMenu.setDisable(false);
		}
	}
	
	static void loadScene(Object obj, ActionEvent event, String path) {
		try {
			Parent root = FXMLLoader.load(obj.getClass().getResource(path));
			stage.setScene(new Scene(root));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	
}
