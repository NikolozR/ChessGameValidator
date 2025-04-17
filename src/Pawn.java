public class Pawn extends ChessPiece {
    private boolean hasMoved;

    public Pawn(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessBoard) {
        super(pieceColor, pieceSquare, chessBoard);
        this.hasMoved = false;
    }


    @Override
    public void move(ChessBoardSquare destinationSquare, boolean taking) {
        if (this.isCorrectMove(destinationSquare, taking)) {
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


    private boolean canWhitePawnTake(ChessBoardSquare destinationSquare) {
        return !destinationSquare.isSquareEmpty()
                && destinationSquare.getCurrentPiece().getPieceColor() == GameColor.BLACK
                && destinationSquare.getRank() - 1 == this.getPieceSquare().getRank()
                && (destinationSquare.getFileCharCode() - 1 == this.getPieceSquare().getFileCharCode()
                || destinationSquare.getFileCharCode() + 1 == this.getPieceSquare().getFileCharCode())
                && destinationSquare.getRank() - 1 == this.getPieceSquare().getRank();
    }

    private boolean canBlackPawnTake(ChessBoardSquare destinationSquare) {
        return !destinationSquare.isSquareEmpty()
                && destinationSquare.getCurrentPiece().getPieceColor() == GameColor.BLACK
                && destinationSquare.getRank() - 1 == this.getPieceSquare().getRank()
                && (destinationSquare.getFileCharCode() - 1 == this.getPieceSquare().getFileCharCode()
                || destinationSquare.getFileCharCode() + 1 == this.getPieceSquare().getFileCharCode())
                && destinationSquare.getRank() + 1 == this.getPieceSquare().getRank();
    }

    private boolean canWhitePawnMoveAfterFirstMove(ChessBoardSquare destinationSquare) {
        return destinationSquare.isSquareEmpty()
                && (destinationSquare.getFile() == this.getPieceSquare().getFile())
                && destinationSquare.getRank() - 1 == this.getPieceSquare().getRank();
    }

    private boolean canWhitePawnMoveFirstMove(ChessBoardSquare destinationSquare) {
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
        return destinationSquare.isSquareEmpty()
                && (destinationSquare.getFile() == this.getPieceSquare().getFile())
                &&
                (destinationSquare.getRank() + 1 == this.getPieceSquare().getRank()
                        || destinationSquare.getRank() + 2 == this.getPieceSquare().getRank());
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }


}
