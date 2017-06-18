/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 * 
 */
package controller;
/**
 * @author Akber Ali
 *
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
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

public class HomeController extends Application implements Initializable {

	private Logger logger;
	private OrderRecordFacade facade;

    @FXML
    private ImageView imageview;
	@FXML
	private MenuBar menubar;
	@FXML
	private Menu customerMenu;
	@FXML
	private MenuItem listCustomersMenu;

	@FXML
	private Menu carMenu;
	@FXML
	private Menu userMenu;
	@FXML
	private MenuItem mnuListUser;
	@FXML
	private MenuItem mnuAddUser;

	@FXML
	private Menu transactionMenu;

	@FXML
	private ImageView imageView;

//	private ObservableList<String> monthNames = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setMaximized(true);
		
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/MainForm.fxml"));
		Scene scene = new Scene(root);
		
//		imageview.setFitWidth(scene.getWidth());
//		imageview.setFitHeight(scene.getHeight());
//		scene.getStylesheets().add("view/background.css");
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setTitle("[Home] Car Rental Management System");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public HomeController() {
		this.logger = new ConsoleLogger(new LoggerImpl());
		this.facade = new OrderProtectionProxy();
	}
	
	
	
	@FXML
	protected void locationHandler(ActionEvent event) throws Exception {

		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		UserController controller = new UserController();
		controller.start(stage);
	}

	@FXML
	protected void userManagerHandler(ActionEvent event) throws Exception {

		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		UserController controller = new UserController();
		controller.start(stage);
	}

	@FXML
	protected void listCustomerHandler(ActionEvent event) throws Exception {

		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		CustomerListController controller = new CustomerListController();
		controller.start(stage);
	}

	@FXML
	protected void addCustomerHandler(ActionEvent event) throws Exception {
		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		CustomerController controller = new CustomerController();
		controller.start(stage);
	}

	@FXML
	protected void listCarHandler(ActionEvent event) throws Exception {
		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;
		Stage stage = new Stage();
		CarListController controller = new CarListController();
		controller.start(stage);
	}

	@FXML
	protected void addCarHandler(ActionEvent event) throws Exception {
		checkUserAccess();
		if (LoginAccess.isSalesman())
			return;

		Stage stage = new Stage();
		CarController controller = new CarController();
		controller.start(stage);
	}

	@FXML
	protected void rentCarHandler(ActionEvent event) throws Exception {
		Stage stage = new Stage();
		OrderController controller = new OrderController("Checkout Car(s) -Car Rental System");
		controller.start(stage);
	}

	@FXML
	protected void returnCarHandler(ActionEvent event) throws Exception {
		Stage stage = new Stage();
		ReturnFormController controller = new ReturnFormController();
		controller.start(stage);
	}

	@FXML
	protected void logoutUserHandler(ActionEvent event) throws Exception {

		SessionCache session = SessionCache.getInstance();
		try {
			session.remove(LoginRoles.ADMIN);
			session.remove(LoginRoles.SALESMAN);
			session.remove(LoginRoles.LOGGED_IN);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		menubar.getScene().getWindow().hide();
		Stage stage = new Stage();
		LoginController controller = new LoginController();
		controller.start(stage);
	}


	@FXML
	protected void exitMenuHandler(ActionEvent event) throws Exception {

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

				dates.add(new KeyValuePair<>(rs.getDate("checkoutDate").toLocalDate(), rs.getInt("quantity")));
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
}
