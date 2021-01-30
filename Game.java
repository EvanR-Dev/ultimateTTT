package uTTT;

import java.util.Scanner;

public class Game {
	Scanner input = new Scanner(System.in);		// Scanner object to read user input
	
	private Player[] players = new Player[2];	// Game players (computer or human)
	private WinBoard winBoard = new WinBoard();	// Track winner of each board using regular 3x3 TTT rules, composition
	private Board board;						// 9x9 UTTT game board
	private String[] marks = {"X", "O"};		// Players' marks
	
	// Array is 9x9, 3 small boards to win entire board
	private int gameRowSize = 9;
	private int gameColSize = 9;
	private int gameScoreToWin = 3;
	private int numMoves;
	private int currentPlayerIndex = -1;		// Track player's turn
	
	private String[] wonBoards = new String[gameRowSize];
	
	// Default game constructor
	public Game() {
		setPlayers();
		setBoard();
	}
	
	// Set 9x9 Board
	private void setBoard() {
		this.board = new Board(gameRowSize, gameColSize);
	}
	
	// Set players in game
	private void setPlayers() {
		for (int i = 0; i < players.length; i++) {
			// Assign X and O to 2 players
			ComputerPlayer p = new ComputerPlayer("Player " + i + 1, marks[i]);
			players[i] = p;
		}
	}
	
	// Set each player as either computer or human
	public void setPlayers(Player player1, Player player2) {
		players[0] = player1;
		players[1] = player2;
	}
	
	public void start() {
		initWonBoards();	// Initialize array that has won boards for players
		do {
			switchPlayer();			// Assign 1st player to start game and then switch after each turn
			printCurrentPlayer(); 	// Print curr player w/ their mark X or O
			
			// First move, set first board
			if (numMoves == 0)
				board.setBoardNum(players[this.currentPlayerIndex].isComputer());
			
			// Get row and col location to determine that the board may be full
			getLocation();
			// Set square number on board (board is not full
			if (!board.isFull()) {
				printBoardNum();	// Print selected board num
				// Show available moves
				board.availableMoves();
				board.setSquareNum(players[this.currentPlayerIndex].isComputer());
			}
			// Get row and col location for 2D array given square and board numbers
			getLocation();
			
			// Check if board being played on is full
			if (board.isFull()) {
				System.out.println("This board is full. Choose any board/square.");
				board.setBoardNum(players[this.currentPlayerIndex].isComputer());
				printBoardNum();	// Print selected board num
				board.setSquareNum(players[this.currentPlayerIndex].isComputer());
				getLocation();
			}
			// Reset squareNum for computer if invalid
			if (players[this.currentPlayerIndex].isComputer()) {
				while (board.resetSquareNum(players[this.currentPlayerIndex].selectRowValue(board.getMaxRowBoundary(), 
						board.getMinRowBoundary(), board.getSquareNum()),
						players[this.currentPlayerIndex].selectColValue(board.getMaxColBoundary(),
								board.getMinColBoundary(), board.getSquareNum()))) {
					getLocation();
				}
			}
			// Print selected square num
			printSquareNum();
			// Make a valid move, using mark, row, col, and if player is computer/human
			if (board.makeMove(players[this.currentPlayerIndex].getMark(),
					players[this.currentPlayerIndex].selectRowValue(board.getMaxRowBoundary(),
							board.getMinRowBoundary(), board.getSquareNum()),
					players[this.currentPlayerIndex].selectColValue(board.getMaxColBoundary(),
							board.getMinColBoundary(), board.getSquareNum()),
					players[this.currentPlayerIndex].isComputer()));
			// Square was taken, invalid move
			else {
				switchPlayer();			// Switch back to other player
				board.printMyBoard();	// Print board
				printBoardWinners();	// Print winners of boards
				continue;				// Switch to player that made invalid move
			}
			numMoves++;					// Count valid move
			
			// Small board winner, board hasn't been won yet
			if (smallBoardWin() && !winBoard.isWon(board.getBoardNum())) {
				// Mark a board win
				winBoard.markBoardWin(board.getBoardNum(), players[this.currentPlayerIndex].getMark());
				// Print board
				board.printMyBoard();
				// Set board as winner, store player's mark at board index
				wonBoards[board.getBoardNum()] = players[this.currentPlayerIndex].getMark();
			}
			// No win on any of the boards
			else {
				board.printMyBoard();
			}											// Print the board and continue game
			printBoardWinners();						// Print winners of boards
			board.setBoardNum(board.getSquareNum());	// Set selected square as board num for next turn
		} while (!gameOver());							// End loop if entire game is finished (tie or win)
	}
	
	// Print winners of boards
	private void printBoardWinners() {
		for (int i = 0; i < wonBoards.length; i++) {
			if (wonBoards[i].equals("X") || wonBoards[i].equals("O")) {
				System.out.println("The Board#" + i + " winner is " + wonBoards[i]);
			}
		}
	}
	
