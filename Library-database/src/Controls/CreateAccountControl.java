package Controls;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Layout.CreateAccountLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.ChickMethods;

public class CreateAccountControl extends CreateAccountLayout implements EventHandler<ActionEvent> {

	private final String STYLE = "style.css";

	private Stage controlStage;
	private Scene controlScene;

	/*------------------------------*/
	private LoginControl loginControl;

	public CreateAccountControl(Stage controlStage, LoginControl loginControl) {
		this.setReferences(controlStage, loginControl);
		this.setEvents();
	}

	private void setReferences(Stage controlStage, LoginControl loginControl) {
		this.loginControl = loginControl;

		this.controlStage = controlStage;
		this.controlScene = new Scene(this);
		this.controlScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());

	}

	public void setScene() {

		// clear all previous informations
		this.clear();
		this.clearError();

		// set scene
		this.controlStage.setScene(this.controlScene);

		// title
		controlStage.setTitle("Create account page");

		// size
		controlStage.setWidth(WIDTH);
		controlStage.setHeight(HEIGHT);
		controlStage.setResizable(false);
		resize(WIDTH, HEIGHT);
	}

	private void setEvents() {

		// buttons
		backToLogin.setOnAction(this);
		createAccount.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {

		if (event.getSource().equals(backToLogin)) {
			this.loginControl.setScene();
		} else if (event.getSource().equals(createAccount)) {

			try {
				this.createAccount();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}

	}

	// --------------------------------------------------------------------------------
	// //

	// chick error
	private boolean chickError;

	private void chickErrors() throws ClassNotFoundException, SQLException {

		this.chickError = true;

		// first name
		String firstName = firstNameText.getText().trim();

		// last name
		String lastName = lastNameText.getText().trim();

		// phone numbers
		String phoneNum1 = phoneNumberText1.getText().trim(), phoneNum2 = phoneNumberText2.getText().trim();

		// password
		String password = passwordText.getText().trim();

		///////////////////////////////////////////////////////////////////////////////////////////////
		// empty informations

		// --------------------------------------------------------------------//
		// name

		// first name
		if (firstName.length() == 0) {
			this.chickError = false;
			firstNameErrorLabel.setText("enter the first name");
		}

		// last name
		if (lastName.length() == 0) {
			this.chickError = false;
			lastNameErrorLabel.setText("enter the last name");
		}

		// --------------------------------------------------------------------//
		// gender

		if (!(male.isSelected() || female.isSelected())) {
			this.chickError = false;
			genderErrorLabel.setText("you should choose a gender");
		}

		// --------------------------------------------------------------------//
		// birth day

		// day
		if (dayComboBox.getValue() == null) {
			this.chickError = false;
			errorDay.setText("you should choose a day");
		}

		// month
		if (monthComboBox.getValue() == null) {
			this.chickError = false;
			errorMonth.setText("you should choose a month");
		}

		// year
		if (yearComboBox.getValue() == null) {
			this.chickError = false;
			errorYear.setText("you should choose a year");
		}

		// --------------------------------------------------------------------//
		// phone number

		if (phoneNum1.length() == 0 && phoneNum2.length() == 0) {
			this.chickError = false;
			phoneNumberErrorLabel.setText("you should enter at least one phone number");
		}

		// --------------------------------------------------------------------//
		// password

		if (password.length() == 0) {
			this.chickError = false;
			passwordErrorLabel.setText("you should enter the password");
		}

		///////////////////////////////////////////////////////////////////////////////////////////////
		// invalid informations

		// --------------------------------------------------------------------//
		// name

		// first name
		if (firstName.length() != 0) {
			if (!ChickMethods.isNameValid(firstName)) {
				this.chickError = false;
				firstNameErrorLabel.setText("unvalid informations");
			}
		}

		// last name
		if (lastName.length() != 0) {
			if (!ChickMethods.isNameValid(firstName)) {
				this.chickError = false;
				lastNameErrorLabel.setText("unvalid informations");
			}
		}

		// --------------------------------------------------------------------//
		// birth date

		if (dayComboBox.getValue() != null && monthComboBox.getValue() != null) {

			// day
			int day = dayComboBox.getValue();

			// month
			String month = monthComboBox.getValue();

			if (yearComboBox.getValue() == null) {
				if (!ChickMethods.isBirthDateValid(day, month)) {
					this.chickError = false;
					errorDay.setText("day is not valid to " + month);
				}
			} else {

				// year
				int year = yearComboBox.getValue();

				if (!ChickMethods.isBirthDateValid(day, month, year)) {
					this.chickError = false;
					errorDay.setText("day is not valid to " + month);
				}

			}

		}

		// --------------------------------------------------------------------//
		// phone

		// is phone valid
		if (phoneNum1.length() != 0 || phoneNum2.length() != 0) {

			if (phoneNum1.length() != 0) {
				if (!ChickMethods.isPhoneNumberValid(phoneNum1)) {
					this.chickError = false;
					phoneNumberErrorLabel.setText("unvalid informatios");
				}
			}

			if (phoneNum2.length() != 0) {
				if (!ChickMethods.isPhoneNumberValid(phoneNum2)) {
					this.chickError = false;
					phoneNumberErrorLabel.setText("unvalid informatios");
				}
			}

		} // end if statements

		// chick if phone exist in the database

		// phone1
		if (!phoneNum1.isEmpty() && (this.isPhoneExist(phoneNum1) || this.isPhoneExistInNewUsers(phoneNum1))) {
			chickError = false;
			phoneNumberErrorLabel.setText("phone exist for anothor person!");
		}

		// phone2
		if (!phoneNum2.isEmpty() && (this.isPhoneExist(phoneNum2) || this.isPhoneExistInNewUsers(phoneNum2))) {
			chickError = false;
			phoneNumberErrorLabel.setText("phone exist for anothor person!");
		}

		// -------------------------------------------------------------//
		// password
		if (password.length() > 30) {
			chickError = false;
			passwordErrorLabel.setText("large value");
		}

	}// end chickErrors method

	// ----------------------------------------------------------------//

	// clear all informations
	private void clear() {
		firstNameText.setText("");
		lastNameText.setText("");

		male.setSelected(false);
		female.setSelected(false);

		dayComboBox.setValue(null);
		monthComboBox.setValue(null);
		yearComboBox.setValue(null);

		phoneNumberText1.setText("");
		phoneNumberText2.setText("");

		passwordText.setText("");

	}// end clear method

	// clear all errors informations
	private void clearError() {

		// name
		firstNameErrorLabel.setText("");
		lastNameErrorLabel.setText("");

		// gender
		genderErrorLabel.setText("");

		// birth date
		errorDay.setText("");
		errorMonth.setText("");
		errorYear.setText("");

		// phone number
		phoneNumberErrorLabel.setText("");

		// password
		passwordErrorLabel.setText("");

	}// end clearError method

	// -----------------------------------------------------------------//
	// create account

	private void createAccount() throws ClassNotFoundException, SQLException {

		// clear errors
		this.clearError();

		// chick errors
		this.chickErrors();

		if (chickError) { // if there is no error in the entered information
			this.insertIntoNewUsersTableInDatabase();
			this.clear();
		} // end if

	} // end submit method

	// insert into new users table
	private void insertIntoNewUsersTableInDatabase() throws ClassNotFoundException, SQLException {

		// first name
		String firstName = firstNameText.getText().trim();

		// last name
		String lastName = lastNameText.getText().trim();

		// gender
		char gender = (male.isSelected()) ? 'M' : 'F';

		// birth date
		int day = dayComboBox.getSelectionModel().getSelectedItem(); // day
		int month = monthComboBox.getSelectionModel().getSelectedIndex() + 1; // month
		int year = yearComboBox.getSelectionModel().getSelectedItem(); // year
		String birthDate = day + "-" + month + "-" + year; // birth date

		// financial balance
		double financialBalance = 0;

		// phone numbers
		String phoneNum1 = phoneNumberText1.getText().trim(), phoneNum2 = phoneNumberText2.getText().trim();

		// password
		String password = passwordText.getText().trim();

		// insert query
		String query = "INSERT INTO new_users(firstName, lastName, gender, birthDate, financialBalance, phone1, phone2,password, isEmployee) \r\n"
				+ "VALUES(\"" + firstName + "\", \"" + lastName + "\", '" + gender + "', \"" + birthDate + "\", "
				+ financialBalance + ",\"" + phoneNum1 + "\", \"" + phoneNum2 + "\",\"" + password + "\", FALSE);";

		Execute.executeQuery(query);

	}

	// chick if phone exit
	private boolean isPhoneExist(String phone) throws ClassNotFoundException, SQLException {

		// search phone query
		String query = "SELECT * FROM phone WHERE phone_number = \"" + phone + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is phone exist boolean
		boolean isPhoneExist = Execute.resultSet.next();

		// close
		Execute.close();

		return isPhoneExist || this.isPhoneExistInNewUsers(phone);

	} // end isPhoneExist method

	// chick if phone exist in any new user in the new users table
	private boolean isPhoneExistInNewUsers(String phone) throws ClassNotFoundException, SQLException {

		// search phone query
		String query = "SELECT * FROM new_users WHERE phone1 = \"" + phone + "\" OR phone2 = \"" + phone + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is phone exist boolean
		boolean isPhoneExist = Execute.resultSet.next();
		if (isPhoneExist)
			isPhoneExist = Execute.resultSet != null;

		// close
		Execute.close();

		return isPhoneExist;
	}
}
