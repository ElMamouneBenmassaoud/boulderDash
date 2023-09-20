package g58112.atlg3.boulderDash.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private BoulderDash boulderDash;



    @BeforeEach
    public void setUp() {
        boulderDash = new BoulderDash();
    }

    @Test
    void moveTileDiamond() {
        try {
            boulderDash.demarrer(102);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.BAS);
        assertEquals(7,boulderDash.getBoard().getNbRemainingDiamonds());
    }

    @Test
    void moveTileRock() {
        try {
            boulderDash.demarrer(102);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //a faire !
    }

    @Test
    void getRockFordPositionTest() {
        try {
            boulderDash.demarrer(1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(new Position(1,1),boulderDash.getBoard().getRockFordPosition());
    }
    @Test
    void getTileTest() {
        try {
            boulderDash.demarrer(1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(true,boulderDash.getBoard().getTile(new Position(0,0)) instanceof Wall);
    }

    @Test
    void checkMobileTileRockFallOnPlayer() {
        try {
            boulderDash.demarrer(102);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.BAS);
        boulderDash.movePlayer(Direction.DROITE);
        boulderDash.movePlayer(Direction.BAS);
        assertEquals(true,boulderDash.getBoard().checkMobileTileFallOnPlayer());
    }
    @Test
    void checkMobileTileDiamondFallOnPlayer() {
        try {
            boulderDash.demarrer(103);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.BAS);
        assertEquals(true,boulderDash.getBoard().checkMobileTileFallOnPlayer());
    }

    @Test
    void checkPlayerReachDoor() {
        try {
            boulderDash.demarrer(100);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.DROITE);
        boulderDash.movePlayer(Direction.DROITE);
        boulderDash.movePlayer(Direction.DROITE);
        assertEquals(true,boulderDash.getBoard().checkPlayerReachDoor());
    }
}