import java.util.ArrayList;

public class ChessBoard {
    private final ChessBoardSquare[][] boardSquares;
    private ArrayList<ChessPieceI> whitesTook;
    private ArrayList<ChessPieceI> blacksTook;

    public ChessBoard() {
        boardSquares = new ChessBoardSquare[8][8];
        for (int i = 0; i < boardSquares.length; i++) {
            for (int k = 0; k < boardSquares[0].length; k++) {
                int rank = 8 - i;
                char file = (char) (k + 97);
                ChessBoardSquare cbs = new ChessBoardSquare(file + "" + rank, this);
                if (rank == 1 || rank == 8) {
                    // whites or blacks depends on rank
                    if (file == 'a' || file == 'h') {
                        // cbs.setCurrentPiece(new Rook)
                    } else if (file == 'b' || file == 'g') {
                        // knight
                    } else if (file == 'c' || file == 'f') {
                        // bishop
                    } else if (file == 'd') {
                        // queen
                    } else {
                        // king
                    }
                } else if (rank == 2) {
                    // white pawns no matter what is file
                    cbs.setCurrentPiece(new Pawn(GameColor.WHITE, cbs, this));
                } else if (rank == 7) {
                    // black pawns no matter what is file
                    cbs.setCurrentPiece(new Pawn(GameColor.BLACK, cbs, this));
                }
                boardSquares[i][k] = cbs;
            }
        }
    }

    public ChessBoardSquare[][] getBoardSquares() {
        return boardSquares;
    }

    public void addWhiteTooks(ChessPiece whiteTook) {
        this.whitesTook.add(whiteTook);
    }

    public void addWBlackTooks(ChessPiece blackTook) {
        this.blacksTook.add(blackTook);
    }
}
