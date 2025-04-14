import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private final HashMap<Integer, ArrayList<GameMove>> tokenizedGameHistory;

    public Tokenizer() {
        this.tokenizedGameHistory = new HashMap<>();
    }

    public void tokenize(int gameNumber, String s) throws IOException {
        Pattern pattern = Pattern.compile("(\\d+)\\.\\s(.*?)(?=(\\d+\\.\\s)|$)");
        Matcher matcher = pattern.matcher(s);
        ArrayList<GameMove> currentGame = new ArrayList<>();
        while (matcher.find()) {
            String number = matcher.group(1);
            String chunk = matcher.group(2).trim();
            String[] splittedChunk = chunk.split(" ");
            if (splittedChunk.length > 3 || splittedChunk.length < 2 || (splittedChunk.length == 3 && !splittedChunk[2].equals("0-1") && !splittedChunk[2].equals("1-0") && !splittedChunk[2].equals("1/2-1/2"))) {
                GameMove currentGameMove = new GameMove(Integer.parseInt(number), "Game Move " + number + ": " + "On each step of the game there should be 2 moves, here it was " + splittedChunk.length);
                currentGame.add(currentGameMove);
            } else {
                SinglePlayerMove whiteMove = new SinglePlayerMove(Integer.parseInt(number), PlayerColor.WHITE, splittedChunk[0]);
                SinglePlayerMove blackMove = new SinglePlayerMove(Integer.parseInt(number), PlayerColor.BLACK, splittedChunk[1]);
                GameMove currentGameMove = new GameMove(Integer.parseInt(number), whiteMove, blackMove);
                currentGame.add(currentGameMove);
            }
        }
        this.tokenizedGameHistory.put(gameNumber, currentGame);
    }

    public HashMap<Integer, ArrayList<GameMove>> getTokenizedGameHistory() {
        return tokenizedGameHistory;
    }
}
