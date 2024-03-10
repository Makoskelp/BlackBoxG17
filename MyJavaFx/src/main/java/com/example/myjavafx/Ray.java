package com.example.myjavafx;

public class Ray {
    private int[] pos;
    private int direction; //0 is NE, 1 is E, 2, is SE, 3 is SW, 4 is W, 5 is NW

    public Ray(int a, int r, int c, int dir) {
        pos[0] = a;
        pos[1] = r;
        pos[2] = c;
        direction = dir;
    }

    public int[] getCurrentPosition() {
        return pos;
    }

    public int getDirection() {
        return direction;
    }

    //amount = how many times to turn right 60deg; negative numbers turn left
    public void rotate(int amount) {
        direction = (direction + amount) % 6;
    }

    //updates position
    public void move(int a, int r, int c) {
        pos[0] = a;
        pos[1] = r;
        pos[2] = c;
    }

}
