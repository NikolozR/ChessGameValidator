package io;

import game.GameEntry;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PGNReader {
    private final ArrayList<GameEntry> games;

    public PGNReader() {
        this.games = new ArrayList<>();
    }

    public void extractGames(String path) throws IOException {
        Path pgnFilePath = Paths.get(path);
        List<String> lines = Files.readAllLines(pgnFilePath);

        int gameCounter = 0;
        boolean readingGame = false;

        String event = "";
        String date = "";
        String white = "";
        String black = "";
        StringBuilder gameMoves = new StringBuilder();

        for (String l : lines) {
            if (readingGame) {
                if (this.gameInfoPattern("Event", l)) {
                    games.add(new GameEntry(gameCounter, event, date, white, black, gameMoves.toString()));
                    gameMoves = new StringBuilder();
                    gameCounter++;
                    event = this.getBetweenQuotes(l);
                    readingGame = false;
                } else {
                    gameMoves.append(l + " ");
                }
            } else {
                if (this.gameInfoPattern("Event", l)) {
                    event = this.getBetweenQuotes(l);
                    gameCounter++;
                } else if (this.gameInfoPattern("Date", l)) date = this.getBetweenQuotes(l);
                else if (this.gameInfoPattern("White", l)) white = this.getBetweenQuotes(l);
                else if (this.gameInfoPattern("Black", l)) black = this.getBetweenQuotes(l);
                else if (this.gameInfoPattern("EventDate", l)) {
                    readingGame = true;
                }
            }
        }
        games.add(new GameEntry(gameCounter, event, date, white, black, gameMoves.toString()));
    }

    private boolean gameInfoPattern(String s, String l) {
        return Pattern.matches("\\[" + s + "\\s+\"([^\"]+)\"\\]", l);
    }

    private String getBetweenQuotes(String s) {
        int firstQuote = s.indexOf("\"");
        int secondQuote = s.indexOf("\"", firstQuote + 1);

        if (firstQuote != -1 && secondQuote != -1) {
            return s.substring(firstQuote + 1, secondQuote);
        } else {
            return "";
        }
    }

    public ArrayList<GameEntry> getGames() {
        return games;
    }
}
