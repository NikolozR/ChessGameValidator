package tests;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Knight;
import pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class KnightTests {

    private ChessBoard chessBoard;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard(true);
    }

    @Test
    public void testKnightLegalMoves() {
        Knight knight = new Knight(GameColor.WHITE, chessBoard.getBoardSquare("e4"), chessBoard);
        chessBoard.getBoardSquare("e4").setCurrentPiece(knight);

        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("d6"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("f6"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("g5"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("g3"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("f2"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("d2"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("c3"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("c5"), false));
    }

    @Test
    public void testKnightIllegalMoves() {
        Knight knight = new Knight(GameColor.WHITE, chessBoard.getBoardSquare("e4"), chessBoard);
        chessBoard.getBoardSquare("e4").setCurrentPiece(knight);

        assertFalse(knight.isCorrectMove(chessBoard.getBoardSquare("e5"), false));
        assertFalse(knight.isCorrectMove(chessBoard.getBoardSquare("f4"), false));
        assertFalse(knight.isCorrectMove(chessBoard.getBoardSquare("d4"), false));
        assertFalse(knight.isCorrectMove(chessBoard.getBoardSquare("e6"), false));
        assertFalse(knight.isCorrectMove(chessBoard.getBoardSquare("e2"), false));
    }

    @Test
    public void testKnightJumpingOverPieces() {
        ChessBoardSquare center = chessBoard.getBoardSquare("e4");
        Knight knight = new Knight(GameColor.WHITE, center, chessBoard);
        center.setCurrentPiece(knight);

        chessBoard.getBoardSquare("e5").setCurrentPiece(new Pawn(GameColor.WHITE, chessBoard.getBoardSquare("e5"), chessBoard));
        chessBoard.getBoardSquare("e3").setCurrentPiece(new Pawn(GameColor.WHITE, chessBoard.getBoardSquare("e3"), chessBoard));
        chessBoard.getBoardSquare("d4").setCurrentPiece(new Pawn(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard));
        chessBoard.getBoardSquare("f4").setCurrentPiece(new Pawn(GameColor.WHITE, chessBoard.getBoardSquare("f4"), chessBoard));

        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("d6"), false));
        assertTrue(knight.isCorrectMove(chessBoard.getBoardSquare("f6"), false));
    }

    @Test
    public void testKnightCapturesEnemy() {
        ChessBoardSquare start = chessBoard.getBoardSquare("e4");
        Knight knight = new Knight(GameColor.WHITE, start, chessBoard);
        start.setCurrentPiece(knight);

        ChessBoardSquare target = chessBoard.getBoardSquare("d6");
        target.setCurrentPiece(new Pawn(GameColor.BLACK, target, chessBoard));

        assertTrue(knight.isCorrectMove(target, true), "Knight should capture enemy in L-move");
    }

    @Test
    public void testKnightCannotCaptureEmptyWithCaptureFlag() {
        ChessBoardSquare start = chessBoard.getBoardSquare("e4");
        Knight knight = new Knight(GameColor.WHITE, start, chessBoard);
        start.setCurrentPiece(knight);

        ChessBoardSquare target = chessBoard.getBoardSquare("d6");

        assertFalse(knight.isCorrectMove(target, true), "Should not capture empty square");
    }

    @Test
    public void testKnightCannotCaptureOwnPiece() {
        ChessBoardSquare start = chessBoard.getBoardSquare("e4");
        Knight knight = new Knight(GameColor.WHITE, start, chessBoard);
        start.setCurrentPiece(knight);

        ChessBoardSquare target = chessBoard.getBoardSquare("d6");
        target.setCurrentPiece(new Pawn(GameColor.WHITE, target, chessBoard));

        assertFalse(knight.isCorrectMove(target, true), "Cannot capture own piece");
    }
}
