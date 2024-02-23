    package com.example.myjavafx;

    import javafx.application.Application;
    import javafx.geometry.Insets;
    import javafx.scene.Scene;
    import javafx.scene.layout.GridPane;
    import javafx.scene.paint.Color;
    import javafx.scene.shape.Polygon;
    import javafx.scene.transform.Rotate;
    import javafx.stage.Stage;
    
    public class HelloApplication extends Application {
    
        private static final double HEXAGON_SIZE = 50;
        private final int size; // Size variable
    
        public HelloApplication(int size) {
            this.size = size; // Initialize size through constructor
        }
    
        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("BlackBox+");
            GridPane gridPane = new GridPane();
            Scene scene = new Scene(gridPane, Color.BLACK);
    
            Board board = new Board(size);
    
            int numRows = 2 * board.getSize();
            int numCols = 4 * board.getSize() - 2;
    
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numCols / 2; col++) {
                    if (board.inBoard(row % 2, row / 2, col)) {
                        Polygon hexagon = createHexagon(1, 1, HEXAGON_SIZE);
                        gridPane.add(hexagon, 2 * col + row % 2, row);
                    }
                }
            }
    
            // Set padding for the GridPane
            gridPane.setPadding(new Insets(50, 50, 50, 50));
            gridPane.setHgap(-60);
            gridPane.setVgap(-13);
    
            primaryStage.setScene(scene);
            primaryStage.show();
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
