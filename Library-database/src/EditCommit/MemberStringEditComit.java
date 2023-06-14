package EditCommit;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Controls.RefreshAdminTables;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import library.ChickMethods;
import library.Member;

public class MemberStringEditComit extends RefreshAdminTables implements EventHandler<CellEditEvent<Member, String>> {

	public MemberStringEditComit() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	public void handle(CellEditEvent<Member, String> t) {

		if (t.getSource().equals(memberFirstName)) { // update first name
			this.updateFirstName(t);
		} else if (t.getSource().equals(memberLastName)) { // update last name
			this.updateLastName(t);
		} else if (t.getSource().equals(memberBirthDate)) { // update birth date
			this.updateBirthDate(t);
		} else if (t.getSource().equals(memberPassword)) { // update password
			this.updatePassword(t);
		} else if (t.getSource().equals(memberFirstPhone)) { // update phone1
			this.updatePhone1(t);
		} else if (t.getSource().equals(memberSecondPhone)) { // update phone2
			this.updatePhone2(t);
		} else if (t.getSource().equals(memberFees)) { // fees
			this.updateFees(t);
		} else if (t.getSource().equals(memberFinantialPalance)) { // financial balance
			this.updateFinancialBalance(t);
		}

	} // end handle

	// -----------------------------------------------------------------------------------------//
	// update first name
	private void updateFirstName(CellEditEvent<Member, String> t) {

		// the old value of the first name
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getFirstName();

		if (ChickMethods.isNameValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstName(t.getNewValue());

			try {
				this.updateFirstNameInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstName(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// update first name
	private void updateFirstNameInDatabase(String id, String firstName) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE users\r\n" + "SET user_firstName = \"" + firstName + "\"\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update last name
	private void updateLastName(CellEditEvent<Member, String> t) {

		// the old value of the first name
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getLastName();

		if (ChickMethods.isNameValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setLastName(t.getNewValue());

			try {
				this.updateLastNameInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setLastName(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// update last name
	private void updateLastNameInDatabase(String id, String lastName) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE users\r\n" + "SET user_lastName = \"" + lastName + "\"\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update birth date
	private void updateBirthDate(CellEditEvent<Member, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBirthDate();

		if (ChickMethods.isBirthDateValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setBirthDate(t.getNewValue());

			try {
				this.updateBirthDateInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setBirthDate(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// update birth date
	private void updateBirthDateInDatabase(String id, String birthDate) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE users\r\n" + "SET user_birthDate = \"" + birthDate + "\"\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update password
	private void updatePassword(CellEditEvent<Member, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPassword();

		if (t.getNewValue().trim().length() != 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPassword(t.getNewValue());

			try {
				this.updatePasswordInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPassword(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	} // end updatePassword method

	// update password
	private void updatePasswordInDatabase(String id, String password) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE login\r\n" + "SET user_password = \"" + password + "\"\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update phone1 birth date
	private void updatePhone1(CellEditEvent<Member, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPhone1();

		// is phone exist boolean
		boolean isPhoneExist = true;

		// chick if phone exist
		try {
			isPhoneExist = super.isPhoneExist(t.getNewValue());
		} catch (ClassNotFoundException | SQLException e1) {
		}

		if (ChickMethods.isPhoneNumberValid(t.getNewValue()) && !isPhoneExist) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone1(t.getNewValue());

			try {
				this.updatePhoneInDatabase(t.getRowValue().getId(), oldValue, t.getNewValue());

			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone1(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// update phone1
	private void updatePhoneInDatabase(String id, String oldPhone, String newPhone)
			throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE phone\r\n" + "SET phone_number = \"" + newPhone + "\"\r\n" + "WHERE user_id = \"" + id
				+ "\" AND\r\n" + "      phone_number = \"" + oldPhone + "\";";
		
		if(oldPhone.isEmpty()) // if there is no phone
			query = "INSERT INTO phone VALUES (\"" + newPhone + "\", \"" + id + "\");";

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update phone2 birth date
	private void updatePhone2(CellEditEvent<Member, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPhone2();

		// is phone exist boolean
		boolean isPhoneExist = true;

		// chick if phone exist
		try {
			isPhoneExist = super.isPhoneExist(t.getNewValue());
		} catch (ClassNotFoundException | SQLException e1) {
		}

		if (ChickMethods.isPhoneNumberValid(t.getNewValue()) && !isPhoneExist) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone2(t.getNewValue());

			try {
				this.updatePhoneInDatabase(t.getRowValue().getId(), oldValue, t.getNewValue());

			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone2(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// -----------------------------------------------------------------------------------------//
	// update fees
	private void updateFees(CellEditEvent<Member, String> t) {

		// the old value of the fees
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getFees();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		// chick if new value valid
		if (!newValue.isEmpty() && ChickMethods.isFloat(newValue) && Double.parseDouble(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setFees(newValue);

			try {
				this.updateFeesInDatabase(t.getRowValue().getId(), newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setFees(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// update fees
	private void updateFeesInDatabase(String id, String fees) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE member\r\n" + "SET fees = \"" + fees + "\"\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update financial balance
	private void updateFinancialBalance(CellEditEvent<Member, String> t) {

		// the old value of the financial balance
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getFinantialPalance();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		// chick if new value valid
		if (!newValue.isEmpty() && ChickMethods.isFloat(newValue) && Double.parseDouble(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setFinantialPalance(newValue);

			try {
				this.updateFinancialBalanceInDatabase(t.getRowValue().getId(), newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setFinantialPalance(oldValue);
		}

		// refresh member table
		super.refreshTablesData(MEMBER);

	}

	// update financial balance
	private void updateFinancialBalanceInDatabase(String id, String financialBalance)
			throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE users\r\n" + "SET user_finantialPalance = \"" + financialBalance + "\"\r\n"
				+ "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);
	}
} // end MemberEditComit method
