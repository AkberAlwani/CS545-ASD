package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;


public abstract class AbstractController {
	@FXML
	protected void handleBackHome(ActionEvent event) {

//		HomeController.loadScene(this, event, "MainForm.fxml");

	}

	protected void clearField(TextField... txt) {
		for (TextField t : txt) {
			t.clear();
		}
	}

	protected void clearField(ComboBox<?>... txt) {
		for (ComboBox<?> t : txt) {
			t.getSelectionModel().clearSelection();
		}
	}

	protected void clearField(TableView... tableViews) {
		for (TableView t : tableViews) {
			t.getItems().clear();
		}
	}

	protected void showAlert(AlertType t, String msg) {
		Alert alert = new Alert(t);

		alert.setContentText(msg);

		alert.showAndWait();
	}
}
