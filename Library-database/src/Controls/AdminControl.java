package Controls;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import ConnectionsToMYSQL.ConnectToDatabase;
import ConnectionsToMYSQL.ConnectionsText;
import ConnectionsToMYSQL.Execute;
import EditCommit.HourlyEmployeeCharEditCommit;
import EditCommit.HourlyEmployeeStringEditCommit;
import EditCommit.MemberCharEditCommit;
import EditCommit.MemberStringEditComit;
import EditCommit.MonthlyEmployeeCharEditCommit;
import EditCommit.MonthlyEmployeeStringEditCommit;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import library.ChickMethods;
import library.Employee;
import library.HourlyEmployee;
import library.Member;
import library.MonthlyEmployee;
import library.NewUser;
import library.Rule;

public class AdminControl extends RefreshAdminTables implements EventHandler<ActionEvent> {

	public AdminControl() throws ClassNotFoundException, SQLException {
		super();
	}

	private final String STYLE = "style.css"; // style

	private String adminId; // administrator id

	private Stage controlStage; // stage
	private Scene controlScene; // scene

	/*--------------------------------*/
	// the login control
	private LoginControl loginControl;

	// Constructor
	public AdminControl(Stage controlStage, LoginControl loginControl) throws ClassNotFoundException, SQLException {
		super();
		this.setReferences(controlStage, loginControl);
		this.setEvents();
	}

	// -------------------------------------------------------------------------//
	// set references for controlStage, loginControl and controlScene
	private void setReferences(Stage controlStage, LoginControl loginControl) {
		// loginControl reference
		this.loginControl = loginControl;

		// controlStage reference
		this.controlStage = controlStage;

		// controlScence reference
		this.controlScene = new Scene(this);

		// Scene style.css file connection
		this.controlScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());

		controlStage.setTitle("admin page");

		// set members table
		super.addTable(MEMBER);
		
		// set table pane
		this.addPane(TABLE);

		// ----------------------------------------------------------//
		// insert initial data

		// insert data form database into member TableView
		super.insertMemberDataIntoDataBase();

		// insert data form database into hourlyEmployee TableView
		super.insertHourlyEmployeeDataIntoDataBase();

		// insert data form database into hourlyEmployee TableView
		super.insertMonthlyEmployeeDataIntoDataBase();

		// ----------------------------------------------------------//
		// add buttons columns

		this.addButtonToTable(MEMBER);
		this.addButtonToTable(HOURLYEMPLOYEE);
		this.addButtonToTable(MONTHLYEMPLOYEE);

		// updates member
		try {
			this.updateMember();
		} catch (ClassNotFoundException | SQLException | NumberFormatException e) {
			e.printStackTrace();
		} finally {
			// refresh all tables
			super.refreshAllTables();
		}

		// update hourly employee
		try {
			this.updateHourlyEmployee();
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// refresh all tables
			super.refreshAllTables();
		}

