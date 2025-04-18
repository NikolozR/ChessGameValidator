import io.ChessValidator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = args.length > 0 ? args[0] : "src/io/test.pgn";
        ChessValidator.run(path);
    }
}

