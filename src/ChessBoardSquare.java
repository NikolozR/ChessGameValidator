public class ChessBoardSquare {
    private ChessPiece currentPiece;
    private final String coordinates;


    public ChessBoardSquare(String coordinates) {
        this.coordinates = coordinates;
        this.currentPiece = null;
    }

    public ChessBoardSquare(ChessPiece currentPiece, String coordinates) {
        this.currentPiece = currentPiece;
        this.coordinates = coordinates;
    }


    public ChessPiece getCurrentPiece() {
        return currentPiece;
    }

    public boolean isSquareEmpy() {
        return this.getCurrentPiece() == null;
    }

    public int getRank() {
        return Integer.parseInt(this.getCoordinates().substring(1));
    }

    public char getFile() {
        return this.getCoordinates().charAt(0);
    }

    public int getFileCharCode() {
        return (int) this.getCoordinates().charAt(0);
    }

    public String getCoordinates() {
        return coordinates;
    }


    public void setCurrentPiece(ChessPiece currentPiece) {
        this.currentPiece = currentPiece;
    }
}
