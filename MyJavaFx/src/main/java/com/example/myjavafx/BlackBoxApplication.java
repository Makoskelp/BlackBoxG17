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

    // Sets the default size of each hexagon
    private static final double HEXAGON_SIZE = 50;
    private final int size; // Size variable
    private int score = 0;
    private int guessesMade = 0;

    public BlackBoxApplication(int size) {
        this.size = size;
    }

    @Override
    public void start(Stage newStage) {
        newStage.setTitle("BlackBox+");

        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);

        Pane rayPane = new Pane();
        rayPane.setMouseTransparent(true); // Make the overlay pane transparent to mouse events
        scene.setRoot(new StackPane(gridPane, rayPane)); // Add both gridPane and rayPane to the scene

        gridPane.setStyle("-fx-background-color: black;");

        Board board = new Board(size);

        int numRows = 2 * board.getSize();
        int numCols = 4 * board.getSize() - 2;

        board.createRandAtoms();

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols / 2; col++) {
                if (board.inBoard(row % 2, row / 2, col)) {
                    Polygon hexagon = createHexagon(1, 1, HEXAGON_SIZE);

                    Integer loopRow = row, loopCol = col;

                    if (board.isBorder(row % 2, row / 2, col)) {
                        hexagon.setFill(Color.GREEN);
                        hexagon.setOnMousePressed(e -> {
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
                                for (int i = 0; i < 6; i++) {
                                    Integer j = i;
                                    int[] pos = board.getDirNeighbourPos(loopRow % 2, loopRow / 2, loopCol, i);
                                    if (pos != null && board.inBoard(pos[0], pos[1], pos[2]) && getHexagon(pos[1] * 2 + pos[0], 2 * pos[2] + pos[0], gridPane) != null) {
                                        Polygon thisHex = getHexagon(pos[1] * 2 + pos[0], 2 * pos[2] + pos[0], gridPane);

                                        Paint prevColour = thisHex.getFill();
                                        EventHandler<? super MouseEvent> prevEventHandler = thisHex.getOnMousePressed();

                                        if(thisHex.getFill() != Color.ORANGE)
                                        thisHex.setFill(Color.YELLOW);
                                        thisHex.setOnMousePressed(f -> {
                                            board.sendRay(loopRow % 2, loopRow / 2, loopCol, j, rayPane, gridPane);
                                            System.out.println("sent ray from " + loopRow % 2 + "," + loopRow / 2 + "," + loopCol + " in direction " + j);
                                            if(thisHex.getFill() != Color.ORANGE)
                                            thisHex.setFill(prevColour);
                                            thisHex.setOnMousePressed(prevEventHandler);
                                            score++;
                                        });
                                    }
                                }
                            }
                        });
                    } else {
                        hexagon.setOnMousePressed(e -> {
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
                    if(board.hasAtom(loopRow % 2, loopRow / 2, loopCol))
                    {
                        hexagon.setFill(Color.RED);
                    }
                }
            }
        }

        Label messageLabel = new Label("Welcome"); // Creating a label with default text
        // Adding the label above the button with alignment and styling
        gridPane.add(messageLabel, 0, numRows - 3, numCols, 1); // Spanning across all columns
        GridPane.setHalignment(messageLabel, HPos.LEFT); // Aligning label text to the center
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: white;"); // Setting font size and text color


        Button button = new Button("Click Me"); // Creating a button with text "Click Me"
        button.setOnAction(e -> {
            if (guessesMade < size) {
                messageLabel.setText("Please make " + size + " guesses");
            } else {
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols / 2; col++) {
                        Polygon hexagon = getHexagon((row / 2) * 2 + (row % 2), 2 * col + (row % 2), gridPane);
                        if (hexagon != null) {
                            hexagon.setFill(Color.BLACK);
                        }
                    }
                }
                messageLabel.setText("Score: " + score);
                for (int row = 0; row < numRows; row++) {
                    for (int col = 0; col < numCols / 2; col++) {
                        Polygon hexagon = getHexagon((row / 2) * 2 + (row % 2), 2 * col + (row % 2), gridPane);
                        if(board.hasAtom(row % 2, row / 2, col))
                        {
                            hexagon.setFill(Color.RED);
                        }
                    }
                }
            }
        });

        // Add the button to the layout (GridPane in your case)
        gridPane.add(button, 0, numRows + 1); // Adding the button below the gridPane

        // Set padding for the GridPane
        gridPane.setPadding(new Insets(50, 75, 50, 50));

        gridPane.setHgap(-1.15 * HEXAGON_SIZE - 2.5);
        gridPane.setVgap(-0.21 * HEXAGON_SIZE - 2.5);

        newStage.setScene(scene);
        newStage.show();
    }

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
