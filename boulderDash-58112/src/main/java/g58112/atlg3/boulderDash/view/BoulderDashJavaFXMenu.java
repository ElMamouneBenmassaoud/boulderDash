package g58112.atlg3.boulderDash.view;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class BoulderDashJavaFXMenu extends AnchorPane {
	private static final int PADDING = 8;
	private static final int FONT_SIZE = 60;

	private static final String MENU_FONT_PATH = "./src/main/resources/fonts/DebugFreeTrial-MVdYB.otf";
	private static final String BACKGROUND_IMG_PATH = "file:src/main/resources/texture/select.png";
	
	private HBox selectZone;
	private Label levelLabel;
	
	private int selectedLevel;
	
	public BoulderDashJavaFXMenu() {
		this.selectedLevel = 1;
		
		init();
		display();
	}
	
	private void init() {
		Image backgroundImg = new Image(BoulderDashJavaFXMenu.BACKGROUND_IMG_PATH, 984, 492, true, true);
		setMinSize(backgroundImg.getWidth(), backgroundImg.getHeight());
		setBackground(new Background(new BackgroundImage(backgroundImg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
		
		this.selectZone = initSelectZone();
	}
	
	private void display() {
		getChildren().clear();
		
		getChildren().add(this.selectZone);

		this.levelLabel.setText(new Integer(this.selectedLevel).toString());
	}
	
	private HBox initSelectZone() {
		HBox selectZone = new HBox(BoulderDashJavaFXMenu.PADDING);
		selectZone.setAlignment(Pos.CENTER_LEFT);
		
		Label selectLevelLabel = initSelectLevelLabel();
		selectZone.getChildren().add(selectLevelLabel);
		
		this.levelLabel = initLevelLabel();
		selectZone.getChildren().add(this.levelLabel);
		
		AnchorPane.setTopAnchor(selectZone, 320.0);
		AnchorPane.setLeftAnchor(selectZone, 520.0);
		
		return selectZone;
	}
	
	private Label initSelectLevelLabel() {
		Label selectLevelLabel = new Label();
		try {
			selectLevelLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXMenu.MENU_FONT_PATH), BoulderDashJavaFXMenu.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		selectLevelLabel.setTextFill(Color.WHITE);

		selectLevelLabel.setText("SELECT LEVEL");
		
		return selectLevelLabel;
	}
	
	private Label initLevelLabel() {
		Label levelLabel = new Label();
		try {
			levelLabel.setFont(Font.loadFont(new FileInputStream(BoulderDashJavaFXMenu.MENU_FONT_PATH), BoulderDashJavaFXMenu.FONT_SIZE));
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		levelLabel.setTextFill(Color.YELLOW);
		
		return levelLabel;
	}
	
	public void nextLevel() {
		if (levelExists(this.selectedLevel + 1)) {
			this.selectedLevel++;
			display();
		}
	}
	
	public void previousLevel() {
		if (this.selectedLevel > 1) {
			this.selectedLevel--;
			display();
		}
	}
	
	private boolean levelExists(int niveau) {
		try {
			InputStream in = new FileInputStream("./src/main/resources/levels/level" + niveau + ".txt");
			try {
				in.close();
			} 
			catch (IOException e) {
				return false;
			}
			
			return true;
		} 
		catch (FileNotFoundException e) {
			return false;
		}
	}
	
	public int getSelectedLevel() {
		return this.selectedLevel;
	}
}
