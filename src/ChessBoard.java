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


    public boolean playMove(String move, GameColor player) {
        if (move.length() == 2) return this.regularPawnMove(move, player, false);
        else if (move.equals("O-O") || move.equals("O-O-O")) return this.castlingKingMove(move, player);
        else if (move.length() == 3) {
            char firstChar = move.charAt(0);
            if (firstChar == 'N') return this.regularKnightMove(move.substring(1), player, false);
            else if (firstChar == 'R') return this.regularRookMove(move.substring(1), player, false);
            else if (firstChar == 'B') return this.regularBishopMove(move.substring(1), player, false);
            else if (firstChar == 'Q') return this.regularQueenMove(move.substring(1), player, false);
            else if (firstChar == 'K') return this.regularKingMove(move.substring(1), player, false);
        } else if (move.length() == 4 && move.charAt(1) == 'x') {
            char firstChar = move.charAt(0);
            if (firstChar == 'N') return this.regularKnightMove(move.substring(2), player, true);
            else if (firstChar == 'R') return this.regularRookMove(move.substring(2), player, true);
            else if (firstChar == 'B') return this.regularBishopMove(move.substring(2), player, true);
            else if (firstChar == 'Q') return this.regularQueenMove(move.substring(2), player, true);
            else if (firstChar == 'K') return this.regularKingMove(move.substring(2), player, true);
            else return this.ambiguousPawnMove(move.substring(2), player, true, firstChar);
        }
        return false;
    }

    private boolean regularPawnMove(String move, GameColor player, boolean taking) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int pawnMoveCounts = 0;
        Pawn currentPawn = null;
        for (Pawn pawn : player == GameColor.WHITE ? this.getWhitePawns() : this.getBlackPawns()) {
            if (pawn.isTaken()) continue;
            boolean willMove = pawn.isCorrectMove(destinationSquare, taking);
            if (willMove) {
                pawnMoveCounts++;
                currentPawn = pawn;
            }
        }
        if (pawnMoveCounts == 1) {
            currentPawn.move(destinationSquare, taking);
            return true;
        }
        return false;
    }

    private boolean ambiguousPawnMove(String move, GameColor player, boolean taking, int file) {
        int destinationFile = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][destinationFile];
        int pawnMoveCounts = 0;
        Pawn currentPawn = null;
        for (Pawn pawn : player == GameColor.WHITE ? this.getWhitePawns() : this.getBlackPawns()) {
            if (pawn.isTaken()) continue;
            if (pawn.getPieceSquare().getFileCharCode() == file) {
                boolean willMove = pawn.isCorrectMove(destinationSquare, taking);
                if (willMove) {
                    pawnMoveCounts++;
                    currentPawn = pawn;
                }
            }
        }
        if (pawnMoveCounts == 1) {
            currentPawn.move(destinationSquare, taking);
            return true;
        }
        return false;
    }

    private boolean regularKnightMove(String move, GameColor player, boolean taking) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int knightMoveCounts = 0;
        Knight currentKnight = null;
        for (Knight knight : player == GameColor.WHITE ? this.getWhiteKnights() : this.getBlackKnights()) {
            if (knight.isTaken()) continue;
            boolean willMove = knight.isCorrectMove(destinationSquare, taking);
            if (willMove) {
                knightMoveCounts++;
                currentKnight = knight;
            }
        }
        if (knightMoveCounts == 1) {
            currentKnight.move(destinationSquare, taking);
            return true;
        }
        return false;
    }

    private boolean regularRookMove(String move, GameColor player, boolean taking) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int rookMoveCounts = 0;
        Rook currentRook = null;
        for (Rook rook : player == GameColor.WHITE ? this.getWhiteRooks() : this.getBlackRooks()) {
            if (rook.isTaken()) continue;
            boolean willMove = rook.isCorrectMove(destinationSquare, taking);
            if (willMove) {
                rookMoveCounts++;
                currentRook = rook;
            }
        }
        if (rookMoveCounts == 1) {
            currentRook.move(destinationSquare, taking);
            return true;
        }
        return false;
    }

    private boolean regularBishopMove(String move, GameColor player, boolean taking) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int bishopMoveCounts = 0;
        Bishop currentBishop = null;
        for (Bishop bishop : player == GameColor.WHITE ? this.getWhiteBishops() : this.getBlackBishops()) {
            if (bishop.isTaken()) continue;
            boolean willMove = bishop.isCorrectMove(destinationSquare, taking);
            if (willMove) {
                bishopMoveCounts++;
                currentBishop = bishop;
            }
        }
        if (bishopMoveCounts == 1) {
            currentBishop.move(destinationSquare, taking);
            return true;
        }
        return false;
    }

    private boolean regularQueenMove(String move, GameColor player, boolean taking) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        Queen queen = player == GameColor.WHITE ? this.getWhiteQueen() : this.getBlackQueen();
        if (queen.isTaken()) return false;
        boolean willMove = queen.isCorrectMove(destinationSquare, taking);
        if (willMove) {
            queen.move(destinationSquare, taking);
            return true;
        }
        return false;
    }

    private boolean regularKingMove(String move, GameColor player, boolean taking) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        King king = player == GameColor.WHITE ? this.getWhiteKing() : this.getBlackKing();
        boolean willMove = king.isCorrectMove(destinationSquare, taking);
        if (willMove) {
            king.move(destinationSquare, taking);
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

    public void addWhiteTooks(ChessPiece whiteTook) {
        this.whitesTook.add(whiteTook);
    }

    public void addBlackTooks(ChessPiece blackTook) {
        this.blacksTook.add(blackTook);
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
