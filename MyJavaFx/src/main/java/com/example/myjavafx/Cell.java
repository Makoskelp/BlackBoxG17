package com.example.myjavafx;

public class Cell {
    private boolean hasAtom;

    public boolean hasAtom() {
        return hasAtom;
    }

    public void addAtom() {
        hasAtom = true;
    }

    public Cell() {
        hasAtom = false;
    }
}
