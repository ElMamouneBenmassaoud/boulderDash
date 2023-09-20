package g58112.atlg3.boulderDash.model;

public interface BoulderDashModel {
    /**
     *Method to start a game from a level
     * @param niveau the number of the level
     * @throws Throwable an exception
     */
    public void demarrer(int niveau) throws Throwable;

    /**
     * Method to detect if the end of the level is reached
     * @return true if is the end of the level, otherwise no
     */
    public boolean isFinDuNiveau();

    /**
     * Method to detect if a level is gained or lost
     * @return true if is a win, false if is a lose
     */
    public boolean isNiveauGagne();

    /**
     * Method for making a move
     * @param direction sens of the direction
     */
    
    public void movePlayer(Direction direction);

    /**
     * Method to undo a blow
     */
    public void undo();

    /**
     * Method to redo a blow
     */
    public void redo();

    /**
     * Method to abandon a game
     */
    public void abandonner();

}
