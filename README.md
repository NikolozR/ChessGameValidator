# Chess PGN Validator

This project is a Java-based PGN (Portable Game Notation) validator that parses and validates chess games from `.pgn`
files. It simulates the moves on an internal board model, checking syntax and legality based on standard chess rules.

---

## 📂 Project Structure

```
├── src
│   ├── board/              # ChessBoard and ChessBoardSquare classes
│   ├── pieces/             # Pawn, Rook, Knight, Bishop, Queen, King, ChessPiece and ChessPieceI (Interface)
│   ├── game/               # GameColor and general utilities
│   ├── io/                 # PGNReader, Tokenizer, ChessValidator and test cases
│   ├── tests/              # JUnit tests for each piece
├── bin/                    # Compiled classes
```

---

## 🧠 How It Works

### 1. `PGNReader`

Reads the `.pgn` file and extracts the relevant part of the game (i.e., the actual moves and game information).

### 2. `Tokenizer`

Breaks the PGN string into individual move tokens.

During this phase, **syntactic validation** is performed using regex:

```java
public static final Pattern LEGAL_CHESS_MOVE =
        Pattern.compile("^((O-O(-O)?)|([KQRBN][a-h]?[1-8]?x?[a-h][1-8])|([a-h][1-8](=[QRBN])?)|([a-h]x[a-h][1-8](=[QRBN])?)|)(\\+|#)?$");
```

Only moves matching this pattern are allowed to proceed to the next phase.

### 3. `Chess Simulation`

Each move is simulated on an internal `ChessBoard` using custom logic and classes for each piece. Invalid moves (e.g.,
illegal knight move, moving into check) are caught and flagged.

---

## Running the Project

#### Command To Compile Code

```bash
javac -d bin -sourcepath src src/Main.java
```

#### Command to run with your .pgn file

```cmd
java -cp bin Main /your/path/of/pgnFile.pgn
```

This will:

1. Compile all necessary classes.
2. Launch the simulation via `Main.java`.
3. Don't worry if you don't have .pgn file, run this command and default .pgn will be tested

```cmd
java -cp bin Main
```

---

## ✅ Features

- PGN format parsing and regex-based syntax validation
- Full chessboard model with piece movement logic
- Legal move simulation (including captures, castling, promotion, en passant)
- Clear feedback on invalid or illegal moves

---

## 🧪 Unit Testing

Each chess piece is tested using JUnit with comprehensive test cases:

- Movement rules (valid and invalid)
- Blocking and capturing
- Initial moves (e.g., pawn double move)

Tests are located under `tests/` directory.

---

## 📌 Notes

- Castling, promotion, and en passant rules are respected.

## 🧾 Sample Input & Output

### Input (sample PGN):

```
[Event "Tbilisi FIDE GP 2015"]
[Site "Tbilisi GEO"]
[Date "2015.02.15"]
[Round "1.1"]
[White "Kasimdzhanov,R"]
[Black "Grischuk,A"]
[Result "0-1"]
[WhiteTitle "GM"]
[BlackTitle "GM"]
[WhiteElo "2705"]
[BlackElo "2810"]
[ECO "D31"]
[Opening "QGD"]
[Variation "semi-Slav, Abrahams variation"]
[WhiteFideId "14200244"]
[BlackFideId "4126025"]
[EventDate "2015.02.15"]

1. d4 d5 2. c4 e6 3. Nc3 Bb4 4. Nf3 dxc4 5. e3 b5 6. a4 c6 7. Bd2 a5 8. axb5
Bxc3 9. Bxc3 cxb5 10. b3 Bb7 11. bxc4 b4 12. Bb2 Nf6 13. Bd3 Nbd7 14. O-O O-O
15. Re1 Ne4 16. c5 Bc6 17. Qc2 f5 18. Bc4 Qe7 19. Bb3 Kh8 20. Red1 Qe8 21. Ba4
Bxa4 22. Rxa4 Ndf6 23. Rda1 Qb5 24. Ne5 Ng4 25. Nxg4 fxg4 26. Qxe4 Qe2 27. Rf1
Qxb2 28. Qxe6 b3 29. Qc4 Rf6 30. Qc1 Qe2 31. c6 b2 32. Qc4 Qd2 33. c7 Rc8 34.
Qb3 Rcf8 35. Qd1 Rxf2 0-1
```

### Output:

```
--- Analyzing Game #1 ---
  Date:  2015.02.15
  White: Kasimdzhanov,R
  Black: Grischuk,A
Game had No Errors, Valid Syntax

-----------------------------------------------------------------------------------

Simulating Game #1... SUCCESS!
--- Simulation Complete ---
```

### Output with Invalid Move:

```
--- Analyzing Game #1 ---
  Date:  2015.02.15
  White: Kasimdzhanov,R
  Black: Grischuk,A
Move #17: White tried illegal move: Qc2R
Game had Errors!
```

### Another Output with Invalid Move:

```
Simulating Game #1... --- Simulation Complete ---
FAILED! Reason:
Move #9.
Player that had error: BLACK
Skipping game
```
