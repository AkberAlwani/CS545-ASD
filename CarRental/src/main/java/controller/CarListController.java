/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the MIT License (MIT);
 */
package controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.rentframework.core.ProductFacade;
import org.rentframework.core.ProductFacadeImpl;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Vehicle;
import utils.DialogHelper;
import view.MainForm;

public class CarListController extends Application implements Initializable {

    @FXML
    private TextField txtSearch;

   

	@FXML
	TableView<Vehicle> tblView;
    
	@FXML
    TableColumn<Vehicle,Integer> colProductId;

	@FXML
    TableColumn<Vehicle,String> colCarMake;

    @FXML
    TableColumn<Vehicle, String> colCarModel;

    @FXML
    TableColumn<Vehicle, Integer> colCarYear;

    @FXML
    TableColumn<Vehicle, String> colName;

    @FXML
    private TableColumn<Vehicle, Integer>colSeats;

    @FXML
    private TableColumn<Vehicle, Boolean> colAutoGear;
    @FXML
	Text txtErrorMessage;
	private Stage primaryStage;


    
    public String title = "Vehicle Listing Form";
	
	ObservableList<Vehicle> vehicleList = FXCollections.observableArrayList();

//	private Vehicle selectedVehicle;
	public CarListController(){
		
	}
	
	public CarListController(String title) {
		this.title = title;
	}

	private void populateVehicles() {
		try {
			ProductFacade productFacade = new ProductFacadeImpl();
			ResultSet result = productFacade.getAllProduct(Vehicle.class);
			
			while (result.next()) {
				Vehicle car= new Vehicle();
				car.setProductId(result.getInt("productId"));
				car.setProductName(result.getString("productName"));
				car.setDescription(result.getString("description"));
				car.setMake(result.getString("make"));
				car.setYear(result.getInt("makeyear"));
				car.setModel(result.getString("model"));
				car.setSeats(result.getInt("seats"));
				car.setAutogear(result.getBoolean("autogear"));
				vehicleList.add((Vehicle) car);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void populateTable() {
		colProductId.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("productId"));
		colName.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("productName"));
		colCarMake.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("make"));
		colCarModel.setCellValueFactory(new PropertyValueFactory<Vehicle, String>("model"));
		colCarYear.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("year"));
		colSeats.setCellValueFactory(new PropertyValueFactory<Vehicle, Integer>("seats"));
		colAutoGear.setCellValueFactory(new PropertyValueFactory<Vehicle, Boolean>("autogear"));

		tblView.setItems(vehicleList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tblView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		populateVehicles();
		populateTable();
	}



	@FXML
	protected void btnCancel(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	protected void btnAdd(ActionEvent event) throws Exception {
		CarController addcar = new CarController(0);
		Stage stage = new Stage();
		((Node) (event.getSource())).getScene().getWindow().hide();
		addcar.start(stage);
	}

	@FXML
	protected void btnEdit(ActionEvent event) throws Exception {

		ObservableList<Vehicle> vehicles= tblView.getSelectionModel().getSelectedItems();
		System.out.println("list size: " + vehicles.size());
		if (vehicles.size() > 1) {
			DialogHelper.toast("Please, select single record only!", AlertType.WARNING);
			return;
		}
		if (vehicles.size() < 1) {
			DialogHelper.toast("Please, select a record!", AlertType.WARNING);
			return;
		}

		int productId = vehicles.get(0).getProductId();
		CarController addCarController = new CarController(productId);
		Stage stage = new Stage();
		((Node) (event.getSource())).getScene().getWindow().hide();
		addCarController.start(stage);

	}

	@FXML
	protected void btnDelete(ActionEvent event) {
		ObservableList<Vehicle> vehicles = tblView.getSelectionModel().getSelectedItems();
		System.out.println("list size: " + vehicles.size());
		if (vehicles.size() > 1) {
			DialogHelper.toast("Please, select single record only!", AlertType.WARNING);
			return;
		}
		if (vehicles.size() < 1) {
			DialogHelper.toast("Please, select a record!", AlertType.WARNING);
			return;
		}

		Vehicle vehicle= vehicles.get(0);
		ProductFacade carFacade = new ProductFacadeImpl();
		carFacade.removeProduct(vehicle);
		txtErrorMessage.setText("Vehicle Record Successfully Deleted");
		txtErrorMessage.setFill(Color.GREEN);

	}

	@FXML
	protected void btnSearch(ActionEvent event) {
		String searchText = txtSearch.getText();
		searchText.toLowerCase();
		vehicleList.clear();
		populateVehicles();
		if (!searchText.isEmpty()) {

			List<Vehicle> appVehicleList = vehicleList.stream()
					.filter(m -> m.getProductName().toLowerCase().contains(searchText)
							|| m.getMake().toLowerCase().contains(searchText)
							|| m.getModel().toLowerCase().contains(searchText))
							
					.collect(Collectors.toList());

			vehicleList.clear();
			for (Vehicle car: appVehicleList) {
				vehicleList.add(car);
			}
			populateTable();
		} else {
			vehicleList.clear();
			populateVehicles();
			populateTable();
		
		}

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle(title);
		try {
			
			FXMLLoader loader = new FXMLLoader(MainForm.class.getResource("CarListForm.fxml"));
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




}
