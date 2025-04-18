package pieces;

import board.ChessBoard;
import board.ChessBoardSquare;
import game.GameColor;

public class King extends ChessPiece {
    private boolean hasMoved;
    private boolean underAttack;

    public King(GameColor pieceColor, ChessBoardSquare pieceSquare, ChessBoard chessboard) {
        super(pieceColor, pieceSquare, chessboard);
        this.hasMoved = false;
        this.underAttack = false;
    }

    public King(GameColor pieceColor, ChessBoard chessboard) {
        super(pieceColor, chessboard);
        this.hasMoved = false;
        this.underAttack = false;
    }

    public void move(String castlingType) {
        if (this.isCorrectMove(castlingType)) {
            this.getPieceSquare().setCurrentPiece(null);
            this.hasMoved = true;
            if (castlingType.equals("O-O")) {
                if (this.getPieceColor() == GameColor.WHITE) {
                    ChessBoardSquare squareh1 = this.getChessboard().getBoardSquares()[0][7];
                    this.getChessboard().getBoardSquares()[0][5].setCurrentPiece(squareh1.getCurrentPiece());
                    ((Rook) squareh1.getCurrentPiece()).setHasMoved(true);
                    squareh1.getCurrentPiece().setPieceSquare(this.getChessboard().getBoardSquares()[0][5]);
                    squareh1.setCurrentPiece(null);
                    this.getChessboard().getBoardSquares()[0][6].setCurrentPiece(this);
                    this.setPieceSquare(this.getChessboard().getBoardSquares()[0][6]);
                } else {
                    ChessBoardSquare squareh8 = this.getChessboard().getBoardSquares()[7][7];
                    this.getChessboard().getBoardSquares()[7][5].setCurrentPiece(squareh8.getCurrentPiece());
                    ((Rook) squareh8.getCurrentPiece()).setHasMoved(true);
                    squareh8.getCurrentPiece().setPieceSquare(this.getChessboard().getBoardSquares()[7][5]);
                    squareh8.setCurrentPiece(null);
                    this.getChessboard().getBoardSquares()[7][6].setCurrentPiece(this);
                    this.setPieceSquare(this.getChessboard().getBoardSquares()[7][6]);
                }
            } else {
                if (this.getPieceColor() == GameColor.WHITE) {
                    ChessBoardSquare squarea1 = this.getChessboard().getBoardSquares()[0][0];
                    this.getChessboard().getBoardSquares()[0][3].setCurrentPiece(squarea1.getCurrentPiece());
                    ((Rook) squarea1.getCurrentPiece()).setHasMoved(true);
                    squarea1.getCurrentPiece().setPieceSquare(this.getChessboard().getBoardSquares()[0][3]);
                    squarea1.setCurrentPiece(null);
                    this.getChessboard().getBoardSquares()[0][2].setCurrentPiece(this);
                    this.setPieceSquare(this.getChessboard().getBoardSquares()[0][2]);
                } else {
                    ChessBoardSquare squarea8 = this.getChessboard().getBoardSquares()[7][0];
                    this.getChessboard().getBoardSquares()[7][3].setCurrentPiece(squarea8.getCurrentPiece());
                    ((Rook) squarea8.getCurrentPiece()).setHasMoved(true);
                    squarea8.getCurrentPiece().setPieceSquare(this.getChessboard().getBoardSquares()[7][3]);
                    squarea8.setCurrentPiece(null);
                    this.getChessboard().getBoardSquares()[7][2].setCurrentPiece(this);
                    this.setPieceSquare(this.getChessboard().getBoardSquares()[7][2]);
                }
            }
        }
    }

    @Override
    public boolean isCorrectMove(ChessBoardSquare destinationSquare, boolean taking) {

        int currentRank = this.getPieceSquare().getRank();
        int currentFile = this.getPieceSquare().getFileCharCode();

        int destRank = destinationSquare.getRank();
        int destFile = destinationSquare.getFileCharCode();

        int rankDiff = Math.abs(destRank - currentRank);
        int fileDiff = Math.abs(destFile - currentFile);


        if (rankDiff <= 1 && fileDiff <= 1 && (rankDiff + fileDiff > 0)) {
            if (isOppositeKingAdj(destinationSquare)) return false;
            if (taking) {
                return this.canTake(destinationSquare, this.getPieceColor());
            } else {
                return destinationSquare.getCurrentPiece() == null;
            }
        }

        return false;
    }


