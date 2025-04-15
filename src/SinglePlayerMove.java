import java.util.regex.Pattern;

public class SinglePlayerMove {
    // Needs only promotions and weird rule of pawns taking
    public static final Pattern LEGAL_CHESS_MOVE = Pattern.compile("^((O-O(-O)?)|([KQRBN][a-h]?[1-8]?x?[a-h][1-8])|([a-h][1-8])|([a-h]x[a-h][1-8])|)(\\+|#)?$");

    private final int gameMoveNumber;
    private final PlayerColor playerColor;
    private final String move;
    private boolean validMove;
    private String errorStatusText;
    private int lastMove;

    public SinglePlayerMove(int gameMove, PlayerColor playerColor, String move) {
        this.gameMoveNumber = gameMove;
        this.playerColor = playerColor;
        this.move = move;

    }

    private boolean checkValidity() {
        if (LEGAL_CHESS_MOVE.matcher(this.move).matches()) return true;
        else {
            return this.gameMoveNumber == this.lastMove
                    && this.playerColor == PlayerColor.BLACK
                    && (this.move.equals("1-0") || this.move.equals("0-1") || this.move.equals("1/2-1/2"));
        }
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public String getMove() {
        return move;
    }

    public boolean isValidMove() {
        return validMove;
    }

    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
        this.validMove = this.checkValidity();
        if (!this.validMove) {
            this.errorStatusText = "Game Move " + this.gameMoveNumber + ": " + (this.playerColor == PlayerColor.WHITE ? "White made illegal move: " : "Black made illegal move: ") + this.move;
        }
    }

    public String getErrorStatusText() {
        return errorStatusText;
    }
}
