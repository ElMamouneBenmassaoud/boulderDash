package g58112.atlg3.boulderDash.model;

public class Door extends Tile {
    /**
     * Constructor to create a Door tile
     * @param position the position of the Door
     */
    public Door(Position position) {
        super(position, false, false);
    }

    @Override
    public String toString() {
        return "X";
    }
}