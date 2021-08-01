package sample;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

import java.awt.*;

public interface WhiteboardShape {
    public void setParameters(Point firstPoint, double x, double y, int order);

    public Point getFirstPoint();

    public Point getSecondPoint();

    public void whiteboardSetStroke(Color c);
    public void whiteboardSetFill(Color c);
    public int getShapeOrder();
}
