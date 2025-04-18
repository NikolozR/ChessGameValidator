package tests;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Pawn;
import pieces.Queen;

import static org.junit.jupiter.api.Assertions.*;

public class QueenTests {

    private ChessBoard chessBoard;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard(true);
    }

    @Test
    public void testQueenVerticalHorizontalDiagonalMoves() {
        Queen queen = new Queen(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(queen);

        assertTrue(queen.isCorrectMove(chessBoard.getBoardSquare("d7"), false));
        assertTrue(queen.isCorrectMove(chessBoard.getBoardSquare("d1"), false));

        assertTrue(queen.isCorrectMove(chessBoard.getBoardSquare("a4"), false));
        assertTrue(queen.isCorrectMove(chessBoard.getBoardSquare("h4"), false));

        assertTrue(queen.isCorrectMove(chessBoard.getBoardSquare("g7"), false));
        assertTrue(queen.isCorrectMove(chessBoard.getBoardSquare("a1"), false));
    }

    @Test
    public void testQueenIllegalLShapedMove() {
        Queen queen = new Queen(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(queen);

        assertFalse(queen.isCorrectMove(chessBoard.getBoardSquare("e6"), false));
        assertFalse(queen.isCorrectMove(chessBoard.getBoardSquare("c6"), false));
    }

    @Test
    public void testQueenBlockedByOwnPiece() {
        Queen queen = new Queen(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(queen);

        ChessBoardSquare blocking = chessBoard.getBoardSquare("d6");
        blocking.setCurrentPiece(new Pawn(GameColor.WHITE, blocking, chessBoard));

        assertFalse(queen.isCorrectMove(chessBoard.getBoardSquare("d7"), false), "Should not move past own piece");
        assertFalse(queen.isCorrectMove(blocking, false), "Should not move into own piece");
    }

    @Test
    public void testQueenCapturesEnemy() {
        Queen queen = new Queen(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(queen);

        ChessBoardSquare target = chessBoard.getBoardSquare("g7");
        target.setCurrentPiece(new Pawn(GameColor.BLACK, target, chessBoard));

        assertTrue(queen.isCorrectMove(target, true), "Should capture enemy diagonally");
    }

    @Test
    public void testQueenCannotCaptureEmpty() {
        Queen queen = new Queen(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(queen);

        ChessBoardSquare target = chessBoard.getBoardSquare("g7");
        assertFalse(queen.isCorrectMove(target, true), "Can't capture empty square");
    }
}
