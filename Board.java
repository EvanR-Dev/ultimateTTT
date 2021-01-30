package uTTT;

import java.util.Scanner;

public class Board {
	Scanner input = new Scanner(System.in);		// Scanner object to read user input
	
	// 9x9 game board that will be split
	private int boardRowSize;
	private int boardColSize;
	private String[][] board;
	
	// Game board has boardNum and squareNum
	private int boardNum;
	private int squareNum;
	
	// Boundaries to split board into 9 different boards
	private int maxRowBoundary;
	private int minRowBoundary;
	private int maxColBoundary;
	private int minColBoundary;
	
	// Set standard sizes of board
	Board() {
		this(9, 9);
	}
	
	// Parameterized constructor
	Board(int rowSize, int colSize) {
		this.setSize(rowSize, colSize);
	}
	
	// Set size of board for 2D array
	private void setSize(int rowSize, int colSize) {
		this.boardColSize = colSize;
		this.boardRowSize = rowSize;
		init();
	}
	
	// Initialize board
	void init() {
		this.board = new String[boardRowSize][boardColSize];	// Define 2D array for board
		
		// Initialize entire 2D array
		int squareNum = 0;
		for(int i = 0 ; i < board.length; i++) {
			// Row 3 and 6 must reset square num
			if (i != 0 && i % 3 == 0)
				squareNum = 0;
			
			for(int j = 0 ; j < board[i].length; j++) {
				// Col 3 and 6 must reset square num
				if (j != 0 && j % 3 == 0)
					squareNum -= 3;
				board[i][j] = Integer.toString(squareNum++);	// Initialize with 0 - 8
			}
		}
		System.out.println("\n===== WELCOME TO THE ULTIMATE TIC-TAC-TOE GAME!! =====");
		printMyBoard();
	}
	
	public void printMyBoard() {
		// Print array by row
		int boardNum = 0;
		for(int i = 0 ; i < board.length; i++) {
			// Not on row mult of 3, reset board num
			if (i % 3 != 0)
				boardNum -= 3;
			
			// Print row by printing all 9 cols each
			for(int j = 0 ; j < board[i].length; j++) {
				// Cols of 3 means a row of 1 sub-board has been printed
				if (j % 3 == 0) {
					System.out.print("\tBOARD#" + boardNum++ + " | ");
				}
				System.out.print(board[i][j] + " | ");		// Print element
			}
			System.out.println();	// Print next row
		}
	}
	
	// Make move on board
	public boolean makeMove(String mark, int row, int col, boolean isComp) {
		// Available square, human, and board is not full
		if (isAvailable(row, col) && !isFull()) {
			board[row][col] = mark;
			return true;
		}
		// A particular board is full
		if (isFull()) {
			return true;	// Don't mark, but continue (since you can go anywhere)
		}
		// Square is a number 0 to 8, 
		System.out.println("Please try again!");
		return false;
	}
	
	// Detects if a small board (0 - 8) is full
	public boolean isFull() {
		int minRow = getMinRowBoundary(), maxRow = getMaxRowBoundary(), minCol = getMinColBoundary(),
				maxCol = getMaxColBoundary();
		
		for(; minRow <= maxRow; minRow++) {
			for (int col = minCol; col <= maxCol; col++) {
				if (isAvailable(minRow, col)) {
					return false;	// Board is not full
				}
			}
		}
		return true;	// Board is full
	}
	
	// Available moves
	public void availableMoves() {
		int minRow = getMinRowBoundary(), maxRow = getMaxRowBoundary(), minCol = getMinColBoundary(),
				maxCol = getMaxColBoundary();
		
		System.out.println("Available Moves: ");
		for(; minRow <= maxRow; minRow++) {
			for (int col = minCol; col <= maxCol; col++) {
				if (isAvailable(minRow, col)) {
					System.out.print(board[minRow][col] + " ");
				}
			}
		}
		System.out.println("\n");
	}
	
	// Entire board is full
	public boolean entireBoardFull() {
		for (int i = 0; i < boardRowSize; i++) {
			for (int j = 0; j < boardColSize; j++) {
				if (!board[i][j].equals("X") && !board[i][j].equals("O"))
					return false;
			}
		}
		return true;
	}
	
	// Square has number 0 - 9
	private boolean isAvailable(int row, int col) {
		return Character.isDigit(board[row][col].charAt(0));
	}
	
	// Return mark at square
	public String getMark(int row, int col) {
		return board[row][col];
	}
	
	// Getters and setters for remaining data fields
	public int getBoardNum() {
		return boardNum;
	}
	
	public void setBoardNum(boolean isComp) {
		if (isComp)		// Player is computer
			boardNum = (int) (Math.random() * 9);	// boardNum is rand num 0 to 8
		else {			// Player is human
			do {		// Validate input
				System.out.print("Please select a valid board: ");
				boardNum = input.nextInt();
			} while (boardNum < 0 || boardNum > 8);	// Num must be 0 to 8
		}
	}
	
	// Overloaded setter for setting boardNum to squareNum
	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	
	// Reset square num for computer
	public boolean resetSquareNum(int row, int col) {
		if (!isAvailable(row, col) && !isFull()) {
			setSquareNum(true);		// Reset square num 0 to 8
			return true;
		}
		return false;	// Set square is available
	}

	public int getSquareNum() {
		return squareNum;
	}

	public void setSquareNum(boolean isComp) {
		if (isComp)		// Player is computer
			squareNum = (int) (Math.random() * 9);	// boardNum is rand num 0 to 8
		else {			// Player is human
			do {		// Validate input
				System.out.print("Please select a valid square on the selected board: ");
				squareNum = input.nextInt();
			} while (squareNum < 0 || squareNum > 8);	// Num must be 0 to 8
		}
	}

	public int getMaxRowBoundary() {
		return maxRowBoundary;
	}

	public void setMaxRowBoundary(int maxRowBoundary) {
		this.maxRowBoundary = maxRowBoundary;
	}

	public int getMinRowBoundary() {
		return minRowBoundary;
	}

	public void setMinRowBoundary(int minRowBoundary) {
		this.minRowBoundary = minRowBoundary;
	}

	public int getMaxColBoundary() {
		return maxColBoundary;
	}

	public void setMaxColBoundary(int maxColBoundary) {
		this.maxColBoundary = maxColBoundary;
	}

	public int getMinColBoundary() {
		return minColBoundary;
	}

	public void setMinColBoundary(int minColBoundary) {
		this.minColBoundary = minColBoundary;
	}
}
