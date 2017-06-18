package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.rentframework.command.CommandManager;
import org.rentframework.command.CommandManagerImpl;
import org.rentframework.core.UserFacade;
import org.rentframework.core.UserFacadeImpl;
import org.rentframework.utils.LoginRoles;
import org.rentframework.utils.LoginAccess;
import org.rentframework.utils.SessionCache;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.SelectionMode;
//import javafx.scene.control.Tab;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.image.Image;
//import javafx.stage.Stage;
import model.AppUser;
import model.FormException;

import utils.Validator;

/**
 * @author Akber
 *
 */
public class UserController extends Application implements Initializable {
	
	
    @FXML
    private Tab tbCheckout;
    @FXML
    private Tab tbView;
    
    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtMiddleName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtUsername;

    @FXML
    private CheckBox isAdminChkBox;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;
	@FXML
	private Label successMsgLabel;
	@FXML
	private TableView<AppUser> userTable;
	@FXML
	private TableColumn<AppUser, String> userName;
	@FXML
	private TableColumn<AppUser, String> name;
	@FXML
	private TableColumn<AppUser, String> userEmail;

	private CommandManager command;
	private UserFacade facade;
	private int userId;

	public UserController() {
		command = new CommandManagerImpl();
		facade = new UserFacadeImpl();
	}
	
	@FXML
	void btnSaveUser() {

		try {
			Validator.validateBlank(txtFirstName);
			Validator.validateBlank(txtLastName);
			Validator.validateBlank(txtEmail);
			if (!(userId > 0)) {
				Validator.validateBlank(txtPassword);
				Validator.validateBlank(txtConfirmPassword);
			}
			Validator.validateNumber(txtPhone);
			Validator.validateEmail(txtEmail);

			String firstName = txtFirstName.getText().toString(), middleName = txtMiddleName.getText().toString(),
					lastName = txtLastName.getText().toString(), userName = txtUsername.getText().toString(),
					password = txtPassword.getText().toString(),
					confirmPassword = txtConfirmPassword.getText().toString(), email = txtEmail.getText().toString(),
					phone = txtPhone.getText().toString();

			if (password.equals(confirmPassword)) {
				AppUser user = new AppUser();
				System.out.println(user.isAdmin());
				if (userId > 0) {
					user.setSysuserId(userId);
					/*
					 * if (user.isAdmin()) { isAdminChkBox.setSelected(true); }
					 */
				}
				user.setFirstName(firstName);
				user.setMiddleName(middleName);
				user.setLastName(lastName);
				user.setUserName(userName);
				user.setPassword(password);
				user.setEmail(email);
				user.setPhone(phone);
				if (isAdminChkBox.isSelected()) {
					user.setIsAdmin(true);
				} else {
					user.setIsAdmin(false);
				}

				if (command.saveUser(user)) {

					if (LoginAccess.getLoggedInUsername() != null
							&& LoginAccess.getLoggedInUsername().equals(userName)) {
						SessionCache session = SessionCache.getInstance();
						if (user.isAdmin()) {
							session.add(LoginRoles.LOGGED_IN_USERID, userName);
							session.add(LoginRoles.ADMIN, LoginRoles.ADMIN);
						} else {
							session.add(LoginRoles.LOGGED_IN_USERID, userName);
							session.add(LoginRoles.SALESMAN, LoginRoles.SALESMAN);
						}
					}

					successMsgLabel.setText("User added/updated successfully !!!");
					successMsgLabel.getStyleClass().add("color-success");
					clearFields();
					updateUserTable();
				} else {
					successMsgLabel.setText("There is an error while adding/updating user !!!");
					successMsgLabel.getStyleClass().add("color-error");
					clearFields();

				}
			} else {
				successMsgLabel.setText("Password Mismatch !!!");
				successMsgLabel.getStyleClass().add("color-error");
			}
		} catch (FormException e) {
			successMsgLabel.setText(e.getMessage());
			successMsgLabel.getStyleClass().add("color-error");
		}

	}

	public void populateTable(ObservableList<AppUser> users) {
		userName.setCellValueFactory(new PropertyValueFactory<AppUser, String>("userName"));
		name.setCellValueFactory(new PropertyValueFactory<AppUser, String>("firstName"));
		userEmail.setCellValueFactory(new PropertyValueFactory<AppUser, String>("email"));
		userTable.setItems(users);
	}

	void clearFields() {
		txtFirstName.setText("");
		txtMiddleName.setText("");
		txtLastName.setText("");
		txtUsername.setText("");
		txtPassword.setText("");
		txtConfirmPassword.setText("");
		txtEmail.setText("");
		txtPhone.setText("");
		isAdminChkBox.setSelected(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		updateUserTable();
	}

	public void updateUserTable() {
		ObservableList<AppUser> users = FXCollections.observableArrayList();
		try {
			ResultSet rs = facade.getAllUsers(AppUser.class);
			while (rs.next()) {
				AppUser user = new AppUser();
				user.setSysuserId(rs.getInt("sysuserId"));
				user.setUserName(rs.getString("userName"));
				user.setFirstName(rs.getString("firstName"));
				user.setMiddleName(rs.getString("middleName"));
				user.setLastName(rs.getString("lastName"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setIsAdmin(rs.getBoolean("isAdmin"));
				user.setPhone(rs.getString("phone"));

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		populateTable(users);
		userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection != null) {
				userId = newSelection.getSysuserId();
				txtUsername.setText(newSelection.getUserName());
				txtFirstName.setText(newSelection.getFirstName());
				txtMiddleName.setText(newSelection.getMiddleName());
				txtLastName.setText(newSelection.getLastName());
				txtEmail.setText(newSelection.getEmail());
				txtPhone.setText(newSelection.getPhone());
				isAdminChkBox.setSelected(newSelection.isAdmin());
			}
		});
	}

	@FXML
	protected void btnCancel(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View/UserTabbed.fxml"));
		Scene scene = new Scene(root);
//		scene.getStylesheets().add("view/style.css");
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setResizable(true);
		primaryStage.setTitle("Application User Form");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	

}
