package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class WhiteboardRectangle extends Rectangle implements WhiteboardShape {

    private Point first;
    private Point second;

    @Override
    public void setParameters(Point firstPoint, double x, double y) {
        first = new Point(firstPoint.getLocation());
        second = new Point();
        second.setLocation(x, y);

        setX(firstPoint.getX());
        setY(firstPoint.getY());
        setHeight(y - this.getY());
        setWidth(x - this.getX());
    }

    @Override
    public void whiteboardSetStroke(Color c) {
        this.setStroke(c);
    }

    @Override
    public void whiteboardSetFill(Color c) {
        this.setFill(c);
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
