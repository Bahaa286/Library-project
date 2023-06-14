package Layout;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class AdminAccountLayout extends Pane {

	// administrator pane to insert this pane on it
	protected final HBox adminPane;

	public AdminAccountLayout(HBox adminPane) {

		this.adminPane = adminPane;

		this.setPrefSize(adminPane.getWidth(), adminPane.getHeight());

		this.prefHeightProperty().bind(adminPane.heightProperty());
		this.prefWidthProperty().bind(adminPane.widthProperty());

		this.setId("adminAccountPane");

		// set layouts
		this.primaryPaneLayout();
	}

	// --------------------------------------------------------------------//
	// primary Layout
	private static final VBox primaryPane = new VBox();

	private void primaryPaneLayout() {

		primaryPane.prefHeightProperty().bind(this.heightProperty());
		primaryPane.prefWidthProperty().bind(this.widthProperty());

		primaryPane.setId("adminAccountPane");

		this.getChildren().add(primaryPane);

		// set layouts
		this.idPaneLayout(); // id
		this.namePaneLayout(); // name
		this.genderPaneLayout(); // gender
		this.birthDatePaneLayout(); // birth date
		this.financialBalancePaneLayout(); // financial balance
		this.phonePaneLayout(); // phone
		this.passwordPaneLayout(); // password
		this.employmentDataPaneLayout(); // employment date
		this.salaryPaneLayout(); // salary
		this.rulePaneLayout(); // rule

	}

	// -------------------------------------------------------------------//
	// id pane
	private static final HBox idPane = new HBox();

	private void idPaneLayout() {

		idPane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		idPane.prefWidthProperty().bind(primaryPane.widthProperty());

		idPane.setId("adminAccountPane");

		primaryPane.getChildren().add(idPane);

		// set layouts
		this.idLabelPane();
	}

	// -------------------------------------------------------------------//
	// name layout
	private static final HBox namePane = new HBox();

	private void namePaneLayout() {

		namePane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		namePane.prefWidthProperty().bind(primaryPane.widthProperty());

		namePane.setId("adminAccountPane");

		primaryPane.getChildren().add(namePane);

		// set layout
		this.nameLayout();

	}

	// -------------------------------------------------------------------//
	// gender layout
	private static final HBox genderPane = new HBox();

	private void genderPaneLayout() {

		genderPane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		genderPane.prefWidthProperty().bind(primaryPane.widthProperty());

		genderPane.setId("adminAccountPane");

		primaryPane.getChildren().add(genderPane);

		// set layout
		this.genderLayout();

	}

	// -------------------------------------------------------------------//
	// birthDate layout
	private static final HBox birthDatePane = new HBox();

	private void birthDatePaneLayout() {

		birthDatePane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		birthDatePane.prefWidthProperty().bind(primaryPane.widthProperty());

		birthDatePane.setId("adminAccountPane");

		primaryPane.getChildren().add(birthDatePane);

		// set layout
		this.birthDateLayout();

	}

	// -------------------------------------------------------------------//
	// financialBalance layout
	private static final HBox financialBalancePane = new HBox();

	private void financialBalancePaneLayout() {

		financialBalancePane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		financialBalancePane.prefWidthProperty().bind(primaryPane.widthProperty());

		financialBalancePane.setId("adminAccountPane");

		primaryPane.getChildren().add(financialBalancePane);

		// set layout
		this.financialBalanceLayout();

	}

	// -------------------------------------------------------------------//
	// phone layout
	private static final HBox phonePane = new HBox();

	private void phonePaneLayout() {

		phonePane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		phonePane.prefWidthProperty().bind(primaryPane.widthProperty());

		phonePane.setId("adminAccountPane");

		primaryPane.getChildren().add(phonePane);

		// set layout
		this.phoneLayout();

	}

	// -------------------------------------------------------------------//
	// password layout
	private static final HBox passwordPane = new HBox();

	private void passwordPaneLayout() {

		passwordPane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		passwordPane.prefWidthProperty().bind(primaryPane.widthProperty());

		passwordPane.setId("adminAccountPane");

		primaryPane.getChildren().add(passwordPane);

		// set layout
		this.passwordLayout();

	}

	// -------------------------------------------------------------------//
	// employment date layout
	private static final HBox employmentDataPane = new HBox();

	private void employmentDataPaneLayout() {

		employmentDataPane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		employmentDataPane.prefWidthProperty().bind(primaryPane.widthProperty());

		employmentDataPane.setId("adminAccountPane");

		primaryPane.getChildren().add(employmentDataPane);

		// set layout
		this.employmentDataLayout();

	}

	// -------------------------------------------------------------------//
	// employment date layout
	private static final HBox salaryPane = new HBox();

	private void salaryPaneLayout() {

		salaryPane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		salaryPane.prefWidthProperty().bind(primaryPane.widthProperty());

		salaryPane.setId("adminAccountPane");

		primaryPane.getChildren().add(salaryPane);

		// set layout
		this.salaryLayout();

	}

	// -------------------------------------------------------------------//
	// rule layout
	private static final HBox rulePane = new HBox();

	private void rulePaneLayout() {

		rulePane.prefHeightProperty().bind(primaryPane.heightProperty().divide(10));
		rulePane.prefWidthProperty().bind(primaryPane.widthProperty());

		rulePane.setId("adminAccountPane");

		primaryPane.getChildren().add(rulePane);

		// set layout
		this.ruleLayout();

	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// id

	private static final Label idPrimaryLabel = new Label("administrator id: ");
	protected static final Label idLabel = new Label();

	private void idLabelPane() {

		// primary label
		idPrimaryLabel.prefHeightProperty().bind(idPane.heightProperty());
		idPrimaryLabel.prefWidthProperty().bind(idPane.widthProperty().divide(2));

		idPrimaryLabel.setId("adminAccountPrimaryLabel");

		idPane.getChildren().add(idPrimaryLabel);
		
		// label
		idLabel.prefHeightProperty().bind(idPane.heightProperty());
		idLabel.prefWidthProperty().bind(idPane.widthProperty().divide(2));

		idLabel.setId("adminAccountLabel");

		idPane.getChildren().add(idLabel);
	}

	// ---------------------------------------------------//
	// name

	private static final Label namePrimaryLabel = new Label("Name: ");
	protected static final Label nameLabel = new Label();
	protected static final TextField firstNameTextField = new TextField(), secondNameTextField = new TextField();

	private void nameLayout() {

		// primary label
		namePrimaryLabel.prefHeightProperty().bind(namePane.heightProperty());
		namePrimaryLabel.prefWidthProperty().bind(namePane.widthProperty().divide(4));

		namePrimaryLabel.setId("adminAccountPrimaryLabel");

		namePane.getChildren().add(namePrimaryLabel);

		// label
		nameLabel.prefHeightProperty().bind(namePane.heightProperty());
		nameLabel.prefWidthProperty().bind(namePane.widthProperty().divide(4));

		nameLabel.setId("adminAccountLabel");

		namePane.getChildren().add(nameLabel);

		// TextField

		// first
		firstNameTextField.prefHeightProperty().bind(namePane.heightProperty());
		firstNameTextField.prefWidthProperty().bind(namePane.widthProperty().divide(4));

		firstNameTextField.setId("adminTextField");
		firstNameTextField.setPromptText("first name");

		namePane.getChildren().add(firstNameTextField);

		// second
		secondNameTextField.prefHeightProperty().bind(namePane.heightProperty());
		secondNameTextField.prefWidthProperty().bind(namePane.widthProperty().divide(4));

		secondNameTextField.setId("adminTextField");
		secondNameTextField.setPromptText("second name");

		namePane.getChildren().add(secondNameTextField);

	}

	// --------------------------------------------------//
	// gender

	private static final Label genderPrimaryLabel = new Label("Gender: ");
	protected static final Label genderLabel = new Label();
	protected static final ComboBox<Character> genderComboBox = new ComboBox<Character>();
	protected static final Button changeGenderButton = new Button("Update");

	private void genderLayout() {

		// primary label
		genderPrimaryLabel.prefHeightProperty().bind(genderPane.heightProperty());
		genderPrimaryLabel.prefWidthProperty().bind(genderPane.widthProperty().divide(4));

		genderPrimaryLabel.setId("adminAccountPrimaryLabel");

		genderPane.getChildren().add(genderPrimaryLabel);

		// label
		genderLabel.prefHeightProperty().bind(genderPane.heightProperty());
		genderLabel.prefWidthProperty().bind(genderPane.widthProperty().divide(4));

		genderLabel.setId("adminAccountLabel");

		genderPane.getChildren().add(genderLabel);

		// ComboBox
		genderComboBox.prefHeightProperty().bind(genderPane.heightProperty());
		genderComboBox.prefWidthProperty().bind(genderPane.widthProperty().divide(4));

		genderComboBox.setId("adminComboBox");
		genderComboBox.setPromptText("gender");
		genderComboBox.getItems().addAll('M', 'F');

		genderPane.getChildren().add(genderComboBox);

		// button
		changeGenderButton.prefHeightProperty().bind(genderPane.heightProperty());
		changeGenderButton.prefWidthProperty().bind(genderPane.widthProperty().divide(4));

		changeGenderButton.setId("adminButton");

		genderPane.getChildren().add(changeGenderButton);

	}

	// --------------------------------------------------//
	// birth date

	private static final Label birthDatePrimaryLabel = new Label("Birth date: ");
	protected static final Label birthDateLabel = new Label();
	protected static final DatePicker birthDateDatePicker = new DatePicker();
	protected static final Button changeBirthDateButton = new Button("Update");

	private void birthDateLayout() {

		// primary label
		birthDatePrimaryLabel.prefHeightProperty().bind(birthDatePane.heightProperty());
		birthDatePrimaryLabel.prefWidthProperty().bind(birthDatePane.widthProperty().divide(4));

		birthDatePrimaryLabel.setId("adminAccountPrimaryLabel");

		birthDatePane.getChildren().add(birthDatePrimaryLabel);

		// label
		birthDateLabel.prefHeightProperty().bind(birthDatePane.heightProperty());
		birthDateLabel.prefWidthProperty().bind(birthDatePane.widthProperty().divide(4));

		birthDateLabel.setId("adminAccountLabel");

		birthDatePane.getChildren().add(birthDateLabel);

		// DateBicker
		birthDateDatePicker.prefHeightProperty().bind(birthDatePane.heightProperty());
		birthDateDatePicker.prefWidthProperty().bind(birthDatePane.widthProperty().divide(4));

		birthDateDatePicker.setId("adminDatePicker");

		birthDatePane.getChildren().add(birthDateDatePicker);

		// Button
		changeBirthDateButton.prefHeightProperty().bind(birthDatePane.heightProperty());
		changeBirthDateButton.prefWidthProperty().bind(birthDatePane.widthProperty().divide(4));

		changeBirthDateButton.setId("adminButton");

		birthDatePane.getChildren().add(changeBirthDateButton);

	}

	// --------------------------------------------------//
	// financial balance

	private static final Label financialBalancePrimaryLabel = new Label("Financial balance: ");
	protected static final Label financialBalanceLabel = new Label();

	private void financialBalanceLayout() {

		// primary label
		financialBalancePrimaryLabel.prefHeightProperty().bind(financialBalancePane.heightProperty());
		financialBalancePrimaryLabel.prefWidthProperty().bind(financialBalancePane.widthProperty().divide(2));

		financialBalancePrimaryLabel.setId("adminAccountPrimaryLabel");

		financialBalancePane.getChildren().add(financialBalancePrimaryLabel);

		// label
		financialBalanceLabel.prefHeightProperty().bind(financialBalancePane.heightProperty());
		financialBalanceLabel.prefWidthProperty().bind(financialBalancePane.widthProperty().divide(2));

		financialBalanceLabel.setId("adminAccountLabel");

		financialBalancePane.getChildren().add(financialBalanceLabel);

	}

	// --------------------------------------------------//
	// phone
	private static final Label phonePrimaryLabel = new Label("Phone");
	protected static final Label phoneLabel = new Label();
	protected static final TextField PhoneTextField = new TextField();
	protected static final Button changePhone1Button = new Button("Update phone1"),
			changePhone2Button = new Button("Update phone2");

	private void phoneLayout() {

		// primary label
		phonePrimaryLabel.prefHeightProperty().bind(phonePane.heightProperty());
		phonePrimaryLabel.prefWidthProperty().bind(phonePane.widthProperty().multiply(2).divide(11));

		phonePrimaryLabel.setId("adminAccountPrimaryLabel");

		phonePane.getChildren().add(phonePrimaryLabel);

		// label
		phoneLabel.prefHeightProperty().bind(phonePane.heightProperty());
		phoneLabel.prefWidthProperty().bind(phonePane.widthProperty().multiply(3).divide(11));

		phoneLabel.setId("adminAccountLabel");

		phonePane.getChildren().add(phoneLabel);

		// TextField
		PhoneTextField.prefHeightProperty().bind(phonePane.heightProperty());
		PhoneTextField.prefWidthProperty().bind(phonePane.widthProperty().multiply(2).divide(11));

		PhoneTextField.setId("adminTextField");
		PhoneTextField.setPromptText("new phone");

		phonePane.getChildren().add(PhoneTextField);

		// Button
		// 1
		changePhone1Button.prefHeightProperty().bind(phonePane.heightProperty());
		changePhone1Button.prefWidthProperty().bind(phonePane.widthProperty().multiply(2).divide(11));

		changePhone1Button.setId("adminButton");

		phonePane.getChildren().add(changePhone1Button);

		// 2
		changePhone2Button.prefHeightProperty().bind(phonePane.heightProperty());
		changePhone2Button.prefWidthProperty().bind(phonePane.widthProperty().multiply(2).divide(11));

		changePhone2Button.setId("adminButton");

		phonePane.getChildren().add(changePhone2Button);

	}

	// --------------------------------------------------//
	// password

	private static final Label passwordPrimaryLabel = new Label("password");
	protected static final TextField previousPasswordTextField = new TextField(),
			newPasswordTextField = new TextField();
	protected static final Button changePasswordButton = new Button("update");
	protected static final Label passwordErrorLabel = new Label();

	private void passwordLayout() {

		// primary label
		passwordPrimaryLabel.prefHeightProperty().bind(passwordPane.heightProperty());
		passwordPrimaryLabel.prefWidthProperty().bind(passwordPane.widthProperty().divide(8));

		passwordPrimaryLabel.setId("adminAccountPrimaryLabel");

		passwordPane.getChildren().add(passwordPrimaryLabel);

		// TextFields
		// previous
		previousPasswordTextField.prefHeightProperty().bind(passwordPane.heightProperty());
		previousPasswordTextField.prefWidthProperty().bind(passwordPane.widthProperty().divide(4));

		previousPasswordTextField.setId("adminTextField");
		previousPasswordTextField.setPromptText("enter old password");

		passwordPane.getChildren().add(previousPasswordTextField);
		
		// previous
		newPasswordTextField.prefHeightProperty().bind(passwordPane.heightProperty());
		newPasswordTextField.prefWidthProperty().bind(passwordPane.widthProperty().divide(4));

		newPasswordTextField.setId("adminTextField");
		newPasswordTextField.setPromptText("enter new password");

		passwordPane.getChildren().add(newPasswordTextField);
		
		// Button
		changePasswordButton.prefHeightProperty().bind(passwordPane.heightProperty());
		changePasswordButton.prefWidthProperty().bind(passwordPane.widthProperty().divide(8));

		changePasswordButton.setId("adminButton");

		passwordPane.getChildren().add(changePasswordButton);
		
		// error Label
		passwordErrorLabel.prefHeightProperty().bind(passwordPane.heightProperty());
		passwordErrorLabel.prefWidthProperty().bind(passwordPane.widthProperty().divide(8));

		passwordErrorLabel.setId("adminerror");

		passwordPane.getChildren().add(passwordErrorLabel);

	}

	// --------------------------------------------------//
	// employment date
	
	private static final Label employmentDataPrimaryLabel = new Label("Employment date: ");
	protected static final Label employmentDataLabel = new Label();

	private void employmentDataLayout() {

		// primary label
		employmentDataPrimaryLabel.prefHeightProperty().bind(employmentDataPane.heightProperty());
		employmentDataPrimaryLabel.prefWidthProperty().bind(employmentDataPane.widthProperty().divide(2));

		employmentDataPrimaryLabel.setId("adminAccountPrimaryLabel");

		employmentDataPane.getChildren().add(employmentDataPrimaryLabel);

		// label
		employmentDataLabel.prefHeightProperty().bind(employmentDataPane.heightProperty());
		employmentDataLabel.prefWidthProperty().bind(employmentDataPane.widthProperty().divide(2));

		employmentDataLabel.setId("adminAccountLabel");

		employmentDataPane.getChildren().add(employmentDataLabel);

	}

	// --------------------------------------------------//
	// salary
	
	private static final Label salaryPrimaryLabel = new Label("salary: ");
	protected static final Label salaryLabel = new Label();

	private void salaryLayout() {

		// primary label
		salaryPrimaryLabel.prefHeightProperty().bind(salaryPane.heightProperty());
		salaryPrimaryLabel.prefWidthProperty().bind(salaryPane.widthProperty().divide(2));

		salaryPrimaryLabel.setId("adminAccountPrimaryLabel");

		salaryPane.getChildren().add(salaryPrimaryLabel);

		// label
		salaryLabel.prefHeightProperty().bind(salaryPane.heightProperty());
		salaryLabel.prefWidthProperty().bind(salaryPane.widthProperty().divide(2));

		salaryLabel.setId("adminAccountLabel");

		salaryPane.getChildren().add(salaryLabel);

	}

	// --------------------------------------------------//
	// rule
	
	private static final Label rulePrimaryLabel = new Label("rule: ");
	protected static final Label ruleLabel = new Label();

	private void ruleLayout() {

		// primary label
		rulePrimaryLabel.prefHeightProperty().bind(rulePane.heightProperty());
		rulePrimaryLabel.prefWidthProperty().bind(rulePane.widthProperty().divide(4));

		rulePrimaryLabel.setId("adminAccountPrimaryLabel");

		rulePane.getChildren().add(rulePrimaryLabel);

		// label
		ruleLabel.prefHeightProperty().bind(rulePane.heightProperty());
		ruleLabel.prefWidthProperty().bind(rulePane.widthProperty().multiply(3).divide(4));

		ruleLabel.setId("adminAccountLabel");

		rulePane.getChildren().add(ruleLabel);
	}
}
