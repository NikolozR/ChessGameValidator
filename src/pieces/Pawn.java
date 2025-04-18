package pieces;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;

public class Pawn extends ChessPiece {
    private boolean hasMoved;

    public Pawn(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessBoard) {
        super(pieceColor, pieceSquare, chessBoard);
        this.hasMoved = false;
    }

    public Pawn(GameColor pieceColor, ChessBoard chessBoard) {
        super(pieceColor, chessBoard);
        this.hasMoved = false;
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
            this.setHasMoved(true);
        }
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        if (!isValid(destinationSquare)) return false;
        if (!taking) {
            if (this.hasMoved) {
                if (this.getPieceColor() == GameColor.WHITE) {
                    return this.canWhitePawnMoveAfterFirstMove(destinationSquare);
                } else {
                    return this.canBlackPawnMoveAfterFirstMove(destinationSquare);
                }
            } else {
                if (this.getPieceColor() == GameColor.WHITE) {
                    return this.canWhitePawnMoveFirstMove(destinationSquare);
                } else {
                    return this.canBlackPawnMoveFirstMove(destinationSquare);
                }
            }
        } else {
            if (this.getPieceColor() == GameColor.WHITE) {
                return this.canWhitePawnTake(destinationSquare);
            } else {
                return this.canBlackPawnTake(destinationSquare);
            }
        }
    }

    public boolean canAttack(ChessBoardSquare startingSquare, ChessBoardSquare destinationSquare, ChessBoard chessBoard) {
        return Math.abs(destinationSquare.getFileCharCode() - startingSquare.getFileCharCode()) == 1
                && destinationSquare.getRank() + (this.getPieceColor() == GameColor.WHITE ? -1 : 1) == startingSquare.getRank();
    }

    public void promote(ChessBoardSquare destinationSquare, boolean taking, char promoteResult) {
        if (canPromote(destinationSquare, taking)) {
            ChessPiece newChessPiece = new Queen(this.getPieceColor(), this.getChessboard());
            if (promoteResult == 'R') newChessPiece = new Rook(this.getPieceColor(), this.getChessboard());
            else if (promoteResult == 'N') newChessPiece = new Knight(this.getPieceColor(), this.getChessboard());
            else if (promoteResult == 'B') newChessPiece = new Bishop(this.getPieceColor(), this.getChessboard());
            destinationSquare.promote(this, newChessPiece);
        }
    }

    public boolean canPromote(ChessBoardSquare destinationSquare, boolean taking) {
        if (destinationSquare.getRank() != 8 && destinationSquare.getRank() != 1) return false;
        else return this.isCorrectMove(destinationSquare, taking);
    }

    private boolean canWhitePawnTake(ChessBoardSquare destinationSquare) {
        return !destinationSquare.isSquareEmpty()
                && destinationSquare.getCurrentPiece().getPieceColor() == GameColor.BLACK
                && destinationSquare.getRank() - 1 == this.getPieceSquare().getRank()
                && Math.abs(destinationSquare.getFileCharCode() - this.getPieceSquare().getFileCharCode()) == 1;
    }

    private boolean canBlackPawnTake(ChessBoardSquare destinationSquare) {
        return !destinationSquare.isSquareEmpty()
                && destinationSquare.getCurrentPiece().getPieceColor() == GameColor.WHITE
                && destinationSquare.getRank() + 1 == this.getPieceSquare().getRank()
                && Math.abs(destinationSquare.getFileCharCode() - this.getPieceSquare().getFileCharCode()) == 1;
    }

    private boolean canWhitePawnMoveAfterFirstMove(ChessBoardSquare destinationSquare) {
        return destinationSquare.isSquareEmpty()
                && (destinationSquare.getFile() == this.getPieceSquare().getFile())
                && destinationSquare.getRank() - 1 == this.getPieceSquare().getRank();
    }

    private boolean canWhitePawnMoveFirstMove(ChessBoardSquare destinationSquare) {
        if (destinationSquare.getRank() - 2 == this.getPieceSquare().getRank()) {
            if (!this.getChessboard().getBoardSquares()[destinationSquare.getRank() - 2][this.getPieceSquare().getFileCharCode() - 97].isSquareEmpty())
                return false;
        }
        return destinationSquare.isSquareEmpty()
                && (destinationSquare.getFile() == this.getPieceSquare().getFile())
                &&
                (destinationSquare.getRank() - 1 == this.getPieceSquare().getRank()
                        || destinationSquare.getRank() - 2 == this.getPieceSquare().getRank());
    }

    private boolean canBlackPawnMoveAfterFirstMove(ChessBoardSquare destinationSquare) {
        return destinationSquare.isSquareEmpty()
                && (destinationSquare.getFile() == this.getPieceSquare().getFile())
                && destinationSquare.getRank() + 1 == this.getPieceSquare().getRank();
    }

    private boolean canBlackPawnMoveFirstMove(ChessBoardSquare destinationSquare) {
        if (destinationSquare.getRank() + 2 == this.getPieceSquare().getRank()) {
            if (!this.getChessboard().getBoardSquares()[destinationSquare.getRank()][this.getPieceSquare().getFileCharCode() - 97].isSquareEmpty())
                return false;
        }
        return destinationSquare.isSquareEmpty()
                && (destinationSquare.getFile() == this.getPieceSquare().getFile())
                &&
                (destinationSquare.getRank() + 1 == this.getPieceSquare().getRank()
                        || destinationSquare.getRank() + 2 == this.getPieceSquare().getRank());
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public Pawn clone(ChessBoard chessBoard) {
        Pawn result = new Pawn(this.getPieceColor(), chessBoard);
        result.setTaken(this.isTaken());
        return result;
    }

}
