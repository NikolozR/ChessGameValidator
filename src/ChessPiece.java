public class ChessPiece implements ChessPieceI {
    private boolean taken;
    private ChessBoardSquare pieceSquare;
    private final GameColor pieceColor;


    public ChessPiece(GameColor pieceColor) {
        this.pieceColor = pieceColor;
        this.taken = false;
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
