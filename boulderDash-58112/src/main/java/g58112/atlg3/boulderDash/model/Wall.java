package g58112.atlg3.boulderDash.model;

public class Wall extends Tile {
    /**
     * Constructor to create a wall tile
     * @param position the position of the wall
     */
    public Wall(Position position) {
        super(position, false, true);
    }

    @Override
    public String toString() {
        return "W";
    }
}

