package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.awt.*;


public class WhiteboardCircle extends Circle implements WhiteboardShape {
    private Point first;
    private Point second;
    private final int sizeOfPanel=500;

    @Override
    public void setParameters(Point firstPoint, double x, double y) {
        first = new Point(firstPoint.getLocation());
        second = new Point();
        second.setLocation(x, y);

        setCenterX((firstPoint.getX() + second.getX()) / 2);
        setCenterY((firstPoint.getY() + second.getY()) / 2);
        Double diffFromUp = getCenterY();
        Double diffFromRight = getCenterX();
        Double diffFromLeft=  sizeOfPanel- diffFromRight;
        Double diffFromDown= sizeOfPanel-diffFromUp;
        Double maxRadius = Math.min(diffFromDown,Math.min(diffFromLeft,Math.min(diffFromRight,diffFromUp)));
        Double radius = Math.sqrt(Math.pow(firstPoint.getX() - getCenterX(), 2) + Math.pow(firstPoint.getY() - getCenterY(), 2));
        setRadius(radius);
        if(radius > maxRadius){
            setRadius (maxRadius);
        }

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

    @Override
    public void whiteboardSetStroke(Color c) {
        this.setStroke(c);
    }


}