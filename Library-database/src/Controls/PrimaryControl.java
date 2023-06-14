package Controls;

import Layout.PrimaryLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PrimaryControl extends PrimaryLayout implements EventHandler<ActionEvent> {

	private final String STYLE = "style.css";

	private Stage controlStage;
	private Scene controlScene;

	/*------------------------------*/
	private CreateAccountControl createAccountControl;
	private LoginControl loginControl;
	private SubmetJobAppControl submetJobAppControl;

	public void setCreateAccountControl(CreateAccountControl createAccountControl) {
		this.createAccountControl = createAccountControl;
	}

	public void setSubmetJobAppControl(SubmetJobAppControl submetJobAppControl) {
		this.submetJobAppControl = submetJobAppControl;
	}

	public PrimaryControl(Stage controlStage, CreateAccountControl createAccountControl, LoginControl loginControl,
			SubmetJobAppControl submetJobAppControl) {
		this.setReferences(controlStage, createAccountControl, loginControl, submetJobAppControl);
		this.setEvents();
	}

	private void setReferences(Stage controlStage, CreateAccountControl createAccountControl, LoginControl loginControl,
			SubmetJobAppControl submetJobAppContro) {

		this.createAccountControl = createAccountControl;
		this.loginControl = loginControl;
		this.submetJobAppControl = submetJobAppContro;

		this.controlStage = controlStage;
		this.controlScene = new Scene(this);
		controlScene.getStylesheets().add(getClass().getResource(STYLE).toExternalForm());
		
	}

	public void setScene() {
		
		// title
		controlStage.setTitle("Library");
		
		// size
		controlStage.setResizable(false);
		controlStage.setWidth(super.WIDTH);
		controlStage.setHeight(super.HEIGHT);

		// add scene
		this.controlStage.setScene(this.controlScene);
		
	}

	private void setEvents() {
		this.login.setOnAction(this);
		this.createAccount.setOnAction(this);
		this.jobApplication.setOnAction(this);

	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource().equals(this.createAccount)) {
			this.createAccountControl.setScene();
		} else if (event.getSource().equals(this.login)) {
			this.loginControl.setScene();
		} else if (event.getSource().equals(this.jobApplication)) {
			this.submetJobAppControl.setScene();
		}
	}
}
