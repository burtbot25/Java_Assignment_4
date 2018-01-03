package q2;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * <p>Draws an equilateral triangle whose size is based on the mouse dragged and
 * draws a circle in the midpoint of the triangle.</p>
 *
 * @author Wilburt Herrera
 * @version 1.0
 */
public class DrawTriangle extends Application {
        
    /**
     * Constant used to obtain the 2nd point of the triangle at 120 degrees
     * from the origin.
     */
    private static final double SIN120 = Math.sin(Math.toRadians(120));
    
    /**
     * Constant used to obtain the 2nd point of the triangle at 120 degrees
     * from the origin.
     */
    private static final double NEG_SIN120 = -(Math.sin(Math.toRadians(120)));
    
    /**
     * Constant used to obtain the 2nd point of the triangle at 120 degrees
     * from the origin.
     */
    private static final double COS120 = Math.cos(Math.toRadians(120));
    
    /**
     * Constant used to obtain the 3rd point of the triangle at 240 degrees
     * from the origin.
     */
    private static final double SIN240 = Math.sin(Math.toRadians(240));
   
    /**
     * Constant used to obtain the 3rd point of the triangle at 240 degrees
     * from the origin.
     */
    private static final double NEG_SIN240 = -(Math.sin(Math.toRadians(240)));
    
    /**
     * Constant used to obtain the 3rd point of the triangle at 240 degrees
     * from the origin.
     */
    private static final double COS240 = Math.cos(Math.toRadians(240));
    
    /**
     * Radius of the centre circle.
     */
    private static final int CIRCLE_RADIUS = 3;
    
    /**
     * The contents of the application scene. 
     */
    private Group root;
    
    /** 
     * Circle to move to first mouse click location. 
     */
    private Circle atCenter;
    
    /**
     * Line for the first side of the triangle.
     */
    private Line side1;
    
    /**
     * Line for the second side of the triangle.
     */
    private Line side2;
    
    /**
     * Line for the third side of the triangle.
     */
    private Line side3;
   
    /**
     * Displays an initially empty scene, waiting for the user to draw lines
     * with the mouse.
     * 
     * @param primaryStage
     *            a Stage
     */
    public void start(Stage primaryStage) {
        root = new Group();
        final int appWidth = 800;
        final int appHeight = 500;
        Scene scene = new Scene(root, appWidth, appHeight, Color.BLACK);
        
        // Instantiation of the shapes.
        side1 = new Line(0, 0, 0, 0);
        side2 = new Line(0, 0, 0, 0);
        side3 = new Line(0, 0, 0, 0);
        atCenter = new Circle(0, 0, CIRCLE_RADIUS);
        
        // Methods to process mouse events
        scene.setOnMousePressed(this::processMousePress);
        scene.setOnMouseDragged(this::processMouseDrag);
        
        // Setting circle and line colors.
        atCenter.setFill(Color.CYAN);
        side1.setStroke(Color.CYAN);
        side2.setStroke(Color.CYAN);
        side3.setStroke(Color.CYAN);
        
        // Adding shapes to the root
        root.getChildren().add(atCenter);
        root.getChildren().add(side1);
        root.getChildren().add(side2);
        root.getChildren().add(side3);

        // Sets up primary stage.
        primaryStage.setTitle("Equilateral Triangle");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Draws a circle at the center of the triangle.
     * @param event invokes this method.
     */
    public void processMousePress(MouseEvent event) {
        
        // Creates a circle at the point of the mouse press.
        atCenter.setCenterX(event.getX());
        atCenter.setCenterY(event.getY());
    }
    
    /**
     * Draws an equilateral triangle with a size relative
     * to the current mouse location from the original click location.
     * @param event invokes this method.
     */
    public void processMouseDrag(MouseEvent event) {
        
        
        // Used to offset points of the triangle within the window.
        double centerX = atCenter.getCenterX();
        double centerY = atCenter.getCenterY();
        
        /*
         * Set of vertices used to find the 2nd and 3rd
         * set of points of the triangle.
         */
        double point1X = event.getX() - centerX;
        double point1Y = event.getY() - centerY;
        
        /*
         * Places the 2nd set of vertices at a rotation 
         * of 120 degrees from the origin.
         */
        double point2X = (COS120 * point1X) + (SIN120 * point1Y) + centerX;
        double point2Y = (NEG_SIN120 * point1X) + (COS120 * point1Y) + centerY;
        
        /*
         * Places the 3rd set of vertices at a rotation 
         * of 240 degrees from the origin.
         */
        double point3X = (COS240 * point1X) + (SIN240 * point1Y) + centerX;
        double point3Y = (NEG_SIN240 * point1X) + (COS240 * point1Y) + centerY;
        
        // Assigning coordinates for the 1st line.
        side1.setStartX(event.getX());
        side1.setStartY(event.getY());
        side1.setEndX(point2X);
        side1.setEndY(point2Y);
        
        // Assigning coordinates for the 2nd line.
        side2.setStartX(point2X);
        side2.setStartY(point2Y);
        side2.setEndX(point3X);
        side2.setEndY(point3Y);
        
        // Assigning coordinates for the 3rd line.
        side3.setStartX(point3X);
        side3.setStartY(point3Y);
        side3.setEndX(event.getX());
        side3.setEndY(event.getY());
    }

    /**
     * Launches the JavaFX application.
     * 
     * @param args
     *            command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

