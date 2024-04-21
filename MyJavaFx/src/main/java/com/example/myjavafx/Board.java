package com.example.myjavafx;

import java.util.Random;

//Board represents the board as an object
public class Board {

    private Cell[][][] board;
    private int size;

    public Board(int sideLength) {
        //Checks to see if the length of the board is equal or less than 0
        //If these board does not have a size or a negative size, we throw an exception
        if (sideLength <= 0) {
            throw new IllegalArgumentException("The board must have a positive size");
        }

        size = sideLength;
        //The board is created using the multiple cells
        board = new Cell[2][size][2 * size - 1];

        //Creates the top half of board
        for (int i = 0; i < size; i++) {
            int lineStart = size / 2 - (i + 1) / 2;
            for (int j = lineStart; j < lineStart + size + i; j++) {
                board[i % 2][i / 2][j] = new Cell(i == 0 || j == lineStart || j == lineStart + size + i - 1);
            }
        }
        //Creates the bottom half of board
        for (int i = 2 * size - 2; i >= size; i--) {
            int lineStart = size / 2 - (2 * size - i) / 2 + (1 - i % 2);
            for (int j = lineStart; j < lineStart + 3 * size - 2 - i; j++) {
                board[i % 2][i / 2][j] = new Cell(i == 2 * size - 2 || j == lineStart || j == lineStart + 3 * size - 3 - i);
            }
        }
    }

    //Returns the size of the board
    public int getSize() {
        return size;
    }

    //inBoard checks to see if a specific co-ordinate is within the boards boundaries
    public boolean inBoard(int a, int r, int c) {
        if (a >= 0 && a <= 1 && r >= 0 && r <= size - a && c >= 0 && c < 2 * size && board[a][r][c] != null) {
            return true;
        } else return false;
    }

