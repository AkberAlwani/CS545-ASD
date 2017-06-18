/**
 * Copyright 2016 the original author or authors.
 * 
 * Licensed under the MIT License (MIT);
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.rentframework.core.OrderRecordEntry;
import org.rentframework.middlelayer.ReturnTransactionManager;
import org.rentframework.middlelayer.TransactionManager;
import org.rentframework.utils.LoginRoles;
import org.rentframework.utils.SessionCache;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Vehicle;
import utils.DialogHelper;

/**
 * @author Akber
 *
 */
public class ReturnPaymentController extends Application implements Initializable {
	@FXML
	private Label overDueFineAmountLabel;
	public static List<OrderRecordEntry> entries = null;
	private TransactionManager returnTransactionManager;

	/**
	 * 
	 */
	public ReturnPaymentController() {
		returnTransactionManager = new ReturnTransactionManager();
	}

	/**
	 * 
	 */
	public ReturnPaymentController(List<OrderRecordEntry> entries) {
		this.entries = entries;
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/ReturnPaymentForm.fxml"));
		Scene scene = new Scene(root);
		//scene.getStylesheets().add("view/style.css");
		primaryStage.getIcons().add(new Image("file:resources/images/icon.png"));
		primaryStage.setResizable(true);
		primaryStage.setTitle("[OVERDUE FEES PAYMENT FORM]");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	protected void btnCancelHandler(ActionEvent event) throws Exception {
		((Node) (event.getSource())).getScene().getWindow().hide();
	}

	@FXML
	protected void btnPayHandler(ActionEvent event) throws Exception {
		SessionCache session = SessionCache.getInstance();
		session.add(LoginRoles.SALESMAN, LoginRoles.SALESMAN);
		returnTransactionManager.proceedTransaction(entries, Vehicle.class);
		DialogHelper.toast("Return record saved successfully!", AlertType.INFORMATION);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		double totalFine = 0.0;
		for (OrderRecordEntry entry : entries) {
			totalFine += entry.getDailyFine();
			System.out.println("CALCULATING TOTAL: " + totalFine);
		}

		overDueFineAmountLabel.setText("" + totalFine);
	}

}
