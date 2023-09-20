package g58112.atlg3.boulderDash.model;

public class Earth extends Tile {
    /**
     Constructor to create an earth tile
     * @param position the position of the earth
     */
    public Earth(Position position) {
        super(position, false, false);
    }

    @Override
    public String toString() {
        return "E";
    }
}
