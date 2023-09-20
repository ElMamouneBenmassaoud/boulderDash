package g58112.atlg3.boulderDash.view;

import java.util.ArrayList;
import java.util.List;

import g58112.atlg3.boulderDash.model.BoulderDash;
import g58112.atlg3.boulderDash.model.Diamond;
import g58112.atlg3.boulderDash.model.Door;
import g58112.atlg3.boulderDash.model.Earth;
import g58112.atlg3.boulderDash.model.Empty;
import g58112.atlg3.boulderDash.model.GameState;
import g58112.atlg3.boulderDash.model.Position;
import g58112.atlg3.boulderDash.model.Rock;
import g58112.atlg3.boulderDash.model.RockFord;
import g58112.atlg3.boulderDash.model.Tile;
import g58112.atlg3.boulderDash.model.Wall;
import g58112.atlg3.boulderDash.util.Observable;
import g58112.atlg3.boulderDash.util.Observer;
import g58112.atlg3.boulderDash.util.SpriteAnimation;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class BoulderDashJavaFXGridPane extends GridPane implements Observer, Observable {
	private static final String WALL_IMG_PATH = "file:src/main/resources/texture/Brick.png";
	private static final String ROCKFORD_IMG_PATH = "file:src/main/resources/texture/rockford.png";
	private static final String EARTH_IMG_PATH = "file:src/main/resources/texture/earth.png";
	private static final String DOOR_IMG_PATH = "file:src/main/resources/texture/Door.png";
	private static final String EMPTY_IMG_PATH = "file:src/main/resources/texture/empty.png";
	private static final String DIAMOND_IMG_PATH = "file:src/main/resources/texture/diamond.png";
	private static final String ROCK_IMG_PATH = "file:src/main/resources/texture/rock.png";
	private static final String EXPLOSION_IMG_PATH = "file:src/main/resources/texture/explosion.png";
	
	private static final int DISPLAYED_LINES = 20;
	private static final int DISPLAYED_COLUMNS = 20;
	private static final int LINE_OFFSET_FROM_PLAYER_POSITION = 3;
	private static final int COLUMN_OFFSET_FROM_PLAYER_POSITION = 1;
	
	private List<Observer> observers = new ArrayList<>();
	
	private BoulderDash boulderDash;
	
	private int startLine;
	private int endLine;
	private int startColumn;
	private int endColumn;
	
	private Animation doorAnimation;
	
	public BoulderDashJavaFXGridPane(BoulderDash boulderDash) {
		this.boulderDash = boulderDash;
        this.boulderDash.register(this);
        
		this.startLine = 0;
		this.endLine = this.startLine;
		this.startColumn = 0;
		this.endColumn = this.startColumn;
		
		init();
		
		if (this.boulderDash.getState() == GameState.PLAY) display();
	}
	
	private void init() {
		setGridLinesVisible(false);
		setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
	}
	
	private void display() {
		this.getChildren().clear();
		
		displayTiles();
		
		if (this.boulderDash.getState() == GameState.WIN || this.boulderDash.getState() == GameState.LOSE || this.boulderDash.getState() == GameState.GAME_OVER) {
			reset();
		}
		
    	notifyObservers();
	}
	
	private void reset() {
		this.startLine = 0;
		this.endLine = this.startLine;
		this.startColumn = 0;
		this.endColumn = this.startColumn;
		
		this.doorAnimation = null;
	}
	
	private void initDoorAnimation(ImageView imgView) {
		Animation doorAnimation = new SpriteAnimation(imgView, Duration.millis(1000), 4, 4, 0, 0, 32, 32);
		doorAnimation.setOnFinished(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent event) {
        		doorAnimation.stop();
        	}
		});
        doorAnimation.play();
        
        this.doorAnimation = doorAnimation;
	}
	
	private void displayTiles() {
		Image wallImg = new Image(BoulderDashJavaFXGridPane.WALL_IMG_PATH, 32, 32, true, true);
		Image rockFordImg = new Image(BoulderDashJavaFXGridPane.ROCKFORD_IMG_PATH, 32, 32, true, true);
		Image earthImg = new Image(BoulderDashJavaFXGridPane.EARTH_IMG_PATH, 32, 32, true, true);
		Image doorImg = new Image(BoulderDashJavaFXGridPane.DOOR_IMG_PATH, 128, 32, true, true);
		Image emptyImg = new Image(BoulderDashJavaFXGridPane.EMPTY_IMG_PATH, 32, 32, true, true);
		Image diamondImg = new Image(BoulderDashJavaFXGridPane.DIAMOND_IMG_PATH, 32, 32, true, true);
		Image rockImg = new Image(BoulderDashJavaFXGridPane.ROCK_IMG_PATH, 32, 32, true, true);
		Image explosionImg = new Image(BoulderDashJavaFXGridPane.EXPLOSION_IMG_PATH, 374, 32, true, true);
		
		setBoundaries();
		
		for (int i = this.startLine; i <= this.endLine; i++) {
			for (int j = this.startColumn; j <= this.endColumn; j++) {
				Position posTile = new Position(i, j);
				Tile tile = this.boulderDash.getBoard().getTile(posTile);
				ImageView imgView = new ImageView();
				if (tile instanceof Wall) {
					imgView.setImage(wallImg);
				}
				if (tile instanceof RockFord) {
					if (this.boulderDash.getState() == GameState.LOSE || this.boulderDash.getState() == GameState.GAME_OVER) {
					    imgView.setImage(explosionImg);
						imgView.setViewport(new Rectangle2D(0, 0, 24, 24));

				        Animation animation = new SpriteAnimation(imgView, Duration.millis(1000), 11, 11, 0, 0, 32, 32);
				        animation.setCycleCount(Animation.INDEFINITE);
				        animation.play();
					}
					else {
						imgView.setImage(rockFordImg);
					}
				}
				if (tile instanceof Earth) {
					imgView.setImage(earthImg);
				}
				if (tile instanceof Door) {
					if (this.boulderDash.getBoard().hasTakenMinimumDiamonds()) {
						imgView.setImage(doorImg);
						if (this.doorAnimation == null)  {
							imgView.setViewport(new Rectangle2D(0, 0, 32, 32));
							
							initDoorAnimation(imgView);
						}
						else {
							imgView.setViewport(new Rectangle2D(96, 0, 32, 32));
						}
					} 
					else {
						imgView.setImage(doorImg);
						imgView.setViewport(new Rectangle2D(0, 0, 32, 32));
					}
				}
				if (tile instanceof Empty) {
					imgView.setImage(emptyImg);
				}
				if (tile instanceof Diamond) {
					imgView.setImage(diamondImg);
				}
				if (tile instanceof Rock) {
					imgView.setImage(rockImg);
				}

				this.add(imgView, j - startColumn, i - startLine);
			}
		}
	}
	
	private void setBoundaries() {
		if (this.boulderDash.getBoard().getRockFordPosition().getLine() >= this.endLine - BoulderDashJavaFXGridPane.LINE_OFFSET_FROM_PLAYER_POSITION + 1) {
			this.startLine = Math.max(this.boulderDash.getBoard().getRockFordPosition().getLine() - BoulderDashJavaFXGridPane.LINE_OFFSET_FROM_PLAYER_POSITION, 0);
			this.endLine = Math.min(this.startLine + BoulderDashJavaFXGridPane.DISPLAYED_LINES - 1, this.boulderDash.getBoard().getCurrentLevel().getHeight() - 1);
		}
		else if (this.boulderDash.getBoard().getRockFordPosition().getLine() <= this.startLine + BoulderDashJavaFXGridPane.LINE_OFFSET_FROM_PLAYER_POSITION - 1) {
			this.startLine = Math.max(this.boulderDash.getBoard().getRockFordPosition().getLine() - BoulderDashJavaFXGridPane.LINE_OFFSET_FROM_PLAYER_POSITION, 0);
			this.endLine = Math.min(this.startLine + BoulderDashJavaFXGridPane.DISPLAYED_LINES - 1, this.boulderDash.getBoard().getCurrentLevel().getHeight() - 1);
		}
		
		if (this.boulderDash.getBoard().getRockFordPosition().getColumn() >= this.endColumn - BoulderDashJavaFXGridPane.COLUMN_OFFSET_FROM_PLAYER_POSITION + 1) {
			this.startColumn = Math.max(this.boulderDash.getBoard().getRockFordPosition().getColumn() - BoulderDashJavaFXGridPane.COLUMN_OFFSET_FROM_PLAYER_POSITION, 0);
			this.endColumn = Math.min(this.startColumn + BoulderDashJavaFXGridPane.DISPLAYED_COLUMNS - 1, this.boulderDash.getBoard().getCurrentLevel().getLength() - 1);
		}
		else if (this.boulderDash.getBoard().getRockFordPosition().getColumn() <= this.startColumn + BoulderDashJavaFXGridPane.COLUMN_OFFSET_FROM_PLAYER_POSITION - 1) {
			this.startColumn = Math.max(this.boulderDash.getBoard().getRockFordPosition().getColumn() - BoulderDashJavaFXGridPane.COLUMN_OFFSET_FROM_PLAYER_POSITION, 0);
			this.endColumn = Math.min(this.startColumn + BoulderDashJavaFXGridPane.DISPLAYED_COLUMNS - 1, this.boulderDash.getBoard().getCurrentLevel().getLength() - 1);
		}
		
		if (this.endLine - this.startLine < BoulderDashJavaFXGridPane.DISPLAYED_LINES - 1) {
			this.startLine = Math.max(0, this.endLine - BoulderDashJavaFXGridPane.DISPLAYED_LINES + 1);
		}
		
		if (this.endColumn - this.startColumn < BoulderDashJavaFXGridPane.DISPLAYED_COLUMNS - 1) {
			this.startColumn = Math.max(0, this.endColumn - BoulderDashJavaFXGridPane.DISPLAYED_COLUMNS + 1);
		}
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
	
    @Override
    public void update() {
    	display();
    }
}
