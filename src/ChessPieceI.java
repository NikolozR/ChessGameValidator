public interface ChessPieceI {
    void move(ChessBoardSquare destinationSquare, boolean taking);

    boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking);


}
