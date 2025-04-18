package tests;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class PawnTests {

    private ChessBoard chessBoard;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard(true);
    }

    @Test
    public void testWhitePawnInitialDoubleMove() {
        ChessBoardSquare square = chessBoard.getBoardSquare("e2");
        Pawn pawn = new Pawn(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(pawn);

        assertTrue(pawn.isCorrectMove(chessBoard.getBoardSquare("e3"), false), "White pawn should be able to move one step forward");
        assertTrue(pawn.isCorrectMove(chessBoard.getBoardSquare("e4"), false), "White pawn should be able to move two steps forward from starting position");
    }

    @Test
    public void testPawnDoubleMoveAfterMoved() {
        ChessBoardSquare square = chessBoard.getBoardSquare("e2");
        Pawn pawn = new Pawn(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(pawn);

        pawn.setHasMoved(true);

        assertFalse(pawn.isCorrectMove(chessBoard.getBoardSquare("e4"), false), "Pawn shouldn't double-move after moving once");
    }


    @Test
    public void testWhitePawnBlockedMove() {
        ChessBoardSquare square = chessBoard.getBoardSquare("e2");
        Pawn pawn = new Pawn(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(pawn);

        ChessBoardSquare blockingSquare = chessBoard.getBoardSquare("e3");
        blockingSquare.setCurrentPiece(new Pawn(GameColor.WHITE, blockingSquare, chessBoard));

        assertFalse(pawn.isCorrectMove(chessBoard.getBoardSquare("e3"), false), "Pawn should not be able to move forward if blocked");
    }

    @Test
    public void testWhitePawnCaptureDiagonal() {
        ChessBoardSquare square = chessBoard.getBoardSquare("e4");
        Pawn pawn = new Pawn(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(pawn);

        ChessBoardSquare targetSquare = chessBoard.getBoardSquare("d5");
        targetSquare.setCurrentPiece(new Pawn(GameColor.BLACK, targetSquare, chessBoard));

        assertTrue(pawn.isCorrectMove(chessBoard.getBoardSquare("d5"), true), "Pawn should be able to capture diagonally");
    }

    @Test
    public void testBlackPawnInitialDoubleMove() {
        ChessBoardSquare square = chessBoard.getBoardSquare("e7");
        Pawn pawn = new Pawn(GameColor.BLACK, square, chessBoard);
        square.setCurrentPiece(pawn);

        assertTrue(pawn.isCorrectMove(chessBoard.getBoardSquare("e6"), false), "Black pawn should be able to move one step forward");
        assertTrue(pawn.isCorrectMove(chessBoard.getBoardSquare("e5"), false), "Black pawn should be able to move two steps forward from starting position");
    }

    @Test
    public void testPawnIllegalBackwardMove() {
        ChessBoardSquare square = chessBoard.getBoardSquare("e4");
        Pawn pawn = new Pawn(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(pawn);

        assertFalse(pawn.isCorrectMove(chessBoard.getBoardSquare("e3"), false), "White pawn should not move backward");
    }
}
