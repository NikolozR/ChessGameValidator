package game;

import board.ChessBoard;

import java.util.ArrayList;
import java.util.HashMap;

public class Simulation {
    private final HashMap<Integer, ArrayList<GameMove>> tokenizedGameHistory;
    private ChessBoard board;

    public Simulation() {
        this.tokenizedGameHistory = new HashMap<>();
        this.board = new ChessBoard();
    }

    public void addValidGameHistories(int gameNumber, ArrayList<GameMove> game) {
        tokenizedGameHistory.put(gameNumber, game);
    }

    public HashMap<Integer, ArrayList<GameMove>> getTokenizedGameHistory() {
        return tokenizedGameHistory;
    }

    public void simulate() {
        tokenizedGameHistory.forEach((key, val) -> {

            try {
                this.simulateSingleGame(key, val);
                System.out.println("Game #" + key + " was simulated correctly!");
                this.setBoard(new ChessBoard());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Moving on the next game...");
                this.setBoard(new ChessBoard());
            }
        });

    }

    public void simulateSingleGame(int gameNumber, ArrayList<GameMove> game) {
        int erroredMoveNumber = 0;
        GameColor erroredColor = GameColor.BLACK;
        int goal = game.size();
        int moveCount = 0;
        for (GameMove gm : game) {
            erroredMoveNumber = gm.getMoveNumber();
            boolean whiteMoved = this.getBoard().playMove(gm.getWhitesMove().getMove(), GameColor.WHITE);
            if (!whiteMoved) {
                erroredMoveNumber = gm.getMoveNumber();
                erroredColor = GameColor.WHITE;
                break;
            }
            boolean blackMoved = this.getBoard().playMove(gm.getBlacksMove().getMove(), GameColor.BLACK);
            if (!blackMoved) {
                erroredMoveNumber = gm.getMoveNumber();
                break;
            }
            moveCount++;
        }
        if (goal != moveCount)
            throw new SimulationGameError("Game #" + gameNumber + " Had Error. Move Number: " + erroredMoveNumber + ". Player that had error: " + erroredColor);
    }


    public ChessBoard getBoard() {
        return board;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }
}
