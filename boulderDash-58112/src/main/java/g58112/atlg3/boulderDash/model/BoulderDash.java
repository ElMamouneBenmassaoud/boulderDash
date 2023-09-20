package g58112.atlg3.boulderDash.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import g58112.atlg3.boulderDash.util.Observable;
import g58112.atlg3.boulderDash.util.Observer;
import javafx.application.Platform;

public class BoulderDash implements BoulderDashModel, Observable {
	private static final int START_LEVEL_DELAY = 2;
	private CommandManager commandManager;
	
    private Board board;
    private Timer countDownTimer;
    private CountDownTimerTask countDownTimerTask;
    private List<Observer> observers = new ArrayList<>();
    private GameState state;

    public BoulderDash() {
        this.board = new Board();
        this.commandManager = new CommandManager();
    }
    
    public BoulderDash(BoulderDash boulderDash) {
    	this.board = new Board(boulderDash.board);
    	this.countDownTimer = boulderDash.countDownTimer;
    	this.countDownTimerTask = new CountDownTimerTask(this, boulderDash.countDownTimerTask);
    	this.observers = boulderDash.observers;
    	this.state = boulderDash.state;
    	this.commandManager = boulderDash.commandManager;
    }
    
    public void revertTo(BoulderDash boulderDash) {
    	this.board = boulderDash.board;
    	this.countDownTimer = boulderDash.countDownTimer;
    	this.observers = boulderDash.observers;
    	this.state = boulderDash.state;
    	this.commandManager = boulderDash.commandManager;
    	
    	notifyObservers();
    }

    public Board getBoard() {
        return this.board;
    }
    
    public void start(int niveau) throws FileNotFoundException {
    	this.board.reset();
    	demarrer(niveau);
    }
   
    public void demarrer(int niveau) throws FileNotFoundException {
        this.state = GameState.PLAY;
        this.board.loadLevel(niveau);
        try {
	        Platform.runLater(new Runnable() {
				@Override
				public void run() {
					notifyObservers();
				}
	        });
        }
        catch (IllegalStateException e) {
        	notifyObservers();
        }
        
        countDown();
    }

    public boolean isFinDuNiveau() {
        return isNiveauGagne() || isNiveauPerdu();
    }

    public boolean isNiveauGagne() {
        return this.board.checkPlayerReachDoor();
    }

    public CountDownTimerTask getCountDownTimerTask() {
        return this.countDownTimerTask;
    }

    public boolean isNiveauPerdu() {
        return this.board.checkMobileTileFallOnPlayer() || this.countDownTimerTask.getRemainingTime() == 0;
    }
    
    public void move(Direction direction) {
        this.commandManager.add(new MovePlayer(this, direction));
    }

    public void hasTakenJocker() {
        this.commandManager.add(new JockerCommand(this));
    }

    
    public void movePlayer(Direction direction) {
        Position rockFordPosition = this.board.getRockFordPosition();

        if (!isFinDuNiveau() && !rockFordPosition.isNextToEdge(this.board, direction)) {
            Position cible = rockFordPosition.nextPosition(direction);

            if (!isObstacle(cible, direction)) {
                this.board.moveTile(this.board.getRockFordPosition(), cible, direction);

                moveMobileTiles(direction);

                if (!isFinDuNiveau()) {
                	setStartPositions();
                }
                else {
                	if (isNiveauGagne()) {
                		win();
                	}
                	else if (isNiveauPerdu()) {
                		die();
                	}
                }
            }
            
            updateState();
            
            notifyObservers();
        }
    }
    
    public void win() {
    	this.countDownTimer.cancel();
		
		StartLevelTimerTask startLevelTask = new StartLevelTimerTask(this, this.board.getCurrentLevel().getId() + 1);

		new Timer().schedule(startLevelTask, BoulderDash.START_LEVEL_DELAY * 1000);
    }
    
    public void die() {
    	this.countDownTimer.cancel();
		this.board.die();
		
		if (this.board.getLives() > 0) {
			StartLevelTimerTask startLevelTask = new StartLevelTimerTask(this, this.board.getCurrentLevel().getId());
			
			new Timer().schedule(startLevelTask, BoulderDash.START_LEVEL_DELAY * 1000);
		}
    }
    
    public void gameOver() {
    	this.countDownTimer.cancel();
		this.board.gameOver();
        
        updateState();
        
        try {
	        Platform.runLater(new Runnable() {
				@Override
				public void run() {
					notifyObservers();
				}
	        });
        }
        catch (IllegalStateException e) {
        	notifyObservers();
        }
    }
    
