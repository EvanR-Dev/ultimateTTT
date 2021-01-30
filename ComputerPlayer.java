package uTTT;

public class ComputerPlayer extends Player {
	// Parameterized constructor to initialize computer player
	public ComputerPlayer(String name, String mark) {
		super(name, mark);
	}
	
	// Identify player as computer
	@Override
	public boolean isComputer() {
		return true;
	}
}