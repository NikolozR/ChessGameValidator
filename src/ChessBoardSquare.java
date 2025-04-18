public class ChessBoardSquare {
    private ChessPiece currentPiece;
    private final String coordinates;
    private final ChessBoard chessBoard;


    public ChessBoardSquare(String coordinates, ChessBoard chessBoard) {
        this.coordinates = coordinates;
        this.currentPiece = null;
        this.chessBoard = chessBoard;
    }

    public void takesCurrentPiece(ChessPiece newCurrentPiece) {
        this.getCurrentPiece().setPieceSquare(null);
        this.getCurrentPiece().setTaken(true);
        if (this.getCurrentPiece().getPieceColor() == GameColor.WHITE) {
            this.chessBoard.addWhiteTooks(this.getCurrentPiece());
        } else {
            this.chessBoard.addBlackTooks(this.getCurrentPiece());
        }
        this.regularMove(newCurrentPiece);
    }

    public void regularMove(ChessPiece newCurrentPiece) {
        ChessBoardSquare old = newCurrentPiece.getPieceSquare();
        old.setCurrentPiece(null);
        this.chessBoard.getBoardSquares()[old.getRank() - 1][old.getFileCharCode() - 97] = old;
        this.setCurrentPiece(newCurrentPiece);
        newCurrentPiece.setPieceSquare(this);
    }


    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }

    public boolean isSquareEmpty() {
        return this.getCurrentPiece() == null;
    }

    public int getRank() {
        return Integer.parseInt(this.getCoordinates().substring(1));
    }

    public char getFile() {
        return this.getCoordinates().charAt(0);
    }

    public int getFileCharCode() {
        return this.getCoordinates().charAt(0);
    }

    public String getCoordinates() {
        return coordinates;
    }


    public void setCurrentPiece(ChessPiece currentPiece) {
        this.currentPiece = currentPiece;
    }
}