    public void reset() {
    	this.countDownTimer.cancel();
        this.board.reset();
        try {
			demarrer(this.board.getCurrentLevel().getId());
		} 
        catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

    public void undo() {
        this.commandManager.undo();
    }

    public void redo() {
    	this.commandManager.redo();
    }

    public void abandonner() {
        gameOver();
    }
    
    public void updateState() {
        if (isNiveauGagne()) {
        	this.state = GameState.WIN;
        }
        else if (board.getLives() == 0) {
    		this.state = GameState.GAME_OVER;
    	}
    	else if (isNiveauPerdu()) {
        	this.state = GameState.LOSE;
        }
        else {
            this.state = GameState.PLAY;
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

    public GameState getState() {
        return this.state;
    }

    /**
     * check if the tile is an obstacle for the player or not
     * @param position the line and the column of the tile
     * @param direction the direction after the tile
     * @return true if the tile is an obstacle, otherwise no
     */
    public boolean isObstacle(Position position, Direction direction) {
        Tile tileInPosition = this.board.getTile(position);
        if (tileInPosition instanceof Wall) return true;
        if (tileInPosition instanceof Door && !this.board.hasTakenMinimumDiamonds()) return true;
        if (tileInPosition instanceof Rock && position.isNextToEdge(this.board, direction)) return true;
        if (tileInPosition instanceof Rock && direction == Direction.HAUT) return true;

        if (!position.isNextToEdge(this.board, direction)) {
	        Position nextPosition = position.nextPosition(direction);
	        Tile tileInNextPosition = this.board.getTile(nextPosition);
	        if (tileInPosition instanceof Rock && !(tileInNextPosition instanceof Empty)) return true;
        }
        return false;
    }

    /**
     * second counter
     */
    private void countDown() {
        long delay = 1000L;
        long period = 1000L;

        this.countDownTimer = new Timer();
        this.countDownTimerTask = new CountDownTimerTask(this);

        countDownTimer.scheduleAtFixedRate(this.countDownTimerTask, delay, period);
    }
    
    public void setStartPositions() {
    	for (int line = 0; line < this.board.getCurrentLevel().getHeight(); line++) {
            for (int column = 0; column < this.board.getCurrentLevel().getLength(); column++) {
                Position position = new Position(line, column);
                if (this.board.getTile(position).isMobile()) {
                    this.board.getTile(position).setStartPosition(position);
                }
            }
        }
    }
    
    public void moveMobileTiles(Direction direction) {
    	boolean mustMoveMobileTiles = true;
        while (mustMoveMobileTiles) {
            mustMoveMobileTiles = false;
            for (int line = 0; line < this.board.getCurrentLevel().getHeight(); line++) {
                for (int column = 0; column < this.board.getCurrentLevel().getLength(); column++) {
                    Position position = new Position(line, column);
                    if (this.board.getTile(position).isMobile()) {
                    	if (moveMobileTile(position, direction)) mustMoveMobileTiles = true;
                    }
                }
            }
        }
    }
    
    private boolean moveMobileTile(Position position, Direction direction) {
    	boolean mustMoveMobileTiles = false;
    	
    	if (!position.isNextToEdge(this.board, Direction.BAS) && this.board.getTile(position.nextPosition(Direction.BAS)) instanceof Empty) {
            this.board.moveTile(position, position.nextPosition(Direction.BAS), direction);
            mustMoveMobileTiles = true;
        }
        if (!position.isNextToEdge(this.board, Direction.BAS) && this.board.getTile(position.nextPosition(Direction.BAS)).isSlippery()) {
            if (!position.isNextToEdge(this.board, Direction.DROITE) && this.board.getTile(position.nextPosition(Direction.DROITE)) instanceof Empty && this.board.getTile(position.nextPosition(Direction.BAS).nextPosition(Direction.DROITE)) instanceof Empty) {
                this.board.moveTile(position, position.nextPosition(Direction.BAS).nextPosition(Direction.DROITE), direction);
                mustMoveMobileTiles = true;
            }
            else if (!position.isNextToEdge(this.board, Direction.GAUCHE) && this.board.getTile(position.nextPosition(Direction.GAUCHE)) instanceof Empty && this.board.getTile(position.nextPosition(Direction.BAS).nextPosition(Direction.GAUCHE)) instanceof Empty) {
                this.board.moveTile(position, position.nextPosition(Direction.BAS).nextPosition(Direction.GAUCHE), direction);
                mustMoveMobileTiles = true;
            }
        }
        
        return mustMoveMobileTiles;
    }

	public int getRemainingTime() {
		return this.countDownTimerTask.getRemainingTime();
	}
	
	public void stopCountDown() {
		die();
		updateState();

		try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    notifyObservers();
                }
            });
        }
        catch (IllegalStateException e) {
            notifyObservers();
        }
	}
	
	public void updateTime() {
		try {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					notifyObservers();
				}
			});
		}
	    catch (IllegalStateException e) {
	    	notifyObservers();
	    }
	}

    private void notifyObservers() {
        for (Observer obs: this.observers) {
            obs.update();
        }
    }

    private static class CountDownTimerTask extends TimerTask {
        private static final int TIME_LIMIT = 150;
        private BoulderDash boulderDash;
        private int remainingTime = CountDownTimerTask.TIME_LIMIT;

        public CountDownTimerTask(BoulderDash boulderDash) {
            super();
            this.boulderDash = boulderDash;
        }
        
        public CountDownTimerTask(BoulderDash boulderDash, CountDownTimerTask timerTask) {
        	this(boulderDash);
        	this.remainingTime = timerTask.getRemainingTime();
        }

        public void run() {
            this.remainingTime--;
            
            this.boulderDash.updateTime();

            if (this.remainingTime == 0) this.boulderDash.stopCountDown();
        }

        public int getRemainingTime() {
            return this.remainingTime;
        }
    }
    
    private static class StartLevelTimerTask extends TimerTask {
    	private BoulderDash boulderDash;
    	private int niveau;
    	
    	public StartLevelTimerTask(BoulderDash boulderDash, int niveau) {
    		super();
    		this.boulderDash = boulderDash;
    		this.niveau = niveau;
    	}
    	
    	public void run() {
    		try {
    			this.boulderDash.demarrer(this.niveau);
    		}
    		catch (IOException e) {
    			this.boulderDash.gameOver();
    		}
        }
    }
}