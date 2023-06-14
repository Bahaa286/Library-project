package Controls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectionsToMYSQL.ConnectToDatabase;
import ConnectionsToMYSQL.ConnectionsText;
import ConnectionsToMYSQL.Execute;
import Layout.AdminLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import library.HourlyEmployee;
import library.Member;
import library.MonthlyEmployee;
import library.Rule;

public class RefreshAdminTables extends AdminLayout {

	public RefreshAdminTables() throws ClassNotFoundException, SQLException {
		super();
	}

	// -----------------------------------------------------------------------------------------//
	// Database Connections
	private ConnectToDatabase connectToDatabase = new ConnectToDatabase(ConnectionsText.URL, ConnectionsText.port,
			ConnectionsText.dbName, ConnectionsText.dbUsername, ConnectionsText.dbPassword);

	// ---------------------------------------------------------------------------------------------//
	// --------------------------------------------------------------------------------//
	// pane backgrounds

	// member background
	protected void memberPaneBakground(boolean isError, int index) {

		if (isError) {
			memberOptionsPane[index].setStyle("-fx-border-color: red");
		} else {
			memberOptionsPane[index].setStyle("-fx-border-color: Aqua");
		}

	}

	// hourly employee background
	protected void hourlyEmployeePaneBakground(boolean isError, int index) {

		if (isError) {
			hourlyEmployeeOptionsPane[index].setStyle("-fx-border-color: red");
		} else {
			hourlyEmployeeOptionsPane[index].setStyle("-fx-border-color: Aqua");
		}

	}

	// monthly employee background
	protected void monthlyEmployeePaneBakground(boolean isError, int index) {

		if (isError) {
			monthlyEmployeeOptionsPane[index].setStyle("-fx-border-color: red");
		} else {
			monthlyEmployeeOptionsPane[index].setStyle("-fx-border-color: Aqua");
		}

	}

	// -------------------------------------------------------------------------//
	// clear all informations method

	// clear all
	protected void clear() {

		// clear errors
		this.clearError();

		// clear selected on tables
		this.clearSelectedOnTable();

		// clear TextFields
		this.clearTextField();

		// clear information from single employee rules ListView
		this.clearRulesForSingleEmpListView();
	}

	// clear error
	protected void clearError() {

		// clear hourly employee error options
		int hourlyEmpError = hourlyEmployeeErrorsOption.length;
		// clear each each error label
		for (int i = 0; i < hourlyEmpError; i++) {
			hourlyEmployeeErrorsOption[i].setText("");
			this.hourlyEmployeePaneBakground(false, i);
		}

		// clear monthly employee error options
		int monthlyEmpError = monthlyEmployeeErrorsOption.length;
		// clear each each error label
		for (int i = 0; i < monthlyEmpError; i++) {
			monthlyEmployeeErrorsOption[i].setText("");
			this.monthlyEmployeePaneBakground(false, i);
		}

		// clear employee error options
		int memberError = memberErrorsOption.length;
		// clear each each error label
		for (int i = 0; i < memberError; i++) {
			memberErrorsOption[i].setText("");
			this.memberPaneBakground(false, i);
		}

		// clear newEmp error label
		newEmpErrorSalary.setText("");

		// rules for single employee error
		rulesOptionForSingleEmpErrorLabel.setText("");

	}

	// clear selected
	protected void clearSelectedOnTable() {

		// clear member selection
		memberTable.getSelectionModel().clearSelection();

		// clear hourly employee selection
		hourlyEmployeeTable.getSelectionModel().clearSelection();

		// clear monthly employee selection
		monthlyEmployeeTable.getSelectionModel().clearSelection();

	}

