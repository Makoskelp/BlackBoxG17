package com.example.myjavafx;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import org.junit.Test;

import static org.junit.Assert.*;

public class BlackBoxApplicationTest {

    @Test
    public void testCreateHexagonPoints() {
        double x = 100.0;
        double y = 150.0;
        double radius = 50.0;

        BlackBoxApplication board1 = new BlackBoxApplication(6);

        Polygon hexagon = board1.createHexagon(x, y, radius);

        // Check the number of points (should be 6 pairs of coordinates for a hexagon)
        assertEquals(6 * 2, hexagon.getPoints().size());
    }

    @Test
    public void testCreateHexagonColorAndStroke() {
        double x = 100.0;
        double y = 150.0;
        double radius = 50.0;

        BlackBoxApplication board1 = new BlackBoxApplication(6);

        Polygon hexagon = board1.createHexagon(x, y, radius);

        // Check the colour of the hexagon
        assertEquals(Color.BLACK, hexagon.getFill());

        // Check the outline of the hexagon
        assertEquals(Color.YELLOW, hexagon.getStroke());
    }

    @Test
    public void testCreateHexagonRotation() {
        double x = 100.0;
        double y = 150.0;
        double radius = 50.0;

        BlackBoxApplication board1 = new BlackBoxApplication(6);

        Polygon hexagon = board1.createHexagon(x, y, radius);

        //  Checks the angle of the points in the hexagon
        assertEquals(60.0, hexagon.getTransforms().get(0).getTz(), 0.01);
    }

    @Test
    public void testRayLimitMetOverLimit() {

        BlackBoxApplication board1 = new BlackBoxApplication(6);

        board1.raysEntered = 2;
        board1.maxNumOfRays = 1;

        //Checks function if raysEntered variable is more than that of maxNumOfRays
        assertTrue(board1.rayLimitMet());
    }

    @Test
    public void testRayLimitMetUnderLimit() {

        BlackBoxApplication board1 = new BlackBoxApplication(6);

        board1.raysEntered = 1;
        board1.maxNumOfRays = 2;

        //Checks function if raysEntered variable is less that the maxNumOfRays
        assertFalse(board1.rayLimitMet());
    }

    @Test
    public void testRayLimitMetEqual() {

        BlackBoxApplication board1 = new BlackBoxApplication(6);

        board1.raysEntered = 2;
        board1.maxNumOfRays = 2;

        //Checks function if both variables are the same
        assertTrue(board1.rayLimitMet());
    }
    @Test
    public void testGetHexagonInvalidIndices() {
        BlackBoxApplication board1 = new BlackBoxApplication(6);

        // Create an empty GridPane
        GridPane emptyGridPane = new GridPane();

        // Call getHexagon with invalid indices
        Polygon result1 = board1.getHexagon(0, 0, emptyGridPane);
        Polygon result2 = board1.getHexagon(2, 1, emptyGridPane);

        // Verify that null is returned for invalid indices
        assertNull(result1);
        assertNull(result2);
    }



}
