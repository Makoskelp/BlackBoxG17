package com.example.myjavafx;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class BlackBoxApplication extends Application {

    //Sets the default size of each hexagon
    private static final double HEXAGON_SIZE = 50;
    private final int size; // Size variable

    public BlackBoxApplication(int size) {
        this.size = size;
    }

    //This is the first function to be called within this class
    //The start method is our main method to be run
    //We pass a stage in as a parameter, calling it newStage
    @Override
    public void start(Stage newStage) {

        //Sets a key or name to our stage
        newStage.setTitle("BlackBox+");

        //Creates a gridPane which is used to format the hexagons in our board
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);

        //Sets the background colour of the gridPane
        gridPane.setStyle("-fx-background-color: black;");

        //A new board is created with the size enetered by the user
        Board board = new Board(size);

        //Sets the number of rows and columns
        //numRows sets maximum number of rows used in the middle of the board
        //numCols sets the maximum number of columns in the board
        //As we are using a gridPane we do not need to use the total num of rows and num of columns each time
        int numRows = 2 * board.getSize();
        int numCols = 4 * board.getSize() - 2;

        board.createRandAtoms();

        //The outer for loop, stops once the number of rows has been hit
        //It also loops through the rows
        for (int row = 0; row < numRows; row++) {

            //The inner for loop checks to see if the number of columns have been reached
            //It also loops through the columns
            for (int col = 0; col < numCols / 2; col++) {

                //If checks to see if the point is included in the board
                if (board.inBoard(row % 2, row / 2, col)) {

                    //We call createHexagon with the parameters 1, 1 and Hexagon_Size
                    //Both x and y are 1 as this is where we want to centre the hexagon
                    //HEXAGON_SIZE is the size of the hexagon to be created
                    Polygon hexagon = createHexagon(1, 1, HEXAGON_SIZE);

                    //cast ints to Integers so they can be used in lambda function for mouse click
                    Integer loopRow = row, loopCol = col;

                    if (board.isBorder(row % 2, row / 2, col)) hexagon.setFill(Color.GREEN);

                    if (board.hasAtom(loopRow % 2, loopRow / 2, loopCol))
                    {
                        hexagon.setFill(Color.RED);
                    }

                    //hexagon clicked event
                    if (board.isBorder(row % 2, row / 2, col))
                    {
                        hexagon.setFill(Color.GREEN);
                        hexagon.setOnMouseClicked(e -> {
                            resetGrid();
                        });
                    }
                    gridPane.add(hexagon, 2 * col + row % 2, row);
                }
            }
        }

        // Set padding for the GridPane
        gridPane.setPadding(new Insets(50, 50, 50, 50));

        //The next 2 lines of code help us format the cells in a board display
        //Sets the horizontal gaps between the cells
        gridPane.setHgap(-1.15 * HEXAGON_SIZE - 2.5);

        //Sets the vertical gaps between the cells
        gridPane.setVgap(-0.21 * HEXAGON_SIZE - 2.5);

        newStage.setScene(scene);

        //Displays the board
        newStage.show();
    }

    //createHexagon creates the hexagon items to be displayed within the board
    //It is passed an x co-ordinate, a y co-ordinate and the radius or size of the hexagon
    private Polygon createHexagon(double x, double y, double radius) {
        //The hexagon is made using polygon objects
        Polygon hexagon = new Polygon();
        //We pivot or rotate the object so that it represents the hexagons with the point facing up
        hexagon.getTransforms().add(new Rotate(30, x + HEXAGON_SIZE / 2, y + HEXAGON_SIZE / 2));

        //A for loop is created to set the points of the hexagon
        //This loop should run 6 times to set all 6 points
        for (int i = 0; i < 6; i++) {

            //This calculates the angle for the current point
            //It directs where the point will be placed
            //This line insures that the points are evenly spaced around the hexagon
            double angle = 2.0 * Math.PI * i / 6;

            //These 2 lines places the points on the circumference of a circle created with the given radius
            //It uses a hypothetical circle around the centre of the hexagon to set the points
            double xPos = x + radius * Math.cos(angle);
            double yPos = y + radius * Math.sin(angle);

            //This adds the specific point to the hexagons points
            hexagon.getPoints().addAll(xPos, yPos);
        }

        //Sets the colour of each hexagon to black
        hexagon.setFill(Color.BLACK);

        //Sets the border or the stroke to yellow
        hexagon.setStroke(Color.YELLOW);

        //Returns the polygon object we have just made
        return hexagon;
    }

    private void resetGrid()
    {

    }
}
