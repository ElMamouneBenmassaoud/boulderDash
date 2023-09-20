package g58112.atlg3.boulderDash.view;

import g58112.atlg3.boulderDash.model.*;

public class BoulderDashView {
    BoulderDash boulderDash;
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String ANSI_BLUE = "\033[0;34m";
    public static final String YELLOW = "\033[0;33m";
    public static final String RED_BACKGROUND = "\033[41m";
    public BoulderDashView(BoulderDash boulderDash) {
        this.boulderDash = boulderDash;
    }

    public void displayTiles() {
        Board board = boulderDash.getBoard();
        Level level = board.getCurrentLevel();

        for (int i = 0; i < level.getHeight(); i++) {
            for (int j = 0; j < level.getLength(); j++) {
                Position posTile = new Position(i, j);
                Tile tile = boulderDash.getBoard().getTile(posTile);

                if (tile instanceof RockFord) {
                    System.out.print(ANSI_RED + tile + ANSI_RESET + "\t");
                }
                else if (tile instanceof Diamond) {
                    System.out.print(ANSI_BLUE + tile + ANSI_RESET + "\t");
                }
                else if (tile instanceof Rock) {
                    System.out.print(PURPLE_BOLD + tile + ANSI_RESET + "\t");
                }
                else if (tile instanceof Door) {
                    System.out.print(YELLOW + tile + ANSI_RESET + "\t");
                }
                else if (tile instanceof Wall) {
                    System.out.print(RED_BACKGROUND + tile + ANSI_RESET + "\t");
                }
                else {
                    System.out.print(tile + "\t");
                }
            }

            System.out.println("\n");
        }
    }
    public void displayInfo() {
        System.out.println("Nombre diamants requis : " + boulderDash.getBoard().getCurrentLevel().getNbRequiredDiamonds());
        System.out.println("Points par diamant : " + boulderDash.getBoard().getCurrentLevel().getScorePerDiamond());
        System.out.println("Diamants captures : " + boulderDash.getBoard().getTakenDiamonds());
        System.out.println("Temps restant (s) : " + boulderDash.getRemainingTime());
        System.out.println("Score : " + boulderDash.getBoard().getScore());
        System.out.println("Nombre de vies restantes : " + boulderDash.getBoard().getLives());
    }

    public void displayGameOver() {
        System.out.println("Partie perdu !");
    }

    public void displayHelp() {
        System.out.println("La commande saisie n'est pas valide !");
        System.out.println("Voici les commandes disponibles :");
        System.out.println("UP | DOWN | RIGHT | LEFT | UNDO | REDO | FF");
    }

    public void displayCommand() {
        System.out.println("Entrez une commande : (UP | DOWN | RIGHT | LEFT | UNDO | REDO | FF)");
    }

    public void displayWin() {
        System.out.println("Vous avez gagné !");
        System.out.println("Félicitations !");
    }
}