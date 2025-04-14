import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        PGNReader pgnReader = new PGNReader();
        pgnReader.extractGames("src/Tbilisi2015.pgn");
        Tokenizer tokenizer = new Tokenizer();
        for (GameEntry g : pgnReader.getGames()) {
            tokenizer.tokenize(g.getGameNumber(), g.getGameMoves());
        }
        for (GameEntry g : pgnReader.getGames()) {
            System.out.println("Analyzing game N" + g.getGameNumber());
            int errorCount = 0;
            ArrayList<GameMove> currentGame = tokenizer.getTokenizedGameHistory().get(g.getGameNumber());
            for (GameMove gm : currentGame) {
                if (gm.isValidLength()) {
                    if (!gm.getWhitesMove().isValidMove()) {
                        errorCount++;
                        System.out.println(gm.getWhitesMove().getErrorStatusText());
                    }
                    if (!gm.getBlacksMove().isValidMove()) {
                        errorCount++;
                        System.out.println(gm.getBlacksMove().getErrorStatusText());
                    }
                } else {
                    errorCount++;
                    System.out.println(gm.getErrorStatusText());
                }
            }
            if (errorCount == 0) {
                System.out.println("Game had No Errors, Valid Syntax");
            } else {
                System.out.println("Game had Errors!");
            }
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println();
        }


//        for (GameEntry g : pgnReader.getGames()) {
//            System.out.println("Game #" + g.getGameNumber());
//            System.out.println("Event: " + g.getEvent());
//            System.out.println("Date: " + g.getDate());
//            System.out.println("White: " + g.getWhite());
//            System.out.println("Black: " + g.getBlack());
//            System.out.println("Moves: " + g.getGameMoves());
//            System.out.println("---------------------------------------------------");
//        }
    }
}
