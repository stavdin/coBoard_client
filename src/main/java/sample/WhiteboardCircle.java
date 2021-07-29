package sample;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;

import java.awt.*;
import java.awt.geom.Point2D;

public class WhiteboardCircle extends Ellipse implements WhiteboardShape {
    private Point first;
    private Point second;
    private int order;
    private double distance;

    @Override
    public void setParameters(Point firstPoint, double x, double y, int order) {
        first = new Point(firstPoint.getLocation());
        second = new Point();
        second.setLocation(x, y);
        this.order = order;

        setCenterX(firstPoint.getX());
        setCenterY(first.getY());


        setRadiusX(Point2D.distance(firstPoint.getX(),firstPoint.getY(),second.getX(),second.getY()));
        setRadiusY(Point2D.distance(firstPoint.getX(),firstPoint.getY(),second.getX(),second.getY()));



    }

    @Override
    public Point getFirstPoint() {
        return first;
    }

    @Override
    public Point getSecondPoint() {
        return second;
    }

    @Override
    public int getShapeOrder() {
        return order;
    }
}
