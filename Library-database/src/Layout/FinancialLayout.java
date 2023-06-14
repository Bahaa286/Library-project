package Layout;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import library.BorrowingFinancial;
import library.BorrowingFinancial;

public class FinancialLayout extends Pane {

	// administrator pane to insert this pane on it
	protected final HBox adminPane;

	public FinancialLayout(HBox adminPane) {
		this.adminPane = adminPane;

		this.setPrefSize(adminPane.getWidth(), adminPane.getHeight());

		this.prefHeightProperty().bind(adminPane.heightProperty());
		this.prefWidthProperty().bind(adminPane.widthProperty());

		this.setId("financialPane");

		// set layout
		this.primaryPaneLayout();
	}

	// ------------------------------------------------------//
	// primary page layout
	private static final VBox primaryPane = new VBox();

	private void primaryPaneLayout() {

		primaryPane.setId("financialPane");

		primaryPane.prefHeightProperty().bind(this.heightProperty());
		primaryPane.prefWidthProperty().bind(this.widthProperty());

		this.getChildren().add(primaryPane);

		// set Layout
		this.totalSalariesPaneLayout();
		this.totalBookPricesPaneLayout();
		this.totalfinancialBalancePaneLayout();
		this.feesFromBorrowingPaneLayout();

	}

	// -----------------------------------------------------//
	// total salaries pane
	private static final VBox totalSalariesPane = new VBox();

	private void totalSalariesPaneLayout() {

		totalSalariesPane.setId("financialPane");

		totalSalariesPane.prefHeightProperty().bind(primaryPane.heightProperty().divide(7));
		totalSalariesPane.prefWidthProperty().bind(primaryPane.widthProperty());

		primaryPane.getChildren().add(totalSalariesPane);

		// set layouts
		this.totalSalariesTextPaneLayout();
		this.totalSalariesLabelsPaneLayout();

	}

	// total salaries Text pane
	private static final HBox totalSalariesTextPane = new HBox();

	private void totalSalariesTextPaneLayout() {

		totalSalariesTextPane.setId("financialPane");

		totalSalariesTextPane.prefHeightProperty().bind(totalSalariesPane.heightProperty().divide(2));
		totalSalariesTextPane.prefWidthProperty().bind(totalSalariesPane.widthProperty());

		totalSalariesPane.getChildren().add(totalSalariesTextPane);

		// set layouts
		this.totalSalariesLabelLayout();

	}

	// total salaries Labels pane
	private static final HBox totalSalariesLabelsPane = new HBox();

	private void totalSalariesLabelsPaneLayout() {

		totalSalariesLabelsPane.setId("financialPane");

		totalSalariesLabelsPane.prefHeightProperty().bind(totalSalariesPane.heightProperty().divide(2));
		totalSalariesLabelsPane.prefWidthProperty().bind(totalSalariesPane.widthProperty());

		totalSalariesPane.getChildren().add(totalSalariesLabelsPane);

		// set layouts
		this.totalSalariesLabelsLayout();

	}

	// -------------------------------------------------------------------//
	// total book prices
	private static final VBox totalBookPricesPane = new VBox();

	private void totalBookPricesPaneLayout() {

		totalBookPricesPane.setId("financialPane");

		totalBookPricesPane.prefHeightProperty().bind(primaryPane.heightProperty().divide(7));
		totalBookPricesPane.prefWidthProperty().bind(primaryPane.widthProperty());

		primaryPane.getChildren().add(totalBookPricesPane);

		// set layouts
		this.totalBookPricesTextPaneLayout();
		this.totalBookPricesLabelsPaneLayout();
		this.totalBookPricesSingleBookLabelsPaneLayout();

	}

	// total book prices Text pane
	private static final HBox totalBookPricesTextPane = new HBox();

	private void totalBookPricesTextPaneLayout() {

		totalBookPricesTextPane.setId("financialPane");

		totalBookPricesTextPane.prefHeightProperty().bind(totalBookPricesPane.heightProperty().divide(3));
		totalBookPricesTextPane.prefWidthProperty().bind(totalBookPricesPane.widthProperty());

		totalBookPricesPane.getChildren().add(totalBookPricesTextPane);

		// set layouts
		this.totalBookPricesLabelLayout();

	}

	// total book prices Labels pane
	private static final HBox totalBookPricesLabelsPane = new HBox();

	private void totalBookPricesLabelsPaneLayout() {

		totalBookPricesLabelsPane.setId("financialPane");

		totalBookPricesLabelsPane.prefHeightProperty().bind(totalBookPricesPane.heightProperty().divide(3));
		totalBookPricesLabelsPane.prefWidthProperty().bind(totalBookPricesPane.widthProperty());

		totalBookPricesPane.getChildren().add(totalBookPricesLabelsPane);

		// set layouts
		this.totalBookPricesLabelsLayout();

	}

