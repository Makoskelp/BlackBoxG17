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
    
        private static final double HEXAGON_SIZE = 50;
        private final int size; // Size variable
    
        public BlackBoxApplication(int size) {
            this.size = size;
        }
    
        @Override
        public void start(Stage newStage) {
            newStage.setTitle("BlackBox+");
            GridPane gridPane = new GridPane();
            Scene scene = new Scene(gridPane);
            gridPane.setStyle("-fx-background-color: black;");
    
            Board board = new Board(size);
    
            int numRows = 2 * board.getSize();
            int numCols = 4 * board.getSize() - 2;
    
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols / 2; col++) {
                    if (board.inBoard(row % 2, row / 2, col)) {
                        Polygon hexagon = createHexagon(1, 1, HEXAGON_SIZE);
                        Integer loopRow = row, loopCol = col;
                        hexagon.setOnMouseClicked(event -> {
                            hexagon.setFill(Color.RED);
                            board.setAtom(loopRow%2, loopRow/2, loopCol, true);
                        });
                        gridPane.add(hexagon, 2 * col + row % 2, row);
                    }
                }
            }
    
            // Set padding for the GridPane
            gridPane.setPadding(new Insets(50, 50, 50, 50));
            gridPane.setHgap(-1.15*HEXAGON_SIZE-2.5);
            gridPane.setVgap(-0.21*HEXAGON_SIZE-2.5);
    
            newStage.setScene(scene);
            newStage.show();
        }
    
        private Polygon createHexagon(double x, double y, double radius) {
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
    }
