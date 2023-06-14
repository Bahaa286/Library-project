package Controls;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Layout.SubmetJobAppLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.ChickMethods;

public class SubmetJobAppControl extends SubmetJobAppLayout implements EventHandler<ActionEvent> {

	private static final String STYLE = "style.css";

	private Stage controlStage;
	private Scene controlScene;

	/*-------------------------------------------------*/
	private LoginControl loginControl;

	public SubmetJobAppControl(Stage controlStage, LoginControl loginControl) {
		this.setReferences(controlStage, loginControl);
		this.setEvents();
	}

	private void setReferences(Stage controlStage, LoginControl loginControl) {
		this.loginControl = loginControl;

		this.controlStage = controlStage;
		this.controlScene = new Scene(this);
		controlScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());

	}

	// set this Scene in stage
	public void setScene() {

		// clear all previous errors
		this.clearError();

		// clear all previous informations
		this.clear();

		this.controlStage.setScene(this.controlScene);

		// title
		controlStage.setTitle("Submet application page");

		// size
		controlStage.setWidth(WIDTH);
		controlStage.setHeight(HEIGHT);
		controlStage.setResizable(false);
	}

	// set all events
	private void setEvents() {
		backToLogin.setOnAction(this);
		submit.setOnAction(this);
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			if (event.getSource().equals(backToLogin)) { // back to login
				this.loginControl.setScene();
			} else if (event.getSource().equals(submit)) { // submit
				this.submit();
			} // end else
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	} // end handle

	// --------------------------------------------------------------------------------//

	// chick error
	private static boolean chickError;

	private void chickErrors() throws ClassNotFoundException, SQLException {

		chickError = true;

		// first name
		String firstName = firstNameText.getText().trim();

		// last name
		String lastName = lastNameText.getText().trim();

		// phone numbers
		String phoneNum1 = phoneNumberText1.getText().trim(), phoneNum2 = phoneNumberText2.getText().trim();

		// password
		String password = passwordTextField.getText().trim();

		///////////////////////////////////////////////////////////////////////////////////////////////
		// empty informations

		// --------------------------------------------------------------------//
		// name

		// first name
		if (firstName.length() == 0) {
			chickError = false;
			firstNameErrorLabel.setText("enter the first name");
		}

		// last name
		if (lastName.length() == 0) {
			chickError = false;
			lastNameErrorLabel.setText("enter the last name");
		}

		// password
		if (password.length() == 0) {
			chickError = false;
			passwordErrorLabel.setText("enter the password");
		}

		// --------------------------------------------------------------------//
		// gender

		if (!(male.isSelected() || female.isSelected())) {
			chickError = false;
			genderErrorLabel.setText("you should choose a gender");
		}

		// --------------------------------------------------------------------//
		// birth day

		// day
		if (dayComboBox.getValue() == null) {
			chickError = false;
			errorDay.setText("you should choose a day");
		}

		// month
		if (monthComboBox.getValue() == null) {
			chickError = false;
			errorMonth.setText("you should choose a month");
		}

		// year
		if (yearComboBox.getValue() == null) {
			chickError = false;
			errorYear.setText("you should choose a year");
		}

		// --------------------------------------------------------------------//
		// phone number

		if (phoneNum1.length() == 0 && phoneNum2.length() == 0) {
			chickError = false;
			phoneNumberErrorLabel.setText("you should enter at least one phone number");
		}

		///////////////////////////////////////////////////////////////////////////////////////////////
		// invalid informations

		// --------------------------------------------------------------------//
		// name

		// first name
		if (firstName.length() != 0) {
			if (!ChickMethods.isNameValid(firstName)) {
				chickError = false;
				firstNameErrorLabel.setText("invalid informations");
			}
		}

		// last name
		if (lastName.length() != 0) {
			if (!ChickMethods.isNameValid(lastName)) {
				chickError = false;
				lastNameErrorLabel.setText("invalid informations");
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
					chickError = false;
					errorDay.setText("day is not valid to " + month);
				}
			} else {

				// year
				int year = yearComboBox.getValue();

				if (!ChickMethods.isBirthDateValid(day, month, year)) {
					chickError = false;
					errorDay.setText("day is not valid to " + month);
				}

			}

		}

		// --------------------------------------------------------------------//
		// phone

		if (phoneNum1.length() != 0 || phoneNum2.length() != 0) {

			if (phoneNum1.length() != 0) {
				if (!ChickMethods.isPhoneNumberValid(phoneNum1)) {
					chickError = false;
					phoneNumberErrorLabel.setText("invalid informatios");
				}
			}

			if (phoneNum2.length() != 0) {
				if (!ChickMethods.isPhoneNumberValid(phoneNum2)) {
					chickError = false;
					phoneNumberErrorLabel.setText("invalid informatios");
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

	// ------------------------------------------------------------------//

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

		passwordTextField.setText("");

	}// end clear method

	// clear errors
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
	}

	// ----------------------------------------------------------------------------//
	// submit

	private void submit() throws ClassNotFoundException, SQLException {

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
		String password = passwordTextField.getText().trim();

		// insert query
		String query = "INSERT INTO new_users(firstName, lastName, gender, birthDate, financialBalance, phone1, phone2,password, isEmployee) \r\n"
				+ "VALUES(\"" + firstName + "\", \"" + lastName + "\", '" + gender + "', \"" + birthDate + "\", "
				+ financialBalance + ",\"" + phoneNum1 + "\", \"" + phoneNum2 + "\",\"" + password + "\", true);";

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
} // end class
