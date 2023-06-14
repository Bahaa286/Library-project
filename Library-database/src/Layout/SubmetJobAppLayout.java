package Layout;

import java.util.Date;

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

public class SubmetJobAppLayout extends Pane {

	protected static final int HEIGHT = 700;
	protected static final int WIDTH = 1100;

	private static final int VPHEIGHT = HEIGHT * 100 / 102;
	private static final int VPWIDTH = WIDTH * 50 / 52;

	public SubmetJobAppLayout() {

		this.imageLayout();

		this.verticalPaneLayout();

		this.setPrefSize(WIDTH, HEIGHT);

	}

	/////////////////////////////////////////////////////////////////
	// Image

	private static final Image image = new Image("images/submetJobImage.jpg");
	private static final ImageView imageView = new ImageView(image);

	private void imageLayout() {

		imageView.fitHeightProperty().bind(this.heightProperty());
		imageView.fitWidthProperty().bind(this.widthProperty());

		this.getChildren().add(imageView);

	}
	/////////////////////////////////////////////////////////////////
	// Panes

	// --------------------------------------------------------------//
	// Primary

	private static final VBox verticalPane = new VBox(HEIGHT / 102);

	private void verticalPaneLayout() {

		verticalPane.layoutXProperty().bind(this.widthProperty().divide(52));
		verticalPane.layoutYProperty().bind(this.heightProperty().divide(102));
		verticalPane.prefHeightProperty().bind(this.heightProperty().multiply(100).divide(102));
		verticalPane.prefWidthProperty().bind(this.widthProperty().multiply(50).divide(52));

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

	// --------------------------------------------------------------//
	// Text

	private static final HBox textPane = new HBox();

	private void textPaneLayout() {

		textPane.prefHeightProperty().bind(this.verticalPane.heightProperty().multiply(20).divide(102));
		textPane.prefWidthProperty().bind(this.verticalPane.widthProperty());

		textPane.setAlignment(Pos.CENTER);

		verticalPane.getChildren().add(textPane);

		this.textlayout();

	}

	// -----------------------------------------------------//
	// Name Layout

	private static final HBox namePane = new HBox(VPWIDTH * 3 / 31);

	private void namePaneLayout() {

		namePane.prefWidthProperty().bind(this.verticalPane.widthProperty());
		namePane.prefHeightProperty().bind(this.heightProperty().multiply(20).divide(102));

		this.verticalPane.getChildren().add(namePane);

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

	private static final HBox firstNameLabelsPane = new HBox(FLNPWIDTH / 6),
			lastNameLabelsPane = new HBox(FLNPWIDTH / 6);

	private void firstLastNameLabelsPaneLayout() {

		// first name

		firstNameLabelsPane.prefWidthProperty().bind(this.firstNamePane.widthProperty());
		firstNameLabelsPane.prefHeightProperty().bind(this.firstNamePane.heightProperty().divide(4));

		firstNamePane.getChildren().add(firstNameLabelsPane);

		// last name

		lastNameLabelsPane.prefWidthProperty().bind(this.lastNamePane.widthProperty());
		lastNameLabelsPane.prefHeightProperty().bind(this.lastNamePane.heightProperty().divide(4));

		lastNamePane.getChildren().add(lastNameLabelsPane);

		// set layouts
		this.nameLayout();
	}

	// -----------------------------------------------------//
	// Gender Layout

	private static final HBox genderPane = new HBox(VPWIDTH / 7), genderChooserPane = new HBox(VPWIDTH / 14);

	private void genderPaneLayout() {

		genderPane.prefWidthProperty().bind(this.verticalPane.widthProperty());
		genderPane.prefHeightProperty().bind(this.verticalPane.heightProperty().multiply(10).divide(102));

		this.verticalPane.getChildren().add(genderPane);

		// set layouts
		this.genderLabelLayout();
		this.chooseGenderPaneLayout();
		this.genderLayout();

	}

	private void chooseGenderPaneLayout() {

		genderChooserPane.prefWidthProperty().bind(this.genderPane.widthProperty().divide(2));
		genderChooserPane.prefHeightProperty().bind(this.genderPane.heightProperty());

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
		birthDatePane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(10).divide(102));

		verticalPane.getChildren().add(birthDatePane);

		// set layouts
		this.birthDateLabelLayout();
		this.birthDateChoicesPaneLayout();
		this.birthDtaLayout();
	}

	private void birthDateChoicesPaneLayout() {

		birthDateChoicesPane.prefWidthProperty().bind(this.birthDatePane.widthProperty().multiply(70).divide(100));
		birthDateChoicesPane.prefHeightProperty().bind(this.birthDatePane.heightProperty());

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
		dayPane.prefWidthProperty().bind(birthDatePane.widthProperty().multiply(70).divide(300));
		dayPane.prefHeightProperty().bind(birthDatePane.heightProperty().multiply(3).divide(4));

		dayPane.setAlignment(Pos.CENTER);

		dayVBox.getChildren().add(dayPane);

		// ------------------//
		// month
		monthPane.prefWidthProperty().bind(birthDatePane.widthProperty().multiply(70).divide(300));
		monthPane.prefHeightProperty().bind(birthDatePane.heightProperty().multiply(3).divide(4));

		monthPane.setAlignment(Pos.CENTER);

		monthVBox.getChildren().add(monthPane);

		// ------------------//
		// year
		yearPane.prefWidthProperty().bind(birthDatePane.widthProperty().multiply(70).divide(300));
		yearPane.prefHeightProperty().bind(birthDatePane.heightProperty().multiply(3).divide(4));

		yearPane.setAlignment(Pos.CENTER);

		yearVBox.getChildren().add(yearPane);

	}

	// -----------------------------------------------------//
	// phone number

	private static final HBox phonePane = new HBox(VPWIDTH / 50);

	private void phonePaneLayout() {

		phonePane.prefWidthProperty().bind(verticalPane.widthProperty());
		phonePane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(10).divide(102));

		phonePane.setAlignment(Pos.CENTER_LEFT);

		verticalPane.getChildren().add(phonePane);

		this.phoneNumberLayout();

	}

