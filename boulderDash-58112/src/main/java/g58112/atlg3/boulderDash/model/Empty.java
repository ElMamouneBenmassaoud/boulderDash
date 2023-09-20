package g58112.atlg3.boulderDash.model;

public class Empty extends Tile {
    /**
     * Constructor to create an empty tile
     * @param position the position of the empty tile
     */
    public Empty(Position position) {
        super(position, false, false);
    }

    @Override
    public String toString() {
        return " ";
    }
}
