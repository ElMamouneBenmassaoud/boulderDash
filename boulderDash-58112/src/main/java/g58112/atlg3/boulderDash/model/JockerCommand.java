package g58112.atlg3.boulderDash.model;

public class JockerCommand implements Command{
    private BoulderDash boulderDashBeforeExecution;
    private BoulderDash boulderDash;

    public JockerCommand(BoulderDash boulderDash) {
        this.boulderDash = boulderDash;
    }

    @Override
    public boolean execute() {
        this.boulderDashBeforeExecution = new BoulderDash(this.boulderDash);
        this.boulderDash.getBoard().hasTakenJocker();
        return true;
    }

    @Override
    public boolean unexecute() {
        boolean levelChanged = this.boulderDashBeforeExecution.getBoard().getCurrentLevel() == this.boulderDash.getBoard().getCurrentLevel();
        boolean stateCorrect = this.boulderDash.getState() == GameState.PLAY;

        boolean canUnexecute = levelChanged && stateCorrect;
        if (canUnexecute) {
            this.boulderDash.revertTo(this.boulderDashBeforeExecution);
        }
        return canUnexecute;
    }
}
