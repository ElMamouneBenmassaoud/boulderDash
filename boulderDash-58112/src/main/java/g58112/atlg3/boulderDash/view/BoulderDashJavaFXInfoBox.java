package g58112.atlg3.boulderDash.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import g58112.atlg3.boulderDash.model.BoulderDash;
import g58112.atlg3.boulderDash.model.GameState;
import g58112.atlg3.boulderDash.util.Observable;
import g58112.atlg3.boulderDash.util.Observer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BoulderDashJavaFXInfoBox extends VBox implements Observer, Observable {
	private static final int PADDING = 8;
	private static final int RIGHT_CENTER_ZONE_PADDING = 40;
	private static final int LEFT_CENTER_ZONE_PADDING = 40;
	private static final int RIGHT_ZONE_PADDING = 80;
	private static final int FONT_SIZE = 60;
	
	private static final String INFO_BOX_FONT_PATH = "./src/main/resources/fonts/DebugFreeTrial-MVdYB.otf";
	private static final String DIAMOND_IMG_PATH = "file:src/main/resources/texture/diamond.png";
	private static final String HEART_IMG_PATH = "file:src/main/resources/texture/heart.png";
	private static final String SKULL_IMG_PATH = "file:src/main/resources/texture/skull.gif";
	
	private List<Observer> observers = new ArrayList<>();
	
	private BoulderDash boulderDash;
	
	private HBox topZone;
	private HBox bottomZone;
	private HBox leftZone;
	private HBox leftCenterZone;
	private HBox rightCenterZone;
	private HBox rightZone;
	private HBox uniqueZone;
	private HBox winZone;
	private HBox loseZone;
	private HBox livesZone;
	
	private Label nbRequiredDiamondsLabel;
	private Label scorePerDiamondLabel;
	private Label takenDiamondsLabel;
	private Label remainingTimeLabel;
	private Label scoreLabel;
	private ImageView[] heartImgViews;
	
	public BoulderDashJavaFXInfoBox(BoulderDash boulderDash) {
		this.boulderDash = boulderDash;
        this.boulderDash.register(this);
        
        init();
		
        if (this.boulderDash.getState() == GameState.PLAY) display();
	}
	
	private void init() {
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		setAlignment(Pos.CENTER);
		setPadding(new Insets(0, 0, 0, BoulderDashJavaFXInfoBox.PADDING));
		initBottomZone();
		this.topZone = initTopZone();
		this.uniqueZone = initUniqueZone();
		this.loseZone = initLoseZone();
		this.winZone = initWinZone();
	}
	
	private HBox initUniqueZone() {
		HBox uniqueZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		uniqueZone.setAlignment(Pos.CENTER);
		
		Label gameOverLabel = initGameOverLabel();
		uniqueZone.getChildren().add(gameOverLabel);
		
		return uniqueZone;
	}
	
	private HBox initLoseZone() {
		HBox loseZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		loseZone.setAlignment(Pos.CENTER);
		
		loseZone.getChildren().add(initSkullImageView());
		
		loseZone.getChildren().add(initYouLoseLabel());
		
		loseZone.getChildren().add(initSkullImageView());
		
		return loseZone;
	}
	
	private HBox initWinZone() {
		HBox winZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		winZone.setAlignment(Pos.CENTER);
		
		winZone.getChildren().add(initYouWinLabel());
		
		return winZone;
	}
	
	private HBox initTopZone() {
		HBox topZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		topZone.setAlignment(Pos.CENTER);
		
		this.leftZone = initLeftZone();
		this.leftCenterZone = initLeftCenterZone();
		this.rightCenterZone = initRightCenterZone();
		this.rightZone = initRightZone();
		
		topZone.getChildren().add(this.leftZone);
		topZone.getChildren().add(this.leftCenterZone);
		topZone.getChildren().add(this.rightCenterZone);
		topZone.getChildren().add(this.rightZone);
		
		return topZone;
	}
	
	private HBox initBottomZone() {
		if (this.heartImgViews == null || this.heartImgViews.length != this.boulderDash.getBoard().getLives()) {
			if (this.bottomZone == null) {
				this.bottomZone = new HBox();
				this.bottomZone.setPadding(new Insets(BoulderDashJavaFXInfoBox.PADDING, 0, BoulderDashJavaFXInfoBox.PADDING, 0));
				this.bottomZone.setAlignment(Pos.CENTER);
			}
			
			this.bottomZone.getChildren().clear();
			this.livesZone = initLivesZone();
			this.bottomZone.getChildren().add(this.livesZone);
		}
		
		return this.bottomZone;
	}
	
	private HBox initLivesZone() {
		this.livesZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		this.livesZone.setAlignment(Pos.CENTER);
		
		this.heartImgViews = new ImageView[this.boulderDash.getBoard().getLives()];
		
		for (int i=0; i<this.heartImgViews.length; i++) {
			this.heartImgViews[i] = initHeartImageView();
			this.livesZone.getChildren().add(heartImgViews[i]);
		}
		
		return this.livesZone;
	}
	
	private HBox initLeftZone() {
		HBox leftZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		leftZone.setAlignment(Pos.CENTER_LEFT);
		
		this.nbRequiredDiamondsLabel = initNbRequiredDiamondsLabel();
		leftZone.getChildren().add(this.nbRequiredDiamondsLabel);
	
		leftZone.getChildren().add(initDiamondImageView());
		
		this.scorePerDiamondLabel = initScorePerDiamondLabel();
		leftZone.getChildren().add(this.scorePerDiamondLabel);
		
		return leftZone;
	}
	
	private HBox initLeftCenterZone() {
		HBox leftCenterZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		leftCenterZone.setAlignment(Pos.CENTER_LEFT);
		leftCenterZone.setPadding(new Insets(0, 0, 0, BoulderDashJavaFXInfoBox.LEFT_CENTER_ZONE_PADDING));
		
		this.takenDiamondsLabel = initTakenDiamondsLabel();
		leftCenterZone.getChildren().add(this.takenDiamondsLabel);
		
		return leftCenterZone;
	}
	
	private HBox initRightCenterZone() {
		HBox rightCenterZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		rightCenterZone.setAlignment(Pos.CENTER_LEFT);
		rightCenterZone.setPadding(new Insets(0, 0, 0, BoulderDashJavaFXInfoBox.RIGHT_CENTER_ZONE_PADDING));
		
		this.remainingTimeLabel = initRemainingTimeLabel();
		rightCenterZone.getChildren().add(this.remainingTimeLabel);
		
		return rightCenterZone;
	}
	
	private HBox initRightZone() {
		getChildren().clear();
		
		HBox rightZone = new HBox(BoulderDashJavaFXInfoBox.PADDING);
		rightZone.setAlignment(Pos.CENTER_LEFT);
		rightZone.setPadding(new Insets(0, 0, 0, BoulderDashJavaFXInfoBox.RIGHT_ZONE_PADDING));
		
		this.scoreLabel = initScoreLabel();
		rightZone.getChildren().add(this.scoreLabel);
		
		return rightZone;
	}
	
	private Label initGameOverLabel() {
		Label gameOverLabel = new Label();
		try {
			gameOverLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		gameOverLabel.setTextFill(Color.WHITE);

		gameOverLabel.setText("GAME OVER");
		
		return gameOverLabel;
	}
	
	private Label initYouLoseLabel() {
		Label youLoseLabel = new Label();
		try {
			youLoseLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		youLoseLabel.setTextFill(Color.RED);

		youLoseLabel.setText("YOU LOSE");
		
		return youLoseLabel;
	}
	
	private Label initYouWinLabel() {
		Label youWinLabel = new Label();
		try {
			youWinLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		youWinLabel.setTextFill(Color.GREEN);

		youWinLabel.setText("YOU WIN");
		
		return youWinLabel;
	}
	
	private Label initNbRequiredDiamondsLabel() {
		Label nbRequiredDiamondsLabel = new Label();
		try {
			nbRequiredDiamondsLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		nbRequiredDiamondsLabel.setTextFill(Color.YELLOW);
		
		return nbRequiredDiamondsLabel;
	}
	
	private Label initScorePerDiamondLabel() {
		Label scorePerDiamondLabel = new Label();
		try {
			scorePerDiamondLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scorePerDiamondLabel.setTextFill(Color.WHITE);
		
		return scorePerDiamondLabel;
	}
	
	private Label initTakenDiamondsLabel() {
		Label takenDiamondsLabel = new Label();
		try {
			takenDiamondsLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		takenDiamondsLabel.setTextFill(Color.YELLOW);
		
		return takenDiamondsLabel;
	}
	
	private Label initRemainingTimeLabel() {
		Label remainingTimeLabel = new Label();
		try {
			remainingTimeLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		remainingTimeLabel.setTextFill(Color.WHITE);
		
		return remainingTimeLabel;
	}
	
	private Label initScoreLabel() {
		Label scoreLabel = new Label();
		try {
			scoreLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXInfoBox.INFO_BOX_FONT_PATH), BoulderDashJavaFXInfoBox.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scoreLabel.setTextFill(Color.WHITE);
		
		return scoreLabel;
	}
	
	private ImageView initDiamondImageView() {
		Image diamondImg = new Image(BoulderDashJavaFXInfoBox.DIAMOND_IMG_PATH, 32, 32, true, true);
		return new ImageView(diamondImg);
	}
	
	private ImageView initHeartImageView() {
		Image heartImg = new Image(BoulderDashJavaFXInfoBox.HEART_IMG_PATH, 32, 32, true, true);
		return new ImageView(heartImg);
	}
	
	private ImageView initSkullImageView() {
		Image skullImg = new Image(BoulderDashJavaFXInfoBox.SKULL_IMG_PATH, 32, 32, true, true);
		return new ImageView(skullImg);
	}
	
	private void display() {
		getChildren().clear();
		
		if (this.boulderDash.getState() == GameState.GAME_OVER || (this.boulderDash.getState() == GameState.WIN && this.boulderDash.getBoard().getLives() == 0)) {
			displayGameOver();
		}
		else {
			displayInfo();
		}
		
		notifyObservers();
	}
	
	private void displayInfo() {
		getChildren().add(this.topZone);
		getChildren().add(initBottomZone());
		
		this.nbRequiredDiamondsLabel.setText(String.format("%02d", this.boulderDash.getBoard().getCurrentLevel().getNbRequiredDiamonds()));
		this.scorePerDiamondLabel.setText(String.format("%02d", this.boulderDash.getBoard().getCurrentLevel().getScorePerDiamond()));
		this.takenDiamondsLabel.setText(String.format("%02d", this.boulderDash.getBoard().getTakenDiamonds()));
		this.remainingTimeLabel.setText(String.format("%03d", this.boulderDash.getRemainingTime()));
		this.scoreLabel.setText(String.format("%06d", this.boulderDash.getBoard().getScore()));
	}
	
	private void displayGameOver() {
		getChildren().add(this.topZone);
		
		if (this.boulderDash.getState() == GameState.WIN) {
			getChildren().add(this.winZone);
		}
		else {
			getChildren().add(this.loseZone);
		}
		getChildren().add(this.uniqueZone);
	}
	
    @Override
    public void update() {
    	display();
    }
    
    private  void notifyObservers() {
        for (Observer obs: this.observers) {
            obs.update();
        }
    }

	@Override
	public void register(Observer observer) {
		if (!this.observers.contains(observer)) {
        	this.observers.add(observer);
        }
	}

	@Override
	public void unregister(Observer observer) {
		this.observers.remove(observer);
	}
}
