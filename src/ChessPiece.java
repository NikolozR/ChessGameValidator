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

    public ChessPiece(GameColor pieceColor, ChessBoard chessboard) {
        this.pieceColor = pieceColor;
        this.taken = false;
        this.chessboard = chessboard;
    }

    public ChessPiece clone(ChessBoard chessBoard) {
        ChessPiece result = new ChessPiece(this.pieceColor, this.pieceSquare, chessboard);
        result.setTaken(taken);
        return result;
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
        if (this.isCorrectMove(destinationSquare, taking)) {
            this.getPieceSquare().setCurrentPiece(null);
            if (taking) {
                destinationSquare.takesCurrentPiece(this);
            } else {
                destinationSquare.regularMove(this);
            }
        }
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {
        System.out.println("DEBUGGER TO CHECK IF USED: INCORRECT");
        return false;
    }

    @Override
    public boolean canTake(ChessBoardSquare destinationSquare, GameColor currentColor) {
        // add that it is not King
        return !destinationSquare.isSquareEmpty() && destinationSquare.getCurrentPiece().getPieceColor() != currentColor;
    }
}
