package EditCommit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import ConnectionsToMYSQL.ConnectToDatabase;
import ConnectionsToMYSQL.ConnectionsText;
import ConnectionsToMYSQL.Execute;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Book;
import library.Borrows;
import library.ChickMethods;

public class BorrowsStringEditCommit implements EventHandler<CellEditEvent<Borrows, String>> {

	private final TableColumn<Borrows, String> name;
	private final TableColumn<Borrows, String> description;
	private final TableColumn<Borrows, String> dateOfPublish;
	private final TableColumn<Borrows, String> publisherName;
	private final TableColumn<Borrows, String> price;
	private final TableColumn<Borrows, String> borrowingPricePerDay;
	private final TableColumn<Borrows, String> numberOfCopy;

	private final TableColumn<Borrows, String> employeeId;
	private final TableColumn<Borrows, String> dateOfBorrows;
	private final TableColumn<Borrows, String> endDateOfBorrows;
	
	private final TableColumn<Borrows, String> borrowingDates;
	private final TableColumn<Borrows, String> returnDate;
	private final TableColumn<Borrows, String> latingDay;

	private final RadioButton returnedRadioButton;
	private final RadioButton unreturnedRadioButton;

	private final TableView<Borrows> table;

	private ObservableList<Borrows> data;
	private ListView<String> listView;
	private String userId;

	public BorrowsStringEditCommit(TableColumn<Borrows, String> name, TableColumn<Borrows, String> description,
			TableColumn<Borrows, String> publisherName, TableColumn<Borrows, String> dateOfPublish,
			TableColumn<Borrows, String> price, TableColumn<Borrows, String> borrowingPricePerDay,
			TableColumn<Borrows, String> numberOfCopy, TableColumn<Borrows, String> employeeId,
			TableColumn<Borrows, String> dateOfBorrows, TableColumn<Borrows, String> endDateOfBorrows,
			TableView<Borrows> table, ObservableList<Borrows> data, ListView<String> listView, String userId,
			RadioButton returnedRadioButton, RadioButton unreturnedRadioButton,
			TableColumn<Borrows, String> borrowingDates, TableColumn<Borrows, String> returnDate, TableColumn<Borrows, String> latingDay) {

		this.name = name;
		this.description = description;
		this.publisherName = publisherName;
		this.dateOfPublish = dateOfPublish;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.numberOfCopy = numberOfCopy;

		this.employeeId = employeeId;
		this.dateOfBorrows = dateOfBorrows;
		this.endDateOfBorrows = endDateOfBorrows;
		
		this.borrowingDates = borrowingDates;
		this.returnDate = returnDate;
		this.latingDay = latingDay;

		this.table = table;

		this.data = data;
		this.listView = listView;

		this.userId = userId;
		
		this.returnedRadioButton = returnedRadioButton;
		this.unreturnedRadioButton = unreturnedRadioButton;
	}

