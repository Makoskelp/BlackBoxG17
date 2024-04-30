package com.example.myjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.shape.Line;

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

        Pane overlayPane = new Pane();
        overlayPane.setMouseTransparent(true); // Make the overlay pane transparent to mouse events
        scene.setRoot(new StackPane(gridPane, overlayPane)); // Add both gridPane and overlayPane to the scene

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

                    //hexagon clicked event
                    if (board.isBorder(row % 2, row / 2, col))
                    {
                        hexagon.setFill(Color.GREEN);
                        hexagon.setOnMouseClicked(e -> {
                            clearBoard(board, gridPane, overlayPane);
                            for (int i = 0; i < 6; i++) {
                                Integer j = i;
                                int[] pos = board.getDirNeighbourPos(loopRow % 2, loopRow / 2, loopCol, i);
                                if (pos != null && board.inBoard(pos[0], pos[1], pos[2]) && getHexagon(pos[1]*2+pos[0], 2*pos[2]+pos[0], gridPane) != null) {
                                    
                                    Paint prevColour = getHexagon(pos[1]*2+pos[0], 2*pos[2]+pos[0], gridPane).getFill();
                                    EventHandler<? super MouseEvent> prevEventHandler = getHexagon(pos[1]*2+pos[0], 2*pos[2]+pos[0], gridPane).getOnMouseClicked();
                                
                                    getHexagon(pos[1]*2+pos[0], 2*pos[2]+pos[0], gridPane).setFill(Color.YELLOW);
                                    getHexagon(pos[1]*2+pos[0], 2*pos[2]+pos[0], gridPane).setOnMouseClicked(f -> {
                                        board.sendRay(loopRow % 2, loopRow / 2, loopCol, j, overlayPane, gridPane);
                                        System.out.println("sent ray from " + loopRow % 2 + "," + loopRow / 2 + "," + loopCol + " in direction " + j);
                                    
                                        getHexagon(pos[1]*2+pos[0], 2*pos[2]+pos[0], gridPane).setFill(prevColour);
                                        getHexagon(pos[1]*2+pos[0], 2*pos[2]+pos[0], gridPane).setOnMouseClicked(prevEventHandler);
                                    });
                                }
                            }
                        });
                    }

                    if (board.hasAtom(loopRow % 2, loopRow / 2, loopCol))
                    {
                        hexagon.setFill(Color.RED);
                    }

                    gridPane.add(hexagon, 2 * col + row % 2, row);
                }
            }
        }

        // Set padding for the GridPane
        gridPane.setPadding(new Insets(50, 75, 50, 50));

        //The next 2 lines of code help us format the cells in a board display
        //Sets the horizontal gaps between the cells
        gridPane.setHgap(-1.15 * HEXAGON_SIZE - 2.5);

        //Sets the vertical gaps between the cells
        gridPane.setVgap(-0.21 * HEXAGON_SIZE - 2.5);

        //drawRay(1,1,1,overlayPane,gridPane);

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

    public static Polygon getHexagon(final int row, final int col, GridPane gp) {
        Polygon node = null;
        ObservableList<Node> children = gp.getChildren();

        for (Node hexagon : children) {
            if (GridPane.getRowIndex(hexagon) == row && GridPane.getColumnIndex(hexagon) == col) {
                node = (Polygon) hexagon;
                break;
            }
        }
        return node;
    }

    public static void clearBoard(Board board, GridPane gridPane, Pane overlayPane)
    {
        int numRows = 2 * board.getSize();
        int numCols = 4 * board.getSize() - 2;
    
        for (int row = 0; row < numRows; row++)
        {
            for (int col = 0; col < numCols / 2; col++)
            {
                // Get the hexagon for the current row and column
                Polygon hexagon = getHexagon((row / 2)*2+(row % 2), 2*col+(row % 2), gridPane);
    
                // Check if hexagon is not null before setting its fill color
                if (hexagon != null) {
                    hexagon.setFill(Color.BLACK);
                }
            }
        }
        overlayPane.getChildren().clear();
    }
}
