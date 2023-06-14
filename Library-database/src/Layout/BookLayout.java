package Layout;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import library.Book;
import library.Borrows;

public class BookLayout extends Pane {

	/*----------------- adminPane -----------------*/
	// the pane which this pane will inserted into
	protected HBox adminPane;

	/*----------------- size -----------------*/
	private static final int HEIGHT = 600; // height
	private static final int WIDTH = 1200; // width

	/*--------------- is SingleUser ---------------*/
	// true: if the reference is for a singular user
	// false if the reference for all book informations
	protected final boolean isSingleUser;

	// constructor for single user
	public BookLayout(String id) {

		this.isSingleUser = true;

		// set size
		this.setPrefSize(WIDTH, HEIGHT);

		// set Layouts
		this.verticalTablesPaneLayout();

	}

	// constructor for all book information
	public BookLayout(HBox adminPane) {

		this.isSingleUser = false;

		// set reference
		this.adminPane = adminPane;

		// set size
		this.setPrefSize(adminPane.getWidth(), adminPane.getHeight());
		this.prefHeightProperty().bind(this.adminPane.heightProperty());
		this.prefWidthProperty().bind(this.adminPane.widthProperty());

		// set layouts
		this.verticalTablesPaneLayout();

	}

	// -------------------------------------------------------------------------------------------------------//
	// primary vertical pane layout

	private final VBox verticalTablesPane = new VBox();

	private void verticalTablesPaneLayout() {

		verticalTablesPane.prefHeightProperty().bind(this.heightProperty());
		verticalTablesPane.prefWidthProperty().bind(this.widthProperty());

		this.getChildren().add(this.verticalTablesPane);

		// setLayout
		this.horizontalTablesPaneLayout();
		this.searchPaneLayout();
	}

	// -------------------------------------------------------------------------------------------------------//
	// primary horizontal tables pane layout

	private final HBox horizontalTablesPane = new HBox();

	private void horizontalTablesPaneLayout() {

		horizontalTablesPane.prefHeightProperty().bind(verticalTablesPane.heightProperty().multiply(7).divide(10));
		horizontalTablesPane.prefWidthProperty().bind(verticalTablesPane.widthProperty());

		this.verticalTablesPane.getChildren().add(this.horizontalTablesPane);

		// setLayout
		this.controlPaneLayout();
		this.tablePaneLayout();
		this.listViewPaneLayout();
	}

	// --------------------------------------------------------------------------------//
	// controls pane

	private final VBox controlPane = new VBox();

	private void controlPaneLayout() {

		controlPane.prefHeightProperty().bind(this.horizontalTablesPane.heightProperty());
		controlPane.prefWidthProperty().bind(this.horizontalTablesPane.widthProperty().divide(6));

		controlPane.setId("controlPane");

		this.horizontalTablesPane.getChildren().add(controlPane);

		// set layouts
		if (this.isSingleUser) {
			this.allBookListViewLayout();
		} else {
			this.optionsPaneLayout();
		}

	}

	// --------------------------------------------------------------------------------//
	// controls options pane

	protected final HBox[] optionsPane = new HBox[11];

	private void optionsPaneLayout() {

		// for each optionsPane
		for (int i = 0; i < optionsPane.length - 2; i++) {

			// reference for each optionsPane
			optionsPane[i] = new HBox();

			optionsPane[i].prefHeightProperty().bind(this.controlPane.heightProperty().multiply(8).divide(99));
			optionsPane[i].prefWidthProperty().bind(this.controlPane.widthProperty());

			optionsPane[i].setAlignment(Pos.CENTER);
			optionsPane[i].setStyle("-fx-border-color: Aqua");

			this.controlPane.getChildren().add(optionsPane[i]);
		} // end for loop

		// reference for ListView optionsPane
		optionsPane[optionsPane.length - 2] = new HBox();

		optionsPane[optionsPane.length - 2].prefHeightProperty()
				.bind(this.controlPane.heightProperty().multiply(3).divide(11));
		optionsPane[optionsPane.length - 2].prefWidthProperty().bind(this.controlPane.widthProperty());

		optionsPane[optionsPane.length - 2].setAlignment(Pos.CENTER);
		optionsPane[optionsPane.length - 2].setStyle("-fx-border-color: Aqua");

		this.controlPane.getChildren().add(optionsPane[optionsPane.length - 2]);

		// -----------------------------------------//
		// reference for insert button optionsPane
		optionsPane[optionsPane.length - 1] = new HBox();

		optionsPane[optionsPane.length - 1].prefHeightProperty()
				.bind(this.controlPane.heightProperty().multiply(8).divide(99));
		optionsPane[optionsPane.length - 1].prefWidthProperty().bind(this.controlPane.widthProperty());

		optionsPane[optionsPane.length - 1].setAlignment(Pos.CENTER);
		optionsPane[optionsPane.length - 1].setStyle("-fx-border-color: Aqua");

		this.controlPane.getChildren().add(optionsPane[optionsPane.length - 1]);

		// set layout
		this.optionsLayout();

	}

