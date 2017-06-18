package view;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainForm extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		LoginController loginController = new LoginController();
		loginController.start(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
