package tests;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Pawn;
import pieces.Rook;

import static org.junit.jupiter.api.Assertions.*;

public class RookTests {

    private ChessBoard chessBoard;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard(true);
    }

    @Test
    public void testRookHorizontalAndVerticalMoves() {
        Rook rook = new Rook(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(rook);

        assertTrue(rook.isCorrectMove(chessBoard.getBoardSquare("d1"), false), "Rook should move vertically down");
        assertTrue(rook.isCorrectMove(chessBoard.getBoardSquare("d8"), false), "Rook should move vertically up");
        assertTrue(rook.isCorrectMove(chessBoard.getBoardSquare("a4"), false), "Rook should move horizontally left");
        assertTrue(rook.isCorrectMove(chessBoard.getBoardSquare("h4"), false), "Rook should move horizontally right");
    }

    @Test
    public void testRookIllegalDiagonalMove() {
        Rook rook = new Rook(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(rook);

        assertFalse(rook.isCorrectMove(chessBoard.getBoardSquare("e5"), false), "Rook cannot move diagonally");
        assertFalse(rook.isCorrectMove(chessBoard.getBoardSquare("c5"), false), "Rook cannot move diagonally");
    }

    @Test
    public void testRookBlockedByOwnPiece() {
        Rook rook = new Rook(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(rook);

        ChessBoardSquare blockerSquare = chessBoard.getBoardSquare("d6");
        blockerSquare.setCurrentPiece(new Pawn(GameColor.WHITE, blockerSquare, chessBoard));

        assertFalse(rook.isCorrectMove(chessBoard.getBoardSquare("d7"), false), "Rook can't move past ally");
        assertFalse(rook.isCorrectMove(blockerSquare, false), "Rook can't capture ally");
    }

    @Test
    public void testRookCaptureEnemyPiece() {
        Rook rook = new Rook(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(rook);

        ChessBoardSquare enemySquare = chessBoard.getBoardSquare("d6");
        enemySquare.setCurrentPiece(new Pawn(GameColor.BLACK, enemySquare, chessBoard));

        assertTrue(rook.isCorrectMove(enemySquare, true), "Rook should capture enemy vertically");
    }

    @Test
    public void testRookCannotCaptureIfEmpty() {
        Rook rook = new Rook(GameColor.WHITE, chessBoard.getBoardSquare("d4"), chessBoard);
        chessBoard.getBoardSquare("d4").setCurrentPiece(rook);

        ChessBoardSquare target = chessBoard.getBoardSquare("d7");
        assertFalse(rook.isCorrectMove(target, true), "Shouldn't 'capture' empty square");
    }
}
