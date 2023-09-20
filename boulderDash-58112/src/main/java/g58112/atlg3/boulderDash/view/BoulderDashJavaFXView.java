package g58112.atlg3.boulderDash.view;



import g58112.atlg3.boulderDash.model.*;
import g58112.atlg3.boulderDash.util.Observer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class BoulderDashJavaFXView extends BorderPane implements Observer {
	private Stage stage;
	private BoulderDashJavaFXGridPane gridPane;
	private BoulderDashJavaFXInfoBox infoBox;
	private BoulderDashJavaFXActionBox actionBox;
	
	public BoulderDashJavaFXView(Stage stage, BoulderDash boulderDash) {
		this.stage = stage;
		
		initInfoBox(this, boulderDash);
		initGrid(this, boulderDash);
		initActionBox(this, boulderDash);
	}
	
	private void initGrid(BoulderDashJavaFXView view, BoulderDash boulderDash) {
		this.gridPane = new BoulderDashJavaFXGridPane(boulderDash);
		setCenter(this.gridPane);
		
		this.gridPane.register(this);
	}
	
	private void initInfoBox(BoulderDashJavaFXView view, BoulderDash boulderDash) {
		this.infoBox = new BoulderDashJavaFXInfoBox(boulderDash);
		setTop(this.infoBox);
		
		this.infoBox.register(this);
    }
	
	private void initActionBox(BoulderDashJavaFXView view, BoulderDash boulderDash) {
		this.actionBox = new BoulderDashJavaFXActionBox(this.stage, boulderDash);
		setBottom(this.actionBox);
    }
	
	public void setMenuScene(Scene menuScene) {
		this.actionBox.setMenuScene(menuScene);
	}

	@Override
	public void update() {
 		this.stage.sizeToScene();
	}
}