	// generate setters
	public void setData(ObservableList<Borrows> data) {
		this.data = data;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public void handle(CellEditEvent<Borrows, String> t) {

		if (t.getSource().equals(this.dateOfBorrows)) { // date of borrows
			this.updateDateOfBorrows(t);
		} else if (t.getSource().equals(this.endDateOfBorrows)) { // end date of borrows
			this.updateEndDateOfBorrows(t);
		} else if (t.getSource().equals(this.returnDate)) { // return date
			this.updateReturnDate(t);
		} // end else

	} // end handle

	// -----------------------------------------------------------------------------------------//
	// Database Connections
	private static final ConnectToDatabase connectToDatabase = new ConnectToDatabase(ConnectionsText.URL,
			ConnectionsText.port, ConnectionsText.dbName, ConnectionsText.dbUsername, ConnectionsText.dbPassword);

	// -----------------------------------------------------------------------------------------//
	// refresh

	public void refresh() {

		// clear data in ObservableList
		this.data.clear();

		// clear all data in table
		this.table.getItems().clear();

		// clear listView items
		this.listView.getItems().clear();

		// insert data from database into ObservableList then add in tableView
		this.insertDataIntoDataBase();
	}

	// insert all information from the database into table
	private void insertDataIntoDataBase() {

		try {
			this.getDataForSingleUser();
			this.addDataOnTable();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void getDataForSingleUser() throws ClassNotFoundException, SQLException {

		String bookInfo = "";

		if (this.returnedRadioButton.isSelected()) { // if the data are for returned books
			// book information query
			bookInfo = "SELECT BR.borrows_id, B.book_name, B.number_of_copy, B.price, B.borrowing_pricePerDay, B.book_description, B.date_of_publish, P.publisher_name,\r\n"
					+ "       BR.employee_id, BR.date_of_borrow, BR.end_date_of_borrow, BR.return_date, LT.lating_days\r\n"
					+ "FROM book B, publisher P, borrows BR, lating_Day LT\r\n"
					+ "WHERE B.publisher_id = P.publisher_id AND\r\n"
					+ "      B.publisher_id = BR.publisher_id AND \r\n" + "	  B.book_name = BR.book_name AND\r\n"
					+ "      LT.return_date = BR.return_date AND\r\n"
					+ "      LT.end_date_of_borrow = BR.end_date_of_borrow AND\r\n"
					+ "      BR.return_date <> \"\" AND\r\n" + "	  BR.user_id = \"" + this.userId + "\";";

		} else if (this.unreturnedRadioButton.isSelected()) { // if the data are for unreturned books
			// book information query
			bookInfo = "SELECT BR.borrows_id, B.book_name, B.number_of_copy, B.price, B.borrowing_pricePerDay, B.book_description, B.date_of_publish, P.publisher_name,\r\n"
					+ "       BR.employee_id, BR.date_of_borrow, BR.end_date_of_borrow, BR.return_date, LT.lating_days\r\n"
					+ "FROM book B, publisher P, borrows BR, lating_Day LT\r\n"
					+ "WHERE B.publisher_id = P.publisher_id AND\r\n"
					+ "      B.publisher_id = BR.publisher_id AND \r\n" + "	  B.book_name = BR.book_name AND\r\n"
					+ "      LT.return_date = BR.return_date AND\r\n"
					+ "      LT.end_date_of_borrow = BR.end_date_of_borrow AND\r\n"
					+ "      BR.return_date = \"\" AND\r\n" + "	  BR.user_id = \"" + this.userId + "\";";

		} // end else statement

		// author information
		String authorInfo = "";

		// execute
		Execute.setResultSELECTQuery(bookInfo);

		// for each row from resultSet
		while (Execute.resultSet.next()) {

			// author name arrayList
			ArrayList<String> authorNames = new ArrayList<String>();

			String bookName = Execute.resultSet.getString(2); // book name
			String publisherName = Execute.resultSet.getString(8); // publisher name

			// authors name for specific book
			authorInfo = "SELECT DISTINCT author_name FROM book_to_auther BTA, author A, publisher P\r\n"
					+ "WHERE BTA.author_id = A.author_id AND\r\n" + "      BTA.publisher_id = P.publisher_id AND\r\n"
					+ "      BTA.book_name = \"" + bookName + "\" AND\r\n" + "	  P.publisher_name = \"" + publisherName
					+ "\";";

			// temporary connection to database
			Connection tempconnection = connectToDatabase.connect();
			Statement tempStatement = tempconnection.createStatement();
			ResultSet tempResult = tempStatement.executeQuery(authorInfo);

			// for each author's name
			while (tempResult.next()) {

				authorNames.add(tempResult.getString(1));
			}

			// close temporary connections
			tempconnection.close();
			tempStatement.close();
			tempResult.close();

			// add into ObservableList
			this.data.add(new Borrows(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
					(Execute.resultSet.getString(3)), (Execute.resultSet.getString(4)),
					(Execute.resultSet.getString(5)), Execute.resultSet.getString(6), Execute.resultSet.getString(7),
					Execute.resultSet.getString(8), authorNames, Execute.resultSet.getString(9),
					Execute.resultSet.getString(10), Execute.resultSet.getString(11), Execute.resultSet.getString(12),
					(Execute.resultSet.getString(13))));
		}

		// close connection
		Execute.close();

	}

	// add data into table
	private void addDataOnTable() {

		this.name.setCellValueFactory(new PropertyValueFactory<Borrows, String>("name"));
		this.numberOfCopy.setCellValueFactory(new PropertyValueFactory<Borrows, String>("numberOfCopy"));
		this.price.setCellValueFactory(new PropertyValueFactory<Borrows, String>("price"));
		this.borrowingPricePerDay
				.setCellValueFactory(new PropertyValueFactory<Borrows, String>("borrowingPricePerDay"));
		this.description.setCellValueFactory(new PropertyValueFactory<Borrows, String>("description"));
		this.dateOfPublish.setCellValueFactory(new PropertyValueFactory<Borrows, String>("dateOfPublish"));
		this.publisherName.setCellValueFactory(new PropertyValueFactory<Borrows, String>("publisherName"));
		this.employeeId.setCellValueFactory(new PropertyValueFactory<Borrows, String>("employeeId"));
		this.dateOfBorrows.setCellValueFactory(new PropertyValueFactory<Borrows, String>("dateOfBorrow"));
		this.endDateOfBorrows.setCellValueFactory(new PropertyValueFactory<Borrows, String>("endDateOfBorrow"));
		
		if(this.unreturnedRadioButton.isSelected()) { // if the data for unreturned books
			this.borrowingDates.getColumns().remove(this.returnDate);
			this.borrowingDates.getColumns().remove(this.latingDay);
			
		}else if(this.returnedRadioButton.isSelected()) { // if the data for returned books
			this.borrowingDates.getColumns().remove(this.returnDate);
			this.borrowingDates.getColumns().remove(this.latingDay);
			
			this.borrowingDates.getColumns().addAll(this.returnDate, this.latingDay);
		}

		this.table.setItems(data);

	} // end addDataOnTable method

	// -----------------------------------------------------------------------------------------//
	// update date of borrows
	private void updateDateOfBorrows(CellEditEvent<Borrows, String> t) {

		// the old value of the date of borrows
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getDateOfBorrow();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && ChickMethods.isBirthDateValid(newValue)
				&& ChickMethods.isFirstDateBeforeSecond(newValue, t.getRowValue().getEndDateOfBorrow())) {

			if (t.getRowValue().getReturnDate().length() == 0
					|| (ChickMethods.isFirstDateBeforeSecond(newValue, t.getRowValue().getReturnDate()))) {

				// update in the tableView
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setDateOfBorrow(newValue);

				try {
					this.updateDateOfBorrowsInDatabase(t.getRowValue().getId(), newValue);
				} catch (ClassNotFoundException | SQLException e) {
				}

			} else {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setDateOfBorrow(oldValue);
			}
		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDateOfBorrow(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update date of borrows
	private void updateDateOfBorrowsInDatabase(String borrowsId, String newDateOfBorrow)
			throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE borrows\r\n" + "SET date_of_borrow = \"" + newDateOfBorrow + "\"\r\n"
				+ "WHERE borrows_id = " + borrowsId + ";";

		// execute
		Execute.executeQuery(query);
	}

	// -----------------------------------------------------------------------------------------//
	// update end date of borrows
	private void updateEndDateOfBorrows(CellEditEvent<Borrows, String> t) {

		// the old value of the end date of borrows
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getEndDateOfBorrow();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && ChickMethods.isDateValidWithoutNowDate(newValue)
				&& ChickMethods.isFirstDateBeforeSecond(t.getRowValue().getDateOfBorrow(), newValue)) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setEndDateOfBorrow(newValue);

			try {
				this.updateEndDateOfBorrowsInDatabase(t.getRowValue().getId(), newValue, oldValue,
						t.getRowValue().getReturnDate());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setEndDateOfBorrow(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update date of borrows
	private void updateEndDateOfBorrowsInDatabase(String borrowsId, String newEndDateOfBorrow,
			String oldEndDateOfBorrow, String returnDate) throws ClassNotFoundException, SQLException  {

		// is the information of end date of borrow and return date in latingDay table
		// are for unique borrowing event
		boolean isUnique = this.isDatesOfBorrowsUnique(oldEndDateOfBorrow, returnDate, borrowsId);

		if (isUnique) {
			
			try { // if the new value already exist in the database then it will not be inserted
				this.updateEndDateOfBorrowLatingDayTable(returnDate, oldEndDateOfBorrow, newEndDateOfBorrow);
			} catch (ClassNotFoundException | SQLException e) {
				// update in borrows table
				this.updateEndDateOnBorrowInBorrowTable(borrowsId, newEndDateOfBorrow);
			} // end catch
		} else { // not unique
			
			// is the information of date of borrow and return date exist in latingDay table
			boolean isExist = this.isInformationInLatingDaysTableExist(returnDate, newEndDateOfBorrow);

			if (isExist) {
				// update in borrows table
				this.updateEndDateOnBorrowInBorrowTable(borrowsId, newEndDateOfBorrow);
			} else {

				int latingDays = 0;

				if (returnDate.length() > 0) {
					latingDays = ChickMethods.differenceDaysBetweenTwoDates(newEndDateOfBorrow, returnDate);
				}

				// insert into latingDay
				this.insertIntoLatingDaysTable(newEndDateOfBorrow, returnDate, latingDays);

				// update in borrows table
				this.updateEndDateOnBorrowInBorrowTable(borrowsId, newEndDateOfBorrow);
			} // end else

		} // end else
		

	} // end updateEndDateOfBorrowsInDatabase method

	// is date of borrow and end date of borrow is for a unique borrow
	private boolean isDatesOfBorrowsUnique(String endDateOfBorrow, String returnDate, String borrowsId)
			throws ClassNotFoundException, SQLException {

		// select query
		String query = "   SELECT * FROM  lating_Day LD, borrows B\r\n"
				+ "  WHERE LD.end_date_of_borrow = B.end_date_of_borrow AND\r\n"
				+ "        LD.return_date = B.return_date AND\r\n" + "        LD.return_date = \"" + returnDate
				+ "\" AND\r\n" + "        LD.end_date_of_borrow = \"" + endDateOfBorrow + "\" AND \r\n"
				+ "        borrows_id <> " + borrowsId + ";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is unique boolean
		boolean isUnique = !Execute.resultSet.next();

		// close
		Execute.close();

		return isUnique;

	} // end isDatesOfBorrowsUnique method

	// update in latindDay table
	private void updateEndDateOfBorrowLatingDayTable(String returnDate, String oldEndDateOfBorrow, String newEndDateOfBorrow)
			throws ClassNotFoundException, SQLException {

		int latingDays = 0;

		if (returnDate.length() > 0) {
			latingDays = ChickMethods.differenceDaysBetweenTwoDates(newEndDateOfBorrow, returnDate);
		}

		// update query
		String query = "UPDATE lating_Day\r\n" + "SET end_date_of_borrow = \"" + newEndDateOfBorrow
				+ "\", lating_days = " + latingDays + "\r\n" + "WHERE end_date_of_borrow = \"" + oldEndDateOfBorrow
				+ "\" AND\r\n" + "     return_date = \"" + returnDate + "\";";

		// execute
		Execute.executeQuery(query);

	}

	// chick if the information of date of borrow and return date exist in latingDay
	// table
	private boolean isInformationInLatingDaysTableExist(String returnDate, String endDateOfBorrow)
			throws ClassNotFoundException, SQLException {

		// select query
		String query = "SELECT * FROM lating_Day\r\n" + "WHERE end_date_of_borrow = \"" + endDateOfBorrow
				+ "\" AND return_date = \"" + returnDate + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// is exist boolean
		boolean isExist = Execute.resultSet.next();

		// close
		Execute.close();

		return isExist;

	}

	// update in borrows table
	private void updateEndDateOnBorrowInBorrowTable(String borrowsId, String endDateOfBorrow)
			throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE borrows\r\n" + "SET end_date_of_borrow = \"" + endDateOfBorrow + "\"\r\n"
				+ "WHERE borrows_id = " + borrowsId + ";";

		// execute
		Execute.executeQuery(query);

	}

	// insert into latingDays table
	private void insertIntoLatingDaysTable(String endDateOfBorrow, String returnDate, int latingDays)
			throws ClassNotFoundException, SQLException {

		// insert query
		String query = "INSERT INTO lating_day VALUES(\"" + endDateOfBorrow + "\", \"" + returnDate + "\", "
				+ latingDays + ");";

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update return date
	private void updateReturnDate(CellEditEvent<Borrows, String> t) {

		// the old value of the return date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getReturnDate();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!oldValue.isEmpty() && !newValue.isEmpty() && ChickMethods.isBirthDateValid(newValue)
				&& ChickMethods.isFirstDateBeforeSecond(t.getRowValue().getDateOfBorrow(), newValue)) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setReturnDate(newValue);

			try {
				this.updateReturnDateInDatabase(t.getRowValue().getId(), newValue, oldValue,
						t.getRowValue().getEndDateOfBorrow());
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setReturnDate(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update return date
	private void updateReturnDateInDatabase(String borrowsId, String newReturnDate,
			String oldReturnDate, String endDateOfBorrow) throws ClassNotFoundException, SQLException {

		// is the information of end date of borrow and return date in latingDay table
		// are for unique borrowing event
		boolean isUnique = this.isDatesOfBorrowsUnique(endDateOfBorrow, oldReturnDate, borrowsId);

		if (isUnique) {
			
			try { // if the new value already exist in the database then it will not be inserted
				this.updateReturnDateLatingDayTable(endDateOfBorrow, oldReturnDate, newReturnDate);
			} catch (ClassNotFoundException | SQLException e) {
				// update in borrows table
				this.updateReturnDateInBorrowTable(borrowsId, newReturnDate);
			}
		
		} else {

			// is the information of date of borrow and return date exist in latingDay table
			boolean isExist = this.isInformationInLatingDaysTableExist(newReturnDate, endDateOfBorrow);

			if (isExist) {
				// update in borrows table
				this.updateReturnDateInBorrowTable(borrowsId, newReturnDate);
			} else {

				int latingDays = 0;

				if (newReturnDate.length() > 0) {
					latingDays = ChickMethods.differenceDaysBetweenTwoDates(endDateOfBorrow, newReturnDate);
				}

				// insert into latingDay
				this.insertIntoLatingDaysTable(endDateOfBorrow, newReturnDate, latingDays);

				// update in borrows table
				this.updateReturnDateInBorrowTable(borrowsId, newReturnDate);
			} // end else

		} // end else
		

	} // end updateEndDateOfBorrowsInDatabase method

	// update in latindDay table
	private void updateReturnDateLatingDayTable(String endDateOfBorrow, String oldReturnDate, String newReturnDate)
			throws ClassNotFoundException, SQLException {

		int latingDays = 0;

		if (newReturnDate.length() > 0) {
			latingDays = ChickMethods.differenceDaysBetweenTwoDates(endDateOfBorrow, newReturnDate);
		}

		// update query
		String query = "UPDATE lating_Day\r\n" + "SET return_date = \"" + newReturnDate
				+ "\", lating_days = " + latingDays + "\r\n" + "WHERE end_date_of_borrow = \"" + endDateOfBorrow
				+ "\" AND\r\n" + "     return_date = \"" + oldReturnDate + "\";";		
		
		
		// execute
		Execute.executeQuery(query);

	}
	
	// update in borrows table
	private void updateReturnDateInBorrowTable(String borrowsId, String returnDate)
			throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE borrows\r\n" + "SET return_date = \"" + returnDate + "\"\r\n"
				+ "WHERE borrows_id = " + borrowsId + ";";

		// execute
		Execute.executeQuery(query);
	}	
}
