
package sample;

import java.awt.*;

import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class WhiteboardTextArea extends Rectangle implements WhiteboardShape {
    private Point first;
    private Point second;
    private TextArea textArea;


    public TextArea getTextArea() {
        return textArea;

    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }


    @Override
    public void setParameters(Point firstPoint, double x, double y) {
        first = new Point(firstPoint.getLocation());
        second = new Point();
        second.setLocation(x, y);
        this.textArea = new TextArea();
        textArea.setPrefHeight(y - this.getY() - 3);
        textArea.setPrefWidth(x - this.getX() - 3);
        textArea.setLayoutX(firstPoint.getX());
        textArea.setLayoutY(firstPoint.getY());
        setX(firstPoint.getX());
        setY(firstPoint.getY());
        setWidth(x - this.getX());
        setHeight(y - this.getY());
        setArcHeight(20.0);
        setArcWidth(30.0);
        setFill(Color.TRANSPARENT);

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
    public void whiteboardSetStroke(javafx.scene.paint.Color c) {
        this.setStroke(c);

    }

    @Override
    public void whiteboardSetFill(javafx.scene.paint.Color c) {
        this.setFill(c);

    }
}