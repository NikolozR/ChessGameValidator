import java.util.ArrayList;

public class ChessBoard {
    private ChessBoardSquare[][] boardSquares;
    private ArrayList<ChessPieceI> whitesTook;
    private ArrayList<ChessPieceI> blacksTook;

    public ChessBoard() {
        boardSquares = new ChessBoardSquare[8][8];
        for (int i = 0; i < boardSquares.length; i++) {
            for (int k = 0; k < boardSquares[0].length; k++) {
                int rank = 8 - i;
                char field = (char) (k + 61);
                ChessBoardSquare cbs = new ChessBoardSquare(field + "" + rank);
            }
        }
    }

    public ChessBoardSquare[][] getBoardSquares() {
        return boardSquares;
    }
}
