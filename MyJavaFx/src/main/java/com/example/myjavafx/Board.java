package com.example.myjavafx;

public class Board {

    public Cell[][][] board;
    private int size;

    public Board(int sideLength) {
        if (sideLength <= 0) {
            throw new IllegalArgumentException("The board must have a positive size");
        }

        size = sideLength;
        board  = new Cell[2][size][2*size-1];

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

    private boolean inBoard(int a, int r, int c) {
        if (a < 0 || a > 1 || r < 0 || r > size-a || c < 0 || c > 2*size-1) {
            return false;
        }
        if (!board[a][r][c].isInBoard()) {
            return false;
        }
        return true;
    }

    public void addAtom(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        board[a][r][c].addAtom();
    }

    public Cell getENeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c < 2*size-1 && board[a][r][c+1].isInBoard()) {
            return board[a][r][c+1];
        } else {
            return null;
        }
    }

    public Cell getWNeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c > 0 && board[a][r][c].isInBoard()) {
            return board[a][r][c-1];
        } else {
            return null;
        }
    }

    public Cell getNENeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c+a <= 2*size-1 && r-(1-a) >= 0) {
            return board[1-a][r-(1-a)][c+a];
        } else {
            return null;
        }
    }

    public Cell getNWNeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if(c-(1-a) >= 0 && r-(1-a) >= 0) {
            return board[1-a][r-(1-a)][c-(1-a)];
        } else {
            return null;
        }
    }

    public Cell getSENeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c+a <= 2*size-1 && r+a < size-a) {
            return board[1-a][r+a][c+a];
        } else {
            return null;
        }
    }

    public Cell getSWNeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c-(1-a) >= 0 && r+a < size-a) {
            return board[1-a][r+a][c-(1-a)];
        } else {
            return null;
        }
    }

}
