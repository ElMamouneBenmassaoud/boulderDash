package g58112.atlg3.boulderDash.model;

public enum Direction {
    /**
     * DIRECTION UP
     */
    HAUT (-1, 0),
    /**
     * DIRECTION DOWN
     */
    BAS (1, 0),
    /**
     * DIRECTION RIGHT
     */
    DROITE (0, 1),
    /**
     * DIRECTION LEFT
     */
    GAUCHE (0, -1);

    private int lineOffset;
    private int columnOffset;

    /**
     * Constructor to initialize the direction
     * @param lineOffset value added line
     * @param columnOffset value added column
     */
    private Direction(int lineOffset, int columnOffset) {
        this.lineOffset = lineOffset;
        this.columnOffset = columnOffset;
    }

    /**
     * get the line offset
     * @return the offset
     */
    public int getLineOffset() {
        return this.lineOffset;
    }
    /**
     * get the column offset
     * @return the offset
     */
    public int getColumnOffset() {
        return this.columnOffset;
    }
}
