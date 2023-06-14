package EditCommit;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Controls.RefreshAdminTables;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import library.ChickMethods;
import library.HourlyEmployee;

public class HourlyEmployeeStringEditCommit extends RefreshAdminTables
		implements EventHandler<CellEditEvent<HourlyEmployee, String>> {

	public HourlyEmployeeStringEditCommit() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	public void handle(CellEditEvent<HourlyEmployee, String> t) {

		if (t.getSource().equals(hourlyEmployeeEmploymentDate)) { // employment date
			this.updateEmploymentDate(t);
		} else if (t.getSource().equals(hourlyEmployeeFirstName)) { // first name
			this.updateFirstName(t);
		} else if (t.getSource().equals(hourlyEmployeeLastName)) { // last name
			this.updateLastName(t);
		} else if (t.getSource().equals(hourlyEmployeeBirthDate)) { // birth date
			this.updateBirthDate(t);
		} else if (t.getSource().equals(hourlyEmployeePassword)) { // password
			this.updatePassword(t);
		} else if (t.getSource().equals(hourlyEmployeeFirstPhone)) { // phone1
			this.updatePhone1(t);
		} else if (t.getSource().equals(hourlyEmployeeSecondPhone)) { // phone2
			this.updatePhone2(t);
		} else if (t.getSource().equals(hourlyEmployeeFinantialPalance)) { // financial balance
			this.updateFinancialBalance(t);
		} else if (t.getSource().equals(hourlyEmployeeWorkingHour)) { // working hour
			this.updateWorkingHours(t);
		} else if (t.getSource().equals(hourlyEmployeeSalaryPerHour)) { // salary/hour
			this.updateSalaryPerHour(t);
		}

	} // end handle

	// -----------------------------------------------------------------------------------------//
	// update employment date
	private void updateEmploymentDate(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getEmploymentDate();

		if (!t.getNewValue().isEmpty() && ChickMethods.isBirthDateValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmploymentDate(t.getNewValue());

			try {
				this.updateEmploymentDateInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmploymentDate(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

	}

	// update employment date
	private void updateEmploymentDateInDatabase(String id, String employmentDate)
			throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE employee\r\n" + "SET employment_date = \"" + employmentDate + "\"\r\n"
				+ "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update first name
	private void updateFirstName(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the first name
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getFirstName();

		if (!t.getNewValue().isEmpty() && ChickMethods.isNameValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstName(t.getNewValue());

			try {
				this.updateFirstNameInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setFirstName(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

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
	private void updateLastName(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the first name
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getLastName();

		if (!t.getNewValue().isEmpty() && ChickMethods.isNameValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setLastName(t.getNewValue());

			try {
				this.updateLastNameInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setLastName(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

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
	private void updateBirthDate(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBirthDate();

		if (!t.getNewValue().isEmpty() && ChickMethods.isBirthDateValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setBirthDate(t.getNewValue());

			try {
				this.updateBirthDateInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setBirthDate(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

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
	private void updatePassword(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPassword();

		if (!t.getNewValue().isEmpty() && t.getNewValue().trim().length() != 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPassword(t.getNewValue());

			try {
				this.updatePasswordInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPassword(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

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
	private void updatePhone1(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPhone1();

		// is phone exist boolean
		boolean isPhoneExist = true;

		// chick if phone exist
		try {
			isPhoneExist = super.isPhoneExist(t.getNewValue());
		} catch (ClassNotFoundException | SQLException e1) {
		}

		if (!t.getNewValue().isEmpty() && ChickMethods.isPhoneNumberValid(t.getNewValue()) && !isPhoneExist) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone1(t.getNewValue());

			try {
				this.updatePhoneInDatabase(t.getRowValue().getId(), oldValue, t.getNewValue());

			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone1(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

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
	private void updatePhone2(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPhone2();

		// is phone exist boolean
		boolean isPhoneExist = true;

		// chick if phone exist
		try {
			isPhoneExist = super.isPhoneExist(t.getNewValue());
		} catch (ClassNotFoundException | SQLException e1) {
		}

		if (!t.getNewValue().isEmpty() && ChickMethods.isPhoneNumberValid(t.getNewValue()) && !isPhoneExist) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone2(t.getNewValue());

			try {
				this.updatePhoneInDatabase(t.getRowValue().getId(), oldValue, t.getNewValue());

			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPhone2(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

	}

	// -----------------------------------------------------------------------------------------//
	// update financial balance
	private void updateFinancialBalance(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the financial balance
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getFinantialPalance();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

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

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

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

	// -----------------------------------------------------------------------------------------//
	// update working hours
	private void updateWorkingHours(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the working hours
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getWorkingHour();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && ChickMethods.isFloat(newValue) && Double.parseDouble(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setWorkingHour(newValue);

			try {
				this.updateWorkingHoursInDatabase(t.getRowValue().getId(), Double.parseDouble(oldValue),
						Double.parseDouble(newValue), Double.parseDouble(t.getRowValue().getSalaryPerHour()));
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setWorkingHour(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

	}

	// update working hours
	private void updateWorkingHoursInDatabase(String id, double oldWorkingHours, double newWorkingHours,
			double salaryPerHour) throws ClassNotFoundException, SQLException {

		// chick if working hours and salary/hour from salary table in the database is
		// for a unique employee
		boolean isUnique = super.isWorkingHoursSalaryPerHourForUniqueEmployee(oldWorkingHours, salaryPerHour, id);

		if (isUnique) {

			// new salary value
			double salary = newWorkingHours * salaryPerHour;

			// update on salary table query
			String salaryTableUpdateQuery = "UPDATE salary\r\n" + "SET workingHours = " + newWorkingHours + ", \r\n"
					+ "	salary = " + salary + "\r\n" + "WHERE workingHours = " + oldWorkingHours + " AND\r\n"
					+ "      salaryPerHour = " + salaryPerHour + ";";

			// execute
			try { // if the new value already exist in the database it will not be updated
				Execute.executeQuery(salaryTableUpdateQuery);
			} catch (ClassNotFoundException | SQLException e) {

				// update working hours and salary/hour in the hourly employee table query
				String hourluEmployeeTableUpdateQuery = "UPDATE hourly_employee\r\n" + "SET workingHours = "
						+ newWorkingHours + "\r\n" + "WHERE user_id = \"" + id + "\";";

				// execute
				Execute.executeQuery(hourluEmployeeTableUpdateQuery);

				// delete unusable data for salary from salary table
				this.deleteFromSalaryTable(oldWorkingHours, salaryPerHour);
			}

		} else {

			// insert the new values into salary table query
			String salaryTableInsertQuery = "INSERT INTO salary VALUES( " + newWorkingHours + ",\r\n" + salaryPerHour
					+ ",\r\n" + "                            workingHours * salaryPerHour );";

			// execute
			Execute.executeQuery(salaryTableInsertQuery);

			// update working hours and salary/hour in the hourly employee table query
			String hourluEmployeeTableUpdateQuery = "UPDATE hourly_employee\r\n" + "SET workingHours = "
					+ newWorkingHours + "\r\n" + "WHERE user_id = \"" + id + "\";";

			// execute
			Execute.executeQuery(hourluEmployeeTableUpdateQuery);

		} // end else

	} // end updateWorkingHoursInDatabase method

	// -----------------------------------------------------------------------------------------//
	// update salary/hour
	private void updateSalaryPerHour(CellEditEvent<HourlyEmployee, String> t) {

		// the old value of the salary/hour
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getSalaryPerHour();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		// chick if new value valid
		if (!newValue.isEmpty() && ChickMethods.isFloat(newValue) && Double.parseDouble(newValue) >= 0) {
			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setSalaryPerHour(newValue);

			try {
				this.updateSalaryPerHourInDatabase(t.getRowValue().getId(), Double.parseDouble(oldValue),
						Double.parseDouble(newValue), Double.parseDouble(t.getRowValue().getWorkingHour()));
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else { // new value does not valid
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalaryPerHour(oldValue);
		}

		// refresh HOURLYEMPLOYEE table
		super.refreshTablesData(HOURLYEMPLOYEE);

	}

	// update salary/hour
	private void updateSalaryPerHourInDatabase(String id, double oldSalaryPerHour, double newSalaryPerHour,
			double workingHours) throws ClassNotFoundException, SQLException {

		// chick if working hours and salary/hour from salary table in the database is
		// for a unique employee
		boolean isUnique = super.isWorkingHoursSalaryPerHourForUniqueEmployee(workingHours, oldSalaryPerHour, id);

		if (isUnique) {

			// new salary value
			double salary = newSalaryPerHour * workingHours;

			// update on salary table query
			String salaryTableUpdateQuery = "UPDATE salary\r\n" + "SET salaryPerHour = " + newSalaryPerHour + ", \r\n"
					+ "	salary = " + salary + "\r\n" + "WHERE workingHours = " + workingHours + " AND\r\n"
					+ "      salaryPerHour = " + oldSalaryPerHour + ";";

			// execute
			try { // if the new value already exist in the database it will not be updated
				Execute.executeQuery(salaryTableUpdateQuery);
			} catch (ClassNotFoundException | SQLException e) {

				// update working hours and salary/hour in the hourly employee table query
				String hourluEmployeeTableUpdateQuery = "UPDATE hourly_employee\r\n" + "SET salaryPerHour = "
						+ newSalaryPerHour + "\r\n" + "WHERE user_id = \"" + id + "\";";

				// execute
				Execute.executeQuery(hourluEmployeeTableUpdateQuery);

				// delete unusable data for salary from salary table
				this.deleteFromSalaryTable(workingHours, oldSalaryPerHour);

			}

		} else {

			// insert the new values into salary table query
			String salaryTableInsertQuery = "INSERT INTO salary VALUES( " + workingHours + ",\r\n" + newSalaryPerHour
					+ ",\r\n" + "                            workingHours * salaryPerHour );";

			// execute
			Execute.executeQuery(salaryTableInsertQuery);

			// update working hours and salary/hour in the hourly employee table query
			String hourluEmployeeTableUpdateQuery = "UPDATE hourly_employee\r\n" + "SET salaryPerHour = "
					+ newSalaryPerHour + "\r\n" + "WHERE user_id = \"" + id + "\";";

			// execute
			Execute.executeQuery(hourluEmployeeTableUpdateQuery);

		} // end else

	} // end updateSalaryPerHourInDatabase method

	// -----------------------------------------------------------------------------------------//
	// delete from salary table

	private void deleteFromSalaryTable(double workingHours, double salaryPerHour)
			throws ClassNotFoundException, SQLException {

		// delete query
		String query = "DELETE FROM salary\r\n" + "WHERE workingHours = " + workingHours + "AND\r\n"
				+ "      salaryPerHour = " + salaryPerHour + ";";

		// execute
		Execute.executeQuery(query);
	}
} // end HourlyEmployeeStringEditCommit class
