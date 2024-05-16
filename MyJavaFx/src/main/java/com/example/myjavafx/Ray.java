package com.example.myjavafx;

public class Ray {
    private int[] pos = new int[3];
    private int direction; //0 is NE, 1 is E, 2, is SE, 3 is SW, 4 is W, 5 is NW
    private int[] markerPos = new int[3];
    private int markerDir;

    public Ray(int a, int r, int c, int dir) {
        markerPos[0] = pos[0] = a;
        markerPos[1] = pos[1] = r;
        markerPos[2] = pos[2] = c;
        direction = dir;
        markerDir = Math.floorMod(dir + 3, 6);
    }

    public int[] getCurrentPosition() {
        return pos;
    }

    public int getDirection() {
        return direction;
    }

    public int[] getMarkerPos() {
        return markerPos;
    }

    public int getMarkerDir() {
        return markerDir;
    }

    //amount = how many times to turn right 60deg; negative numbers turn left
    public void rotate(int amount) {
        direction = Math.floorMod(direction + amount, 6);
    }

    //updates position
    public void move(int a, int r, int c) {
        pos[0] = a;
        pos[1] = r;
        pos[2] = c;
    }

}
