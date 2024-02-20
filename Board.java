public class Board {

    public Cell[][][] board;
    private int size;

    public Board(int sideLength) {
        if (sideLength <= 0) {
            throw new IllegalArgumentException("The board must have a positive size");
        }

        size = sideLength;
        board  = new Cell[2][size][];

        //top half of board
        for (int i = 0; i < size; i++) {
            int lineStart = size/2-(i-1)/2;
            for (int j = lineStart; j < lineStart + size + i; j++) {
                board[i%2][i/2][j].addToBoard();
            }
        }
        //bottom half of board
        for (int i = size; i < 2*size-1; i++) {
            int lineStart = 2*(size-1)-i;
            for (int j = lineStart; j < lineStart + 3*size-1-i; j++) {
                board[i%2][i/2][j].addToBoard();
            }
        }
    }

    public void addAtom(int a, int r, int c) {
        if (a < 0 || a > 1 || r < 0 || r > size || c < 0 || c > 2*size-1) {
            throw new IllegalArgumentException("Coordinates outside of Board");
        }
        if (!board[a][r][c].isInBoard()) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        board[a][r][c].addAtom();
    }

    public Cell getENeighbour(int a, int r, int c) {
        if (a < 0 || a > 1 || r < 0 || r > size || c < 0 || c > 2*size-1) {
            throw new IllegalArgumentException("Coordinates outside of Board");
        }
        if (!board[a][r][c].isInBoard()) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c < 2*size-2 && board[a][r][c+1].isInBoard()) {
            return board[a][r][c+1];
        } else {
            return null;
        }
    }

    public Cell getWNeighbour(int a, int r, int c) {
        if (a < 0 || a > 1 || r < 0 || r > size || c < 0 || c > 2*size-1) {
            throw new IllegalArgumentException("Coordinates outside of Board");
        }
        if (!board[a][r][c].isInBoard()) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c > 1 && board[a][r][c].isInBoard()) {
            return board[a][r][c];
        } else {
            return null;
        }
    }

}