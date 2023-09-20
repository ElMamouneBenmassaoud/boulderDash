package g58112.atlg3.boulderDash.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class BoulderDashTest {
    private BoulderDash boulderDash;


    @BeforeEach
    public void setUp() {
        boulderDash = new BoulderDash();
    }

    @Test
    void demarrer() {
        try {
            boulderDash.demarrer(1);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(new Position(1,1),boulderDash.getBoard().getRockFordPosition());
        assertEquals(1,boulderDash.getBoard().getCurrentLevel().getId());
    }

    @Test
    void isFinDuNiveau() {
        isNiveauPerduPlayerDead();
        assertEquals(true,boulderDash.isFinDuNiveau());
    }

    @Test
    void isNiveauGagne() {
        try {
            boulderDash.demarrer(100);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.DROITE);
        boulderDash.movePlayer(Direction.DROITE);
        boulderDash.movePlayer(Direction.DROITE);
        assertEquals(true,boulderDash.isNiveauGagne());
    }

    @Test
    void isNiveauPerduSurrender() {
        try {
            boulderDash.demarrer(101);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.abandonner();
        assertEquals(true,boulderDash.isFinDuNiveau());
    }
    @Test
    void isNiveauPerduTimeNoFinish() {
        try {
            boulderDash.demarrer(101);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertEquals(false,boulderDash.isNiveauPerdu());
    }
    @Test
    void isNiveauPerduPlayerDead() {
        try {
            boulderDash.demarrer(101);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.BAS);
        assertEquals(true,boulderDash.isNiveauPerdu());
    }

    @Test
    void movePlayerToRock() {
        try {
            boulderDash.demarrer(102);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.GAUCHE);
        assertEquals(new Position(1,2),boulderDash.getBoard().getRockFordPosition());
    }
    @Test
    void movePlayerPushRock() {
        try {
            boulderDash.demarrer(102);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.DROITE);
        assertEquals(new Position(1,3),boulderDash.getBoard().getRockFordPosition());
        assertEquals(true,boulderDash.getBoard().getTile(new Position(1,4)) instanceof Rock);
    }
    @Test
    void movePlayerToWall() {
        try {
            boulderDash.demarrer(102);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.HAUT);
        assertEquals(new Position(1,2),boulderDash.getBoard().getRockFordPosition());
    }
    @Test
    void movePlayerEatDiamond() {
        try {
            boulderDash.demarrer(102);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        boulderDash.movePlayer(Direction.BAS);
        assertEquals(true,boulderDash.getBoard().getTile(new Position(1,2)) instanceof Empty);
        assertEquals(new Position(2,2),boulderDash.getBoard().getRockFordPosition());
    }
    @Test
    void movePlayer5() {

    }

    @Test
    void undo() {
    }

    @Test
    void redo() {
    }

    @Test
    void abandonner() {
    }
}