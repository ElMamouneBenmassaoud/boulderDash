package g58112.atlg3.boulderDash.model;

import java.io.FileNotFoundException;

public class Board {
	private static final int INITIAL_NB_LIVES = 3;
	
    private Level currentLevel;
    private Tile[][] tiles;
    private Position doorPosition;
    private Position rockFordPosition;
    private int nbRemainingDiamonds;
    private int score;
    private int lives;

    private int getTakenDiamonds;

    public Board() {
    	reset();
    }
    
    public Board(Board board) {
    	this.currentLevel = board.currentLevel;
    	this.tiles = board.copyTiles();
    	this.doorPosition = new Position(board.doorPosition.getLine(), board.doorPosition.getColumn());
    	this.rockFordPosition = new Position(board.rockFordPosition.getLine(), board.rockFordPosition.getColumn());
    	this.nbRemainingDiamonds = board.nbRemainingDiamonds;
    	this.score = board.score;
    	this.lives = board.lives;
        this.getTakenDiamonds = board.getTakenDiamonds;
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }
    
    public Tile[][] copyTiles() {
    	Tile[][] copy = new Tile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            copy[i] = tiles[i].clone();
        }

        return copy;
    }

    /**
     * Load the level
     * @param niveau number of the level
     * @throws FileNotFoundException 
     * @throws Throwable
     */
    public void loadLevel(int niveau) throws FileNotFoundException  {
        this.currentLevel = new Level(niveau);
        this.tiles = this.currentLevel.getTiles();
        this.doorPosition = this.currentLevel.getDoorPosition();
        this.rockFordPosition = this.currentLevel.getRockFordPosition();
        this.nbRemainingDiamonds = this.currentLevel.getNbTotalDiamonds();
        this.getTakenDiamonds = this.currentLevel.getGetTakenDiamond();
    }



    /**
     * move the rock or the diamond
     * @param origin start position
     * @param cible next position
     * @param direction sense of direction
     */
    public void moveTile(Position origin, Position cible, Direction direction) {
        Tile tileInCible = getTile(cible);
        if (tileInCible instanceof Diamond) {
        	this.nbRemainingDiamonds--;
        	this.score += this.currentLevel.getScorePerDiamond();
        }
        if (tileInCible instanceof Rock) {
            setTile(cible.nextPosition(direction), new Rock(tileInCible.getStartPosition()));
        }
        setTile(cible, getTile(origin));
        setTile(origin, new Empty(origin));

        if (origin.equals(this.rockFordPosition)) this.rockFordPosition = cible;
    }
    
    public void die() {
    	this.lives--;
    }
    
    public void gameOver() {
    	this.lives = 0;
    }
    
    public void reset() {
    	this.score = 0;
    	this.lives = Board.INITIAL_NB_LIVES;
    }
    
    public int getLives() {
    	return this.lives;
    }

    /**
     * get the type of tile on a position
     * @param position the position of the tile
     * @return the tile
     */
    public Tile getTile(Position position) {
        return this.tiles[position.getLine()][position.getColumn()];
    }

    /**
     * set a tile on a position
     * @param position the position of the new tile
     * @param tile the new tile
     */
    public void setTile(Position position, Tile tile) {
        this.tiles[position.getLine()][position.getColumn()] = tile;
    }
    
    public int getTakenDiamonds() {
        return this.currentLevel.getNbTotalDiamonds() - this.nbRemainingDiamonds;
    }

    public void setGetTakenDiamonds(int getTakenDiamonds) {
        this.getTakenDiamonds = getTakenDiamonds;
    }

    public int getNbRemainingDiamonds() {
        return nbRemainingDiamonds;
    }

    /**
     * check if a mobile tile fall on the Rockford
     * @return true if the RockFord dies otherwise no
     */
    public boolean checkMobileTileFallOnPlayer() {
        if (rockFordPosition.getLine() > 0) {
            Position posAboveTile = new Position(rockFordPosition.getLine() - 1, rockFordPosition.getColumn());
            Tile aboveTile = getTile(posAboveTile);
            if (aboveTile.isMobile()) {
                if (!aboveTile.getStartPosition().equals(posAboveTile)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Check if the player reach the door
     * @return true if the player reach the door, otherwise no
     */
    public boolean checkPlayerReachDoor() {
        return (rockFordPosition.equals(doorPosition) && hasTakenMinimumDiamonds()) ;
    }
    
    public boolean hasTakenMinimumDiamonds() {
    	return this.getTakenDiamonds >= this.currentLevel.getNbRequiredDiamonds();
    }

    public void hasTakenJocker() {
        setGetTakenDiamonds(this.getCurrentLevel().getNbRequiredDiamonds());
    }

    /**
     * get the position of the RockFord
     * @return the position of the RockFord
     */
    public Position getRockFordPosition() {
        return this.rockFordPosition;
    }

    /**
     * get the current level
     * @return the current level
     */
    public Level getCurrentLevel() {
        return this.currentLevel;
    }
    
    public int getScore() {
    	return this.score;
    }
}


