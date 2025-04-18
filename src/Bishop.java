import java.util.Objects;

public class Bishop extends ChessPiece {
    public Bishop(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        if (taking) return this.canTake(destinationSquare, this.getPieceColor()) && this.canMove(destinationSquare);
        else return destinationSquare.isSquareEmpty() && this.canMove(destinationSquare);
    }


    private boolean canMove(ChessBoardSquare destinationSquare) {
        if (Objects.equals(destinationSquare.getCoordinates(), this.getPieceSquare().getCoordinates())) return false;
        else {
            int currentRankIndex = this.getPieceSquare().getRank() - 1;
            int destinationRankIndex = destinationSquare.getRank() - 1;
            int currentFileIndex = this.getPieceSquare().getFileCharCode() - 97;
            int destinationFileIndex = destinationSquare.getFileCharCode() - 97;

            int rankDiff = Math.abs(destinationRankIndex - currentRankIndex);
            int fileDiff = Math.abs(destinationFileIndex - currentFileIndex);

            if (rankDiff != fileDiff) return false;
            int rankStep = currentRankIndex > destinationRankIndex ? -1 : 1;
            int fileStep = currentFileIndex > destinationFileIndex ? -1 : 1;

            int rank = currentRankIndex + rankStep;
            int file = currentFileIndex + fileStep;

            while (rank != destinationRankIndex && file != destinationFileIndex) {
                ChessBoardSquare intermediate = this.getChessboard().getBoardSquares()[rank][file];
                if (intermediate.getCurrentPiece() != null) {
                    return false;
                }
                rank += rankStep;
                file += fileStep;
            }

            return true;

        }
    }

    @Override
    public Bishop clone(ChessBoard chessBoard) {
        Bishop result = new Bishop(this.getPieceColor(), this.getPieceSquare(), chessBoard);
        result.setTaken(this.isTaken());
        return result;
    }

    public boolean canAttack(ChessBoardSquare startingSquare, ChessBoardSquare destinationSquare, ChessBoard chessBoard) {
        if (Objects.equals(destinationSquare.getCoordinates(), this.getPieceSquare().getCoordinates())) return false;
        else {
            int currentRankIndex = startingSquare.getRank() - 1;
            int destinationRankIndex = destinationSquare.getRank() - 1;
            int currentFileIndex = startingSquare.getFileCharCode() - 97;
            int destinationFileIndex = destinationSquare.getFileCharCode() - 97;

            int rankDiff = Math.abs(destinationRankIndex - currentRankIndex);
            int fileDiff = Math.abs(destinationFileIndex - currentFileIndex);

            if (rankDiff != fileDiff) return false;
            int rankStep = currentRankIndex > destinationRankIndex ? -1 : 1;
            int fileStep = currentFileIndex > destinationFileIndex ? -1 : 1;

            int rank = currentRankIndex + rankStep;
            int file = currentFileIndex + fileStep;

            while (rank != destinationRankIndex && file != destinationFileIndex) {
                ChessBoardSquare intermediate = chessBoard.getBoardSquares()[rank][file];
                if (intermediate.getCurrentPiece() != null) {
                    return false;
                }
                rank += rankStep;
                file += fileStep;
            }

            return true;

        }
    }
}
