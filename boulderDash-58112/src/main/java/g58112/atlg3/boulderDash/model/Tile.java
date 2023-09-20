package g58112.atlg3.boulderDash.model;

public abstract class Tile {
    private Position startPosition;
    private boolean isMobile;
    private boolean isSlippery;

    /**
     * Constructor to define the position, the mobility and the slippery of the tile
     * @param startPosition the starting position of the tile
     * @param isMobile tile mobility
     * @param isSlippery tile slippery
     */
    public Tile(Position startPosition, boolean isMobile, boolean isSlippery) {
        this.startPosition = startPosition;
        this.isMobile = isMobile;
        this.isSlippery = isSlippery;
    }

    /**
     * get the starting position of the tile
     * @return the line and the column of the tile
     */
    public Position getStartPosition() {
        return this.startPosition;
    }

    /**
     * get the mobility of the tile
     * @return true if the is mobile, false otherwise
     */
    public boolean isMobile() {
        return this.isMobile;
    }

    /**
     * get the slippery of the tile
     * @return true if the tile is slippery, false otherwise
     */
    public boolean isSlippery() {
        return this.isSlippery;
    }

    /**
     * set the new starting position of the tile
     * @param startPosition the new line and the new column of the tile
     */
    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }
}
