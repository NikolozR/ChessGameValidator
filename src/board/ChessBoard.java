package board;

import game.GameColor;
import pieces.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private final ArrayList<Queen> whiteQueens = new ArrayList<>();
    private final ArrayList<Queen> blackQueens = new ArrayList<>();
    private King whiteKing;
    private King blackKing;


    public ChessBoard() {
        this.boardSquares = new ChessBoardSquare[8][8];
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
                        if (rank == 1) this.whiteQueens.add(queen);
                        if (rank == 8) this.blackQueens.add(queen);
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

    public ChessBoard(boolean empty) {
        this.boardSquares = new ChessBoardSquare[8][8];
        if (empty) {
            for (int i = 0; i < boardSquares.length; i++) {
                for (int k = 0; k < boardSquares[0].length; k++) {
                    int rank = i + 1;
                    char file = (char) (k + 97);
                    ChessBoardSquare cbs = new ChessBoardSquare(file + "" + rank);
                    this.boardSquares[i][k] = cbs;
                }
            }

        }
    }

    public boolean playMove(String move, GameColor player) {
        if (move.equals("0-1") || move.equals("1-0") || move.equals("1/2-1/2")) return true;
        if (move.length() == 2) return this.regularPawnMove(move, player, false, false, -1);
        if (move.contains("=")) {
            return this.promotePawnMove(move.contains("x") ? move.substring(2, 4) : move.substring(0, 2), player, move.contains("x"), move.contains("+"), move.contains("x") ? move.charAt(0) : -1, move.contains("+") ? move.charAt(move.length() - 2) : move.charAt(move.length() - 1));
        } else if (move.equals("O-O") || move.equals("O-O-O")) return this.castlingKingMove(move, player);
        else if (move.length() == 3 && move.contains("+"))
            return this.regularPawnMove(move.substring(0, 2), player, false, true, -1);
        else if (move.length() == 3) {
            char firstChar = move.charAt(0);
            if (move.charAt(2) == '+') return this.regularPawnMove(move.substring(0, 1), player, false, true, -1);
            else if (firstChar == 'N') return this.regularKnightMove(move.substring(1), player, false, false, -1, -1);
            else if (firstChar == 'R') return this.regularRookMove(move.substring(1), player, false, false, -1, -1);
            else if (firstChar == 'B') return this.regularBishopMove(move.substring(1), player, false, false, -1, -1);
            else if (firstChar == 'Q') return this.regularQueenMove(move.substring(1), player, false, false, -1, -1);
            else if (firstChar == 'K') return this.regularKingMove(move.substring(1), player, false);
        } else if (move.length() == 4 && move.contains("x")) {
            char firstChar = move.charAt(0);
            if (firstChar == 'N') return this.regularKnightMove(move.substring(2), player, true, false, -1, -1);
            else if (firstChar == 'R') return this.regularRookMove(move.substring(2), player, true, false, -1, -1);
            else if (firstChar == 'B') return this.regularBishopMove(move.substring(2), player, true, false, -1, -1);
            else if (firstChar == 'Q') return this.regularQueenMove(move.substring(2), player, true, false, -1, -1);
            else if (firstChar == 'K') return this.regularKingMove(move.substring(2), player, true);
            else return this.regularPawnMove(move.substring(2), player, true, false, firstChar - 97);
        } else if ((move.length() == 5 || move.length() == 4) && move.charAt(move.length() - 1) == '+') {
            char firstChar = move.charAt(0);
            int fileSolver = (move.length() == 5 && !move.contains("x")) ? (move.charAt(1) >= 97 ? move.charAt(1) : -1) : -1;
            int rankSolver = (move.length() == 5 && !move.contains("x")) ? (move.charAt(1) < 97 ? Integer.parseInt(move.substring(1, 2)) : -1) : -1;
            String destinationMove = move.substring(move.length() - 3, move.length() - 1);
            if (firstChar == 'N')
                return this.regularKnightMove(destinationMove, player, move.contains("x"), true, fileSolver, rankSolver);
            else if (firstChar == 'R')
                return this.regularRookMove(destinationMove, player, move.contains("x"), true, fileSolver, rankSolver);
            else if (firstChar == 'B')
                return this.regularBishopMove(destinationMove, player, move.contains("x"), true, fileSolver, rankSolver);
            else if (firstChar == 'Q')
                return this.regularQueenMove(destinationMove, player, move.contains("x"), true, fileSolver, rankSolver);
            else if (firstChar == 'K') return this.regularKingMove(destinationMove, player, move.contains("x"));
            else if (move.length() == 5)
                return this.regularPawnMove(destinationMove, player, true, true, firstChar - 97);
            else return this.regularPawnMove(destinationMove, player, move.contains("x"), true, -1);
        } else if ((move.length() == 4 || move.length() == 5) && !move.contains("x")) {
            char firstChar = move.charAt(0);
            int fileSolver = move.length() == 4 ? (move.charAt(1) >= 97 ? move.charAt(1) : -1) : move.charAt(1);
            int rankSolver = move.length() == 4 ? (move.charAt(1) < 97 ? Integer.parseInt(move.substring(1, 2)) : -1) : Integer.parseInt(move.substring(2, 3));
            String destinationMove = move.substring(2);
            if (firstChar == 'N')
                return this.regularKnightMove(destinationMove, player, false, false, fileSolver, rankSolver);
            else if (firstChar == 'R')
                return this.regularRookMove(destinationMove, player, false, false, fileSolver, rankSolver);
            if (firstChar == 'B')
                return this.regularBishopMove(destinationMove, player, false, false, fileSolver, rankSolver);
        } else if (move.length() == 5 && !move.contains("x")) {
            char firstChar = move.charAt(0);
            int fileSolver = move.charAt(1);
            int rankSolver = Integer.parseInt(move.substring(2, 3));
            String destinationMove = move.substring(3);
            if (firstChar == 'N')
                return this.regularKnightMove(destinationMove, player, false, false, fileSolver, rankSolver);
            else if (firstChar == 'R')
                return this.regularRookMove(destinationMove, player, false, false, fileSolver, rankSolver);
            if (firstChar == 'B')
                return this.regularBishopMove(destinationMove, player, false, false, fileSolver, rankSolver);
        } else if (move.length() == 5 && move.contains("x")) {
            char firstChar = move.charAt(0);
            String destinationMove = move.substring(3);
            int fileSolver = move.charAt(1) >= 97 ? move.charAt(1) : -1;
            int rankSolver = move.charAt(1) < 97 ? Integer.parseInt(move.substring(1, 2)) : -1;
            if (firstChar == 'N')
                return this.regularKnightMove(destinationMove, player, true, false, fileSolver, rankSolver);
            else if (firstChar == 'R')
                return this.regularRookMove(destinationMove, player, true, false, fileSolver, rankSolver);
            if (firstChar == 'B')
                return this.regularBishopMove(destinationMove, player, true, false, fileSolver, rankSolver);
        }
        return false;
    }

    private boolean regularPawnMove(String move, GameColor player, boolean taking, boolean attacks, int fileSolver) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int pawnMoveCounts = 0;
        Pawn currentPawn = null;
        for (Pawn pawn : player == GameColor.WHITE ? this.getWhitePawns() : this.getBlackPawns()) {
            if (pawn.isTaken()) continue;
            if (fileSolver != -1 && pawn.getPieceSquare().getFileCharCode() - 97 != fileSolver) {
                continue;
            }
            if (attacks) {
                ChessBoard testBoard = this.copy();
                King attackingTo = player == GameColor.WHITE ? testBoard.getBlackKing() : testBoard.getWhiteKing();
                if (!attackingTo.isValidAttack(testBoard, testBoard.getBoardSquares()[pawn.getPieceSquare().getRank() - 1][pawn.getPieceSquare().getFileCharCode() - 97].getCurrentPiece(), testBoard.getBoardSquares()[destinationSquare.getRank() - 1][destinationSquare.getFileCharCode() - 97], taking, false, 'Q'))
                    continue;
            }
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

    private boolean promotePawnMove(String move, GameColor player, boolean taking, boolean attacks, int fileSolver, char promotionResult) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int pawnMoveCounts = 0;
        Pawn currentPawn = null;
        for (Pawn pawn : player == GameColor.WHITE ? this.getWhitePawns() : this.getBlackPawns()) {
            if (pawn.isTaken()) continue;
            if (fileSolver != -1 && pawn.getPieceSquare().getFileCharCode() - 97 != fileSolver) {
                continue;
            }
            if (attacks) {
                ChessBoard testBoard = this.copy();
                King attackingTo = player == GameColor.WHITE ? testBoard.getBlackKing() : testBoard.getWhiteKing();
                if (!attackingTo.isValidAttack(testBoard, testBoard.getBoardSquares()[pawn.getPieceSquare().getRank() - 1][pawn.getPieceSquare().getFileCharCode() - 97].getCurrentPiece(), testBoard.getBoardSquares()[destinationSquare.getRank() - 1][destinationSquare.getFileCharCode() - 97], taking, true, promotionResult))
                    continue;
            }
            boolean willMove = pawn.canPromote(destinationSquare, taking);
            if (willMove) {
                pawnMoveCounts++;
                currentPawn = pawn;
            }
        }
        if (pawnMoveCounts == 1) {
            currentPawn.promote(destinationSquare, taking, promotionResult);
            return true;
        }
        return false;
    }

    private boolean regularKnightMove(String move, GameColor player, boolean taking, boolean attacks, int fileSolver, int rankSolver) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int knightMoveCounts = 0;
        Knight currentKnight = null;
        for (Knight knight : player == GameColor.WHITE ? this.getWhiteKnights() : this.getBlackKnights()) {
            if (knight.isTaken()) continue;
            int currentFile = knight.getPieceSquare().getFileCharCode();
            int currentRank = knight.getPieceSquare().getRank();
            if (fileSolver != -1 && currentFile != fileSolver) {
                continue;
            }
            if (rankSolver != -1 && currentRank != rankSolver) continue;
            boolean willMove = knight.isCorrectMove(destinationSquare, taking);
            if (attacks) {
                ChessBoard testBoard = this.copy();
                King attackingTo = player == GameColor.WHITE ? testBoard.getBlackKing() : testBoard.getWhiteKing();
                if (!attackingTo.isValidAttack(testBoard, testBoard.getBoardSquares()[knight.getPieceSquare().getRank() - 1][knight.getPieceSquare().getFileCharCode() - 97].getCurrentPiece(), testBoard.getBoardSquares()[destinationSquare.getRank() - 1][destinationSquare.getFileCharCode() - 97], taking, false, 'Q'))
                    continue;
            }
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

    private boolean regularRookMove(String move, GameColor player, boolean taking, boolean attacks, int fileSolver, int rankSolver) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int rookMoveCounts = 0;
        Rook currentRook = null;
        for (Rook rook : player == GameColor.WHITE ? this.getWhiteRooks() : this.getBlackRooks()) {
            if (rook.isTaken()) continue;
            int currentFile = rook.getPieceSquare().getFileCharCode();
            int currentRank = rook.getPieceSquare().getRank();
            if (fileSolver != -1 && currentFile != fileSolver) continue;
            if (rankSolver != -1 && currentRank != rankSolver) continue;
            if (attacks) {
                ChessBoard testBoard = this.copy();
                King attackingTo = (player == GameColor.WHITE) ? testBoard.getBlackKing() : testBoard.getWhiteKing();
                if (!attackingTo.isValidAttack(testBoard, testBoard.getBoardSquares()[rook.getPieceSquare().getRank() - 1][rook.getPieceSquare().getFileCharCode() - 97].getCurrentPiece(), testBoard.getBoardSquares()[destinationSquare.getRank() - 1][destinationSquare.getFileCharCode() - 97], taking, false, 'Q')) {
                    continue;
                }
            }
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

    private boolean regularBishopMove(String move, GameColor player, boolean taking, boolean attacks, int fileSolver, int rankSolver) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int bishopMoveCounts = 0;
        Bishop currentBishop = null;
        for (Bishop bishop : player == GameColor.WHITE ? this.getWhiteBishops() : this.getBlackBishops()) {
            if (bishop.isTaken()) continue;
            int currentFile = bishop.getPieceSquare().getFileCharCode();
            int currentRank = bishop.getPieceSquare().getRank();
            if (fileSolver != -1 && currentFile != fileSolver) continue;
            if (rankSolver != -1 && currentRank != rankSolver) continue;
            if (attacks) {
                ChessBoard testBoard = this.copy();
                King attackingTo = player == GameColor.WHITE ? testBoard.getBlackKing() : testBoard.getWhiteKing();
                if (!attackingTo.isValidAttack(testBoard, testBoard.getBoardSquares()[bishop.getPieceSquare().getRank() - 1][bishop.getPieceSquare().getFileCharCode() - 97].getCurrentPiece(), testBoard.getBoardSquares()[destinationSquare.getRank() - 1][destinationSquare.getFileCharCode() - 97], taking, false, 'Q'))
                    continue;
            }
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

    private boolean regularQueenMove(String move, GameColor player, boolean taking, boolean attacks, int fileSolver, int rankSolver) {
        int file = ((int) move.charAt(0)) - 97;
        int rank = Integer.parseInt(move.substring(1)) - 1;
        ChessBoardSquare destinationSquare = this.getBoardSquares()[rank][file];
        int queenMoveCounts = 0;
        Queen currentQueen = null;
        for (Queen queen : player == GameColor.WHITE ? this.getWhiteQueens() : this.getBlackQueens()) {
            if (queen.isTaken()) continue;
            int currentFile = queen.getPieceSquare().getFileCharCode();
            int currentRank = queen.getPieceSquare().getRank();
            if (fileSolver != -1 && currentFile != fileSolver) continue;
            if (rankSolver != -1 && currentRank != rankSolver) continue;
            if (attacks) {
                ChessBoard testBoard = this.copy();
                King attackingTo = player == GameColor.WHITE ? testBoard.getBlackKing() : testBoard.getWhiteKing();
                if (!attackingTo.isValidAttack(testBoard, testBoard.getBoardSquares()[queen.getPieceSquare().getRank() - 1][queen.getPieceSquare().getFileCharCode() - 97].getCurrentPiece(), testBoard.getBoardSquares()[destinationSquare.getRank() - 1][destinationSquare.getFileCharCode() - 97], taking, false, 'Q'))
                    continue;
            }
            boolean willMove = queen.isCorrectMove(destinationSquare, taking);
            if (willMove) {
                queenMoveCounts++;
                currentQueen = queen;
            }
        }
        if (queenMoveCounts == 1) {
            currentQueen.move(destinationSquare, taking);
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


    public ChessBoardSquare getBoardSquare(String coordinate) {
        return this.getBoardSquares()[Integer.parseInt(coordinate.substring(1)) - 1][coordinate.charAt(0) - 97];
    }


    public ChessBoard copy() {
        ChessBoard result = new ChessBoard(true);
        Map<ChessPiece, ChessPiece> cloneMap = new HashMap<>();

        // --- First, clone all pieces and fill cloneMap ---

        // Took pieces
        for (ChessPiece p : this.blacksTook) {
            ChessPiece clone = p.clone(result);
            result.blacksTook.add(clone);
            cloneMap.put(p, clone);
        }

        for (ChessPiece p : this.whitesTook) {
            ChessPiece clone = p.clone(result);
            result.whitesTook.add(clone);
            cloneMap.put(p, clone);
        }

        // White pieces
        for (Bishop b : this.whiteBishops) {
            Bishop clone = b.clone(result);
            result.whiteBishops.add(clone);
            cloneMap.put(b, clone);
        }

        for (Rook r : this.whiteRooks) {
            Rook clone = r.clone(result);
            result.whiteRooks.add(clone);
            cloneMap.put(r, clone);
        }

        for (Pawn p : this.whitePawns) {
            Pawn clone = p.clone(result);
            result.whitePawns.add(clone);
            cloneMap.put(p, clone);
        }

        for (Knight k : this.whiteKnights) {
            Knight clone = k.clone(result);
            result.whiteKnights.add(clone);
            cloneMap.put(k, clone);
        }

        // Black pieces
        for (Bishop b : this.blackBishops) {
            Bishop clone = b.clone(result);
            result.blackBishops.add(clone);
            cloneMap.put(b, clone);
        }

        for (Rook r : this.blackRooks) {
            Rook clone = r.clone(result);
            result.blackRooks.add(clone);
            cloneMap.put(r, clone);
        }

        for (Pawn p : this.blackPawns) {
            Pawn clone = p.clone(result);
            result.blackPawns.add(clone);
            cloneMap.put(p, clone);
        }

        for (Knight k : this.blackKnights) {
            Knight clone = k.clone(result);
            result.blackKnights.add(clone);
            cloneMap.put(k, clone);
        }

        // Kings and Queens
        result.whiteKing = this.whiteKing.clone(result);
        cloneMap.put(this.whiteKing, result.whiteKing);

        result.blackKing = this.blackKing.clone(result);
        cloneMap.put(this.blackKing, result.blackKing);

        for (Queen k : this.whiteQueens) {
            Queen clone = k.clone(result);
            result.whiteQueens.add(clone);
            cloneMap.put(k, clone);
        }

        for (Queen q : this.blackQueens) {
            Queen clone = q.clone(result);
            result.blackQueens.add(clone);
            cloneMap.put(q, clone);
        }

        // --- Now copy the boardSquares using the cloned pieces from cloneMap ---
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                ChessPiece originalPiece = this.getBoardSquares()[x][y].getCurrentPiece();
                if (originalPiece != null) {
                    ChessPiece clonedPiece = cloneMap.get(originalPiece);
                    result.getBoardSquares()[x][y].setChessBoard(result);
                    result.getBoardSquares()[x][y].setCurrentPiece(clonedPiece);
                    clonedPiece.setPieceSquare(result.getBoardSquares()[x][y]);
                }
            }
        }

        return result;
    }


    public boolean isKingUnderAttack(GameColor color) {
        for (Pawn p : color == GameColor.WHITE ? this.getBlackPawns() : this.getWhitePawns()) {
            if (p.isTaken()) continue;
            boolean t = p.canAttack(p.getPieceSquare(), color == GameColor.WHITE ? this.getWhiteKing().getPieceSquare() : this.getBlackKing().getPieceSquare(), this);
            if (t) return true;
        }
        for (Bishop b : color == GameColor.WHITE ? this.getBlackBishops() : this.getWhiteBishops()) {
            if (b.isTaken()) continue;
            boolean t = b.canAttack(b.getPieceSquare(), color == GameColor.WHITE ? this.getWhiteKing().getPieceSquare() : this.getBlackKing().getPieceSquare(), this);
            if (t) return true;
        }
        for (Rook r : color == GameColor.WHITE ? this.getBlackRooks() : this.getWhiteRooks()) {
            if (r.isTaken()) continue;
            boolean t = r.canAttack(r.getPieceSquare(), color == GameColor.WHITE ? this.getWhiteKing().getPieceSquare() : this.getBlackKing().getPieceSquare(), this);
            if (t) return true;
        }
        for (Knight k : color == GameColor.WHITE ? this.getBlackKnights() : this.getWhiteKnights()) {
            if (k.isTaken()) continue;
            boolean t = k.canAttack(k.getPieceSquare(), color == GameColor.WHITE ? this.getWhiteKing().getPieceSquare() : this.getBlackKing().getPieceSquare(), this);
            if (t) return true;
        }
        for (Queen q : color == GameColor.WHITE ? this.getBlackQueens() : this.getWhiteQueens()) {
            if (q.isTaken()) continue;
            boolean t = q.canAttack(q.getPieceSquare(), color == GameColor.WHITE ? this.getWhiteKing().getPieceSquare() : this.getBlackKing().getPieceSquare(), this);
            if (t) return true;
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

    public ArrayList<Queen> getBlackQueens() {
        return blackQueens;
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

    public ArrayList<Queen> getWhiteQueens() {
        return whiteQueens;
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