    public boolean isCorrectMove(String castlingType) {
        if (hasMoved) return false;
        if (castlingType.equals("O-O")) {
            if (this.getPieceColor() == GameColor.WHITE) {
                ChessBoardSquare squareh1 = this.getChessboard().getBoardSquares()[0][7];
                for (int i = 5; i < 7; i++) {
                    if (!this.getChessboard().getBoardSquares()[0][i].isSquareEmpty()) return false;
                }
                if (squareh1.isSquareEmpty() || !(squareh1.getCurrentPiece() instanceof Rook) || squareh1.getCurrentPiece().isTaken())
                    return false;
                else return !((Rook) squareh1.getCurrentPiece()).isHasMoved();
            } else {
                ChessBoardSquare squareh8 = this.getChessboard().getBoardSquares()[7][7];
                for (int i = 5; i < 7; i++) {
                    if (!this.getChessboard().getBoardSquares()[7][i].isSquareEmpty()) return false;
                }
                if (squareh8.isSquareEmpty() || !(squareh8.getCurrentPiece() instanceof Rook) || squareh8.getCurrentPiece().isTaken())
                    return false;
                else return !((Rook) squareh8.getCurrentPiece()).isHasMoved();
            }
        } else {
            if (this.getPieceColor() == GameColor.WHITE) {
                ChessBoardSquare squarea1 = this.getChessboard().getBoardSquares()[0][0];
                for (int i = 1; i < 4; i++) {
                    if (!this.getChessboard().getBoardSquares()[0][i].isSquareEmpty()) return false;
                }
                if (squarea1.isSquareEmpty() || !(squarea1.getCurrentPiece() instanceof Rook) || squarea1.getCurrentPiece().isTaken())
                    return false;
                else return !((Rook) squarea1.getCurrentPiece()).isHasMoved();
            } else {
                ChessBoardSquare squarea8 = this.getChessboard().getBoardSquares()[7][0];
                for (int i = 1; i < 4; i++) {
                    if (!this.getChessboard().getBoardSquares()[7][i].isSquareEmpty()) return false;
                }
                if (squarea8.isSquareEmpty() || !(squarea8.getCurrentPiece() instanceof Rook) || squarea8.getCurrentPiece().isTaken())
                    return false;
                else return !((Rook) squarea8.getCurrentPiece()).isHasMoved();
            }
        }
    }


    private boolean isOppositeKingAdj(ChessBoardSquare destinationSquare) {
        int rank = destinationSquare.getRank() - 1;
        int file = destinationSquare.getFileCharCode() - 97;

        for (int dr = -1; dr <= 1; dr++) {
            for (int df = -1; df <= 1; df++) {
                if (dr == 0 && df == 0) continue;

                int neighborRank = rank + dr;
                int neighborFile = file + df;

                if (neighborRank < 0 || neighborRank > 7 || neighborFile < 0 || neighborFile > 7) continue;

                ChessBoardSquare neighbor = this.getChessboard().getBoardSquares()[neighborRank][neighborFile];
                if (neighbor != null && neighbor.getCurrentPiece() instanceof King &&
                        neighbor.getCurrentPiece().getPieceColor() != this.getPieceColor()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isValidAttack(ChessBoard testBoard, ChessPiece movingPiece, ChessBoardSquare destinationSquare, boolean taking, boolean promoting, char promotionResult) {
        if (!promoting) {
            if (!movingPiece.isCorrectMove(destinationSquare, taking)) {
                return false;
            }
            if (taking) {
                destinationSquare.takesCurrentPiece(movingPiece);
            } else destinationSquare.regularMove(movingPiece);
            return testBoard.isKingUnderAttack(this.getPieceColor());
        } else {
            if (!((Pawn) movingPiece).canPromote(destinationSquare, taking)) {
                return false;
            }
            ((Pawn) movingPiece).promote(destinationSquare, taking, promotionResult);
            return testBoard.isKingUnderAttack(this.getPieceColor());
        }
    }

    @Override
    public King clone(ChessBoard chessBoard) {
        King result = new King(this.getPieceColor(), chessBoard);
        result.setTaken(this.isTaken());
        return result;
    }

    public boolean isUnderAttack() {
        return underAttack;
    }

    public void setUnderAttack(boolean underAttack) {
        this.underAttack = underAttack;
    }
}
