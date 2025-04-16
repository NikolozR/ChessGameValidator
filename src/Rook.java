import java.util.Objects;

public class Rook extends ChessPiece {
    private boolean hasMoved;

    public Rook(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
        this.hasMoved = false;
    }


    @Override
    public void move(ChessBoardSquare destinationSquare, boolean taking) {
        super.move(destinationSquare, taking);
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        if (Objects.equals(this.getPieceSquare().getCoordinates(), destinationSquare.getCoordinates())) {
            // incorrect, current square and destination square is the same
            return false;
        } else if (this.getPieceSquare().getFile() == destinationSquare.getFile()) {
            return this.canMoveVertically(destinationSquare);
        } else if (this.getPieceSquare().getRank() == destinationSquare.getRank()) {
            return this.canMoveHorizontally(destinationSquare);
        } else {
            // neither file nor rank is the same (Rook should move with 90 degree)
            return false;
        }
    }

    private boolean canMoveVertically(ChessBoardSquare destinationSquare) {
        int currentRank = this.getPieceSquare().getRank();
        int destinationRank = destinationSquare.getRank();
        int fileIndex = destinationSquare.getFileCharCode() - 97;
        for (int i = 8 - (Math.max(currentRank, destinationRank)); i < 8 - Math.min(currentRank, destinationRank); i++) {
            if (this.getChessboard().getBoardSquares()[i][fileIndex] != null) {
                // something was in a way of Rook
                return false;
            }
        }
        // for loop finished without any encountered piece, thus can move
        return true;
    }

    // todo make sure correct logic, currently copied version of vertical logic
    private boolean canMoveHorizontally(ChessBoardSquare destinationSquare) {
        int currentRank = this.getPieceSquare().getRank();
        int destinationRank = destinationSquare.getRank();
        int fileIndex = destinationSquare.getFileCharCode() - 97;
        for (int i = 8 - (Math.max(currentRank, destinationRank)); i < 8 - Math.min(currentRank, destinationRank); i++) {
            if (this.getChessboard().getBoardSquares()[i][fileIndex] != null) {
                // something was in a way of Rook
                return false;
            }
        }
        // for loop finished without any encountered piece, thus can move
        return true;
    }

}
