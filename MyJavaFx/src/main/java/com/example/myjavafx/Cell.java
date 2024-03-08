package com.example.myjavafx;

public class Cell {
    private boolean hasAtom;

    public boolean hasAtom() {
        return hasAtom;
    }

    public void setAtom(boolean value) {
        hasAtom = value;
    }

    public Cell() {
        hasAtom = false;
    }
}
