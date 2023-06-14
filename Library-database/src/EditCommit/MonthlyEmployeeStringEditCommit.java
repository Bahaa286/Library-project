package EditCommit;

import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Controls.RefreshAdminTables;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn.CellEditEvent;
import library.ChickMethods;
import library.MonthlyEmployee;

public class MonthlyEmployeeStringEditCommit extends RefreshAdminTables
		implements EventHandler<CellEditEvent<MonthlyEmployee, String>> {

	public MonthlyEmployeeStringEditCommit() throws ClassNotFoundException, SQLException {
		super();
	}

	@Override
	public void handle(CellEditEvent<MonthlyEmployee, String> t) {

		if (t.getSource().equals(monthlyEmployeeEmploymentDate)) { // employment date
			this.updateEmploymentDate(t);
		} else if (t.getSource().equals(monthlyEmployeeFirstName)) { // first name
			this.updateFirstName(t);
		} else if (t.getSource().equals(monthlyEmployeeLastName)) { // last name
			this.updateLastName(t);
		} else if (t.getSource().equals(monthlyEmployeeBirthDate)) { // birth date
			this.updateBirthDate(t);
		} else if (t.getSource().equals(monthlyEmployeePassword)) { // password
			this.updatePassword(t);
		} else if (t.getSource().equals(monthlyEmployeeFirstPhone)) { // phone1
			this.updatePhone1(t);
		} else if (t.getSource().equals(monthlyEmployeeSecondPhone)) { // phone2
			this.updatePhone2(t);
		} else if (t.getSource().equals(monthlyEmployeeFinantialPalance)) { // financial balance
			this.updateFinancialBalance(t);
		} else if (t.getSource().equals(monthlyEmployeeSalary)) { // salary
			this.updateSalary(t);
		} else if (t.getSource().equals(monthlyEmployeeAllowedVactionDay)) { // allowed vacation days
			this.updateAllowedVacationDays(t);
		} else if (t.getSource().equals(monthlyEmployeeVactionDay)) { // vacation days
			this.updateVacationDays(t);
		}

	} // end handle

	// -----------------------------------------------------------------------------------------//
	// update employment date
	private void updateEmploymentDate(CellEditEvent<MonthlyEmployee, String> t) {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getEmploymentDate();

		if (ChickMethods.isBirthDateValid(t.getNewValue())) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmploymentDate(t.getNewValue());

			try {
				this.updateEmploymentDateInDatabase(t.getRowValue().getId(), t.getNewValue());
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setEmploymentDate(oldValue);
		}

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

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
	private void updateFirstName(CellEditEvent<MonthlyEmployee, String> t) {

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

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

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
	private void updateLastName(CellEditEvent<MonthlyEmployee, String> t) {

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

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

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
	private void updateBirthDate(CellEditEvent<MonthlyEmployee, String> t) {

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

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

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
	private void updatePassword(CellEditEvent<MonthlyEmployee, String> t) {

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

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

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
	private void updatePhone1(CellEditEvent<MonthlyEmployee, String> t) {

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

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

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
	private void updatePhone2(CellEditEvent<MonthlyEmployee, String> t) {

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

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

	}

	// -----------------------------------------------------------------------------------------//
	// update financial balance
	private void updateFinancialBalance(CellEditEvent<MonthlyEmployee, String> t) {

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

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

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
	// update salary
	private void updateSalary(CellEditEvent<MonthlyEmployee, String> t) {

		// the old value of the salary
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getSalary();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		// chick if new value valid
		if (!newValue.isEmpty() && ChickMethods.isFloat(newValue) && Double.parseDouble(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setSalary(newValue);

			try {
				this.updateSalaryInDatabase(t.getRowValue().getId(), newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(oldValue);
		}

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

	}

	// update salary
	private void updateSalaryInDatabase(String id, String salary) throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE employee\r\n" + "SET salary = \"" + salary + "\"\r\n" + "WHERE user_id = " + id;

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update allowed vacation days
	private void updateAllowedVacationDays(CellEditEvent<MonthlyEmployee, String> t) {

		// the old value of the allowed vacation days
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getAllowedVacationDays();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		// chick if new value valid
		if (!newValue.isEmpty() && ChickMethods.isDigit(newValue) && Integer.parseInt(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setAllowedVacationDays(newValue);

			try {
				this.updateAllowedVacationDaysInDatabase(t.getRowValue().getId(), Integer.parseInt(oldValue),
						Integer.parseInt(newValue), Integer.parseInt(t.getRowValue().getVacationDays()));
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setAllowedVacationDays(oldValue);
		}

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

	}

	// update allowed vacation days
	private void updateAllowedVacationDaysInDatabase(String id, int oldAllowedVacationDays, int newAllowedVacationDays,
			int vacationDays) throws ClassNotFoundException, SQLException {

		// chick if vacation days and allowed vacation days from deduction table in the
		// database is for a unique monthly employee
		boolean isUnique = super.isvacationDaysAllowedvacationDaysForUniqueEmployee(vacationDays,
				oldAllowedVacationDays, id);

		if (isUnique) { // if information unique

			// deduction table update query
			String deductionTableUpdateQuery = "UPDATE deduction\r\n" + "SET allowd_vaction_days = "
					+ newAllowedVacationDays + "\r\n" + "WHERE vaction_days = " + vacationDays + " AND\r\n"
					+ "      allowd_vaction_days = " + oldAllowedVacationDays + ";";

			try { // if the new value already exist in the database it will not be updated
				Execute.executeQuery(deductionTableUpdateQuery);
			} catch (ClassNotFoundException | SQLException e) {

				// monthly employee update query
				String monthlyEmployeeTableUpdate = "UPDATE monthly_employee\r\n" + "SET allowd_vaction_days = "
						+ newAllowedVacationDays + "\r\n" + "WHERE user_id = \"" + id + "\";";

				// execute
				Execute.executeQuery(monthlyEmployeeTableUpdate);

				// deduction table delete query
				String deductionTableDeleteQuery = "DELETE FROM deduction\r\n" + "WHERE vaction_days = " + vacationDays
						+ " AND\r\n" + "     allowd_vaction_days = " + oldAllowedVacationDays + ";";

				// execute
				Execute.executeQuery(deductionTableDeleteQuery);

			}

		} else {

			// deduction table insert
			String deductionTableInsert = "INSERT INTO deduction VALUES(" + vacationDays + ",\r\n"
					+ newAllowedVacationDays + ",\r\n"
					+ " IF(vaction_days > allowd_vaction_days, (vaction_days - allowd_vaction_days)/(30 - allowd_vaction_days)  ,  0));";

			// execute
			Execute.executeQuery(deductionTableInsert);

			// monthly employee update query
			String monthlyEmployeeTableUpdate = "UPDATE monthly_employee\r\n" + "SET allowd_vaction_days = "
					+ newAllowedVacationDays + "\r\n" + "WHERE user_id = \"" + id + "\";";

			// execute
			Execute.executeQuery(monthlyEmployeeTableUpdate);

		} // end else

		// update deduction
		this.updateDeduction(vacationDays, newAllowedVacationDays);

	}

	// -----------------------------------------------------------------------------------------//
	// update vacation days
	private void updateVacationDays(CellEditEvent<MonthlyEmployee, String> t) {

		// the old value of the vacation days
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getVacationDays();

		// the new Value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		// chick if new value valid
		if (!newValue.isEmpty() && ChickMethods.isDigit(newValue) && Integer.parseInt(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setVacationDays(newValue);

			try {
				this.updateVacationDaysInDatabase(t.getRowValue().getId(), Integer.parseInt(oldValue),
						Integer.parseInt(newValue), Integer.parseInt(t.getRowValue().getAllowedVacationDays()));
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			(t.getTableView().getItems().get(t.getTablePosition().getRow())).setVacationDays(oldValue);
		}

		// refresh MONTHLYEMPLOYEE table
		super.refreshTablesData(MONTHLYEMPLOYEE);

	}

	// update vacation days
	private void updateVacationDaysInDatabase(String id, int oldVacationDays, int newVacationDays,
			int allowedVacationDays) throws ClassNotFoundException, SQLException {

		// chick if vacation days and allowed vacation days from deduction table in the
		// database is for a unique monthly employee
		boolean isUnique = super.isvacationDaysAllowedvacationDaysForUniqueEmployee(oldVacationDays,
				allowedVacationDays, id);

		if (isUnique) { // if information unique

			// deduction table update query
			String deductionTableUpdateQuery = "UPDATE deduction\r\n" + "SET vaction_days = " + newVacationDays + "\r\n"
					+ "WHERE vaction_days = " + oldVacationDays + " AND\r\n" + "      allowd_vaction_days = "
					+ allowedVacationDays + ";";

			try { // if the new value already exist in the database it will not be updated
				Execute.executeQuery(deductionTableUpdateQuery);
			} catch (ClassNotFoundException | SQLException e) {

				// monthly employee update query
				String monthlyEmployeeTableUpdate = "UPDATE monthly_employee\r\n" + "SET vaction_days = "
						+ newVacationDays + "\r\n" + "WHERE user_id = \"" + id + "\";";

				// execute
				Execute.executeQuery(monthlyEmployeeTableUpdate);

				// deduction table delete query
				String deductionTableDeleteQuery = "DELETE FROM deduction\r\n" + "WHERE vaction_days = "
						+ oldVacationDays + " AND\r\n" + "     allowd_vaction_days = " + oldVacationDays + ";";

				// execute
				Execute.executeQuery(deductionTableDeleteQuery);

			}

		} else {

			// deduction table insert
			String deductionTableInsert = "INSERT INTO deduction VALUES(" + newVacationDays + ",\r\n"
					+ allowedVacationDays + ",\r\n"
					+ " IF(vaction_days > allowd_vaction_days, (vaction_days - allowd_vaction_days)/(30 - allowd_vaction_days)  ,  0));";

			// execute
			Execute.executeQuery(deductionTableInsert);

			// monthly employee update query
			String monthlyEmployeeTableUpdate = "UPDATE monthly_employee\r\n" + "SET vaction_days = " + newVacationDays
					+ "\r\n" + "WHERE user_id = \"" + id + "\";";

			// execute
			Execute.executeQuery(monthlyEmployeeTableUpdate);

		} // end else

		// update deduction
		this.updateDeduction(newVacationDays, allowedVacationDays);

	}

	// -----------------------------------------------------------------------------------------//
	// update deduction
	private void updateDeduction(int vacationDays, int allowedVacationDays)
			throws ClassNotFoundException, SQLException {

		// deduction percentage value
		double deduction = (vacationDays > allowedVacationDays)
				? 1.0 * (vacationDays - allowedVacationDays) / (30 - allowedVacationDays)
				: 0;

		// update deduction query
		String query = "UPDATE deduction\r\n" + "SET deduction = " + deduction + "\r\n" + "WHERE vaction_days = "
				+ vacationDays + " AND\r\n" + "     allowd_vaction_days = " + allowedVacationDays + ";";

		// execute
		Execute.executeQuery(query);
	}
} // end class