		// update monthly employee
		try {
			this.updateMonthlyEmployee();
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} finally {
			// refresh all tables
			super.refreshAllTables();
		}

	}

	// -------------------------------------------------------------------------//
	// reset
	protected void reset() {

		// clear all information
		super.clear();

		// set members table
		super.addTable(MEMBER);

		// set table pane
		this.addPane(TABLE);

		// reset bookControl pane information
		bookControl.refresh();

		// reset the comboBox selection
		if (comboBox.getSelectionModel() != null)
			comboBox.getSelectionModel().select(3);
	}

	// -------------------------------------------------------------------------//
	// set this controlScene in the stage
	public void setScene() {

		this.reset();
		
		// set scene
		this.controlStage.setScene(this.controlScene);

		// change stage title
		this.controlStage.setTitle("Admin layout");

		// the size of stage
		this.controlStage.setHeight(HEIGHT);
		this.controlStage.setWidth(WIDTH);

		this.controlStage.setResizable(true);

	}

	// -------------------------------------------------------------------------//
	// set administrator id
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	// -------------------------------------------------------------------------//
	// set all action events
	private void setEvents() {

		// upload
		uploadHourlyEmployeeTable.setOnAction(this);
		uploadMemberTable.setOnAction(this);
		uploadMonthlyEmployeeTable.setOnAction(this);

		// insert
		hourlyEmployeeInsert.setOnAction(this);
		monthlyEmployeeInsert.setOnAction(this);
		memberInsert.setOnAction(this);

		// delete
		memberDelete.setOnAction(this);
		hourlyEmployeeDelete.setOnAction(this);
		monthlyEmployeeDelete.setOnAction(this);

		// refresh
		memberRefresh.setOnAction(this);
		hourlyEmployeeRefresh.setOnAction(this);
		monthlyEmployeeRefresh.setOnAction(this);

		// comboBox
		comboBox.setOnAction(this);

		// radioButtons
		hourlyEmpRadioButton.setOnAction(this);
		monthlyEmpRadioButton.setOnAction(this);

		// new employee buttons
		newEmpAccept.setOnAction(this);
		newEmpReject.setOnAction(this);
		selectNewEmpButton.setOnAction(this);

		// new member buttons
		newMemberAccept.setOnAction(this);
		newMemberReject.setOnAction(this);
		selectNewMemberButton.setOnAction(this);

		// monthly employee rules information
		monthlyEmployeeTable.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {

				// selected monthlyEmployee
				MonthlyEmployee selected = monthlyEmployeeTable.getSelectionModel().getSelectedItem();

				if (selected != null) { // if there is an item selected

					rulesListView.getItems().clear();
					super.refreshRulesListView(selected.getRules());

				}
			}
		});

		// hourly employee rules information
		hourlyEmployeeTable.setOnMouseClicked(e -> {
			if (e.getClickCount() == 1) {

				// selected monthlyEmployee
				HourlyEmployee selected = hourlyEmployeeTable.getSelectionModel().getSelectedItem();

				if (selected != null) { // if there is an item selected

					rulesListView.getItems().clear();
					super.refreshRulesListView(selected.getRules());
				}
			}
		});

		// rules for single employee
		addRuleForSingleEmp.setOnAction(this);
		removeRuleForSingleEmp.setOnAction(this);

	}

	// -----------------------------------------------------------------------------------------//
	// Database Connections
	private ConnectToDatabase connectToDatabase = new ConnectToDatabase(ConnectionsText.URL, ConnectionsText.port,
			ConnectionsText.dbName, ConnectionsText.dbUsername, ConnectionsText.dbPassword);

	// ---------------------------------------------------------------------------------------------//

	// -------------------------------------------------------------------------//
	// overriding handle method
	@Override
	public void handle(ActionEvent event) {

		try {

			if (event.getSource().equals(uploadHourlyEmployeeTable)) { // upload employee
				super.clear();
				super.clearSelectedOnTable();
				super.addTable(HOURLYEMPLOYEE);
			} else if (event.getSource().equals(uploadMonthlyEmployeeTable)) { // upload member
				super.clear();
				super.clearSelectedOnTable();
				super.addTable(MONTHLYEMPLOYEE);
			} else if (event.getSource().equals(uploadMemberTable)) { // upload member
				super.clear();
				super.clearSelectedOnTable();
				super.addTable(MEMBER);
			} else if (event.getSource().equals(memberInsert)) { // member insert
				this.memberInsert();
			} else if (event.getSource().equals(hourlyEmployeeInsert)) { // hourly employee insert
				this.hourlyEmployeeInsert();
			} else if (event.getSource().equals(monthlyEmployeeInsert)) { // monthly employee insert
				this.monthlyEmployeeInsert();
			} else if (event.getSource().equals(memberDelete)) { // member delete
				this.memberDelete();
			} else if (event.getSource().equals(hourlyEmployeeDelete)) { // hourly employee delete
				this.hourlyEmployeeDelete();
			} else if (event.getSource().equals(monthlyEmployeeDelete)) { // monthly employee delete
				this.monthlyEmployeeDelete();
			} else if (event.getSource().equals(memberRefresh)) { // member refresh
				super.refreshTablesData(MEMBER);
			} else if (event.getSource().equals(hourlyEmployeeRefresh)) { // hourly employee refresh
				super.refreshTablesData(HOURLYEMPLOYEE);
			} else if (event.getSource().equals(monthlyEmployeeRefresh)) { // monthly employee refresh
				super.refreshTablesData(MONTHLYEMPLOYEE);
			} else if (event.getSource().equals(hourlyEmpRadioButton)) { // hourly employee radioButton
				newEmpSalary.setPromptText("salary/hour");
			} else if (event.getSource().equals(monthlyEmpRadioButton)) { // monthly employee radioButton
				newEmpSalary.setPromptText("salary");
			} else if (event.getSource().equals(newEmpAccept)) { // new employee accept button
				this.acceptNewEmp();
			} else if (event.getSource().equals(newEmpReject)) { // new employee reject button
				this.rejectNewEmp();
			} else if (event.getSource().equals(selectNewEmpButton)) { // select new employee
				this.selectNewEmp();
			} else if (event.getSource().equals(newMemberAccept)) { // new member accept button
				this.acceptNewMember();
			} else if (event.getSource().equals(newMemberReject)) { // new member reject button
				this.rejectNewMember();
			} else if (event.getSource().equals(selectNewMemberButton)) { // select new member 
				this.selectNewMember();
			}else if (event.getSource().equals(addRuleForSingleEmp)) { // add rule for single employee
				this.addRule();
			} else if (event.getSource().equals(removeRuleForSingleEmp)) { // remove rule for single employee
				this.removeRule();
			} else if (event.getSource().equals(comboBox)) { // comboBox
				switch (comboBox.getValue()) {
				case "Log out":
					this.loginControl.setScene();
					break;
				case "Employment applications":
					this.refreshNewEmpData();
					super.addPane(NEWMPLOYEE);
					break;
				case "library informations":
					super.refreshAllTables();// refresh
					super.addPane(TABLE);
					break;
				case "book informations":
					super.addPane(BOOK);
					break;
				case "new members":
					this.refreshNewMemberDate();
					super.addPane(NEWMEMBER);
					break;
				case "financial":
					super.addPane(FINANCIAL);
					break;
				case "account":
					super.addPane(ACCOUNT);
					break;
				}
			} // end else

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // end handle method

	// -----------------------------------------------------------------------------------------//
	// chick error

	private boolean chickError;

	// member
	private void chickMemberError() throws ClassNotFoundException, SQLException {

		String firstName = memberTextFields[0].getText().trim(); // first name
		String lastName = memberTextFields[1].getText().trim(); // last name
		int genderIndex = memberGenderComboBox.getSelectionModel().getSelectedIndex(); // gender
		String financialBalance = memberTextFields[4].getText().trim(); // financial balance
		String phone1 = memberTextFields[5].getText().trim(); // phone
		String phone2 = memberTextFields[6].getText().trim(); // phone
		String password = memberTextFields[7].getText().trim(); // password

		String birthDate = ""; // birth date

		if (memberDateOfBirthDatePicker.getValue() != null) // chick if there is chosen date
			birthDate = memberDateOfBirthDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		// clear all previous errors
		super.clearError();

		this.chickError = true;

		/* is empty */
		int memberSize = memberTextFields.length;
		for (int i = 0; i < memberSize; i++) {

			// if textField empty
			if (i != 3 && i != 2 && i != 5 && i != 6 && memberTextFields[i].getText().trim().length() == 0) {
				this.chickError = false; // change chickAddBookError to false
				memberErrorsOption[i].setText("Invalid"); // set a text on errorLabe
				super.memberPaneBakground(true, i); // change the color of background pane to red
			} // end if
		} // end for

		// gender
		if (genderIndex < 0) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[2].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 2); // change the color of background pane to red
		}

		// DateBicker value
		if (birthDate.length() == 0) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[3].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 3); // change the color of background pane to red
		}

		// phone value
		if (phone1.length() == 0 && phone2.length() == 0) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[5].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 5); // change the color of background pane to red

			memberErrorsOption[6].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 6); // change the color of background pane to red
		}

		/* Invalid information */

		// first name
		if (!ChickMethods.isNameValid(firstName)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[0].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 0); // change the color of background pane to red
		}

		// second name
		if (!ChickMethods.isNameValid(lastName)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[1].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 1); // change the color of background pane to red
		}

		// birth date
		if (birthDate.length() != 0) {
			if (!ChickMethods.isDatePickerValid(memberDateOfBirthDatePicker.getValue())) {
				this.chickError = false; // change chickAddBookError to false
				memberErrorsOption[3].setText("Invalid"); // set a text on errorLabe
				super.memberPaneBakground(true, 3); // change the color of background pane to red
			}
		}

		// financial balance
		if (!ChickMethods.isFloat(financialBalance) && (financialBalance.length() != 0)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[4].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 4); // change the color of background pane to red
		}

		// phone1

		// phone1 valid
		if ((phone1.length() != 0) && !ChickMethods.isPhoneNumberValid(phone1)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[5].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 5); // change the color of background pane to red
		}

		// phone1 exist before
		if ((phone1.length() != 0) && super.isPhoneExist(phone1)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[5].setText("phone exist"); // set a text on errorLabe
			super.memberPaneBakground(true, 5); // change the color of background pane to red
		}

		// phone2

		// phone2 valid
		if ((phone2.length() != 0) && !ChickMethods.isPhoneNumberValid(phone2)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[6].setText("Invalid"); // set a text on errorLabe
			super.memberPaneBakground(true, 6); // change the color of background pane to red
		}

		// phone1 exist before
		if ((phone2.length() != 0) && super.isPhoneExist(phone2)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[6].setText("phone exist"); // set a text on errorLabe
			super.memberPaneBakground(true, 6); // change the color of background pane to red
		}

		// phone1 == phone2
		if (phone1.equals(phone2)) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[5].setText("same phone!"); // set a text on errorLabe
			super.memberPaneBakground(true, 5); // change the color of background pane to red

			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[6].setText("same phone!"); // set a text on errorLabe
			super.memberPaneBakground(true, 6); // change the color of background pane to red

		}

		// password
		if (password.length() > 30) {
			this.chickError = false; // change chickAddBookError to false
			memberErrorsOption[7].setText("large value"); // set a text on errorLabe
			super.memberPaneBakground(true, 7); // change the color of background pane to red
		}

	}// end chickMemberError method

	// hourly employee
	private void chickHourlyEmployeeError() throws ClassNotFoundException, SQLException {

		String firstName = hourlyEmployeeTextFields[0].getText().trim(); // first name
		String lastName = hourlyEmployeeTextFields[1].getText().trim(); // last name
		int genderIndex = hourlyEmployeeGenderComboBox.getSelectionModel().getSelectedIndex();// gender
		String financialBalance = hourlyEmployeeTextFields[4].getText().trim(); // financial balance
		String phone1 = hourlyEmployeeTextFields[5].getText().trim(); // phone
		String phone2 = hourlyEmployeeTextFields[6].getText().trim(); // phone
		String password = hourlyEmployeeTextFields[7].getText().trim(); // password
		String salaryPerHour = hourlyEmployeeTextFields[8].getText().trim(); // salary/hour
		int ruleIndex = hourlyEmployeeRulesComboBox.getSelectionModel().getSelectedIndex(); // rule

		String birthDate = ""; // birth date

		if (hourlyEmployeeDateOfBirthDatePicker.getValue() != null) // chick if there is chosen date
			birthDate = hourlyEmployeeDateOfBirthDatePicker.getValue()
					.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		// clear all previous errors
		super.clearError();

		this.chickError = true;

		/* is empty */
		int employeeSize = hourlyEmployeeTextFields.length;
		for (int i = 0; i < employeeSize; i++) {

			// if textField empty
			if (i != 2 && i != 3 && i != 5 && i != 6 && hourlyEmployeeTextFields[i].getText().trim().length() == 0) {
				this.chickError = false; // change chickAddBookError to false
				hourlyEmployeeErrorsOption[i].setText("Invalid");// set a text on errorLabe
				super.hourlyEmployeePaneBakground(true, i);// change the color of background pane to red
			} // end if
		} // end for

		// gender
		if (genderIndex < 0) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[2].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 2); // change the color of background pane to red
		}

		// DateBicker value
		if (birthDate.length() == 0) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[3].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 3); // change the color of background pane to red
		}

		// phone value
		if (phone1.length() == 0 && phone2.length() == 0) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[5].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 5); // change the color of background pane to red

			hourlyEmployeeErrorsOption[6].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 6); // change the color of background pane to red
		}

		// rule
		if (ruleIndex < 0) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[9].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 9); // change the color of background pane to red
		}

		/* Invalid information */

		// first name
		if (!ChickMethods.isNameValid(firstName)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[0].setText("Invalid");
			super.hourlyEmployeePaneBakground(true, 0);// change the color of background pane to red
		}

		// second name
		if (!ChickMethods.isNameValid(lastName)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[1].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 1);// change the color of background pane to red
		}

		// birth date
		if (birthDate.length() != 0) {
			if (!ChickMethods.isDatePickerValid(hourlyEmployeeDateOfBirthDatePicker.getValue())) {
				this.chickError = false; // change chickAddBookError to false
				hourlyEmployeeErrorsOption[3].setText("Invalid"); // set a text on errorLabe
				super.hourlyEmployeePaneBakground(true, 3); // change the color of background pane to red
			}
		}

		// financial balance
		if (!ChickMethods.isFloat(financialBalance) && (financialBalance.length() != 0)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[4].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 4);// change the color of background pane to red
		}

		// phone1

		// phone1 valid
		if ((phone1.length() != 0) && !ChickMethods.isPhoneNumberValid(phone1)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[5].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 5);// change the color of background pane to red
		}

		// phone1 exist before
		if ((phone1.length() != 0) && super.isPhoneExist(phone1)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[5].setText("phone exist"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 5);// change the color of background pane to red
		}

		// phone2
		if ((phone2.length() != 0) && !ChickMethods.isPhoneNumberValid(phone2)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[6].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 6);// change the color of background pane to red
		}

		// phone2 exist before
		if ((phone2.length() != 0) && super.isPhoneExist(phone2)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[6].setText("phone exist"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 6);// change the color of background pane to red
		}

		// password
		if (password.length() > 30) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[7].setText("large value"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 7);// change the color of background pane to red
		}

		// salary/hour
		if ((salaryPerHour.length() != 0) && !ChickMethods.isFloat(salaryPerHour)) {
			this.chickError = false; // change chickAddBookError to false
			hourlyEmployeeErrorsOption[8].setText("Invalid"); // set a text on errorLabe
			super.hourlyEmployeePaneBakground(true, 8);// change the color of background pane to red
		}

	}// end chickHourlyEmployeeError method

	// hourly employee
	private void chickMonthlyEmployeeError() throws ClassNotFoundException, SQLException {

		String firstName = monthlyEmployeeTextFields[0].getText().trim(); // first name
		String lastName = monthlyEmployeeTextFields[1].getText().trim(); // last name
		int genderIndex = monthlyEmployeeGenderComboBox.getSelectionModel().getSelectedIndex(); // gender
		String financialBalance = monthlyEmployeeTextFields[4].getText().trim(); // financial balance
		String phone1 = monthlyEmployeeTextFields[5].getText().trim(); // phone
		String phone2 = monthlyEmployeeTextFields[6].getText().trim(); // phone
		String password = monthlyEmployeeTextFields[7].getText().trim(); // password
		String allowedVacationDays = monthlyEmployeeTextFields[8].getText().trim(); // allowed vacation days
		String salary = monthlyEmployeeTextFields[9].getText().trim(); // salary
		int ruleIndex = monthlyEmployeeRulesComboBox.getSelectionModel().getSelectedIndex();

		String birthDate = ""; // birth date

		if (monthlyEmployeeDateOfBirthDatePicker.getValue() != null) // chick if there is chosen date
			birthDate = monthlyEmployeeDateOfBirthDatePicker.getValue()
					.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		// clear all previous errors
		super.clearError();

		this.chickError = true;

		/* is empty */
		int employeeSize = monthlyEmployeeTextFields.length;
		for (int i = 0; i < employeeSize; i++) {

			if (i != 2 && i != 3 && i != 5 && i != 6 && monthlyEmployeeTextFields[i].getText().trim().length() == 0) { // if
				// textField
				// empty
				this.chickError = false; // change chickAddBookError to false
				monthlyEmployeeErrorsOption[i].setText("Invalid"); // set a text on errorLabe
				super.monthlyEmployeePaneBakground(true, i); // change the color of background pane to red
			} // end if
		} // end for

		// gender
		if (genderIndex < 0) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[2].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 2); // change the color of background pane to red
		}

		// DateBicker value
		if (birthDate.length() == 0) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[3].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 3); // change the color of background pane to red
		}

		// phone value
		if (phone1.length() == 0 && phone2.length() == 0) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[5].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 5); // change the color of background pane to red

			monthlyEmployeeErrorsOption[6].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 6); // change the color of background pane to red
		}

		// rule
		if (ruleIndex < 0) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[10].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 10); // change the color of background pane to red
		}

		/* Invalid information */

		// first name
		if (!ChickMethods.isNameValid(firstName)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[0].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 0); // change the color of background pane to red
		}

		// second name
		if (!ChickMethods.isNameValid(lastName)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[1].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 1); // change the color of background pane to red
		}

		// birth date
		if (birthDate.length() != 0) {
			if (!ChickMethods.isDatePickerValid(monthlyEmployeeDateOfBirthDatePicker.getValue())) {
				this.chickError = false; // change chickAddBookError to false
				monthlyEmployeeErrorsOption[3].setText("Invalid"); // set a text on errorLabe
				super.monthlyEmployeePaneBakground(true, 3); // change the color of background pane to red
			}
		}

		// financial balance
		if (!ChickMethods.isFloat(financialBalance) && (financialBalance.length() != 0)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[4].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 4); // change the color of background pane to red
		}

		// phone1

		// phone1 valid
		if ((phone1.length() != 0) && !ChickMethods.isPhoneNumberValid(phone1)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[5].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 5); // change the color of background pane to red
		}

		// phone1 exist before
		if ((phone1.length() != 0) && super.isPhoneExist(phone1)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[5].setText("phone exist"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 5); // change the color of background pane to red
		}

		// phone2

		// phone2 valid
		if ((phone2.length() != 0) && !ChickMethods.isPhoneNumberValid(phone2)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[6].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 6); // change the color of background pane to red
		}

		// phone2 exist before
		if ((phone2.length() != 0) && super.isPhoneExist(phone2)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[6].setText("phone exist"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 6); // change the color of background pane to red
		}

		// password
		if (password.length() > 30) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[7].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 7); // change the color of background pane to red
		}

		// salary
		if ((allowedVacationDays.length() != 0) && !ChickMethods.isDigit(allowedVacationDays)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[8].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 8); // change the color of background pane to red
		}

		// salary
		if ((salary.length() != 0) && !ChickMethods.isFloat(salary)) {
			this.chickError = false; // change chickAddBookError to false
			monthlyEmployeeErrorsOption[9].setText("Invalid"); // set a text on errorLabe
			super.monthlyEmployeePaneBakground(true, 9); // change the color of background pane to red
		}

	}// end chickHourlyEmployeeError method

	// add rule for single employee
	private void chickAddRuleForSingleEmployeeError() throws ClassNotFoundException, SQLException {

		super.clearError(); // clear errors
		chickError = true; // set boolean to true

		// selected rule index from ComboBox
		int ruleIndex = rulesSingleEmpComboBox.getSelectionModel().getSelectedIndex();

		// selected employee
		Employee emp = this.getSelectedEmpFromTableView();

		if (emp != null) { // if there is an employee chosen from the table view
			if (ruleIndex >= 0) { // if there is a rule chosen from the ComboBox
				if (emp.isRuleExist(rulesList.get(ruleIndex))) { // if the rule already exist to this employee
					chickError = false; // set boolean to false
					rulesOptionForSingleEmpErrorLabel.setText("rule already exist"); // set a text in the error Label
				} // end if
			} else { // if there is no rule chosen from the ComboBox
				chickError = false; // set boolean to false
				rulesOptionForSingleEmpErrorLabel.setText("choose rule"); // set a text in the error Label
			} // end else
		} else { // if there is no employee chosen from the table view
			chickError = false; // set boolean to false
			rulesOptionForSingleEmpErrorLabel.setText("choose employee"); // set a text in the error Label
		} // end else

	}

	// remove rule for single employee
	private void chickRemoveRuleForSingleEmployeeError() {

		super.clearError(); // clear errors
		chickError = true; // set boolean to true
		Employee emp = this.getSelectedEmpFromTableView();// selected employee
		int ruleIndex = rulesListView.getSelectionModel().getSelectedIndex(); // selected rule index

		if (emp != null) {// if there is an employee chosen from the table view
			if (ruleIndex >= 0) { // if there is a rule selected to delete
				if (emp.getRules().size() == 1) {// if there is just one rule for this employee
					chickError = false; // set boolean to false
					// set a text in the error Label
					rulesOptionForSingleEmpErrorLabel.setText("employee connot remain without rule");
				} // end if
			} else { // if there is no rule selected to delete
				chickError = false; // set boolean to false
				rulesOptionForSingleEmpErrorLabel.setText("select rule to remove"); // set a text in the error Label
			} // end else
		} else {// if there is no employee chosen from the table view
			chickError = false; // set boolean to false
			rulesOptionForSingleEmpErrorLabel.setText("choose employee"); // set a text in the error Label
		} // end else

	}

	private Employee getSelectedEmpFromTableView() {

		if (adminShownTable == HOURLYEMPLOYEE) { // if the shown table is for hourly employee
			int index = hourlyEmployeeTable.getSelectionModel().getSelectedIndex(); // selected index
			if (index < 0) // if there is no employee selected
				return null;
			return hourlyEmployeeTable.getSelectionModel().getSelectedItem();
		} else { // if the shown table is for monthly employee
			int index = monthlyEmployeeTable.getSelectionModel().getSelectedIndex(); // selected index
			if (index < 0) // if there is no employee selected
				return null;
			return monthlyEmployeeTable.getSelectionModel().getSelectedItem();
		}
	} // end getSelectedEmpFromTableView

	// ----------------------------------------------------------------------------------//
	// add Button columns into table

	// temporary Stage
	private Stage tempStage;

	// temporary Scene
	private Scene tempScene;

	private void addButtonToTable(int type) {

		tempStage = new Stage();

		if (type == MEMBER) { // member table

			TableColumn<Member, Void> colBtn = new TableColumn<Member, Void>("Button for books");

			Callback<TableColumn<Member, Void>, TableCell<Member, Void>> cellFactory = new Callback<TableColumn<Member, Void>, TableCell<Member, Void>>() {
				@Override
				public TableCell<Member, Void> call(final TableColumn<Member, Void> param) {
					final TableCell<Member, Void> cell = new TableCell<Member, Void>() {

						private final Button btn = new Button("see books");

						{
							// button style
							btn.setId("AdminChooseButton");

							// set button action
							btn.setOnAction((ActionEvent event) -> {
								Member member = getTableView().getItems().get(getIndex());

								// BookControl
								BookControl bookControl = new BookControl(member.getId(), adminId);

								// Scene
								tempScene = new Scene(bookControl);

								// connect to style.css
								tempScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());

								// Stage
								tempStage.setTitle(member.getFirstName() + " " + member.getLastName());
								tempStage.setScene(tempScene);
								tempStage.show();

							});
						}

						@Override
						public void updateItem(Void item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
							} else {
								setGraphic(btn);
							}
						}
					};
					return cell;
				}
			};

			colBtn.setCellFactory(cellFactory);

			// add button in table
			memberTable.getColumns().add(colBtn);

		} else if (type == HOURLYEMPLOYEE) { // hourlyEmployee table

			TableColumn<HourlyEmployee, Void> colBtn = new TableColumn<HourlyEmployee, Void>("Button for books");

			Callback<TableColumn<HourlyEmployee, Void>, TableCell<HourlyEmployee, Void>> cellFactory = new Callback<TableColumn<HourlyEmployee, Void>, TableCell<HourlyEmployee, Void>>() {
				@Override
				public TableCell<HourlyEmployee, Void> call(final TableColumn<HourlyEmployee, Void> param) {
					final TableCell<HourlyEmployee, Void> cell = new TableCell<HourlyEmployee, Void>() {

						private final Button btn = new Button("see books");

						{
							// button style
							btn.setId("AdminChooseButton");

							// set button action
							btn.setOnAction((ActionEvent event) -> {
								HourlyEmployee hourlyEmployee = getTableView().getItems().get(getIndex());

								// BookControl
								BookControl bookControl = new BookControl(hourlyEmployee.getId(), adminId);

								// Scene
								tempScene = new Scene(bookControl);

								// connect to style.css
								tempScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());

								// Stage
								tempStage.setTitle(hourlyEmployee.getFirstName() + " " + hourlyEmployee.getLastName());
								tempStage.setScene(tempScene);
								tempStage.show();
							});
						}

						@Override
						public void updateItem(Void item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
							} else {
								setGraphic(btn);
							}
						}
					};
					return cell;
				}
			};

			colBtn.setCellFactory(cellFactory);

			// add button in table
			hourlyEmployeeTable.getColumns().add(colBtn);

		} else if (type == MONTHLYEMPLOYEE) { // monthlyEmployee table

			TableColumn<MonthlyEmployee, Void> colBtn = new TableColumn<MonthlyEmployee, Void>("Button for books");

			Callback<TableColumn<MonthlyEmployee, Void>, TableCell<MonthlyEmployee, Void>> cellFactory = new Callback<TableColumn<MonthlyEmployee, Void>, TableCell<MonthlyEmployee, Void>>() {
				@Override
				public TableCell<MonthlyEmployee, Void> call(final TableColumn<MonthlyEmployee, Void> param) {
					final TableCell<MonthlyEmployee, Void> cell = new TableCell<MonthlyEmployee, Void>() {

						private final Button btn = new Button("see books");

						{
							// button style
							btn.setId("AdminChooseButton");

							// set button action
							btn.setOnAction((ActionEvent event) -> {
								MonthlyEmployee monthlyEmployee = getTableView().getItems().get(getIndex());

								// BookControl
								BookControl bookControl = new BookControl(monthlyEmployee.getId(), adminId);

								// Scene
								tempScene = new Scene(bookControl);

								// connect to style.css
								tempScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());

								// Stage
								tempStage
										.setTitle(monthlyEmployee.getFirstName() + " " + monthlyEmployee.getLastName());
								tempStage.setScene(tempScene);
								tempStage.show();
							});
						}

						@Override
						public void updateItem(Void item, boolean empty) {
							super.updateItem(item, empty);
							if (empty) {
								setGraphic(null);
							} else {
								setGraphic(btn);
							}
						}
					};
					return cell;
				}
			};

			colBtn.setCellFactory(cellFactory);

			// add button in table
			monthlyEmployeeTable.getColumns().add(colBtn);
		}
	}// end addButtonToTable method

	// -----------------------------------------------------------------------------------------//
	// insert

	// member
	private void memberInsert() throws ClassNotFoundException, SQLException {

		// chick if there is invalid input information
		this.chickMemberError();

		if (this.chickError) { // if there is no error

			String id = this.getNextId(MEMBER); // id
			String firstName = memberTextFields[0].getText().trim(); // first name
			String lastName = memberTextFields[1].getText().trim(); // last name
			char gender = memberGenderComboBox.getSelectionModel().getSelectedItem(); // gender
			String financialBalance = (memberTextFields[4].getText().trim()); // financial balance
			String phone1 = memberTextFields[5].getText().trim(); // phone number
			String phone2 = memberTextFields[6].getText().trim(); // phone number
			String password = memberTextFields[7].getText().trim(); // password

			String birthDate = birthDate = memberDateOfBirthDatePicker.getValue()
					.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // birth date
			String fees = "0"; // fees

			// insert information into the database
			this.insertAllInformationOfMemberInDatabase(id, firstName, lastName, gender, birthDate, financialBalance,
					fees, password, phone1, phone2, null);

			// set data into member table
			memberTable.setItems(memberData);

			// clear all
			super.clear();

		} // end if

	}// end memberInsertMethod

	// hourly employee
	private void hourlyEmployeeInsert() throws ClassNotFoundException, SQLException {

		this.chickHourlyEmployeeError();

		if (this.chickError) {

			String id = this.getNextId(HOURLYEMPLOYEE); // id
			String firstName = hourlyEmployeeTextFields[0].getText().trim(); // first name
			String lastName = hourlyEmployeeTextFields[1].getText().trim(); // last name
			char gender = hourlyEmployeeGenderComboBox.getSelectionModel().getSelectedItem(); // gender
			String financialBalance = (hourlyEmployeeTextFields[4].getText().trim()); // financial balance
			String phone1 = hourlyEmployeeTextFields[5].getText().trim(); // phone number
			String phone2 = hourlyEmployeeTextFields[6].getText().trim(); // phone number
			String password = hourlyEmployeeTextFields[7].getText().trim(); // password
			String salaryPerHour = (hourlyEmployeeTextFields[8].getText().trim()); // salary/hour
			String employmentDate = ChickMethods.getNowDate(); // today date(employmentDate)
			String workingHour = "0"; // working hours
			String salary = "0"; // salary
			Rule rule = super.rulesList.get(hourlyEmployeeRulesComboBox.getSelectionModel().getSelectedIndex());

			String birthDate = birthDate = hourlyEmployeeDateOfBirthDatePicker.getValue()
					.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // birth date

			// insert the information in the database
			this.insertAllInformationOfHourlyEmployeeInDatabase(id, firstName, lastName, gender, birthDate,
					financialBalance, employmentDate, salary, workingHour, salaryPerHour, password, phone1, phone2,
					rule);

			// set data into member table
			hourlyEmployeeTable.setItems(hourlyEmployeeData);

			// clear all
			super.clear();

		} // end if

	} // end hourlyEmployeeInsert

	// insert information into salary table
	private void insertIntoSalaryTable(double workingHour, double salaryPerHour) {

		// query
		String salaryInsertQuery = "INSERT INTO salary VALUES(" + workingHour + "," + salaryPerHour
				+ ", workingHours * salaryPerHour );";

		try { // if the information exist in the table then it will not be inserted
				// execute
			Execute.executeQuery(salaryInsertQuery);
		} catch (ClassNotFoundException | SQLException e) {
		}
	} // end insertIntoSalaryTable method

	// monthly employee
	private void monthlyEmployeeInsert() throws ClassNotFoundException, SQLException {

		this.chickMonthlyEmployeeError();

		if (this.chickError) {

			String id = this.getNextId(MONTHLYEMPLOYEE); // id
			String firstName = monthlyEmployeeTextFields[0].getText().trim(); // first name
			String lastName = monthlyEmployeeTextFields[1].getText().trim(); // last name
			char gender = monthlyEmployeeGenderComboBox.getSelectionModel().getSelectedItem(); // gender
			String financialBalance = (monthlyEmployeeTextFields[4].getText().trim()); // financial balance
			String phone1 = monthlyEmployeeTextFields[5].getText().trim(); // phone number
			String phone2 = monthlyEmployeeTextFields[6].getText().trim(); // phone number
			String password = monthlyEmployeeTextFields[7].getText().trim(); // password
			String allowedVacationDays = monthlyEmployeeTextFields[8].getText().trim(); // allowed vacation days
			String employmentDate = ChickMethods.getNowDate(); // today date(employmentDate)
			String salary = monthlyEmployeeTextFields[9].getText().trim(); // salary
			String birthDate = birthDate = monthlyEmployeeDateOfBirthDatePicker.getValue()
					.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // birth date
			String vacationDays = "0"; // vacation days
			String deduction = "0"; // deduction

			// rule
			Rule rule = rulesList.get(monthlyEmployeeRulesComboBox.getSelectionModel().getSelectedIndex());

			this.insertAllInformationOfMonthlyEmployeeInDatabase(id, firstName, lastName, gender, birthDate,
					financialBalance, employmentDate, vacationDays, allowedVacationDays, salary, password, phone1,
					phone2, deduction, rule);

			// set data into monthlyEmployee table
			monthlyEmployeeTable.setItems(monthlyEmployeeData);

			// clear all
			super.clear();

		} // end if

	}// end monthlyEmployeeInsert

	// get next id in the table
	private String getNextId(int table) throws ClassNotFoundException, SQLException {

		if (table == MEMBER) { // member table
			return this.getMemberNextId();
		} else if (table == HOURLYEMPLOYEE) { // hourly employee table
			return this.getHourlyEmployeeNextId();
		} else if (table == MONTHLYEMPLOYEE) { // monthly employee table
			return this.getMonthlyEmployeeNextId();
		} // end else statement

		return "00000000";

	} // end getNextId method

	// monthly employee next id
	private String getMonthlyEmployeeNextId() throws ClassNotFoundException, SQLException {

		// query to get max id from monthly_employee table
		String maxIdQuery = "SELECT max(user_id) FROM monthly_employee;";

		// execute
		Execute.setResultSELECTQuery(maxIdQuery);

		// get the max id from result
		Execute.resultSet.next();

		// nextId String
		String nextId = ChickMethods.incrementStringNum(Execute.resultSet.getString(1));

		// close connection
		Execute.close();

		return nextId;

	} // end getMonthlyEmployeeNextId method

	// hourly employee next id
	private String getHourlyEmployeeNextId() throws ClassNotFoundException, SQLException {

		// query to get max id from hourly_employee table
		String maxIdQuery = "SELECT max(user_id) FROM hourly_employee;";

		// execute
		Execute.setResultSELECTQuery(maxIdQuery);

		// get the max id from result
		Execute.resultSet.next();

		// nextId String
		String nextId = ChickMethods.incrementStringNum(Execute.resultSet.getString(1));

		// close connection
		Execute.close();

		return nextId;

	} // end getHourlyEmployeeNextId method

	// member next id
	private String getMemberNextId() throws ClassNotFoundException, SQLException {

		// query to get max id from member table
		String maxIdQuery = "SELECT max(user_id) FROM member;";

		// execute
		Execute.setResultSELECTQuery(maxIdQuery);

		// get the max id from result
		Execute.resultSet.next();

		// nextId String
		String nextId = ChickMethods.incrementStringNum(Execute.resultSet.getString(1));

		// close connection
		Execute.close();

		return nextId;

	} // end getMemberNextId method

	// insert into user table in the database
	private void insertIntoUserTable(String id, String firstName, String lastName, char gender, String birthDate,
			String financialBalance) throws ClassNotFoundException, SQLException {

		// query
		String userInsertQuery = "INSERT INTO users \r\n" + "VALUES(\"" + id + "\",\r\n" + "        \"" + firstName
				+ "\",\r\n" + "        \"" + lastName + "\",\r\n" + "        '" + gender + "',\r\n" + "        \""
				+ birthDate + "\",\r\n" + financialBalance + ");";

		// execute
		Execute.executeQuery(userInsertQuery);
	}

	// insert into member table in the database
	private void insertIntoMemberTable(String id, double fees) throws ClassNotFoundException, SQLException {

		// query
		String memberInsertQuery = "INSERT INTO member VALUES (\r\n" + "\"" + id + "\",\r\n" + "" + fees + ");";

		// execute
		Execute.executeQuery(memberInsertQuery);

	}

	// insert into login table in the database
	private void insertIntoLoginTable(String id, String password) throws ClassNotFoundException, SQLException {

		// query
		String loginInsertQuery = "INSERT INTO login VALUES (\"" + id + "\",\"" + password + "\");";

		// execute
		Execute.executeQuery(loginInsertQuery);
	}

	// insert into employee table in the database
	private void insertIntoEmployeeTable(String id, String employmentDate, String salary)
			throws ClassNotFoundException, SQLException {

		// query
		String employeeInsertQuery = "INSERT INTO employee VALUES (\"" + id + "\", \"" + employmentDate + "\"," + salary
				+ ");";

		// execute
		Execute.executeQuery(employeeInsertQuery);
	}

	// insert into hourly employee table in the database
	private void insertIntoHourlyEmployeeTable(String id, String workingHour, String salaryPerHour)
			throws ClassNotFoundException, SQLException {

		// query
		String hourlyEmployeeInsertQuery = "INSERT INTO hourly_employee VALUES(\"" + id + "\"," + workingHour + ","
				+ salaryPerHour + ");";

		// execute
		Execute.executeQuery(hourlyEmployeeInsertQuery);
	}

	// insert into monthly employee table in the database
	private void insertIntoMonthlyEmployeeTable(String id, int vacationDays, String allowedVacationDays)
			throws ClassNotFoundException, SQLException {

		// query
		String monthlyEmployeeInsertQuery = "INSERT INTO monthly_employee VALUES(\"" + id + "\"," + vacationDays + ","
				+ allowedVacationDays + ");";

		// execute
		Execute.executeQuery(monthlyEmployeeInsertQuery);
	}

	// insert into deduction table in the database
	private void insertIntoDeductionTable(int vacationDays, String allowedVacationDays) {

		// query
		String deductionInsertQuery = "INSERT INTO deduction VALUES(" + vacationDays + "," + allowedVacationDays
				+ ",IF(vaction_days > allowd_vaction_days, (vaction_days - allowd_vaction_days)/(30 - allowd_vaction_days)  ,  0));";

		// execute
		try { // if the deduction information are exist in the database it will not inserted
			Execute.executeQuery(deductionInsertQuery);
		} catch (ClassNotFoundException | SQLException e) {
		}
	}

	// insert into employee to rules table in the database
	private void insertIntoEmpToRuleTable(String empId, String ruleName, String ruleDescription)
			throws ClassNotFoundException, SQLException {

		// insert query
		String query = "INSERT INTO employee_to_rules VALUES (\"" + empId + "\", \"" + ruleName + "\", \""
				+ ruleDescription + "\"); ";

		// execute
		Execute.executeQuery(query);

	}

	// insert into phone table in the database
	private void insertIntoPhoneTable(String id, String firstName, String lastName, char gender, String birthDate,
			String financialBalance, String phone1, String phone2, String fees, String password, String employmentDate,
			String workingHour, String salaryPerHour, String salary, String vacationDays, String allowedVacationDays,
			String deduction, int userType, Rule rule) throws ClassNotFoundException, SQLException {

		// query
		String phone1InsertQuery = "INSERT INTO phone VALUES (\"" + phone1 + "\", \"" + id + "\");";
		String phone2InsertQuery = "INSERT INTO phone VALUES (\"" + phone2 + "\", \"" + id + "\");";

		if (phone1.length() != 0 && phone2.length() != 0) {

			// execute
			Execute.executeQuery(phone1InsertQuery); // phone1
			Execute.executeQuery(phone2InsertQuery); // phone2

			if (userType == MEMBER) {
				// add member object into memberData ObservableList
				memberData.add(new Member(id, firstName, lastName, gender, birthDate, financialBalance, fees, phone1,
						phone2, password));
			} else if (userType == HOURLYEMPLOYEE) {
				// add hourly employee object into memberData ObservableList
				hourlyEmployeeData.add(new HourlyEmployee(id, firstName, lastName, gender, birthDate, financialBalance,
						phone1, phone2, password, employmentDate, workingHour, salaryPerHour, rule));

			} else if (userType == MONTHLYEMPLOYEE) {
				// add monthly employee object into memberData ObservableList
				monthlyEmployeeData.add(new MonthlyEmployee(id, firstName, lastName, gender, birthDate,
						financialBalance, phone1, phone2, password, salary, employmentDate, vacationDays,
						allowedVacationDays, deduction, rule));

			}

		} else if (phone1.length() != 0) {

			// execute
			Execute.executeQuery(phone1InsertQuery); // phone1

			if (userType == MEMBER) {
				// add member object into memberData ObservableList
				memberData.add(new Member(id, firstName, lastName, gender, birthDate, financialBalance, fees, phone1,
						password));
			} else if (userType == HOURLYEMPLOYEE) {
				// add hourly employee object into memberData ObservableList
				hourlyEmployeeData.add(new HourlyEmployee(id, firstName, lastName, gender, birthDate, financialBalance,
						phone1, password, employmentDate, workingHour, salaryPerHour, rule));

			} else if (userType == MONTHLYEMPLOYEE) {
				// add monthly employee object into memberData ObservableList
				monthlyEmployeeData
						.add(new MonthlyEmployee(id, firstName, lastName, gender, birthDate, financialBalance, phone1,
								password, salary, employmentDate, vacationDays, allowedVacationDays, deduction, rule));

			}

		} else if (phone2.length() != 0) {

			// execute
			Execute.executeQuery(phone2InsertQuery); // phone1

			if (userType == MEMBER) {
				// add member object into memberData ObservableList
				memberData.add(new Member(id, firstName, lastName, gender, birthDate, financialBalance, fees, phone2,
						password));

			} else if (userType == HOURLYEMPLOYEE) {
				// add hourly employee object into memberData ObservableList
				hourlyEmployeeData.add(new HourlyEmployee(id, firstName, lastName, gender, birthDate, financialBalance,
						phone2, password, employmentDate, workingHour, salaryPerHour, rule));

			} else if (userType == MONTHLYEMPLOYEE) {
				// add monthly employee object into memberData ObservableList
				monthlyEmployeeData
						.add(new MonthlyEmployee(id, firstName, lastName, gender, birthDate, financialBalance, phone2,
								password, salary, employmentDate, vacationDays, allowedVacationDays, deduction, rule));

			} // end else
		} // end else

	} // end insertIntoPhoneTable method

	// insert all information of member into database
	private void insertAllInformationOfMemberInDatabase(String id, String firstName, String lastName, char gender,
			String birthDate, String financialBalance, String fees, String password, String phone1, String phone2,
			Rule rule) throws ClassNotFoundException, SQLException {

		// ---------------------------------------------------//
		// insert into users table
		this.insertIntoUserTable(id, firstName, lastName, gender, birthDate, financialBalance);

		// ---------------------------------------------------//
		// insert into member table
		this.insertIntoMemberTable(id, Double.parseDouble(fees));

		// ---------------------------------------------------//
		// insert into login table
		this.insertIntoLoginTable(id, password);

		// ---------------------------------------------------//
		// insert into phone table
		this.insertIntoPhoneTable(id, firstName, lastName, gender, birthDate, financialBalance, phone1, phone2, fees,
				password, "", "", "", "", "", "", "", MEMBER, rule);

	} // end insertAllInformationOfMemberInDatabase method

	// insert all information of hourly employee into database
	private void insertAllInformationOfHourlyEmployeeInDatabase(String id, String firstName, String lastName,
			char gender, String birthDate, String financialBalance, String employmentDate, String salary,
			String workingHour, String salaryPerHour, String password, String phone1, String phone2, Rule rule)
			throws ClassNotFoundException, SQLException {
		// ---------------------------------------------------//
		// insert into users table
		this.insertIntoUserTable(id, firstName, lastName, gender, birthDate, financialBalance);

		// ---------------------------------------------------//
		// insert into employee table
		this.insertIntoEmployeeTable(id, employmentDate, salary);

		// ---------------------------------------------------//
		// insert into salary table
		this.insertIntoSalaryTable(Double.parseDouble(workingHour), Double.parseDouble(salaryPerHour));

		// ---------------------------------------------------//
		// insert into hourly_employee table
		this.insertIntoHourlyEmployeeTable(id, workingHour, salaryPerHour);

		// ---------------------------------------------------//
		// insert into login table
		this.insertIntoLoginTable(id, password);

		// --------------------------------------------------//
		// insert into employee to rule table
		this.insertIntoEmpToRuleTable(id, rule.getName(), rule.getDescription());

		// ---------------------------------------------------//
		// insert into phone table
		this.insertIntoPhoneTable(id, firstName, lastName, gender, birthDate, financialBalance, phone1, phone2, "",
				password, employmentDate, workingHour, salaryPerHour, salaryPerHour, "", "", "", HOURLYEMPLOYEE, rule);
	}

	// insert all information of monthly employee into database
	private void insertAllInformationOfMonthlyEmployeeInDatabase(String id, String firstName, String lastName,
			char gender, String birthDate, String financialBalance, String employmentDate, String vacationDays,
			String allowedVacationDays, String salary, String password, String phone1, String phone2, String deduction,
			Rule rule) throws ClassNotFoundException, SQLException {

		// ----------------------------------------------------//
		// insert into deduction
		this.insertIntoDeductionTable(Integer.parseInt(vacationDays), allowedVacationDays);

		// ---------------------------------------------------//
		// insert into users table
		this.insertIntoUserTable(id, firstName, lastName, gender, birthDate, financialBalance);

		// ---------------------------------------------------//
		// insert into employee table
		this.insertIntoEmployeeTable(id, employmentDate, salary);

		// ---------------------------------------------------//
		// insert into monthly_employee table
		this.insertIntoMonthlyEmployeeTable(id, 0, allowedVacationDays);

		// ---------------------------------------------------//
		// insert into login table
		this.insertIntoLoginTable(id, password);

		// --------------------------------------------------//
		// insert into employee to rule table
		this.insertIntoEmpToRuleTable(id, rule.getName(), rule.getDescription());

		// ---------------------------------------------------//
		// insert into phone table
		this.insertIntoPhoneTable(id, firstName, lastName, gender, birthDate, financialBalance, phone1, phone2, "",
				password, employmentDate, "", "", salary, vacationDays, allowedVacationDays, deduction, MONTHLYEMPLOYEE,
				rule);

	}

	// -----------------------------------------------------------------------------------------//
	// delete

	// member
	private void memberDelete() throws ClassNotFoundException, SQLException {

		ObservableList<Member> selectedRows = memberTable.getSelectionModel().getSelectedItems();
		ArrayList<Member> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			memberTable.getItems().remove(row);
			try {
				deleteUserRow(row.getId());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			memberTable.refresh();
		});

	} // end delete method

	// hourlyEmployee
	private void hourlyEmployeeDelete() throws ClassNotFoundException, SQLException {

		ObservableList<HourlyEmployee> selectedRows = hourlyEmployeeTable.getSelectionModel().getSelectedItems();
		ArrayList<HourlyEmployee> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			hourlyEmployeeTable.getItems().remove(row);
			try {
				deleteUserRow(row.getId());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			hourlyEmployeeTable.refresh();
		});

	} // end delete method

	// monthlyEmployee
	private void monthlyEmployeeDelete() throws ClassNotFoundException, SQLException {

		ObservableList<MonthlyEmployee> selectedRows = monthlyEmployeeTable.getSelectionModel().getSelectedItems();
		ArrayList<MonthlyEmployee> rows = new ArrayList<>(selectedRows);
		rows.forEach(row -> {
			monthlyEmployeeTable.getItems().remove(row);
			try {
				deleteUserRow(row.getId());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			monthlyEmployeeTable.refresh();
		});

	} // end delete method

	private void deleteUserRow(String id) throws ClassNotFoundException, SQLException {

		// delete query
		String query = "DELETE FROM users\r\n" + "WHERE user_id = " + id;

		// execute query
		Execute.executeQuery(query);

	} // end deleteUserRow method

	// --------------------------------------------------------------------------------------------//
	// update

	// edit commit class references

	// member string edit commit
	private final MemberStringEditComit memberStringEditCommit = new MemberStringEditComit();

	// MemberCharEditCommit
	private final MemberCharEditCommit memberCharEditCommit = new MemberCharEditCommit();

	// hourly employee string edit commit
	private final HourlyEmployeeStringEditCommit hourlyEmployeeStringEditCommit = new HourlyEmployeeStringEditCommit();

	// hourly employee char edit commit
	private final HourlyEmployeeCharEditCommit hourlyEmployeeCharEditCommit = new HourlyEmployeeCharEditCommit();

	// monthly employee string edit commit
	private final MonthlyEmployeeStringEditCommit monthlyEmployeeStringEditCommit = new MonthlyEmployeeStringEditCommit();

	// monthly employee char edit commit
	private final MonthlyEmployeeCharEditCommit monthlyEmployeeCharEditCommit = new MonthlyEmployeeCharEditCommit();

	// member
	private void updateMember() throws ClassNotFoundException, SQLException, NumberFormatException {

		// -----------------------------------------------------------------------------//
		// first name

		// set cell factory
		super.memberFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberFirstName.setOnEditCommit(memberStringEditCommit);

		// -----------------------------------------------------------------------------//
		// last name

		// set cell factory
		super.memberLastName.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberLastName.setOnEditCommit(memberStringEditCommit);

		// -----------------------------------------------------------------------------//
		// fees

		// set cell factory
		super.memberFees.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberFees.setOnEditCommit(memberStringEditCommit);

		// -----------------------------------------------------------------------------//
		// gender

		ComboBoxTableCell cmt = new ComboBoxTableCell<Member, Character>('M', 'F');
		cmt.setId("adminUpdatedCompoBox");

		super.memberGender.setCellFactory(cmt.forTableColumn('M', 'F'));

		// set on edit commit
		super.memberGender.setOnEditCommit(memberCharEditCommit);

		// -----------------------------------------------------------------------------//
		// birth date

		// set cell factory
		super.memberBirthDate.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberBirthDate.setOnEditCommit(memberStringEditCommit);

		// -----------------------------------------------------------------------------//
		// financial balance

		// set cell factory
		super.memberFinantialPalance.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberFinantialPalance.setOnEditCommit(memberStringEditCommit);

		// -----------------------------------------------------------------------------//
		// password

		// set cell factory
		super.memberPassword.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberPassword.setOnEditCommit(memberStringEditCommit);

		// -----------------------------------------------------------------------------//
		// first phone

		// set cell factory
		super.memberFirstPhone.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberFirstPhone.setOnEditCommit(memberStringEditCommit);

		// -----------------------------------------------------------------------------//
		// second phone

		// set cell factory
		super.memberSecondPhone.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.memberSecondPhone.setOnEditCommit(memberStringEditCommit);
	}

	// hourly employee
	private void updateHourlyEmployee() throws ClassNotFoundException, SQLException, NumberFormatException {

		// ------------------------------------------------------------------//
		// employment date

		// set cell factory
		hourlyEmployeeEmploymentDate.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeEmploymentDate.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// first name

		// set cell factory
		hourlyEmployeeFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeFirstName.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// last name

		// set cell factory
		hourlyEmployeeLastName.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeLastName.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// -----------------------------------------------------------------------------//
		// gender
		ComboBoxTableCell cmt = new ComboBoxTableCell<HourlyEmployee, Character>('M', 'F');
		cmt.setId("adminUpdatedCompoBox");

		// set cell factory
		hourlyEmployeeGender.setCellFactory(cmt.forTableColumn('M', 'F'));

		// set on edit commit
		hourlyEmployeeGender.setOnEditCommit(hourlyEmployeeCharEditCommit);

		// ------------------------------------------------------------------//
		// birth date

		// set cell factory
		hourlyEmployeeBirthDate.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeBirthDate.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// financial balance

		// set cell factory
		hourlyEmployeeFinantialPalance.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeFinantialPalance.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// password

		// set cell factory
		hourlyEmployeePassword.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeePassword.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// phone1

		// set cell factory
		hourlyEmployeeFirstPhone.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeFirstPhone.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// phone2

		// set cell factory
		hourlyEmployeeSecondPhone.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeSecondPhone.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// working hour

		// set cell factory
		hourlyEmployeeWorkingHour.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeWorkingHour.setOnEditCommit(hourlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// salary/hour

		// set cell factory
		hourlyEmployeeSalaryPerHour.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		hourlyEmployeeSalaryPerHour.setOnEditCommit(hourlyEmployeeStringEditCommit);

	}

	// monthly employee
	private void updateMonthlyEmployee() throws ClassNotFoundException, SQLException, NumberFormatException {

		// ------------------------------------------------------------------//
		// employment date

		// set cell factory
		monthlyEmployeeEmploymentDate.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeEmploymentDate.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// first name

		// set cell factory
		monthlyEmployeeFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeFirstName.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// last name

		// set cell factory
		monthlyEmployeeLastName.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeLastName.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// -----------------------------------------------------------------------------//
		// gender
		ComboBoxTableCell cmt = new ComboBoxTableCell<HourlyEmployee, Character>('M', 'F');
		cmt.setId("adminUpdatedCompoBox");

		// set cell factory
		monthlyEmployeeGender.setCellFactory(cmt.forTableColumn('M', 'F'));

		// set on edit commit
		monthlyEmployeeGender.setOnEditCommit(monthlyEmployeeCharEditCommit);

		// ------------------------------------------------------------------//
		// birth date

		// set cell factory
		monthlyEmployeeBirthDate.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeBirthDate.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// financial balance

		// set cell factory
		monthlyEmployeeFinantialPalance.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeFinantialPalance.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// password

		// set cell factory
		monthlyEmployeePassword.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeePassword.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// phone1

		// set cell factory
		monthlyEmployeeFirstPhone.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeFirstPhone.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// phone2

		// set cell factory
		monthlyEmployeeSecondPhone.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeSecondPhone.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// salary

		// set cell factory
		monthlyEmployeeSalary.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeSalary.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// allowed vacation days

		// set cell factory
		monthlyEmployeeAllowedVactionDay.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeAllowedVactionDay.setOnEditCommit(monthlyEmployeeStringEditCommit);

		// ------------------------------------------------------------------//
		// allowed vacation days

		// set cell factory
		monthlyEmployeeVactionDay.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		monthlyEmployeeVactionDay.setOnEditCommit(monthlyEmployeeStringEditCommit);

	}

	// ------------------------------------------------------------//
	// rules for single employee

	// add
	private void addRule() throws ClassNotFoundException, SQLException {

		// chick error
		this.chickAddRuleForSingleEmployeeError();

		if (this.chickError) { // if there is no error

			// selected rule index from ComboBox
			int ruleIndex = rulesSingleEmpComboBox.getSelectionModel().getSelectedIndex();
			Employee emp = this.getSelectedEmpFromTableView(); // selected employee
			Rule selectedRule = rulesList.get(ruleIndex); // the selected rule from ComboBox

			// insert into employee to rule table in the database
			this.insertIntoEmpToRuleTable(emp.getId(), selectedRule.getName(), selectedRule.getDescription());

			// add rule to employee reference
			emp.addRule(selectedRule);

			// refresh ListView
			super.refreshRulesListView(emp.getRules());
		} // end if
	} // end addRule

	// remove
	private void removeRule() throws ClassNotFoundException, SQLException {

		// chick error
		this.chickRemoveRuleForSingleEmployeeError();

		if (this.chickError) { // if there is no error

			Employee emp = this.getSelectedEmpFromTableView();// selected employee
			int ruleIndex = rulesListView.getSelectionModel().getSelectedIndex(); // selected rule index
			Rule selectedRule = emp.getRules().get(ruleIndex); // the selected rule from ListView

			// remove from database
			this.removeFromEmpToRuleTable(emp.getId(), selectedRule.getName(), selectedRule.getDescription());

			emp.deleteRule(selectedRule); // delete from employee reference
			super.refreshRulesListView(emp.getRules()); // refresh ListView information

		} // end if

	} // end removeRule method

	// remove from employee to rule table in the database
	private void removeFromEmpToRuleTable(String empId, String ruleName, String ruleDescription)
			throws ClassNotFoundException, SQLException {

		// delete query
		String query = "DELETE FROM employee_to_rules\r\n" + "WHERE employee_id = \"" + empId + "\" AND\r\n"
				+ "      rule_name = \"" + ruleName + "\" AND\r\n" + "	  rule_description = \"" + ruleDescription
				+ "\";";

		// execute
		Execute.executeQuery(query);

	}

	// ---------------------------------------------------------------------------------------------------------------//
	// new member

	private static int newMemberIndex = -1;

	// new employees ArrayList
	private static final ArrayList<NewUser> newMemberList = new ArrayList<NewUser>();

	// refresh new employee data
	private void refreshNewMemberDate() throws ClassNotFoundException, SQLException {

		this.clearNewMemberDate();

		this.insertIntoNewMemberListView();
	}

	// clear new employee date
	private void clearNewMemberDate() {

		newMemberIndex = -1;

		// clear errors
		this.clearNewMemberError();

		// clear informations
		this.clearNewMemberInformation();

		// ListView and ArrayList
		newMemberListView.getItems().clear();
		newMemberList.clear();

	}

	// clear new employee informations
	private void clearNewMemberInformation() {

		newMemberIndex = -1;

		// Labels
		newMemberName.setText("");
		newMemberBirthDate.setText("");
		newMemberGender.setText("");
		newMemberPhone.setText("");

	}

	// clear errors
	private void clearNewMemberError() {

		errorSelectNewMemberLabel.setText("");

	}

	// insert into new employee ListView
	private void insertIntoNewMemberListView() throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT * FROM new_users WHERE isEmployee = FALSE;";

		// execute
		Execute.setResultSELECTQuery(query);

		while (Execute.resultSet.next()) {
			NewUser newUser = new NewUser(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), Execute.resultSet.getString(6), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), Execute.resultSet.getString(9));
			newMemberList.add(newUser);
			newMemberListView.getItems().add(newUser.getName());
		} // end while

		// close
		Execute.close();

	} // end insertIntoNewEmpListView

	// ListView event
	private void selectNewMember() {

		// clear data
		this.clearNewMemberInformation();

		newMemberIndex = newMemberListView.getSelectionModel().getSelectedIndex();

		if (newMemberIndex >= 0) { // if there is selected item

			// get selected object
			NewUser user = newMemberList.get(newMemberIndex);

			// set the data in the label
			newMemberName.setText(" Name: " + user.getName());
			newMemberBirthDate.setText(" Birth date: " + user.getBirthDate());
			newMemberGender.setText(" Gender: " + ChickMethods.getGenderString(user.getGender()));
			newMemberPhone.setText(user.getPhone());

		} else { // if there is no selected employee
			errorSelectNewMemberLabel.setText("select member");
			newMemberIndex = -1;
		} // end else

	} // end newEmpListViewEvents

	// chick error boolean
	private boolean chickNewMemberError;

	// chick new employee errors
	private void chickNewMemberError() {

		// clear error
		this.clearNewMemberError();

		this.chickNewMemberError = true;

		int memberIndex = newMemberListView.getSelectionModel().getSelectedIndex(); // member index

		/* empty information */

		// new employee listView
		if (memberIndex < 0) {
			errorSelectNewMemberLabel.setText("select member"); // set text into error label
			this.chickNewMemberError = false; // set error boolean to false
		}

	} // end chickNewEmpError() method

	// accept employee
	private void acceptNewMember() throws ClassNotFoundException, SQLException {

		// chick error
		this.chickNewMemberError();

		if (this.chickNewMemberError) {

			// new user chosen
			NewUser member = newMemberList.get(newMemberIndex);

			// delete from new_users table in the database
			this.deleteFormNewUserTable(member.getId());

			// insert into the database
			this.insertNewMemberIntoDataBase(member);

			// refresh
			this.refreshNewMemberDate();

		} // end if statement

	} // end acceptNewEmp() method

	// add employee into database
	private void insertNewMemberIntoDataBase(NewUser member) throws ClassNotFoundException, SQLException {

		this.insertAllInformationOfMemberInDatabase(this.getMemberNextId(), member.getFirstName(), member.getLastName(),
				member.getGender(), member.getBirthDate(), member.getFinancialBalance(), "30", member.getPassword(),
				member.getPhone1(), member.getPhone2(), null);

	} // end insertNewMemberIntoDataBase method

	// reject employee
	private void rejectNewMember() throws ClassNotFoundException, SQLException {

		// clear errors
		this.clearNewMemberError();

		if (newMemberIndex >= 0) {
			this.deleteFormNewUserTable(newMemberList.get(newMemberIndex).getId());
			this.refreshNewMemberDate();

		} else {
			errorSelectNewMemberLabel.setText("choose member");
			newMemberIndex = -1;
		}

	}

	// ----------------------------------------------------------------------------------------------------//
	// new employee
	private static int newEmpIndex = -1;

	// new employees ArrayList
	private static final ArrayList<NewUser> newEmpList = new ArrayList<NewUser>();

	// refresh new employee data
	private void refreshNewEmpData() throws ClassNotFoundException, SQLException {

		this.clearNewEmpDate();

		this.insertIntoNewEmpListView();
		super.insertIntoRulesComboBox(newEmpRulesComboBox);

	}

	// clear new employee date
	private void clearNewEmpDate() {

		newEmpIndex = -1;

		// clear errors
		this.clearNewEmpError();

		// clear informations
		this.clearNewEmpInformation();

		// ListView and ArrayList
		newEmployeeListView.getItems().clear();
		newEmpList.clear();

		// rules ArrayList and ComboBox
		rulesList.clear();
		newEmpRulesComboBox.getItems().clear();

	}

	// clear new employee informations
	private void clearNewEmpInformation() {

		newEmpIndex = -1;

		// Labels
		newEmpName.setText("");
		newEmpBirthDate.setText("");
		newEmpGender.setText("");
		newEmpPhone.setText("");

		// TextField
		newEmpSalary.clear();
		newEmpSalary.setPromptText("Salary");

		// RadioButtons
		hourlyEmpRadioButton.setSelected(false);
		monthlyEmpRadioButton.setSelected(true);

	}

	// clear errors
	private void clearNewEmpError() {

		newEmpErrorSalary.setText("");
		newEmpErrorRules.setText("");
		errorSelectNewEmpLabel.setText("");

	}

	// insert into new employee ListView
	private void insertIntoNewEmpListView() throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT * FROM new_users WHERE isEmployee = TRUE;";

		// execute
		Execute.setResultSELECTQuery(query);

		while (Execute.resultSet.next()) {
			NewUser newUser = new NewUser(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4).charAt(0),
					Execute.resultSet.getString(5), Execute.resultSet.getString(6), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), Execute.resultSet.getString(9));
			newEmpList.add(newUser);
			newEmployeeListView.getItems().add(newUser.getName());
		} // end while

		// close
		Execute.close();

	} // end insertIntoNewEmpListView

	// ListView event
	private void selectNewEmp() {

		// clear data
		this.clearNewEmpInformation();

		newEmpIndex = newEmployeeListView.getSelectionModel().getSelectedIndex();

		if (newEmpIndex >= 0) { // if there is selected item

			// get selected object
			NewUser user = newEmpList.get(newEmpIndex);

			// set the data in the label
			newEmpName.setText(" Name: " + user.getName());
			newEmpBirthDate.setText(" Birth date: " + user.getBirthDate());
			newEmpGender.setText(" Gender: " + ChickMethods.getGenderString(user.getGender()));
			newEmpPhone.setText(user.getPhone());

		} else { // if there is no selected employee
			errorSelectNewEmpLabel.setText("select employee");
			newEmpIndex = -1;
		} // end else

	} // end newEmpListViewEvents

	// chick error boolean
	private boolean chickNewEmpError;

	// chick new employee errors
	private void chickNewEmpError() {

		// clear error
		this.clearNewEmpError();

		this.chickNewEmpError = true;

		String salary = newEmpSalary.getText().trim(); // salary
		int ruleIndex = newEmpRulesComboBox.getSelectionModel().getSelectedIndex(); // rule index
		int empIndex = newEmployeeListView.getSelectionModel().getSelectedIndex(); // employee index

		/* empty information */

		// salary
		if (salary.isEmpty()) {

			newEmpErrorSalary.setText("enter the salary"); // set text into error label
			this.chickNewEmpError = false; // set error boolean to false
		}

		// rule
		if (ruleIndex < 0) {
			newEmpErrorRules.setText("select a rule"); // set text into error label
			this.chickNewEmpError = false; // set error boolean to false
		}

		// new employee listView
		if (empIndex < 0) {
			errorSelectNewEmpLabel.setText("select employee"); // set text into error label
			this.chickNewEmpError = false; // set error boolean to false
		}

		/* invalid information */

		if (!ChickMethods.isFloat(salary)) {
			newEmpErrorSalary.setText("ivnalid"); // set text into error label
			this.chickNewEmpError = false; // set error boolean to false
		}

	} // end chickNewEmpError() method

	// accept employee
	private void acceptNewEmp() throws ClassNotFoundException, SQLException {

		// chick error
		this.chickNewEmpError();

		if (this.chickNewEmpError) {

			// new user chosen
			NewUser emp = newEmpList.get(newEmpIndex);

			// delete from new_users table in the database
			this.deleteFormNewUserTable(emp.getId());

			// insert into the database
			this.insertNewEmpIntoDataBase(emp, this.getNewEmpType());

			// refresh
			this.refreshNewEmpData();

		} // end if statement

	} // end acceptNewEmp() method

	// get employee type
	private int getNewEmpType() {
		if (hourlyEmpRadioButton.isSelected())
			return HOURLYEMPLOYEE;
		return MONTHLYEMPLOYEE;
	}

	// add employee into database
	private void insertNewEmpIntoDataBase(NewUser emp, int employeeType) throws ClassNotFoundException, SQLException {

		// today date
		String employmentDate = ChickMethods.getNowDate();

		// salary or salary/hour
		String interedTextField = newEmpSalary.getText().trim();

		// rule
		Rule rule = rulesList.get(newEmpRulesComboBox.getSelectionModel().getSelectedIndex());

		if (employeeType == HOURLYEMPLOYEE) { // if user is hourly employee

			String id = this.getHourlyEmployeeNextId(); // id
			String workingHour = "0"; // working hours
			String salary = "0"; // salary
			String salaryPerHour = interedTextField; // salary/hour

			// insert into database
			this.insertAllInformationOfHourlyEmployeeInDatabase(id, emp.getFirstName(), emp.getLastName(),
					emp.getGender(), emp.getBirthDate(), emp.getFinancialBalance(), employmentDate, salary, workingHour,
					salaryPerHour, emp.getPassword(), emp.getPhone1(), emp.getPhone2(), rule);

		} else if (employeeType == MONTHLYEMPLOYEE) { // if user is monthly employee

			String id = this.getMonthlyEmployeeNextId(); // id
			String vacationDays = "0"; // vacation days
			String deduction = "0"; // deduction
			String allowedVacationDays = "4";
			String salary = interedTextField; // salary

			this.insertAllInformationOfMonthlyEmployeeInDatabase(id, emp.getFirstName(), emp.getLastName(),
					emp.getGender(), emp.getBirthDate(), emp.getFinancialBalance(), employmentDate, vacationDays,
					allowedVacationDays, salary, emp.getPassword(), emp.getPhone1(), emp.getPhone2(), deduction, rule);

		} // end else

		// refresh data
		this.refreshNewEmpData();

	} // end insertNewEmpIntoDataBase method

	// reject employee
	private void rejectNewEmp() throws ClassNotFoundException, SQLException {

		// clear errors
		this.clearNewEmpError();

		if (newEmpIndex >= 0) {
			this.deleteFormNewUserTable(newEmpList.get(newEmpIndex).getId());
			this.refreshNewEmpData();

		} else {
			errorSelectNewEmpLabel.setText("choose employee");
			newEmpIndex = -1;
		}

	}

	// ------------------------------//
	// delete form new user table in the database
	private void deleteFormNewUserTable(String id) throws ClassNotFoundException, SQLException {

		// delete query
		String query = "DELETE FROM new_users WHERE id = " + id + ";";

		// execute
		Execute.executeQuery(query);
	}
}// end AdminControl class
