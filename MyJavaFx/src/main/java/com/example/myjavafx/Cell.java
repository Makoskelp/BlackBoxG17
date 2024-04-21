package com.example.myjavafx;


    //Cell is used to represent each hexagon


public class Cell {

    //hasAtom is a boolean variable which states if there is an atom inside this cell
    private boolean hasAtom;

    //isBorder is a boolean variable which states whether the cell is at the edge of the board
    private boolean isBorder;

    //Returns the value of hasAtom
    public boolean hasAtom() {
        return hasAtom;
    }

    //Used to add an atom into this cell
    public void setAtom(boolean value) {
        hasAtom = value;
    }

    //Returns the value of isBorder
    public boolean isBorder() {
        return isBorder;
    }

    //The default creation of this object has hasAtom set to false
    public Cell(boolean border) {
        hasAtom = false;
        isBorder = border;
    }

}
