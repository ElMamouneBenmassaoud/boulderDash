package g58112.atlg3.boulderDash.model;

public class Diamond extends Tile {
    /**
     * Constructor to create a diamond tile
     * @param position the position of the diamond
     */
    public Diamond(Position position) {
        super(position, true, true);
    }

    @Override
    public String toString() {
        return "D";
    }
}
