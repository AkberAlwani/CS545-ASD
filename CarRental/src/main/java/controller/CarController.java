package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.rentframework.command.CommandManager;
import org.rentframework.command.CommandManagerImpl;
import org.rentframework.core.Product;
import org.rentframework.core.ProductFacade;
import org.rentframework.core.ProductFacadeImpl;
import org.rentframework.logger.ConsoleLogger;
import org.rentframework.logger.Logger;
import org.rentframework.logger.LoggerImpl;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Vehicle;
import model.FormException;
import utils.DialogHelper;
import utils.Validator;

public class CarController extends Application implements Initializable {


	@FXML
	private TextField txtModel;
	@FXML
	private TextField txtYear;
	@FXML
	private TextField txtColor;
	@FXML
	private TextField txtMake;
	@FXML
	private TextField txtMileage;
	@FXML
	private TextField txtDailyFee;
	@FXML
	private TextField txtDailyFine;
	@FXML
	private TextField txtQuantity;
	@FXML
	private TextField txtSeats;
	@FXML
	private CheckBox chkAutoGear;

	@FXML
	private TextField txtPlate;

	private CommandManager command;
	public static int carId = 0;

	@FXML
	private Label lblCarId;

	private Logger logger;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/CarForm.fxml"));
		Scene scene = new Scene(root);
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setResizable(true);
		primaryStage.setTitle("Add/Update Vehicle Information");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public CarController() {
		this.command = new CommandManagerImpl();
		logger = new ConsoleLogger(new LoggerImpl());
	}

	public CarController(int carId) {
		CarController.carId = carId;
		this.command = new CommandManagerImpl();
		logger = new ConsoleLogger(new LoggerImpl());
	}

	@FXML
	protected void btnSave(ActionEvent event) throws Exception {

		try {

			Validator.validateBlank(txtModel);
			Validator.validateBlank(txtPlate);
			Validator.validateBlank(txtMake);
			Validator.validateNumber(txtYear);
			
			if (!txtMileage.getText().isEmpty())
				Validator.validateNumber(txtMileage);
			Validator.validateNumber(txtDailyFee);
			Validator.validateNumber(txtDailyFine);

			String name=txtYear.getText() +"-"+txtMake.getText()+" "+txtModel.getText(),
					description=txtYear.getText() +"-"+txtMake.getText()+" "+txtModel.getText()+"-"+txtPlate.getText();
			int quantity=1;
			
			String make = txtMake.getText(),model = txtModel.getText(),plate = txtPlate.getText(), color = txtColor.getText();
			int year = Integer.parseInt(txtYear.getText()),mileage = Integer.parseInt(txtMileage.getText() == "" ? "0" : txtMileage.getText());

			double dailyFee = Double.parseDouble(txtDailyFee.getText()),
					dailyFine = Double.parseDouble(txtDailyFine.getText());
			boolean autogear=false;
			if (chkAutoGear.isSelected()) 
				autogear=true;
			int seats  = Integer.parseInt(txtSeats.getText());
			Product product = new Vehicle(make,model,year,name, description, plate, color, quantity, dailyFee,
					dailyFine, mileage,autogear,seats);
			if (carId > 0)
				product.setProductId(carId);
			command.saveProduct(product);

			DialogHelper.toast("The Vehicle information saved successfully!", AlertType.INFORMATION);
		} catch (FormException ex) {
			DialogHelper.toast(ex.getMessage(), AlertType.ERROR);
		}
	}

	@FXML
	protected void btnCancel(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	private void initCarInfo() {

		ProductFacade facade = new ProductFacadeImpl();

		ResultSet rs = facade.getProductById(carId, Vehicle.class);
		logger.debug((rs == null) + "");

		try {
			while (rs.next()) {

				txtMake.setText(rs.getString("make"));
				txtModel.setText(rs.getString("model"));
				txtYear.setText("" + rs.getInt("makeyear"));
				txtColor.setText(rs.getString("color"));
				txtMileage.setText("" + rs.getInt("mileage"));
				txtPlate.setText(rs.getString("plate"));
				txtDailyFee.setText("" + rs.getDouble("dailyFee"));
				txtDailyFine.setText("" + rs.getDouble("dailyFine"));
				txtSeats.setText(""+rs.getInt("seats"));
//				chkAutoGear.selectedProperty().set(true);
							}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		if (carId > 0)
			initCarInfo();
	}

}
