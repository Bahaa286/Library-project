package Layout;

import java.util.Date;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class CreateAccountLayout extends Pane {

	protected static final int HEIGHT = 700;
	protected static final int WIDTH = 1000;

	private static final int VPHEIGHT = HEIGHT * 68 / 70;
	private static final int VPWIDTH = WIDTH * 78 / 80;

	public CreateAccountLayout() {

		// image
		this.imageLayout();

		// basic pane
		this.verticalPaneLayout();

		this.setPrefSize(WIDTH, HEIGHT);

	}

	////////////////////////////////////////////////////////////////////////
	// image

	private static final Image image = new Image("images/createAccountImage.jpg");
	private static final ImageView imageView = new ImageView(image);
	
	private void imageLayout() {	

		imageView.fitHeightProperty().bind(this.heightProperty());
		imageView.fitWidthProperty().bind(this.widthProperty());

		this.getChildren().add(imageView);
	}

	////////////////////////////////////////////////////////////////////////
	// Panes

	// -----------------------------------------------------//
	// primary

	private static final VBox verticalPane = new VBox((HEIGHT - HEIGHT * 2 / 70) / 24);

	private void verticalPaneLayout() {

		verticalPane.layoutXProperty().bind(this.widthProperty().divide(80));
		verticalPane.layoutYProperty().bind(this.heightProperty().divide(70));
		verticalPane.prefWidthProperty().bind(this.widthProperty().multiply(78).divide(80));
		verticalPane.prefHeightProperty().bind(this.heightProperty().multiply(68).divide(70));

		this.getChildren().add(verticalPane);

		// set layouts
		this.textPaneLayout();
		this.namePaneLayout();
		this.genderPaneLayout();
		this.birthDatePaneLayout();
		this.phonePaneLayout();
		this.passwordPaneLayout();
		this.buttonsPaneLayout();

	}

	// -----------------------------------------------------//
	// Text

	private static final HBox textPane = new HBox();

	private void textPaneLayout() {

		textPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(3).divide(24));
		textPane.prefWidthProperty().bind(verticalPane.widthProperty());

		textPane.setAlignment(Pos.CENTER);

		verticalPane.getChildren().add(textPane);

		this.textlayout();

	}

	// -----------------------------------------------------//
	// Name Layout

	private static final HBox namePane = new HBox(VPWIDTH * 3 / 31);

	private void namePaneLayout() {

		namePane.prefWidthProperty().bind(verticalPane.widthProperty());
		namePane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(3).divide(24));

		verticalPane.getChildren().add(namePane);

		// set layouts
		this.firstLastNamePane();
	}

	private static final VBox firstNamePane = new VBox(), lastNamePane = new VBox();
	private static final int FLNPWIDTH = VPWIDTH * 14 / 31;

	private void firstLastNamePane() {

		// --------------------------//
		// first name
		firstNamePane.prefWidthProperty().bind(this.namePane.widthProperty().multiply(14).divide(31));
		firstNamePane.prefHeightProperty().bind(this.namePane.heightProperty());

		this.namePane.getChildren().add(firstNamePane);

		// --------------------------//
		// last name
		lastNamePane.prefWidthProperty().bind(this.namePane.widthProperty().multiply(14).divide(31));
		lastNamePane.prefHeightProperty().bind(this.namePane.heightProperty());

		this.namePane.getChildren().add(lastNamePane);

		// set layouts
		this.firstLastNameLabelsPaneLayout();

	}

	private static final HBox firstNameLabelsPane = new HBox(FLNPWIDTH / 6), lastNameLabelsPane = new HBox(FLNPWIDTH / 6);

	private void firstLastNameLabelsPaneLayout() {

		// first name	
		firstNameLabelsPane.prefWidthProperty().bind(firstNamePane.widthProperty());
		firstNameLabelsPane.prefHeightProperty().bind(firstNamePane.heightProperty().divide(4));

		firstNamePane.getChildren().add(firstNameLabelsPane);

		// last name
		lastNameLabelsPane.prefWidthProperty().bind(lastNamePane.widthProperty());
		lastNameLabelsPane.prefHeightProperty().bind(lastNamePane.heightProperty().divide(4));

		lastNamePane.getChildren().add(lastNameLabelsPane);

		// set layouts
		this.nameLayout();
	}

	// -----------------------------------------------------//
	// Gender Layout

	private static final HBox genderPane = new HBox(VPWIDTH / 7), genderChooserPane = new HBox(VPWIDTH / 14);

	private void genderPaneLayout() {

		genderPane.prefWidthProperty().bind(verticalPane.widthProperty());
		genderPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(24));

		verticalPane.getChildren().add(genderPane);

		// set layouts
		this.genderLabelLayout();
		this.chooseGenderPaneLayout();
		this.genderLayout();

	}

	private void chooseGenderPaneLayout() {	

		genderChooserPane.prefWidthProperty().bind(genderPane.widthProperty().divide(2));
		genderChooserPane.prefHeightProperty().bind(genderPane.heightProperty());

		genderChooserPane.setAlignment(Pos.CENTER);

		genderPane.getChildren().add(genderChooserPane);
	}
	// -----------------------------------------------------//
	// birth date Layout

	// ----------------//
	// Basic

	private static final HBox birthDatePane = new HBox(VPWIDTH / 10), birthDateChoicesPane = new HBox(VPWIDTH / 20);

	private void birthDatePaneLayout() {

		birthDatePane.prefWidthProperty().bind(verticalPane.widthProperty());
		birthDatePane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(24));

		verticalPane.getChildren().add(birthDatePane);

		// set layouts
		this.birthDateLabelLayout();
		this.birthDateChoicesPaneLayout();
		this.birthDtaLayout();
	}

	private void birthDateChoicesPaneLayout() {

		birthDateChoicesPane.prefWidthProperty().bind(birthDatePane.widthProperty().multiply(70).divide(100));
		birthDateChoicesPane.prefHeightProperty().bind(birthDatePane.heightProperty());

		birthDateChoicesPane.setAlignment(Pos.CENTER);

		birthDatePane.getChildren().add(birthDateChoicesPane);

		this.dayMonthYearVBoxLayout();
	}

	// ----------------//
	// Verticals box

	private static final VBox dayVBox = new VBox(), monthVBox = new VBox(), yearVBox = new VBox();

	private void dayMonthYearVBoxLayout() {

		// ------------------//
		// day
		dayVBox.prefWidthProperty().bind(this.birthDatePane.widthProperty().multiply(70).divide(300));
		dayVBox.prefHeightProperty().bind(this.birthDatePane.heightProperty());

		dayVBox.setAlignment(Pos.CENTER);

		birthDateChoicesPane.getChildren().add(dayVBox);

		// ------------------//
		// month
		monthVBox.prefWidthProperty().bind(this.birthDatePane.widthProperty().multiply(70).divide(300));
		monthVBox.prefHeightProperty().bind(this.birthDatePane.heightProperty());

		monthVBox.setAlignment(Pos.CENTER);

		birthDateChoicesPane.getChildren().add(monthVBox);

		// ------------------//
		// year
		yearVBox.prefWidthProperty().bind(this.birthDatePane.widthProperty().multiply(70).divide(300));
		yearVBox.prefHeightProperty().bind(this.birthDatePane.heightProperty());

		yearVBox.setAlignment(Pos.CENTER);

		birthDateChoicesPane.getChildren().add(yearVBox);

		// set layouts
		this.dayMonthYearPaneLayout();

	}
	// ----------------//
	// Horizontal box

	private static final HBox dayPane = new HBox(), monthPane = new HBox(), yearPane = new HBox();

	private void dayMonthYearPaneLayout() {

		// ------------------//
		// day
		dayPane.prefWidthProperty().bind(birthDatePane.widthProperty().divide(4));
		dayPane.prefHeightProperty().bind(birthDatePane.heightProperty().multiply(3).divide(4));

		dayPane.setAlignment(Pos.CENTER);

		dayVBox.getChildren().add(dayPane);

		// ------------------//
		// month		
		monthPane.prefWidthProperty().bind(birthDatePane.widthProperty().divide(4));
		monthPane.prefHeightProperty().bind(birthDatePane.heightProperty().multiply(3).divide(4));

		monthPane.setAlignment(Pos.CENTER);

		monthVBox.getChildren().add(monthPane);

		// ------------------//
		// year
		yearPane.prefWidthProperty().bind(birthDatePane.widthProperty().divide(4));
		yearPane.prefHeightProperty().bind(birthDatePane.heightProperty().multiply(3).divide(4));

		yearPane.setAlignment(Pos.CENTER);

		yearVBox.getChildren().add(yearPane);

	}

	// -----------------------------------------------------//
	// phone number

	private static final HBox phonePane = new HBox(VPWIDTH / 50);

	private void phonePaneLayout() {	

		phonePane.prefWidthProperty().bind(verticalPane.widthProperty());
		phonePane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(24));

		phonePane.setAlignment(Pos.CENTER_LEFT);

		verticalPane.getChildren().add(phonePane);

		this.phoneNumberLayout();

	}

	// -----------------------------------------------------//
	// password

	private static final HBox passwordPane = new HBox(VPWIDTH / 100);

	private void passwordPaneLayout() {

		passwordPane.prefWidthProperty().bind(verticalPane.widthProperty());
		passwordPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(24));

		verticalPane.getChildren().add(passwordPane);

		this.passwordLayout();
	}
	// -----------------------------------------------------//
	// buttons

	private static final HBox buttonsPane = new HBox(VPWIDTH / 5);

	private void buttonsPaneLayout() {		

		buttonsPane.prefWidthProperty().bind(verticalPane.widthProperty());
		buttonsPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(3).divide(17));

		verticalPane.getChildren().add(buttonsPane);

		// set layouts
		this.buttonsLayout();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// -----------------------------------------------------//
	// Text
	private static final Text text = new Text("Enter all information to creat new account");

	private void textlayout() {

		text.setId("primaryText");

		textPane.getChildren().add(text);
	}

	// -----------------------------------------------------//
	// Name

	private static final Label firstNameLabel = new Label("First name"), lastNameLabel = new Label("Last name");
	protected static final TextField firstNameText = new TextField(), lastNameText = new TextField();
	protected static final Label firstNameErrorLabel = new Label(), lastNameErrorLabel = new Label();

	private void nameLayout() {

		// ---------------------------------//
		// first name

		// Label
		firstNameLabel.prefWidthProperty().bind(firstNameLabelsPane.widthProperty().divide(3));
		firstNameLabel.prefHeightProperty().bind(firstNameLabelsPane.heightProperty());

		firstNameLabel.setAlignment(Pos.CENTER_LEFT);
		firstNameLabel.setId("createAccountLabels");

		firstNameLabelsPane.getChildren().add(firstNameLabel);

		// error label	
		firstNameErrorLabel.prefWidthProperty().bind(firstNameLabelsPane.widthProperty().divide(2));
		firstNameErrorLabel.prefHeightProperty().bind(firstNameLabelsPane.heightProperty());

		firstNameErrorLabel.setAlignment(Pos.CENTER_LEFT);

		firstNameErrorLabel.setId("errorMessage");

		firstNameLabelsPane.getChildren().add(firstNameErrorLabel);

		// Text
		firstNameText.prefWidthProperty().bind(firstNamePane.widthProperty());
		firstNameText.prefHeightProperty().bind(firstNamePane.heightProperty().divide(2));

		firstNameText.setId("creatAccountTextField");

		firstNamePane.getChildren().add(firstNameText);

		// ---------------------------------//
		// Last Name

		// Label
		lastNameLabel.prefWidthProperty().bind(lastNameLabelsPane.widthProperty().divide(3));
		lastNameLabel.prefHeightProperty().bind(lastNameLabelsPane.heightProperty());

		lastNameLabel.setAlignment(Pos.CENTER_LEFT);
		lastNameLabel.setId("createAccountLabels");

		lastNameLabelsPane.getChildren().add(lastNameLabel);

		// error label
		lastNameErrorLabel.prefWidthProperty().bind(lastNameLabelsPane.widthProperty().divide(2));
		lastNameErrorLabel.prefHeightProperty().bind(lastNameLabelsPane.heightProperty());

		lastNameErrorLabel.setAlignment(Pos.CENTER_LEFT);

		lastNameErrorLabel.setId("errorMessage");

		lastNameLabelsPane.getChildren().add(lastNameErrorLabel);

		// Text
		lastNameText.prefWidthProperty().bind(lastNamePane.widthProperty());
		lastNameText.prefHeightProperty().bind(lastNamePane.heightProperty().divide(2));

		lastNameText.setId("creatAccountTextField");

		lastNamePane.getChildren().add(lastNameText);
	}

	// -----------------------------------------------------//
	// Gender

	private static final Label genderLabel = new Label("Select gender");
	protected RadioButton male, female;
	protected static final Label genderErrorLabel = new Label();
	private ToggleGroup group;

	private void genderLabelLayout() {
		// Label
		genderLabel.prefWidthProperty().bind(genderPane.widthProperty().multiply(4).divide(14));
		genderLabel.prefHeightProperty().bind(genderPane.heightProperty());

		genderLabel.setAlignment(Pos.CENTER_LEFT);
		genderLabel.setId("createAccountBasicLabels");

		genderPane.getChildren().add(genderLabel);
	}

	private void genderLayout() {

		group = new ToggleGroup();

		// RadioButton
		male = new RadioButton("Male");
		male.prefWidthProperty().bind(genderPane.widthProperty().divide(7));
		male.prefHeightProperty().bind(genderPane.heightProperty());
		male.setToggleGroup(group);
		male.setId("createAccountLabels");

		female = new RadioButton("Female");
		female.prefWidthProperty().bind(genderPane.widthProperty().divide(7));
		female.prefHeightProperty().bind(genderPane.heightProperty());
		female.setToggleGroup(group);
		female.setId("createAccountLabels");

		// error label		
		genderErrorLabel.prefWidthProperty().bind(genderPane.widthProperty().divide(7));
		genderErrorLabel.prefHeightProperty().bind(genderPane.heightProperty());

		genderErrorLabel.setId("errorMessage");

		genderChooserPane.getChildren().addAll(male, female, genderErrorLabel);
	}
	// -----------------------------------------------------//
	// birth date

	private static final Label birthDateLabel = new Label("Birth date"), dayLabel = new Label("day"), monthLabel = new Label("month"), yearLabel = new Label("year");
	protected static final ComboBox<Integer> dayComboBox = new ComboBox<Integer>(), yearComboBox = new ComboBox<Integer>();
	protected static final ComboBox<String> monthComboBox = new ComboBox<String>();
	private Date date;
	protected static final Label errorDay = new Label(), errorMonth = new Label(), errorYear = new Label();

	private void birthDateLabelLayout() {

		// birth date label
		birthDateLabel.prefWidthProperty().bind(birthDatePane.widthProperty().divide(10));
		birthDateLabel.prefHeightProperty().bind(birthDatePane.heightProperty());

		birthDateLabel.setAlignment(Pos.CENTER_LEFT);
		birthDateLabel.setId("createAccountBasicLabels");

		birthDatePane.getChildren().add(birthDateLabel);

	}

	private void birthDtaLayout() {

		// ----------------------------//
		// day

		// label	
		dayLabel.prefWidthProperty().bind(dayPane.widthProperty().divide(6));
		dayLabel.prefHeightProperty().bind(dayPane.heightProperty());

		dayLabel.setAlignment(Pos.CENTER_LEFT);
		dayLabel.setId("createAccountLabels");

		dayPane.getChildren().add(dayLabel);

		// ComboBox
		
		// add elements in dayComboBox from 1 to 31
		for (int i = 1; i <= 31; dayComboBox.getItems().add(i++))
			;

		dayComboBox.prefWidthProperty().bind(dayPane.widthProperty().multiply(5).divide(6));
		dayComboBox.prefHeightProperty().bind(dayPane.heightProperty().divide(2));

		dayComboBox.setId("createAccountComboBox");

		dayPane.getChildren().add(dayComboBox);

		// error
		errorDay.prefWidthProperty().bind(dayVBox.widthProperty());
		errorDay.prefHeightProperty().bind(dayVBox.heightProperty().divide(4));

		errorDay.setAlignment(Pos.CENTER_LEFT);
		errorDay.setId("errorMessage");

		dayVBox.getChildren().add(errorDay);

		// ----------------------------//
		// month

		// label
		monthLabel.prefWidthProperty().bind(monthPane.widthProperty().divide(5));
		monthLabel.prefHeightProperty().bind(monthPane.heightProperty());

		monthLabel.setAlignment(Pos.CENTER_LEFT);
		monthLabel.setId("createAccountLabels");

		monthPane.getChildren().add(monthLabel);

		// ComboBox

		// month names
		String[] months = { "January", "February", "March", "Abrel", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		
		// add all months in monthComboBox
		for (int i = 0; i < months.length; monthComboBox.getItems().add(months[i++]))
			;

		monthComboBox.prefWidthProperty().bind(monthPane.widthProperty().multiply(4).divide(5));
		monthComboBox.prefHeightProperty().bind(monthPane.heightProperty().divide(2));

		monthComboBox.setId("createAccountComboBox");

		monthPane.getChildren().add(monthComboBox);

		// error

		errorMonth.prefWidthProperty().bind(monthVBox.widthProperty());
		errorMonth.prefHeightProperty().bind(monthVBox.heightProperty().divide(4));

		errorMonth.setAlignment(Pos.CENTER_LEFT);
		errorMonth.setId("errorMessage");

		monthVBox.getChildren().add(errorMonth);

		// ----------------------------//
		// year

		// label
		yearLabel.prefWidthProperty().bind(yearPane.widthProperty().divide(6));
		yearLabel.prefHeightProperty().bind(yearPane.heightProperty());

		yearLabel.setAlignment(Pos.CENTER_LEFT);
		yearLabel.setId("createAccountLabels");

		yearPane.getChildren().add(yearLabel);

		// ComboBox

		date = new Date();
		int thisYear = date.getYear() + 1900;

		for (int i = thisYear - 100; i <= thisYear; yearComboBox.getItems().add(i++))
			;

		yearComboBox.prefWidthProperty().bind(yearPane.widthProperty().multiply(5).divide(6));
		yearComboBox.prefHeightProperty().bind(yearPane.heightProperty().divide(2));

		yearComboBox.setId("createAccountComboBox");

		yearPane.getChildren().add(yearComboBox);

		// error
		errorYear.prefWidthProperty().bind(yearVBox.widthProperty());
		errorYear.prefHeightProperty().bind(yearVBox.heightProperty().divide(4));

		errorYear.setAlignment(Pos.CENTER_LEFT);
		errorYear.setId("errorMessage");

		yearVBox.getChildren().add(errorYear);

	}

	// -----------------------------------------------------//
	// Phone number

	private static final Label phoneNumberLabel = new Label("Phone Number");
	protected static final TextField phoneNumberText1 = new TextField(), phoneNumberText2 = new TextField();
	protected static final Label phoneNumberErrorLabel = new Label();

	private void phoneNumberLayout() {

		// label
		phoneNumberLabel.prefWidthProperty().bind(verticalPane.widthProperty().multiply(17).divide(50));
		phoneNumberLabel.prefHeightProperty().bind(phonePane.heightProperty());

		phoneNumberLabel.setId("createAccountBasicLabels");

		phonePane.getChildren().add(phoneNumberLabel);

		// Texts

		// 1
		phoneNumberText1.prefWidthProperty().bind(verticalPane.widthProperty().divide(5));
		phoneNumberText1.prefHeightProperty().bind(phonePane.heightProperty());

		phoneNumberText1.setId("creatAccountTextField");

		phonePane.getChildren().add(phoneNumberText1);

		// 2
		phoneNumberText2.prefWidthProperty().bind(verticalPane.widthProperty().divide(5));
		phoneNumberText2.prefHeightProperty().bind(phonePane.heightProperty());

		phoneNumberText2.setId("creatAccountTextField");

		phonePane.getChildren().add(phoneNumberText2);

		// error	
		phoneNumberErrorLabel.prefWidthProperty().bind(verticalPane.widthProperty().divide(5));
		phoneNumberErrorLabel.prefHeightProperty().bind(phonePane.heightProperty());

		phoneNumberErrorLabel.setAlignment(Pos.CENTER);

		phoneNumberErrorLabel.setTextFill(Color.RED);

		phonePane.getChildren().add(phoneNumberErrorLabel);
	}

	// -----------------------------------------------------//
	// code

	private static final Label passwordLabel = new Label("Password");
	protected static final TextField passwordText = new TextField();
	protected static final Label passwordErrorLabel = new Label();

	private void passwordLayout() {

		// label	
		passwordLabel.prefHeightProperty().bind(passwordPane.heightProperty());
		passwordLabel.prefWidthProperty().bind(passwordPane.widthProperty().divide(5));

		passwordLabel.setId("createAccountBasicLabels");

		passwordPane.getChildren().add(passwordLabel);

		// Text
		

		passwordText.prefHeightProperty().bind(passwordPane.heightProperty());
		passwordText.prefWidthProperty().bind(passwordPane.widthProperty().divide(4));

		passwordText.setId("creatAccountTextField");

		passwordPane.getChildren().add(passwordText);

		// Error Label
		passwordErrorLabel.prefHeightProperty().bind(passwordPane.heightProperty());
		passwordErrorLabel.prefWidthProperty().bind(passwordPane.widthProperty().divide(4));
		
		passwordErrorLabel.setId("errorMessage");

		passwordPane.getChildren().add(passwordErrorLabel);

	}
	// -----------------------------------------------------//
	// buttons

	protected Button createAccount, backToLogin;

	private void buttonsLayout() {

		// create account
		createAccount = new Button("Create acount");

		createAccount.prefWidthProperty().bind(buttonsPane.widthProperty().multiply(2).divide(5));
		createAccount.prefHeightProperty().bind(buttonsPane.heightProperty());

		createAccount.setId("primaryButton");

		buttonsPane.getChildren().add(createAccount);

		// back to login
		backToLogin = new Button("Back to login");

		backToLogin.prefWidthProperty().bind(buttonsPane.widthProperty().multiply(2).divide(5));
		backToLogin.prefHeightProperty().bind(buttonsPane.heightProperty());

		backToLogin.setId("primaryButton");

		buttonsPane.getChildren().add(backToLogin);
	}
}
