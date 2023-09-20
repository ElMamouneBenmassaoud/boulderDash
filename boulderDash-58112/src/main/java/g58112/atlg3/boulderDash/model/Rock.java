package g58112.atlg3.boulderDash.model;

public class Rock extends Tile {
    /**
     * Constructor to create a Rock tile
     * @param position the position of the rock
     */
    public Rock(Position position) {
        super(position, true, true);
    }

    @Override
    public String toString() {
        return "R";
    }
}
