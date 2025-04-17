public class King extends ChessPiece {
    public King(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
    }


    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        return super.isCorrectMove(destinationSquare, taking);
    }
}
