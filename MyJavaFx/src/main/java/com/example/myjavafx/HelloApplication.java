    package com.example.myjavafx;

    import javafx.application.Application;
    import javafx.geometry.Insets;
    import javafx.scene.shape.Polygon;
    import javafx.scene.Scene;
    import javafx.stage.Stage;
    import javafx.scene.paint.Color;
    import javafx.scene.transform.Rotate;
    import javafx.scene.layout.GridPane;



    public class HelloApplication extends Application {

            private static final double HEXAGON_SIZE =  50;

        @Override
        public void start(Stage primaryStage) {
            primaryStage.setTitle("Hexagon Group with Padding");
            GridPane gridPane = new GridPane();
            //GridPane gridPane2 = new GridPane();
            Scene scene = new Scene(gridPane,  1920,  1080, Color.BLACK);

            Board board = new Board(5);

            int numRows =  2*board.getSize();
            int numCols =  4*board.getSize()-2;

            for (int row =  0; row < numRows; row++) {
                for (int col =  0; col < numCols/2; col++) {
                    if (board.inBoard(row%2, row/2, col)) {
                        Polygon hexagon = createHexagon(1, 1, HEXAGON_SIZE);
                        gridPane.add(hexagon, 2*col+row%2, row);
                    }
                }
            }

            // Set padding for the GridPane
            gridPane.setPadding(new Insets(50,  50,  50,  50));
            gridPane.setHgap(-60);
            gridPane.setVgap(-13);

            primaryStage.setScene(scene);
            primaryStage.show();
        }

        private Polygon createHexagon(double x, double y, double radius) {
            Polygon hexagon = new Polygon();
            hexagon.getTransforms().add(new Rotate(30, x + HEXAGON_SIZE/2, y + HEXAGON_SIZE/2));
            for (int i =  0; i <  6; i++) {
                double angle =  2.0 * Math.PI * i /  6;
                double xPos = x + radius * Math.cos(angle);
                double yPos = y + radius * Math.sin(angle);
                hexagon.getPoints().addAll(xPos, yPos);
            }
            hexagon.setFill(Color.BLACK);
            hexagon.setStroke(Color.YELLOW);
            return hexagon;
        }

        public static void main(String[] args) {
            launch();}
        }
