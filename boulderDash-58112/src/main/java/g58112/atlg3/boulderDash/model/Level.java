package g58112.atlg3.boulderDash.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Level {
    private int id;
    private File file;
    private Tile[][] tiles;
    private Position doorPosition;
    private Position rockFordPosition;
    private int nbTotalDiamonds;

    private int getTakenDiamond = 0;

    public int getGetTakenDiamond() {
        return getTakenDiamond;
    }

    private int nbRequiredDiamonds = 10;
    private final int scorePerDiamond = 20;
    private int length;
    private int height;

    /**
     * initialize level with a level
     * @param niveau the number of the level
     * @throws FileNotFoundException 
     */
    public Level(int niveau) throws FileNotFoundException  {
        this.id = niveau;
        this.nbTotalDiamonds = 0;
        
		InputStream in = new FileInputStream("./src/main/resources/levels/level" + niveau + ".txt");
	    this.file = new File(in);

        createMapFromFile();
        
        try {
			in.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
    }


    /**
     * the creation of the map thanks to the file and put each tile in its place
     */
    private void createMapFromFile() {
        this.length = this.file.getLength();
        this.height = this.file.getHeight();

        this.tiles = new Tile[this.height][this.length];

        String str = this.file.toString();
        int line = 0;
        int column = 0;

        int nbRockFords = 0;
        int nbDoors = 0;

        for (int i = 0; i < str.length(); i++) {
            char character = str.charAt(i);

            if (character == '\n') {
                line++;
                column = 0;
            }
            else {
                Position position = new Position(line, column);

                switch (character) {
                    case 'D':
                        this.tiles[line][column] = new Diamond(position);
                        this.nbTotalDiamonds++;
                        break;
                    case 'X':
                        this.tiles[line][column] = new Door(position);
                        doorPosition = new Position(line, column);
                        nbDoors++;
                        break;
                    case 'E':
                        this.tiles[line][column] = new Earth(position);
                        break;
                    case ' ':
                        this.tiles[line][column] = new Empty(position);
                        break;
                    case 'R':
                        this.tiles[line][column] = new Rock(position);
                        break;
                    case 'P':
                        this.tiles[line][column] = new RockFord(position);
                        rockFordPosition = new Position(line, column);
                        nbRockFords++;
                        break;
                    case 'W':
                        this.tiles[line][column] = new Wall(position);
                        break;
                    case '\r':
                        break;
                    default:
                        throw new IllegalArgumentException("Character " + character + " is not allowed");
                }
                column++;
            }
        }

        if ((nbDoors != 1) || (nbRockFords != 1)) {
            throw new IllegalArgumentException("File format is invalid");
        }
    }

    public int getId() {
        return this.id;
    }

    /**
     * get all the tiles on the map
     * @return
     */
    public Tile[][] getTiles() {
        return this.tiles;
    }

    /**
     * get the position of the door
     * @return the line and the column of the door
     */
    public Position getDoorPosition() {
        return this.doorPosition;
    }
    /**
     * get the position of the Rockford
     * @return the line and the column of the Rockford
     */
    public Position getRockFordPosition() {
        return this.rockFordPosition;
    }

    /**
     * get the number of the diamonds on the map
     * @return the total of
     */
    public int getNbTotalDiamonds() {
        return this.nbTotalDiamonds;
    }

    /**
     * get the required number of diamonds
     * @return the required number of diamonds
     */
    public int getNbRequiredDiamonds() {
        return this.nbRequiredDiamonds;
    }
    
    public int getScorePerDiamond() {
        return this.scorePerDiamond;
    }

    /**
     * get the length of the map
     * @return the length of the map
     */
    public int getLength() {
        return this.length;
    }
    /**
     * get the height of the map
     * @return the height of the map
     */
    public int getHeight() {
        return this.height;
    }
}

