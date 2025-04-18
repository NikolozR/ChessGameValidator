package tests;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pieces.Bishop;
import pieces.Pawn;

import static org.junit.jupiter.api.Assertions.*;

public class BishopTests {

    private ChessBoard chessBoard;

    @BeforeEach
    public void setup() {
        chessBoard = new ChessBoard(true);
    }

    @Test
    public void testBishopMoveDiagonally() {
        ChessBoardSquare square = chessBoard.getBoardSquare("c1");
        Bishop bishop = new Bishop(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(bishop);

        assertTrue(bishop.isCorrectMove(chessBoard.getBoardSquare("e3"), false), "Bishop should be able to move diagonally to e3");
        assertTrue(bishop.isCorrectMove(chessBoard.getBoardSquare("a3"), false), "Bishop should be able to move diagonally to a3");
    }

    @Test
    public void testBishopBlockedMove() {
        ChessBoardSquare square = chessBoard.getBoardSquare("c1");
        Bishop bishop = new Bishop(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(bishop);

        // Block with a white pawn at d2
        ChessBoardSquare blockingSquare = chessBoard.getBoardSquare("d2");
        blockingSquare.setCurrentPiece(new Pawn(GameColor.WHITE, blockingSquare, chessBoard));

        assertFalse(bishop.isCorrectMove(chessBoard.getBoardSquare("e3"), false), "Bishop should not be able to move through another piece");
    }

    @Test
    public void testBishopCapture() {
        ChessBoardSquare square = chessBoard.getBoardSquare("c1");
        Bishop bishop = new Bishop(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(bishop);

        ChessBoardSquare targetSquare = chessBoard.getBoardSquare("e3");
        targetSquare.setCurrentPiece(new Pawn(GameColor.BLACK, targetSquare, chessBoard));

        assertTrue(bishop.isCorrectMove(chessBoard.getBoardSquare("e3"), true), "Bishop should be able to capture an enemy piece diagonally");
    }

    @Test
    public void testBishopIllegalMove() {
        ChessBoardSquare square = chessBoard.getBoardSquare("c1");
        Bishop bishop = new Bishop(GameColor.WHITE, square, chessBoard);
        square.setCurrentPiece(bishop);

        assertFalse(bishop.isCorrectMove(chessBoard.getBoardSquare("c2"), false), "Bishop should not move vertically");
        assertFalse(bishop.isCorrectMove(chessBoard.getBoardSquare("d1"), false), "Bishop should not move in non-diagonal patterns");
        assertFalse(bishop.isCorrectMove(chessBoard.getBoardSquare("d9"), false), "Bishop should not move out of board");
    }
}
