package mcgratheg;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Enum holds list of states of board pieces, R = RED, Y = YELLOW, e = EMPTY
 * represented by single characters for clear display
 * 
 * @author eibhl
 * 
 */
enum Colour {
	R, Y, e

}

/**
 * Connect 4 game between 2 players
 * 
 * @author eibhl
 *
 */
public class Connect4 {
	public static Colour[][] board; // 2D array to represent board
	public static Colour[] player; // array of players by colour
	static Scanner scanner = new Scanner(System.in); // Scanner for user input

	/**
	 * Player 1 is asked which colour they wish to play and player array is set
	 * accordingly
	 * 
	 * @param scanner
	 * @param player
	 */
	public static void player1Selection(Colour[] player) {
		System.out.println("Player 1, please choose to play as either RED or YELLOW");
		String choice = scanner.nextLine();
		if (choice.equalsIgnoreCase("RED")) {
			player[0] = Colour.R;
			player[1] = Colour.Y;
		} else if (choice.equalsIgnoreCase("YELLOW")) {
			player[0] = Colour.Y;
			player[1] = Colour.R;
		} else {
			System.out.println("Error, please try again.");
			player1Selection(player);
		}

	}

	/**
	 * Player is asked for number between 1 and 7 for the column they wish to 'drop'
	 * their piece into
	 * 
	 * @param scanner
	 * @return column number
	 */
	public static int chooseColumn() {
		System.out.println("\nPlease choose a number between 1 and 7");
		int column = scanner.nextInt() - 1;
		return column;
	}

	/**
	 * Piece will be placed on the board in the next empty space in the column
	 * selected by the player. If no empty space exists in the column the player is
	 * asked to choose another column and the method is recalled.
	 * 
	 * @param board
	 * @param column
	 * @param player
	 * @return board with new piece added
	 */
	public static Colour[][] placePiece(Colour[][] board, int column, Colour player) {
		for (int row = 0; row < board.length; row++) {
			if (board[row][column] == Colour.e) {
				board[row][column] = player;
				break;
			} else if (row == board.length - 1 && board[row][column] != Colour.e) {
				System.out.println("Column is already full!");
				do {
					column = chooseColumn();
				} while (column < 0 || column > 6);
				return placePiece(board, column, player);
			}
		}

		return board;
	}

	/**
	 * Checks board for 4 pieces of same colour in a horizontal line
	 * 
	 * @param board
	 * @param gameOver
	 * @return gameOver boolean
	 */
	public static boolean checkHorizontal(Colour[][] board, boolean gameOver) {

		outerloop: for (int row = 0; row < board.length; row++) {
			for (int col = 0; col <= board[row].length / 2; col++) {
				if (board[row][col] != Colour.e) {
					for (int step = 1; step <= 3; step++) {
						if (board[row][col] == board[row][col + step]) {
							gameOver = true;
							continue;
						} else {
							gameOver = false;
							break;
						}
					}
				}
				if (gameOver)
					break outerloop;

			}
		}
		return gameOver;
	}

	/**
	 * Checks board for 4 pieces of same colour in a vertical line
	 * 
	 * @param board
	 * @param gameOver
	 * @return gameOver boolean
	 */
	public static boolean checkVertical(Colour[][] board, boolean gameOver) {

		outerloop: for (int col = 0; col < board[0].length; col++) {
			for (int row = 0; row <= (board.length / 2) - 1; row++) {
				if (board[row][col] != Colour.e) {
					for (int step = 1; step <= 3; step++) {
						if (board[row][col] == board[row + step][col]) {
							gameOver = true;
							continue;
						} else {
							gameOver = false;
							break;
						}
					}
				}
				if (gameOver)
					break outerloop;
			}
		}

		return gameOver;
	}

	/**
	 * Checks board for 4 pieces in a diagonal line pointing up towards the top
	 * right corner
	 * 
	 * @param board
	 * @param gameOver
	 * @return gameOver boolean
	 */
	public static boolean checkDiagonalPositive(Colour[][] board, boolean gameOver) {

		outerloop: for (int row = 0; row <= (board.length / 2) - 1; row++) {
			for (int col = 0; col <= board[0].length / 2; col++) {
				if (board[row][col] != Colour.e) {
					for (int step = 1; step <= 3; step++) {
						if (board[row][col] == board[row + step][col + step]) {
							gameOver = true;
							continue;
						} else {
							gameOver = false;
							break;
						}
					}
				}
				if (gameOver)
					break outerloop;
			}
		}

		return gameOver;
	}

	/**
	 * Checks board for 4 pieces in a diagonal line pointing down towards the bottom
	 * right corner
	 * 
	 * @param board
	 * @param gameOver
	 * @return gameOver boolean
	 */
	public static boolean checkDiagonalNegative(Colour[][] board, boolean gameOver) {

		outerloop: for (int row = board.length - 1; row >= board.length / 2; row--) {
			for (int col = 0; col <= board[0].length / 2; col++) {
				if (board[row][col] != Colour.e) {
					for (int step = 1; step <= 3; step++) {
						if (board[row][col] == board[row - step][col + step]) {
							gameOver = true;
							continue;
						} else {
							gameOver = false;
							break;
						}
					}
				}
				if (gameOver)
					break outerloop;
			}
		}

		return gameOver;
	}

	/**
	 * Main game method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		board = new Colour[6][7]; // Initialise board array
		player = new Colour[2]; // initialise player array
		boolean gameOver = false; // initialise win state
		int turn = 1, column = -1; // begin at turn 1

		try {
			// begin game with every board space empty
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					board[i][j] = Colour.e;
				}
			}

			player1Selection(player); // Player 1 selects colour
			Colour currentPlayer = player[0]; // current player set to Player 1

			// game loop runs until a player wins or board is filled
			do {
				do {
					column = chooseColumn(); // current player chooses a column
				} while (column < 0 || column > 6);
				placePiece(board, column, currentPlayer); // piece is placed on board

				// board is printed to screen
				for (int i = board.length - 1; i >= 0; i--) {
					System.out.println(Arrays.toString(board[i]));
				}

				// game must have gone at least 7 turns to have a possible win
				// checks each possible win type unless win is found true
				if (turn >= 7) {
					gameOver = checkHorizontal(board, gameOver);
					if (!gameOver)
						gameOver = checkVertical(board, gameOver);
					if (!gameOver)
						gameOver = checkDiagonalPositive(board, gameOver);
					if (!gameOver)
						gameOver = checkDiagonalNegative(board, gameOver);
				}

				// switch player
				if (currentPlayer == player[0])
					currentPlayer = player[1];
				else if (currentPlayer == player[1])
					currentPlayer = player[0];

				turn++; // increase turn count by 1
				if (turn > board.length * board[0].length)
					gameOver = true;
			} while (!gameOver); // game loop ends

			// if board was not filled i.e. game was not a draw, the last player to place a
			// piece is the winner
			// game result printed to screen
			if (turn <= board.length * board[0].length) {
				if (currentPlayer == Colour.Y)
					System.out.println("\nRed player wins.");
				else if (currentPlayer == Colour.R)
					System.out.println("\nYellow player wins");
			} else {
				System.out.println("\nDraw");
			}
		} catch (Exception e) {
			System.out.println("Error occured!");
		}
		System.out.println("Game ended.");

		scanner.close(); // close scanner
	}

}
