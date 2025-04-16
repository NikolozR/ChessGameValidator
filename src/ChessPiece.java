public class ChessPiece implements ChessPieceI {
    private boolean taken;
    private ChessBoardSquare pieceSquare;
    private final GameColor pieceColor;
    private final ChessBoard chessboard;


    public ChessPiece(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        this.pieceColor = pieceColor;
        this.pieceSquare = pieceSquare;
        this.taken = false;
        this.chessboard = chessboard;
    }

    public ChessBoard getChessboard() {
        return chessboard;
    }

    public GameColor getPieceColor() {
        return pieceColor;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public ChessBoardSquare getPieceSquare() {
        return pieceSquare;
    }

    public void setPieceSquare(ChessBoardSquare pieceSquare) {
        this.pieceSquare = pieceSquare;
    }

    @Override
    public void move(ChessBoardSquare destinationSquare, boolean taking) {

    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        return false;
    }


}
