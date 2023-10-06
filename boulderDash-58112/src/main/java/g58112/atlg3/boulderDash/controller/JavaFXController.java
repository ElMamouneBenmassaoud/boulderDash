package g58112.atlg3.boulderDash.controller;


import g58112.atlg3.boulderDash.view.BoulderDashJavaFXInfoBox;
import g58112.atlg3.boulderDash.view.BoulderDashJavaFXMenu;
import g58112.atlg3.boulderDash.view.BoulderDashJavaFXView;

import java.io.FileNotFoundException;

import g58112.atlg3.boulderDash.model.BoulderDash;
import g58112.atlg3.boulderDash.model.Direction;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class JavaFXController extends Application {
	private BoulderDash boulderDash;
	
	private Stage stage;
	private VBox menuRoot;
	private VBox viewRoot;
	private Scene menuScene;
	private Scene viewScene;
    private BoulderDashJavaFXView view;
    private BoulderDashJavaFXMenu menu;
    
    private void init(Stage stage) {
    	this.stage = stage;
        this.stage.setResizable(false);
        
        this.boulderDash = new BoulderDash();
        
        this.menu = initMenu();
        this.view = initView();
        
        this.menuRoot = new VBox(this.menu);
        this.viewRoot = new VBox(this.view);
        
        this.menuScene = initMenuScene(this.menu, this.menuRoot);
        this.viewScene = initViewScene(this.viewRoot);
        
        this.view.setMenuScene(this.menuScene);
    }
    
    private BoulderDashJavaFXMenu initMenu() {
    	return new BoulderDashJavaFXMenu();
    }

	private BoulderDashJavaFXInfoBox initInfoBox(){
		Stage stage = new Stage();
		stage.setResizable(false);

		BoulderDashJavaFXInfoBox infoBox = new BoulderDashJavaFXInfoBox(this.boulderDash);
		VBox infoRoot = new VBox(infoBox);

		Scene infoScene = initViewScene(infoRoot);

		stage.setScene(infoScene);

		stage.show();


		return infoBox;
	}
    private BoulderDashJavaFXView initView() {
        return new BoulderDashJavaFXView(stage, this.boulderDash);
    }
    
    private Scene initMenuScene(BoulderDashJavaFXMenu menu, VBox root) {
    	Scene menuScene = new Scene(root);
        
        menuScene.setOnKeyPressed(
        	keyEvent -> {
	            switch (keyEvent.getCode()) {
	                case UP:
	                	menu.nextLevel();
	                    break;
	                case DOWN:
	                	menu.previousLevel();
	                    break;
	                case ENTER:
	                    startLevel(menu.getSelectedLevel());
	                default:
	                	break;
	            }
        	}
        );
        
        return menuScene;
    }
    private Scene initInfoScene(VBox root){
		return new Scene(root);
	}

    private Scene initViewScene(VBox root) {
    	Scene viewScene = new Scene(root);
        
    	viewScene.setOnKeyPressed(
	    	keyEvent -> {
	            switch (keyEvent.getCode()) {
	                case W:
	                	this.boulderDash.move(Direction.HAUT);
	                    break;
	                case S:
	                	this.boulderDash.move(Direction.BAS);
	                    break;
	                case D:
	                	this.boulderDash.move(Direction.DROITE);
	                    break;
	                case A:
	                	this.boulderDash.move(Direction.GAUCHE);
	                    break;
					case P:
	                	this.boulderDash.abandonner();
	                    break;
	                case U:
	                	this.boulderDash.undo();
	                    break;
	                case R:
	                	this.boulderDash.redo();
	                    break;
					case Q:
						this.boulderDash.hasTakenJocker();
						break;
					case N:
						initInfoBox();
						break;
	                default:
	                	break;
	            }
	    	}
	    );
        
        return viewScene;
    }
    
    @Override
    public void start(Stage stage) {
    	init(stage);
    	
        this.stage.setScene(this.menuScene);
        this.stage.show();
    }
    
    private void startLevel(int niveau) {
    	try {
            this.boulderDash.start(niveau);
        }
        catch (FileNotFoundException e) {
        	e.printStackTrace();
        }
    	
        this.stage.setScene(this.viewScene);
        this.stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}