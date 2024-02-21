    package com.example.myjavafx;

    import javafx.application.Application;
    import javafx.fxml.FXMLLoader;
    import javafx.geometry.Insets;
    import javafx.scene.Group;
    import javafx.scene.shape.Polygon;
    import javafx.scene.Scene;
    import javafx.stage.Stage;
    import javafx.scene.paint.Color;
    import javafx.scene.transform.Rotate;
    import javafx.scene.layout.GridPane;
    import javafx.scene.transform.Rotate;



    import java.io.IOException;

    public class HelloApplication extends Application {

            private static final double HEXAGON_SIZE =  100;
            private static final double HEXAGON_HEIGHT = Math.sqrt(3) * HEXAGON_SIZE /  2;

        @Override
        public void start(Stage primaryStage) {
            /*primaryStage.setTitle("Hexagon Group with Padding");
            GridPane gridPane = new GridPane();*/
            Scene scene = new Scene(gridPane,  800,  600, Color.WHITE);
/*
            int numRows =  5;
            int numCols =  5;

            for (int row =  0; row < numRows; row++) {
                for (int col =  0; col < numCols; col++) {
                    double x = col *  1.5 * HEXAGON_SIZE;
                    double y = row * HEXAGON_HEIGHT;
                    if (col %  2 ==  1) {
                        x =x + (HEXAGON_HEIGHT /  2);
                    }

                    Polygon hexagon = createHexagon(x, y, HEXAGON_SIZE);
                    gridPane.add(hexagon, col, row);
                }
            }

            // Set padding for the GridPane
            gridPane.setPadding(new Insets(50,  50,  50,  50));*/

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
            hexagon.setStroke(Color.BLACK);
            return hexagon;
        }

        public static void main(String[] args) {
            launch();}
        }
