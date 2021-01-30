package uTTT;

public abstract class Player {
	// Private members name and mark for all players
	private String name;
	private String mark;
	
	// Set name and mark constructor
	public Player(String name, String mark) {
		this.setName(name);
		this.setMark(mark);
	}
	
	// Setters and getters for data
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getMark() {
		return mark;
	}
	
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	// Get row value from square number
	public int selectRowValue(int maxRow, int minRow, int sqNum) {
		if (sqNum <= 2) {
			return minRow;
		}
		else if (sqNum <= 5) {
			return minRow + 1;
		}
		return maxRow;
	}
	
	// Get col value from square number
	public int selectColValue(int maxCol, int minCol, int sqNum) {
		if (sqNum == 0 || sqNum == 3 || sqNum == 6) {
			return minCol;
		}
		else if (sqNum == 1 || sqNum == 4 || sqNum == 7) {
			return minCol + 1;
		}
		return maxCol;
	}
	
	public abstract boolean isComputer();
}
