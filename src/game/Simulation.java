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
            System.out.print(String.format("Simulating Game #%d... ", key)); // Indicate start
            try {
                this.simulateSingleGame(key, val);
                System.out.println("SUCCESS!"); // Indicate success
                // Reset board *only* after successful simulation or handling failure
                // this.setBoard(new ChessBoard()); // Move reset below
            } catch (Exception e) {
                // Indicate failure clearly and provide the reason
                String errorMessage = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
                System.err.println(String.format("FAILED! Reason:\n%s\nSkipping game.", errorMessage));
                // Optionally print stack trace for debugging (to standard error)
                // e.printStackTrace(System.err);
            } finally {
                // Reset the board regardless of success or failure before the next iteration
                this.setBoard(new ChessBoard());
            }
        });
        System.out.println("--- Simulation Complete ---"); // Add a final message
    }

// --- Example Output ---
// Simulating Game #56... SUCCESS!
// Simulating Game #57... FAILED! Reason: Invalid move format 'O-O-O-O'. Skipping game.
// Simulating Game #58... SUCCESS!
// --- Simulation Complete ---

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
            throw new SimulationGameError("Move #" + erroredMoveNumber + ".\nPlayer that had error: " + erroredColor);
    }


    public ChessBoard getBoard() {
        return board;
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
    }
}
