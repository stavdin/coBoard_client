package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.awt.*;

class WhiteboardLine extends Line implements WhiteboardShape {

    private Point first;
    private Point second;

    @Override
    public void setParameters(Point firstPoint, double x, double y) {
        first = new Point(firstPoint.getLocation());
        second = new Point();
        second.setLocation(x, y);

        setStartX(firstPoint.getX());
        setStartY(firstPoint.getY());
        setEndX(second.getX());
        setEndY(second.getY());


    }

    @Override
    public void whiteboardSetFill(Color c) {
        this.setFill(c);
    }

    @Override
    public void whiteboardSetStroke(Color c) {
        this.setStroke(c);
    }

    @Override
    public Point getFirstPoint() {
        return first;
    }

    @Override
    public Point getSecondPoint() {
        return second;
    }

}


