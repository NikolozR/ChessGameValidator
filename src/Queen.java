public class Queen extends ChessPiece {

    public Queen(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
    }

    public Queen(GameColor pieceColor, ChessBoard chessboard) {
        super(pieceColor, chessboard);
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        Rook rook = new Rook(this.getPieceColor(), this.getPieceSquare(), this.getChessboard());
        Bishop bishop = new Bishop(this.getPieceColor(), this.getPieceSquare(), this.getChessboard());
        return rook.isCorrectMove(destinationSquare, taking) || bishop.isCorrectMove(destinationSquare, taking);
    }

    public boolean canAttack(ChessBoardSquare startingSquare, ChessBoardSquare destinationSquare, ChessBoard chessBoard) {
        Rook rook = new Rook(this.getPieceColor(), this.getPieceSquare(), this.getChessboard());
        Bishop bishop = new Bishop(this.getPieceColor(), this.getPieceSquare(), this.getChessboard());
        return rook.canAttack(startingSquare, destinationSquare, chessBoard) || bishop.canAttack(startingSquare, destinationSquare, chessBoard);
    }

    @Override
    public Queen clone(ChessBoard chessBoard) {
        Queen result = new Queen(this.getPieceColor(), chessBoard);
        result.setTaken(this.isTaken());
        return result;
    }
}
