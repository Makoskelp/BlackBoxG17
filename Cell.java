public class Cell {
    private boolean inBoard;
    private boolean hasAtom;

    public boolean hasAtom() {
        return hasAtom;
    }

    public void setAtom(boolean value) {
        hasAtom = value;
    }

    public boolean isInBoard() {
        return inBoard;
    }

    public Cell(boolean isInBoard) {
        hasAtom = false;
        inBoard = isInBoard;
    }
}
