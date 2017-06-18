package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;



public class MainController extends Application implements Initializable {
   @FXML
   private JFXButton listCustomersMenu;

   @FXML
   private JFXButton addLocationMenu;

   @FXML
   private JFXButton addCustomerMenu;

    @FXML
    private JFXButton mnuListUser;

    @FXML
    private JFXButton logoutMenu;

    @FXML
    private JFXButton listCarsMenu;

    @FXML
    private JFXButton checkoutMenu;

    @FXML
    private JFXButton checkinMenu;

    @FXML
    private JFXButton addCarMenu;

    @FXML
    private JFXButton exitMenu;

	static Stage stage;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
		Scene scene = new Scene(root);
		stage = primaryStage;
		primaryStage.setTitle("Car Rental Management System");
		primaryStage.setScene(scene);
		primaryStage.show();
		// primaryStage.addEventHandler(, eventHandler);
	}

	@FXML
	protected void logoutMenuAction(ActionEvent event) {
		MainController.loadScene(this, event, "/view/login.fxml");
	}

	@FXML
	protected void addLocationMenuAction(ActionEvent event) {
		MainController.loadScene(this, event, "/resources/UserTabbed.fxml");
	}
	

	@FXML
	protected void addVehicleMenuAction(ActionEvent event) {
		MainController.loadScene(this, event, "/resources/book.fxml");
	}	
	
	
	@FXML
	protected void checkinMenuAction(ActionEvent event) {
		MainController.loadScene(this, event, "/resources/report.fxml");
	}
	
	@FXML
	protected void checkoutMenuAction(ActionEvent event) {
	
	}

	@FXML
	protected void exitMenuAction(ActionEvent event) {

	}

	@FXML
	protected void listCustomersMenuAction(ActionEvent event) {

	}

	@FXML
	protected void listUsersMenuAction(ActionEvent event) {

	}

	@FXML
	protected void listVehicleMenuAction(ActionEvent event) {

	}
	
	@FXML
	protected void listCarsMenuAction(ActionEvent event) {

	}

	
	static void loadScene(Object obj, ActionEvent event, String path) {
		try {
			Parent root = FXMLLoader.load(obj.getClass().getResource(path));
			stage.setScene(new Scene(root));
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		Customer user = Customer.getLoggedIn();
//		btnReports.setVisible(user.getRole() != Role.GUEST);
//		btnUsers.setVisible(user.getRole() != Role.GUEST);

	}
}
