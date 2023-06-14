package Layout;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class LoginLayout extends Pane {

	protected static final int HEIGHT = 400;
	protected static final int WIDTH = 800;

	public LoginLayout() {
		this.setId("pane");
		
		//image
		this.primaryImageLayout();

		//vertical panes
		this.verticalPaneLayout();
		this.userIDVPaneLayout();
		this.passwordVPaneLayout();
		this.buttonsPaneLayout();
		
		//horizontal panes 
		this.passwordHPaneLayout();
		this.userIDHPaneLayout();
		
		//ID
		this.userIDLabelLayout();
		this.errorUserIDLabelLayout();
		this.userIDTextTextLyout();
		
		//password
		this.passwordLabelLayout();
		this.errorPasswordLabelLayout();
		this.passwordTextLyout();
		
		//buttons
		this.loginLayout();
		this.creatAccountLayout();
		this.backToPrimaryPageLayout();

		
		this.setPrefSize(WIDTH, HEIGHT);
	}
	/////////////////////////////////////////////////////
	//Image
	
	private static final Image primaryImage = new Image("images/primary.jpg");
	private static final ImageView primaryImageView = new ImageView(primaryImage);
	
	
	private void primaryImageLayout() {
	
		primaryImageView.fitHeightProperty().bind(this.heightProperty());
		primaryImageView.fitWidthProperty().bind(this.widthProperty());
		
		this.getChildren().add(primaryImageView);
		
		
	}
	/////////////////////////////////////////////////////
	// Pane

	private static final VBox verticalPane = new VBox(HEIGHT / 18);

	private void verticalPaneLayout() {

		verticalPane.layoutXProperty().bind(this.widthProperty().divide(10));
		verticalPane.layoutYProperty().bind(this.heightProperty().divide(9));
		verticalPane.prefWidthProperty().bind(this.widthProperty().multiply(8).divide(10));
		verticalPane.prefHeightProperty().bind(this.heightProperty().multiply(7).divide(9));

		this.getChildren().add(verticalPane);

	}

	private static final HBox buttonsPane = new HBox(WIDTH / 20);

	private void buttonsPaneLayout() {	

		buttonsPane.prefWidthProperty().bind(verticalPane.widthProperty());
		buttonsPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(9));

		verticalPane.getChildren().add(buttonsPane);

	}

	private static final VBox userIDVPane = new VBox();

	private void userIDVPaneLayout() {		

		userIDVPane.prefWidthProperty().bind(verticalPane.widthProperty());
		userIDVPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(9));

		verticalPane.getChildren().add(userIDVPane);

	}

	private static final HBox userIDHPane = new HBox(8*WIDTH/100);

	private void userIDHPaneLayout() {

		userIDHPane.prefWidthProperty().bind(verticalPane.widthProperty());
		userIDHPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(27));

		userIDVPane.getChildren().add(userIDHPane);

	}

	private static final VBox passwordVPane = new VBox();

	private void passwordVPaneLayout() {		

		passwordVPane.prefWidthProperty().bind(verticalPane.widthProperty());
		passwordVPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(9));

		verticalPane.getChildren().add(passwordVPane);

	}

	private static final HBox passwordHPane = new HBox(8*WIDTH/100);

	private void passwordHPaneLayout() {	

		passwordHPane.prefWidthProperty().bind(verticalPane.widthProperty());
		passwordHPane.prefHeightProperty().bind(verticalPane.heightProperty().multiply(2).divide(27));

		passwordVPane.getChildren().add(passwordHPane);

	}

	////////////////////////////////////////////////////////////////
	// Button

	protected static final Button login = new Button("Login");

	private void loginLayout() {
		

		login.setId("primaryButton");

		login.prefWidthProperty().bind(buttonsPane.widthProperty().subtract(WIDTH / 20).divide(4));
		login.prefHeightProperty().bind(buttonsPane.heightProperty());

		buttonsPane.getChildren().add(login);
	}

	protected static final Button creatAccount = new Button("Creat acount");

	private void creatAccountLayout() {
		

		creatAccount.setId("primaryButton");

		creatAccount.prefWidthProperty().bind(buttonsPane.widthProperty().subtract(WIDTH / 20).divide(4));
		creatAccount.prefHeightProperty().bind(buttonsPane.heightProperty());

		buttonsPane.getChildren().add(creatAccount);
	}
	
	protected static final Button backToPrimaryPage = new Button("Back to primary paget");
	
	private void backToPrimaryPageLayout() {

		backToPrimaryPage.setId("primaryButton");

		backToPrimaryPage.prefWidthProperty().bind(buttonsPane.widthProperty().subtract(WIDTH / 20).divide(2));
		backToPrimaryPage.prefHeightProperty().bind(buttonsPane.heightProperty());

		buttonsPane.getChildren().add(backToPrimaryPage);
		
	}
	////////////////////////////////////////////////////////////
	// Label

	private static final Label userIDLabel = new Label("userID");

	private void userIDLabelLayout() {	

		userIDLabel.setId("primaryLabel");

		userIDLabel.prefWidthProperty().bind(userIDHPane.widthProperty().multiply(4).divide(10));
		userIDLabel.prefHeightProperty().bind(userIDHPane.heightProperty());

		userIDHPane.getChildren().add(userIDLabel);
	}

	protected static final Label errorUserIDLabel = new Label("");

	private void errorUserIDLabelLayout() {

		errorUserIDLabel.setId("errorMessage");

		errorUserIDLabel.prefWidthProperty().bind(userIDHPane.widthProperty().multiply(5).divide(10));
		errorUserIDLabel.prefHeightProperty().bind(userIDHPane.heightProperty());

		userIDHPane.getChildren().add(errorUserIDLabel);
	}

	private static final Label passwordLabel = new Label("password");

	private void passwordLabelLayout() {	

		passwordLabel.setId("primaryLabel");
		
		passwordLabel.prefWidthProperty().bind(passwordHPane.widthProperty().multiply(4).divide(10));
		passwordLabel.prefHeightProperty().bind(passwordHPane.heightProperty());

		passwordHPane.getChildren().add(passwordLabel);
	}

	protected static final Label errorPasswordLabel = new Label("");

	private void errorPasswordLabelLayout() {
		
		errorPasswordLabel.setId("errorMessage");
		
		errorPasswordLabel.prefWidthProperty().bind(passwordHPane.widthProperty().multiply(5).divide(10));
		errorPasswordLabel.prefHeightProperty().bind(passwordHPane.heightProperty());

		passwordHPane.getChildren().add(errorPasswordLabel);
	}

	//////////////////////////////////////////////////////////////
	// TextField

	protected static final PasswordField passwordText = new PasswordField();

	private void passwordTextLyout() {

		passwordText.setId("primaryTextField");
		
		passwordText.prefWidthProperty().bind(passwordVPane.widthProperty());
		passwordText.prefHeightProperty().bind(passwordVPane.heightProperty().multiply(4).divide(27));

		passwordVPane.getChildren().add(passwordText);
	}

	protected static final TextField userIDText = new TextField();

	private void userIDTextTextLyout() {

		userIDText.setId("primaryTextField");

		userIDText.prefWidthProperty().bind(userIDVPane.widthProperty());
		userIDText.prefHeightProperty().bind(userIDVPane.heightProperty().multiply(4).divide(27));

		userIDVPane.getChildren().add(userIDText);
	}
}
