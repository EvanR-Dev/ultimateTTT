package uTTT;

public class HumanPlayer extends Player {
	// Parameterized constructor to initialize human player
	public HumanPlayer(String name, String mark) {
		super(name, mark);
	}
	
	// Identify player as human
	@Override
	public boolean isComputer() {
		return false;
	}
}
