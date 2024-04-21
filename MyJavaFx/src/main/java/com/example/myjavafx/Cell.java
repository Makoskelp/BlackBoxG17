package com.example.myjavafx;


    //Cell is used to represent each hexagon


public class Cell {

    //hasAtom is a boolean variable which states if there is an atom inside this cell
    private boolean hasAtom;

    //Returns the value of hasAtom
    public boolean hasAtom() {
        return hasAtom;
    }

    //Used to add an atom into this cell
    public void setAtom(boolean value) {
        hasAtom = value;
    }

    //The default creation of this object has hasAtom set to false
    public Cell() {
        hasAtom = false;
    }

}