    public void setAtom(int a, int r, int c, boolean value) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        board[a][r][c].setAtom(value);
    }

    public boolean hasAtom(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        return board[a][r][c].hasAtom();
    }

    public boolean isBorder(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        return board[a][r][c].isBorder();
    }

    public Cell getENeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c < 2 * size - 1 && inBoard(a, r, c + 1)) {
            return board[a][r][c + 1];
        } else {
            return null;
        }
    }

    public Cell getWNeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (c > 0 && inBoard(a, r, c - 1)) {
            return board[a][r][c - 1];
        } else {
            return null;
        }
    }

    public Cell getNENeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (inBoard(1 - a, r - (1 - a), c + a) && c + a <= 2 * size - 1 && r - (1 - a) >= 0) {
            return board[1 - a][r - (1 - a)][c + a];
        } else {
            return null;
        }
    }

    public Cell getNWNeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (inBoard(1 - a, r - (1 - a), c - (1 - a)) && c - (1 - a) >= 0 && r - (1 - a) >= 0) {
            return board[1 - a][r - (1 - a)][c - (1 - a)];
        } else {
            return null;
        }
    }

    public Cell getSENeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (inBoard(1 - a, r + a, c + a) && c + a <= 2 * size - 1 && r + a < size - a) {
            return board[1 - a][r + a][c + a];
        } else {
            return null;
        }
    }

    public Cell getSWNeighbour(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        if (inBoard(1 - a, r + a, c - (1 - a)) && c - (1 - a) >= 0 && r + a < size - a) {
            return board[1 - a][r + a][c - (1 - a)];
        } else {
            return null;
        }
    }

    private int[] getDirNeighbourPos(int a, int r, int c, int dir) {
        switch (dir) {
            case 0:
                if (inBoard(1 - a, r - (1 - a), c + a) && c + a <= 2 * size - 1 && r - (1 - a) >= 0) {
                    int[] pos = {1 - a, r - (1 - a), c + a};
                    return pos;
                } else {
                    return null;
                }
            case 1:
                if (c < 2 * size - 1 && inBoard(a, r, c + 1)) {
                    int[] pos = {a, r, c + 1};
                    return pos;
                } else {
                    return null;
                }
            case 2:
                if (inBoard(1 - a, r + a, c + a) && c + a <= 2 * size - 1 && r + a < size - a) {
                    int[] pos = {1 - a, r + a, c + a};
                    return pos;
                } else {
                    return null;
                }
            case 3:
                if (inBoard(1 - a, r + a, c - (1 - a)) && c - (1 - a) >= 0 && r + a < size - a) {
                    int[] pos = {1 - a, r + a, c - (1 - a)};
                    return pos;
                } else {
                    return null;
                }
            case 4:
                if (c > 0 && inBoard(a, r, c)) {
                    int[] pos = {a, r, c - 1};
                    return pos;
                } else {
                    return null;
                }
            case 5:
                if (inBoard(1 - a, r - (1 - a), c - (1 - a)) && c - (1 - a) >= 0 && r - (1 - a) >= 0) {
                    int[] pos = {1 - a, r - (1 - a), c - (1 - a)};
                    return pos;
                } else {
                    return null;
                }

            default:
                return null;
        }
    }

    private int[] getRayForwardCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], r.getDirection());
    }

    private Cell getRayForwardCell(Ray r) {
        if (getRayForwardCellPos(r) == null) {
            return null;
        } else
            return board[getRayForwardCellPos(r)[0]][getRayForwardCellPos(r)[1]][getRayForwardCellPos(r)[2]];
    }

    private int[] getRayRightCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() + 1) % 6);
    }

    private Cell getRayRightCell(Ray r) {
        if (getRayRightCellPos(r) == null) {
            return null;
        } else
            return board[getRayRightCellPos(r)[0]][getRayRightCellPos(r)[1]][getRayRightCellPos(r)[2]];
    }

    private int[] getRayLeftCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() - 1) % 6);
    }

    private Cell getRayLeftCell(Ray r) {
        if (getRayLeftCellPos(r) == null) {
            return null;
        } else
            return board[getRayLeftCellPos(r)[0]][getRayLeftCellPos(r)[1]][getRayLeftCellPos(r)[2]];
    }

    private int[] getRayBackRightCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() + 2) % 6);
    }

    private Cell getRayBackRightCell(Ray r) {
        if (getRayBackRightCellPos(r) == null) {
            return null;
        } else
            return board[getRayBackRightCellPos(r)[0]][getRayBackRightCellPos(r)[1]][getRayBackRightCellPos(r)[2]];
    }

    private int[] getRayBackLeftCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() - 2) % 6);
    }

    private Cell getRayBackLeftCell(Ray r) {
        if (getRayBackLeftCellPos(r) == null) {
            return null;
        } else
            return board[getRayBackLeftCellPos(r)[0]][getRayBackLeftCellPos(r)[1]][getRayBackLeftCellPos(r)[2]];
    }

    public void sendRay(int a, int r, int c, int dir) {
        Ray ray = new Ray(a, r, c, dir);

        //check ray absorbed
        if (getRayForwardCell(ray).hasAtom()) {
            //return absorbed ray marker

            //end of ray
            return;
        } else

            //check ray reflected on entry
            if (getRayBackLeftCell(ray).hasAtom() || getRayBackRightCell(ray).hasAtom() || getRayLeftCell(ray).hasAtom() || getRayRightCell(ray).hasAtom()) {
                //return reflected ray marker

                //end of ray
                return;
            }

        //start ray motion
        ray.move(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2]);

        while (getRayForwardCell(ray) != null) {
            if (getRayLeftCell(ray).hasAtom()) {
                //check ray reflected 180deg
                if (getRayRightCell(ray).hasAtom()) {
                    //return ray reflected ray marker

                    //end of ray
                    return;
                } else

                    //check ray deflected 120deg right
                    if (getRayForwardCell(ray).hasAtom()) {
                        ray.rotate(2);
                    } else

                        //ray deflected 60deg right
                        ray.rotate(1);

            } else if (getRayRightCell(ray).hasAtom()) {
                //check ray deflected 120deg left
                if (getRayForwardCell(ray).hasAtom()) {
                    ray.rotate(-2);
                } else

                    //ray deflected 60deg left
                    ray.rotate(-1);
            } else

                //check ray absorbed
                if (getRayForwardCell(ray).hasAtom()) {
                    //return absorbed ray marker

                    //stop ray
                    return;
                }

            //move ray
            ray.move(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2]);
        }
        //return regular ray marker

        //end of ray
        return;
    }

    public void createRandAtoms(int size) {

        Random rand = new Random();

        for (int i = 0; i < size; i++) {

            int num1 = rand.nextInt(0, 1);
            int num2 = rand.nextInt(0, size);
            int num3 = rand.nextInt(0, 2 * size - 1);

            while (!inBoard(num1, num2, num3)) {
                num1 = rand.nextInt(0, 1);
                num2 = rand.nextInt(0, size);
                num3 = rand.nextInt(0, 2 * size - 1);
            }
            while (board[num1][num2][num3].hasAtom()) {
                num1 = rand.nextInt(0, 1);
                num2 = rand.nextInt(0, size);
                num3 = rand.nextInt(0, 2 * size - 2);
            }
            board[num1][num2][num3].setAtom(true);
        }
    }


}
