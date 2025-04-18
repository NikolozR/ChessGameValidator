package pieces;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;

public class ChessPiece implements ChessPieceI {
    private boolean taken;
    private ChessBoardSquare pieceSquare;
    private final GameColor pieceColor;
    private final ChessBoard chessboard;


    public ChessPiece(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        this.pieceColor = pieceColor;
        this.pieceSquare = pieceSquare;
        this.taken = false;
        this.chessboard = chessboard;
    }

    public ChessPiece(GameColor pieceColor, ChessBoard chessboard) {
        this.pieceColor = pieceColor;
        this.taken = false;
        this.chessboard = chessboard;
    }

    public ChessPiece clone(ChessBoard chessBoard) {
        ChessPiece result = new ChessPiece(this.pieceColor, this.pieceSquare, chessboard);
        result.setTaken(taken);
        return result;
    }

    public ChessBoard getChessboard() {
        return chessboard;
    }

    public GameColor getPieceColor() {
        return pieceColor;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public ChessBoardSquare getPieceSquare() {
        return pieceSquare;
    }

    public void setPieceSquare(ChessBoardSquare pieceSquare) {
        this.pieceSquare = pieceSquare;
    }

    @Override
    public void move(ChessBoardSquare destinationSquare, boolean taking) {
        if (this.isCorrectMove(destinationSquare, taking)) {
            this.getPieceSquare().setCurrentPiece(null);
            if (taking) {
                destinationSquare.takesCurrentPiece(this);
            } else {
                destinationSquare.regularMove(this);
            }
        }
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        System.out.println("DEBUGGER TO CHECK IF USED: INCORRECT");
        return false;

    }

    public boolean isValid(ChessBoardSquare destinationSquare) {
        if (destinationSquare == null) return false;
        int rank = Integer.parseInt(destinationSquare.getCoordinates().substring(1));
        int file = destinationSquare.getCoordinates().charAt(0) - 97;
        if (rank > 8 || rank < 0) return false;
        if (file < 0 || file > 7) return false;
        return true;
    }

    @Override
    public boolean canTake(ChessBoardSquare destinationSquare, GameColor currentColor) {
        // add that it is not pieces.King
        return !destinationSquare.isSquareEmpty() && destinationSquare.getCurrentPiece().getPieceColor() != currentColor;
    }
}
