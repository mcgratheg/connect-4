# Connect 4
Connect 4 game playable on the command line between 2 players.

Game board is represented by a 6x7 2D array as the standard size of a Connect 4 board.

## Technologies
* Java v8
* JUnit5

## To Run
Clone the repositiory, in cmd navigate to 'src' folder, compile 'mcgratheg' package, run 'mcgratheg/Connect4'.

## Notes
* Player 1 is asked which colour they wish to play, either "RED" or "YELLOW" (case is ignored in user entry).
* Board represents state of spaces as 'R' for RED, 'Y' for YELLOW, or 'e' if the space is empty.
* Counter counts how many turns have gone past.
* There must be at least 7 pieces on the board before a winning scenario can happen.
* After every piece is placed on the board, the program will check if there are any lines of 4 in any direction.
* Game loop runs until either a player has connected 4 in a line or the board is filled.