	// total book prices for single book Labels pane
	private static final HBox totalBookPricesSingleBookLabelsPane = new HBox(5);

	private void totalBookPricesSingleBookLabelsPaneLayout() {

		totalBookPricesSingleBookLabelsPane.setId("financialPane");

		totalBookPricesSingleBookLabelsPane.prefHeightProperty().bind(totalBookPricesPane.heightProperty().divide(3));
		totalBookPricesSingleBookLabelsPane.prefWidthProperty().bind(totalBookPricesPane.widthProperty());

		totalBookPricesPane.getChildren().add(totalBookPricesSingleBookLabelsPane);

		this.totalBookPricesSingleBookLabelsLayout();

	}

	// ----------------------------------------------------------------//
	// total financial balance for all users

	private static final VBox totalfinancialBalancePane = new VBox();

	private void totalfinancialBalancePaneLayout() {

		totalfinancialBalancePane.setId("financialPane");

		totalfinancialBalancePane.prefHeightProperty().bind(primaryPane.heightProperty().divide(7));
		totalfinancialBalancePane.prefWidthProperty().bind(primaryPane.widthProperty());

		primaryPane.getChildren().add(totalfinancialBalancePane);

		// set layouts
		this.totalFinancialBalanceTextPaneLayout();
		this.totalFinancialBalanceLabelsPaneLayout();
		this.totalFinancialBalanceSingleBookLabelsPaneLayout();

	}

	// total financial balance Text pane
	private static final HBox totalFinancialBalanceTextPane = new HBox();

	private void totalFinancialBalanceTextPaneLayout() {

		totalFinancialBalanceTextPane.setId("financialPane");

		totalFinancialBalanceTextPane.prefHeightProperty().bind(totalfinancialBalancePane.heightProperty().divide(3));
		totalFinancialBalanceTextPane.prefWidthProperty().bind(totalfinancialBalancePane.widthProperty());

		totalfinancialBalancePane.getChildren().add(totalFinancialBalanceTextPane);

		this.totalFinancialBalanceLabelLayout();

	}

	// total financial balance Labels pane
	private static final HBox totalFinancialBalanceLabelsPane = new HBox();

	private void totalFinancialBalanceLabelsPaneLayout() {

		totalFinancialBalanceLabelsPane.setId("financialPane");

		totalFinancialBalanceLabelsPane.prefHeightProperty().bind(totalfinancialBalancePane.heightProperty().divide(3));
		totalFinancialBalanceLabelsPane.prefWidthProperty().bind(totalfinancialBalancePane.widthProperty());

		totalfinancialBalancePane.getChildren().add(totalFinancialBalanceLabelsPane);

		// set layouts
		this.totalFinancialBalanceLabelsLayout();

	}

	// total financial balance for single user Labels pane
	private static final HBox totalFinancialBalanceSingleuserLabelsPane = new HBox(5);

	private void totalFinancialBalanceSingleBookLabelsPaneLayout() {

		totalFinancialBalanceSingleuserLabelsPane.setId("financialPane");

		totalFinancialBalanceSingleuserLabelsPane.prefHeightProperty()
				.bind(totalfinancialBalancePane.heightProperty().divide(3));
		totalFinancialBalanceSingleuserLabelsPane.prefWidthProperty().bind(totalfinancialBalancePane.widthProperty());

		totalfinancialBalancePane.getChildren().add(totalFinancialBalanceSingleuserLabelsPane);

		// set layouts
		this.totalFinancialBalanceSingleBookLabelsLayout();

	}

	// ------------------------------------------------------------------//
	// fees from borrowing table view pane
	private static final VBox feesFromBorrowingPane = new VBox();

	private void feesFromBorrowingPaneLayout() {

		feesFromBorrowingPane.setId("financialPane");

		feesFromBorrowingPane.prefHeightProperty().bind(primaryPane.heightProperty().multiply(4).divide(7));
		feesFromBorrowingPane.prefWidthProperty().bind(primaryPane.widthProperty());

		primaryPane.getChildren().add(feesFromBorrowingPane);

		// set layout
		this.tableViewPaneLayout();
		this.singleUserFeesPaneLayout();

	}

	// table view pane
	private static final HBox tableViewPane = new HBox();

