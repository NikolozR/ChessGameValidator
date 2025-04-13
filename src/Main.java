import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        PGNReader pgnReader = new PGNReader();
        pgnReader.extractGames("src/Tbilisi2015.pgn");

        for (GameEntry g : pgnReader.getGames()) {
            System.out.println("Game #" + g.getGameNumber());
            System.out.println("Event: " + g.getEvent());
            System.out.println("Date: " + g.getDate());
            System.out.println("White: " + g.getWhite());
            System.out.println("Black: " + g.getBlack());
            System.out.println("Moves: " + g.getGameMoves());
            System.out.println("---------------------------------------------------");
        }
    }
}
