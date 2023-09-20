package g58112.atlg3.boulderDash.controller;

import g58112.atlg3.boulderDash.model.BoulderDash;
import g58112.atlg3.boulderDash.model.Direction;
import g58112.atlg3.boulderDash.model.GameState;
import g58112.atlg3.boulderDash.view.BoulderDashView;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConsoleController {
    private final static Pattern UNDO_PATTERN = Pattern.compile("undo\\s*");
    private final static Pattern REDO_PATTERN = Pattern.compile("redo\\s*");
    private final static Pattern UP_PATTERN = Pattern.compile("up\\s*");
    private final static Pattern DOWN_PATTERN = Pattern.compile("down\\s*");
    private final static Pattern RIGHT_PATTERN = Pattern.compile("right\\s*");
    private final static Pattern LEFT_PATTERN = Pattern.compile("left\\s*");
    private final static Pattern FF_PATTERN = Pattern.compile("ff\\s*");
    private final static Direction UP = Direction.HAUT;
    private final static Direction DOWN = Direction.BAS;
    private final static Direction RIGHT = Direction.DROITE;
    private final static Direction LEFT = Direction.GAUCHE;

    private BoulderDash boulderDash;
    private BoulderDashView view;

    public static void main(String[] args) throws Throwable {
        BoulderDash boulderDash = new BoulderDash();
        BoulderDashView view = new BoulderDashView(boulderDash);
        ConsoleController applicationConsole = new ConsoleController(boulderDash,view);
        applicationConsole.selectLevel();
    }
    
    public ConsoleController (BoulderDash boulderDash, BoulderDashView view) {
        this.boulderDash = boulderDash;
        this.view = view;
    }

    private void selectLevel() {
        System.out.println("Selectionnez un niveau : ");
        Scanner clavier = new Scanner(System.in);
        String command = clavier.nextLine();
        play(Integer.valueOf(command));
        clavier.close();
    }

    private void play(int niveau) {
        try {
            boulderDash.start(niveau);
            Scanner clavier = new Scanner(System.in);

            while (!this.boulderDash.getState().equals(GameState.GAME_OVER) &&
                    !(this.boulderDash.getState().equals(GameState.WIN) && this.boulderDash.getBoard().getLives() == 0)
            ) {
                this.view.displayTiles();
                this.view.displayCommand();
                String command = clavier.nextLine();

                if(ConsoleController.UP_PATTERN.matcher(command.toLowerCase()).matches()) {
                    this.boulderDash.move(ConsoleController.UP);
                }
                else if (ConsoleController.DOWN_PATTERN.matcher(command.toLowerCase()).matches()) {
                    this.boulderDash.move(ConsoleController.DOWN);
                }
                else if (ConsoleController.RIGHT_PATTERN.matcher(command.toLowerCase()).matches()) {
                    this.boulderDash.move(ConsoleController.RIGHT);
                }
                else if (ConsoleController.LEFT_PATTERN.matcher(command.toLowerCase()).matches()) {
                    this.boulderDash.move(ConsoleController.LEFT);
                }
                else if (ConsoleController.UNDO_PATTERN.matcher(command.toLowerCase()).matches()) {
                    this.boulderDash.undo();
                }
                else if (ConsoleController.REDO_PATTERN.matcher(command.toLowerCase()).matches()) {
                    this.boulderDash.redo();
                }
                else if (ConsoleController.FF_PATTERN.matcher(command.toLowerCase()).matches()) {
                    this.boulderDash.abandonner();
                    this.view.displayGameOver();
                }
                else {
                    this.view.displayHelp();
                }
            }

            if (this.boulderDash.getState().equals(GameState.GAME_OVER)) {
                this.view.displayGameOver();
            }
            else if (this.boulderDash.getState().equals(GameState.WIN)  && this.boulderDash.getBoard().getLives() == 0) {
                this.view.displayWin();
            }

            clavier.close();
        } catch (FileNotFoundException e) {
            System.out.println("Le niveau " + niveau + " n'existe pas !");
            selectLevel();

        }

    }
}

