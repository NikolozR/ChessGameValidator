import java.util.Objects;

public class Rook extends ChessPiece {
    private boolean hasMoved;

    public Rook(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
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
        if (Objects.equals(this.getPieceSquare().getCoordinates(), destinationSquare.getCoordinates())) {
            // incorrect, current square and destination square is the same
            return false;
        } else if (this.getPieceSquare().getFile() == destinationSquare.getFile()) {
            // if taking, then canTake should be true with also moving true
            if (taking) {
                return this.canTake(destinationSquare, this.getPieceColor()) && this.canMoveVertically(destinationSquare);
            }
            // if not taking then destination should be empty
            else return destinationSquare.isSquareEmpty() && this.canMoveVertically(destinationSquare);
        } else if (this.getPieceSquare().getRank() == destinationSquare.getRank()) {
            if (taking)
                return this.canTake(destinationSquare, this.getPieceColor()) && this.canMoveHorizontally(destinationSquare);
            else return destinationSquare.isSquareEmpty() && this.canMoveHorizontally(destinationSquare);
        } else {
            // neither file nor rank is the same (Rook should move with 90 degree)
            return false;
        }
    }

    private boolean canMoveVertically(ChessBoardSquare destinationSquare) {
        int currentRankIndex = this.getPieceSquare().getRank() - 1;
        int destinationRankIndex = destinationSquare.getRank() - 1;
        int fileIndex = destinationSquare.getFileCharCode() - 97;
        for (int i = Math.min(currentRankIndex, destinationRankIndex); i < Math.max(currentRankIndex, destinationRankIndex); i++) {
            ChessBoardSquare currentSquare = this.getChessboard().getBoardSquares()[i][fileIndex];
            if (!currentSquare.isSquareEmpty() && !Objects.equals(currentSquare.getCoordinates(), this.getPieceSquare().getCoordinates())) {
                // something was in a way of Rook
                return false;
            }
        }
        // for loop finished without any encountered piece, thus can move
        return true;
    }

    private boolean canMoveHorizontally(ChessBoardSquare destinationSquare) {
        int rankIndex = destinationSquare.getRank() - 1;
        int currentFile = this.getPieceSquare().getFileCharCode() - 97;
        int destinationFile = destinationSquare.getFileCharCode() - 97;
        for (int i = Math.min(currentFile, destinationFile) + 1; i < Math.max(currentFile, destinationFile); i++) {
            ChessBoardSquare currentSquare = this.getChessboard().getBoardSquares()[rankIndex][i];
            if (!currentSquare.isSquareEmpty() && !Objects.equals(currentSquare.getCoordinates(), this.getPieceSquare().getCoordinates())) {
                // something was in a way of Rook
                return false;
            }
        }
        // for loop finished without any encountered piece, thus can move
        return true;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }
}
