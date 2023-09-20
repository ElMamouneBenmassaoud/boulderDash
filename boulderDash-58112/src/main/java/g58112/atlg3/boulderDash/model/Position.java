package g58112.atlg3.boulderDash.model;

import java.util.Objects;
/**
 * the Position class is used to indicate a position on a game board
 *
*/
public class Position {
    private int line;
    private int column;

    /**
     * the constructor allows to define the line and the column of the position
     * @param line the number of the line
     * @param column the number of the column
     */
    public Position(int line, int column) {
        this.line = line;
        this.column = column;
    }

    /**
     * check if a tile is close to the edge
     * @param board the board of the tiles
     * @param direction the direction to check the edges
     * @return true if a tile is close to the edge, otherwise no
     */
    public boolean isNextToEdge(Board board, Direction direction) {
        switch (direction) {
            case HAUT:
                return getLine() == 0;
            case BAS:
                return getLine() == board.getCurrentLevel().getHeight() - 1;
            case DROITE:
                return getColumn() == board.getCurrentLevel().getLength() - 1;
            case GAUCHE:
                return getColumn() == 0;
        }

        return false;
    }

    /**
     * this method allows you to see the next position depending on the direction
     * @param direction  the direction in which the tile will move
     * @return the line and the column of the next position
     */
    public Position nextPosition(Direction direction) {
        return new Position(getLine() + direction.getLineOffset(), getColumn() + direction.getColumnOffset());
    }

    /**
     * get the line of the position
     * @return the line
     */
    public int getLine() {
        return this.line;
    }
    /**
     * get the column of the position
     * @return the column
     */
    public int getColumn() {
        return this.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }

    /**
     * the equals method allows you to check if 2 positions are equal if they
     * have the same rows and the same column
     * @param o the object I want to compare
     * @return true if is equals otherwise no
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Position position = (Position) o;

        return this.line == position.getLine() && this.column == position.getColumn();
    }
}
