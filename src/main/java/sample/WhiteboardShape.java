package sample;

import javafx.scene.Node;
import javafx.scene.shape.Shape;

import java.awt.*;

public interface WhiteboardShape {
    public void setParameters(Point firstPoint, double x, double y, int order);

    public Point getFirstPoint();

    public Point getSecondPoint();

    public int getShapeOrder();
}
