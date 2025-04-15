public class GameMove {

    private final int moveNumber;
    private final SinglePlayerMove whitesMove;
    private final SinglePlayerMove blacksMove;
    private boolean validLength = true;
    private String errorStatusText;
    private int lastMove;

    public GameMove(int moveNumber, SinglePlayerMove whitesMove, SinglePlayerMove blacksMove) {
        this.moveNumber = moveNumber;
        this.whitesMove = whitesMove;
        this.blacksMove = blacksMove;
    }

    public GameMove(int moveNumber, String errorStatusText) {
        this.moveNumber = moveNumber;
        this.whitesMove = null;
        this.blacksMove = null;
        this.validLength = false;
        this.errorStatusText = errorStatusText;
    }

    public int getMoveNumber() {
        return moveNumber;
    }

    public SinglePlayerMove getBlacksMove() {
        return blacksMove;
    }

    public SinglePlayerMove getWhitesMove() {
        return whitesMove;
    }

    public boolean isValidLength() {
        return validLength;
    }

    public void setValidLength(boolean validLength) {
        this.validLength = validLength;
    }

    public String getErrorStatusText() {
        return errorStatusText;
    }

    public int getLastMove() {
        return lastMove;
    }

    public void setLastMove(int lastMove) {
        this.lastMove = lastMove;
        if (whitesMove != null && blacksMove != null) {
            whitesMove.setLastMove(lastMove);
            blacksMove.setLastMove(lastMove);
        }
    }
}
