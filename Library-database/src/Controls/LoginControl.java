package Controls;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Layout.LoginLayout;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginControl extends LoginLayout implements EventHandler<ActionEvent> {

	private final String STYLE = "style.css"; // style

	private final int ADMIN = 1; // administrator
	private final int HEMPLOYEE = 2; // hourly employee
	private final int MEMPLOYEE = 3; // monthly employee
	private final int MEMBER = 4; // member

	private Stage controlStage; // stage
	private Scene controlScene; // scene

	/*-------------------------------------------------------------------------*/
	private CreateAccountControl createAccountControl;
	private PrimaryControl primaryControl;
	private AdminControl adminControl;

	// set createAccountControl
	public void setCreateAccountControl(CreateAccountControl createAccountControl) {
		this.createAccountControl = createAccountControl;
	}

	// set primaryControl
	public void setPrimaryControl(PrimaryControl primaryControl) {
		this.primaryControl = primaryControl;
	}

	// set adminControl
	public void setAdminControl(AdminControl adminControl) {
		this.adminControl = adminControl;
	}
	/*-------------------------------------------------------------------------*/

	public LoginControl(Stage controlStage, CreateAccountControl createAccountControl, PrimaryControl primaryControl) {
		this.setReferences(controlStage, createAccountControl, primaryControl);
		this.setEvents();
	}

	private void setReferences(Stage controlStage, CreateAccountControl createAccountControl,
			PrimaryControl primaryControl) {

		this.createAccountControl = createAccountControl;
		this.primaryControl = primaryControl;

		this.controlStage = controlStage;
		this.controlScene = new Scene(this);
		controlScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());

	}

	public void setScene() {
		this.clear();
		this.clearError();

		// set scene
		this.controlStage.setScene(this.controlScene);

		// title
		controlStage.setTitle("Login page");

		// size
		controlStage.setWidth(super.WIDTH);
		controlStage.setHeight(super.HEIGHT);
		controlStage.setResizable(false);
	}

	private void setEvents() {

		// buttons
		this.login.setOnAction(this);
		this.creatAccount.setOnAction(this);
		this.backToPrimaryPage.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent node) {
		try {
			if (node.getSource().equals(this.creatAccount)) {
				this.createAccountControl.setScene();
			} else if (node.getSource().equals(this.backToPrimaryPage)) { // back to primary page
				this.primaryControl.setScene();
			} else if (node.getSource().equals(this.login)) { // login
				this.login();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// chick error
	private boolean chickError;

	private void chickErrors() throws ClassNotFoundException, SQLException {

		this.chickError = true;

		// --------------------------------------------- //
		// empty informations

		String id = super.userIDText.getText().trim();
		String password = super.passwordText.getText().trim();

		// user ID
		if (id.length() == 0) {
			chickError = false; // set chick error to false
			super.errorUserIDLabel.setText("enter the ID"); // set an error text in the label
		}

		// password
		if (password.length() == 0) {
			chickError = false; // set chick error to false
			super.errorPasswordLabel.setText("enter the password"); // set an error text in the label
		}

		// ---------------------------------------------------//
		// invalid information

		// isIdExist boolean
		boolean isIdExist = false;

		if (id.length() != 0) // chick if id exist in the database
			isIdExist = this.isIdExist(id);

		// chick if user id exist in the database
		if (!isIdExist) {
			chickError = false; // set chick error to false
			super.errorUserIDLabel.setText("ID does not exist"); // set an error text in the label
		}

		// chick if password is correct
		if (password.length() != 0 && isIdExist && !isPasswordCorrect(password, id)) {
			chickError = false; // set chick error to false
			super.errorPasswordLabel.setText("password id incorect"); // set an error text in the label
		}

	} // end chickError method

	// chick if id exist
	private boolean isIdExist(String id) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT * FROM login WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is exist boolean
		boolean isExist = Execute.resultSet.next();

		// close
		Execute.connection.close();
		Execute.statement.close();
		Execute.resultSet.close();

		return isExist;

	}// end isIdExist method

	// chick if password correct
	private boolean isPasswordCorrect(String password, String id) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT user_password FROM login WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		Execute.resultSet.next();

		// the correct password
		String correctPassword = Execute.resultSet.getString(1);

		// is password correct boolean
		boolean isCorrect = correctPassword.equals(password);

		// close
		Execute.connection.close();
		Execute.statement.close();
		Execute.resultSet.close();

		return isCorrect;

	} // end isPasswordCorrect

	// clear all informations
	private void clear() {
		super.userIDText.setText("");
		super.passwordText.setText("");
	}

	// clear all errors informations
	private void clearError() {
		super.errorUserIDLabel.setText("");
		super.errorPasswordLabel.setText("");
	}

	// --------------------------------------------------------------//
	// login

	private void login() throws ClassNotFoundException, SQLException {

		// clear previous errors
		this.clearError();

		// chick new errors
		this.chickErrors();

		if (this.chickError) {

			// id
			String id = super.userIDText.getText().trim();

			// user number
			int userNum = this.getUserNumber(id);

			if (userNum == this.ADMIN) {// if the id for administrator
				this.adminControl.setScene(); // set administrator scene
				this.adminControl.reset(); // reset information in the administrator scene
				this.adminControl.setAdminId(id); // set administrator id
			}
		} // end if
	} // end login

	// get user number
	private int getUserNumber(String id) throws ClassNotFoundException, SQLException {

		// user number
		int userNum = 0;

		// ------------------------------------//
		// administrator
		String adminQuery = "SELECT * FROM admins WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(adminQuery);

		if (Execute.resultSet.next())
			userNum = this.ADMIN;

		// close
		Execute.connection.close();
		Execute.statement.close();
		Execute.resultSet.close();

		// ------------------------------------//
		// member
		String memberQuery = "SELECT * FROM member WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(memberQuery);

		if (Execute.resultSet.next())
			userNum = this.MEMBER;

		// close
		Execute.connection.close();
		Execute.statement.close();
		Execute.resultSet.close();

		// ------------------------------------//
		// hourly employee
		String hourlyEmployeeQuery = "SELECT * FROM hourly_employee WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(hourlyEmployeeQuery);

		if (Execute.resultSet.next())
			userNum = this.HEMPLOYEE;

		// close
		Execute.connection.close();
		Execute.statement.close();
		Execute.resultSet.close();

		// ------------------------------------//
		// monthly employee
		String monthlyEmployeeQuery = "SELECT * FROM monthly_employee WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(monthlyEmployeeQuery);

		if (Execute.resultSet.next())
			userNum = this.MEMPLOYEE;

		// close
		Execute.connection.close();
		Execute.statement.close();
		Execute.resultSet.close();

		return userNum;
	}
} // end LoginControl class
