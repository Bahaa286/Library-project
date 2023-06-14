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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import library.Book;
import library.ChickMethods;

public class BookStringEditCommit implements EventHandler<CellEditEvent<Book, String>> {

	private final TableColumn<Book, String> name;
	private final TableColumn<Book, String> description;
	private final TableColumn<Book, String> dateOfPublish;
	private final TableColumn<Book, String> publisherName;
	private final TableColumn<Book, String> price;
	private final TableColumn<Book, String> borrowingPricePerDay;
	private final TableColumn<Book, String> numberOfCopy;
	private final TableView<Book> table;

	private ObservableList<Book> data;
	private ArrayList<Book> allBookInfo;
	private ListView<String> listView;

	public BookStringEditCommit(TableColumn<Book, String> name, TableColumn<Book, String> description,
			TableColumn<Book, String> publisherName, TableColumn<Book, String> dateOfPublish, TableView<Book> table,
			TableColumn<Book, String> price, TableColumn<Book, String> borrowingPricePerDay,
			TableColumn<Book, String> numberOfCopy, ObservableList<Book> data, ArrayList<Book> allBookInfo,
			ListView<String> listView) {
		this.name = name;
		this.description = description;
		this.publisherName = publisherName;
		this.dateOfPublish = dateOfPublish;
		this.price = price;
		this.borrowingPricePerDay = borrowingPricePerDay;
		this.numberOfCopy = numberOfCopy;
		this.table = table;

		this.data = data;
		this.allBookInfo = allBookInfo;
		this.listView = listView;
	}
	
	// generate setters 
	public void setData(ObservableList<Book> data) {
		this.data = data;
	}
	
	public void setAllBookInformation(ArrayList<Book> allBookInfo) {
		this.allBookInfo = allBookInfo;
	}
	
	public void setListView(ListView<String> listView) {
		this.listView = listView;
	}
	
	@Override
	public void handle(CellEditEvent<Book, String> t) {
		try {

			if (t.getSource().equals(this.name)) { // name
				this.updateName(t);
			} else if (t.getSource().equals(this.description)) { // description
				this.updateDescription(t);
			} else if (t.getSource().equals(this.publisherName)) { // publisher name
				this.updatePublisherName(t);
			} else if (t.getSource().equals(this.dateOfPublish)) { // date of publish
				this.updateDateOfPublish(t);
			} else if (t.getSource().equals(this.price)) { // price
				this.updatePrice(t);
			} else if (t.getSource().equals(this.borrowingPricePerDay)) { // borrowing price/day
				this.updateBorrowingPricePerDay(t);
			} else if (t.getSource().equals(this.numberOfCopy)) { // number of copies
				this.updateNumberOfCopies(t);
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

	} // end handle

	// -----------------------------------------------------------------------------------------//
	// Database Connections
	private static final ConnectToDatabase connectToDatabase = new ConnectToDatabase(ConnectionsText.URL,
			ConnectionsText.port, ConnectionsText.dbName, ConnectionsText.dbUsername, ConnectionsText.dbPassword);

	// ---------------------------------------------------------------------------------------------//

	// -----------------------------------------------------------------------------------------//
	// refresh book table

	private void refresh() {

		// clear data in ObservableList
		this.data.clear();

		// clear static array list
		allBookInfo.clear();

		// clear all data in table
		this.table.getItems().clear();

		// clear listView items
		this.listView.getItems().clear();

		// insert data from database into ObservableList then add in tableView
		this.insertDataIntoDataBase();
	}

	// -----------------------------------------------------------------------------------------//
	// insert all information from database into table view (after refreshing)

	// insert all information from the database into table
	private void insertDataIntoDataBase() {

		try {
			this.insertAllBookInfoIntoList();
			this.addDataOnTable();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// insert all book info in ObservableList for all book info
	private void insertAllBookInfoIntoList() throws SQLException, ClassNotFoundException {

		// is the static ArrayList empty before insertion
		boolean isStaticArrayListEmpty = allBookInfo.isEmpty();

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
					+ "WHERE BTA.author_id = A.author_id AND\r\n" + "      BTA.publisher_id = P.publisher_id AND\r\n"
					+ "      BTA.book_name = \"" + bookName + "\" AND\r\n" + "	  P.publisher_name = \"" + publisherName
					+ "\";";

			// temporary connection
			Connection tempconnection = connectToDatabase.connect();
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

			// insert into data ObservableList
			data.add(new Book(Execute.resultSet.getString(1), (Execute.resultSet.getString(2)),
					(Execute.resultSet.getString(3)), (Execute.resultSet.getString(4)), Execute.resultSet.getString(5),
					Execute.resultSet.getString(6), Execute.resultSet.getString(7), authorNames));

			if (isStaticArrayListEmpty) // if the static ArrayList empty then insert informations into it
				allBookInfo.add( // add into allBookInfo static ArrayList
						new Book(Execute.resultSet.getString(1), (Execute.resultSet.getString(2)),
								(Execute.resultSet.getString(3)), (Execute.resultSet.getString(4)),
								Execute.resultSet.getString(5), Execute.resultSet.getString(6),
								Execute.resultSet.getString(7), authorNames));

		} // end while

		// close connection
		Execute.close();

	} // end insertAllBookInfoIntoList method

	// add data into table
	private void addDataOnTable() {

		this.name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));
		this.numberOfCopy.setCellValueFactory(new PropertyValueFactory<Book, String>("numberOfCopy"));
		this.price.setCellValueFactory(new PropertyValueFactory<Book, String>("price"));
		this.borrowingPricePerDay.setCellValueFactory(new PropertyValueFactory<Book, String>("borrowingPricePerDay"));
		this.description.setCellValueFactory(new PropertyValueFactory<Book, String>("description"));
		this.dateOfPublish.setCellValueFactory(new PropertyValueFactory<Book, String>("dateOfPublish"));
		this.publisherName.setCellValueFactory(new PropertyValueFactory<Book, String>("publisherName"));

		this.table.setItems(data);

	} // end addDataOnTable method

	// -----------------------------------------------------------------------------------------//
	// update book name
	private void updateName(CellEditEvent<Book, String> t) throws ClassNotFoundException, SQLException {

		// the old value of the birth date
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getName();

		// the new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty()) {
			if (!this.isBookAlreadyExist(newValue, t.getRowValue().getPublisherName())) {

				// update in the tableView
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(newValue);

				try {
					this.updateBookNameInDatabase(oldValue, newValue, t.getRowValue().getPublisherName());
				} catch (ClassNotFoundException | SQLException e) {
				}

			} else {
				t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(oldValue);
			}
		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setName(oldValue);
		}

		// refresh table
		this.refresh();

	}