	private void tableViewPaneLayout() {

		tableViewPane.setId("financialPane");

		tableViewPane.prefHeightProperty().bind(feesFromBorrowingPane.heightProperty().multiply(3).divide(4));
		tableViewPane.prefWidthProperty().bind(feesFromBorrowingPane.widthProperty());

		feesFromBorrowingPane.getChildren().add(tableViewPane);

		this.BorrowingFinancialTableLayout();

	}

	// single user fees
	private static final HBox singleUserFeesPane = new HBox(5);

	private void singleUserFeesPaneLayout() {

		singleUserFeesPane.setId("financialPane");

		singleUserFeesPane.prefHeightProperty().bind(feesFromBorrowingPane.heightProperty().divide(4));
		singleUserFeesPane.prefWidthProperty().bind(feesFromBorrowingPane.widthProperty());

		feesFromBorrowingPane.getChildren().add(singleUserFeesPane);

		// set layouts
		this.singleUserFeesLayout();
		this.exportInfoPaneLayout();

	}

	// -----------------------------------------------------------------//
	// export information to file pane
	private static final VBox exportInfoPane = new VBox();

	private void exportInfoPaneLayout() {

		exportInfoPane.setId("financialPane");

		exportInfoPane.prefHeightProperty().bind(singleUserFeesPane.heightProperty());
		exportInfoPane.prefWidthProperty().bind(singleUserFeesPane.widthProperty().divide(5));

		singleUserFeesPane.getChildren().add(exportInfoPane);

		// set layouts
		this.exportInfoLayout();

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// employees' salaries

	// total salaries text
	private static final Label totalSalariesLabel = new Label("Total salaries for all employees");

	private void totalSalariesLabelLayout() {

		totalSalariesLabel.setId("financialPrimaryLabel");

		totalSalariesLabel.prefHeightProperty().bind(totalSalariesTextPane.heightProperty().divide(2));
		totalSalariesLabel.prefWidthProperty().bind(totalSalariesTextPane.widthProperty());

		totalSalariesTextPane.getChildren().add(totalSalariesLabel);

	}

	// total salaries labels
	protected static final Label hourlyEmployeesTotalSalaries = new Label(),
			monthlyEmployeesTotalSalaries = new Label(), allEmployeesTotalSalaries = new Label();

	private void totalSalariesLabelsLayout() {

		// hourly employee
		hourlyEmployeesTotalSalaries.setId("financialLabel");
		hourlyEmployeesTotalSalaries.setAlignment(Pos.CENTER_LEFT);

		hourlyEmployeesTotalSalaries.prefHeightProperty().bind(totalSalariesLabelsPane.heightProperty());
		hourlyEmployeesTotalSalaries.prefWidthProperty().bind(totalSalariesLabelsPane.widthProperty().divide(3));

		totalSalariesLabelsPane.getChildren().add(hourlyEmployeesTotalSalaries);

		// MONTHHLY employee
		monthlyEmployeesTotalSalaries.setId("financialLabel");
		monthlyEmployeesTotalSalaries.setAlignment(Pos.CENTER_LEFT);

		monthlyEmployeesTotalSalaries.prefHeightProperty().bind(totalSalariesLabelsPane.heightProperty());
		monthlyEmployeesTotalSalaries.prefWidthProperty().bind(totalSalariesLabelsPane.widthProperty().divide(3));

		totalSalariesLabelsPane.getChildren().add(monthlyEmployeesTotalSalaries);

		// all employee
		allEmployeesTotalSalaries.setId("financialLabel");
		allEmployeesTotalSalaries.setAlignment(Pos.CENTER_LEFT);

		allEmployeesTotalSalaries.prefHeightProperty().bind(totalSalariesLabelsPane.heightProperty());
		allEmployeesTotalSalaries.prefWidthProperty().bind(totalSalariesLabelsPane.widthProperty().divide(3));

		totalSalariesLabelsPane.getChildren().add(allEmployeesTotalSalaries);

	}

	// ------------------------------------------------------------//
	// books' prices

	// total prices
	private static final Label totalBookPricesLabel = new Label("Total prices for all books in the library");

	private void totalBookPricesLabelLayout() {

		totalBookPricesLabel.setId("financialPrimaryLabel");

		totalBookPricesLabel.prefHeightProperty().bind(totalBookPricesTextPane.heightProperty());
		totalBookPricesLabel.prefWidthProperty().bind(totalBookPricesTextPane.widthProperty());

		totalBookPricesTextPane.getChildren().add(totalBookPricesLabel);

	}

	// total prices Label
	protected static final Label bookPricesLabel = new Label();

	private void totalBookPricesLabelsLayout() {

		// hourly employee
		bookPricesLabel.setId("financialLabel");
		bookPricesLabel.setAlignment(Pos.CENTER_LEFT);

		bookPricesLabel.prefHeightProperty().bind(totalBookPricesLabelsPane.heightProperty());
		bookPricesLabel.prefWidthProperty().bind(totalBookPricesLabelsPane.widthProperty().divide(3));

		totalBookPricesLabelsPane.getChildren().add(bookPricesLabel);
	}

	// total price single book
	protected static final TextField bookName = new TextField();
	protected static final ComboBox<String> publisherName = new ComboBox<String>();
	protected static final Label singleBookPricesLabel = new Label();

	private void totalBookPricesSingleBookLabelsLayout() {

		// TextField
		bookName.setId("adminTextField");
		bookName.setAlignment(Pos.CENTER_LEFT);
		bookName.setPromptText("book name");

		bookName.prefHeightProperty().bind(totalBookPricesSingleBookLabelsPane.heightProperty());
		bookName.prefWidthProperty().bind(totalBookPricesSingleBookLabelsPane.widthProperty().divide(5));

		totalBookPricesSingleBookLabelsPane.getChildren().add(bookName);

		// ComboBox
		publisherName.setId("adminComboBox");
		publisherName.setPromptText("publisher name");

		publisherName.prefHeightProperty().bind(totalBookPricesSingleBookLabelsPane.heightProperty());
		publisherName.prefWidthProperty().bind(totalBookPricesSingleBookLabelsPane.widthProperty().divide(5));

		totalBookPricesSingleBookLabelsPane.getChildren().add(publisherName);

		// Label
		singleBookPricesLabel.setId("financialLabel");
		singleBookPricesLabel.setAlignment(Pos.CENTER_LEFT);

		singleBookPricesLabel.prefHeightProperty().bind(totalBookPricesSingleBookLabelsPane.heightProperty());
		singleBookPricesLabel.prefWidthProperty()
				.bind(totalBookPricesSingleBookLabelsPane.widthProperty().multiply(2).divide(5));

		totalBookPricesSingleBookLabelsPane.getChildren().add(singleBookPricesLabel);

	}

	// ------------------------------------------------------------//
	// total financial balance

	// total financial balance
	private static final Label totalFinancialBalanceLabel = new Label("Total financial balance for all users");

	private void totalFinancialBalanceLabelLayout() {

		totalFinancialBalanceLabel.setId("financialPrimaryLabel");

		totalFinancialBalanceLabel.prefHeightProperty().bind(totalFinancialBalanceTextPane.heightProperty());
		totalFinancialBalanceLabel.prefWidthProperty().bind(totalFinancialBalanceTextPane.widthProperty());

		totalFinancialBalanceTextPane.getChildren().add(totalFinancialBalanceLabel);

	}

	// labels
	protected static final Label totalFinancialLabel = new Label(), employeesTotalFinancialLabel = new Label(),
			membersTotalFinancialLabel = new Label();

	private void totalFinancialBalanceLabelsLayout() {

		// employees
		employeesTotalFinancialLabel.setId("financialLabel");
		employeesTotalFinancialLabel.setAlignment(Pos.CENTER_LEFT);

		employeesTotalFinancialLabel.prefHeightProperty().bind(totalFinancialBalanceLabelsPane.heightProperty());
		employeesTotalFinancialLabel.prefWidthProperty()
				.bind(totalFinancialBalanceLabelsPane.widthProperty().divide(3));

		totalFinancialBalanceLabelsPane.getChildren().add(employeesTotalFinancialLabel);

		// members
		membersTotalFinancialLabel.setId("financialLabel");
		membersTotalFinancialLabel.setAlignment(Pos.CENTER_LEFT);

		membersTotalFinancialLabel.prefHeightProperty().bind(totalFinancialBalanceLabelsPane.heightProperty());
		membersTotalFinancialLabel.prefWidthProperty().bind(totalFinancialBalanceLabelsPane.widthProperty().divide(3));

		totalFinancialBalanceLabelsPane.getChildren().add(membersTotalFinancialLabel);

		// total
		totalFinancialLabel.setId("financialLabel");
		totalFinancialLabel.setAlignment(Pos.CENTER_LEFT);

		totalFinancialLabel.prefHeightProperty().bind(totalFinancialBalanceLabelsPane.heightProperty());
		totalFinancialLabel.prefWidthProperty().bind(totalFinancialBalanceLabelsPane.widthProperty().divide(3));

		totalFinancialBalanceLabelsPane.getChildren().add(totalFinancialLabel);
	}

	// single user
	protected static final TextField userIdFinancial = new TextField();
	protected static final Label totalFinancialBalanceSingleUserLabel = new Label();

	private void totalFinancialBalanceSingleBookLabelsLayout() {

		// TextField
		userIdFinancial.setId("adminTextField");
		userIdFinancial.setPromptText("user id");

		userIdFinancial.prefHeightProperty().bind(totalFinancialBalanceSingleuserLabelsPane.heightProperty());
		userIdFinancial.prefWidthProperty()
				.bind(totalFinancialBalanceSingleuserLabelsPane.widthProperty().multiply(2).divide(5));

		totalFinancialBalanceSingleuserLabelsPane.getChildren().add(userIdFinancial);

		// label
		totalFinancialBalanceSingleUserLabel.setId("financialLabel");

		totalFinancialBalanceSingleUserLabel.prefHeightProperty()
				.bind(totalFinancialBalanceSingleuserLabelsPane.heightProperty());
		totalFinancialBalanceSingleUserLabel.prefWidthProperty()
				.bind(totalFinancialBalanceSingleuserLabelsPane.widthProperty().multiply(2).divide(5));

		totalFinancialBalanceSingleuserLabelsPane.getChildren().add(totalFinancialBalanceSingleUserLabel);

	}

	// ----------------------------------------------------//
	// table view
	protected static final TableView<BorrowingFinancial> table = new TableView<BorrowingFinancial>();

	private void BorrowingFinancialTableLayout() {

		table.prefWidthProperty().bind(tableViewPane.widthProperty());
		table.prefHeightProperty().bind(tableViewPane.heightProperty());

		table.setId("adminTableView");

		table.setEditable(true);

		tableViewPane.getChildren().add(table);

		// set layouts
		this.addColumns();
	}

	protected static final TableColumn<BorrowingFinancial, String> bookNameColumn = new TableColumn<BorrowingFinancial, String>(
			"book");
	protected static final TableColumn<BorrowingFinancial, String> publisherNameColumn = new TableColumn<BorrowingFinancial, String>(
			"publisher");
	protected static final TableColumn<BorrowingFinancial, String> userIdColumn = new TableColumn<BorrowingFinancial, String>(
			"user id");
	protected static final TableColumn<BorrowingFinancial, String> addingDateColumn = new TableColumn<BorrowingFinancial, String>(
			"adding date");
	protected static final TableColumn<BorrowingFinancial, String> financialColumn = new TableColumn<BorrowingFinancial, String>(
			"financial");

	private void addColumns() {

		table.getColumns().addAll(bookNameColumn, publisherNameColumn, userIdColumn, addingDateColumn, financialColumn);

		bookNameColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
		publisherNameColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
		userIdColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
		addingDateColumn.prefWidthProperty().bind(table.widthProperty().divide(5));
		financialColumn.prefWidthProperty().bind(table.widthProperty().divide(5));

	}

	// ----------------------------------------------------//
	// single user fees
	protected static final TextField userIdFees = new TextField();
	protected static final Label totalFeesSingleUserLabel = new Label();

	private void singleUserFeesLayout() {

		// TextField
		userIdFees.setId("adminTextField");
		userIdFees.setPromptText("user id");

		userIdFees.prefHeightProperty().bind(singleUserFeesPane.heightProperty());
		userIdFees.prefWidthProperty().bind(singleUserFeesPane.widthProperty().multiply(2).divide(5));

		singleUserFeesPane.getChildren().add(userIdFees);

		// label
		totalFeesSingleUserLabel.setId("financialPrimaryLabel");

		totalFeesSingleUserLabel.prefHeightProperty().bind(singleUserFeesPane.heightProperty());
		totalFeesSingleUserLabel.prefWidthProperty().bind(singleUserFeesPane.widthProperty().divide(5));

		singleUserFeesPane.getChildren().add(totalFeesSingleUserLabel);

	}

	// --------------------------------------------------//
	// export information
	protected static final Button financialInfoExportToFileButton = new Button("Export information");
	protected static final Label financialInfoExportToFileLabel = new Label();

	private void exportInfoLayout() {

		// Button
		financialInfoExportToFileButton.setId("adminButton");

		financialInfoExportToFileButton.prefHeightProperty().bind(exportInfoPane.heightProperty().divide(2));
		financialInfoExportToFileButton.prefWidthProperty().bind(exportInfoPane.widthProperty());

		exportInfoPane.getChildren().add(financialInfoExportToFileButton);

		// Label
		financialInfoExportToFileLabel.setId("exportLabel");

		financialInfoExportToFileLabel.prefHeightProperty().bind(exportInfoPane.heightProperty().divide(2));
		financialInfoExportToFileLabel.prefWidthProperty().bind(exportInfoPane.widthProperty());

		exportInfoPane.getChildren().add(financialInfoExportToFileLabel);
	}
}
