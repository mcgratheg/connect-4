package mcgratheg;

import static org.junit.jupiter.api.Assertions.*;

import java.util.InputMismatchException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class Connect4Test {

	Colour[][] emptyBoard, horizontalWinLower, horizontalWinUpper, verticalWinLower, verticalWinUpper,
			diagonalPositiveWinLower, diagonalPositiveWinUpper, diagonalNegativeWinLower, diagonalNegativeWinUpper,
			drawBoard;
	String validInput;
	int validColumnLowerInt, validColumnUpperInt, invalidColumnLowerInt, invalidColumnUpperInt;
	Colour validColourRed, validColourYellow, invalidColour;
	boolean gameOver;

	@BeforeEach
	void setUp() throws Exception {
		validColumnLowerInt = 0;
		validColumnUpperInt = 6;
		invalidColumnLowerInt = -1;
		invalidColumnUpperInt = 8;

		validColourRed = Colour.R;
		invalidColour = Colour.e;

		emptyBoard = new Colour[6][7];
		for (int row = 0; row < emptyBoard.length; row++) {
			for (int col = 0; col < emptyBoard[0].length; col++) {
				emptyBoard[row][col] = Colour.e;
			}
		}
		
		drawBoard = new Colour[6][7];
		for (int row = 0; row < drawBoard.length; row++) {
			for (int col = 0; col < drawBoard[0].length; col++) {
				if (col%2 == 0 || col == 3) {
					if (row%2 == 0) {
						drawBoard[row][col] = validColourRed;
					} else if (row%2 == 1) {
						drawBoard[row][col] = validColourYellow;
					}
				} else if (col == 1 || col == 5) {
					if (row%2 == 0) {
						drawBoard[row][col] = validColourYellow;
					} else if (row%2 == 1) {
						drawBoard[row][col] = validColourRed;
					}
				}
			}
		}
		
		gameOver = false;
	}

	@Test
	void testChooseColumnValid() {
		// test takes user input, please enter '1' when running test
		assertEquals(0, Connect4.chooseColumn());
	}
	
	@Test
	void testChooseColumnInvalid() {
		// test for user input mismatch, please enter not an int
		Assertions.assertThrows(InputMismatchException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				Connect4.chooseColumn();
			}
		});
	}

	@Test
	void testPlacePieceValid() {
		Colour[][] testBoard = emptyBoard;
		testBoard[0][validColumnLowerInt] = validColourRed;
		assertEquals(testBoard, Connect4.placePiece(emptyBoard, validColumnLowerInt, validColourRed));
		
		Colour[][] testBoard2 = emptyBoard;
		testBoard2[0][validColumnUpperInt] = validColourRed;
		assertEquals(testBoard, Connect4.placePiece(emptyBoard, validColumnUpperInt, validColourRed));
	}

	@Test
	void testPlacePieceInvalid() {
		Assertions.assertThrows(IndexOutOfBoundsException.class, new Executable() {
			@Override
			public void execute() throws Throwable {
				Connect4.placePiece(emptyBoard, invalidColumnLowerInt, validColourRed);
				Connect4.placePiece(emptyBoard, invalidColumnUpperInt, validColourRed);
			}
		});
	}

	@Test
	void testCheckHorizontalTrue() {
		horizontalWinLower = emptyBoard;
		horizontalWinUpper = emptyBoard;
		for (int i = 0; i <= 3; i++) {
			horizontalWinLower[0][i] = validColourRed;
			horizontalWinUpper[0][(emptyBoard[0].length - 1) - i] = validColourRed;
		}
		assertEquals(true, Connect4.checkHorizontal(horizontalWinLower, gameOver));
		assertEquals(true, Connect4.checkHorizontal(horizontalWinUpper, gameOver));
	}
	
	@Test
	void testCheckHorizontalFalse() {
		assertEquals(false, Connect4.checkHorizontal(emptyBoard, gameOver));
		assertEquals(false, Connect4.checkHorizontal(drawBoard, gameOver));
	}

	@Test
	void testCheckVerticalTrue() {
		verticalWinLower = emptyBoard;
		verticalWinUpper = emptyBoard;
		for (int i = 0; i <=3; i++) {
			verticalWinLower[i][0] = validColourRed;
			verticalWinUpper[(emptyBoard.length - 1) - i][0] = validColourRed;
		}
		
		assertEquals(true, Connect4.checkVertical(verticalWinLower, gameOver));
		assertEquals(true, Connect4.checkVertical(verticalWinUpper, gameOver));
	}
	
	@Test
	void testCheckVerticalFalse() {
		assertEquals(false, Connect4.checkVertical(emptyBoard, gameOver));
		assertEquals(false, Connect4.checkVertical(drawBoard, gameOver));
	}

	@Test
	void testCheckDiagonalPositiveTrue() {
		diagonalPositiveWinLower = emptyBoard;
		diagonalPositiveWinUpper = emptyBoard;
		for (int i = 0; i<=3; i++) {
			diagonalPositiveWinLower[0 + i][0 + i] = validColourRed;
			diagonalPositiveWinUpper[(emptyBoard.length/2 - 1) + i][(emptyBoard[0].length/2) + i] = validColourRed;
		}
		
		assertEquals(true, Connect4.checkDiagonalPositive(diagonalPositiveWinLower, gameOver));
		assertEquals(true, Connect4.checkDiagonalPositive(diagonalPositiveWinUpper, gameOver));
	}
	
	@Test
	void testCheckDiagonalPositiveFalse() {
		assertEquals(false, Connect4.checkDiagonalPositive(emptyBoard, gameOver));
		assertEquals(false, Connect4.checkDiagonalPositive(drawBoard, gameOver));
	}

	@Test
	void testCheckDiagonalNegativeTrue() {
		diagonalNegativeWinLower = emptyBoard;
		diagonalNegativeWinUpper = emptyBoard;
		for (int i = 0; i<=3; i++) {
			diagonalNegativeWinLower[(emptyBoard.length - 1) - i][0 + i] = validColourRed;
			diagonalNegativeWinUpper[(emptyBoard.length - 1) - i][(emptyBoard[0].length/2) + i] = validColourRed;
		}
		
		assertEquals(true, Connect4.checkDiagonalNegative(diagonalNegativeWinLower, gameOver));
		assertEquals(true, Connect4.checkDiagonalNegative(diagonalNegativeWinUpper, gameOver));
	}
	
	@Test
	void testCheckDiagonalNegativeFalse() {
		assertEquals(false, Connect4.checkDiagonalNegative(emptyBoard, gameOver));
		assertEquals(false, Connect4.checkDiagonalNegative(drawBoard, gameOver));
	}

}
