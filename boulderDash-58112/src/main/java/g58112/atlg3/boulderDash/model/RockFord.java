package g58112.atlg3.boulderDash.model;

public class RockFord extends Tile {
    /**
     * Constructor to create a Rockford tile
     * @param position the position of the Rockford
     */
    public RockFord(Position position) {
        super(position, false, false);
    }

    @Override
    public String toString() {
        return "P";
    }
}
