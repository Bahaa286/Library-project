package Layout;

import java.sql.SQLException;

import Controls.AdminAccountControl;
import Controls.BookControl;
import Controls.FinancialControl;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import javafx.scene.text.Text;
import library.HourlyEmployee;
import library.Member;
import library.MonthlyEmployee;

public class AdminLayout extends Pane {

	// --------------------------------------------//
	// basic width
	protected static final int WIDTH = 1300;

	// basic height
	protected static final int HEIGHT = 600;
	// --------------------------------------------//

	private static boolean usedLayout = false;

	// constructor
	public AdminLayout() throws ClassNotFoundException, SQLException {

		if (!usedLayout) {

			usedLayout = true;
			// set layouts
			verticalPaneLayout();

			setPrefSize(WIDTH, HEIGHT);
		} // end if
	}

	private static final VBox verticalPane = new VBox();

	private void verticalPaneLayout() {

		verticalPane.prefHeightProperty().bind(heightProperty());
		verticalPane.prefWidthProperty().bind(widthProperty());

		getChildren().add(verticalPane);

		// set layouts
		primaryUpperPaneLayout();
		adminPaneLayout();
	}
	// --------------------------------------------------------------------------------//
	// upper pane

	private static final HBox primaryUpperPane = new HBox();

	private void primaryUpperPaneLayout() {

		primaryUpperPane.prefHeightProperty().bind(verticalPane.heightProperty().divide(10));
		primaryUpperPane.prefWidthProperty().bind(verticalPane.widthProperty());

		primaryUpperPane.setAlignment(Pos.CENTER);

		verticalPane.getChildren().add(primaryUpperPane);

		// setLayout
		primaryTextPaneLayout();
		comboBoxPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// text pane
	private static final HBox primaryTextPane = new HBox();

	private void primaryTextPaneLayout() {

		primaryTextPane.prefHeightProperty().bind(verticalPane.heightProperty().divide(10));
		primaryTextPane.prefWidthProperty().bind(verticalPane.widthProperty().multiply(4).divide(5));

		primaryTextPane.setAlignment(Pos.CENTER);

		primaryUpperPane.getChildren().add(primaryTextPane);

		// set layouts
		primaryTextLayout();

	}

	// --------------------------------------------------------------------------------//
	// comboBox pane
	private static final VBox comboBoxPane = new VBox();

	private void comboBoxPaneLayout() {

		comboBoxPane.prefHeightProperty().bind(verticalPane.heightProperty().divide(10));
		comboBoxPane.prefWidthProperty().bind(verticalPane.widthProperty().divide(5));

		comboBoxPane.setAlignment(Pos.CENTER);

		primaryUpperPane.getChildren().add(comboBoxPane);

		// set layouts
		comboBoxLayout();
		comboBoxLabelLyout();

	}

	// --------------------------------------------------------------------------------//
	// table && newEmployee && book panes

	protected static int AdminShownPane = 0;
	protected static final int TABLE = 1;
	protected static final int NEWMPLOYEE = 2;
	protected static final int BOOK = 3;
	protected static final int NEWMEMBER = 4;
	protected static final int FINANCIAL = 5;
	protected static final int ACCOUNT = 6;

	protected void addPane(int num) {
		
		AdminShownPane = num;

		if (num == TABLE) { // tables
			adminPane.getChildren().clear();
			adminPane.getChildren().add(horizontalTablesPane);	
		} else if (num == NEWMPLOYEE) { // new employee
			adminPane.getChildren().clear();
			adminPane.getChildren().add(newEmployeePane);
		} else if (num == BOOK) { // book
			bookControl.addPane();
		} else if (num == NEWMEMBER) { // new member
			adminPane.getChildren().clear();
			adminPane.getChildren().add(newMemberloyeePane);
		} else if(num == FINANCIAL) { // financial
			financialControl.addOnPane();
		} else if(num == ACCOUNT) { // account
			adminAccountControl.addOnPane();
		}

	}

	// --------------------------------------------------------------------------------//
	// primary administrator pane
	private static final HBox adminPane = new HBox();

	private void adminPaneLayout() {

		adminPane.prefHeightProperty().bind(heightProperty().multiply(9).divide(10));
		adminPane.prefWidthProperty().bind(widthProperty());

		verticalPane.getChildren().add(adminPane);

		// set layouts
		this.horizontalTablesPaneLayout();
		this.newEmployeePaneLayout();
		this.newMemberPaneLayout();

	}

	// ------------------------------------------------------------------------------------------------------//
	// ------------------------------------------------------------------------------------------------------//
	// newEmployee pane

	private static final HBox newEmployeePane = new HBox();

	private void newEmployeePaneLayout() {

		newEmployeePane.prefHeightProperty().bind(adminPane.heightProperty());
		newEmployeePane.prefWidthProperty().bind(adminPane.widthProperty());

		// set layouts
		this.listViewPaneLayout();
		this.newEmployeeInformationPaneLayout();

	}

	// new employee ListView pane
	private static final VBox newEmpListViewPane = new VBox();

	private void listViewPaneLayout() {

		newEmpListViewPane.prefHeightProperty().bind(newEmployeePane.heightProperty());
		newEmpListViewPane.prefWidthProperty().bind(newEmployeePane.widthProperty().divide(5));

		newEmployeePane.getChildren().add(newEmpListViewPane);

		// set layouts
		this.newEmployeeListViewLayout();

	}

	// new Employee information
	private static final VBox newEmployeeInformationPane = new VBox();

	private void newEmployeeInformationPaneLayout() {

		newEmployeeInformationPane.prefHeightProperty().bind(newEmployeePane.heightProperty());
		newEmployeeInformationPane.prefWidthProperty().bind(newEmployeePane.widthProperty().multiply(4).divide(5));

		newEmployeePane.getChildren().add(newEmployeeInformationPane);

		// set layouts
		this.newEmployeeInformationLayout();
		this.newEmpSalaryPaneLayout();
		this.newEmployeeRulesPaneLayout();
		this.newEmpButtonPaneLayout();

	}

	// salary pane
	private static final HBox newEmpSalaryPane = new HBox(5);

	private void newEmpSalaryPaneLayout() {

		newEmpSalaryPane.prefHeightProperty().bind(newEmployeeInformationPane.heightProperty().divide(7));
		newEmpSalaryPane.prefWidthProperty().bind(newEmployeeInformationPane.widthProperty().multiply(9).divide(10));

		newEmployeeInformationPane.getChildren().add(newEmpSalaryPane);

		// setLayout
		this.newEmpSalaryLayout();
	}

	// rules pane
	private static final HBox newEmployeeRulesPane = new HBox(5);

	private void newEmployeeRulesPaneLayout() {

		newEmployeeRulesPane.prefHeightProperty().bind(newEmployeeInformationPane.heightProperty().divide(7));
		newEmployeeRulesPane.prefWidthProperty()
				.bind(newEmployeeInformationPane.widthProperty().multiply(9).divide(10));

		newEmployeeInformationPane.getChildren().add(newEmployeeRulesPane);

		// setLayout
		this.newEmpRulesLayout();

	}

	// button pane
	private static final HBox newEmpButtonPane = new HBox(5);

	private void newEmpButtonPaneLayout() {

		newEmpButtonPane.prefHeightProperty().bind(newEmployeeInformationPane.heightProperty().divide(7));
		newEmpButtonPane.prefWidthProperty().bind(newEmployeeInformationPane.widthProperty().multiply(9).divide(10));

		newEmployeeInformationPane.getChildren().add(newEmpButtonPane);

		// set layouts
		newEmpButtonLayout();

	}

	
	// ----------------------------------------------------------------//
	// new members pane
	private static final HBox newMemberloyeePane = new HBox();

	private void newMemberPaneLayout() {

		newMemberloyeePane.prefHeightProperty().bind(adminPane.heightProperty());
		newMemberloyeePane.prefWidthProperty().bind(adminPane.widthProperty());

		// set layouts
		this.newMemberlistViewPaneLayout();
		this.newMemberInformationPaneLayout();

	}

	// new employee ListView pane
	private static final VBox newMemberListViewPane = new VBox();

	private void newMemberlistViewPaneLayout() {

		newMemberListViewPane.prefHeightProperty().bind(newMemberloyeePane.heightProperty());
		newMemberListViewPane.prefWidthProperty().bind(newMemberloyeePane.widthProperty().divide(5));

		newMemberloyeePane.getChildren().add(newMemberListViewPane);

		// set layouts
		this.newMemberListViewLayout();

	}

	// new Employee information
	private static final VBox newMemberInformationPane = new VBox();

	private void newMemberInformationPaneLayout() {

		newMemberInformationPane.prefHeightProperty().bind(newMemberloyeePane.heightProperty());
		newMemberInformationPane.prefWidthProperty().bind(newMemberloyeePane.widthProperty().multiply(4).divide(5));

		newMemberloyeePane.getChildren().add(newMemberInformationPane);

		// set layouts
		this.newMemberInformationLayout();
		this.newMemberButtonPaneLayout();

	}

	// button pane
	private static final HBox newMemberButtonPane = new HBox(5);

	private void newMemberButtonPaneLayout() {

		newMemberButtonPane.prefHeightProperty().bind(newMemberInformationPane.heightProperty().divide(7));
		newMemberButtonPane.prefWidthProperty().bind(newMemberInformationPane.widthProperty().multiply(9).divide(10));

		newMemberInformationPane.getChildren().add(newMemberButtonPane);

		// set layouts
		this.newMemberButtonLayout();

	}

	
	
	
	// -------------------------------------------------------------------------------------------------------//
	// -------------------------------------------------------------------------------------------------------//
	// primary horizontal tables pane layout

	private static final HBox horizontalTablesPane = new HBox();

	private void horizontalTablesPaneLayout() {

		horizontalTablesPane.prefHeightProperty().bind(heightProperty().multiply(9).divide(10));
		horizontalTablesPane.prefWidthProperty().bind(widthProperty());

		// setLayout
		this.controlPaneLayout();
		this.tablePaneLayout();
	}

	// --------------------------------------------------------------------------------//
	// controls pane

	private static final VBox controlPane = new VBox();

	private void controlPaneLayout() {

		controlPane.prefHeightProperty().bind(horizontalTablesPane.heightProperty());
		controlPane.prefWidthProperty().bind(horizontalTablesPane.widthProperty().divide(5));

		controlPane.setId("controlPane");

		horizontalTablesPane.getChildren().add(controlPane);

		// set layouts
		memberControlPaneLayout();
		hourlyEmployeeControlPaneLayout();
		monthlyEmployeeControlPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// controls member options pane

	protected static final VBox memberControlPane = new VBox();

	private void memberControlPaneLayout() {

		memberControlPane.prefHeightProperty().bind(controlPane.heightProperty());
		memberControlPane.prefWidthProperty().bind(controlPane.widthProperty());

		memberControlPane.setId("controlPane");

		// set layouts
		memberOptionsPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// member controls options pane

	protected static final HBox[] memberOptionsPane = new HBox[9];

	private void memberOptionsPaneLayout() {

		// for each optionsPane
		for (int i = 0; i < memberOptionsPane.length; i++) {

			// reference for each optionsPane
			memberOptionsPane[i] = new HBox();

			memberOptionsPane[i].prefHeightProperty().bind(controlPane.heightProperty().divide(9));
			memberOptionsPane[i].prefWidthProperty().bind(controlPane.widthProperty());

			memberOptionsPane[i].setAlignment(Pos.CENTER);
			memberOptionsPane[i].setStyle("-fx-border-color: Aqua");

			memberControlPane.getChildren().add(memberOptionsPane[i]);
		} // end for loop

		// set layout
		memberOptionsLayout();

	}

	// --------------------------------------------------------------------------------//
	// controls hourly employee options pane

	protected static final VBox hourlyEmployeeControlPane = new VBox();

	private void hourlyEmployeeControlPaneLayout() {

		hourlyEmployeeControlPane.prefHeightProperty().bind(controlPane.heightProperty());
		hourlyEmployeeControlPane.prefWidthProperty().bind(controlPane.widthProperty());

		hourlyEmployeeControlPane.setId("controlPane");

		// set layouts
		hourlymployeeOptionsPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// hourly employee controls options pane

	protected static final HBox[] hourlyEmployeeOptionsPane = new HBox[12];

	private void hourlymployeeOptionsPaneLayout() {

		// for each optionsPane
		for (int i = 0; i < hourlyEmployeeOptionsPane.length; i++) {

			// reference for each optionsPane
			hourlyEmployeeOptionsPane[i] = new HBox();

			hourlyEmployeeOptionsPane[i].prefHeightProperty().bind(controlPane.heightProperty().divide(10));
			hourlyEmployeeOptionsPane[i].prefWidthProperty().bind(controlPane.widthProperty());

			hourlyEmployeeOptionsPane[i].setAlignment(Pos.CENTER);
			hourlyEmployeeOptionsPane[i].setStyle("-fx-border-color: Aqua");

			hourlyEmployeeControlPane.getChildren().add(hourlyEmployeeOptionsPane[i]);
		} // end for loop

		// set layout
		hourlyEmployeeOptionsLayout();

	}

	// --------------------------------------------------------------------------------//
	// controls hourly employee options pane

	protected static final VBox monthlyEmployeeControlPane = new VBox();

	private void monthlyEmployeeControlPaneLayout() {

		monthlyEmployeeControlPane.prefHeightProperty().bind(controlPane.heightProperty());
		monthlyEmployeeControlPane.prefWidthProperty().bind(controlPane.widthProperty());

		monthlyEmployeeControlPane.setId("controlPane");

		// set layouts
		monthlyEmployeeOptionsPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// hourly employee controls options pane

	protected static final HBox[] monthlyEmployeeOptionsPane = new HBox[13];

	private void monthlyEmployeeOptionsPaneLayout() {

		// for each optionsPane
		for (int i = 0; i < monthlyEmployeeOptionsPane.length; i++) {

			// reference for each optionsPane
			monthlyEmployeeOptionsPane[i] = new HBox();

			monthlyEmployeeOptionsPane[i].prefHeightProperty().bind(controlPane.heightProperty().divide(10));
			monthlyEmployeeOptionsPane[i].prefWidthProperty().bind(controlPane.widthProperty());

			monthlyEmployeeOptionsPane[i].setAlignment(Pos.CENTER);
			monthlyEmployeeOptionsPane[i].setStyle("-fx-border-color: Aqua");

			monthlyEmployeeControlPane.getChildren().add(monthlyEmployeeOptionsPane[i]);
		} // end for loop

		// set layout
		monthlyEmployeeOptionsLayout();

	}

	// --------------------------------------------------------------------------------//
	// tableButtons pane

	private static final HBox tableButtonsPane = new HBox(5);

	private void tableButtonsPaneLayout() {

		tableButtonsPane.prefHeightProperty().bind(tablePane.heightProperty().divide(10));
		tableButtonsPane.prefWidthProperty().bind(tablePane.widthProperty());

		tablePane.getChildren().add(tableButtonsPane);

		// set layouts
		tableButtonsPane();

	}

	// --------------------------------------------------------------------------------//
	// table pane

	protected static final VBox tablePane = new VBox();

	private void tablePaneLayout() {

		tablePane.prefHeightProperty().bind(horizontalTablesPane.heightProperty());
		tablePane.prefWidthProperty().bind(horizontalTablesPane.widthProperty().multiply(4).divide(5));

		tablePane.setId("tablePane");

		horizontalTablesPane.getChildren().add(tablePane);

		// set layouts
		this.tableButtonsPaneLayout();
		this.tableViewPaneLayout();
		this.buttonPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// tableView pane

	protected static final HBox tableViewPane = new HBox();

	private void tableViewPaneLayout() {

		tableViewPane.prefHeightProperty().bind(tablePane.heightProperty().multiply(8).divide(10));
		tableViewPane.prefWidthProperty().bind(tablePane.widthProperty());

		tablePane.getChildren().add(tableViewPane);

		// set layouts
		this.rulesPaneLayout();
		this.memberTableLayout();
		this.hourlyEmployeeTableLayout();
		this.monthlyEmployeeTableLayout();

	}

	// ---------------------------------------------------------------------------------//
	// rules pane

	protected static final VBox rulesPane = new VBox();

	private void rulesPaneLayout() {

		rulesPane.prefHeightProperty().bind(tableViewPane.heightProperty());
		rulesPane.prefWidthProperty().bind(tableViewPane.widthProperty().multiply(1).divide(5));

		// set layouts
		this.rulesListViewLayout();
		this.rulesSingleEmpComboBoxLayout();
		this.rulesPaneForSingleEmpLayout();

	}

	// rules options pane
	private static final HBox rulesPaneForSingleEmp = new HBox();

	private void rulesPaneForSingleEmpLayout() {

		rulesPaneForSingleEmp.prefHeightProperty().bind(rulesPane.heightProperty().divide(10));
		rulesPaneForSingleEmp.prefWidthProperty().bind(rulesPane.widthProperty());

		rulesPane.getChildren().add(rulesPaneForSingleEmp);

		// set layouts
		this.rulesForSingleEmpLayout();
	}

	// ---------------------------------------------------------------------------------//
	// button pane

	private static final HBox buttonPane = new HBox(10);

	private void buttonPaneLayout() {

		buttonPane.prefHeightProperty().bind(tablePane.heightProperty().divide(10));
		buttonPane.prefWidthProperty().bind(tablePane.widthProperty());

		tablePane.getChildren().add(buttonPane);

		// set layouts
		hourlyEmployeeButtonPaneLayout();
		monthlyEmployeeButtonPaneLayout();
		memberButtonPaneLayout();

	}

	// --------------------------------------------------------------------------------//
	// member delete and refresh button pane
	private static final HBox memberButtonPane = new HBox(10);

	private void memberButtonPaneLayout() {

		memberButtonPane.prefHeightProperty().bind(tablePane.heightProperty().divide(10));
		memberButtonPane.prefWidthProperty().bind(tablePane.widthProperty());

		// set layouts
		memberButtonLayout();

	}

	// --------------------------------------------------------------------------------//
	// hourly employee delete and refresh button pane
	private static final HBox hourlyEmployeeButtonPane = new HBox(10);

	private void hourlyEmployeeButtonPaneLayout() {

		hourlyEmployeeButtonPane.prefHeightProperty().bind(tablePane.heightProperty().divide(10));
		hourlyEmployeeButtonPane.prefWidthProperty().bind(tablePane.widthProperty());

		// set layouts
		hourlyEmployeeButtonLayout();

	}

	// --------------------------------------------------------------------------------//
	// monthly employee delete and refresh button pane
	private static final HBox monthlyEmployeeButtonPane = new HBox(10);

	private void monthlyEmployeeButtonPaneLayout() {

		monthlyEmployeeButtonPane.prefHeightProperty().bind(tablePane.heightProperty().divide(10));
		monthlyEmployeeButtonPane.prefWidthProperty().bind(tablePane.widthProperty());

		// set layouts
		monthlyEmployeeButtonLayout();

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	// text

	protected static final Text primaryText = new Text("Administrator page");;

	private void primaryTextLayout() {

		primaryText.setId("adminText");

		primaryTextPane.getChildren().add(primaryText);

	}

	// --------------------------------------------------------------------------------//
	// comboBox

	protected static final ComboBox<String> comboBox = new ComboBox<String>();

	private void comboBoxLayout() {

		comboBox.setId("adminComboBox");

		comboBox.prefHeightProperty().bind(comboBoxPane.heightProperty().divide(2));
		comboBox.prefWidthProperty().bind(comboBoxPane.widthProperty());

		comboBox.getItems().addAll("account", "Employment applications", "new members", "library informations", "book informations", "financial", "Log out");

		comboBoxPane.getChildren().add(comboBox);
	}

	// --------------------------------------------------------------------------------//
	// comboBox label

	protected static final Label comboBoxLabel = new Label();

	private void comboBoxLabelLyout() {

		comboBoxLabel.setId("adminLabel");

		comboBoxLabel.prefHeightProperty().bind(comboBoxPane.heightProperty().divide(2));
		comboBoxLabel.prefWidthProperty().bind(comboBoxPane.widthProperty());

		comboBoxLabel.setAlignment(Pos.CENTER);

		comboBoxPane.getChildren().add(comboBoxLabel);
	}

	// -----------------------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------------------//
	// new employee

	// ListView
	protected static final ListView<String> newEmployeeListView = new ListView<String>();
	protected static final Button selectNewEmpButton = new Button("show information");
	protected static final Label errorSelectNewEmpLabel = new Label();

	private void newEmployeeListViewLayout() {

		// ListVew
		newEmployeeListView.prefHeightProperty().bind(newEmpListViewPane.heightProperty().multiply(8).divide(10));
		newEmployeeListView.prefWidthProperty().bind(newEmpListViewPane.widthProperty());

		newEmployeeListView.setId("adminListView");

		newEmpListViewPane.getChildren().add(newEmployeeListView);

		// button
		selectNewEmpButton.setId("adminButton");
		selectNewEmpButton.setAlignment(Pos.CENTER);

		selectNewEmpButton.prefHeightProperty().bind(newEmpListViewPane.heightProperty().divide(10));
		selectNewEmpButton.prefWidthProperty().bind(newEmpListViewPane.widthProperty());

		newEmpListViewPane.getChildren().add(selectNewEmpButton);

		// error Label
		errorSelectNewEmpLabel.setId("adminerror");
		errorSelectNewEmpLabel.setAlignment(Pos.CENTER);

		errorSelectNewEmpLabel.prefHeightProperty().bind(newEmpListViewPane.heightProperty().divide(10));
		errorSelectNewEmpLabel.prefWidthProperty().bind(newEmpListViewPane.widthProperty());

		newEmpListViewPane.getChildren().add(errorSelectNewEmpLabel);
	}

	// informations
	protected static final Label newEmpName = new Label(), newEmpBirthDate = new Label(), newEmpGender = new Label(),
			newEmpPhone = new Label(), newEmpErrorSalary = new Label(), newEmpErrorRules = new Label();
	protected static final TextField newEmpSalary = new TextField();;
	protected static final Button newEmpAccept = new Button("Accept"), newEmpReject = new Button("Reject");
	protected static final ComboBox<String> newEmpRulesComboBox = new ComboBox<String>();
	protected static final RadioButton hourlyEmpRadioButton = new RadioButton("hourly employee"),
			monthlyEmpRadioButton = new RadioButton("monthy employee");
	private static final ToggleGroup hourlyMonthleEmpToggleGroup = new ToggleGroup();

	private void newEmployeeInformationLayout() {

		// name
		newEmpName.setId("adminNewEmpLabel");

		newEmpName.prefHeightProperty().bind(newEmployeeInformationPane.heightProperty().divide(7));
		newEmpName.prefWidthProperty().bind(newEmployeeInformationPane.widthProperty());

		newEmployeeInformationPane.getChildren().add(newEmpName);

		// birth date
		newEmpBirthDate.setId("adminNewEmpLabel");

		newEmpBirthDate.prefHeightProperty().bind(newEmployeeInformationPane.heightProperty().divide(7));
		newEmpBirthDate.prefWidthProperty().bind(newEmployeeInformationPane.widthProperty());

		newEmployeeInformationPane.getChildren().add(newEmpBirthDate);

		// gender
		newEmpGender.setId("adminNewEmpLabel");

		newEmpGender.prefHeightProperty().bind(newEmployeeInformationPane.heightProperty().divide(7));
		newEmpGender.prefWidthProperty().bind(newEmployeeInformationPane.widthProperty());

		newEmployeeInformationPane.getChildren().add(newEmpGender);

		// phone
		newEmpPhone.setId("adminNewEmpLabel");

		newEmpPhone.prefHeightProperty().bind(newEmployeeInformationPane.heightProperty().divide(7));
		newEmpPhone.prefWidthProperty().bind(newEmployeeInformationPane.widthProperty());

		newEmployeeInformationPane.getChildren().add(newEmpPhone);

	}

	// salary layout
	private void newEmpSalaryLayout() {

		// textField
		newEmpSalary.setPromptText("Salary");
		newEmpSalary.setId("adminTextField");

		newEmpSalary.prefHeightProperty().bind(newEmpSalaryPane.heightProperty());
		newEmpSalary.prefWidthProperty().bind(newEmpSalaryPane.widthProperty().divide(5));

		newEmpSalaryPane.getChildren().add(newEmpSalary);

		// error label

		newEmpErrorSalary.setId("adminerror");
		newEmpErrorSalary.setAlignment(Pos.CENTER);

		newEmpErrorSalary.prefHeightProperty().bind(newEmpSalaryPane.heightProperty());
		newEmpErrorSalary.prefWidthProperty().bind(newEmpSalaryPane.widthProperty().divide(5));

		newEmpSalaryPane.getChildren().add(newEmpErrorSalary);

		// hourly employee radioButton
		hourlyEmpRadioButton.setId("adminRadioButton");
		hourlyEmpRadioButton.setAlignment(Pos.CENTER_LEFT);
		hourlyEmpRadioButton.setToggleGroup(hourlyMonthleEmpToggleGroup);
		hourlyEmpRadioButton.setSelected(false);

		hourlyEmpRadioButton.prefHeightProperty().bind(newEmpSalaryPane.heightProperty());
		hourlyEmpRadioButton.prefWidthProperty().bind(newEmpSalaryPane.widthProperty().divide(4));

		newEmpSalaryPane.getChildren().add(hourlyEmpRadioButton);

		// monthly employee radioButton
		monthlyEmpRadioButton.setId("adminRadioButton");
		monthlyEmpRadioButton.setAlignment(Pos.CENTER_LEFT);
		monthlyEmpRadioButton.setToggleGroup(hourlyMonthleEmpToggleGroup);
		monthlyEmpRadioButton.setSelected(true);

		monthlyEmpRadioButton.prefHeightProperty().bind(newEmpSalaryPane.heightProperty());
		monthlyEmpRadioButton.prefWidthProperty().bind(newEmpSalaryPane.widthProperty().divide(4));

		newEmpSalaryPane.getChildren().add(monthlyEmpRadioButton);

	}

	// rules layout
	private void newEmpRulesLayout() {

		newEmpRulesComboBox.setId("adminComboBox");

		newEmpRulesComboBox.prefHeightProperty().bind(newEmployeeRulesPane.heightProperty());
		newEmpRulesComboBox.prefWidthProperty().bind(newEmployeeRulesPane.widthProperty().divide(2));

		newEmployeeRulesPane.getChildren().add(newEmpRulesComboBox);

		// error label

		newEmpErrorRules.setId("adminerror");
		newEmpErrorRules.setAlignment(Pos.CENTER);

		newEmpErrorRules.prefHeightProperty().bind(newEmployeeRulesPane.heightProperty());
		newEmpErrorRules.prefWidthProperty().bind(newEmployeeRulesPane.widthProperty().divide(2));

		newEmployeeRulesPane.getChildren().add(newEmpErrorRules);

	}

	// buttons layout
	private void newEmpButtonLayout() {

		// accept

		newEmpAccept.setId("adminButton");
		newEmpAccept.setAlignment(Pos.CENTER);

		newEmpAccept.prefHeightProperty().bind(newEmpButtonPane.heightProperty());
		newEmpAccept.prefWidthProperty().bind(newEmpButtonPane.widthProperty().divide(3));

		newEmpButtonPane.getChildren().add(newEmpAccept);

		// reject

		newEmpReject.setId("adminButton");
		newEmpReject.setAlignment(Pos.CENTER);

		newEmpReject.prefHeightProperty().bind(newEmpButtonPane.heightProperty());
		newEmpReject.prefWidthProperty().bind(newEmpButtonPane.widthProperty().divide(3));

		newEmpButtonPane.getChildren().add(newEmpReject);

	}

	// ------------------------------------------------------------------------//
	// new member
	
	// ListView
		protected static final ListView<String> newMemberListView = new ListView<String>();
		protected static final Button selectNewMemberButton = new Button("show information");
		protected static final Label errorSelectNewMemberLabel = new Label();

		private void newMemberListViewLayout() {

			// ListVew
			newMemberListView.prefHeightProperty().bind(newMemberListViewPane.heightProperty().multiply(8).divide(10));
			newMemberListView.prefWidthProperty().bind(newMemberListViewPane.widthProperty());

			newMemberListView.setId("adminListView");

			newMemberListViewPane.getChildren().add(newMemberListView);

			// button
			selectNewMemberButton.setId("adminButton");
			selectNewMemberButton.setAlignment(Pos.CENTER);

			selectNewMemberButton.prefHeightProperty().bind(newMemberListViewPane.heightProperty().divide(10));
			selectNewMemberButton.prefWidthProperty().bind(newMemberListViewPane.widthProperty());

			newMemberListViewPane.getChildren().add(selectNewMemberButton);

			// error Label
			errorSelectNewMemberLabel.setId("adminerror");
			errorSelectNewMemberLabel.setAlignment(Pos.CENTER);

			errorSelectNewMemberLabel.prefHeightProperty().bind(newMemberListViewPane.heightProperty().divide(10));
			errorSelectNewMemberLabel.prefWidthProperty().bind(newMemberListViewPane.widthProperty());

			newMemberListViewPane.getChildren().add(errorSelectNewMemberLabel);
		}
	
		
		// informations
		protected static final Label newMemberName = new Label(), newMemberBirthDate = new Label(), newMemberGender = new Label(),
				newMemberPhone = new Label();
		protected static final Button newMemberAccept = new Button("Accept"), newMemberReject = new Button("Reject");

		// new member information layout
		private void newMemberInformationLayout() {

			// name
			newMemberName.setId("adminNewEmpLabel");

			newMemberName.prefHeightProperty().bind(newMemberInformationPane.heightProperty().divide(7));
			newMemberName.prefWidthProperty().bind(newMemberInformationPane.widthProperty());

			newMemberInformationPane.getChildren().add(newMemberName);

			// birth date
			newMemberBirthDate.setId("adminNewEmpLabel");

			newMemberBirthDate.prefHeightProperty().bind(newMemberInformationPane.heightProperty().divide(7));
			newMemberBirthDate.prefWidthProperty().bind(newMemberInformationPane.widthProperty());

			newMemberInformationPane.getChildren().add(newMemberBirthDate);

			// gender
			newMemberGender.setId("adminNewEmpLabel");

			newMemberGender.prefHeightProperty().bind(newMemberInformationPane.heightProperty().divide(7));
			newMemberGender.prefWidthProperty().bind(newMemberInformationPane.widthProperty());

			newMemberInformationPane.getChildren().add(newMemberGender);

			// phone
			newMemberPhone.setId("adminNewEmpLabel");

			newMemberPhone.prefHeightProperty().bind(newMemberInformationPane.heightProperty().divide(7));
			newMemberPhone.prefWidthProperty().bind(newMemberInformationPane.widthProperty());

			newMemberInformationPane.getChildren().add(newMemberPhone);

		}

		// buttons layout
		private void newMemberButtonLayout() {

			// accept

			newMemberAccept.setId("adminButton");
			newMemberAccept.setAlignment(Pos.CENTER);

			newMemberAccept.prefHeightProperty().bind(newMemberButtonPane.heightProperty());
			newMemberAccept.prefWidthProperty().bind(newMemberButtonPane.widthProperty().divide(3));

			newMemberButtonPane.getChildren().add(newMemberAccept);

			// reject

			newMemberReject.setId("adminButton");
			newMemberReject.setAlignment(Pos.CENTER);

			newMemberReject.prefHeightProperty().bind(newMemberButtonPane.heightProperty());
			newMemberReject.prefWidthProperty().bind(newMemberButtonPane.widthProperty().divide(3));

			newMemberButtonPane.getChildren().add(newMemberReject);

		}


	// -----------------------------------------------------------------------------------------------------//
	// -----------------------------------------------------------------------------------------------------//
	// --------------------------------------------------------------------------------//
	// member options

	protected static final TextField[] memberTextFields = new TextField[8];; // textFields information
	protected static final DatePicker memberDateOfBirthDatePicker = new DatePicker(); // date of birth
	protected static final Label[] memberErrorsOption = new Label[8]; // error labels
	protected static final Button memberInsert = new Button("insert"); // insert button
	protected static final ComboBox<Character> memberGenderComboBox = new ComboBox<Character>(); // gender
	private static final Label memberGenderLabel = new Label("gender"); // gender label

	private void memberOptionsLayout() {

		// -------------------------------------------//
		// member textFields

		// for each TextField
		for (int i = 0; i < memberTextFields.length; i++) {

			// to put the DatePicker after 5 TextFields
			if (i != 3 && i != 2) {
				memberTextFields[i] = new TextField();

				memberTextFields[i].setId("adminTextField");

				memberTextFields[i].prefHeightProperty()
						.bind(memberOptionsPane[i].heightProperty().multiply(9).divide(10));
				memberTextFields[i].prefWidthProperty()
						.bind(memberOptionsPane[i].widthProperty().multiply(2).divide(3));

				memberOptionsPane[i].getChildren().add(memberTextFields[i]);
			} else if (i == 3) { // add the DatePicker

				memberDateOfBirthDatePicker.setId("adminDatePicker");

				memberDateOfBirthDatePicker.prefHeightProperty()
						.bind(memberOptionsPane[i].heightProperty().multiply(9).divide(10));
				memberDateOfBirthDatePicker.prefWidthProperty()
						.bind(memberOptionsPane[i].widthProperty().multiply(2).divide(3));

				memberOptionsPane[i].getChildren().add(memberDateOfBirthDatePicker);

			} else if (i == 2) {

				// label
				memberGenderLabel.setId("adminLabel");

				memberGenderLabel.prefHeightProperty()
						.bind(memberOptionsPane[i].heightProperty().multiply(9).divide(10));
				memberGenderLabel.prefWidthProperty().bind(memberOptionsPane[i].widthProperty().multiply(1).divide(3));

				memberOptionsPane[i].getChildren().add(memberGenderLabel);

				// ComboBox
				memberGenderComboBox.setId("adminChooseComboBox");

				memberGenderComboBox.prefHeightProperty()
						.bind(memberOptionsPane[i].heightProperty().multiply(9).divide(10));
				memberGenderComboBox.prefWidthProperty()
						.bind(memberOptionsPane[i].widthProperty().multiply(1).divide(3));

				memberGenderComboBox.getItems().addAll('M', 'F');

				memberOptionsPane[i].getChildren().add(memberGenderComboBox);

			}

		} // end for loop

		// prompt text
		memberTextFields[0].setPromptText("first name");
		memberTextFields[1].setPromptText("last name");

		memberDateOfBirthDatePicker.setPromptText("birth date");

		memberTextFields[4].setPromptText("financial balance");
		memberTextFields[5].setPromptText("phone number");
		memberTextFields[6].setPromptText("phone number");
		memberTextFields[7].setPromptText("password");

		// -------------------------------------------//
		// errors

		// for each error Label
		for (int i = 0; i < memberErrorsOption.length; i++) {

			memberErrorsOption[i] = new Label();

			memberErrorsOption[i].setId("adminerror");

			memberErrorsOption[i].prefHeightProperty()
					.bind(memberOptionsPane[i].heightProperty().multiply(9).divide(10));
			memberErrorsOption[i].prefWidthProperty().bind(memberOptionsPane[i].widthProperty().divide(3));

			memberOptionsPane[i].getChildren().add(memberErrorsOption[i]);

		} // end for loop

		// -------------------------------------------//
		// button

		memberInsert.setId("adminButton");

		memberInsert.prefHeightProperty().bind(memberOptionsPane[8].heightProperty().multiply(9).divide(10));
		memberInsert.prefWidthProperty().bind(memberOptionsPane[8].widthProperty().multiply(4).divide(5));

		memberOptionsPane[8].getChildren().add(memberInsert);

	}

	// --------------------------------------------------------------------------------//
	// hourly employee options

	protected static final TextField[] hourlyEmployeeTextFields = new TextField[9];; // textFields information
	protected static final DatePicker hourlyEmployeeDateOfBirthDatePicker = new DatePicker(); // date of birth
	protected static final Label[] hourlyEmployeeErrorsOption = new Label[10]; // error labels
	protected static final Button hourlyEmployeeInsert = new Button("insert");; // insert button
	protected static final ComboBox<Character> hourlyEmployeeGenderComboBox = new ComboBox<Character>(); // gender
	private static final Label hourlyEmployeeGenderLabel = new Label("gender"); // gender label
	private static final Label hourlyEmployeeRulesLabel = new Label("rule"); // rules label
	protected static final ComboBox<String> hourlyEmployeeRulesComboBox = new ComboBox<String>(); // rules

	private void hourlyEmployeeOptionsLayout() {

		// -------------------------------------------//
		// hourly employee textFields

		// for each TextField
		for (int i = 0; i < hourlyEmployeeTextFields.length; i++) {

			// to put the DatePicker after 5 TextFields
			if (i != 3 && i != 2) {
				hourlyEmployeeTextFields[i] = new TextField();

				hourlyEmployeeTextFields[i].setId("adminTextField");

				hourlyEmployeeTextFields[i].prefHeightProperty()
						.bind(hourlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
				hourlyEmployeeTextFields[i].prefWidthProperty()
						.bind(hourlyEmployeeOptionsPane[i].widthProperty().multiply(2).divide(3));

				hourlyEmployeeOptionsPane[i].getChildren().add(hourlyEmployeeTextFields[i]);
			} else if (i == 3) {// add the DatePicker

				hourlyEmployeeDateOfBirthDatePicker.setId("adminDatePicker");

				hourlyEmployeeDateOfBirthDatePicker.prefHeightProperty()
						.bind(hourlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
				hourlyEmployeeDateOfBirthDatePicker.prefWidthProperty()
						.bind(hourlyEmployeeOptionsPane[i].widthProperty().multiply(2).divide(3));

				hourlyEmployeeOptionsPane[i].getChildren().add(hourlyEmployeeDateOfBirthDatePicker);

			} else if (i == 2) {

				// label
				hourlyEmployeeGenderLabel.setId("adminLabel");

				hourlyEmployeeGenderLabel.prefHeightProperty()
						.bind(hourlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
				hourlyEmployeeGenderLabel.prefWidthProperty()
						.bind(hourlyEmployeeOptionsPane[i].widthProperty().multiply(1).divide(3));

				hourlyEmployeeOptionsPane[i].getChildren().add(hourlyEmployeeGenderLabel);

				// ComboBox
				hourlyEmployeeGenderComboBox.setId("adminChooseComboBox");

				hourlyEmployeeGenderComboBox.prefHeightProperty()
						.bind(hourlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
				hourlyEmployeeGenderComboBox.prefWidthProperty()
						.bind(hourlyEmployeeOptionsPane[i].widthProperty().multiply(1).divide(3));

				hourlyEmployeeGenderComboBox.getItems().addAll('M', 'F');

				hourlyEmployeeOptionsPane[i].getChildren().add(hourlyEmployeeGenderComboBox);

			}

		} // end for loop

		// prompt text
		hourlyEmployeeTextFields[0].setPromptText("first name");
		hourlyEmployeeTextFields[1].setPromptText("last name");

		hourlyEmployeeDateOfBirthDatePicker.setPromptText("birth date");
		hourlyEmployeeTextFields[4].setPromptText("financial balance");
		hourlyEmployeeTextFields[5].setPromptText("phone number");
		hourlyEmployeeTextFields[6].setPromptText("phone number");
		hourlyEmployeeTextFields[7].setPromptText("password");
		hourlyEmployeeTextFields[8].setPromptText("salary/Hour");

		// -------------------------------------------//
		// rules

		// label
		hourlyEmployeeRulesLabel.setId("adminLabel");

		hourlyEmployeeRulesLabel.prefHeightProperty()
				.bind(hourlyEmployeeOptionsPane[9].heightProperty().multiply(9).divide(10));
		hourlyEmployeeRulesLabel.prefWidthProperty()
				.bind(hourlyEmployeeOptionsPane[9].widthProperty().multiply(1).divide(3));

		hourlyEmployeeOptionsPane[9].getChildren().add(hourlyEmployeeRulesLabel);

		// comoBox
		hourlyEmployeeRulesComboBox.setId("adminChooseComboBox");

		hourlyEmployeeRulesComboBox.prefHeightProperty()
				.bind(hourlyEmployeeOptionsPane[10].heightProperty().multiply(9).divide(10));
		hourlyEmployeeRulesComboBox.prefWidthProperty().bind(hourlyEmployeeOptionsPane[9].widthProperty());

		hourlyEmployeeOptionsPane[10].getChildren().add(hourlyEmployeeRulesComboBox);

		// -------------------------------------------//
		// errors

		// for each error Label
		for (int i = 0; i < hourlyEmployeeErrorsOption.length; i++) {

			hourlyEmployeeErrorsOption[i] = new Label();

			hourlyEmployeeErrorsOption[i].setId("adminerror");

			hourlyEmployeeErrorsOption[i].prefHeightProperty()
					.bind(hourlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
			hourlyEmployeeErrorsOption[i].prefWidthProperty()
					.bind(hourlyEmployeeOptionsPane[i].widthProperty().divide(3));

			hourlyEmployeeOptionsPane[i].getChildren().add(hourlyEmployeeErrorsOption[i]);

		} // end for loop

		// -------------------------------------------//
		// button

		hourlyEmployeeInsert.setId("adminButton");

		hourlyEmployeeInsert.prefHeightProperty()
				.bind(hourlyEmployeeOptionsPane[11].heightProperty().multiply(9).divide(10));
		hourlyEmployeeInsert.prefWidthProperty()
				.bind(hourlyEmployeeOptionsPane[11].widthProperty().multiply(4).divide(5));

		hourlyEmployeeOptionsPane[11].getChildren().add(hourlyEmployeeInsert);

	}

	// --------------------------------------------------------------------------------//
	// monthly employee options

	protected static final TextField[] monthlyEmployeeTextFields = new TextField[10];; // textFields information
	protected static final DatePicker monthlyEmployeeDateOfBirthDatePicker = new DatePicker(); // date of birth
	protected static final Label[] monthlyEmployeeErrorsOption = new Label[11]; // error labels
	protected static final Button monthlyEmployeeInsert = new Button("insert"); // insert button
	protected static final ComboBox<Character> monthlyEmployeeGenderComboBox = new ComboBox<Character>(); // gender
	private static final Label monthlyEmployeeGenderLabel = new Label("gender"); // gender label
	private static final Label monthlyEmployeeRulesLabel = new Label("rule"); // rules label
	protected static final ComboBox<String> monthlyEmployeeRulesComboBox = new ComboBox<String>(); // rules

	private void monthlyEmployeeOptionsLayout() {

		// -------------------------------------------//
		// textFields

		// for each TextField
		for (int i = 0; i < monthlyEmployeeTextFields.length; i++) {

			// to put the DatePicker after 5 TextFields
			if (i != 3 && i != 2) {
				monthlyEmployeeTextFields[i] = new TextField();

				monthlyEmployeeTextFields[i].setId("adminTextField");

				monthlyEmployeeTextFields[i].prefHeightProperty()
						.bind(monthlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
				monthlyEmployeeTextFields[i].prefWidthProperty()
						.bind(monthlyEmployeeOptionsPane[i].widthProperty().multiply(2).divide(3));

				monthlyEmployeeOptionsPane[i].getChildren().add(monthlyEmployeeTextFields[i]);
			} else if (i == 3) {// add the DatePicker

				monthlyEmployeeDateOfBirthDatePicker.setId("adminDatePicker");

				monthlyEmployeeDateOfBirthDatePicker.prefHeightProperty()
						.bind(monthlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
				monthlyEmployeeDateOfBirthDatePicker.prefWidthProperty()
						.bind(monthlyEmployeeOptionsPane[i].widthProperty().multiply(2).divide(3));

				monthlyEmployeeOptionsPane[i].getChildren().add(monthlyEmployeeDateOfBirthDatePicker);

			} else if (i == 2) {

				// label
				monthlyEmployeeGenderLabel.setId("adminLabel");

				monthlyEmployeeGenderLabel.prefHeightProperty()
						.bind(memberOptionsPane[i].heightProperty().multiply(9).divide(10));
				monthlyEmployeeGenderLabel.prefWidthProperty()
						.bind(memberOptionsPane[i].widthProperty().multiply(1).divide(3));

				monthlyEmployeeOptionsPane[i].getChildren().add(monthlyEmployeeGenderLabel);

				// ComboBox
				monthlyEmployeeGenderComboBox.setId("adminChooseComboBox");

				monthlyEmployeeGenderComboBox.prefHeightProperty()
						.bind(memberOptionsPane[i].heightProperty().multiply(9).divide(10));
				monthlyEmployeeGenderComboBox.prefWidthProperty()
						.bind(memberOptionsPane[i].widthProperty().multiply(1).divide(3));

				monthlyEmployeeGenderComboBox.getItems().addAll('M', 'F');

				monthlyEmployeeOptionsPane[i].getChildren().add(monthlyEmployeeGenderComboBox);

			}

		} // end for loop

		// prompt text
		monthlyEmployeeTextFields[0].setPromptText("first name");
		monthlyEmployeeTextFields[1].setPromptText("last name");

		monthlyEmployeeDateOfBirthDatePicker.setPromptText("birth date");
		monthlyEmployeeTextFields[4].setPromptText("financial balance");
		monthlyEmployeeTextFields[5].setPromptText("phone number");
		monthlyEmployeeTextFields[6].setPromptText("phone number");
		monthlyEmployeeTextFields[7].setPromptText("password");
		monthlyEmployeeTextFields[8].setPromptText("alowed vactions day");
		monthlyEmployeeTextFields[9].setPromptText("salary");

		// -------------------------------------------//
		// rules

		// label
		monthlyEmployeeRulesLabel.setId("adminLabel");

		monthlyEmployeeRulesLabel.prefHeightProperty()
				.bind(monthlyEmployeeOptionsPane[10].heightProperty().multiply(9).divide(10));
		monthlyEmployeeRulesLabel.prefWidthProperty()
				.bind(monthlyEmployeeOptionsPane[10].widthProperty().multiply(1).divide(3));

		monthlyEmployeeOptionsPane[10].getChildren().add(monthlyEmployeeRulesLabel);

		// comoBox
		monthlyEmployeeRulesComboBox.setId("adminChooseComboBox");

		monthlyEmployeeRulesComboBox.prefHeightProperty()
				.bind(monthlyEmployeeOptionsPane[11].heightProperty().multiply(9).divide(10));
		monthlyEmployeeRulesComboBox.prefWidthProperty().bind(monthlyEmployeeOptionsPane[11].widthProperty());

		monthlyEmployeeOptionsPane[11].getChildren().add(monthlyEmployeeRulesComboBox);

		// -------------------------------------------//
		// errors

		// for each error Label
		for (int i = 0; i < monthlyEmployeeErrorsOption.length; i++) {

			monthlyEmployeeErrorsOption[i] = new Label();

			monthlyEmployeeErrorsOption[i].setId("adminerror");

			monthlyEmployeeErrorsOption[i].prefHeightProperty()
					.bind(monthlyEmployeeOptionsPane[i].heightProperty().multiply(9).divide(10));
			monthlyEmployeeErrorsOption[i].prefWidthProperty()
					.bind(monthlyEmployeeOptionsPane[i].widthProperty().divide(3));

			monthlyEmployeeOptionsPane[i].getChildren().add(monthlyEmployeeErrorsOption[i]);

		} // end for loop

		// -------------------------------------------//
		// button

		monthlyEmployeeInsert.setId("adminButton");

		monthlyEmployeeInsert.prefHeightProperty()
				.bind(monthlyEmployeeOptionsPane[12].heightProperty().multiply(9).divide(10));
		monthlyEmployeeInsert.prefWidthProperty()
				.bind(monthlyEmployeeOptionsPane[12].widthProperty().multiply(4).divide(5));

		monthlyEmployeeOptionsPane[12].getChildren().add(monthlyEmployeeInsert);

	}
	// --------------------------------------------------------------------------------//
	// tables buttons

	protected static final Button uploadMemberTable = new Button("upload member table"),
			uploadHourlyEmployeeTable = new Button("Upload hourly employee table"),
			uploadMonthlyEmployeeTable = new Button("Upload monthly employee table");

	private void tableButtonsPane() {

		// member

		uploadMemberTable.setId("adminButton");

		uploadMemberTable.prefHeightProperty().bind(tableButtonsPane.heightProperty().multiply(9).divide(10));
		uploadMemberTable.prefWidthProperty().bind(tableButtonsPane.widthProperty().multiply(1).divide(4));

		// hourly employee

		uploadHourlyEmployeeTable.setId("adminButton");

		uploadHourlyEmployeeTable.prefHeightProperty().bind(tableButtonsPane.heightProperty().multiply(9).divide(10));
		uploadHourlyEmployeeTable.prefWidthProperty().bind(tableButtonsPane.widthProperty().multiply(1).divide(4));

		// monthly employee

		uploadMonthlyEmployeeTable.setId("adminButton");

		uploadMonthlyEmployeeTable.prefHeightProperty().bind(tableButtonsPane.heightProperty().multiply(9).divide(10));
		uploadMonthlyEmployeeTable.prefWidthProperty().bind(tableButtonsPane.widthProperty().multiply(1).divide(4));

		tableButtonsPane.getChildren().addAll(uploadMemberTable, uploadHourlyEmployeeTable, uploadMonthlyEmployeeTable);

	}

	// ------------------------------------------------------------------------------------------------//
	// tables

	protected static int adminShownTable = 0;
	protected static final int MEMBER = 1;
	protected static final int HOURLYEMPLOYEE = 2;
	protected static final int MONTHLYEMPLOYEE = 3;

	protected void addTable(int tableNum) {
		
		adminShownTable = tableNum;
		
		if (tableNum == MEMBER) {

			// table
			tableViewPane.getChildren().clear();
			tableViewPane.getChildren().add(memberTable);

			// options
			controlPane.getChildren().clear();
			controlPane.getChildren().add(memberControlPane);

			// buttons
			buttonPane.getChildren().clear();
			buttonPane.getChildren().add(memberButtonPane);
			

		} else if (tableNum == HOURLYEMPLOYEE) {

			// table
			tableViewPane.getChildren().clear();
			tableViewPane.getChildren().addAll(hourlyEmployeeTable, rulesPane);

			// options
			controlPane.getChildren().clear();
			controlPane.getChildren().add(hourlyEmployeeControlPane);

			// buttons
			buttonPane.getChildren().clear();
			buttonPane.getChildren().add(hourlyEmployeeButtonPane);

		} else if (tableNum == MONTHLYEMPLOYEE) {

			// table
			tableViewPane.getChildren().clear();
			tableViewPane.getChildren().addAll(monthlyEmployeeTable, rulesPane);

			// options
			controlPane.getChildren().clear();
			controlPane.getChildren().add(monthlyEmployeeControlPane);

			// buttons
			buttonPane.getChildren().clear();
			buttonPane.getChildren().add(monthlyEmployeeButtonPane);
		}
	}

	// columns

	// ---------------------------------------------//
	// member table
	protected static final TableView<Member> memberTable = new TableView<Member>();

	private void memberTableLayout() {

		memberTable.prefWidthProperty().bind(tableViewPane.widthProperty());
		memberTable.prefHeightProperty().bind(tableViewPane.heightProperty());

		memberTable.setId("adminTableView");

		memberTable.setEditable(true);

		this.addMemberColumns();
	}

	
	protected static final TableColumn<Member, String> memberFees = new TableColumn<Member, String>("Fees");
	protected static final TableColumn<Member, String> memberId = new TableColumn<Member, String>("ID");
	protected static final TableColumn<Member, String> memberName = new TableColumn<Member, String>("name");
	protected static final TableColumn<Member, String> memberFirstName = new TableColumn<Member, String>("First name");
	protected static final TableColumn<Member, String> memberLastName = new TableColumn<Member, String>("Last name");
	protected static final TableColumn<Member, Character> memberGender = new TableColumn<Member, Character>("Gender");
	protected static final TableColumn<Member, String> memberBirthDate = new TableColumn<Member, String>("Birth date");
	protected static final TableColumn<Member, String> memberFinantialPalance = new TableColumn<Member, String>(
			"Finantial palance");
	protected static final TableColumn<Member, String> memberPassword = new TableColumn<Member, String>("password");
	protected static final TableColumn<Member, String> memberPhone = new TableColumn<Member, String>("Phone");
	protected static final TableColumn<Member, String> memberFirstPhone = new TableColumn<Member, String>("First");
	protected static final TableColumn<Member, String> memberSecondPhone = new TableColumn<Member, String>("Second");

	private void addMemberColumns() {

		memberName.getColumns().addAll(memberFirstName, memberLastName);
		memberPhone.getColumns().addAll(memberFirstPhone, memberSecondPhone);

		memberTable.getColumns().addAll(memberId, memberName, memberGender, memberBirthDate, memberFees,
				memberFinantialPalance, memberPhone, memberPassword);

		memberId.prefWidthProperty().bind(tablePane.widthProperty().divide(20));
		memberName.prefWidthProperty().bind(tablePane.widthProperty().multiply(30).divide(200));
		memberFirstName.prefWidthProperty().bind(tablePane.widthProperty().multiply(15).divide(200));
		memberLastName.prefWidthProperty().bind(tablePane.widthProperty().multiply(15).divide(200));
		memberGender.prefWidthProperty().bind(tablePane.widthProperty().multiply(10).divide(200));
		memberBirthDate.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		memberFinantialPalance.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		memberFees.prefWidthProperty().bind(tablePane.widthProperty().multiply(15).divide(200));
		memberPhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(80).divide(200));
		memberFirstPhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(40).divide(200));
		memberSecondPhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(40).divide(200));
		memberPassword.prefWidthProperty().bind(tablePane.widthProperty().multiply(15).divide(200));

	}// end add columns method

	// ---------------------------------------------//
	// hourly employee table

	protected static final TableView<HourlyEmployee> hourlyEmployeeTable = new TableView<HourlyEmployee>();

	private void hourlyEmployeeTableLayout() {

		hourlyEmployeeTable.prefWidthProperty().bind(tablePane.widthProperty().multiply(4).divide(5));
		hourlyEmployeeTable.prefHeightProperty().bind(tablePane.heightProperty().multiply(8).divide(10));

		hourlyEmployeeTable.setId("adminTableView");

		hourlyEmployeeTable.setEditable(true);

		this.addHourlyEmployeeColumns();
	}

	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeSalary = new TableColumn<HourlyEmployee, String>(
			"Salary");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeEmploymentDate = new TableColumn<HourlyEmployee, String>(
			"Employment date");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeId = new TableColumn<HourlyEmployee, String>(
			"ID");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeName = new TableColumn<HourlyEmployee, String>(
			"name");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeFirstName = new TableColumn<HourlyEmployee, String>(
			"First name");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeLastName = new TableColumn<HourlyEmployee, String>(
			"Last name");
	protected static final TableColumn<HourlyEmployee, Character> hourlyEmployeeGender = new TableColumn<HourlyEmployee, Character>(
			"Gender");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeBirthDate = new TableColumn<HourlyEmployee, String>(
			"Birth date");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeFinantialPalance = new TableColumn<HourlyEmployee, String>(
			"Finantial palance");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeePassword = new TableColumn<HourlyEmployee, String>(
			"password");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeePhone = new TableColumn<HourlyEmployee, String>(
			"Phone");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeFirstPhone = new TableColumn<HourlyEmployee, String>(
			"First");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeSecondPhone = new TableColumn<HourlyEmployee, String>(
			"Second");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeWorkingHour = new TableColumn<HourlyEmployee, String>(
			"Working hours");
	protected static final TableColumn<HourlyEmployee, String> hourlyEmployeeSalaryPerHour = new TableColumn<HourlyEmployee, String>(
			"Salary/Hour");

	private void addHourlyEmployeeColumns() {

		hourlyEmployeeName.getColumns().addAll(hourlyEmployeeFirstName, hourlyEmployeeLastName);
		hourlyEmployeePhone.getColumns().addAll(hourlyEmployeeFirstPhone, hourlyEmployeeSecondPhone);

		hourlyEmployeeTable.getColumns().addAll(hourlyEmployeeId, hourlyEmployeeName, hourlyEmployeeGender,
				hourlyEmployeeBirthDate, hourlyEmployeeFinantialPalance, hourlyEmployeePhone, hourlyEmployeePassword,
				hourlyEmployeeSalary, hourlyEmployeeEmploymentDate, hourlyEmployeeWorkingHour,
				hourlyEmployeeSalaryPerHour);

		hourlyEmployeeId.prefWidthProperty().bind(tablePane.widthProperty().multiply(7).divide(200));
		hourlyEmployeeName.prefWidthProperty().bind(tablePane.widthProperty().multiply(24).divide(200));
		hourlyEmployeeFirstName.prefWidthProperty().bind(tablePane.widthProperty().multiply(12).divide(200));
		hourlyEmployeeLastName.prefWidthProperty().bind(tablePane.widthProperty().multiply(12).divide(200));
		hourlyEmployeeGender.prefWidthProperty().bind(tablePane.widthProperty().multiply(10).divide(200));
		hourlyEmployeeBirthDate.prefWidthProperty().bind(tablePane.widthProperty().multiply(14).divide(200));
		hourlyEmployeeFinantialPalance.prefWidthProperty().bind(tablePane.widthProperty().multiply(25).divide(200));
		hourlyEmployeePhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(40).divide(200));
		hourlyEmployeeFirstPhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		hourlyEmployeeSecondPhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		hourlyEmployeePassword.prefWidthProperty().bind(tablePane.widthProperty().multiply(15).divide(200));
		hourlyEmployeeSalary.prefWidthProperty().bind(tablePane.widthProperty().multiply(10).divide(200));
		hourlyEmployeeEmploymentDate.prefWidthProperty().bind(tablePane.widthProperty().multiply(21).divide(200));
		hourlyEmployeeWorkingHour.prefWidthProperty().bind(tablePane.widthProperty().multiply(17).divide(200));
		hourlyEmployeeSalaryPerHour.prefWidthProperty().bind(tablePane.widthProperty().multiply(17).divide(200));

	}// end add columns method

	// ---------------------------------------------//
	// monthly employee table

	protected static final TableView<MonthlyEmployee> monthlyEmployeeTable = new TableView<MonthlyEmployee>();

	private void monthlyEmployeeTableLayout() {

		monthlyEmployeeTable.prefWidthProperty().bind(tablePane.widthProperty().multiply(4).divide(5));
		monthlyEmployeeTable.prefHeightProperty().bind(tablePane.heightProperty().multiply(8).divide(10));

		monthlyEmployeeTable.setId("adminTableView");

		monthlyEmployeeTable.setEditable(true);

		addMonthlyEmployeeColumns();
	}

	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeSalary = new TableColumn<MonthlyEmployee, String>(
			"Salary");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeEmploymentDate = new TableColumn<MonthlyEmployee, String>(
			"Employment date");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeId = new TableColumn<MonthlyEmployee, String>(
			"ID");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeName = new TableColumn<MonthlyEmployee, String>(
			"name");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeFirstName = new TableColumn<MonthlyEmployee, String>(
			"First name");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeLastName = new TableColumn<MonthlyEmployee, String>(
			"Last name");
	protected static final TableColumn<MonthlyEmployee, Character> monthlyEmployeeGender = new TableColumn<MonthlyEmployee, Character>(
			"Gender");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeBirthDate = new TableColumn<MonthlyEmployee, String>(
			"Birth date");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeFinantialPalance = new TableColumn<MonthlyEmployee, String>(
			"Finantial palance");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeePassword = new TableColumn<MonthlyEmployee, String>(
			"password");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeePhone = new TableColumn<MonthlyEmployee, String>(
			"Phone");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeFirstPhone = new TableColumn<MonthlyEmployee, String>(
			"First");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeSecondPhone = new TableColumn<MonthlyEmployee, String>(
			"Second");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeVaction = new TableColumn<MonthlyEmployee, String>(
			"Vaction");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeAllowedVactionDay = new TableColumn<MonthlyEmployee, String>(
			"Allowed");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeVactionDay = new TableColumn<MonthlyEmployee, String>(
			"Days");
	protected static final TableColumn<MonthlyEmployee, String> monthlyEmployeeDeduction = new TableColumn<MonthlyEmployee, String>(
			"Deduction");

	private void addMonthlyEmployeeColumns() {

		monthlyEmployeeName.getColumns().addAll(monthlyEmployeeFirstName, monthlyEmployeeLastName);
		monthlyEmployeePhone.getColumns().addAll(monthlyEmployeeFirstPhone, monthlyEmployeeSecondPhone);
		monthlyEmployeeVaction.getColumns().addAll(monthlyEmployeeVactionDay, monthlyEmployeeAllowedVactionDay);

		monthlyEmployeeTable.getColumns().addAll(monthlyEmployeeId, monthlyEmployeeName, monthlyEmployeeGender,
				monthlyEmployeeBirthDate, monthlyEmployeeFinantialPalance, monthlyEmployeePhone,
				monthlyEmployeePassword, monthlyEmployeeSalary, monthlyEmployeeEmploymentDate, monthlyEmployeeVaction,
				monthlyEmployeeDeduction);

		monthlyEmployeeId.prefWidthProperty().bind(tablePane.widthProperty().multiply(7).divide(200));
		monthlyEmployeeName.prefWidthProperty().bind(tablePane.widthProperty().multiply(24).divide(200));
		monthlyEmployeeFirstName.prefWidthProperty().bind(tablePane.widthProperty().multiply(12).divide(200));
		monthlyEmployeeLastName.prefWidthProperty().bind(tablePane.widthProperty().multiply(12).divide(200));
		monthlyEmployeeGender.prefWidthProperty().bind(tablePane.widthProperty().multiply(9).divide(200));
		monthlyEmployeeBirthDate.prefWidthProperty().bind(tablePane.widthProperty().multiply(15).divide(200));
		monthlyEmployeeFinantialPalance.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		monthlyEmployeePhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(40).divide(200));
		monthlyEmployeeFirstPhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		monthlyEmployeeSecondPhone.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		monthlyEmployeePassword.prefWidthProperty().bind(tablePane.widthProperty().multiply(15).divide(200));
		monthlyEmployeeSalary.prefWidthProperty().bind(tablePane.widthProperty().multiply(10).divide(200));
		monthlyEmployeeEmploymentDate.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		monthlyEmployeeVaction.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));
		monthlyEmployeeAllowedVactionDay.prefWidthProperty().bind(tablePane.widthProperty().multiply(13).divide(200));
		monthlyEmployeeVactionDay.prefWidthProperty().bind(tablePane.widthProperty().multiply(7).divide(200));
		monthlyEmployeeDeduction.prefWidthProperty().bind(tablePane.widthProperty().multiply(20).divide(200));

	}// end add columns method

	// ---------------------------------------------//
	// member's buttons

	protected static final Button memberDelete = new Button("delete"), memberRefresh = new Button("refresh");

	private void memberButtonLayout() {

		// delete

		memberDelete.prefHeightProperty().bind(memberButtonPane.heightProperty().multiply(9).divide(10));
		memberDelete.prefWidthProperty().bind(memberButtonPane.widthProperty().divide(3));

		memberDelete.setId("adminButton");

		// delete

		memberRefresh.prefHeightProperty().bind(memberButtonPane.heightProperty().multiply(9).divide(10));
		memberRefresh.prefWidthProperty().bind(memberButtonPane.widthProperty().divide(3));

		memberRefresh.setId("adminButton");

		memberButtonPane.getChildren().addAll(memberDelete, memberRefresh);

	}

	// ---------------------------------------------//
	// hourly employee's buttons

	protected static final Button hourlyEmployeeDelete = new Button("delete"),
			hourlyEmployeeRefresh = new Button("refresh");;

	private void hourlyEmployeeButtonLayout() {

		// delete

		hourlyEmployeeDelete.prefHeightProperty()
				.bind(hourlyEmployeeButtonPane.heightProperty().multiply(9).divide(10));
		hourlyEmployeeDelete.prefWidthProperty().bind(hourlyEmployeeButtonPane.widthProperty().divide(3));

		hourlyEmployeeDelete.setId("adminButton");

		// delete

		hourlyEmployeeRefresh.prefHeightProperty()
				.bind(hourlyEmployeeButtonPane.heightProperty().multiply(9).divide(10));
		hourlyEmployeeRefresh.prefWidthProperty().bind(hourlyEmployeeButtonPane.widthProperty().divide(3));

		hourlyEmployeeRefresh.setId("adminButton");

		hourlyEmployeeButtonPane.getChildren().addAll(hourlyEmployeeDelete, hourlyEmployeeRefresh);

	}

	// ---------------------------------------------//
	// monthly employee's buttons

	protected static final Button monthlyEmployeeDelete = new Button("delete"),
			monthlyEmployeeRefresh = new Button("refresh");

	private void monthlyEmployeeButtonLayout() {

		// delete

		monthlyEmployeeDelete.prefHeightProperty()
				.bind(monthlyEmployeeButtonPane.heightProperty().multiply(9).divide(10));
		monthlyEmployeeDelete.prefWidthProperty().bind(monthlyEmployeeButtonPane.widthProperty().divide(3));

		monthlyEmployeeDelete.setId("adminButton");

		// delete

		monthlyEmployeeRefresh.prefHeightProperty()
				.bind(monthlyEmployeeButtonPane.heightProperty().multiply(9).divide(10));
		monthlyEmployeeRefresh.prefWidthProperty().bind(monthlyEmployeeButtonPane.widthProperty().divide(3));

		monthlyEmployeeRefresh.setId("adminButton");

		monthlyEmployeeButtonPane.getChildren().addAll(monthlyEmployeeDelete, monthlyEmployeeRefresh);

	}

	// ---------------------------------------------------------------------//
	// rules ListView
	protected static final ListView<String> rulesListView = new ListView<String>();

	private void rulesListViewLayout() {

		rulesListView.prefHeightProperty().bind(rulesPane.heightProperty().multiply(7).divide(10));
		rulesListView.prefWidthProperty().bind(rulesPane.widthProperty());

		rulesListView.setId("adminListView");

		rulesPane.getChildren().add(rulesListView);

	}

	// rules comboBox
	protected static final ComboBox<String> rulesSingleEmpComboBox = new ComboBox<String>();

	private void rulesSingleEmpComboBoxLayout() {

		rulesSingleEmpComboBox.prefHeightProperty().bind(rulesPane.heightProperty().divide(10));
		rulesSingleEmpComboBox.prefWidthProperty().bind(rulesPane.widthProperty());

		rulesSingleEmpComboBox.setId("adminComboBox");

		rulesPane.getChildren().add(rulesSingleEmpComboBox);

	}

	// rules options
	protected static final Button addRuleForSingleEmp = new Button("Add"),
			removeRuleForSingleEmp = new Button("delete");
	protected static final Label rulesOptionForSingleEmpErrorLabel = new Label();

	private void rulesForSingleEmpLayout() {

		// add button
		addRuleForSingleEmp.prefHeightProperty().bind(rulesPaneForSingleEmp.heightProperty());
		addRuleForSingleEmp.prefWidthProperty().bind(rulesPaneForSingleEmp.widthProperty().divide(2));

		addRuleForSingleEmp.setId("adminButton");

		rulesPaneForSingleEmp.getChildren().add(addRuleForSingleEmp);

		// delete button
		removeRuleForSingleEmp.prefHeightProperty().bind(rulesPaneForSingleEmp.heightProperty());
		removeRuleForSingleEmp.prefWidthProperty().bind(rulesPaneForSingleEmp.widthProperty().divide(2));

		removeRuleForSingleEmp.setId("adminButton");

		rulesPaneForSingleEmp.getChildren().add(removeRuleForSingleEmp);

		// error label
		rulesOptionForSingleEmpErrorLabel.setId("adminerror");

		rulesOptionForSingleEmpErrorLabel.prefHeightProperty().bind(rulesPane.heightProperty().divide(10));
		rulesOptionForSingleEmpErrorLabel.prefWidthProperty().bind(rulesPane.widthProperty());

		rulesPane.getChildren().add(rulesOptionForSingleEmpErrorLabel);

	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	// Book pane
	protected static final BookControl bookControl = new BookControl(adminPane);

	// Financial pane
	protected static final FinancialControl financialControl = new FinancialControl(adminPane);
	
	// account pane
	protected static final AdminAccountControl adminAccountControl = new AdminAccountControl(adminPane);
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

}// end AdminLayout class
