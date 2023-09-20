package g58112.atlg3.boulderDash.model;

import java.util.Stack;

public class CommandManager {
	private Stack<Command> undoStack;
	private Stack<Command> redoStack;

	public CommandManager() {
		this.undoStack = new Stack<Command>();
		this.redoStack = new Stack<Command>();
	}
	
	public void add(Command command) {
		command.execute();
		undoStack.push(command);
		redoStack.clear();
	}
	
	public void undo() {
		if (!undoStack.empty()) {
			Command command = undoStack.pop();
			if (command.unexecute()) {
				redoStack.push(command);
			}
		}
	}
	
	public void redo(){
        if (!redoStack.isEmpty()){
            Command command = redoStack.pop();
            if (command.execute()) {
            	undoStack.push(command);
            }
        }
    }
}