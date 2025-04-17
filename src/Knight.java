import java.util.Objects;

public class Knight extends ChessPiece {

    public Knight(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        if (taking) {
            return this.canTake(destinationSquare, this.getPieceColor()) && this.canMove(destinationSquare);
        } else return destinationSquare.isSquareEmpty() && this.canMove(destinationSquare);
    }

    private boolean canMove(ChessBoardSquare destinationSquare) {
        // not logical to move in the place where it is placed
        if (Objects.equals(this.getPieceSquare().getCoordinates(), destinationSquare.getCoordinates())) return false;
        else {
            int currentFile = this.getPieceSquare().getFileCharCode();
            int currentRank = this.getPieceSquare().getRank();
            int destinationFile = destinationSquare.getFileCharCode();
            int destinationRank = destinationSquare.getRank();
            return (Math.abs(destinationFile - currentFile) == 1 && Math.abs(currentRank - destinationRank) == 2)
                    || (Math.abs(destinationFile - currentFile) == 2 && Math.abs(currentRank - destinationRank) == 1);

        }
    }
}