	// -----------------------------------------------------//
	// certificate

	private static final HBox passwordPane = new HBox(VPWIDTH / 100);

	private void passwordPaneLayout() {

		passwordPane.prefWidthProperty().bind(verticalPane.widthProperty());
		passwordPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(10).divide(102));

		verticalPane.getChildren().add(passwordPane);

		// set layouts
		this.passwordLayout();

	}

	// -----------------------------------------------------//
	// buttons

	private static final HBox buttonsPane = new HBox(VPWIDTH / 5);

	private void buttonsPaneLayout() {

		buttonsPane.prefWidthProperty().bind(verticalPane.widthProperty());
		buttonsPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(20).divide(102));

		verticalPane.getChildren().add(buttonsPane);

		// set layouts
		this.buttonsLayout();
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// -----------------------------------------------------//
	// Text
	private static final Text text = new Text("Enter all information to submet to the job");

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

		this.firstNameLabelsPane.getChildren().add(firstNameLabel);

		// error label
		firstNameErrorLabel.prefWidthProperty().bind(firstNameLabelsPane.widthProperty().divide(2));
		firstNameErrorLabel.prefHeightProperty().bind(firstNameLabelsPane.heightProperty());

		firstNameErrorLabel.setAlignment(Pos.CENTER_LEFT);

		firstNameErrorLabel.setId("errorMessage");

		this.firstNameLabelsPane.getChildren().add(firstNameErrorLabel);

		// Text
		firstNameText.prefWidthProperty().bind(firstNamePane.widthProperty());
		firstNameText.prefHeightProperty().bind(firstNamePane.heightProperty().divide(2));

		firstNameText.setId("creatAccountTextField");

		this.firstNamePane.getChildren().add(firstNameText);

		// ---------------------------------//
		// Last Name

		// Label

		lastNameLabel.prefWidthProperty().bind(lastNameLabelsPane.widthProperty().divide(3));
		lastNameLabel.prefHeightProperty().bind(lastNameLabelsPane.heightProperty());

		lastNameLabel.setAlignment(Pos.CENTER_LEFT);
		lastNameLabel.setId("createAccountLabels");

		this.lastNameLabelsPane.getChildren().add(lastNameLabel);

		// error label
		lastNameErrorLabel.prefWidthProperty().bind(lastNameLabelsPane.widthProperty().divide(2));
		lastNameErrorLabel.prefHeightProperty().bind(lastNameLabelsPane.heightProperty());

		lastNameErrorLabel.setAlignment(Pos.CENTER_LEFT);

		lastNameErrorLabel.setId("errorMessage");

		this.lastNameLabelsPane.getChildren().add(lastNameErrorLabel);

		// Text
		lastNameText.prefWidthProperty().bind(lastNamePane.widthProperty());
		lastNameText.prefHeightProperty().bind(lastNamePane.heightProperty().divide(2));

		lastNameText.setId("creatAccountTextField");

		this.lastNamePane.getChildren().add(lastNameText);
	}

	// -----------------------------------------------------//
	// Gender

	private static final Label genderLabel = new Label("Select gender");
	protected static final RadioButton male = new RadioButton("Male"), female = new RadioButton("Female");
	protected static final Label genderErrorLabel = new Label();
	private static final ToggleGroup group = new ToggleGroup();

	private void genderLabelLayout() {

		// Label
		genderLabel.prefWidthProperty().bind(this.genderPane.widthProperty().multiply(4).divide(14));
		genderLabel.prefHeightProperty().bind(this.genderPane.heightProperty());

		genderLabel.setAlignment(Pos.CENTER_LEFT);
		genderLabel.setId("createAccountBasicLabels");

		this.genderPane.getChildren().add(genderLabel);
	}

	private void genderLayout() {

		// RadioButton

		male.prefWidthProperty().bind(this.genderPane.widthProperty().divide(7));
		male.prefHeightProperty().bind(this.genderPane.heightProperty());
		male.setToggleGroup(group);
		male.setId("createAccountLabels");

		female.prefWidthProperty().bind(this.genderPane.widthProperty().divide(7));
		female.prefHeightProperty().bind(this.genderPane.heightProperty());
		female.setToggleGroup(group);
		female.setId("createAccountLabels");

		// error label
		genderErrorLabel.prefWidthProperty().bind(this.genderPane.widthProperty().divide(7));
		genderErrorLabel.prefHeightProperty().bind(this.genderPane.heightProperty());

		genderErrorLabel.setId("errorMessage");

		this.genderChooserPane.getChildren().addAll(male, female, genderErrorLabel);
	}
	// -----------------------------------------------------//
	// birth date

	private static final Label birthDateLabel = new Label("Birth date"), dayLabel = new Label("day"),
			monthLabel = new Label("month"), yearLabel = new Label("year");
	protected static final ComboBox<Integer> dayComboBox = new ComboBox<Integer>(), yearComboBox = new ComboBox<Integer>();
	protected static final ComboBox<String> monthComboBox = new ComboBox<String>();
	private static final Date date = new Date();
	protected static final Label errorDay = new Label(), errorMonth = new Label(), errorYear = new Label();

	private void birthDateLabelLayout() {

		// birth date label

		birthDateLabel.prefWidthProperty().bind(this.birthDatePane.widthProperty().divide(10));
		birthDateLabel.prefHeightProperty().bind(this.birthDatePane.heightProperty());

		birthDateLabel.setAlignment(Pos.CENTER_LEFT);
		birthDateLabel.setId("createAccountBasicLabels");

		this.birthDatePane.getChildren().add(birthDateLabel);

	}

	private void birthDtaLayout() {

		// ----------------------------//
		// day

		// label
		dayLabel.prefWidthProperty().bind(this.dayPane.widthProperty().divide(6));
		dayLabel.prefHeightProperty().bind(this.dayPane.heightProperty());

		dayLabel.setAlignment(Pos.CENTER_LEFT);
		dayLabel.setId("createAccountLabels");

		this.dayPane.getChildren().add(dayLabel);

		// ComboBox		
		for (int i = 1; i <= 31; dayComboBox.getItems().add(i++))
			;

		dayComboBox.prefWidthProperty().bind(this.dayPane.widthProperty().multiply(5).divide(6));
		dayComboBox.prefHeightProperty().bind(this.dayPane.heightProperty().divide(2));

		dayComboBox.setId("createAccountComboBox");

		this.dayPane.getChildren().add(dayComboBox);

		// error
		errorDay.prefWidthProperty().bind(this.dayVBox.widthProperty());
		errorDay.prefHeightProperty().bind(this.dayVBox.heightProperty().divide(4));

		errorDay.setAlignment(Pos.CENTER_LEFT);
		errorDay.setId("errorMessage");

		this.dayVBox.getChildren().add(errorDay);

		// ----------------------------//
		// month

		// label

		monthLabel.prefWidthProperty().bind(this.monthPane.widthProperty().divide(3));
		monthLabel.prefHeightProperty().bind(this.monthPane.heightProperty());

		monthLabel.setAlignment(Pos.CENTER_LEFT);
		monthLabel.setId("createAccountLabels");

		this.monthPane.getChildren().add(monthLabel);

		// ComboBox
		

		String[] months = { "January", "February", "March", "Abrel", "May", "June", "July", "August", "September",
				"October", "November", "December" };
		for (int i = 0; i < months.length; monthComboBox.getItems().add(months[i++]))
			;

		monthComboBox.prefWidthProperty().bind(this.monthPane.widthProperty().multiply(2).divide(3));
		monthComboBox.prefHeightProperty().bind(this.monthPane.heightProperty().divide(2));

		monthComboBox.setId("createAccountComboBox");

		this.monthPane.getChildren().add(monthComboBox);

		// error
		errorMonth.prefWidthProperty().bind(this.monthVBox.widthProperty());
		errorMonth.prefHeightProperty().bind(this.monthVBox.heightProperty().divide(4));

		errorMonth.setAlignment(Pos.CENTER_LEFT);
		errorMonth.setId("errorMessage");

		this.monthVBox.getChildren().add(errorMonth);

		// ----------------------------//
		// year

		// label

		yearLabel.prefWidthProperty().bind(this.yearPane.widthProperty().divide(3));
		yearLabel.prefHeightProperty().bind(this.yearPane.heightProperty());

		yearLabel.setAlignment(Pos.CENTER_LEFT);
		yearLabel.setId("createAccountLabels");

		this.yearPane.getChildren().add(yearLabel);

		// ComboBox

		int thisYear = date.getYear() + 1900;

		for (int i = thisYear - 100; i <= thisYear; yearComboBox.getItems().add(i++))
			;

		yearComboBox.prefWidthProperty().bind(this.yearPane.widthProperty().multiply(2).divide(3));
		yearComboBox.prefHeightProperty().bind(this.yearPane.heightProperty().divide(2));

		yearComboBox.setId("createAccountComboBox");

		this.yearPane.getChildren().add(yearComboBox);

		// error
		errorYear.prefWidthProperty().bind(this.yearVBox.widthProperty());
		errorYear.prefHeightProperty().bind(this.yearVBox.heightProperty().divide(4));

		errorYear.setAlignment(Pos.CENTER_LEFT);
		errorYear.setId("errorMessage");

		this.yearVBox.getChildren().add(errorYear);

	}

	// -----------------------------------------------------//
	// Phone number

	private static final Label phoneNumberLabel = new Label("Phone Number");
	protected static final TextField phoneNumberText1 = new TextField(), phoneNumberText2 = new TextField();
	protected static final Label phoneNumberErrorLabel = new Label();

	private void phoneNumberLayout() {

		// label
		phoneNumberLabel.prefWidthProperty().bind(this.verticalPane.widthProperty().multiply(17).divide(50));
		phoneNumberLabel.prefHeightProperty().bind(this.phonePane.heightProperty());

		phoneNumberLabel.setId("createAccountBasicLabels");

		this.phonePane.getChildren().add(phoneNumberLabel);

		// Texts

		// 1
		phoneNumberText1.prefWidthProperty().bind(this.verticalPane.widthProperty().divide(5));
		phoneNumberText1.prefHeightProperty().bind(this.phonePane.heightProperty());

		phoneNumberText1.setId("creatAccountTextField");

		this.phonePane.getChildren().add(phoneNumberText1);

		// 2
		phoneNumberText2.prefWidthProperty().bind(this.verticalPane.widthProperty().divide(5));
		phoneNumberText2.prefHeightProperty().bind(this.phonePane.heightProperty());

		phoneNumberText2.setId("creatAccountTextField");

		this.phonePane.getChildren().add(phoneNumberText2);

		// error
		phoneNumberErrorLabel.prefWidthProperty().bind(this.verticalPane.widthProperty().divide(5));
		phoneNumberErrorLabel.prefHeightProperty().bind(this.phonePane.heightProperty());

		phoneNumberErrorLabel.setAlignment(Pos.CENTER);

		phoneNumberErrorLabel.setTextFill(Color.RED);

		this.phonePane.getChildren().add(phoneNumberErrorLabel);
	}

	// -----------------------------------------------------//
	// certificate

	private static final Label passwordLable = new Label("password");
	protected static TextField passwordTextField = new TextField();
	protected static final Label passwordErrorLabel = new Label();

	private void passwordLayout() {

		// label
		passwordLable.prefWidthProperty().bind(passwordPane.widthProperty().multiply(13).divide(50));
		passwordLable.prefHeightProperty().bind(passwordPane.heightProperty());

		passwordLable.setId("createAccountBasicLabels");

		passwordPane.getChildren().add(passwordLable);

		// TextField
		passwordTextField.prefWidthProperty().bind(passwordPane.widthProperty().multiply(13).divide(50));
		passwordTextField.prefHeightProperty().bind(passwordPane.heightProperty());

		passwordTextField.setId("creatAccountTextField");

		passwordPane.getChildren().add(passwordTextField);

		// error
		passwordErrorLabel.prefWidthProperty().bind(passwordPane.widthProperty().multiply(13).divide(50));
		passwordErrorLabel.prefHeightProperty().bind(passwordPane.heightProperty());

		passwordErrorLabel.setAlignment(Pos.CENTER);
		passwordErrorLabel.setTextFill(Color.RED);

		passwordPane.getChildren().add(passwordErrorLabel);

	}
	// -----------------------------------------------------//
	// buttons

	protected static final Button submit = new Button("submit"), backToLogin = new Button("Back to login");

	private void buttonsLayout() {
		
		// create account		

		submit.prefWidthProperty().bind(buttonsPane.widthProperty().multiply(2).divide(5));
		submit.prefHeightProperty().bind(buttonsPane.heightProperty());

		submit.setId("primaryButton");

		buttonsPane.getChildren().add(submit);

		// back to login		

		backToLogin.prefWidthProperty().bind(buttonsPane.widthProperty().multiply(2).divide(5));
		backToLogin.prefHeightProperty().bind(buttonsPane.heightProperty());

		backToLogin.setId("primaryButton");

		buttonsPane.getChildren().add(backToLogin);
	}
}