	// clear textFields information
	protected void clearTextField() {
		// clear employee textFields
		int hourlyEmpText = hourlyEmployeeTextFields.length;
		for (int i = 0; i < hourlyEmpText; i++) { // for each TextField
			if (i != 3 && i != 2) { // if index not 3 since 3 is for DatePicker
				hourlyEmployeeTextFields[i].clear();
			} else { // if the index is 3 (for the DatePicker)
				hourlyEmployeeDateOfBirthDatePicker.setValue(null);
			}

		} // end for

		// clear hourly employee textFields
		int memberText = memberTextFields.length;
		for (int i = 0; i < memberText; i++) { // for each TextField
			if (i != 3 && i != 2) { // if index not 3 since 3 is for DatePicker
				memberTextFields[i].clear();
			} else { // if the index is 3 (for the DatePicker)
				memberDateOfBirthDatePicker.setValue(null);
			}
		} // end for

		// clear monthly employee textFields
		int monthlyEmpText = monthlyEmployeeTextFields.length;
		for (int i = 0; i < monthlyEmpText; i++) { // for each TextField

			if (i != 3 && i != 2) { // if index not 3 since 3 is for DatePicker
				monthlyEmployeeTextFields[i].clear();
			} else { // if the index is 3 (for the DatePicker)
				monthlyEmployeeDateOfBirthDatePicker.setValue(null);
			}

		} // end for

		// clear newEmpSalary textField
		newEmpSalary.clear();
	}

	// clear information from single employee rules ListView
	protected void clearRulesForSingleEmpListView() {
		rulesListView.getItems().clear();
	}

	// ----------------------------------------------------------------------------------//
	// refresh tables

	protected void refreshAllTables() {
		this.refreshTablesData(MEMBER);
		this.refreshTablesData(HOURLYEMPLOYEE);
		this.refreshTablesData(MONTHLYEMPLOYEE);
	}

	protected void refreshTablesData(int tableNum) {

		this.clear();

		if (tableNum == MEMBER) { // employee table

			// clear data in memberData Observable list
			memberData.clear();

			// insert data from database into TableView
			this.insertMemberDataIntoDataBase();

		} else if (tableNum == HOURLYEMPLOYEE) { // hourly employee table

			// clear data in hourlyEmployeeData Observable list
			hourlyEmployeeData.clear();

			// insert data from database into TableView
			this.insertHourlyEmployeeDataIntoDataBase();

		} else if (tableNum == MONTHLYEMPLOYEE) { // monthly employee table

			// clear data in hourlyEmployeeData Observable list
			monthlyEmployeeData.clear();

			// insert data from database into TableView
			this.insertMonthlyEmployeeDataIntoDataBase();

		}

	} // end refreshTablesData method

	protected void refreshRulesListView(ArrayList<Rule> rules) {

		// clear ListView items
		rulesListView.getItems().clear();

		// ArrayList size
		int size = rules.size();

		for (int i = 0; i < size; i++)
			rulesListView.getItems().add(rules.get(i).toString());
	} // end refreshRulesListView method

	// ----------------------------------------------------------------------------------//
	// chick methods

	// chick if phone exit
	protected boolean isPhoneExist(String phone) throws ClassNotFoundException, SQLException {

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

	// chick if working hours and salary/hour from salary table in the database is
	// for a unique employee
	protected boolean isWorkingHoursSalaryPerHourForUniqueEmployee(double workingHours, double salaryPerHour,
			String empId) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT * \r\n" + "FROM hourly_employee HE, salary S\r\n"
				+ "WHERE HE.workingHours = S.workingHours AND\r\n" + "      HE.salaryPerHour = S.salaryPerHour AND\r\n"
				+ "      S.salaryPerHour = " + salaryPerHour + " AND\r\n" + "      S.workingHours = " + workingHours
				+ " AND\r\n" + "      HE.user_id <> \"" + empId + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is unique boolean
		boolean isUnique = !Execute.resultSet.next();

		// close
		Execute.close();

		return isUnique;

	} // end isWorkingHoursSalaryPerHourForUniqueEmployee method

