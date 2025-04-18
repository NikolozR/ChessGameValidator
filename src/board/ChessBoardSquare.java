package board;

import game.GameColor;
import pieces.*;

public class ChessBoardSquare {
    private ChessPiece currentPiece;
    private final String coordinates;
    private ChessBoard chessBoard;


    public ChessBoardSquare(String coordinates, ChessBoard chessBoard) {
        this.coordinates = coordinates;
        this.currentPiece = null;
        this.chessBoard = chessBoard;
    }

    public ChessBoardSquare(String coordinates) {
        this.coordinates = coordinates;
        this.currentPiece = null;
    }


    public void takesCurrentPiece(ChessPiece newCurrentPiece) {
        this.getCurrentPiece().setPieceSquare(null);
        this.getCurrentPiece().setTaken(true);
        if (this.getCurrentPiece().getPieceColor() == GameColor.WHITE) {
            this.chessBoard.addWhiteTooks(this.getCurrentPiece());
        } else {
            this.chessBoard.addBlackTooks(this.getCurrentPiece());
        }
        this.regularMove(newCurrentPiece);
    }

    public void takesCurrentPiece(ChessPiece newCurrentPiece, boolean wasEP, GameColor capturerColor) {
        int rank = this.getRank() - 1;
        int file = this.getFileCharCode() - 97;
        ChessBoardSquare toKill;
        if (capturerColor == GameColor.BLACK) {
            toKill = this.chessBoard.getBoardSquares()[rank + 1][file];
        } else {
            toKill = this.chessBoard.getBoardSquares()[rank - 1][file];
        }
        toKill.getCurrentPiece().setPieceSquare(null);
        toKill.getCurrentPiece().setTaken(true);
        toKill.setCurrentPiece(null);
        this.regularMove(newCurrentPiece);
    }

    public void regularMove(ChessPiece newCurrentPiece) {
        newCurrentPiece.getPieceSquare().setCurrentPiece(null);
        this.setCurrentPiece(newCurrentPiece);
        newCurrentPiece.setPieceSquare(this);
    }

    public void promote(Pawn pawn, ChessPiece newPiece) {
        if (this.isSquareEmpty()) {
            this.setCurrentPiece(newPiece);
        } else {
            this.getCurrentPiece().setPieceSquare(null);
            this.getCurrentPiece().setTaken(true);
            this.setCurrentPiece(newPiece);
        }
        if (newPiece instanceof Rook) {
            (newPiece.getPieceColor() == GameColor.WHITE ? this.chessBoard.getWhiteRooks() : this.chessBoard.getBlackRooks()).add((Rook) newPiece);
        } else if (newPiece instanceof Knight) {
            (newPiece.getPieceColor() == GameColor.WHITE ? this.chessBoard.getWhiteKnights() : this.chessBoard.getBlackKnights()).add((Knight) newPiece);
        } else if (newPiece instanceof Bishop) {
            (newPiece.getPieceColor() == GameColor.WHITE ? this.chessBoard.getWhiteBishops() : this.chessBoard.getBlackBishops()).add((Bishop) newPiece);
        } else if (newPiece instanceof Queen) {
            (newPiece.getPieceColor() == GameColor.WHITE ? this.chessBoard.getWhiteQueens() : this.chessBoard.getBlackQueens()).add((Queen) newPiece);
        }
        newPiece.setPieceSquare(this);
        pawn.setTaken(true);
        pawn.setPieceSquare(null);
    }


    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }

    public boolean isSquareEmpty() {
        return this.getCurrentPiece() == null;
    }

    public int getRank() {
        return Integer.parseInt(this.getCoordinates().substring(1));
    }

    public char getFile() {
        return this.getCoordinates().charAt(0);
    }

    public int getFileCharCode() {
        return this.getCoordinates().charAt(0);
    }

    public String getCoordinates() {
        return coordinates;
    }


    public void setCurrentPiece(ChessPiece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }
}
