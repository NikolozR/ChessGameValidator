package io;

import game.GameEntry;
import game.GameMove;
import game.Simulation;

import java.io.IOException;
import java.util.ArrayList;

public class ChessValidator {
    public static void run(String src) throws IOException {
        PGNReader pgnReader = new PGNReader();
        pgnReader.extractGames(src);
        Tokenizer tokenizer = new Tokenizer();
        Simulation simulation = new Simulation();
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
                simulation.addValidGameHistories(g.getGameNumber(), currentGame);
                System.out.println("Game had No Errors, Valid Syntax");
            } else {
                System.out.println("Game had Errors!");
            }
            System.out.println();
            System.out.println("-----------------------------------------------------------------------------------");
            System.out.println();
        }
        simulation.simulate();
    }
}
