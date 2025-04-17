public class Queen extends ChessPiece {

    public Queen(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        Rook rook = new Rook(this.getPieceColor(), this.getPieceSquare(), this.getChessboard());
        Bishop bishop = new Bishop(this.getPieceColor(), this.getPieceSquare(), this.getChessboard());
        return rook.isCorrectMove(destinationSquare, taking) || bishop.isCorrectMove(destinationSquare, taking);
    }
}