	// update book name
	private void updateBookNameInDatabase(String oldBookName, String newBookName, String publisherName)
			throws ClassNotFoundException, SQLException {

		// update query
		String query = "UPDATE book\r\n" + "SET book_name = \"" + newBookName + "\"\r\n" + "WHERE book_name = \""
				+ oldBookName + "\" AND\r\n" + "      publisher_id = " + this.getPublisherId(publisherName) + ";";

		// execute
		Execute.executeQuery(query);

	}

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

	// get publisher id from publisher name
	private int getPublisherId(String publisherName) throws ClassNotFoundException, SQLException {

		// select query
		String query = "SELECT publisher_id\r\n" + "FROM publisher\r\n" + "WHERE publisher_name = \"" + publisherName
				+ "\";";

		// execute
		Execute.setResultSELECTQuery(query);

		// next
		Execute.resultSet.next();

		int id = Integer.parseInt(Execute.resultSet.getString(1));

		return id;
	}

	// -----------------------------------------------------------------------------------------//
	// update description
	private void updateDescription(CellEditEvent<Book, String> t) {

		// the old value of the description
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getDescription();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && newValue.length() <= 400) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(newValue);

			try {
				this.updateDescriptionInDatabase(t.getRowValue().getName(), t.getRowValue().getPublisherName(),
						newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDescription(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update description
	private void updateDescriptionInDatabase(String bookName, String publisherName, String description)
			throws ClassNotFoundException, SQLException {

		int publisherId = this.getPublisherId(publisherName);

		// update query
		String query = "UPDATE book\r\n" + "SET book_description = \"" + description + "\"\r\n" + "WHERE book_name = \""
				+ bookName + "\" AND\r\n" + "      publisher_id = " + publisherId + ";";

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update publisher name
	private void updatePublisherName(CellEditEvent<Book, String> t) {

		// the old value of the publisher name
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPublisherName();

		// update in the tableView
		t.getTableView().getItems().get(t.getTablePosition().getRow()).setPublisherName(t.getNewValue());

		try {
			this.updatePublisherNameInDatabase(t.getRowValue().getName(), oldValue, t.getNewValue());
		} catch (ClassNotFoundException | SQLException e) {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPublisherName(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update gender
	private void updatePublisherNameInDatabase(String bookName, String oldPublisherName, String newPublisherName)
			throws ClassNotFoundException, SQLException {

		// old publisher id
		int oldPublisherId = this.getPublisherId(oldPublisherName);

		// new publisher id
		int newPublisherId = this.getPublisherId(newPublisherName);

		// update query
		String query = "UPDATE book\r\n" + "SET publisher_id = " + newPublisherId + " \r\n" + "WHERE publisher_id = "
				+ oldPublisherId + " AND\r\n" + "      book_name = \"" + bookName + "\";";

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update date of publish
	private void updateDateOfPublish(CellEditEvent<Book, String> t) {

		// the old value of the date of publish
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getDateOfPublish();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && ChickMethods.isBirthDateValid(newValue)) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDateOfPublish(newValue);

			try {
				this.updateDateOfPublishInDatabase(t.getRowValue().getName(), t.getRowValue().getPublisherName(),
						newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setDateOfPublish(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update date of publish
	private void updateDateOfPublishInDatabase(String bookName, String publisherName, String dateOfPublish)
			throws ClassNotFoundException, SQLException {

		int publisherId = this.getPublisherId(publisherName);

		// update query
		String query = "UPDATE book\r\n" + "SET date_of_publish = \"" + dateOfPublish + "\"\r\n"
				+ "WHERE book_name = \"" + bookName + "\" AND\r\n" + "      publisher_id = " + publisherId + ";";

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update price
	private void updatePrice(CellEditEvent<Book, String> t) {

		// the old value of the price
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getPrice();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && ChickMethods.isFloat(newValue) && Double.parseDouble(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPrice(newValue);

			try {
				this.updatePriceInDatabase(t.getRowValue().getName(), t.getRowValue().getPublisherName(), newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setPrice(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update price
	private void updatePriceInDatabase(String bookName, String publisherName, String price)
			throws ClassNotFoundException, SQLException {

		int publisherId = this.getPublisherId(publisherName);

		// update query
		String query = "UPDATE book\r\n" + "SET price = \"" + price + "\"\r\n" + "WHERE book_name = \"" + bookName
				+ "\" AND\r\n" + "      publisher_id = " + publisherId + ";";

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update borrowing price/day
	private void updateBorrowingPricePerDay(CellEditEvent<Book, String> t) {

		// the old value of the borrowing price/day
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getBorrowingPricePerDay();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && ChickMethods.isFloat(newValue) && Double.parseDouble(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setBorrowingPricePerDay(newValue);

			try {
				this.updateBorrowingPricePerDayInDatabase(t.getRowValue().getName(), t.getRowValue().getPublisherName(),
						newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setBorrowingPricePerDay(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update borrowing price/day
	private void updateBorrowingPricePerDayInDatabase(String bookName, String publisherName,
			String borrowingPricePerDay) throws ClassNotFoundException, SQLException {

		int publisherId = this.getPublisherId(publisherName);

		// update query
		String query = "UPDATE book\r\n" + "SET borrowing_pricePerDay = \"" + borrowingPricePerDay + "\"\r\n"
				+ "WHERE book_name = \"" + bookName + "\" AND\r\n" + "      publisher_id = " + publisherId + ";";

		// execute
		Execute.executeQuery(query);

	}

	// -----------------------------------------------------------------------------------------//
	// update number of copies
	private void updateNumberOfCopies(CellEditEvent<Book, String> t) {

		// the old value of the number of copies
		String oldValue = t.getTableView().getItems().get(t.getTablePosition().getRow()).getNumberOfCopy();

		// new value
		String newValue = "";

		if (t.getNewValue() != null)
			newValue = t.getNewValue().trim();

		if (!newValue.isEmpty() && ChickMethods.isDigit(newValue) && Integer.parseInt(newValue) >= 0) {

			// update in the tableView
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setNumberOfCopy(newValue);

			try {
				this.updateNumberOfCopiesInDatabase(t.getRowValue().getName(), t.getRowValue().getPublisherName(),
						newValue);
			} catch (ClassNotFoundException | SQLException e) {
			}

		} else {
			t.getTableView().getItems().get(t.getTablePosition().getRow()).setNumberOfCopy(oldValue);
		}

		// refresh
		this.refresh();

	}

	// update number of copies
	private void updateNumberOfCopiesInDatabase(String bookName, String publisherName, String numberOfCopies)
			throws ClassNotFoundException, SQLException {

		int publisherId = this.getPublisherId(publisherName);

		// update query
		String query = "UPDATE book\r\n" + "SET number_of_copy = \"" + numberOfCopies + "\"\r\n"
				+ "WHERE book_name = \"" + bookName + "\" AND\r\n" + "      publisher_id = " + publisherId + ";";

		// execute
		Execute.executeQuery(query);
	}
} // end class
