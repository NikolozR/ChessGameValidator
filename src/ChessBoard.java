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
    private King whiteKing;
    private King blackKing;


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
                        King king = new King(rank == 1 ? GameColor.WHITE : GameColor.BLACK, cbs, this);
                        if (rank == 1) this.setWhiteKing(king);
                        if (rank == 8) this.setBlackKing(king);
                        cbs.setCurrentPiece(king);
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


    public void addWhiteTooks(ChessPiece whiteTook) {
        this.whitesTook.add(whiteTook);
    }

    public void addBlackTooks(ChessPiece blackTook) {
        this.blacksTook.add(blackTook);
    }

    public boolean playMove(String move, GameColor player) {
        if (move.length() == 2) return this.regularPawnMove(move, player);
        else if (move.length() == 3) {
            char firstChar = move.charAt(0);
            if (firstChar == 'N') return this.regularKnightMove(move, player);
            else if (firstChar == 'R') return this.regularRookMove(move, player);
            else if (firstChar == 'B') return this.regularBishopMove(move, player);
            else if (firstChar == 'Q') return this.regularQueenMove(move, player);
            else if (firstChar == 'K') return this.regularKingMove(move, player);
            else if (move.equals("O-O") || move.equals("O-O-O")) return this.castlingKingMove(move, player);
        }
        return false;
    }

    private boolean regularPawnMove(String move, GameColor player) {
        ChessBoardSquare destinationSquare = new ChessBoardSquare(move, this);
        int pawnMoveCounts = 0;
        Pawn currentPawn = null;
        for (Pawn pawn : player == GameColor.WHITE ? this.getWhitePawns() : this.getBlackPawns()) {
            boolean willMove = pawn.isCorrectMove(destinationSquare, false);
            if (willMove) {
                pawnMoveCounts++;
                currentPawn = pawn;
            }
        }
        if (pawnMoveCounts == 1) {
            currentPawn.move(destinationSquare, false);
            return true;
        }
        return false;
    }

    private boolean regularKnightMove(String move, GameColor player) {
        ChessBoardSquare destinationSquare = new ChessBoardSquare(move.substring(1), this);
        int knightMoveCounts = 0;
        Knight currentKnight = null;
        for (Knight knight : player == GameColor.WHITE ? this.getWhiteKnights() : this.getBlackKnights()) {
            boolean willMove = knight.isCorrectMove(destinationSquare, false);
            if (willMove) {
                knightMoveCounts++;
                currentKnight = knight;
            }
        }
        if (knightMoveCounts == 1) {
            currentKnight.move(destinationSquare, false);
            return true;
        }
        return false;
    }

    private boolean regularRookMove(String move, GameColor player) {
        ChessBoardSquare destinationSquare = new ChessBoardSquare(move.substring(1), this);
        int rookMoveCounts = 0;
        Rook currentRook = null;
        for (Rook rook : player == GameColor.WHITE ? this.getWhiteRooks() : this.getBlackRooks()) {
            boolean willMove = rook.isCorrectMove(destinationSquare, false);
            if (willMove) {
                rookMoveCounts++;
                currentRook = rook;
            }
        }
        if (rookMoveCounts == 1) {
            currentRook.move(destinationSquare, false);
            return true;
        }
        return false;
    }

    private boolean regularBishopMove(String move, GameColor player) {
        ChessBoardSquare destinationSquare = new ChessBoardSquare(move.substring(1), this);
        int bishopMoveCounts = 0;
        Bishop currentBishop = null;
        for (Bishop bishop : player == GameColor.WHITE ? this.getWhiteBishops() : this.getBlackBishops()) {
            boolean willMove = bishop.isCorrectMove(destinationSquare, false);
            if (willMove) {
                bishopMoveCounts++;
                currentBishop = bishop;
            }
        }
        if (bishopMoveCounts == 1) {
            currentBishop.move(destinationSquare, false);
            return true;
        }
        return false;
    }

    private boolean regularQueenMove(String move, GameColor player) {
        ChessBoardSquare destinationSquare = new ChessBoardSquare(move.substring(1), this);
        Queen queen = player == GameColor.WHITE ? this.getWhiteQueen() : this.getBlackQueen();
        boolean willMove = queen.isCorrectMove(destinationSquare, false);
        if (willMove) {
            queen.move(destinationSquare, false);
            return true;
        }
        return false;
    }

    private boolean regularKingMove(String move, GameColor player) {
        ChessBoardSquare destinationSquare = new ChessBoardSquare(move.substring(1), this);
        King king = player == GameColor.WHITE ? this.getWhiteKing() : this.getBlackKing();
        boolean willMove = king.isCorrectMove(destinationSquare, false);
        if (willMove) {
            king.move(destinationSquare, false);
            return true;
        }
        return false;
    }

    private boolean castlingKingMove(String castlingType, GameColor player) {
        King king = player == GameColor.WHITE ? this.getWhiteKing() : this.getBlackKing();
        boolean willMove = king.isCorrectMove(castlingType);
        if (willMove) {
            king.move(castlingType);
            return true;
        }
        return false;
    }


    public ChessBoardSquare[][] getBoardSquares() {
        return boardSquares;
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

    public void setBlackKing(King blackKing) {
        this.blackKing = blackKing;
    }

    public void setWhiteKing(King whiteKing) {
        this.whiteKing = whiteKing;
    }

    public King getBlackKing() {
        return blackKing;
    }

    public King getWhiteKing() {
        return whiteKing;
    }
}
