package uTTT;

/** Evan Roman
 * 
 * 	Analysis:
 * 
 * 	Ultimate TTT involves a board that has 9 separate games, each in a square.
 * 	So, there are now 81 square total, with the 9 games x 9 squares.
 * 
 *	Winning involves winning 3 games/smaller boards in the same pattern as normal TTT.
 *	So, you must plot ahead for each game. You only get to choose the board on the first turn,
 *	and then the square that is selected on each turn determines the board for the next player.
 *	For example, in the first turn, Board 2 is selected, and square 4 is selected. In the next
 *	turn for the other player, they musst select an available square on board 4 only. Let's say they
 *	select square 6. Then on the next turn, the player must select an available square on board 6, and so on...
 *
 *	When a board is won or tied, it cannot be won by the other player. However, a player can force the
 *	other player to go back to it. They can select any square available, but if the board they go to is full, then they can
 *	select any board and square.
 * 
 *	Design:
 *
 *	DriverMain Class:
 *	We will need to create 2 players for our game. We will ask the user what kind of players
 *	(computer or human) each will be. Then we will set these as the players and start the same.
 *
 * 	Board Class:
 * 	Use a 9x9 board that has a minimum and maximum boundary for each row and column according to
 * 	the board number that is selected. This means we also have a board number and a square number
 * 	for the board. The array will be defined using the gameRow and gameCol size members to make a
 * 	2D 9x9 array. We initialize the board with numbers 0 thru 8 to act as our square numbers. We also
 * 	have a print method that can print the board at any time.
 * 
 * 	A player's move will be processed here. If a square is available, then it will be a number. So,
 * 	we can replace it with the user's mark. Otherwise, the move is invalid, and we will return false
 * 	and allow the user to try again. We also have methods that will cover the rest of board with an *
 * 	if it was already won, checking if a small board is full, checking if the large board 9x9 is full
 * 	and setters and getters for data fields that will validate the data based on input and if the player is
 * 	a computer or human.
 * 
 * 	Game Class:
 * 	This is where most things take place. This sets the players and the board we will be working with.
 * 	The start method is the most important method. This continuously runs the game until there is a winner.
 * 	In the first turn, allow the user to select a board. Then let them select a square. From these two fields,
 * 	we will find which row and column the mark should be placed, and if it can be done (full or not). If the
 * 	square is already taken, then stay on the player and ask them to try again. The computer will randomly select a
 * 	square number and board number.
 * 
 * 	If successful, check for the winner of the smallBoard and track it if there was one.
 * 	Keep on tracking these small boards to see if a player won the entire board. At the end, assign the board num equal
 *  to the last entered square number. In each iteration, we will switch the player index to switch players, print the
 *  required info (current player, selected board, etc.), and only ask for the square number. The only time that the board
 *  number will be asked again is if a small board that a player was sent to by another player is full, meaning they can
 *  choose where to go next.
 * 
 * 	ComputerPlayer Class:
 * 	Inherits from Player. We have a method that tells us if the player is a computer. It will be set to true,
 * 	so that we can distinguish it from the human player to make the correct moves. It inherits methods from the
 * 	Player class, so we can get the mark and name of the player.
 * 
 * 	HumanPlayer Class:
 *  Inherits from Player. Similar to ComputerPlayer, but the boolean method that tells us that it's a computer will
 *  be set to false. This is important, for example, for knowing when to ask for data such as the square number and knowing
 *  when to generate it. In this case, the player will be asked for data since this boolean is false instead of true (not a
 *  computer).
 * 
 * 	Player Abstract Class:
 * 	We can make different kinds of players from this class. Every player has a name and mark, and we will use a parameterized
 * 	constructor to set these. Then, have setters and getters for the name and mark. This is important especially for getting the
 * 	so we can keep track of which player's turn it is.
 * 
 * 	WinBoard Class:
 * 	This is a 3x3 board that track the winner of each of the 8 smaller boards. This tracks and determines the winner for the the entire game.
 * 	This acts as a normal TTT board with the same winning rules. Each square in this board represents a board on the UTTT board (Board Class).
 * 
 *	Test:
 * 	Check ranges of input. Use all different combinations of players. Check that X wins. Check that O wins. Check that there's a tie.
 * 	Check when a board is won that it cannot be won again. Check when a board is full, the player that was sent to it can go to any board and square.
 */

import java.util.Scanner;

public class DriverMain {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);		// Scanner object
		
		// Player objects to be defined
		Player[] players = new Player[2];
		
		// Get user's choice for players
		int choice = 0;
		do {
			System.out.println("Choose Player 1:");
			System.out.println("1 - Human Player\n2 - Computer Player");
			choice = input.nextInt();		// Get user's choice and validate
		} while (choice <= 0 || choice >= 3);
		
		// Set to correct player type
		if (choice == 1)
			players[0] = new HumanPlayer("Player 1", "X");
		else
			players[0] = new ComputerPlayer("Player 1", "X");
		
		do {
			System.out.println("\nChoose Player 2:");
			System.out.println("1 - Human Player\n2 - Computer Player");
			choice = input.nextInt();		// Get user's choice and validate
		} while (choice <= 0 || choice >= 3);
		
		// Set to correct player type
		if (choice == 1)
			players[1] = new HumanPlayer("Player 2", "O");
		else
			players[1] = new ComputerPlayer("Player 2", "O");
		
		Game game = new Game();		// Start game
		game.setPlayers(players[0], players[1]);
		game.start();
		System.out.println("Program has ended");
	}
}