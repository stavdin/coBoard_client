package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class WhiteboardTextbox extends Rectangle implements WhiteboardShape {

    private Point first;
    private Point second;

    @Override
    public void setParameters(Point firstPoint, double x, double y) {
        first = new Point(firstPoint.getLocation());
        second = new Point();
        second.setLocation(x, y);

        setX(firstPoint.getX());
        setY(firstPoint.getY());

        setWidth(x - this.getX());
        setHeight(y - this.getY());
        setArcHeight(20.0);
        setArcWidth(30.0);

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
    public void whiteboardSetStroke(Color c) {
        this.setStroke(c);
    }

    @Override
    public void whiteboardSetFill(Color c) {
        this.setFill(c);
    }
}
