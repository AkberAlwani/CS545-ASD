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

import org.rentframework.core.LocationFacade;
import org.rentframework.core.LocationFacadeImpl;
import org.rentframework.core.UserFacade;
import org.rentframework.core.UserFacadeImpl;
import org.rentframework.utils.LoginRoles;
import org.rentframework.utils.SessionCache;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AppUser;
import model.KeyValuePair;
import model.StoreLocation;
import view.MainForm;


public class LoginController extends Application implements Initializable {
	@FXML
	private ComboBox<KeyValuePair<Integer, String>> cboLocation;
	@FXML
	private TextField txtUserName;
	@FXML
	private TextField txtPassword;
	@FXML
	private Button btnLogin;
	@FXML
	private Text txtErrorMessage;
	@FXML
	private Button btnRest;
	private Stage primaryStage;

	private LocationFacade facade;	
	private ObservableList<KeyValuePair<Integer, String>> locationListObservable = FXCollections.observableArrayList();
	
	public LoginController() {
		facade = new LocationFacadeImpl();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		try {
			FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("LoginForm.fxml"));
			primaryStage.setResizable(false);
			primaryStage.setTitle("<<Login>> Car Rental Management");
			primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
			AnchorPane page = (AnchorPane) loader.load();
//			Scene scene = new Scene(page, 500, 300);
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
	public void login(ActionEvent event) throws Exception {
		if (!txtUserName.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()) {
			String userName = txtUserName.getText();
			String password = txtPassword.getText();
			AppUser user = authenticateUser(userName, password);
			if (user != null) {
				SessionCache session = SessionCache.getInstance();
				if (user.isAdmin()) {
					session.add(LoginRoles.LOGGED_IN_USERID, userName);
					session.add(LoginRoles.ADMIN, LoginRoles.ADMIN);
				} else {
					session.add(LoginRoles.LOGGED_IN_USERID, userName);
					session.add(LoginRoles.SALESMAN, LoginRoles.SALESMAN);
				}

				((Node) (event.getSource())).getScene().getWindow().hide();

				HomeController home = new HomeController();
				Stage stage = new Stage();
				home.start(stage);
			} else {
				txtErrorMessage.setFill(Color.RED);
				txtErrorMessage.setText("Invalid User Name and Password!");
			}

		} else {
			txtErrorMessage.setFill(Color.RED);
			txtErrorMessage.setText("Please specify User Name and Password!");
		}
	}

	private AppUser authenticateUser(String userName, String password) throws SQLException {
		UserFacade userFacade = new UserFacadeImpl();
		ResultSet result = userFacade.getUserByUserNameAndPassword(userName, password, AppUser.class);
		AppUser user = null;
		while (result.next()) {
			user = new AppUser();
			user.setIsAdmin(result.getBoolean("isAdmin"));
			user.setFirstName(result.getString("firstName"));
			user.setLastName(result.getString("lastName"));

		}
		return user;
	}

	@FXML
	protected void clearField(ActionEvent event) throws Exception {
		txtUserName.clear();
		txtPassword.clear();
		txtErrorMessage.setText("");
	}
	
	@FXML
	void onLocationSelected() {
		KeyValuePair<Integer, String> selectedLocation = cboLocation.getSelectionModel().getSelectedItem();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			ResultSet rs = facade.getAllLocations(StoreLocation.class);
			while (rs.next()) {
				int id = rs.getInt("locationId");
				String locationName = rs.getString("locationName");
				KeyValuePair<Integer, String> data = new KeyValuePair<Integer, String>();
				data.setKey(id);
				data.setValue(locationName);
				locationListObservable.add(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.cboLocation.setPromptText("--Choose Location");
		this.cboLocation.setItems(locationListObservable);
		
	}

}
