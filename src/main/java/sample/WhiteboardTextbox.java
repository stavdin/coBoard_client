//package sample;
//
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//
//import java.awt.*;
//
//public class WhiteboardTextbox extends Rectangle implements WhiteboardShape {
//
//    private Point first;
//    private Point second;
//
//    @Override
//    public void setParameters(Point firstPoint, double x, double y) {
//        first = new Point(firstPoint.getLocation());
//        second = new Point();
//        second.setLocation(x, y);
//
//        setX(firstPoint.getX());
//        setY(firstPoint.getY());
//        setArcHeight(y - this.getY());
//        setArcWidth(x - this.getX());
//    }
//
//    @Override
//    public Point getFirstPoint() {
//        return null;
//    }
//
//    @Override
//    public Point getSecondPoint() {
//        return null;
//    }
//
//    @Override
//    public void whiteboardSetStroke(Color c) {
//
//    }
//
//    @Override
//    public void whiteboardSetFill(Color c) {
//
//    }
//}
