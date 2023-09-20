package g58112.atlg3.boulderDash.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {
    private BoulderDash boulderDash;

    @BeforeEach
    public void setUp() {
        boulderDash = new BoulderDash();
        try {
            boulderDash.demarrer(1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void isNextToEdgeHaut() {
        int line = 0;
        Position pos = new Position(line,12);
        assertEquals(true,pos.isNextToEdge(boulderDash.getBoard(), Direction.HAUT));
    }
    @Test
    void isNextToEdgeBas() {
        int line = boulderDash.getBoard().getCurrentLevel().getHeight()-1;
        Position posEdge = new Position(line,0);
        assertEquals(true,posEdge.isNextToEdge(boulderDash.getBoard(), Direction.BAS));
    }
    @Test
    void isNextToEdgeDroite() {
        int column = boulderDash.getBoard().getCurrentLevel().getLength()-1;
        Position posEdge = new Position(0,column);
        assertEquals(true,posEdge.isNextToEdge(boulderDash.getBoard(), Direction.DROITE));
    }
    @Test
    void isNextToEdgeGauche() {
        int column = 0;
        Position posEdge = new Position(10,column);
        assertEquals(true,posEdge.isNextToEdge(boulderDash.getBoard(), Direction.GAUCHE));
    }
    @Test
    void isNotNextToEdge() {
        int column = 12;
        int line = 7;
        Position posEdge = new Position(line,column);
        assertEquals(false,posEdge.isNextToEdge(boulderDash.getBoard(), Direction.GAUCHE));
    }

    @Test
    void nextPositionRight() {
        Position pos = new Position(1,2);
        assertEquals(new Position(1,3),pos.nextPosition(Direction.DROITE));
    }
    @Test
    void nextPositionLeft() {
        Position pos = new Position(1,2);
        assertEquals(new Position(1,1),pos.nextPosition(Direction.GAUCHE));
    }
    @Test
    void nextPositionUp() {
        Position pos = new Position(1,2);
        assertEquals(new Position(0,2),pos.nextPosition(Direction.HAUT));
    }
    @Test
    void nextPositionDown() {
        Position pos = new Position(1,2);
        assertEquals(new Position(2,2),pos.nextPosition(Direction.BAS));
    }

    @Test
    void getLine() {
        Position pos = new Position(1,2);
        assertEquals(1,pos.getLine());
    }

    @Test
    void getColumn() {
        Position pos = new Position(1,2);
        assertEquals(2,pos.getColumn());
    }
}