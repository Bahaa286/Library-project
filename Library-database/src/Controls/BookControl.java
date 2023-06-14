package Controls;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import ConnectionsToMYSQL.ConnectToDatabase;
import ConnectionsToMYSQL.ConnectionsText;
import ConnectionsToMYSQL.Execute;
import EditCommit.BookStringEditCommit;
import EditCommit.BorrowsStringEditCommit;
import Layout.BookLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import library.Book;
import library.Borrows;
import library.ChickMethods;
import library.Member;

public class BookControl extends BookLayout implements EventHandler<ActionEvent> {

	// style text having name of css file
	private final String STYLE = "style.css";

	// user id
	private String id;

	// employee id
	private String employeeId;

	public BookControl(String id, String employeeId) {
		super(id);

		// set memberId
		this.id = id;

		// set employee id
		this.employeeId = employeeId;

		// references
		this.setReferences();

		// events
		this.setEvents();

	}

	public BookControl(HBox tableViewPane) {
		super(tableViewPane);

		// references
		this.setReferences();

		// events
		this.setEvents();
	}

	// handle method
	@Override
	public void handle(ActionEvent event) {

		try {
			if (event.getSource().equals(super.insert)) { // insert
				this.insertBook();
			} else if (event.getSource().equals(super.chooseBookButton)) { // choose book
				this.insertBook();
			} else if (event.getSource().equals(super.refresh)) { // refresh
				this.refresh();
			} else if (event.getSource().equals(super.delete)) { // delete
				this.deleteBook();
			} else if (event.getSource().equals(super.addAuthor)) { // add author
				this.insertAuthor();
			} else if (event.getSource().equals(super.deleteAuthor)) { // delete author
				this.removeAuthorFromListView();
			} else if (event.getSource().equals(super.addOnList)) { // add on list (author name)
				this.insertAuthorIntoBook();
			} else if (event.getSource().equals(super.deleteFromList)) { // delete from list
				this.deleteAuthorName();
			} else if (event.getSource().equals(super.searchButton)) { // search button
				this.searchBook();
			} else if (event.getSource().equals(super.addFromSearch)) { // add book from search result
				this.addBookFromResultSearch();
			} else if (event.getSource().equals(super.deleteFromSearch)) { // delete book from search result
				this.deleteBookFromResultSearch();
			} else if (event.getSource().equals(super.returnedRadioButton)) { // returned radioButton
				this.refresh();
			} else if (event.getSource().equals(super.unreturnedRadioButton)) { // unreturned radioButton
				this.refresh();
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// -----------------------------------------//
	// add pane
	public void addPane() {
		this.clear();
		this.refresh();
		super.adminPane.getChildren().clear();
		super.adminPane.getChildren().add(this);
	}
	// -----------------------------------------//
	// references

	private void setReferences() {

		// data ObservableList
		this.data = FXCollections.observableArrayList();

		// set references into bookStringEditCommit
		borrowsStringEditCommit.setData(this.data);
		borrowsStringEditCommit.setUserId(this.id);

		// set references into bookStringEditCommit
		bookStringEditCommit.setData(this.data);
		bookStringEditCommit.setAllBookInformation(allBookInfo);
		bookStringEditCommit.setListView(super.allBookListView);

		// insert from database into table
		this.insertDataIntoDataBase();

		// update
		try {
			this.update();
		} catch (NumberFormatException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	// -----------------------------------------//
	// events

	private void setEvents() {

		if (super.isSingleUser) { // if this for single user
			super.chooseBookButton.setOnAction(this); // choose book from list

			super.table.setOnMouseClicked(e -> { // select item in table
				if (e.getClickCount() == 1) {

					// selected book
					Borrows selected = (Borrows) table.getSelectionModel().getSelectedItem();

					if (selected != null) { // if there is an item selected

						// authors in selected book
						ArrayList<String> authors = selected.getAuthorName();

						// clear listView
						super.listView.getItems().clear();

						// insert authors into listView
						for (int i = 0; i < authors.size(); i++) {
							// insert
							super.listView.getItems().add(authors.get(i));
						} // end for
					} // end if(selected != null)
				} // end if (e.getClickCount() == 1)
			});

			super.addFromSearch.setOnAction(this); // add book from search
			super.returnedRadioButton.setOnAction(this); // returned radioButton action
			super.unreturnedRadioButton.setOnAction(this); // unreturned radioButton action

		} else { // if this for all book
			super.insert.setOnAction(this); // insert
			super.addAuthor.setOnAction(this); // add author
			super.deleteAuthor.setOnAction(this); // delete author
			super.addOnList.setOnAction(this); // add author to list
			super.deleteFromList.setOnAction(this); // delete author from list

			super.table.setOnMouseClicked(e -> { // select item in table
				if (e.getClickCount() == 1) {

					// selected book
					Book selected = (Book) table.getSelectionModel().getSelectedItem();

					if (selected != null) { // if there is an item selected

						// authors in selected book
						ArrayList<String> authors = selected.getAuthorName();

						// clear listView
						super.listView.getItems().clear();

						// insert authors into listView
						for (int i = 0; i < authors.size(); i++) {
							// insert
							super.listView.getItems().add(authors.get(i));
						} // end for
					} // end if(selected != null)
				} // end if (e.getClickCount() == 1)
			});

			super.deleteFromSearch.setOnAction(this); // delete book from search

		} // end else

		super.refresh.setOnAction(this); // refresh
		super.delete.setOnAction(this); // delete

		super.searchButton.setOnAction(this); // search book

	}
	
	// -----------------------------------------------------------------------------------------//
	// Database Connections
	private ConnectToDatabase connectToDatabase = new ConnectToDatabase(ConnectionsText.URL, ConnectionsText.port,
			ConnectionsText.dbName, ConnectionsText.dbUsername, ConnectionsText.dbPassword);

	// ---------------------------------------------------------------------------------------------//
	// clear

	// clear all
	private void clear() {

		// error
		this.clearError();

		// option
		this.clearOption();

		// author option
		this.clearAutherOption();

		// clear search ListView result
		super.resultSearchListView.getItems().clear();

	} // end clear() method

	// clear all error
	private void clearError() {

		// -----------------------------------------------//
		// labels

		if (!super.isSingleUser) { // if this for all book information

			// error labels number
			int errorOption = super.errorsOption.length;

			// clear all error labels
			for (int i = 0; i < errorOption; super.errorsOption[i++].setText(""))
				;

			// author Label
			super.errorListView.setText("");

			// -----------------------------------------------//
			// error pane

			// error pane number
			int errorPaneOption = super.optionsPane.length;

			// clear all error pane
			for (int i = 0; i < errorOption; this.paneBakground(false, i++))
				;

			// textField ListView
			this.listViewTextPaneBakground(false);

			// author error pane
			this.authorNameBackground(false);
			this.authorNameListViewPaneBakground(false);

		} else { // if this for a single user
			super.errorSelectBookLabel.setText("");

		}

		// authors label error

		// ListView
		this.listViewPaneBakground(false);

		// search book error
		super.errorSearchLabel.setText("");

		// search book background pane
		this.searchPaneBackground(false);

	}

	// clear options
	private void clearOption() {

		if (!super.isSingleUser) { // if layout for all book information

			// textField number
			int textFieldOption = super.textFields.length;

			// clear all texts in all textFields
			for (int i = 0; i < textFieldOption; i++) {

				if (i != 5) {// index 5 is for DatePicker

					// clear text
					super.textFields[i].clear();
				} // end if

			} // end for

			// clear authorsName ListView
			super.authorsName.getItems().clear();

			// clear dateOdPublish DatePicker
			super.dateOfPublishDatePicker.setValue(null);

			// clear author name TextField
			super.authorTextOnList.clear();
		}

		// clear selection item
		super.table.getSelectionModel().clearSelection();

		// clear search TextFields
		super.searchBookNameTextField.clear();
		super.searchPublisherNameTextField.clear();

	}

	// clear author option
	private void clearAutherOption() {

		if (!super.isSingleUser) { // if this layout for all book informations

			// author textField
			super.authorTextOnList.clear();
		}

		// author ListView
		super.listView.getItems().clear();

	} // end clearAutherOption method

	// --------------------------------------------------------------------------------------------//
	// pane background

	// errors pane
	private void paneBakground(boolean isError, int index) {

		if (isError) {
			super.optionsPane[index].setStyle("-fx-border-color: red");
		} else {
			super.optionsPane[index].setStyle("-fx-border-color: Aqua");
		}

	}

	// author listView pane
	private void listViewPaneBakground(boolean isError) {

		if (isError) {
			super.listView.setStyle("-fx-border-color: red");
		} else {
			super.listView.setStyle("-fx-border-color: Aqua");
		}

	}

	// error ListView text Pane background
	private void listViewTextPaneBakground(boolean isError) {

		if (isError) {
			super.listViewControlPane.setStyle("-fx-border-color: red");
		} else {
			super.listViewControlPane.setStyle("-fx-border-color: Aqua");
		}

	}

	// author listView pane
	private void authorNameListViewPaneBakground(boolean isError) {

		if (isError) {
			super.authorsName.setStyle("-fx-border-color: red");
		} else {
			super.authorsName.setStyle("-fx-border-color: Aqua");
		}

	}

	// error author name background
	private void authorNameBackground(boolean isError) {

		if (isError) {
			super.listViewErrorPane.setStyle("-fx-border-color: red");
		} else {
			super.listViewErrorPane.setStyle("-fx-border-color: Aqua");
		}

	}

	// error search pane background
	private void searchPaneBackground(boolean isError) {

		if (isError) {
			super.searchPane.setStyle("-fx-border-color: red");
		} else {
			super.searchPane.setStyle("-fx-border-color: Aqua");
		}

	}

	// --------------------------------------------------------------------------------------------//
	// chick errors

	// add book error
	private boolean chickAddBookError;

	// chick add book error
	private void chickAddBookError() throws ClassNotFoundException, SQLException {

		// ----------------------------------------//
		// textFields values
		String bookName = super.textFields[0].getText().trim();
		String numberOfCopies = super.textFields[1].getText().trim();
		String price = super.textFields[2].getText().trim();
		String borrowingPricePerDay = super.textFields[3].getText().trim();
		String description = super.textFields[4].getText().trim();

		String dateOfPublish = "";

		if (super.dateOfPublishDatePicker.getValue() != null) // chick if there is chosen date
			dateOfPublish = super.dateOfPublishDatePicker.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

		String publisherName = super.textFields[6].getText().trim();
		// ----------------------------------------//

		// clear all previous errors
		this.clearError();

		// set chick error to true
		this.chickAddBookError = true;

		// -----------------------------------------------------//
		// empty information
		int optionTextFieldNum = super.textFields.length;

		// TextFields
		for (int i = 0; i < optionTextFieldNum - 1; i++) {

			if (i != 5 && super.textFields[i].getText().trim().length() == 0) { // if tectField empty

				this.chickAddBookError = false; // change chickAddBookError to false
				super.errorsOption[i].setText("invalid"); // set a text on errorLabe
				this.paneBakground(true, i); // change the color of background pane to red

			} // end if
		} // end for

		// ListView
		if (super.authorsName.getItems().isEmpty()) {
			this.chickAddBookError = false; // change chickAddBookError to false
			super.errorsOption[optionTextFieldNum - 1].setText("invalid"); // set a text on errorLabe
			this.authorNameListViewPaneBakground(true); // change the color of background pane to red
		}

		// DatePicker
		if (dateOfPublish.length() == 0) {
			this.chickAddBookError = false; // change chickAddBookError to false
			super.errorsOption[5].setText("invalid"); // set a text on errorLabe
			this.paneBakground(true, 5); // change the color of background pane to red
		}
		// -----------------------------------------------------//
		// invalid information

		// chick if the value is large
		if (bookName.length() > 40) {
			this.chickAddBookError = false; // change chickAddBookError to false
			super.errorsOption[0].setText("large value"); // set a text on errorLabe
			this.paneBakground(true, 0); // change the color of background pane to red
		}

		// chick if the value is large
		if (publisherName.length() > 20) {
			this.chickAddBookError = false; // change chickAddBookError to false
			super.errorsOption[1].setText("large value"); // set a text on errorLabe
			this.paneBakground(true, 1); // change the color of background pane to red
		}

		// chick if book already exist
		if (!bookName.isEmpty() && !publisherName.isEmpty()) {
			if (this.isBookAlreadyExist(bookName, publisherName)) { // if book exist
				this.chickAddBookError = false; // change chickAddBookError to false

				// book
				super.errorsOption[0].setText("book already exist"); // set a text on errorLabe
				this.paneBakground(true, 0); // change the color of background pane to red

				// author
				super.errorsOption[6].setText("already exist"); // set a text on errorLabe
				this.paneBakground(true, 6); // change the color of background pane to red

			} // end if

		} // end if

		// chick if number of copies valid
		if (!ChickMethods.isDigit(numberOfCopies)) {
			this.chickAddBookError = false; // change chickAddBookError to false
			super.errorsOption[1].setText("invalid"); // set a text on errorLabe
			this.paneBakground(true, 1); // change the color of background pane to red
		}

		// chick if price valid
		if (!ChickMethods.isFloat(price)) {
			this.chickAddBookError = false; // change chickAddBookError to false
			super.errorsOption[2].setText("invalid"); // set a text on errorLabe
			this.paneBakground(true, 2); // change the color of background pane to red
		}

		// chick if borrowing price per day valid
		if (!ChickMethods.isFloat(borrowingPricePerDay)) {
			this.chickAddBookError = false; // change chickAddBookError to false
			super.errorsOption[3].setText("invalid"); // set a text on errorLabe
			this.paneBakground(true, 3); // change the color of background pane to red
		}

		// chick if date of publish valid
		if (dateOfPublish.length() != 0) {
			if (!ChickMethods.isDatePickerValid(super.dateOfPublishDatePicker.getValue())) {
				this.chickAddBookError = false; // change chickAddBookError to false
				super.errorsOption[5].setText("invalid"); // set a text on errorLabe
				this.paneBakground(true, 5); // change the color of background pane to red
			}
		}

	} // end chick add book error

	// author name error
	private boolean chickAuthorNameError;

	// chick author name error
	private void chickAuthorNameError() {

		// clear all previous error
		this.clearError();

		// author name
		String authorName = super.textFields[7].getText().trim();

		// set chick author name value to true
		chickAuthorNameError = true;

		// is empty
		if (authorName.length() == 0) {
			this.chickAuthorNameError = false;
			super.errorsOption[7].setText("invalid");
			this.paneBakground(true, 7);
		}

		// invalid
		if (authorName.length() == 0) {
			if (!ChickMethods.isNameValid(authorName)) {
				this.chickAuthorNameError = false;
				super.errorsOption[7].setText("invalid");
				this.paneBakground(true, 6);
			}
		}

	}

	// listView error
	private boolean chickListViewError;

	// chick listView error
	private void chickListViewError() {

		// clear all previous error
		this.clearError();

		// author name value
		String name = super.authorTextOnList.getText().trim();

		// set chick error to true
		this.chickListViewError = true;

		// is their any book chosen
		if (!this.isThereSelectedBookFromTable()) {
			chickListViewError = false;
			this.listViewPaneBakground(true);
			super.errorListView.setText("you must choose a book");
		} else { // if their is a book chosen

			if (name.length() == 0) { // if name textField empty
				chickListViewError = false;
				this.listViewTextPaneBakground(true);
				super.errorListView.setText("invalid");
			} else if (!ChickMethods.isNameValid(name)) {// if name textField invalid
				chickListViewError = false;
				this.listViewTextPaneBakground(true);
				super.errorListView.setText("invalid");
			} // end else
		} // end else

	}// end chickListViewError

	// chick if book already exist
	private boolean isBookAlreadyExist(String book, String publisher) throws ClassNotFoundException, SQLException {

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

	// search book error
	private boolean searchBookError;

	// chick searchBook error
	private void ChickSearchBookError() {

		// clear error
		this.clearError();

		// set chick error to true
		searchBookError = true;

		// book name
		String bookName = super.searchBookNameTextField.getText().trim();

		// author name
		String publisherName = super.searchPublisherNameTextField.getText().trim();

		// chick if at least one of the TextField is empty
		if (bookName.isEmpty() || publisherName.isEmpty()) {
			searchBookError = false; // set chick error to false
			super.errorSearchLabel.setText("enter the information"); // set text into error label
			this.searchPaneBackground(true); // change the color of background pane to red
		} // end if

	} // end ChickSearchBookError method

	// search book delete and insert error
	private boolean searchBookDeleteInsertError;

	// chick if there is a selected item from result search ListView
	private void chickSearchBookDeleteInsertError() {

		searchBookDeleteInsertError = true; // set the boolean to true

		// chick if there a selected item from the ListView
		if (super.resultSearchListView.getItems().isEmpty() || !this.isThereSelectedItemFormResultSearchListView()) {
			searchBookDeleteInsertError = false; // set the boolean to false
			super.errorSearchLabel.setText("choose book"); // set a text in the error label
			this.searchPaneBackground(true); // change the background color to red
		} // end if
	} // end chickSearchBookDeleteInsertError method

	// is there a selected item from resultSearchListView
	private boolean isThereSelectedItemFormResultSearchListView() {

		// get selected index from ListView
		int index = super.resultSearchListView.getSelectionModel().getSelectedIndex();

		return index >= 0;

	}

	// --------------------------------------------------------------------------------------------//
	// refresh data

	public void refresh() {
		
		this.clear();

		// clear data in ObservableList
		this.data.clear();

		// clear static array list
		allBookInfo.clear();

		// clear all data in table
		super.table.getItems().clear();

		// clear listView items
		super.listView.getItems().clear();

		if (super.isSingleUser) { // if this layout for single user
			// clear ListView data
			super.allBookListView.getItems().clear();
		} // end if

		// insert data from database into ObservableList then add in tableView
		this.insertDataIntoDataBase();
	}

	private void refreshListView(ArrayList<String> list) {

		// clear list view items
		super.listView.getItems().clear();

		// list size
		int size = list.size();

		// adding elements from list into listView
		for (int i = 0; i < size; i++) {
			super.listView.getItems().add(list.get(i));
		} // end for

	}// end refreshListView method

	private void refreshResultSearchListView(ArrayList<String> list) {

		// clear list view items
		super.resultSearchListView.getItems().clear();

		// list size
		int size = list.size();

		// adding elements from list into listView
		for (int i = 0; i < size; i++) {
			super.resultSearchListView.getItems().add(list.get(i));
		} // end for

	} // end refreshResultSearchListView method

	// --------------------------------------------------------------------------------------------//

	// for all book information
	private ObservableList data;

	// insert all information from the database into table
	private void insertDataIntoDataBase() {

		try {
			this.getData();
			this.addDataOnTable();

			if (super.isSingleUser) // if the layout for a single user
				this.insertBookNameInListView(); // add all book names in the ListView

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// get data from the database
	private void getData() throws ClassNotFoundException, SQLException {

		if (!super.isSingleUser) { // information for general book
			this.getDataForAllBookInformation(); // get data for all book information from the database
		} else if (super.isSingleUser) { // book information for single user
			this.getDataForSingleUser(); // get data for single user from the database
		} // end else

	} // end getData method

	// get data for all book information from the database
	private void getDataForAllBookInformation() throws ClassNotFoundException, SQLException {

		this.insertAllBookInfoIntoList();

	}

	private void getDataForSingleUser() throws ClassNotFoundException, SQLException {

		String bookInfo = "";

		if (super.returnedRadioButton.isSelected()) { // if the data are for returned books
			// book information query
			bookInfo = "SELECT BR.borrows_id, B.book_name, B.number_of_copy, B.price, B.borrowing_pricePerDay, B.book_description, B.date_of_publish, P.publisher_name,\r\n"
					+ "       BR.employee_id, BR.date_of_borrow, BR.end_date_of_borrow, BR.return_date, LT.lating_days\r\n"
					+ "FROM book B, publisher P, borrows BR, lating_Day LT\r\n"
					+ "WHERE B.publisher_id = P.publisher_id AND\r\n"
					+ "      B.publisher_id = BR.publisher_id AND \r\n" + "	  B.book_name = BR.book_name AND\r\n"
					+ "      LT.return_date = BR.return_date AND\r\n"
					+ "      LT.end_date_of_borrow = BR.end_date_of_borrow AND\r\n"
					+ "      BR.return_date <> \"\" AND\r\n" + "	  BR.user_id = \"" + this.id + "\";";

		} else if (super.unreturnedRadioButton.isSelected()) { // if the data are for unreturned books
			// book information query
			bookInfo = "SELECT BR.borrows_id, B.book_name, B.number_of_copy, B.price, B.borrowing_pricePerDay, B.book_description, B.date_of_publish, P.publisher_name,\r\n"
					+ "       BR.employee_id, BR.date_of_borrow, BR.end_date_of_borrow, BR.return_date, LT.lating_days\r\n"
					+ "FROM book B, publisher P, borrows BR, lating_Day LT\r\n"
					+ "WHERE B.publisher_id = P.publisher_id AND\r\n"
					+ "      B.publisher_id = BR.publisher_id AND \r\n" + "	  B.book_name = BR.book_name AND\r\n"
					+ "      LT.return_date = BR.return_date AND\r\n"
					+ "      LT.end_date_of_borrow = BR.end_date_of_borrow AND\r\n"
					+ "      BR.return_date = \"\" AND\r\n" + "	  BR.user_id = \"" + this.id + "\";";

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
			Connection tempconnection = this.connectToDatabase.connect();
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
			data.add(new Borrows(Execute.resultSet.getString(1), Execute.resultSet.getString(2),
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

		if (super.isSingleUser) { // if this for single user

			super.name.setCellValueFactory(new PropertyValueFactory<Borrows, String>("name"));
			super.numberOfCopy.setCellValueFactory(new PropertyValueFactory<Borrows, String>("numberOfCopy"));
			super.price.setCellValueFactory(new PropertyValueFactory<Borrows, String>("price"));
			super.borrowingPricePerDay
					.setCellValueFactory(new PropertyValueFactory<Borrows, String>("borrowingPricePerDay"));
			super.description.setCellValueFactory(new PropertyValueFactory<Borrows, String>("description"));
			super.dateOfPublish.setCellValueFactory(new PropertyValueFactory<Borrows, String>("dateOfPublish"));
			super.publisherName.setCellValueFactory(new PropertyValueFactory<Borrows, String>("publisherName"));
			super.employeeId.setCellValueFactory(new PropertyValueFactory<Borrows, String>("employeeId"));
			super.dateOfBorrows.setCellValueFactory(new PropertyValueFactory<Borrows, String>("dateOfBorrow"));
			super.endDateOfBorrows.setCellValueFactory(new PropertyValueFactory<Borrows, String>("endDateOfBorrow"));

			super.returnDate.setCellValueFactory(new PropertyValueFactory<Borrows, String>("returnDate"));
			super.latingDay.setCellValueFactory(new PropertyValueFactory<Borrows, String>("lateDays"));

			if (super.unreturnedRadioButton.isSelected()) { // if the data for unreturned books
				super.borrowingDates.getColumns().remove(super.returnDate);
				super.borrowingDates.getColumns().remove(super.latingDay);

			} else if (super.returnedRadioButton.isSelected()) { // if the data for returned books
				super.borrowingDates.getColumns().remove(super.returnDate);
				super.borrowingDates.getColumns().remove(super.latingDay);

				super.borrowingDates.getColumns().addAll(super.returnDate, super.latingDay);
			}

			super.table.setItems(data);

		} else { // if this for all book information

			super.name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
			super.numberOfCopy.setCellValueFactory(new PropertyValueFactory<Book, String>("numberOfCopy"));
			super.price.setCellValueFactory(new PropertyValueFactory<Book, String>("price"));
			super.borrowingPricePerDay
					.setCellValueFactory(new PropertyValueFactory<Book, String>("borrowingPricePerDay"));
			super.description.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));
			super.dateOfPublish.setCellValueFactory(new PropertyValueFactory<Book, String>("dateOfPublish"));
			super.publisherName.setCellValueFactory(new PropertyValueFactory<Book, String>("publisherName"));

			super.table.setItems(data);
		} // end else

	} // end addDataOnTable method

	// array List for all book info
	private final static ArrayList<Book> allBookInfo = new ArrayList<Book>();

	// insert book name into listView
	private void insertBookNameInListView() throws ClassNotFoundException, SQLException {

		// insert all book information into ArrayList
		this.insertAllBookInfoIntoList();

		// insert book and publisher names into ListView
		this.insertFromArrayListIntoListView();

	} // end insertBookNameInListView method

	// insert book name and publisher name from arrayList of all book information
	// into the ListView
	private void insertFromArrayListIntoListView() {

		// ArrayList size
		int size = allBookInfo.size();

		// add all elements into ListView
		for (int i = 0; i < size; i++) {

			// temporary book for each element in the arrayList
			Book tempBook = allBookInfo.get(i);

			// insert into ListView
			super.allBookListView.getItems()
					.add("book: " + tempBook.getName() + ",publisher: " + tempBook.getPublisherName());

		} // end for

	} // end insertFromArrayListIntoListView method

	// insert all book info in ObservableList for all book info or ArrayList for
	// single user
	private void insertAllBookInfoIntoList() throws SQLException, ClassNotFoundException {

		// is the static ArrayList empty before insertion
		boolean isStaticArrayListEmpty = allBookInfo.isEmpty();

		// chick if this layout for single user and the static ArrayList doesn't empty
		if (super.isSingleUser && !isStaticArrayListEmpty) {
			// if the ArrayList not empty then we don't need to fill it
		} else {

			// query to get data from database

			// book information query
			String bookInfo = " SELECT B.book_name, B.number_of_copy, B.price, B.borrowing_pricePerDay, B.book_description, B.date_of_publish, P.publisher_name\r\n"
					+ " FROM book B, publisher P\r\n" + " WHERE B.publisher_id = P.publisher_id;";

			// author information query
			String authorInfo = "";

			// execute
			Execute.setResultSELECTQuery(bookInfo);

			// result of SELECT from database
			while (Execute.resultSet.next()) {

				// author names ArrayList
				ArrayList<String> authorNames = new ArrayList<String>();

				String bookName = Execute.resultSet.getString(1);
				String publisherName = Execute.resultSet.getString(7);

				authorInfo = "SELECT DISTINCT author_name FROM book_to_auther BTA, author A, publisher P\r\n"
						+ "WHERE BTA.author_id = A.author_id AND\r\n"
						+ "      BTA.publisher_id = P.publisher_id AND\r\n" + "      BTA.book_name = \"" + bookName
						+ "\" AND\r\n" + "	  P.publisher_name = \"" + publisherName + "\";";

				// temporary connection
				Connection tempconnection = this.connectToDatabase.connect();
				Statement tempStatement = tempconnection.createStatement();
				ResultSet tempResult = tempStatement.executeQuery(authorInfo);

				// add author names into ArrayList
				while (tempResult.next()) {

					authorNames.add(tempResult.getString(1));
				} // end while

				// close temporary connections
				tempconnection.close();
				tempStatement.close();
				tempResult.close();

				if (super.isSingleUser) { // if this layout for a single user

					// insert into allBookInfo arrayList
					allBookInfo.add(new Book(Execute.resultSet.getString(1), (Execute.resultSet.getString(2)),
							(Execute.resultSet.getString(3)), (Execute.resultSet.getString(4)),
							Execute.resultSet.getString(5), Execute.resultSet.getString(6),
							Execute.resultSet.getString(7), authorNames));

				} else { // if this layout for the all book information

					// insert into data ObservableList
					data.add(new Book(Execute.resultSet.getString(1), (Execute.resultSet.getString(2)),
							(Execute.resultSet.getString(3)), (Execute.resultSet.getString(4)),
							Execute.resultSet.getString(5), Execute.resultSet.getString(6),
							Execute.resultSet.getString(7), authorNames));

					if (isStaticArrayListEmpty) // if the static ArrayList empty then insert informations into it
						allBookInfo.add( // add into allBookInfo static ArrayList
								new Book(Execute.resultSet.getString(1), (Execute.resultSet.getString(2)),
										(Execute.resultSet.getString(3)), (Execute.resultSet.getString(4)),
										Execute.resultSet.getString(5), Execute.resultSet.getString(6),
										Execute.resultSet.getString(7), authorNames));

				} // end else statement

			} // end while

			// close connection
			Execute.close();
		} // end else

	} // end insertAllBookInfoIntoList method

	// --------------------------------------------------------------------------------------------//
	// insert

	// insert book
	private void insertBook() throws ClassNotFoundException, SQLException {

		if (super.isSingleUser) {// if the reference is for a single user

			this.insertBookForSingleUser(); // inert for a single user (Borrowing book)

		} else {// if the reference for all book information (insert new book into library)

			this.chickAddBookError(); // chick error

			if (this.chickAddBookError) { // if there is no error

				this.insertBookForAllInformation();// insert for all information

			} // end if

		} // end else

	} // end insertBook method

	// ----------------------------------------//
	// all book information

	private void insertBookForAllInformation() throws ClassNotFoundException, SQLException {

		String bookName = super.textFields[0].getText().trim(); // book name
		int numOfCopies = Integer.parseInt(super.textFields[1].getText().trim()); // number of copies
		double price = Double.parseDouble(super.textFields[2].getText().trim()); // price
		double borrowingPrice = Double.parseDouble(super.textFields[3].getText().trim()); // borrowing price
		String description = super.textFields[4].getText().trim(); // description
		String dateOfPublish = super.dateOfPublishDatePicker.getValue()
				.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // date of publish
		String publisherName = super.textFields[6].getText().trim(); // publisher name

		// authors name
		String[] authorNames = ChickMethods.ObservableListToArrayOfString(super.authorsName.getItems());

		// book
		Book bookIserted = new Book(bookName, numOfCopies + "", price + "", borrowingPrice + "", description,
				dateOfPublish, publisherName, ChickMethods.getArrayListFromArray(authorNames));

		// insert the book
		this.insertBookIntoDataBase(bookIserted);

	} // end insertBookForAllInformation method

	// insert book into database
	private void insertBookIntoDataBase(Book book) throws ClassNotFoundException, SQLException {

		// this method get publisherId if exist
		// and if not it will insert the publisher into database then get its Id
		int publisherId = this.getPublisherId(book.getPublisherName());

		// insert into book table query
		String insertBookQuery = "INSERT INTO book VALUES (\r\n" + "            \"" + book.getName() + "\",\r\n"
				+ "            " + book.getNumberOfCopy() + ",\r\n" + "            " + book.getPrice() + ",\r\n"
				+ "            " + book.getBorrowingPricePerDay() + ",\r\n" + "            \"" + book.getDescription()
				+ "\",\r\n" + "            \"" + book.getDateOfPublish() + "\",\r\n" + "            " + publisherId
				+ "); ";

		// execute
		Execute.executeQuery(insertBookQuery);

		// insert into book_to_author table
		this.insrtAuthor_bookIdIntoDatabase(ChickMethods.ArrayListToArrayOfString(book.getAuthorName()), book.getName(),
				publisherId);

		// refresh data
		this.refresh();

		// clear
		this.clear();

	} // end insertBookIntoDataBase method

	// get publisher id (If exist get id. If not add them then get id)
	private int getPublisherId(String publisherName) throws ClassNotFoundException, SQLException {

		// is publisher exist boolean
		boolean isPublisherExist = this.isPublisherExist(publisherName);

		// publisher id
		int publisherId = 0;

		if (isPublisherExist) { // if publisher exist

			publisherId = Integer.parseInt(Execute.resultSet.getString(1));

			// close
			Execute.close();

			return publisherId;

		} else { // if publisher doesn't exist

			// close
			Execute.close();

			// insert publisher query
			String insertPublisherQuery = "INSERT INTO publisher(publisher_name) VALUES (\"" + publisherName + "\");";

			// execute
			Execute.executeQuery(insertPublisherQuery);

			// close
			Execute.close();
			return getPublisherId(publisherName);

		} // end else
	} // end getPublisherId

	// chick if publisher exist
	private boolean isPublisherExist(String publisherName) throws ClassNotFoundException, SQLException {

		// query
		String publisherQuery = "SELECT * FROM publisher WHERE publisher_name = \"" + publisherName + "\";";

		// execute
		Execute.setResultSELECTQuery(publisherQuery);

		return Execute.resultSet.next();

	}

	// insert authors into database
	// if author exist it will add its Id into array to return it
	// if not it will insert it into database then add the it in array to return it
	private int[] insertAuthorsIntoDatabase(String[] authors)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// size
		int size = authors.length;

		// ArrayList
		int[] authorsId = new int[size];

		// for each author
		for (int i = 0; i < size; i++) {
			authorsId[i] = this.getAuthorId(authors[i]);
		} // end for

		return authorsId;

	}

	// get author id (If exist get id. If not add them then get id)
	private int getAuthorId(String author) throws NumberFormatException, SQLException, ClassNotFoundException {

		// is author exist boolean
		boolean isAuthorExist = this.isAuthorExist(author);

		if (isAuthorExist) {

			// id
			int id = Integer.parseInt(Execute.resultSet.getString(1));

			// close
			Execute.close();

			return id;

		} else {

			// close
			Execute.close();

			// query
			String query = "INSERT INTO author (author_name) VALUES (\"" + author + "\");";

			// execute
			Execute.executeQuery(query);

			return getAuthorId(author);

		} // end else

	} // end getAuthorId method

	// is author exist
	private boolean isAuthorExist(String author) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT author_id FROM author WHERE author_name = \"" + author + "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		return Execute.resultSet.next();

	}

	// insert authors_book id into book_to_author table
	private void insrtAuthor_bookIdIntoDatabase(String[] authors, String bookName, int publisherId)
			throws ClassNotFoundException, SQLException {

		// author id
		int[] authorsId = this.insertAuthorsIntoDatabase(authors);

		// size
		int size = authorsId.length;

		// query
		String query = "";

		// for each author id
		for (int i = 0; i < size; i++) {

			// insert query
			query = "INSERT INTO book_to_auther VALUES (\"" + bookName + "\",\r\n" + publisherId + ",\r\n"
					+ authorsId[i] + ");";

			// execute
			Execute.executeQuery(query);

		} // end for

	} // end insrtAuthor_bookIdIntoDatabase

	// insert author
	private void insertAuthor() {

		// chick error of author name
		this.chickAuthorNameError();

		if (this.chickAuthorNameError) { // if there is no error in author name

			String authorName = super.textFields[7].getText().trim(); // author's name

			// chick if the author's name is already exist
			if (!ChickMethods.isItemExistInObservableList(super.authorsName.getItems(), authorName)) {
				super.authorsName.getItems().add(authorName); // add name into ListView
				super.textFields[7].clear(); // clear textField
			} else {
				super.errorsOption[7].setText("Name already exist");
			}

		} // end if

	} // end insertAuthor method

	// remove author from listView
	private void removeAuthorFromListView() {

		if (super.authorsName.getItems().isEmpty()) { // if the ListView is empty
			super.errorsOption[7].setText("there is no names");
		} else { // if the ListView is not empty

			// selected index
			int selectedIndex = super.authorsName.getSelectionModel().getSelectedIndex();

			if (selectedIndex >= 0) { // if there is a selected item

				// clear authorsName error
				super.errorsOption[7].setText("");

				// remove
				super.authorsName.getItems().remove(selectedIndex);
			} else { // there is no selected item
				super.errorsOption[7].setText("select item to remove");
			} // end else
		} // end else

	} // end removeAuthorFromListView

	// insert author into book
	private void insertAuthorIntoBook() throws NumberFormatException, ClassNotFoundException, SQLException {

		// clear error
		this.clearError();

		// chick listView error
		this.chickListViewError();

		if (this.chickListViewError) { // if author name is valid

			// selected book from table
			Book book = (Book) super.table.getSelectionModel().getSelectedItem();

			// written author name
			String authorName = super.authorTextOnList.getText();

			// chick if the author already exist in the list of authors of the book
			if (ChickMethods.isItemExistInArrayList(book.getAuthorName(), authorName)) {
				super.errorListView.setText("author name already exist");

			} else { // if author doesn't exist in the list

				// return author id if exist and if not insert it then get id
				int authorId = this.getAuthorId(authorName);

				// insert author name into ListView and book class
				this.insertAuthorIntoListViewAndBook(authorName, book, authorId);

				// insert author name into book_to_author Table in the database
				this.insertAuthorInBookToAuthorTable(book, authorId);

			} // end else

			super.authorTextOnList.clear();
		} // end if
	} // end insertAuthorIntoBook method

	// insert author name into ListView and book class
	private void insertAuthorIntoListViewAndBook(String authorName, Book book, int authorId)
			throws NumberFormatException, ClassNotFoundException, SQLException {

		// insert name into ArrayList in the book class
		book.addAuthorName(authorName);

		// refresh ListView information
		this.refreshListView(book.getAuthorName());

	} // end insertAuthorIntoListViewAndBook method

	// insert author name into book_to_author Table in the database
	private void insertAuthorInBookToAuthorTable(Book book, int authorId) throws ClassNotFoundException, SQLException {

		// inserting query
		String query = "INSERT INTO book_to_auther VALUES (\"" + book.getName() + "\",\r\n"
				+ this.getPublisherId(book.getPublisherName()) + ",\r\n" + authorId + ");";

		// execute
		Execute.executeQuery(query);
	} // end insertAuthorInBookToAuthorTable method

	// --------------------------------------------------------//
	// single user
	private void insertBookForSingleUser() throws ClassNotFoundException, SQLException {

		// chick if there is selected book from ListView
		if (this.isThereIsSelectedBookFromList()) {

			Book selectedBook = allBookInfo.get(super.allBookListView.getSelectionModel().getSelectedIndex());

			// borrow book
			this.insertBookForSingleUser(selectedBook, this.id, this.employeeId, 30);

		} else { // there is no selected book from list
			super.errorSelectBookLabel.setText("Select book");

		} // end else statement

	}// end insertBookForSingleUser

	// insert book from single user
	private void insertBookForSingleUser(Book book, String userId, String empId, int numOfDays)
			throws ClassNotFoundException, SQLException {

		Borrows borrows = this.getBorrows(book, 30); // borrows information

		// insert into database

		// --------------------------------------------//
		// late date insert

		// insert into late date table query
		String insertLatingDateQuery = "INSERT INTO lating_day VALUES(\"" + borrows.getEndDateOfBorrow() + "\",\r\n"
				+ "                               \"" + borrows.getReturnDate() + "\",\r\n"
				+ ChickMethods.differenceDaysBetweenTwoDates(borrows.getEndDateOfBorrow(), borrows.getReturnDate())
				+ ");";

		// exception may appear if the inserted information were exist in lating_day
		// table
		try {
			Execute.executeQuery(insertLatingDateQuery); // execute
		} catch (ClassNotFoundException | SQLException e) {
		}

		// -------------------------------------------//
		// borrows insert

		// insert into borrows table query
		String insertBorrowsQuery = "INSERT INTO borrows(user_id, book_name, publisher_id, employee_id, date_of_borrow, end_date_of_borrow, return_date )\r\n"
				+ " VALUES(\r\n" + "         \"" + userId + "\"\r\n" + "         ,\r\n" + "         \""
				+ borrows.getName() + "\"\r\n" + "         ,\r\n" + this.getPublisherId(borrows.getPublisherName())
				+ "\r\n" + "         ,\r\n" + "         \"" + empId + "\"\r\n" + "         ,\r\n" + "         \""
				+ borrows.getDateOfBorrow() + "\"\r\n" + "         ,\r\n" + "         \"" + borrows.getEndDateOfBorrow()
				+ "\"\r\n" + "         ,\r\n" + "         \"" + borrows.getReturnDate() + "\"\r\n" + "         );";

		// execute
		Execute.executeQuery(insertBorrowsQuery);

		// change copies
		this.changeBookCopies(borrows.getName(), this.getPublisherId(borrows.getPublisherName()), -1);

		// refresh data
		this.refresh();

	} // end insertBookForSingleUser method

	// chick if there is any selection from the ListView
	private boolean isThereIsSelectedBookFromList() {

		// selection index from listView
		int selectionIndex = super.allBookListView.getSelectionModel().getSelectedIndex();

		return selectionIndex >= 0;
	} // end isThereIsSelectedBookFromList

	// get authors name of some book
	private Borrows getBorrows(Book book, int days) throws ClassNotFoundException, SQLException {

		String employeeId = this.employeeId; // employee id
		String dateOfBorrow = ChickMethods.getNowDate(); // date of borrow
		String endDateOfBorrow = ChickMethods.incrementDate(dateOfBorrow, days); // end date of borrow
		String returningDate = ""; // return date
		int lateDays = 0; // late days

		return new Borrows(book.getName(), book.getNumberOfCopy(), book.getPrice(), book.getBorrowingPricePerDay(),
				book.getDescription(), book.getDateOfPublish(), book.getPublisherName(), book.getAuthorName(),
				employeeId, dateOfBorrow, endDateOfBorrow, returningDate, lateDays + "");

	} // end getBorrows method

	// --------------------------------------------------------------------------------------------//
	// delete

	// delete book
	private void deleteBook() throws ClassNotFoundException, SQLException {

		if (super.isSingleUser) {// if layout is for a single user

			// chick if the data on the table are for the unreturned borrows books
			if (super.unreturnedRadioButton.isSelected())
				this.deleteBookSingleUser();

		} else { // if the layout is for all books

			// delete book
			this.deleteBookAllInfo();

		} // end else

		// refresh
		this.refresh();

	} // end delete method

	// ----------------------------------//
	// all book information

	private void deleteBookAllInfo() {

		// selected items
		ObservableList<Book> selectedRows = super.table.getSelectionModel().getSelectedItems();
		ArrayList<Book> rows = new ArrayList<>(selectedRows);

		// for each of the selected items
		rows.forEach(row -> {

			// remove from table
			super.table.getItems().remove(row);

			try {
				// delete from database
				deleteBookRow(row);
				deleteUnusableAuthors(row);
				deleteUnusablePublisher(row);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			table.refresh(); // refresh table
		});

		// clear items in ListView
		super.listView.getItems().clear();

	}

	// delete book row
	private void deleteBookRow(Book book) throws ClassNotFoundException, SQLException {

		// delete query
		String query = "DELETE FROM book\r\n" + "WHERE book_name = \"" + book.getName() + "\" AND\r\n"
				+ "      publisher_id = " + this.getPublisherId(book.getPublisherName()) + ";";

		// execute query
		Execute.executeQuery(query);
	}

	// delete unusable authors from a Book object
	private void deleteUnusableAuthors(Book book) throws ClassNotFoundException, SQLException {

		// authors name
		ArrayList<String> authorsName = book.getAuthorName();

		// size
		int size = authorsName.size();

		// for each author
		for (int i = 0; i < size; i++) {

			this.deleteUnusableAuthors(authorsName.get(i));

		} // end for

	}// deleteUnusableAuthors

	// delete an unusable author
	private void deleteUnusableAuthors(String authorName) throws ClassNotFoundException, SQLException {

		// author name
		String author = authorName;

		if (!isAuthorUsable(author)) { // if author unusable

			// delete query
			String deleteAuthorQuery = "DELETE FROM author WHERE author_name = \"" + author + "\";";

			// execute
			Execute.executeQuery(deleteAuthorQuery);

			// close
			Execute.close();

		} // end if

	} // end deleteUnusableAuthors method

	// chick if author usable
	private boolean isAuthorUsable(String author) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT DISTINCT BTA.book_name \r\n" + "FROM book_to_auther BTA, author A\r\n"
				+ "WHERE A.author_id = BTA.author_id AND\r\n" + "      A.author_name = \"" + author + "\"; ";

		// execute
		Execute.setResultSELECTQuery(query);

		// is usable
		boolean isUsable = Execute.resultSet.next();

		// close
		Execute.close();

		return isUsable;

	}

	// delete unusable publisher
	private void deleteUnusablePublisher(Book book) throws ClassNotFoundException, SQLException {

		// publisher name
		String publisher = book.getPublisherName();

		if (!this.isPublisherUsable(publisher)) { // if publisher unusable

			// delete publisher query
			String query = "DELETE FROM publisher WHERE publisher_name = \"" + publisher + "\";";

			// execute
			Execute.executeQuery(query);

		} // end

	}// end deleteUnusablePublisher

	// chick if publisher usable
	private boolean isPublisherUsable(String publisher) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT B.book_name\r\n" + "FROM publisher P, book B\r\n"
				+ "WHERE P.publisher_id = B.publisher_id AND\r\n" + "	  P.publisher_name = \"" + publisher + "\"; ";

		// execute
		Execute.setResultSELECTQuery(query);

		// is publisher usable boolean
		boolean isUsable = Execute.resultSet.next();

		// close
		Execute.close();

		return isUsable;

	}// end isPublisherUsable

	// ------------------------------------//
	// single user

	private void deleteBookSingleUser() {

		// selected items ObservableList
		ObservableList<Borrows> selectedRows = super.table.getSelectionModel().getSelectedItems();

		// selected items ArrayList
		ArrayList<Borrows> rows = new ArrayList<>(selectedRows);

		// for each selected item
		rows.forEach(row -> {
			super.table.getItems().remove(row);
			try {

				// delete from borrows
				deleteBorrowsRow_DatabaseUpdating(row);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

			table.refresh();
		});
	} // end deleteBookSingleUser

	// UPDATE on borrows TABLE return date and late days in the database
	private void deleteBorrowsRow_DatabaseUpdating(Borrows borrow) throws ClassNotFoundException, SQLException {

		// update in the lating_day table
		this.updateOnLatingDay(borrow);

		// insert into borrows_financial table
		this.insertIntoBorrowsFinancialTable(borrow);

		// change copies
		this.changeBookCopies(borrow.getName(), this.getPublisherId(borrow.getPublisherName()), 1);

	} // end deleteBorrowsRowDatabaseUpdating method

	// update on lating_day table in the database
	private void updateOnLatingDay(Borrows borrow) throws ClassNotFoundException, SQLException {

		// chick if the dates are for a unique borrow
		if (this.isBorrowsTuplesForUnique(borrow)) {

			this.latingDayUpdating(borrow);

		} else { // if there is more than one borrow for the same dates

			this.latingDayInserting(borrow);

		} // end else

	} // end updateOnLatingDay method

	// chick if the late date tuples for unique borrows
	private boolean isBorrowsTuplesForUnique(Borrows borrow) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT * FROM borrows B\r\n" + "WHERE B.return_date = \"" + borrow.getReturnDate()
				+ "\" AND \r\n" + "      B.end_date_of_borrow = \"" + borrow.getEndDateOfBorrow() + "\" AND\r\n"
				+ "      B.borrows_id <> " + borrow.getId() + ";";

		// execute
		Execute.setResultSELECTQuery(query);

		// isBorrowsTuplesForUnique boolean
		boolean isBorrowsTuplesForUnique = !Execute.resultSet.next();

		// close
		Execute.close();

		return isBorrowsTuplesForUnique;

	} // end isBorrowsTuplesForUnique method

	// updating
	private void latingDayUpdating(Borrows borrow) throws ClassNotFoundException, SQLException {

		// today date
		String nowDate = ChickMethods.getNowDate();

		// update query
		String query = "UPDATE lating_Day \r\n" + "SET return_date = \"" + nowDate + "\",\r\n" + "    lating_days = "
				+ ChickMethods.differenceDaysBetweenTwoDates(borrow.getEndDateOfBorrow(), nowDate) + "\r\n"
				+ "WHERE end_date_of_borrow = \"" + borrow.getEndDateOfBorrow() + "\" AND\r\n"
				+ "      return_date = \"" + borrow.getReturnDate() + "\"; ";

		// exception may appear if the updating new values were exist before
		try {
			Execute.executeQuery(query); // execute
		} catch (ClassNotFoundException | SQLException e) {

			// update on borrows Table in the database
			this.borrowsUpdate(borrow);

			// delete unusable latingDay information from lating_day table in the database
			this.latingDayDeleting(borrow);
		}

	}

	// inserting
	private void latingDayInserting(Borrows borrow) throws ClassNotFoundException, SQLException {

		// today date (return date)
		String nowDate = ChickMethods.getNowDate();

		// end date of borrow
		String endDateOfBorrow = borrow.getEndDateOfBorrow();

		// insert query
		String insertQuery = "INSERT INTO lating_day VALUES(\"" + endDateOfBorrow + "\", \"" + nowDate + "\","
				+ ChickMethods.differenceDaysBetweenTwoDates(endDateOfBorrow, nowDate) + ");";

		// change return date
		borrow.setReturnDate(nowDate);

		// exception may appear if the insertion values exist before
		try {
			Execute.executeQuery(insertQuery); // execute
		} catch (ClassNotFoundException | SQLException e) {
		}

		// -------------------------------------------------------------//
		// update into borrows
		this.borrowsUpdate(borrow);

	} // end latingDayInserting method

	// borrows updating
	private void borrowsUpdate(Borrows borrow) throws ClassNotFoundException, SQLException {

		// today date
		String nowDate = ChickMethods.getNowDate();

		// query
		String query = "UPDATE borrows \r\n" + " SET return_date = \"" + nowDate + "\"\r\n" + " WHERE borrows_id = "
				+ borrow.getId() + ";";

		// execute
		Execute.executeQuery(query);

	} // end borrowsUpdate method

	// deleting
	private void latingDayDeleting(Borrows borrow) throws ClassNotFoundException, SQLException {

		// query
		String query = "DELETE FROM lating_Day\r\n" + "WHERE end_date_of_borrow = \"" + borrow.getEndDateOfBorrow()
				+ "\" AND\r\n" + "      return_date = \"" + borrow.getReturnDate() + "\";";

		try {
			Execute.executeQuery(query); // execute
		} catch (ClassNotFoundException | SQLException e) {
		}

	}

	// insert into borrows_financial table
	private void insertIntoBorrowsFinancialTable(Borrows borrow) throws ClassNotFoundException, SQLException {

		// today date (return date)
		String nowDate = ChickMethods.getNowDate();

		int latingDate = ChickMethods.differenceDaysBetweenTwoDates(borrow.getEndDateOfBorrow(), nowDate);

		// if there is late days
		if (latingDate > 0) {

			// financial
			double financial = Double.parseDouble(borrow.getBorrowingPricePerDay()) * latingDate;

			// borrows_financial table insert
			String query = "INSERT INTO borrowing_financial (book_name, publisher_name, user_id, adding_date, financial)\r\n"
					+ "VALUES(\"" + borrow.getName() + "\", \"" + borrow.getPublisherName() + "\", \"" + this.id
					+ "\", \"" + nowDate + "\", " + financial + ");";

			// execute
			Execute.executeQuery(query);

			// add fees into member table
			this.addFeesToMember(this.id, financial);
		} // end if

	} // end insertIntoBorrowsFinancialTable method

	// chick if user id is for a member id
	private boolean isIdForMember(String id) throws ClassNotFoundException, SQLException {

		// query
		String query = "SELECT * FROM member WHERE user_id = \"" + id + "\"; ";

		// execute
		Execute.setResultSELECTQuery(query);

		// is member boolean
		boolean isMember = Execute.resultSet.next();

		// close
		Execute.close();

		return isMember;

	}

	// add fees to member
	private void addFeesToMember(String memberId, double fees) throws ClassNotFoundException, SQLException {

		if (this.isIdForMember(memberId)) { // if the id for member

			// query
			String query = "UPDATE member\r\n" + "SET fees = fees + " + fees + "\r\n" + "WHERE user_id = \"" + memberId
					+ "\";";

			// execute
			Execute.executeQuery(query);

		} // end if

	} // end addFeesToMember method

	// delete author name
	private void deleteAuthorName() throws ClassNotFoundException, SQLException {

		// clear error
		this.clearError();

		// chick if there is selected book from table
		if (this.isThereSelectedBookFromTable()) {

			// selected book from table
			Book book = (Book) super.table.getSelectionModel().getSelectedItem();

			// chick if there is a selected element from ListView
			if (this.isThereChosenAuthorFormListView()) {

				// selected author
				String authorName = super.listView.getSelectionModel().getSelectedItem();

				// delete author from ListView and book object
				this.deleteAuthorFromListViewAndBook(book, authorName);

				// delete author from database
				this.deleteAuthorFromDatabase(book, authorName);

			} else { // if there is no selected element from listView
				super.errorListView.setText("choose author to delete");
			}

		} else { // if there is no book selected from list
			super.errorListView.setText("choose book");
		} // end else

	}

	// chick if there is a selected book from table
	private boolean isThereSelectedBookFromTable() {

		// selected index
		int index = super.table.getSelectionModel().getSelectedIndex();

		return index >= 0;

	} // end isThereSelectedBookFromTable method

	// delete author from ListView and book object
	private void deleteAuthorFromListViewAndBook(Book book, String authorName) {

		// delete from book
		book.deleteAuthorName(authorName);

		// refresh ListView
		this.refreshListView(book.getAuthorName());

	} // end deleteAuthorFromListViewAndBook method

	// chick if there is chosen author from ListView
	private boolean isThereChosenAuthorFormListView() {

		// chosen index
		int index = super.listView.getSelectionModel().getSelectedIndex();

		return index >= 0;

	} // end isThereChosenAuthorFormListView method

	// delete author from database
	private void deleteAuthorFromDatabase(Book book, String authorName) throws ClassNotFoundException, SQLException {

		// delete from book_to_author table query
		String query = "DELETE FROM book_to_auther\r\n" + "WHERE book_name = \"" + book.getName() + "\" AND\r\n"
				+ "      publisher_id = " + this.getPublisherId(book.getPublisherName()) + " AND\r\n"
				+ "      author_id = " + this.getAuthorId(authorName) + ";";

		// execute
		Execute.executeQuery(query);

		// delete unusable author
		this.deleteUnusableAuthors(authorName);

	}

	// -----------------------------------------------------------//
	// change number of copies for book

	private void changeBookCopies(String bookName, int publisherId, int copies)
			throws ClassNotFoundException, SQLException {

		// query
		String query = "UPDATE book\r\n" + "SET number_of_copy = number_of_copy + " + copies + "\r\n"
				+ "WHERE book_name = \"" + bookName + "\" AND publisher_id = " + publisherId + ";";

		// execute
		Execute.executeQuery(query);

	} // end changeBookCopies

	// ----------------------------------------------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------------------------------------------//
	// search book

	// result search book ArrayList
	private final ArrayList<Book> resultSearchArrayList = new ArrayList<Book>();

	// search
	private void searchBook() throws ClassNotFoundException, SQLException {

		// clear search ListView result
		super.resultSearchListView.getItems().clear();

		// clear error
		this.clearError();

		// chick error
		this.ChickSearchBookError();

		// if there is no error in the inserted data
		if (this.searchBookError) {

			// book name
			String bookName = super.searchBookNameTextField.getText().trim();

			// publisher name
			String publisherName = super.searchPublisherNameTextField.getText().trim();

			// get all result
			this.getBookInfoFromDatabase(bookName, publisherName);

			if (this.isBookExist) { // if there are results exist in the database

				// array size
				int size = resultSearchArrayList.size();

				// add result into ListView
				for (int i = 0; i < size; super.resultSearchListView.getItems()
						.add(resultSearchArrayList.get(i++).toString()))
					;

			} else {
				super.resultSearchListView.getItems().add("No result");
			} // end else

		} else {
			super.errorSearchLabel.setText("invalid");
		} // end else

	} // end searchBook() method

	private boolean isBookExist;

	// get book information from database
	private void getBookInfoFromDatabase(String book, String publisher) throws ClassNotFoundException, SQLException {

		// clear result search ArrayList
		this.resultSearchArrayList.clear();

		// set isBookExist into false
		this.isBookExist = false;

		// select from book and publisher tables query
		String bookQuery = " SELECT B.book_name, B.number_of_copy, B.price, B.borrowing_pricePerDay, B.book_description, B.date_of_publish, P.publisher_name\r\n"
				+ " FROM book B, publisher P\r\n" + " WHERE B.publisher_id = P.publisher_id AND\r\n"
				+ "       B.book_name LIKE \"%" + book + "%\" AND\r\n" + "       P.publisher_name LIKE \"%" + publisher
				+ "%\";";

		// execute
		Execute.setResultSELECTQuery(bookQuery);

		// for each book
		while (Execute.resultSet.next()) {

			// set isBookExist to true
			this.isBookExist = true;

			ArrayList<String> authors = new ArrayList<String>();

			// author query
			String authorsQuery = "SELECT DISTINCT author_name FROM book_to_auther BTA, author A, publisher P\r\n"
					+ "WHERE BTA.author_id = A.author_id AND\r\n" + "      BTA.publisher_id = P.publisher_id AND\r\n"
					+ "      BTA.book_name = \"" + Execute.resultSet.getString(1) + "\" AND\r\n"
					+ "	  P.publisher_name = \"" + Execute.resultSet.getString(7) + "\";";

			// temporary connection to database
			Connection tempconnection = this.connectToDatabase.connect();
			Statement tempStatement = tempconnection.createStatement();
			ResultSet tempResult = tempStatement.executeQuery(authorsQuery);

			// for each author's name
			while (tempResult.next()) {

				authors.add(tempResult.getString(1));
			} // end while

			// add to bookResult ArrayList
			resultSearchArrayList.add(new Book(Execute.resultSet.getString(1), (Execute.resultSet.getString(2)),
					(Execute.resultSet.getString(3)), (Execute.resultSet.getString(4)), Execute.resultSet.getString(5),
					Execute.resultSet.getString(6), Execute.resultSet.getString(7), authors));

			// close temporary connections
			tempconnection.close();
			tempStatement.close();
			tempResult.close();

		} // end while

		// close
		Execute.close();

	} // end getBookInfoFromDatabase() method

	// add book from search
	private void addBookFromResultSearch() throws ClassNotFoundException, SQLException {

		// clear error
		this.clearError();

		// chick error
		this.chickSearchBookDeleteInsertError();

		if (this.searchBookDeleteInsertError) { // if there is no error

			// selected index
			int index = super.resultSearchListView.getSelectionModel().getSelectedIndex();

			// borrow the book
			this.insertBookForSingleUser(this.resultSearchArrayList.get(index), this.id, this.employeeId, 30);

			// refresh resultSearchListView
			this.refreshResultSearchListView(this.getToStringListFromBookList(this.resultSearchArrayList));

		} else { // if there is an error (there is no selected book from ListView)
			super.errorSearchLabel.setText("choos book"); // change error label text
		} // end else

	} // end addBookFromResultSearch method

	// delete book from search
	private void deleteBookFromResultSearch() throws ClassNotFoundException, SQLException {

		// clear error
		this.clearError();

		// chick error
		this.chickSearchBookDeleteInsertError();

		if (this.searchBookDeleteInsertError) { // if there is no error

			// selected index
			int index = super.resultSearchListView.getSelectionModel().getSelectedIndex();

			// deleted book
			Book deletedBook = this.resultSearchArrayList.get(index);

			/* delete book */
			// delete from database
			deleteBookRow(deletedBook);
			deleteUnusableAuthors(deletedBook);
			deleteUnusablePublisher(deletedBook);

			// delete from ArrayList
			this.resultSearchArrayList.remove(deletedBook);

			// refresh resultSearchListView
			this.refreshResultSearchListView(this.getToStringListFromBookList(this.resultSearchArrayList));

		} else { // if there is an error (there is no selected book from ListView)
			super.errorSearchLabel.setText("choos book"); // change error label text
		} // end else

	}

	// get an ArrayList of string from ArrayList of book (toString of each book)
	private ArrayList<String> getToStringListFromBookList(ArrayList<Book> list) {

		// ArrayList
		ArrayList<String> stringList = new ArrayList<String>();

		// size
		int size = list.size();

		// for each book in the list
		for (int i = 0; i < size; i++) {
			stringList.add(list.get(i).toString());
		} // end for

		return stringList;

	} // end getToStringListFromBookList method

	// ----------------------------------------------------------------------------------------------------------------------//
	// ----------------------------------------------------------------------------------------------------------------------//
	// update

	// book string edit commit
	private final BookStringEditCommit bookStringEditCommit = new BookStringEditCommit(super.name, super.description,
			super.publisherName, super.dateOfPublish, super.table, super.price, super.borrowingPricePerDay,
			super.numberOfCopy, this.data, allBookInfo, super.listView);

	// borrows String edit commit
	private final BorrowsStringEditCommit borrowsStringEditCommit = new BorrowsStringEditCommit(super.name,
			super.description, super.publisherName, super.dateOfPublish, super.price, super.borrowingPricePerDay,
			super.numberOfCopy, super.employeeId, super.dateOfBorrows, super.endDateOfBorrows, super.table, this.data,
			super.listView, this.id, super.returnedRadioButton, super.unreturnedRadioButton, super.borrowingDates,
			super.returnDate, super.latingDay);

	private void update() throws NumberFormatException, ClassNotFoundException, SQLException {

		if (super.isSingleUser) { // if layout for single user
			this.updateForSingleUser();
		} else { // if the layout for all book information
			this.upadteForAllBookInformation();
		}

	}

	// update for single user
	private void updateForSingleUser() throws ClassNotFoundException, SQLException, NumberFormatException {

		// ---------------------------------------//
		// date of borrows

		// set cell factory
		super.dateOfBorrows.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.dateOfBorrows.setOnEditCommit(borrowsStringEditCommit);

		// ---------------------------------------//
		// end date of borrows

		// set cell factory
		super.endDateOfBorrows.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.endDateOfBorrows.setOnEditCommit(borrowsStringEditCommit);

		// ---------------------------------------//
		// return date

		// set cell factory
		super.returnDate.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.returnDate.setOnEditCommit(borrowsStringEditCommit);

	} // end updateForSingleUser method

	// update for all book information
	private void upadteForAllBookInformation() throws ClassNotFoundException, SQLException, NumberFormatException {

		// ---------------------------------------//
		// price

		// set cell factory
		super.price.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.price.setOnEditCommit(bookStringEditCommit);

		// ---------------------------------------//
		// borrowing price/day

		// set cell factory
		super.borrowingPricePerDay.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.borrowingPricePerDay.setOnEditCommit(bookStringEditCommit);

		// ---------------------------------------//
		// number of copy

		// set cell factory
		super.numberOfCopy.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.numberOfCopy.setOnEditCommit(bookStringEditCommit);

		// ---------------------------------------//
		// name

		// set cell factory
		super.name.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.name.setOnEditCommit(bookStringEditCommit);

		// ---------------------------------------//
		// description

		// set cell factory
		super.description.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.description.setOnEditCommit(bookStringEditCommit);

		// ---------------------------------------//
		// publisher name

		ArrayList<String> publishersNames = this.getAllPublisherNames();

		ComboBoxTableCell<Book, String> cmt = new ComboBoxTableCell<Book, String>();
		cmt.setId("adminUpdatedCompoBox");

		// ArrayList size
		int size = publishersNames.size();

		// add publisher names into the comboBox
		for (int i = 0; i < size; i++)
			cmt.getItems().add(publishersNames.get(i));

		super.publisherName.setCellFactory(cmt.forTableColumn(cmt.getItems()));

		// set on edit commit
		super.publisherName.setOnEditCommit(bookStringEditCommit);

		// ---------------------------------------//
		// date of publish

		// set cell factory
		super.dateOfPublish.setCellFactory(TextFieldTableCell.forTableColumn());

		// set on edit commit
		super.dateOfPublish.setOnEditCommit(bookStringEditCommit);

	}

	// get all publisher names
	private ArrayList<String> getAllPublisherNames() throws ClassNotFoundException, SQLException {

		// select query
		String query = "SELECT publisher_name FROM publisher;";

		// execute
		Execute.setResultSELECTQuery(query);

		// all publishers names arrayList
		ArrayList<String> allNames = new ArrayList<String>();

		// insert all publishers names into the array list
		while (Execute.resultSet.next()) {
			allNames.add(Execute.resultSet.getString(1));
		}

		// close
		Execute.close();

		return allNames;
	} // getAllPublisherNames method
} // end BookControl class
