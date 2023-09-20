package g58112.atlg3.boulderDash.model;

public class MovePlayer implements Command {
	private BoulderDash boulderDashBeforeExecution;
    private BoulderDash boulderDash;
    private Direction direction;

    public MovePlayer(BoulderDash boulderDash, Direction direction) {
        this.boulderDash = boulderDash;
        this.direction = direction;
    }
	
	@Override
	public boolean execute() {
		this.boulderDashBeforeExecution = new BoulderDash(this.boulderDash);
		this.boulderDash.movePlayer(this.direction);
		
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
