public class Cell {
    private boolean inBoard;
    private boolean hasAtom;

    public boolean hasAtom() {
        return hasAtom;
    }

    public void addAtom() {
        hasAtom = true;
    }

    public boolean isInBoard() {
        return inBoard;
    }

    public void addToBoard() {
        inBoard = true;
    }

    public Cell() {
        hasAtom = false;
        inBoard = false;
    }
}
