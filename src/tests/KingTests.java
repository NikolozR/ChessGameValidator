package tests;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.King;
import pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class KingTests {

    private ChessBoard chessBoard;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard(true);
    }

    @Test
    public void testKingLegalMoves() {
        King king = new King(GameColor.WHITE, chessBoard.getBoardSquare("e4"), chessBoard);
        chessBoard.getBoardSquare("e4").setCurrentPiece(king);

        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("e5"), false));
        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("f5"), false));
        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("f4"), false));
        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("f3"), false));
        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("e3"), false));
        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("d3"), false));
        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("d4"), false));
        assertTrue(king.isCorrectMove(chessBoard.getBoardSquare("d5"), false));
    }

    @Test
    public void testKingIllegalMoveTooFar() {
        King king = new King(GameColor.WHITE, chessBoard.getBoardSquare("e4"), chessBoard);
        chessBoard.getBoardSquare("e4").setCurrentPiece(king);

        assertFalse(king.isCorrectMove(chessBoard.getBoardSquare("e6"), false), "King cannot move two squares");
        assertFalse(king.isCorrectMove(chessBoard.getBoardSquare("g4"), false), "King cannot move horizontally more than one");
    }

    @Test
    public void testKingCapturesEnemy() {
        ChessBoardSquare center = chessBoard.getBoardSquare("e4");
        King king = new King(GameColor.WHITE, center, chessBoard);
        center.setCurrentPiece(king);

        ChessBoardSquare target = chessBoard.getBoardSquare("d5");
        target.setCurrentPiece(new Pawn(GameColor.BLACK, target, chessBoard));

        assertTrue(king.isCorrectMove(target, true), "King should capture diagonally");
    }

    @Test
    public void testKingCannotCaptureOwnPiece() {
        ChessBoardSquare center = chessBoard.getBoardSquare("e4");
        King king = new King(GameColor.WHITE, center, chessBoard);
        center.setCurrentPiece(king);

        ChessBoardSquare target = chessBoard.getBoardSquare("d5");
        target.setCurrentPiece(new Pawn(GameColor.WHITE, target, chessBoard));

        assertFalse(king.isCorrectMove(target, true), "King cannot capture own piece");
    }

    @Test
    public void testKingCannotCaptureEmptyWithCaptureFlag() {
        ChessBoardSquare center = chessBoard.getBoardSquare("e4");
        King king = new King(GameColor.WHITE, center, chessBoard);
        center.setCurrentPiece(king);

        assertFalse(king.isCorrectMove(chessBoard.getBoardSquare("d5"), true), "King cannot 'capture' an empty square");
    }
}
