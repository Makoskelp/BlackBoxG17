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
            return false;
        }

        return board[a][r][c].hasAtom();
    }

    public boolean isBorder(int a, int r, int c) {
        if (!inBoard(a, r, c)) {
            throw new IllegalArgumentException("Not a valid cell");
        }

        return board[a][r][c].isBorder();
    }

    public int[] getDirNeighbourPos(int a, int r, int c, int dir) {
        switch (dir) {
            case 0:
                if (inBoard(1 - a, r - (1 - a), c + a) && c + a <= 2 * size - 1 && r - (1 - a) >= 0) {
                    int[] pos = {1 - a, r - (1 - a), c + a};
                    return pos;
                } else {
                    return new int[] {0,0,0};
                }
            case 1:
                if (c < 2 * size - 1 && inBoard(a, r, c + 1)) {
                    int[] pos = {a, r, c + 1};
                    return pos;
                } else {
                    return new int[] {0,0,0};
                }
            case 2:
                if (inBoard(1 - a, r + a, c + a) && c + a <= 2 * size - 1 && r + a < size - a) {
                    int[] pos = {1 - a, r + a, c + a};
                    return pos;
                } else {
                    return new int[] {0,0,0};
                }
            case 3:
                if (inBoard(1 - a, r + a, c - (1 - a)) && c - (1 - a) >= 0 && r + a < size - a) {
                    int[] pos = {1 - a, r + a, c - (1 - a)};
                    return pos;
                } else {
                    return new int[] {0,0,0};
                }
            case 4:
                if (c > 0 && inBoard(a, r, c)) {
                    int[] pos = {a, r, c - 1};
                    return pos;
                } else {
                    return new int[] {0,0,0};
                }
            case 5:
                if (inBoard(1 - a, r - (1 - a), c - (1 - a)) && c - (1 - a) >= 0 && r - (1 - a) >= 0) {
                    int[] pos = {1 - a, r - (1 - a), c - (1 - a)};
                    return pos;
                } else {
                    return new int[] {0,0,0};
                }

            default:
                return new int[] {0,0,0};
        }
    }

    private int[] getRayForwardCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], r.getDirection());
    }

    private int[] getRayRightCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() + 1) % 6);
    }

    private int[] getRayLeftCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() - 1) % 6);
    }

    private int[] getRayBackRightCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() + 2) % 6);
    }

    private int[] getRayBackLeftCellPos(Ray r) {
        return getDirNeighbourPos(r.getCurrentPosition()[0], r.getCurrentPosition()[1], r.getCurrentPosition()[2], (r.getDirection() - 2) % 6);
    }

    public void sendRay(int a, int r, int c, int dir) {
        Ray ray = new Ray(a, r, c, dir);

        //check ray absorbed
        if (hasAtom(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2])) {
            //return absorbed ray marker

            //end of ray
            return;
        } else

            //check ray reflected on entry
            if (hasAtom(getRayBackLeftCellPos(ray)[0], getRayBackLeftCellPos(ray)[1], getRayBackLeftCellPos(ray)[2]) ||
            hasAtom(getRayBackRightCellPos(ray)[0], getRayBackRightCellPos(ray)[1], getRayBackRightCellPos(ray)[2]) ||
            hasAtom(getRayLeftCellPos(ray)[0], getRayLeftCellPos(ray)[1], getRayLeftCellPos(ray)[2]) ||
            hasAtom(getRayRightCellPos(ray)[0], getRayRightCellPos(ray)[1], getRayRightCellPos(ray)[2])) {
                //return reflected ray marker

                //end of ray
                return;
            }

        //start ray motion
        ray.move(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2]);

        while (inBoard(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2])) {
            if (hasAtom(getRayLeftCellPos(ray)[0], getRayLeftCellPos(ray)[1], getRayLeftCellPos(ray)[2])) {
                //check ray reflected 180deg
                if (hasAtom(getRayRightCellPos(ray)[0], getRayRightCellPos(ray)[1], getRayRightCellPos(ray)[2])) {
                    //return ray reflected ray marker

                    //end of ray
                    return;
                } else

                    //check ray deflected 120deg right
                    if (hasAtom(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2])) {
                        ray.rotate(2);
                    } else

                        //ray deflected 60deg right
                        ray.rotate(1);

            } else if (hasAtom(getRayRightCellPos(ray)[0], getRayRightCellPos(ray)[1], getRayRightCellPos(ray)[2])) {
                //check ray deflected 120deg left
                if (hasAtom(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2])) {
                    ray.rotate(-2);
                } else

                    //ray deflected 60deg left
                    ray.rotate(-1);
            } else

                //check ray absorbed
                if (hasAtom(getRayForwardCellPos(ray)[0], getRayForwardCellPos(ray)[1], getRayForwardCellPos(ray)[2])) {
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

    public void createRandAtoms() {

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
            while (hasAtom(num1, num2, num3)) {
                num1 = rand.nextInt(0, 1);
                num2 = rand.nextInt(0, size);
                num3 = rand.nextInt(0, 2 * size - 2);
            }
            board[num1][num2][num3].setAtom(true);
        }
    }


}