	// --------------------------------------------------------------------------------//
	// table pane

	protected final VBox tablePane = new VBox();

	private void tablePaneLayout() {

		tablePane.prefHeightProperty().bind(this.horizontalTablesPane.heightProperty());
		tablePane.prefWidthProperty().bind(this.horizontalTablesPane.widthProperty().multiply(4).divide(6));

		tablePane.setId("tablePane");

		this.horizontalTablesPane.getChildren().add(tablePane);

		// set layouts
		if (this.isSingleUser)
			this.radioButtonPaneLayout();

		this.tableViewPaneLayout();
		this.buttonPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// books borrowing type RadioButton pane (type: returned, unreturned)
	private final HBox radioButtonPane = new HBox(4);

	private void radioButtonPaneLayout() {

		radioButtonPane.prefHeightProperty().bind(this.tablePane.heightProperty().divide(10));
		radioButtonPane.prefWidthProperty().bind(this.tablePane.widthProperty());

		this.tablePane.getChildren().add(radioButtonPane);

		// set layouts
		this.radioButtonLayout();

	}

	// --------------------------------------------------------------------------------//
	// tableView pane

	protected final VBox tableViewPane = new VBox();

	private void tableViewPaneLayout() {

		tableViewPane.prefHeightProperty().bind(this.tablePane.heightProperty().multiply(8).divide(10));
		tableViewPane.prefWidthProperty().bind(this.tablePane.widthProperty());

		this.tablePane.getChildren().add(tableViewPane);

		// set layouts
		this.tableLayout();

	}

	// ---------------------------------------------------------------------------------//
	// button pane

	private final HBox buttonPane = new HBox(10);

	private void buttonPaneLayout() {

		buttonPane.prefHeightProperty().bind(this.tablePane.heightProperty().divide(10));
		buttonPane.prefWidthProperty().bind(this.tablePane.widthProperty());

		this.tablePane.getChildren().add(buttonPane);

		// set layouts
		this.buttonLayout();

	}

	// ---------------------------------------------------------------------------------//
	// listView pane

	// pane
	private final VBox listViewPane = new VBox();

	private void listViewPaneLayout() {

		listViewPane.prefHeightProperty().bind(this.horizontalTablesPane.heightProperty());
		listViewPane.prefWidthProperty().bind(this.horizontalTablesPane.widthProperty().divide(6));

		listViewPane.setId("controlPane");

		this.horizontalTablesPane.getChildren().add(listViewPane);

		// set layout
		this.listViewLayout();

		if (!this.isSingleUser) {
			this.listViewControlPaneLayout();
			this.listViewErrorPaneLayout();
		}

	}

	// control
	protected final HBox listViewControlPane = new HBox();

	private void listViewControlPaneLayout() {

		listViewControlPane.prefHeightProperty().bind(this.listViewPane.heightProperty().divide(10));
		listViewControlPane.prefWidthProperty().bind(this.listViewPane.widthProperty());

		listViewControlPane.setId("controlPane");

		this.listViewPane.getChildren().add(listViewControlPane);

		// set layouts
		this.listViewControlLayout();

	}

	// error
	protected final HBox listViewErrorPane = new HBox();

	private void listViewErrorPaneLayout() {

		listViewErrorPane.prefHeightProperty().bind(this.listViewPane.heightProperty().divide(10));
		listViewErrorPane.prefWidthProperty().bind(this.listViewPane.widthProperty());

		listViewErrorPane.setId("controlPane");

		this.listViewPane.getChildren().add(listViewErrorPane);

		// set layouts
		this.listViewErrorLayout();

	}

	// ---------------------------------------------------------------------------------------//
	// search pane
	protected final HBox searchPane = new HBox(2);

	private void searchPaneLayout() {

		searchPane.prefHeightProperty().bind(this.verticalTablesPane.heightProperty().multiply(3).divide(10));
		searchPane.prefWidthProperty().bind(this.verticalTablesPane.widthProperty());

		searchPane.setStyle("-fx-border-color: Aqua;");

		this.verticalTablesPane.getChildren().add(searchPane);

		// set layouts
		this.HSearchPaneLayout();
		this.VSearchPaneLayout();

	}

	// HsearchPane layout

	protected final HBox HSearchPane = new HBox(2);

	private void HSearchPaneLayout() {

		HSearchPane.prefHeightProperty().bind(this.searchPane.heightProperty());
		HSearchPane.prefWidthProperty().bind(this.searchPane.widthProperty().multiply(9).divide(10));

		HSearchPane.setStyle("-fx-border-color: Aqua;");
		HSearchPane.setAlignment(Pos.CENTER);

		this.searchPane.getChildren().add(HSearchPane);

		// set layouts
		this.HSearchLayout();

	}

	// VSearchPane layout
	private final VBox VSearchPane = new VBox();

	private void VSearchPaneLayout() {

		VSearchPane.prefHeightProperty().bind(this.searchPane.heightProperty());
		VSearchPane.prefWidthProperty().bind(this.searchPane.widthProperty().divide(10));

		VSearchPane.setStyle("-fx-border-color: Aqua;");

		this.searchPane.getChildren().add(VSearchPane);

		// set layouts
		this.VSearchLayout();

	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// --------------------------------------------------------------------------------//
	// options

	protected final TextField[] textFields = new TextField[8]; // textFields
	protected final DatePicker dateOfPublishDatePicker = new DatePicker(); // date of publish
	protected final Label[] errorsOption = new Label[8]; // errors Labels
	protected final Button insert = new Button("insert"), addAuthor = new Button("add"),
			deleteAuthor = new Button("delete"); // insert,addAuthor and deleteAuthor buttons
	protected final ListView<String> authorsName = new ListView<String>(); // autherNames ListView

	private void optionsLayout() {

		// -------------------------------------------//
		// textFields

		// for each TextField
		for (int i = 0; i < textFields.length; i++) {

			// to put the DatePicker after 5 TextFields
			if (i != 5) {

				textFields[i] = new TextField();

				textFields[i].setId("adminTextField");

				textFields[i].prefHeightProperty().bind(this.optionsPane[i].heightProperty().multiply(9).divide(10));
				textFields[i].prefWidthProperty().bind(this.optionsPane[i].widthProperty().multiply(2).divide(3));

				this.optionsPane[i].getChildren().add(textFields[i]);

			} else { // add the DatePicker

				dateOfPublishDatePicker.setId("adminDatePicker");

				dateOfPublishDatePicker.prefHeightProperty()
						.bind(this.optionsPane[i].heightProperty().multiply(9).divide(10));
				dateOfPublishDatePicker.prefWidthProperty()
						.bind(this.optionsPane[i].widthProperty().multiply(2).divide(3));

				this.optionsPane[i].getChildren().add(dateOfPublishDatePicker);

			} // end else

		} // end for loop

		// height and width property for authorName TextField
		textFields[7].prefHeightProperty().bind(this.optionsPane[7].heightProperty().multiply(9).divide(10));
		textFields[7].prefWidthProperty().bind(this.optionsPane[7].widthProperty().multiply(5).divide(12));

		// prompt text
		textFields[0].setPromptText("book name");
		textFields[1].setPromptText("number of copies");
		textFields[2].setPromptText("price");
		textFields[3].setPromptText("borrowing price/day");
		textFields[4].setPromptText("description");

		dateOfPublishDatePicker.setPromptText("date of publish");

		textFields[6].setPromptText("publisher name");
		textFields[7].setPromptText("auther name");

		// -------------------------------------------//
		// errors

		// for each error Label
		for (int i = 0; i < errorsOption.length - 1; i++) {

			errorsOption[i] = new Label();

			errorsOption[i].setId("adminerror");

			errorsOption[i].prefHeightProperty().bind(this.optionsPane[i].heightProperty().multiply(9).divide(10));
			errorsOption[i].prefWidthProperty().bind(this.optionsPane[i].widthProperty().divide(3));

			this.optionsPane[i].getChildren().add(errorsOption[i]);

		} // end for loop

		// the error label for authors name
		errorsOption[errorsOption.length - 1] = new Label();

		errorsOption[errorsOption.length - 1].setId("adminerror");

		errorsOption[errorsOption.length - 1].prefHeightProperty()
				.bind(this.optionsPane[errorsOption.length].heightProperty().multiply(9).divide(10));
		errorsOption[errorsOption.length - 1].prefWidthProperty()
				.bind(this.optionsPane[errorsOption.length].widthProperty());

		this.optionsPane[errorsOption.length].getChildren().add(errorsOption[errorsOption.length - 1]);

		// --------------------------------------------//
		// add authors button

		addAuthor.setId("adminButton");

		addAuthor.prefHeightProperty()
				.bind(this.optionsPane[errorsOption.length - 1].heightProperty().multiply(9).divide(10));
		addAuthor.prefWidthProperty().bind(this.optionsPane[errorsOption.length - 1].widthProperty().divide(4));

		this.optionsPane[errorsOption.length - 1].getChildren().add(addAuthor);

		// --------------------------------------------//
		// delete authors button

		deleteAuthor.setId("adminButton");

		deleteAuthor.prefHeightProperty()
				.bind(this.optionsPane[errorsOption.length - 1].heightProperty().multiply(9).divide(10));
		deleteAuthor.prefWidthProperty().bind(this.optionsPane[errorsOption.length - 1].widthProperty().divide(3));

		this.optionsPane[errorsOption.length - 1].getChildren().add(deleteAuthor);

		// --------------------------------------------//
		// ListView

		authorsName.setId("adminListView");

		authorsName.prefHeightProperty().bind(this.optionsPane[optionsPane.length - 2].heightProperty());
		authorsName.prefWidthProperty().bind(this.optionsPane[optionsPane.length - 2].widthProperty());

		this.optionsPane[optionsPane.length - 2].getChildren().add(authorsName);

		// --------------------------------------------//
		// insert button

		insert.setId("adminButton");

		insert.prefHeightProperty()
				.bind(this.optionsPane[optionsPane.length - 1].heightProperty().multiply(9).divide(10));
		insert.prefWidthProperty().bind(this.optionsPane[optionsPane.length - 1].widthProperty().multiply(4).divide(5));

		this.optionsPane[optionsPane.length - 1].getChildren().add(insert);

	}

	// ---------------------------------------------------------------------------------------//
	// all Book ListView

	protected final ListView<String> allBookListView = new ListView<String>();
	protected final Button chooseBookButton = new Button("select");
	private final Label allBookLabel = new Label("Avilable books");
	protected final Label errorSelectBookLabel = new Label("");

	private void allBookListViewLayout() {

		// ---------------------------------------------//
		// Label

		allBookLabel.setId("adminLabel");
		allBookLabel.setAlignment(Pos.CENTER);

		allBookLabel.prefHeightProperty().bind(this.controlPane.heightProperty().divide(10));
		allBookLabel.prefWidthProperty().bind(this.controlPane.widthProperty());

		this.controlPane.getChildren().add(allBookLabel);

		// ------------------------------------------------//
		// allBookListView

		allBookListView.prefHeightProperty().bind(this.controlPane.heightProperty().multiply(7).divide(10));
		allBookListView.prefWidthProperty().bind(this.controlPane.widthProperty());

		allBookListView.setId("adminListView");

		// chooseBookButton

		chooseBookButton.prefHeightProperty().bind(this.controlPane.heightProperty().divide(10));
		chooseBookButton.prefWidthProperty().bind(this.controlPane.widthProperty());

		chooseBookButton.setId("adminButton");

		this.controlPane.getChildren().addAll(allBookListView, chooseBookButton);

		// error

		errorSelectBookLabel.prefHeightProperty().bind(this.controlPane.heightProperty().divide(10));
		errorSelectBookLabel.prefWidthProperty().bind(this.controlPane.widthProperty());

		errorSelectBookLabel.setId("adminerror");

		this.controlPane.getChildren().addAll(errorSelectBookLabel);

	}

	// ---------------------------------------------//
	// RadioButton
	protected final RadioButton returnedRadioButton = new RadioButton("show returned books"),
			unreturnedRadioButton = new RadioButton("show unreturned books");

	private final ToggleGroup toggleGroup = new ToggleGroup();

	private void radioButtonLayout() {

		// returned books radioButton
		returnedRadioButton.setId("adminRadioButton");
		returnedRadioButton.setAlignment(Pos.CENTER_LEFT);

		returnedRadioButton.prefHeightProperty().bind(this.radioButtonPane.heightProperty());
		returnedRadioButton.prefWidthProperty().bind(this.radioButtonPane.widthProperty().divide(2));

		returnedRadioButton.setToggleGroup(this.toggleGroup);
		returnedRadioButton.setSelected(false);

		this.radioButtonPane.getChildren().add(returnedRadioButton);

		// unreturned books radioButton
		unreturnedRadioButton.setId("adminRadioButton");
		unreturnedRadioButton.setAlignment(Pos.CENTER_LEFT);

		unreturnedRadioButton.prefHeightProperty().bind(this.radioButtonPane.heightProperty());
		unreturnedRadioButton.prefWidthProperty().bind(this.radioButtonPane.widthProperty().divide(2));

		unreturnedRadioButton.setToggleGroup(this.toggleGroup);
		unreturnedRadioButton.setSelected(true);

		this.radioButtonPane.getChildren().add(unreturnedRadioButton);

	}

	// ---------------------------------------------//
	// table
	protected TableView table;

	private void tableLayout() {

		if (this.isSingleUser) {
			table = new TableView<Borrows>();
		} else {
			table = new TableView<Book>();
		}

		table.prefWidthProperty().bind(this.tableViewPane.widthProperty());
		table.prefHeightProperty().bind(this.tableViewPane.heightProperty());

		table.setId("adminTableView");

		table.setEditable(true);

		this.tableViewPane.getChildren().add(table);

		this.addColumns();
	}

	protected final TableColumn name = new TableColumn("Name");
	protected final TableColumn numberOfCopy = new TableColumn("copies#");
	protected final TableColumn price = new TableColumn("Price");
	protected final TableColumn borrowingPricePerDay = new TableColumn("price/day");
	protected final TableColumn description = new TableColumn("description");
	protected final TableColumn dateOfPublish = new TableColumn("Date of publish");
	protected final TableColumn publisherName = new TableColumn("Publisher name");

	// for single user
	protected final TableColumn<Borrows, String> employeeId = new TableColumn<Borrows, String>("employee id");
	protected final TableColumn<Borrows, String> borrowingDates = new TableColumn<Borrows, String>("dates information");
	protected final TableColumn<Borrows, String> dateOfBorrows = new TableColumn<Borrows, String>("borrows date");
	protected final TableColumn<Borrows, String> endDateOfBorrows = new TableColumn<Borrows, String>(
			"borrows end date");
	protected final TableColumn<Borrows, String> returnDate = new TableColumn<Borrows, String>(
			"return date");
	protected final TableColumn<Borrows, String> latingDay = new TableColumn<Borrows, String>(
			"lating days");

	private void addColumns() {

		this.table.getColumns().addAll(name, numberOfCopy, price, borrowingPricePerDay, description, dateOfPublish,
				publisherName);

		// columns width
		this.name.prefWidthProperty().bind(this.table.widthProperty().multiply(17).divide(200));
		this.numberOfCopy.prefWidthProperty().bind(this.table.widthProperty().multiply(10).divide(200));
		this.price.prefWidthProperty().bind(this.table.widthProperty().multiply(10).divide(200));
		this.borrowingPricePerDay.prefWidthProperty().bind(this.table.widthProperty().multiply(15).divide(200));
		this.description.prefWidthProperty().bind(this.table.widthProperty().multiply(100).divide(200));
		this.dateOfPublish.prefWidthProperty().bind(this.table.widthProperty().multiply(20).divide(200));
		this.publisherName.prefWidthProperty().bind(this.table.widthProperty().multiply(20).divide(200));

		if (this.isSingleUser) {

			this.borrowingDates.getColumns().addAll(dateOfBorrows, endDateOfBorrows);

			this.table.getColumns().addAll(employeeId, borrowingDates);

			// columns width
			this.employeeId.prefWidthProperty().bind(this.table.widthProperty().multiply(20).divide(200));
			//this.borrowingDates.prefWidthProperty().bind(this.table.widthProperty().multiply(85).divide(200));
			this.dateOfBorrows.prefWidthProperty().bind(this.table.widthProperty().multiply(25).divide(200));
			this.endDateOfBorrows.prefWidthProperty().bind(this.table.widthProperty().multiply(30).divide(200));
			this.returnDate.prefWidthProperty().bind(this.table.widthProperty().multiply(25).divide(200));
			this.latingDay.prefWidthProperty().bind(this.table.widthProperty().multiply(30).divide(200));

		}

	}// end add columns method

	// ---------------------------------------------//
	// buttons

	protected final Button delete = new Button("delete"), refresh = new Button("refresh");

	private void buttonLayout() {

		// delete

		delete.prefHeightProperty().bind(this.buttonPane.heightProperty().multiply(9).divide(10));
		delete.prefWidthProperty().bind(this.buttonPane.widthProperty().divide(3));

		delete.setId("adminButton");

		// delete

		refresh.prefHeightProperty().bind(this.buttonPane.heightProperty().multiply(9).divide(10));
		refresh.prefWidthProperty().bind(this.buttonPane.widthProperty().divide(3));

		refresh.setId("adminButton");

		this.buttonPane.getChildren().addAll(delete, refresh);

	}

	// -----------------------------------------------------------------------------------//
	// List view (to insert author names)

	// listView
	protected final ListView<String> listView = new ListView<String>();

	// Label
	private final Label listViewLabel = new Label("Authors Names");

	private void listViewLayout() {

		// ---------------------------------------------//
		// Label

		listViewLabel.setId("adminLabel");
		listViewLabel.setAlignment(Pos.CENTER);

		listViewLabel.prefHeightProperty().bind(this.listViewPane.heightProperty().divide(10));
		listViewLabel.prefWidthProperty().bind(this.listViewPane.widthProperty());

		this.listViewPane.getChildren().add(listViewLabel);

		// --------------------------------//
		// listView

		listView.setId("adminListView");

		if (this.isSingleUser) {
			listView.prefHeightProperty().bind(this.listViewPane.heightProperty().multiply(9).divide(10));
		} else {
			listView.prefHeightProperty().bind(this.listViewPane.heightProperty().multiply(7).divide(10));
		}

		listView.prefWidthProperty().bind(this.listViewPane.widthProperty());

		this.listViewPane.getChildren().add(listView);

	}

	// add, delete button and textField
	protected final Button addOnList = new Button("add"), deleteFromList = new Button("delete");
	protected final TextField authorTextOnList = new TextField();

	private void listViewControlLayout() {

		// textField

		authorTextOnList.prefHeightProperty().bind(this.listViewControlPane.heightProperty().multiply(9).divide(10));
		authorTextOnList.prefWidthProperty().bind(this.listViewControlPane.widthProperty().multiply(7).divide(20));

		authorTextOnList.setId("adminTextField");

		authorTextOnList.setPromptText("author name");

		this.listViewControlPane.getChildren().add(authorTextOnList);

		// add on list button

		addOnList.prefHeightProperty().bind(this.listViewControlPane.heightProperty().multiply(9).divide(10));
		addOnList.prefWidthProperty().bind(this.listViewControlPane.widthProperty().multiply(5).divide(20));

		addOnList.setId("adminButton");

		this.listViewControlPane.getChildren().add(addOnList);

		// delete from list button

		deleteFromList.prefHeightProperty().bind(this.listViewControlPane.heightProperty().multiply(9).divide(10));
		deleteFromList.prefWidthProperty().bind(this.listViewControlPane.widthProperty().multiply(8).divide(20));

		deleteFromList.setId("adminButton");

		this.listViewControlPane.getChildren().add(deleteFromList);

	}

	// error
	protected final Label errorListView = new Label();

	private void listViewErrorLayout() {

		// textField

		errorListView.prefHeightProperty().bind(this.listViewErrorPane.heightProperty().multiply(9).divide(10));
		errorListView.prefWidthProperty().bind(this.listViewErrorPane.widthProperty());

		errorListView.setId("adminerror");

		this.listViewErrorPane.getChildren().add(errorListView);
	}

	// -------------------------------------------------------------------------------------------//
	// search

	protected final Label errorSearchLabel = new Label(); // error search label
	protected final Button addFromSearch = new Button("borrow"), deleteFromSearch = new Button("delete"); // add, delete
																											// Buttons
	protected final TextField searchBookNameTextField = new TextField(), searchPublisherNameTextField = new TextField();// TextFields
	protected final Button searchButton = new Button("search"); // search Button
	protected final ListView<String> resultSearchListView = new ListView<String>(); // result search ListView

	private void HSearchLayout() {

		// searchBookNameTextField TextField

		searchBookNameTextField.setId("adminTextField");
		searchBookNameTextField.setPromptText("Book name");

		searchBookNameTextField.prefHeightProperty().bind(this.HSearchPane.heightProperty().divide(3));
		searchBookNameTextField.prefWidthProperty().bind(this.HSearchPane.widthProperty().divide(11));

		this.HSearchPane.getChildren().add(searchBookNameTextField);

		// searchBookNameTextField TextField

		searchPublisherNameTextField.setId("adminTextField");
		searchPublisherNameTextField.setPromptText("publisher name");

		searchPublisherNameTextField.prefHeightProperty().bind(this.HSearchPane.heightProperty().divide(3));
		searchPublisherNameTextField.prefWidthProperty().bind(this.HSearchPane.widthProperty().divide(11));

		this.HSearchPane.getChildren().add(searchPublisherNameTextField);

		// Button

		searchButton.setId("adminButton");

		searchButton.prefHeightProperty().bind(this.HSearchPane.heightProperty().multiply(4).divide(10));
		searchButton.prefWidthProperty().bind(this.HSearchPane.widthProperty().divide(10));

		this.HSearchPane.getChildren().add(searchButton);

		// result ListView

		resultSearchListView.setId("adminListView");

		resultSearchListView.prefHeightProperty().bind(this.HSearchPane.heightProperty());
		resultSearchListView.prefWidthProperty().bind(this.HSearchPane.widthProperty().multiply(7).divide(10));

		this.HSearchPane.getChildren().add(resultSearchListView);

	} // end searchLayout method

	private void VSearchLayout() {

		if (this.isSingleUser) {

			// add button

			addFromSearch.setId("adminButton");

			addFromSearch.prefHeightProperty().bind(this.VSearchPane.heightProperty().multiply(4).divide(10));
			addFromSearch.prefWidthProperty().bind(this.VSearchPane.widthProperty());

			this.VSearchPane.getChildren().add(addFromSearch);

		} else {

			// delete button

			deleteFromSearch.setId("adminButton");

			deleteFromSearch.prefHeightProperty().bind(this.VSearchPane.heightProperty().multiply(4).divide(10));
			deleteFromSearch.prefWidthProperty().bind(this.VSearchPane.widthProperty());

			this.VSearchPane.getChildren().add(deleteFromSearch);

		}

		// error result Label

		errorSearchLabel.setId("adminerror");
		errorSearchLabel.setAlignment(Pos.CENTER);

		errorSearchLabel.prefHeightProperty().bind(this.VSearchPane.heightProperty().multiply(5).divide(10));
		errorSearchLabel.prefWidthProperty().bind(this.VSearchPane.widthProperty());

		this.VSearchPane.getChildren().add(errorSearchLabel);

	} // end VSearchLayout
} // end BookLayout class
