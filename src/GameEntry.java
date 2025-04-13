public class GameEntry {
    private final int gameNumber;
    private final String event;
    private final String date;
    private final String white;
    private final String black;
    private final String gameMoves;
    private boolean valid = true;


    public GameEntry(int gameNumber, String event, String date, String white, String black, String gameMoves) {
        this.gameNumber = gameNumber;
        this.event = event;
        this.date = date;
        this.white = white;
        this.black = black;
        this.gameMoves = gameMoves;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public String getBlack() {
        return black;
    }

    public String getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public String getWhite() {
        return white;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean getValid() {
        return this.valid;
    }

    public String getGameMoves() {
        return gameMoves;
    }
}
