package com.example.myjavafx;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class BlackBoxApplication extends Application {

    //how big the hexagons should be
    private static final double HEXAGON_SIZE = 50;
    //size of the board
    private final int size;
    //increases by one with every ray sent, and by 5 with every incorrectly guessed atom location
    private int score = 0;
    //must guess as many times as there are atoms
    private int guessesMade = 0;

    public BlackBoxApplication(int size) {
        this.size = size;
    }

    @Override
    public void start(Stage newStage) {
        //setup the scene
        newStage.setTitle("BlackBox+");

        //pane to display hexagonal grid
        GridPane gridPane = new GridPane();

        //seperate pane for rays, prevents formatting issues
        Pane rayPane = new Pane();

        Scene scene = new Scene(gridPane);

        rayPane.setMouseTransparent(true); //rays not clickable
        scene.setRoot(new StackPane(gridPane, rayPane));

        gridPane.setStyle("-fx-background-color: black;");

        //creating the board
        Board board = new Board(size);

        //how many rows and columns the board takes up, for use with GridPane
        int numRows = 2 * board.getSize();
        int numCols = 4 * board.getSize() - 2;

        //populates the board with atoms
        board.createRandAtoms();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols / 2; col++) {
                if (board.inBoard(row % 2, row / 2, col)) {
                    //adds the hexagons to the board
                    Polygon hexagon = createHexagon(1, 1, HEXAGON_SIZE);

                    //to pass into lambda functions
                    Integer loopRow = row, loopCol = col;

                    //enables sending rays from the border cells
                    if (board.isBorder(row % 2, row / 2, col)) {
                        hexagon.setFill(Color.GREEN);
                        hexagon.setOnMousePressed(e -> {
                            //right click to guess that there's an atom here
                            if (e.getButton() == MouseButton.SECONDARY)
                            {
                                if(hexagon.getFill() != Color.ORANGE)
                                {
                                    if(guessesMade < size)
                                    {
                                        guessesMade++;
                                        hexagon.setFill(Color.ORANGE);
                                        if (!board.hasAtom(loopRow % 2, loopRow / 2, loopCol)) {
                                            score += 5;
                                        }
                                    }   
                                }
                                //or to remove your guess
                                else
                                {
                                    guessesMade--;
                                    hexagon.setFill(Color.BLACK);
                                    if (!board.hasAtom(loopRow % 2, loopRow / 2, loopCol)) {
                                        score -= 5;
                                    }
                                }
                            }
                            else
                            {
                                clearBoard(board, gridPane, rayPane);
                                //set up each neighbouring cell to send a ray in their direction when they are clicked
                                for (int i = 0; i < 6; i++) {
                                    //for passing into lambda function
                                    Integer j = i;

                                    int[] pos = board.getDirNeighbourPos(loopRow % 2, loopRow / 2, loopCol, i);
                                    //checking it's a valid cell
                                    if (pos != null && board.inBoard(pos[0], pos[1], pos[2]) && getHexagon(pos[1] * 2 + pos[0], 2 * pos[2] + pos[0], gridPane) != null) {
                                        Polygon thisHex = getHexagon(pos[1] * 2 + pos[0], 2 * pos[2] + pos[0], gridPane);

                                        //to return the cell to its previous behaviour afterwards... only partially works
                                        Paint prevColour = thisHex.getFill();
                                        EventHandler<? super MouseEvent> prevEventHandler = thisHex.getOnMousePressed();

                                        //(non-guessed) cells become yellow to show they can be clicked
                                        if(thisHex.getFill() != Color.ORANGE)
                                        thisHex.setFill(Color.YELLOW);
                                        //send ray when clicked
                                        thisHex.setOnMousePressed(f -> {
                                            board.sendRay(loopRow % 2, loopRow / 2, loopCol, j, rayPane, gridPane);
                                            //(debug) System.out.println("sent ray from " + loopRow % 2 + "," + loopRow / 2 + "," + loopCol + " in direction " + j);
                                            //return to previous behaviour
                                            if(thisHex.getFill() != Color.ORANGE)
                                            thisHex.setFill(prevColour);
                                            thisHex.setOnMousePressed(prevEventHandler);
                                            //updates score
                                            score++;
                                        });
                                    }
                                }
                            }
                        });
                    } else {
                        hexagon.setOnMousePressed(e -> {
                            //right click to guess that there's an atom here
                            if (e.getButton() == MouseButton.SECONDARY)
                            {
                                if(hexagon.getFill() != Color.ORANGE)
                                {
                                    if(guessesMade < size)
                                    {
                                        guessesMade++;
                                        hexagon.setFill(Color.ORANGE);
                                        if (!board.hasAtom(loopRow % 2, loopRow / 2, loopCol)) {
                                            score += 5;
                                        }
                                    }   
                                }
                                //or to remove your guess
                                else
                                {
                                    guessesMade--;
                                    hexagon.setFill(Color.BLACK);
                                    if (!board.hasAtom(loopRow % 2, loopRow / 2, loopCol)) {
                                        score -= 5;
                                    }
                                }
                            }
                        });
                    }

                    gridPane.add(hexagon, 2 * col + row % 2, row);

                    // TEST - show genarated atoms
                    // if(board.hasAtom(loopRow % 2, loopRow / 2, loopCol))
                    // {
                    //     hexagon.setFill(Color.RED);
                    // }
                }
            }
        }

        //text on screen
        Label messageLabel = new Label("Welcome");

        gridPane.add(messageLabel, 0, numRows - 3, numCols, 1);
        GridPane.setHalignment(messageLabel, HPos.LEFT);
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;");

        //buttons to end game and show score, then play again
        Button button = new Button("Finish");
        Button playAgain = new Button("Play Again");
        button.setOnAction(e -> {
            if (guessesMade < size) {
                messageLabel.setText("Please make " + size + " guesses");
            } else {
                //clears board
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols / 2; col++) {
                        Polygon hexagon = getHexagon((row / 2) * 2 + (row % 2), 2 * col + (row % 2), gridPane);
                        if (hexagon != null) {
                            hexagon.setFill(Color.BLACK);
                        }
                    }
                }
                //shows score
                messageLabel.setText("Score: " + score);
                //shows atom locations
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols / 2; col++) {
                        Polygon hexagon = getHexagon((row / 2) * 2 + (row % 2), 2 * col + (row % 2), gridPane);
                        if(board.hasAtom(row % 2, row / 2, col))
                        {
                            hexagon.setFill(Color.RED);
                        }
                    }
                }
                rayPane.getChildren().clear();
                gridPane.add(playAgain, 2, numRows + 1);
            }
        });

        //to play again
        playAgain.setOnAction(e -> {
            BlackBoxApplication board2 = new BlackBoxApplication(size);
            newStage.close();
            board2.start(new Stage());
        });

        gridPane.add(button, 0, numRows + 1);

        //setting the layout
        gridPane.setPadding(new Insets(50, 75, 50, 50));
        gridPane.setHgap(-1.15 * HEXAGON_SIZE - 2.5);
        gridPane.setVgap(-0.21 * HEXAGON_SIZE - 2.5);

        newStage.setScene(scene);
        newStage.show();
    }

    //to make a hexagon
    public Polygon createHexagon(double x, double y, double radius) {
        Polygon hexagon = new Polygon();
        hexagon.getTransforms().add(new Rotate(30, x + HEXAGON_SIZE / 2, y + HEXAGON_SIZE / 2));

        for (int i = 0; i < 6; i++) {
            double angle = 2.0 * Math.PI * i / 6;
            double xPos = x + radius * Math.cos(angle);
            double yPos = y + radius * Math.sin(angle);
            hexagon.getPoints().addAll(xPos, yPos);
        }

        hexagon.setFill(Color.BLACK);
        hexagon.setStroke(Color.YELLOW);
        return hexagon;
    }

    //to get the hexagon object in the GridPane from its GridPane coordinates
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

    //to clear the board so there isn't a mess of sent rays visible
    public static void clearBoard(Board board, GridPane gridPane, Pane rayPane) {
        int numRows = 2 * board.getSize();
        int numCols = 4 * board.getSize() - 2;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols / 2; col++) {
                Polygon hexagon = getHexagon((row / 2) * 2 + (row % 2), 2 * col + (row % 2), gridPane);
                if (hexagon != null && hexagon.getFill() != Color.ORANGE) {
                    hexagon.setFill(Color.BLACK);
                }
            }
        }
        rayPane.getChildren().clear();
    }
}
