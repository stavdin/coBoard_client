package sample;

import javafx.scene.shape.Line;

import java.awt.*;

class WhiteboardLine extends Line implements WhiteboardShape {

    private Point first;
    private Point second;
    private int order;

    @Override
    public void setParameters(Point firstPoint, double x, double y, int order) {
        first = new Point(firstPoint.getLocation());
        second = new Point();
        second.setLocation(x, y);
        this.order = order;

        setStartX(firstPoint.getX());
        setStartY(firstPoint.getY());
        setEndX(second.getX());
        setEndY(second.getY());



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

