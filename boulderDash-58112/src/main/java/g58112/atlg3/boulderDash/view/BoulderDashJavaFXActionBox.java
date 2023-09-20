package g58112.atlg3.boulderDash.view;

import g58112.atlg3.boulderDash.model.BoulderDash;
import g58112.atlg3.boulderDash.model.GameState;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class BoulderDashJavaFXActionBox extends VBox {
	private Stage stage;
	private Scene menuScene;
	
	private static final int PADDING = 8;
	private static final int HOME_ZONE_PADDING = 100;
	private static final int RESTART_ZONE_PADDING = 100;
	private static final int ACTIONS_ZONE_PADDING = 50;

	private static final String HOME_IMG_PATH = "file:src/main/resources/texture/home.png";
	private static final String RESTART_IMG_PATH = "file:src/main/resources/texture/restart.png";

	private static final String UNDO_IMG_PATH = "file:src/main/resources/texture/undo.png";
	private static final String REDO_IMG_PATH = "file:src/main/resources/texture/redo.png";
	
	private BoulderDash boulderDash;

	private HBox buttonsZone;
	private HBox homeZone;
	private HBox restartZone;
	private HBox actionsZone;
	
	private Button homeButton;
	private Button restartButton;
	private Button undoButton;
	private Button redoButton;
	
	public BoulderDashJavaFXActionBox(Stage stage, BoulderDash boulderDash) {
		this.stage = stage;
		this.boulderDash = boulderDash;
        
        init();
        display();
	}
	
	private void init() {
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(0, 0, 0, BoulderDashJavaFXActionBox.PADDING));
		this.buttonsZone = initButtonsZone();
	}
	
	private HBox initButtonsZone() {
		HBox buttonsZone = new HBox(BoulderDashJavaFXActionBox.PADDING);
		buttonsZone.setAlignment(Pos.CENTER);
		buttonsZone.setPadding(new Insets(BoulderDashJavaFXActionBox.PADDING, 0, BoulderDashJavaFXActionBox.PADDING, 0));
		
		this.homeZone = initHomeZone();
		this.restartZone = initRestartZone();
		this.actionsZone = initActionsZone();
		
		buttonsZone.getChildren().add(this.homeZone);
		buttonsZone.getChildren().add(this.actionsZone);
		buttonsZone.getChildren().add(this.restartZone);
		
		return buttonsZone;
	}
	
	private HBox initHomeZone() {
		HBox homeZone = new HBox(BoulderDashJavaFXActionBox.PADDING);
		homeZone.setAlignment(Pos.CENTER_LEFT);
		homeZone.setPadding(new Insets(0, BoulderDashJavaFXActionBox.HOME_ZONE_PADDING, 0, 0));
		
		this.homeButton = initHomeButton();
		homeZone.getChildren().add(this.homeButton);
		
		return homeZone;
	}
	
	private HBox initRestartZone() {
		HBox restartZone = new HBox(BoulderDashJavaFXActionBox.PADDING);
		restartZone.setAlignment(Pos.CENTER_RIGHT);
		restartZone.setPadding(new Insets(0, 0, 0, BoulderDashJavaFXActionBox.RESTART_ZONE_PADDING));
		
		this.restartButton = initRestartButton();
		restartZone.getChildren().add(this.restartButton);
		
		return restartZone;
	}
	
	private HBox initActionsZone() {
		HBox actionsZone = new HBox(BoulderDashJavaFXActionBox.PADDING);
		actionsZone.setAlignment(Pos.CENTER);
		actionsZone.setPadding(new Insets(0, BoulderDashJavaFXActionBox.ACTIONS_ZONE_PADDING, 0, BoulderDashJavaFXActionBox.ACTIONS_ZONE_PADDING));
		
		this.undoButton = initUndoButton();
		this.redoButton = initRedoButton();
		
		actionsZone.getChildren().add(this.undoButton);
		actionsZone.getChildren().add(this.redoButton);
		
		return actionsZone;
	}
	
	private Button initHomeButton() {
		Button homeButton = new Button();
		
		Image homeImg = new Image(BoulderDashJavaFXActionBox.HOME_IMG_PATH, 48, 48, true, true);
		ImageView homeImgView = new ImageView(homeImg);
		homeImgView.setPreserveRatio(true);
		
		homeButton.setGraphic(homeImgView);
		homeButton.setStyle(
            "-fx-background-radius: 5em; " +
            "-fx-min-width: 44px; " +
            "-fx-min-height: 44px; " +
            "-fx-max-width: 44px; " +
            "-fx-max-height: 44px; " +
            "-fx-cursor: hand;"
        );
		
		homeButton.setOnMouseClicked(
			e -> {
				this.boulderDash.gameOver();
				this.stage.setScene(this.menuScene);
				this.stage.show();
			}
		);
		
		return homeButton;
	}
	
	private Button initRestartButton() {
		Button restartButton = new Button();
		
		Image restartImg = new Image(BoulderDashJavaFXActionBox.RESTART_IMG_PATH, 48, 48, true, true);
		ImageView restartImgView = new ImageView(restartImg);
		restartImgView.setPreserveRatio(true);
		
		restartButton.setGraphic(restartImgView);
		restartButton.setStyle(
            "-fx-background-radius: 5em; " +
            "-fx-min-width: 44px; " +
            "-fx-min-height: 44px; " +
            "-fx-max-width: 44px; " +
            "-fx-max-height: 44px; " +
            "-fx-cursor: hand;"
        );

		restartButton.setOnMouseClicked(
			e -> {
				if (this.boulderDash.getState() == GameState.PLAY || 
					this.boulderDash.getState() == GameState.GAME_OVER || 
					(this.boulderDash.getState() == GameState.WIN && this.boulderDash.getBoard().getLives() == 0)) 
						this.boulderDash.reset();
			}
		);
		
		return restartButton;
	}
	
	private Button initUndoButton() {
		Button undoButton = new Button();
		
		Image undoImg = new Image(BoulderDashJavaFXActionBox.UNDO_IMG_PATH, 48, 48, true, true);
		ImageView undoImgView = new ImageView(undoImg);
		undoImgView.setPreserveRatio(true);
		
		undoButton.setGraphic(undoImgView);
		undoButton.setStyle(
            "-fx-background-radius: 5em; " +
            "-fx-min-width: 44px; " +
            "-fx-min-height: 44px; " +
            "-fx-max-width: 44px; " +
            "-fx-max-height: 44px; " +
            "-fx-cursor: hand;"
        );

		undoButton.setOnMouseClicked(
			e -> {
				boulderDash.undo();
			}
		);
		
		return undoButton;
	}
	
	private Button initRedoButton() {
		Button redoButton = new Button();
		
		Image redoImg = new Image(BoulderDashJavaFXActionBox.REDO_IMG_PATH, 48, 48, true, true);
		ImageView redoImgView = new ImageView(redoImg);
		redoImgView.setPreserveRatio(true);
		
		redoButton.setGraphic(redoImgView);
		redoButton.setStyle(
            "-fx-background-radius: 5em; " +
            "-fx-min-width: 44px; " +
            "-fx-min-height: 44px; " +
            "-fx-max-width: 44px; " +
            "-fx-max-height: 44px; " +
            "-fx-cursor: hand;"
        );

		redoButton.setOnMouseClicked(
			e -> {
				boulderDash.redo();
			}
		);
		
		return redoButton;
	}
	
	private void display() {
		getChildren().clear();
		
		getChildren().add(this.buttonsZone);
	}
	
	public void setMenuScene(Scene menuScene) {
		this.menuScene = menuScene;
	}
}