	// Initialize the array that has the winner for each board
	private void initWonBoards() {
		if (numMoves == 0)
			for (int i = 0; i < wonBoards.length; i++)
				wonBoards[i] = "-";
	}
	
	// Using board number, set boundaries to focus on each board
	private void getLocation() {
		final int OFFSET = 3;	// Multiplier to jump rows/cols
		
		// Find the boundaries of the 9 boards
		// Uses a min/max row and col boundary to make sure that board is played on
		board.setMaxRowBoundary(2 + (board.getBoardNum() / 3) * OFFSET);
		board.setMinRowBoundary((board.getBoardNum() / 3) * OFFSET);
		board.setMaxColBoundary(2 + ((board.getBoardNum() % 3) * OFFSET));
		board.setMinColBoundary((board.getBoardNum() % 3) * OFFSET);
	}
	
	// Switch player index
	private void switchPlayer() {
		if (this.currentPlayerIndex == -1 || this.currentPlayerIndex == 1)
			this.currentPlayerIndex = 0;
		else
			this.currentPlayerIndex = 1;
	}
	
	// Print square number
	private void printSquareNum() {
		System.out.println("Selected Square : " + board.getSquareNum());
	}
	
	// Print current player (mark)
	private void printCurrentPlayer() {
		System.out.println("\nCurrent player is : "
				+ players[this.currentPlayerIndex].getMark());
	}
	
	// Print board number
	private void printBoardNum() {
		System.out.println("Selected Board : " + board.getBoardNum());
	}
	
	// Boards 0 thru 8
	private boolean smallBoardWin() {
		// Detect that one of the boards is a win
		if (checkSmallBoardRows(board.getMinRowBoundary()))
			return true;
		else if (checkSmallBoardCols(board.getMinColBoundary()))
			return true;
		else if (checkSmallBoardDiagLR(board.getMinRowBoundary(), board.getMaxColBoundary()))
			return true;
		else if (checkSmallBoardDiagRL(board.getMinRowBoundary(), board.getMinColBoundary()))
			return true;
		else
			return false;
	}
	
	// Use normal TTT rules using board boundaries
	// Check for row win
	private boolean checkSmallBoardRows(int row) {
		for (; row <= board.getMaxRowBoundary(); row++) {
			if (checkSmallBoardRow(row, board.getMinColBoundary()))
				return true;
		}
		return false;
	}
	
	// Check by traversing cols of row
	private boolean checkSmallBoardRow(int row, int col) {
		int count = 0;
		for (; col <= board.getMaxColBoundary(); col++) {
			if (board.getMark(row, col).equals(players[currentPlayerIndex].getMark()))
				count++;
		}
		if (count == this.gameScoreToWin)
			return true;
		return false;
	}
	
	// Check for col win
	private boolean checkSmallBoardCols(int col) {
		for (; col <= board.getMaxColBoundary(); col++) {
			if (checkSmallBoardCol(col, board.getMinRowBoundary()))
				return true;
		}
		return false;
	}
	
	// Check by traversing rows of col
	private boolean checkSmallBoardCol(int col, int row) {
		int count = 0;
		for (; row <= board.getMaxRowBoundary(); row++) {
			if (board.getMark(row, col).equals(players[currentPlayerIndex].getMark()))
				count++;
		}
		if (count == this.gameScoreToWin)
			return true;
		return false;
	}
	
	// Bottom left to top right win
	private boolean checkSmallBoardDiagLR(int row, int col) {
		int count = 0;
		for (; row <= board.getMaxRowBoundary() && col >= board.getMinColBoundary(); row++, col--) {
			if (board.getMark(row, col).equals(players[currentPlayerIndex].getMark()))
				count++;
		}
		if (count == this.gameScoreToWin)
			return true;
		return false;
	}
	
	// Top left to bottom right win
	private boolean checkSmallBoardDiagRL(int row, int col) {
		int count = 0;
		for (; col <= board.getMaxColBoundary() && row <= board.getMaxRowBoundary(); col++, row++) {
			if (board.getMark(row, col).equals(players[currentPlayerIndex].getMark()))
				count++;
		}
		if (count == this.gameScoreToWin)
			return true;
		return false;
	}
	
	// A pattern has been detected as a win
	private boolean gameOver() {
		// Game winner of entire board, check 3x3, "winBoard"
		if (winBoard.isWinner(players[currentPlayerIndex].getMark())) {
			System.out.println("\n" + this.marks[this.currentPlayerIndex] + " won!");
			System.out.println("Total moves in game: " + numMoves);
			return true;
		}
		// Tie (no row, col, or diags)
		else if (winBoard.isFull() || board.entireBoardFull()) {
			System.out.println("\nTie!");
			System.out.println("Total moves in game: " + numMoves);
			return true;
		}
		return false;	// Continue game
	}
}