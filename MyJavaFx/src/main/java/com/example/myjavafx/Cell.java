package com.example.myjavafx;


    //Cell is used to represent each hexagon


public class Cell {

    //stores whether the cell has an atom in it
    private boolean hasAtom;

    //stores whether the cell is at the edge of the board and a ray can be sent starting from this cell
    private boolean isBorder;

    //for public access
    public boolean hasAtom() {
        return hasAtom;
    }

    //used to add an atom into the cell
    public void setAtom(boolean value) {
        hasAtom = value;
    }

    //for public access
    public boolean isBorder() {
        return isBorder;
    }

    //the default cell does not have an atom
    public Cell(boolean border) {
        hasAtom = false;
        isBorder = border;
    }

}
