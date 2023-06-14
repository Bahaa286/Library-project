package Layout;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PrimaryLayout extends Pane {

	protected static final int HEIGHT = 300;
	protected static final int WIDTH = 400;

	public PrimaryLayout() {
		
		this.imageLayout();
		this.verticalPaneLayout();
		this.buttonsLayout();

		this.setPrefSize(WIDTH, HEIGHT);

	}

	/////////////////////////////////////////////////////////////////
	// Image

	private static final Image image = new Image("images/LibraryImage.jpg");
	private static final ImageView imageView = new ImageView(image);
	
	private void imageLayout() {	
		
		imageView.fitHeightProperty().bind(this.heightProperty());
		imageView.fitWidthProperty().bind(this.widthProperty());
		
		this.getChildren().add(imageView);

	}

	/////////////////////////////////////////////////////////////////
	// VBox

	private static final VBox verticalPane = new VBox(HEIGHT / 10);

	private void verticalPaneLayout() {	

		verticalPane.layoutXProperty().bind(this.widthProperty().divide(10));
		verticalPane.layoutYProperty().bind(this.heightProperty().divide(10));
		verticalPane.prefHeightProperty().bind(this.heightProperty().multiply(4).divide(5));
		verticalPane.prefWidthProperty().bind(this.widthProperty().multiply(4).divide(5));

		this.getChildren().add(verticalPane);

	}

    /////////////////////////////////////////////////////////////////
	// Buttons   
	
	protected static final Button login = new Button("Go to login page"), createAccount = new Button("Create account"), jobApplication = new Button("submit a job application");
	
	private void buttonsLayout() {
			
		login.prefHeightProperty().bind(this.heightProperty().divide(5));
		login.prefWidthProperty().bind(this.widthProperty().multiply(4).divide(5));
		login.setId("primaryButton");
				
		createAccount.prefHeightProperty().bind(this.heightProperty().divide(5));
		createAccount.prefWidthProperty().bind(this.widthProperty().multiply(4).divide(5));
		createAccount.setId("primaryButton");
				
		jobApplication.prefHeightProperty().bind(this.heightProperty().divide(5));
		jobApplication.prefWidthProperty().bind(this.widthProperty().multiply(4).divide(5));
		jobApplication.setId("primaryButton");
		
		verticalPane.getChildren().addAll(login, createAccount, jobApplication);
	}
}
