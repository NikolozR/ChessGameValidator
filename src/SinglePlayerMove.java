import java.util.regex.Pattern;

public class SinglePlayerMove {
    // Still Needs work for this pattern
    public static final Pattern LEGAL_CHESS_MOVE = Pattern.compile("^(O-O(-O)?)|([KQRBN]?[a-h]?[1-8]?x?[a-h][1-8](=[QRBN])?)$");

    private final int gameMove;
    private final PlayerColor playerColor;
    private final String move;
    private final boolean validMove;
    private String errorStatusText;

    public SinglePlayerMove(int gameMove, PlayerColor playerColor, String move) {
        this.gameMove = gameMove;
        this.playerColor = playerColor;
        this.move = move;
        this.validMove = this.checkValidity();
        if (!this.validMove) {
            this.errorStatusText = "Game Move " + this.gameMove + ": " + (this.playerColor == PlayerColor.WHITE ? "White made illegal move: " : "Black made illegal move: ") + this.move;
        }
    }

    private boolean checkValidity() {
        return LEGAL_CHESS_MOVE.matcher(this.move).matches();
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

    public String getErrorStatusText() {
        return errorStatusText;
    }
}
