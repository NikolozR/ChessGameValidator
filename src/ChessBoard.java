import java.util.ArrayList;

public class ChessBoard {
    private final ChessBoardSquare[][] boardSquares;
    private final ArrayList<ChessPiece> blacksTook = new ArrayList<>();
    private final ArrayList<ChessPiece> whitesTook = new ArrayList<>();
    private final ArrayList<Bishop> whiteBishops = new ArrayList<>();
    private final ArrayList<Bishop> blackBishops = new ArrayList<>();
    private final ArrayList<Rook> whiteRooks = new ArrayList<>();
    private final ArrayList<Rook> blackRooks = new ArrayList<>();
    private final ArrayList<Pawn> whitePawns = new ArrayList<>();
    private final ArrayList<Pawn> blackPawns = new ArrayList<>();
    private final ArrayList<Knight> whiteKnights = new ArrayList<>();
    private final ArrayList<Knight> blackKnights = new ArrayList<>();
    private Queen whiteQueen;
    private Queen blackQueen;


    public ChessBoard() {
        boardSquares = new ChessBoardSquare[8][8];
        for (int i = 0; i < boardSquares.length; i++) {
            for (int k = 0; k < boardSquares[0].length; k++) {
                int rank = i + 1;
                char file = (char) (k + 97);
                ChessBoardSquare cbs = new ChessBoardSquare(file + "" + rank, this);
                if (rank == 1 || rank == 8) {
                    // if rank is 1 --> white piece, if not then rank == 8 so black piece
                    if (file == 'a' || file == 'h') {
                        Rook rook = new Rook(rank == 1 ? GameColor.WHITE : GameColor.BLACK, cbs, this);
                        if (rank == 1) this.whiteRooks.add(rook);
                        if (rank == 8) this.blackRooks.add(rook);
                        cbs.setCurrentPiece(rook);
                    } else if (file == 'b' || file == 'g') {
                        Knight knight = new Knight(rank == 1 ? GameColor.WHITE : GameColor.BLACK, cbs, this);
                        if (rank == 1) this.whiteKnights.add(knight);
                        if (rank == 8) this.blackKnights.add(knight);
                        cbs.setCurrentPiece(knight);
                    } else if (file == 'c' || file == 'f') {
                        Bishop bishop = new Bishop(rank == 1 ? GameColor.WHITE : GameColor.BLACK, cbs, this);
                        if (rank == 1) this.whiteBishops.add(bishop);
                        if (rank == 8) this.blackBishops.add(bishop);
                        cbs.setCurrentPiece(bishop);
                    } else if (file == 'd') {
                        Queen queen = new Queen(rank == 1 ? GameColor.WHITE : GameColor.BLACK, cbs, this);
                        if (rank == 1) this.setWhiteQueen(queen);
                        if (rank == 8) this.setBlackQueen(queen);
                        cbs.setCurrentPiece(queen);
                    } else {
                        // king
                    }
                } else if (rank == 2) {
                    // white pawns no matter what is file
                    Pawn whitePawn = new Pawn(GameColor.WHITE, cbs, this);
                    this.whitePawns.add(whitePawn);
                    cbs.setCurrentPiece(whitePawn);
                } else if (rank == 7) {
                    // black pawns no matter what is file
                    Pawn blackPawn = new Pawn(GameColor.BLACK, cbs, this);
                    this.blackPawns.add(blackPawn);
                    cbs.setCurrentPiece(blackPawn);
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

    public void addBlackTooks(ChessPiece blackTook) {
        this.blacksTook.add(blackTook);
    }

    public boolean playMove(String move, GameColor player) {
        ChessBoardSquare destinationSquare = new ChessBoardSquare(move, this);
        if (move.length() == 2) {
            int pawnMoveCounts = 0;
            Pawn currentPawn = null;
            for (Pawn pawn : player == GameColor.WHITE ? this.getWhitePawns() : this.getBlackPawns()) {
                boolean moved = pawn.isCorrectMove(destinationSquare, false);
                if (moved) {
                    pawnMoveCounts++;
                    currentPawn = pawn;
                }
            }
            if (pawnMoveCounts == 1) {
                currentPawn.move(destinationSquare, false);
                return true;
            }
        } else if (move.length() == 3) {

        }
        return false;
    }


    public Queen getBlackQueen() {
        return blackQueen;
    }

    public ArrayList<ChessPiece> getWhitesTook() {
        return whitesTook;
    }

    public ArrayList<ChessPiece> getBlacksTook() {
        return blacksTook;
    }

    public ArrayList<Bishop> getWhiteBishops() {
        return whiteBishops;
    }

    public ArrayList<Knight> getBlackKnights() {
        return blackKnights;
    }

    public ArrayList<Knight> getWhiteKnights() {
        return whiteKnights;
    }

    public ArrayList<Bishop> getBlackBishops() {
        return blackBishops;
    }

    public ArrayList<Rook> getWhiteRooks() {
        return whiteRooks;
    }

    public ArrayList<Rook> getBlackRooks() {
        return blackRooks;
    }

    public ArrayList<Pawn> getWhitePawns() {
        return whitePawns;
    }

    public ArrayList<Pawn> getBlackPawns() {
        return blackPawns;
    }

    public Queen getWhiteQueen() {
        return whiteQueen;
    }

    public void setBlackQueen(Queen blackQueen) {
        this.blackQueen = blackQueen;
    }

    public void setWhiteQueen(Queen whiteQueen) {
        this.whiteQueen = whiteQueen;
    }
}
