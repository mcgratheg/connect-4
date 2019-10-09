package mcgratheg;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

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
		validInput = "1";

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
		
		drawBoard = emptyBoard;
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
	void testChooseColumn() {
		InputStream in = new ByteArrayInputStream(validInput.getBytes());
		System.setIn(in);

		assertEquals(0, Connect4.chooseColumn());
	}

	@Test
	void testPlacePieceValid() {
		Colour[][] testBoard = emptyBoard;
		testBoard[0][validColumnLowerInt] = validColourRed;
		assertEquals(testBoard, Connect4.placePiece(emptyBoard, validColumnLowerInt, validColourRed));
		
		testBoard = emptyBoard;
		testBoard[0][validColumnUpperInt] = validColourRed;
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
	void testCheckVertical() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckDiagonalPositive() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckDiagonalNegative() {
		fail("Not yet implemented");
	}

}
