package Controls;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;

import ConnectionsToMYSQL.Execute;
import Layout.FinancialLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import library.BorrowingFinancial;

public class FinancialControl extends FinancialLayout implements EventHandler<ActionEvent> {

	public FinancialControl(HBox adminPane) {
		super(adminPane);
		this.setEvent();
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			if (event.getSource().equals(bookName)) { // book price
				this.getPriceForSingleBook();
			} else if (event.getSource().equals(userIdFinancial)) { // user financial balance
				this.getFinancialBalanceForSingleUser();
			} else if (event.getSource().equals(userIdFees)) { // fees
				this.getFeesForSingleUser();
			} else if (event.getSource().equals(financialInfoExportToFileButton)) { // export
				this.exportInformation();
			} // end else

		} catch (ClassNotFoundException | SQLException | FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	// add on pane
	public void addOnPane() {
		super.adminPane.getChildren().clear();
		super.adminPane.getChildren().add(this);

		this.refreshDate();
	}

	// set event
	private void setEvent() {

		// total price
		bookName.setOnAction(this);

		// financial
		userIdFinancial.setOnAction(this);

		// fees
		userIdFees.setOnAction(this);

		// export
		financialInfoExportToFileButton.setOnAction(this);
	}

	// refresh
	private void refreshDate() {

		// clear data
		this.clearData();

		// ------------------------------------//
		// insert all information
		this.insertInfoFromDatabaseIntoTable(); // insert fees into table
		try {
			this.insertIntoSalariesLael(); // get salaries data
			this.insertIntoPricesLabel(); // get prices data
			this.insertIntoFinancial(); // get financial data
			this.insertIntoPublishersComboBox();// insert publisher names into ComboBox
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// clear date
	private void clearData() {

		financialList.clear();
		table.getItems().clear();

		this.clearTexts();

	}

	// clear texts
	private void clearTexts() {

		// price
		singleBookPricesLabel.setText("");
		bookName.setText("");

		// financial
		userIdFinancial.setText("");
		totalFinancialBalanceSingleUserLabel.setText("");

		// fees
		userIdFees.setText("");
		totalFeesSingleUserLabel.setText("");

		financialInfoExportToFileLabel.setText("");
	}

	// -----------------------------------------------------//
	// add information into table

	// observableList
	private static final ObservableList<BorrowingFinancial> financialList = FXCollections.observableArrayList();

	// insert information into table from the database
	private void insertInfoFromDatabaseIntoTable() {

		try {
			this.getData();
			this.addDataOnTable();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} // end insertInfoFromDatabaseIntoTable method

	private void getData() throws ClassNotFoundException, SQLException {

		// insert query
		String query = "SELECT * FROM borrowing_financial;";

		// execute
		Execute.setResultSELECTQuery(query);

		while (Execute.resultSet.next()) {
			financialList.add(new BorrowingFinancial(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					Execute.resultSet.getString(3), Execute.resultSet.getString(4), Execute.resultSet.getString(5),
					Execute.resultSet.getString(6)));
		} // end while

		// close
		Execute.close();

	} // end getData method

	private void addDataOnTable() {

		bookNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowingFinancial, String>("bookName"));
		publisherNameColumn.setCellValueFactory(new PropertyValueFactory<BorrowingFinancial, String>("publisherName"));
		userIdColumn.setCellValueFactory(new PropertyValueFactory<BorrowingFinancial, String>("userId"));
		addingDateColumn.setCellValueFactory(new PropertyValueFactory<BorrowingFinancial, String>("addingDate"));
		financialColumn.setCellValueFactory(new PropertyValueFactory<BorrowingFinancial, String>("financial"));

		table.setItems(financialList);

	}

	// ---------------------------------------------------------------------//
	// insert all information in the label

	// salaries
	private void insertIntoSalariesLael() throws ClassNotFoundException, SQLException {

		this.getSalariesFromDatabase();

		hourlyEmployeesTotalSalaries.setText("Hourly employees: " + hourlyEmployeeSalaries);
		monthlyEmployeesTotalSalaries.setText("Monthly employees: " + monthlyEmployeeSalaries);
		allEmployeesTotalSalaries.setText("Total: " + totalSalaries);

	}

	// get salaries from database
	private static double hourlyEmployeeSalaries, monthlyEmployeeSalaries, totalSalaries;

	private void getSalariesFromDatabase() throws ClassNotFoundException, SQLException {

		hourlyEmployeeSalaries = 0;
		monthlyEmployeeSalaries = 0;
		totalSalaries = 0;

		// -------------------------------------------------------------//
		// hourly employee salaries select query
		String hourlyEmpSalaryQuery = "SELECT SUM(salary) FROM hourly_employee HE, salary S \r\n"
				+ "WHERE HE.workingHours = S.workingHours AND HE.salaryPerHour = S.salaryPerHour; ";

		// execute
		Execute.setResultSELECTQuery(hourlyEmpSalaryQuery);

		if (Execute.resultSet.next())
			hourlyEmployeeSalaries = Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		// -------------------------------------------------------------//
		// monthly employee salaries select query
		String monthlyEmpSalaryQuery = "SELECT DISTINCT  E.salary - E.salary*D.deduction  FROM employee E, monthly_employee ME, deduction D\r\n"
				+ "WHERE E.user_id = ME.user_id AND ME.vaction_days = D.vaction_days AND ME.vaction_days = D.vaction_days;";

		// execute
		Execute.setResultSELECTQuery(monthlyEmpSalaryQuery);

		while (Execute.resultSet.next())
			monthlyEmployeeSalaries += Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		// total salaries
		totalSalaries = hourlyEmployeeSalaries + monthlyEmployeeSalaries;

	} // end getSalariesFromDatabase() method

	// insert into prices
	private void insertIntoPricesLabel() throws ClassNotFoundException, SQLException {

		this.getAllPricesFromDatabase();

		bookPricesLabel.setText("All book prices: " + allBooksPrices);

	}

	// get prices from database
	private static double nonBorrowingBooksPrices, borrowingBookPrices, allBooksPrices;

	private void getAllPricesFromDatabase() throws ClassNotFoundException, SQLException {

		nonBorrowingBooksPrices = 0;
		borrowingBookPrices = 0;
		allBooksPrices = 0;

		// select all non borrowing prices query
		String nonBorrowingquery = "SELECT SUM(price* number_of_copy) FROM book;";

		// execute
		Execute.setResultSELECTQuery(nonBorrowingquery);

		if (Execute.resultSet.next())
			nonBorrowingBooksPrices = Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		// select all borrowing prices query
		String borrowingQuery = " SELECT  COUNT(*)* B.price FROM book B, borrows BR \r\n"
				+ "       WHERE B.book_name = BR.book_name AND B.publisher_id = BR.publisher_id AND BR.return_date = \"\"\r\n"
				+ "       GROUP BY B.book_name, B.publisher_id;";

		// execute
		Execute.setResultSELECTQuery(borrowingQuery);

		while (Execute.resultSet.next())
			borrowingBookPrices += Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		// all books' prices
		allBooksPrices = nonBorrowingBooksPrices + borrowingBookPrices;

	} // end getAllPricesFromDatabase method

	// insert into financial
	private void insertIntoFinancial() throws ClassNotFoundException, SQLException {

		this.getFinancialsFromDatabase();

		employeesTotalFinancialLabel.setText("Employees: " + empFinancial);
		membersTotalFinancialLabel.setText("Members: " + memberFinalncial);
		totalFinancialLabel.setText("Total: " + allUsersFinalncial);

	}

	// get financial from database
	private static double memberFinalncial, empFinancial, allUsersFinalncial;

	private void getFinancialsFromDatabase() throws ClassNotFoundException, SQLException {

		memberFinalncial = 0;
		empFinancial = 0;
		allUsersFinalncial = 0;

		// member ------------------------------------------------------

		// select member financial query
		String memberQuery = "SELECT SUM(U.user_finantialPalance) FROM users U, member M WHERE u.user_id = M.user_id;";

		// execute
		Execute.setResultSELECTQuery(memberQuery);

		if (Execute.resultSet.next())
			memberFinalncial = Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		// employee ------------------------------------------------------

		// select employee financial query
		String employeeQuery = "SELECT SUM(U.user_finantialPalance) FROM users U, employee E WHERE u.user_id = E.user_id;";

		// execute
		Execute.setResultSELECTQuery(employeeQuery);

		if (Execute.resultSet.next())
			empFinancial = Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		// all financial
		allUsersFinalncial = memberFinalncial + empFinancial;

	}

	// ----------------------------------------------//
	// insert publishers in the comboBox

	private void insertIntoPublishersComboBox() throws ClassNotFoundException, SQLException {

		// clear ComboBox information
		publisherName.getItems().clear();

		// select all publishers query
		String query = "SELECT * FROM publisher;";

		// execute
		Execute.setResultSELECTQuery(query);

		while (Execute.resultSet.next())
			publisherName.getItems().add(Execute.resultSet.getString(2));

		// close
		Execute.close();

	}

	// -----------------------------------------------------------------------------------------------//
	// search data

	// ----------------------------------- book price
	// ---------------------------------
	private void getPriceForSingleBook() throws ClassNotFoundException, SQLException {

		// clear book price label
		singleBookPricesLabel.setText("");
		singleBookPricesLabel.setStyle("-fx-text-fill:MediumBlue;");

		// publisher selected index
		int index = publisherName.getSelectionModel().getSelectedIndex();
		String name = bookName.getText().trim();

		if (!(index < 0 || name.isEmpty())) { // if there is an entered information
			String publisher = publisherName.getSelectionModel().getSelectedItem();
			if (this.isBookExist(name, publisher)) { // if book exist in the database
				// -----------------------------set the result in the
				// Label-----------------------------------
				singleBookPricesLabel.setText("all copies price: " + this.getBookTotalPrice(name, publisher));
			} else { // if book does not exist in the database
				singleBookPricesLabel.setStyle("-fx-text-fill:red;");
				singleBookPricesLabel.setText("book does not exist");
			} // end else
		} else { // if there is no entered information
			singleBookPricesLabel.setStyle("-fx-text-fill:red;");
			singleBookPricesLabel.setText("enter book name and choose publisher");
		} // end else

	}

	// is book exist
	private boolean isBookExist(String book, String publisher) throws ClassNotFoundException, SQLException {

		// select from book and publisher tables query
		String query = "SELECT B.book_name, P.publisher_name FROM book B, publisher P \r\n"
				+ "WHERE B.publisher_id = P.publisher_id AND \r\n" + "      B.book_name = \"" + book + "\" AND\r\n"
				+ "      P.publisher_name = \"" + publisher + "\"\r\n" + "ORDER BY B.book_name;";

		// execute
		Execute.setResultSELECTQuery(query);

		// is book already exist boolean
		boolean isBookExist = Execute.resultSet.next();

		// close
		Execute.close();

		return isBookExist;
	}

	// get book total price
	private double getBookTotalPrice(String bookName, String publisherName)
			throws ClassNotFoundException, SQLException {

		double prices = 0; // prices double
		String id = this.getPublisherId(publisherName); // publisher id

		// non borrows books -------------------------------------------------------
		// select query for non borrows books
		String nonBorrowsQuery = "SELECT number_of_copy * price FROM book WHERE book_name = \"" + bookName
				+ "\" AND publisher_id = " + id + ";";

		// execute
		Execute.setResultSELECTQuery(nonBorrowsQuery);

		if (Execute.resultSet.next())
			prices += Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		// borrows books -------------------------------------------------------
		// select query for borrows books
		String borrowsQuery = "SELECT price* COUNT(*) FROM borrows BR, book B WHERE BR.book_name = B.book_name AND BR.publisher_id = B.publisher_id AND return_date = \"\" AND B.publisher_id = "
				+ id + " AND B.book_name = \"" + bookName + "\" GROUP BY B.publisher_id,  B.book_name;";

		// execute
		Execute.setResultSELECTQuery(borrowsQuery);

		if (Execute.resultSet.next())
			prices += Double.parseDouble(Execute.resultSet.getString(1));

		// close
		Execute.close();

		return prices;

	}

	// get publisher id
	private String getPublisherId(String name) throws ClassNotFoundException, SQLException {

		// select query
		String query = "SELECT publisher_id FROM publisher WHERE publisher_name = \"" + name + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		String id = ""; // id String
		if (Execute.resultSet.next())
			id = Execute.resultSet.getString(1);

		return id;

	}

	// -------------------------------- financial balance
	// ---------------------------------
	private void getFinancialBalanceForSingleUser() throws ClassNotFoundException, SQLException {

		// clear financial label
		totalFinancialBalanceSingleUserLabel.setText("");
		totalFinancialBalanceSingleUserLabel.setStyle("-fx-text-fill:MediumBlue;");

		String id = userIdFinancial.getText().trim(); // entered user id

		if (!id.isEmpty()) { // if there is an entered id
			if (this.isUserExist(id)) { // if the user id exist in the database
				// ---------------------------------set the result in the
				// Label-----------------------------------------
				totalFinancialBalanceSingleUserLabel.setText("financial: " + this.getFinancialBalanceForSingleUser(id));
			} else { // if the user id does not exist in the database
				totalFinancialBalanceSingleUserLabel.setText("user id does not exist");
				totalFinancialBalanceSingleUserLabel.setStyle("-fx-text-fill:red;");
			}

		} else {// if there is no entered id
			totalFinancialBalanceSingleUserLabel.setText("enter the user id");
			totalFinancialBalanceSingleUserLabel.setStyle("-fx-text-fill:red;");
		}

	}

	// is user exist
	private boolean isUserExist(String id) throws ClassNotFoundException, SQLException {

		// select from user table query
		String query = "SELECT * FROM users WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is exist boolean
		boolean isExist = Execute.resultSet.next();

		return isExist;

	}

	// get financial balance
	private String getFinancialBalanceForSingleUser(String id) throws ClassNotFoundException, SQLException {

		// select financial balance query from user table
		String query = "SELECT user_finantialPalance FROM users WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		String financialBalance = ""; // financial balance String

		if (Execute.resultSet.next())
			financialBalance = Execute.resultSet.getString(1);

		// close
		Execute.close();

		return financialBalance;
	} // end getFinancialBalanceForSingleUser method

	// --------------------------------------- fees
	// --------------------------------------------
	private void getFeesForSingleUser() throws ClassNotFoundException, SQLException {

		// clear fees label label
		totalFeesSingleUserLabel.setText("");
		totalFeesSingleUserLabel.setStyle("-fx-text-fill:MediumBlue;");

		String id = userIdFees.getText().trim();

		if (!id.isEmpty()) { // if there is an entered id
			if (this.isUserExist(id)) { // if the user id exist in the database
				// ---------------------------------set the result in the
				// Label-----------------------------------------
				totalFeesSingleUserLabel.setText("fees: " + this.getFeesForSingleUser(id));
			} else {// if the user id does not exist in the database
				totalFeesSingleUserLabel.setText("user id does not exist");
				totalFeesSingleUserLabel.setStyle("-fx-text-fill:red;");
			} // end else
		} else { // if there is no entered id
			totalFeesSingleUserLabel.setText("enter user id");
			totalFeesSingleUserLabel.setStyle("-fx-text-fill:red;");
		} // end else

	}

	// get fees
	private String getFeesForSingleUser(String id) throws ClassNotFoundException, SQLException {

		// select fees query
		String query = "SELECT SUM(financial) FROM borrowing_financial WHERE user_id = \"" + id + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		String fees = "0"; // fees String

		if (Execute.resultSet.next())
			if (Execute.resultSet.getString(1) != null)
				fees = Execute.resultSet.getString(1);

		// close
		Execute.close();

		return fees;
	}

	// ------------------------------------------------------------------------------//
	// export information to file
	private static final FileChooser fileChooser = new FileChooser();
	private static File file;

	private void exportInformation() throws FileNotFoundException {

		fileChooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
		file = fileChooser.showSaveDialog(new Stage());

		if (file != null) {
			this.insertIntoFile(file, this.getStringOfAllFinancialInfo());
			financialInfoExportToFileLabel.setText("information saved");
		}
	}

	// get string of all information
	private String getStringOfAllFinancialInfo() {

		String info = "";

		// total salaries
		info += "Total salaries for all employees: \n"; // text
		info += "\tHourly employee: " + hourlyEmployeesTotalSalaries.getText() + "\n"; // hourly
		info += "\tMonthly employee: " + monthlyEmployeesTotalSalaries.getText() + "\n"; // monthly
		info += "\ttotal: " + allEmployeesTotalSalaries.getText() + "\n\n"; // total

		// total prices
		info += "Total prices for all book in the library: \n"; // text
		info += "\tAll book prices: " + bookPricesLabel.getText() + "\n\n"; // prices

		// total financial balance
		info += "Total financial balance: \n"; // text
		info += "\tEmployees: " + employeesTotalFinancialLabel.getText() + "\n"; // employees
		info += "\tMembers: " + membersTotalFinancialLabel.getText() + "\n"; // member
		info += "\tTotal: " + totalFinancialLabel.getText() + "\n\n"; // total

		// fees from borrowing
		info += "\nFees form borrowing: \n";
		int size = financialList.size(); // list size
		info += "\n" + this.getStringWith15Char("book") + this.getStringWith15Char("publisher")
				+ this.getStringWith15Char("user id") + this.getStringWith15Char("adding date")
				+ this.getStringWith15Char("financial") + "\n\n";
		for (int i = 0; i < size; i++) {
			info += this.getStringWith15Char(financialList.get(i).getBookName())
					+ this.getStringWith15Char(financialList.get(i).getPublisherName())
					+ this.getStringWith15Char(financialList.get(i).getUserId())
					+ this.getStringWith15Char(financialList.get(i).getAddingDate())
					+ this.getStringWith15Char(financialList.get(i).getFinancial()) + "\n";
		}

		return info;

	}

	private String getStringWith15Char(String text) {
		
		if(text == null)
			return "               ";

		int length = text.length();// length

		if (length < 15)
			for (int i = 15; i > length; i--)
				text += " ";

		return text;
	}

	// insert String into file
	private void insertIntoFile(File file, String text) throws FileNotFoundException {
		
		PrintWriter print = new PrintWriter(file);
		
		print.print(text);
		
		print.close();		
	}
} // end class
