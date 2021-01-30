package uTTT;

public class WinBoard {
	// Separate 2D array of 3x3 to follow/simulate regular TTT winning rules
	private int rowSize = 3;
	private int colSize = 3;
	private String[][] boardWinner;
	private int gameScoreToWin = 3;
	
	// Create board to track win
	WinBoard() {
		boardWinner = new String[rowSize][colSize];
		
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				boardWinner[i][j] = "-";
			}
		}
	}
	
	// 1 for win on board, 0 for yet to be determined
	public void markBoardWin(int boardNum, String mark) {
		// Get location in 2D array
		int row = getBoardWinRow(boardNum);
		int col = getBoardWinCol(boardNum);
		
		boardWinner[row][col] = mark;	// Set as winner of a board, check for gameOver after
	}
	
	// Follow pattern of board to get correct row for 3x3 for winner
	private int getBoardWinRow(int boardNum) {
		if (boardNum <= 2)
			return 0;
		if (boardNum <= 5)
			return 1;
		return 2;
	}
	
	// Follow pattern of board to get correct col for 3x3 for winner
	private int getBoardWinCol(int boardNum) {
		if (boardNum == 0 || boardNum == 3 || boardNum == 6)
			return 0;
		if (boardNum == 1 || boardNum == 4 || boardNum == 7)
			return 1;
		return 2;
	}
	
	// This means there was a tie
	public boolean isFull() {
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				// Board has not been filled (not all boards have been completed)
				if (isAvailable(i, j)) {
					return false;
				}
			}
		}
		return true;
	}
	
	public boolean isWon(int boardNum) {
		// Get location in 2D array
		int row = getBoardWinRow(boardNum);
		int col = getBoardWinCol(boardNum);
				
		return !isAvailable(row, col);
	}
	
	private boolean isAvailable(int row, int col) {
		return !boardWinner[row][col].equals("X") && !boardWinner[row][col].equals("O");
	}
	
	// Detect winner
	public boolean isWinner(String mark) {
		if (checkRows(mark))
			return true;
		else if (checkCols(mark))
			return true;
		else if (checkDiagLR(mark))
			return true;
		else if (checkDiagRL(mark))
			return true;
		else
			return false;
	}
	
	// Use normal TTT rules on the 3x3 array to detect winner of UTTT (entire) board
	// Check for row win
	private boolean checkRows(String mark) {
		for (int row = 0; row < this.rowSize; row++) {
			if (checkRow(row, mark))
				return true;
		}
		return false;
	}
	
	// Check by traversing cols of row
	private boolean checkRow(int row, String mark) {
		int count = 0;
		for (int col = 0; col < this.rowSize; col++) {
			if (boardWinner[row][col].equals(mark))
				count++;
		}
		if (count == gameScoreToWin)
			return true;
		return false;
	}
	
	// Check for col win
	private boolean checkCols(String mark) {
		for (int col = 0; col < this.colSize; col++) {
			if (checkCol(col, mark))
				return true;
		}
		return false;
	}
	
	// Check by traversing rows of col
	private boolean checkCol(int col, String mark) {
		int count = 0;
		for (int row = 0; row < this.rowSize; row++) {
			if (boardWinner[row][col].equals(mark))
				count++;
		}
		if (count == this.gameScoreToWin)
			return true;
		return false;
	}
	
	// Bottom left to top right win
	private boolean checkDiagLR(String mark) {
		int count = 0;
		for (int row = 0, col = this.rowSize - 1; row < this.colSize && col >= 0; row++, col--) {
			if (boardWinner[row][col].equals(mark))
				count++;
		}
		if (count == this.gameScoreToWin)
			return true;
		return false;
	}
	
	// Top left to bottom right win
	private boolean checkDiagRL(String mark) {
		int count = 0;
		for (int col = 0, row = 0; col < colSize && row < this.colSize; col++, row++) {
			if (boardWinner[row][col].equals(mark))
				count++;
		}
		if (count == this.gameScoreToWin)
			return true;
		return false;
	}
}