	// chick if vacation days and allowed vacation days from deduction table in the
	// database is for a unique monthly employee
	protected boolean isvacationDaysAllowedvacationDaysForUniqueEmployee(int vacationDays, int AllowedVacationDays,
			String empId) throws ClassNotFoundException, SQLException {

		// select query
		String query = "SELECT * FROM monthly_employee ME, deduction D\r\n"
				+ "WHERE ME.vaction_days = D.vaction_days AND\r\n"
				+ "      ME.allowd_vaction_days = D.allowd_vaction_days AND\r\n" + "      ME.vaction_days = "
				+ vacationDays + " AND\r\n" + "      ME.allowd_vaction_days = " + AllowedVacationDays + " AND\r\n"
				+ "      ME.user_id <> \"" + empId + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is unique boolean
		boolean isUnique = !Execute.resultSet.next();

		// close
		Execute.close();

		return isUnique;

	}

	// -----------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------//
	// insert data into table

	// ----------------------------------------------------------------------------------//
	// member
	protected static final ObservableList<Member> memberData = FXCollections.observableArrayList();

	// insert all information from the database into member table
	protected void insertMemberDataIntoDataBase() {

		try {
			this.getMemberData();
			this.addDataOnMemberTable();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// query to get member data from database
	protected void getMemberData() throws ClassNotFoundException, SQLException {

		// members with 2 phone numbers
		String query1 = "SELECT U1.user_id, U1.user_firstName, U1.user_lastName, U1.user_gender, U1.user_birthDate, U1.user_finantialPalance, M.fees, P1.phone_Number, P2.phone_Number, L.user_password\r\n"
				+ "FROM users U1, phone P1, users U2, phone P2, member M, login L\r\n"
				+ "WHERE U1.user_id = P1.user_id AND\r\n" + "      U2.user_id = P2.user_id AND \r\n"
				+ "      U1.user_id =  U2.user_id AND\r\n" + "      M.user_id = U1.user_id AND\r\n"
				+ "      L.user_id = U1.user_id AND\r\n" + "      P1.phone_number > P2.phone_number;";

		// execute query
		Execute.setResultSELECTQuery(query1);

		// insert from database into
		while (Execute.resultSet.next()) {

			memberData.add(new Member(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), Execute.resultSet.getString(6), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), Execute.resultSet.getString(9), Execute.resultSet.getString(10)));

		} // end while

		// close connection
		Execute.close();

		// members with 1 phone number
		String query2 = "SELECT U1.user_id, U1.user_firstName, U1.user_lastName, U1.user_gender, U1.user_birthDate, U1.user_finantialPalance, M.fees, P1.phone_Number, L.user_password \r\n"
				+ "FROM users U1, phone P1, member M, login L\r\n" + "WHERE U1.user_id = P1.user_id AND \r\n"
				+ "M.user_id = U1.user_id AND\r\n" + " L.user_id = U1.user_id AND\r\n" + "U1.user_id NOT IN (\r\n"
				+ "         SELECT U1.user_id\r\n" + "         FROM users U1, phone P1, users U2, phone P2 \r\n"
				+ "         WHERE U1.user_id = P1.user_id AND\r\n" + "               U2.user_id = P2.user_id AND \r\n"
				+ "               U1.user_id =  U2.user_id AND\r\n"
				+ "               P1.phone_number > P2.phone_number\r\n" + "       );  ";

		// execute query
		Execute.setResultSELECTQuery(query2);

		// insert from database into
		while (Execute.resultSet.next()) {

			memberData.add(new Member(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), (Execute.resultSet.getString(6)), (Execute.resultSet.getString(7)),
					Execute.resultSet.getString(8), Execute.resultSet.getString(9)));

		} // end while

