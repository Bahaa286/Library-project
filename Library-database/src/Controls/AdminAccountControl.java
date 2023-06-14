package Controls;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ConnectionsToMYSQL.Execute;
import Layout.AdminAccountLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import library.Admin;
import library.ChickMethods;

public class AdminAccountControl extends AdminAccountLayout implements EventHandler<ActionEvent> {

	public AdminAccountControl(HBox adminPane) {
		super(adminPane);
		this.setEvent();
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			if (event.getSource().equals(firstNameTextField)) { // first name
				this.updateFirstName();
			} else if (event.getSource().equals(secondNameTextField)) { // last name
				this.updateLastName();
			} else if (event.getSource().equals(changeGenderButton)) { // gender
				this.updateGender();
			} else if (event.getSource().equals(changeBirthDateButton)) { // birth date
				this.updateBirthDate();
			} else if (event.getSource().equals(changePhone1Button)) { // phone1
				this.updatePhone1();
			} else if (event.getSource().equals(changePhone2Button)) { // phone2
				this.updatePhone2();
			} else if (event.getSource().equals(changePasswordButton)) { // password
				this.updatePassword();
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	}

	// add this on administrator pane
	public void addOnPane() {
		super.adminPane.getChildren().clear();
		super.adminPane.getChildren().add(this);

		this.refresh();
	}

	// set event
	private void setEvent() {

		// name
		firstNameTextField.setOnAction(this);
		secondNameTextField.setOnAction(this);

		// gender
		changeGenderButton.setOnAction(this);

		// birth date
		changeBirthDateButton.setOnAction(this);

		// phone
		changePhone1Button.setOnAction(this);
		changePhone2Button.setOnAction(this);

		// password
		changePasswordButton.setOnAction(this);

	}

	// refresh
	private void refresh() {
		this.clearAllData();

		try {
			this.insertAllDataIntoLabels();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------------------------------//
	// clear

	// clear all data
	private void clearAllData() {

		this.clearLabel();
		this.clearError();
		this.clearEnteredText();

	}

	// clear all information in the label
	private void clearLabel() {

		idLabel.setText(""); // id
		nameLabel.setText(""); // name
		genderLabel.setText(""); // gender
		birthDateLabel.setText(""); // birth date
		financialBalanceLabel.setText(""); // financial balance
		phoneLabel.setText(""); // phone
		employmentDataLabel.setText(""); // employment data
		salaryLabel.setText(""); // salary
		ruleLabel.setText(""); // rule

	}

	// clear error
	private void clearError() {

		passwordErrorLabel.setText("");

	}

	// clear entered text
	private void clearEnteredText() {

		firstNameTextField.clear(); // first name
		secondNameTextField.clear(); // second name
		genderComboBox.getSelectionModel().select(null); // gender
		birthDateDatePicker.setValue(null); // birth date
		PhoneTextField.clear(); // phone
		previousPasswordTextField.clear(); // previous password
		newPasswordTextField.clear(); // new password

	}

	// ---------------------------------------------------------//
	// insert all data
	private static Admin admin;

	private void insertAllDataIntoLabels() throws ClassNotFoundException, SQLException {

		// administrator information
		this.getAdminInfo();

		idLabel.setText(admin.getId());
		nameLabel.setText(admin.getFirstName() + " " + admin.getLastName());
		genderLabel.setText(ChickMethods.getGenderString(admin.getGender()));
		birthDateLabel.setText(admin.getBirthDate());
		financialBalanceLabel.setText(admin.getFinantialPalance());
		phoneLabel.setText(admin.getPhone1() + "     " + admin.getPhone2());
		employmentDataLabel.setText(admin.getEmploymentDate());
		salaryLabel.setText(admin.getSalary());
		ruleLabel.setText(admin.getRuleName() + ": " + admin.getRuleDescription());

	}

	// get administrator information
	private void getAdminInfo() throws ClassNotFoundException, SQLException {

		admin = null;

		// administrator information
		String id = "", firstName = "", lastName = "", birthDate = "", finantialPalance = "", password = "",
				employmentDate = "", salary = "", ruleName = "", ruleDescription = "";
		char gender = ' ';
		ArrayList<String> phone = new ArrayList<String>();

		// --------------------------------------------------------------------------//
		// select information from administrator and users tables query
		String query = "SELECT * FROM users U, admins A WHERE A.user_id = U.user_id;";

		// execute
		Execute.setResultSELECTQuery(query);

		if (Execute.resultSet.next()) { // store all information
			id = Execute.resultSet.getString(1);
			firstName = Execute.resultSet.getString(2);
			lastName = Execute.resultSet.getString(3);
			gender = Execute.resultSet.getString(4).charAt(0);
			birthDate = Execute.resultSet.getString(5);
			finantialPalance = Execute.resultSet.getString(6);
			employmentDate = Execute.resultSet.getString(8);
			salary = Execute.resultSet.getString(9);
			ruleName = Execute.resultSet.getString(10);
			ruleDescription = Execute.resultSet.getString(11);
		}

		// close
		Execute.close();

		// --------------------------------------------------------------------------//
		// select password from administrator and login tables
		String passwordQuery = "SELECT user_password FROM admins A, login L WHERE A.user_id = L.user_id;";

		// execute
		Execute.setResultSELECTQuery(passwordQuery);

		if (Execute.resultSet.next()) // store password information
			password = Execute.resultSet.getString(1);

		// close
		Execute.close();

		// --------------------------------------------------------------------------//
		// select phone information from administrator and phone tables query
		String phoneQuery = " SELECT * FROM phone WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(phoneQuery);

		while (Execute.resultSet.next())
			phone.add(Execute.resultSet.getString(1));

		if (phone.size() == 1) { // if there is one phone
			admin = new Admin(id, firstName, lastName, gender, birthDate, finantialPalance, phone.get(0), password,
					employmentDate, salary, ruleName, ruleDescription);
		} else { // if there is 2 phone
			admin = new Admin(id, firstName, lastName, gender, birthDate, finantialPalance, phone.get(0), phone.get(1),
					password, employmentDate, salary, ruleName, ruleDescription);
		} // else

	} // end getAdminInfo method

	// ---------------------------------------------------------------------------------------------------------//
	// update

	// first name
	private void updateFirstName() throws ClassNotFoundException, SQLException {

		String firstName = firstNameTextField.getText().trim(); // first name

		if (ChickMethods.isNameValid(firstName) && firstName.length() <= 10) { // if the name valid

			// update query
			String query = "UPDATE users SET user_firstName = \"" + firstName + "\" WHERE user_id = \"" + admin.getId()
					+ "\";";

			// execute
			Execute.executeQuery(query);

			this.refresh(); // refresh

		} else { // if the name is not valid
			this.clearEnteredText();
			this.clearError();
		} // end else

	}

	// last name
	private void updateLastName() throws ClassNotFoundException, SQLException {

		String lastName = secondNameTextField.getText().trim(); // last name

		if (ChickMethods.isNameValid(lastName) && lastName.length() <= 10) { // if the name valid

			// update query
			String query = "UPDATE users SET user_lastName = \"" + lastName + "\" WHERE user_id = \"" + admin.getId()
					+ "\";";

			// execute
			Execute.executeQuery(query);

			this.refresh(); // refresh

		} else { // if the name is not valid
			this.clearEnteredText();
			this.clearError();
		} // end else

	}

	// gender
	private void updateGender() throws ClassNotFoundException, SQLException {

		int index = genderComboBox.getSelectionModel().getSelectedIndex(); // selected index

		if (index >= 0) { // if there is selected gender

			char gender = genderComboBox.getSelectionModel().getSelectedItem(); // Gender

			// update query
			String query = "UPDATE users SET user_gender = '" + gender + "' WHERE user_id = \"" + admin.getId() + "\";";

			// execute
			Execute.executeQuery(query);

			this.refresh(); // refresh

		} else { // if there is no selected gender
			this.clearEnteredText();
			this.clearError();
		} // end else

	}

	// birth date
	private void updateBirthDate() throws ClassNotFoundException, SQLException {

		// chick if there is valid selected date
		if (birthDateDatePicker.getValue() != null && ChickMethods.isDatePickerValid(birthDateDatePicker.getValue())) {

			String birthDate = birthDate = birthDateDatePicker.getValue()
					.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // birth date

			// update query
			String query = "UPDATE users SET user_birthDate = \"" + birthDate + "\" WHERE user_id = \"" + admin.getId()
					+ "\";";

			// execute
			Execute.executeQuery(query);

			this.refresh(); // refresh

		} else { // if there is no selected date
			this.clearEnteredText();
			this.clearError();
		}

	}

	// phone1
	private void updatePhone1() throws ClassNotFoundException, SQLException {

		String phone1 = PhoneTextField.getText().trim(); // phone1
		String oldPhone1 = admin.getPhone1(); // old value of phone1

		if (ChickMethods.isPhoneNumberValid(phone1)) { // if the phone valid
			if (!this.isPhoneExist(phone1)) { // if phone does not exist in the database

				// update query
				String query = "UPDATE phone SET phone_number = \"" + phone1 + "\" WHERE user_id = \"" + admin.getId()
						+ "\" AND phone_number = \"" + oldPhone1 + "\";";

				// execute
				Execute.executeQuery(query);

				this.refresh(); // refresh

			} else { // if phone exist in the database
				this.clearEnteredText();
				this.clearError();
			}

		} else { // if the phone is not valid
			this.clearEnteredText();
			this.clearError();
		} // end else
	}

	// phone2
	private void updatePhone2() throws ClassNotFoundException, SQLException {

		String phone2 = PhoneTextField.getText().trim(); // phone2
		String oldPhone2 = admin.getPhone2(); // old value of phone2

		if (ChickMethods.isPhoneNumberValid(phone2)) { // if the phone valid
			if (!this.isPhoneExist(phone2)) { // if phone does not exist in the database
				if (!admin.getPhone2().isEmpty()) { // if there is phone value before
					// update query
					String query = "UPDATE phone SET phone_number = \"" + phone2 + "\" WHERE user_id = \""
							+ admin.getId() + "\" AND phone_number = \"" + oldPhone2 + "\";";

					// execute
					Execute.executeQuery(query);

					this.refresh(); // refresh
				} else { // if there is no phone value before

					// insert query
					String query = "INSERT INTO phone VALUES (\"" + phone2 + "\", \"" + admin.getId() + "\");";

					// execute
					Execute.executeQuery(query);

					this.refresh(); // refresh
				}

			} else { // if phone exist in the database
				this.clearEnteredText();
				this.clearError();
			}

		} else { // if the phone is not valid
			this.clearEnteredText();
			this.clearError();
		} // end else
	}

	// update password
	private void updatePassword() throws ClassNotFoundException, SQLException {

		String oldPassword = previousPasswordTextField.getText().trim(); // old password
		String newPassword = newPasswordTextField.getText().trim(); // new password

		if (!oldPassword.isEmpty() && !newPassword.isEmpty()) { // if there is entered passwords
			if (admin.getPassword().equals(oldPassword)) { // if entered old password correct
				if (newPassword.length() <= 30) {

					// update query
					String query = "UPDATE login SET user_password = \"" + newPassword + "\" WHERE user_id = \""
							+ admin.getId() + "\";";

					// execute
					Execute.executeQuery(query);

					this.refresh(); // refresh

					passwordErrorLabel.setStyle("-fx-text-fill:green;");
					passwordErrorLabel.setText("password updated");

				} else {
					passwordErrorLabel.setStyle("-fx-text-fill:red;");
					passwordErrorLabel.setText("large password");
				}

			} else { // if entered old password does not correct
				passwordErrorLabel.setStyle("-fx-text-fill:red;");
				passwordErrorLabel.setText("old password does not correct");
			}

		} else { // if there is no entered passwords
			passwordErrorLabel.setStyle("-fx-text-fill:red;");
			passwordErrorLabel.setText("Enter passwords");
		}

	}

	// ------------------------------------------------------------------------//

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
