package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.rentframework.logger.Logger;
import org.rentframework.logger.LoggerImpl;
import org.rentframework.utils.LoginAccess;
import org.rentframework.core.CustomerFacade;
import org.rentframework.core.CustomerFacadeImpl;
import org.rentframework.core.ProductFacade;
import org.rentframework.core.ProductFacadeImpl;
import org.rentframework.logger.ConsoleLogger;


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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.AppCustomer;
import model.Vehicle;
import model.OrderCart;
import model.KeyValuePair;
import utils.DialogHelper;

public class OrderController extends Application implements Initializable {

	@FXML
	private ComboBox<KeyValuePair<Integer, String>> customerListComboBox;

	@FXML
	private Button checkoutBtn;

	@FXML
	private Button btnAddCar;

	@FXML
	private Button updateBtn;

	@FXML
	private TableColumn<Vehicle, String> name;

	@FXML
	private TableColumn<Vehicle, String> model;

	@FXML
	private TableColumn<Vehicle, Integer> year;

	@FXML
	private TableColumn<Vehicle, Double> fee;

	@FXML
	private TableColumn<Vehicle, Integer> quantity;

	@FXML
	private Label lblTitle;

	@FXML
	private TableView<Vehicle> carList;
	ObservableList<Vehicle> carListObservable = FXCollections.observableArrayList();
	private Logger logger;
	public static String title = "Vehicle Order Form";

	ObservableList<KeyValuePair<Integer, String>> customerListObservable = FXCollections.observableArrayList();

	public OrderController() {
		logger = new ConsoleLogger(new LoggerImpl());
	}

	public OrderController(String title) {
		this.title = title;

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (LoginAccess.isSalesman()) {
			btnAddCar.setDisable(true);
			updateBtn.setDisable(true);
			// customerListComboBox.setDisable(true);
		} else if (LoginAccess.isAdmin()) {
			// customerListComboBox.setDisable(false);
			btnAddCar.setDisable(false);
			updateBtn.setDisable(false);
		}

		lblTitle.setText(title);
		carList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		getTableData();
	};

	private void getTableData() {

		carList.getItems().clear();
		ProductFacade facade = new ProductFacadeImpl();
		ResultSet resultSet = facade.getAllProduct(Vehicle.class);
		try {
			while (resultSet.next()) {
				Vehicle car = new Vehicle();
				car.setProductId(resultSet.getInt("productId"));
				car.setProductName(resultSet.getString("productName"));
				car.setMake(resultSet.getString("make"));
				car.setModel(resultSet.getString("model"));
				car.setYear(resultSet.getInt("makeYear"));
				car.setDailyFee(resultSet.getDouble("dailyFee"));
				car.setQuantity(resultSet.getInt("quantity"));

				carListObservable.add(car);

				System.out.println("INsdie");
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		populateTable();
	}

	private void populateTable() {

		name.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("productName"));
		name.setSortType(SortType.ASCENDING);
		model.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
		year.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("year"));
		fee.setCellValueFactory(new PropertyValueFactory<Vehicle, Double>("perDayRentalFee"));
		quantity.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("quantity"));

		carList.setItems(carListObservable);

		populateComboBox();
	}

	private void populateComboBox() {

		CustomerFacade facade = new CustomerFacadeImpl();
		ResultSet rs = facade.getAllCustomers(AppCustomer.class);

		try {
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
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		this.customerListComboBox.getItems().clear();
		this.customerListComboBox.setPromptText("--Choose for checkout");
		this.customerListComboBox.setItems(customerListObservable);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader
				.load(getClass().getClassLoader().getResource("view/OrderForm.fxml"));
//		Scene scene = new Scene(root, 800, 800);
		Scene scene = new Scene(root);
//		scene.getStylesheets().add("view/style.css");
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setResizable(true);
		primaryStage.setTitle("Checkout Form - Car List");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	protected void btnCancelAction(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	protected void btnUpdateAction(ActionEvent event) throws Exception {

		ObservableList<Vehicle> cars = carList.getSelectionModel().getSelectedItems();
		if (cars.size() > 1) {
			DialogHelper.toast("Please, select only one record!", AlertType.WARNING);
			return;
		}
		if (cars.size() < 1) {
			DialogHelper.toast("Please, select a record!", AlertType.WARNING);
			return;
		}

		int carId = cars.get(0).getProductId();
		System.out.println("carID: " + carId);
		CarController carController = new CarController(carId);
		Stage stage = new Stage();
		carController.start(stage);

	}

	@FXML
	protected void btnRefreshAction(ActionEvent event) throws Exception {
		getTableData();
	}

	@FXML
	protected void btnAddCarAction(ActionEvent event) throws Exception {
		Stage stage = new Stage();
		CarController controller = new CarController();
		controller.start(stage);
	}

	@FXML
	protected void btnCheckoutAction(ActionEvent event) throws Exception {

		ObservableList<Vehicle> cars = carList.getSelectionModel().getSelectedItems();
		if (cars.size() < 1) {
			DialogHelper.toast("Please, select a record!", AlertType.WARNING);
			return;
		}
		KeyValuePair<Integer, String> selectedCustomer = customerListComboBox.getSelectionModel().getSelectedItem();

		if (selectedCustomer == null) {
			DialogHelper.toast("Please, select a customer!", AlertType.WARNING);
			return;
		}

		System.out.println("cust: " + selectedCustomer.getKey());
		// prepare the cart
		OrderCart cart = new OrderCart();
		cart.setCustomerId(selectedCustomer.getKey());
		cart.setCustomerName(selectedCustomer.getValue());
		cart.setCars(cars);

		Stage stage = new Stage();
		OrderPaymentController controller = new OrderPaymentController(cart);
		controller.start(stage);

	}

}