		// close connection
		Execute.close();

	}

	// add date on table
	protected void addDataOnMemberTable() {

		memberId.setCellValueFactory(new PropertyValueFactory<Member, String>("id"));
		memberFirstName.setCellValueFactory(new PropertyValueFactory<Member, String>("firstName"));
		memberLastName.setCellValueFactory(new PropertyValueFactory<Member, String>("lastName"));
		memberGender.setCellValueFactory(new PropertyValueFactory<Member, Character>("gender"));
		memberBirthDate.setCellValueFactory(new PropertyValueFactory<Member, String>("birthDate"));
		memberFinantialPalance.setCellValueFactory(new PropertyValueFactory<Member, String>("finantialPalance"));
		memberFees.setCellValueFactory(new PropertyValueFactory<Member, String>("fees"));
		memberFirstPhone.setCellValueFactory(new PropertyValueFactory<Member, String>("phone1"));
		memberSecondPhone.setCellValueFactory(new PropertyValueFactory<Member, String>("phone2"));
		memberPassword.setCellValueFactory(new PropertyValueFactory<Member, String>("password"));

		memberTable.setItems(memberData);

	}

	// ----------------------------------------------------------------------------------//
	// hourly employee
	protected static final ObservableList<HourlyEmployee> hourlyEmployeeData = FXCollections.observableArrayList();

	// insert all information from the database into hourlyEmployee table
	protected void insertHourlyEmployeeDataIntoDataBase() {

		try {
			this.getHourlyEmployeeData();
			this.addDataOnHourlyEmployeeTable();
			this.insertIntoRulesComboBox(hourlyEmployeeRulesComboBox);
			this.insertIntoRulesComboBox(rulesSingleEmpComboBox);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// query to get hourly employees data from database
	protected void getHourlyEmployeeData() throws ClassNotFoundException, SQLException {

		// hourly employee with 2 phone numbers
		String query1 = "Select U1.user_id, U1.user_firstName, U1.user_lastName, U1.user_gender, U1.user_birthDate, U1.user_finantialPalance, P1.phone_number, P2.phone_number, L.user_password, E.employment_date, HE.workingHours, HE.salaryPerHour  \r\n"
				+ "FROM hourly_employee HE, employee E, users U1, phone P1, users U2, phone P2, login L\r\n"
				+ "WHERE U1.user_id = U2.user_id \r\n" + "      AND U1.user_id = HE.user_id \r\n"
				+ "      AND U1.user_id  = E.user_id \r\n" + "      AND U1.user_id = P1.user_id\r\n"
				+ "      AND U1.user_id = P2.user_id\r\n" + "      AND U1.user_id = L.user_id\r\n"
				+ "      AND P1.phone_number > P2.phone_number;";

		// rules information query
		String rulesInfo = "";

		// execute query
		Execute.setResultSELECTQuery(query1);

		// insert from database into
		while (Execute.resultSet.next()) {

			// rules ArrayList
			ArrayList<Rule> rules = new ArrayList<Rule>();

			// employee id
			String empId = Execute.resultSet.getString(1);

			rulesInfo = "SELECT rule_name, rule_description\r\n" + "FROM employee_to_rules\r\n"
					+ "WHERE employee_id = \"" + empId + "\";";

			// temporary connection
			Connection tempconnection = this.connectToDatabase.connect();
			Statement tempStatement = tempconnection.createStatement();
			ResultSet tempResult = tempStatement.executeQuery(rulesInfo);

			// add author names into ArrayList
			while (tempResult.next()) {
				rules.add(new Rule(tempResult.getString(1), tempResult.getString(2)));
			} // end while

			// close temporary connections
			tempconnection.close();
			tempStatement.close();
			tempResult.close();

			// insert in hourly employee ObservableList
			hourlyEmployeeData.add(new HourlyEmployee(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), (Execute.resultSet.getString(6)), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), Execute.resultSet.getString(9), Execute.resultSet.getString(10),
					(Execute.resultSet.getString(11)), Execute.resultSet.getString(12), rules));

		}

		// close connection
		Execute.close();

		// hourly employee with 1 phone number
		String query2 = "Select U1.user_id, U1.user_firstName, U1.user_lastName, U1.user_gender, U1.user_birthDate, U1.user_finantialPalance, P1.phone_number, L.user_password, E.employment_date, HE.workingHours, HE.salaryPerHour  \r\n"
				+ "FROM hourly_employee HE, employee E, users U1, phone P1, login L\r\n"
				+ "WHERE U1.user_id = HE.user_id \r\n" + "      AND U1.user_id  = E.user_id \r\n"
				+ "      AND U1.user_id = P1.user_id\r\n" + "      AND U1.user_id = L.user_id\r\n"
				+ "      AND U1.user_id NOT IN (\r\n" + "           \r\n" + "           Select U1.user_id  \r\n"
				+ "           FROM hourly_employee HE, employee E, users U1, phone P1, users U2, phone P2, login L\r\n"
				+ "           WHERE U1.user_id = U2.user_id \r\n" + "				AND U1.user_id = HE.user_id \r\n"
				+ "                AND U1.user_id  = E.user_id \r\n" + "                AND U1.user_id = P1.user_id\r\n"
				+ "                AND U1.user_id = P2.user_id\r\n" + "                AND U1.user_id = L.user_id\r\n"
				+ "                AND P1.phone_number > P2.phone_number\r\n" + "      );";

		// execute query
		Execute.setResultSELECTQuery(query2);

		// insert from database into
		while (Execute.resultSet.next()) {

			// rules ArrayList
			ArrayList<Rule> rules = new ArrayList<Rule>();

			// employee id
			String empId = Execute.resultSet.getString(1);

			rulesInfo = "SELECT rule_name, rule_description\r\n" + "FROM employee_to_rules\r\n"
					+ "WHERE employee_id = \"" + empId + "\";";

			// temporary connection
			Connection tempconnection = this.connectToDatabase.connect();
			Statement tempStatement = tempconnection.createStatement();
			ResultSet tempResult = tempStatement.executeQuery(rulesInfo);

			// add author names into ArrayList
			while (tempResult.next()) {
				rules.add(new Rule(tempResult.getString(1), tempResult.getString(2)));
			} // end while

			// close temporary connections
			tempconnection.close();
			tempStatement.close();
			tempResult.close();

			// insert in hourly employee ObservableList
			hourlyEmployeeData.add(new HourlyEmployee(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), (Execute.resultSet.getString(6)), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), Execute.resultSet.getString(9), (Execute.resultSet.getString(10)),
					Execute.resultSet.getString(11), rules));

		}

		// close connection
		Execute.close();

	}

	// add data on table
	protected void addDataOnHourlyEmployeeTable() {

		hourlyEmployeeId.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("id"));
		hourlyEmployeeFirstName.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("firstName"));
		hourlyEmployeeLastName.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("lastName"));
		hourlyEmployeeGender.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, Character>("gender"));
		hourlyEmployeeBirthDate.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("birthDate"));
		hourlyEmployeeFinantialPalance
				.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("finantialPalance"));
		hourlyEmployeeFirstPhone.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("phone1"));
		hourlyEmployeeSecondPhone.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("phone2"));
		hourlyEmployeePassword.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("password"));
		hourlyEmployeeWorkingHour.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("workingHour"));
		hourlyEmployeeSalaryPerHour
				.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("salaryPerHour"));
		hourlyEmployeeEmploymentDate
				.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("employmentDate"));
		hourlyEmployeeSalary.setCellValueFactory(new PropertyValueFactory<HourlyEmployee, String>("salary"));

		hourlyEmployeeTable.setItems(hourlyEmployeeData);

	}
	// ----------------------------------------------------------------------------------//
	// monthly employee

	protected static final ObservableList<MonthlyEmployee> monthlyEmployeeData = FXCollections.observableArrayList();

	// insert all information from the database into monthlyEmployee table
	protected void insertMonthlyEmployeeDataIntoDataBase() {

		try {
			this.getMonthlyEmployeeData();
			this.addDataOnMonthlyEmployeeTable();
			this.insertIntoRulesComboBox(monthlyEmployeeRulesComboBox);
			this.insertIntoRulesComboBox(rulesSingleEmpComboBox);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// query to get monthly employees data from database
	protected void getMonthlyEmployeeData() throws ClassNotFoundException, SQLException {

		// monthly employee with 2 phone numbers
		String query1 = "Select U1.user_id, U1.user_firstName, U1.user_lastName, U1.user_gender, U1.user_birthDate, U1.user_finantialPalance, P1.phone_number, P2.phone_number, L.user_password, E.salary, E.employment_date, ME.vaction_days, ME.allowd_vaction_days, D.deduction  \r\n"
				+ "FROM monthly_employee ME, employee E, users U1, phone P1, users U2, phone P2, login L, deduction D\r\n"
				+ "WHERE U1.user_id = U2.user_id \r\n" + "      AND U1.user_id = ME.user_id \r\n"
				+ "      AND U1.user_id  = E.user_id \r\n" + "      AND U1.user_id = P1.user_id\r\n"
				+ "      AND U1.user_id = P2.user_id\r\n" + "      AND U1.user_id = L.user_id\r\n"
				+ "      AND D.vaction_days = ME.vaction_days\r\n"
				+ "      AND D.allowd_vaction_days = ME.allowd_vaction_days\r\n"
				+ "      AND P1.phone_number > P2.phone_number;  ";

		// rules information query
		String rulesInfo = "";

		// execute query
		Execute.setResultSELECTQuery(query1);

		// insert from database into
		while (Execute.resultSet.next()) {

			// rules ArrayList
			ArrayList<Rule> rules = new ArrayList<Rule>();

			// employee id
			String empId = Execute.resultSet.getString(1);

			rulesInfo = "SELECT rule_name, rule_description\r\n" + "FROM employee_to_rules\r\n"
					+ "WHERE employee_id = \"" + empId + "\";";

			// temporary connection
			Connection tempconnection = this.connectToDatabase.connect();
			Statement tempStatement = tempconnection.createStatement();
			ResultSet tempResult = tempStatement.executeQuery(rulesInfo);

			// add author names into ArrayList
			while (tempResult.next()) {
				rules.add(new Rule(tempResult.getString(1), tempResult.getString(2)));
			} // end while

			// close temporary connections
			tempconnection.close();
			tempStatement.close();
			tempResult.close();

			// insert in hourly employee ObservableList
			monthlyEmployeeData.add(new MonthlyEmployee(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), (Execute.resultSet.getString(6)), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), Execute.resultSet.getString(9), (Execute.resultSet.getString(10)),
					Execute.resultSet.getString(11), (Execute.resultSet.getString(12)),
					(Execute.resultSet.getString(13)), Execute.resultSet.getString(14), rules));

		}

		// close connection
		Execute.close();

		// monthly employee with 1 phone number
		String query2 = "Select U1.user_id, U1.user_firstName, U1.user_lastName, U1.user_gender, U1.user_birthDate, U1.user_finantialPalance, P1.phone_number, L.user_password, E.salary, E.employment_date, ME.vaction_days, ME.allowd_vaction_days, D.deduction \r\n"
				+ "FROM monthly_employee ME, employee E, users U1, phone P1, login L, deduction D\r\n"
				+ "WHERE U1.user_id = ME.user_id \r\n" + "      AND U1.user_id  = E.user_id \r\n"
				+ "      AND U1.user_id = P1.user_id\r\n" + "      AND U1.user_id = L.user_id\r\n"
				+ "      AND D.vaction_days = ME.vaction_days\r\n"
				+ "	  AND D.allowd_vaction_days = ME.allowd_vaction_days\r\n" + "      AND U1.user_id NOT IN (\r\n"
				+ "           \r\n" + "          Select U1.user_id  \r\n"
				+ "		  FROM monthly_employee ME, employee E, users U1, phone P1, users U2, phone P2, login L, deduction D\r\n"
				+ "          WHERE U1.user_id = U2.user_id \r\n" + "                 AND U1.user_id = ME.user_id \r\n"
				+ "                 AND U1.user_id  = E.user_id \r\n"
				+ "                 AND U1.user_id = P1.user_id\r\n"
				+ "                 AND U1.user_id = P2.user_id\r\n" + "                 AND U1.user_id = L.user_id\r\n"
				+ "                 AND D.vaction_days = ME.vaction_days\r\n"
				+ "                 AND D.allowd_vaction_days = ME.allowd_vaction_days\r\n"
				+ "                 AND P1.phone_number > P2.phone_number\r\n" + "      ); ";

		// execute query
		Execute.setResultSELECTQuery(query2);

		// insert from database into
		while (Execute.resultSet.next()) {

			// rules ArrayList
			ArrayList<Rule> rules = new ArrayList<Rule>();

			// employee id
			String empId = Execute.resultSet.getString(1);

			rulesInfo = "SELECT rule_name, rule_description\r\n" + "FROM employee_to_rules\r\n"
					+ "WHERE employee_id = \"" + empId + "\";";

			// temporary connection
			Connection tempconnection = this.connectToDatabase.connect();
			Statement tempStatement = tempconnection.createStatement();
			ResultSet tempResult = tempStatement.executeQuery(rulesInfo);

			// add author names into ArrayList
			while (tempResult.next()) {
				rules.add(new Rule(tempResult.getString(1), tempResult.getString(2)));
			} // end while

			// close temporary connections
			tempconnection.close();
			tempStatement.close();
			tempResult.close();

			// insert in hourly employee ObservableList
			monthlyEmployeeData.add(new MonthlyEmployee(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), (Execute.resultSet.getString(6)), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), (Execute.resultSet.getString(9)), Execute.resultSet.getString(10),
					(Execute.resultSet.getString(11)), (Execute.resultSet.getString(12)),
					Execute.resultSet.getString(13), rules));

		}

		// close connection
		Execute.close();

	}

	// add data on table
	protected void addDataOnMonthlyEmployeeTable() {

		monthlyEmployeeId.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("id"));
		monthlyEmployeeFirstName.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("firstName"));
		monthlyEmployeeLastName.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("lastName"));
		monthlyEmployeeGender.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, Character>("gender"));
		monthlyEmployeeBirthDate.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("birthDate"));
		monthlyEmployeeFinantialPalance
				.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("finantialPalance"));
		monthlyEmployeeFirstPhone.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("phone1"));
		monthlyEmployeeSecondPhone.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("phone2"));
		monthlyEmployeePassword.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("password"));
		monthlyEmployeeAllowedVactionDay
				.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("allowedVacationDays"));
		monthlyEmployeeVactionDay
				.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("vacationDays"));
		monthlyEmployeeDeduction.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("deduction"));
		monthlyEmployeeEmploymentDate
				.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("employmentDate"));
		monthlyEmployeeSalary.setCellValueFactory(new PropertyValueFactory<MonthlyEmployee, String>("salary"));

		monthlyEmployeeTable.setItems(monthlyEmployeeData);

	}

	// --------------------------------------------------------------------------------//
	// rules

	// rules arrayList
	protected static final ArrayList<Rule> rulesList = new ArrayList<Rule>();

	// insert into rules ListView from ArrayList
	protected void insertIntoRulesComboBox(ComboBox<String> rules) throws ClassNotFoundException, SQLException {

		// insert rules from database into ArrayList
		this.insertRulesIntoArrayList();

		// clear ComboBox
		rules.getItems().clear();

		// ArrayList size
		int size = rulesList.size();

		// insert from ArrayList into ComboBox
		for (int i = 0; i < size; i++) {
			rules.getItems().add(rulesList.get(i).toString());
		} // end for
	} // end insertIntoRulesComboBox method

	// insert rule from database into arrayList and comboBox
	protected void insertRulesIntoArrayList() throws ClassNotFoundException, SQLException {

		// clear the ArrayList
		rulesList.clear();

		// select from rules table query
		String query = "SELECT * FROM rules\r\n" + "WHERE rule_name <> \"admin\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// insert the result into ArrayList
		while (Execute.resultSet.next()) {
			rulesList.add(new Rule(Execute.resultSet.getString(1), Execute.resultSet.getString(2)));
		} // end while

		// close
		Execute.close();

	} // end insertRulesIntoArrayList() method
}
