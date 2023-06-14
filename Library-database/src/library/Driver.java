package library;

import java.sql.SQLException;

import Controls.*;
import Layout.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Driver extends Application {

	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private PrimaryControl primaryControl;
	private SubmetJobAppControl submetJobAppControl;
	private AdminControl adminControl;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		this.references(primaryStage);

		primaryStage.show();


	}

	private void references(Stage primaryStage) {

		try {

			// login
			this.loginControl = new LoginControl(primaryStage, createAccountControl, primaryControl);

			// create account
			this.createAccountControl = new CreateAccountControl(primaryStage, loginControl);

			this.loginControl.setCreateAccountControl(this.createAccountControl);

			// primary
			primaryControl = new PrimaryControl(primaryStage, createAccountControl, loginControl, submetJobAppControl);

			// submit job application
			submetJobAppControl = new SubmetJobAppControl(primaryStage, loginControl);

			this.primaryControl.setSubmetJobAppControl(submetJobAppControl);
			
			// administrator
			this.adminControl = new AdminControl(primaryStage, loginControl);
			
			// set on login control
			this.loginControl.setPrimaryControl(this.primaryControl);
			this.loginControl.setAdminControl(this.adminControl);
			
			primaryControl.setScene();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
