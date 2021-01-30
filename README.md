# ultimateTTT
An implementation of ultimate TTT in Java.

A spin-off of normal TTT. Ultimate TTT requires the players to win 3 boards in the same pattern of TTT. More info is given in the program.

I implemented the list of all possible moves for each board when playing on a board.

The code will start off with a menu at DriverMain, where you can choose each player to be a human or computer.

Please excuse the length of the video. I had to explain and test a lot.

The rules have been implemented correctly. For example, a board that is already won cannot be won by the other player. Also, when a board is full, you will not be stuck on that board forever. You will be allowed to pick any square (go to any board) as the rules state.

Assume that input will be integer. However, there is error checking. You cannot enter a number that isn't between 0 and 8 for the board number and square number, and you cannot take another player's square. There is also more of this to check for invalid input.

This uses a 2D array implementation. I felt that the logic was easier to understand overall by being able to access the "rows" and "columns." The board itself is actually a 9x9 board, but each 3x3 board is separated by using min and max boundaries to stay in bounds. It was quite fun and interesting to take this approach, and it actually worked extremely well. The idea behind this was to translate a given board number and square number into a mapped row and column.

The players (ComputerPlayer and HumanPlayer) have little implementation. This route was taken because an abstract boolean method isComputer was used between them to distinguish them apart. So, the ComputerPlayer has this set this set to true, while the HumanPlayer has it set to false. This made switching between the two extremely easy, with the program knowing which player was a computer or not to handle accordingly in the methods.

As a design a decision, I left out filling a won board with "*" as the sample output has. This is because this may be confusing. The numbers on the boad should stay because a player can still select an unused number to force the other player to the other board. By replacing the numbers with an *, the user no longer knows which number specifically is which.

I am overall proud of how it turned out. It is necessary to stick to the rules of the game and print everything out at the right time. I liked using OOP concepts such as encapsulation between the Board and the Game, as well as the abstraction of the player.

There is a strong relationship between the Game and the Board, Player, and WinBoard, hence the composition relationship. This because the Game provides the rows and columns that each of the other classes need to process the Game's information.
