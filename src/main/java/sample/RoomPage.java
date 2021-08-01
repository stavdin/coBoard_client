package sample;


import com.sun.javafx.scene.paint.GradientUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class RoomPage extends GridPane {
    public Button undo;
    public Button redo;

    public MenuButton chooseShape;
    public MenuButton chooseColor;
    public TextField text;
    private ColorPicker colorPicker;
    private String chosenShape = "";
    private Point firstPoint;
    private Pane whiteBoard;
    private GridPane toolBar;
    private WhiteboardRectangle newShapeRectangle;
    private WhiteboardCircle newShapeCircle;
    private WhiteboardLine newShapeLine;
    private WhiteboardShape newShape;
    private ArrayList<WhiteboardShape> shapeList;
    private int shapeToken = 1;
    private int syncpoint = 0;
    private String roomId;
    private String owner;
    private CheckBox fillShape;

    public RoomPage() {
        this.setPadding(new Insets(25, 25, 25, 25));
        // TODO: ensure size of all windows is big enough.
        // login page size is perfect. the room page size needs to be much bigger and include all of the whiteboard
        // black border for whiteboard
        this.setAlignment(Pos.TOP_RIGHT);
        this.setHgap(10);
        this.setVgap(10);
        this.setMinSize(800, 800);
        this.setMaxSize(800, 800);


        configureToolBar();
        configureUndoAndRedo();
        configureChooseShapes();
        configureChooseColor();
        configureFillButton();
        configureWhiteBoard();

        shapeList = new ArrayList<WhiteboardShape>();

        text = new TextField();
        toolBar.add(text, 9, 0, 2, 3);

    }

    private void configureToolBar() {
        //tool bar should be positioned at the top of the page
        toolBar = new GridPane();
        toolBar.setAlignment(Pos.TOP_RIGHT);
        toolBar.setHgap(10);
        this.add(toolBar, 0, 0);
    }

    private void configureFillButton() {
        fillShape = new CheckBox("fill shape");
        toolBar.add(fillShape, 7, 0);
    }

    private void configureUndoAndRedo() {
        undo = new Button("undo");
        toolBar.add(undo, 1, 0);
        redo = new Button("redo");
        toolBar.add(redo, 3, 0);
    }

    private void configureChooseShapes() {
        chooseShape = new MenuButton("choose shape");
        toolBar.add(chooseShape, 5, 0);
        MenuItem rectangle = new MenuItem("Rectangle");
        rectangle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Rectangle";
                //make sure the size of the button doesn't change
                chooseShape.setText("Rectangle");
            }
        });

        MenuItem circle = new MenuItem("Circle");
        circle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Circle";
                chooseShape.setText("Circle");

            }
        });
        MenuItem line = new MenuItem("Line");
        line.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Line";
                chooseShape.setText("Line");

            }
        });
        chooseShape.getItems().add(rectangle);
        chooseShape.getItems().add(circle);
        chooseShape.getItems().add(line);

    }

    private void configureChooseColor() {
        colorPicker = new ColorPicker();
        toolBar.add(colorPicker, 8, 0);
    }


    private void configureWhiteBoard() {

        whiteBoard = new Pane();
        whiteBoard.setMaxSize(600,600);
        whiteBoard.setMinSize(600,600);
        //set size
        // create a background fill
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);
        // create Background
        whiteBoard.setBackground(new Background(background_fill));

        //make sure min size works
        //whiteboard should be positioned at the bottom
        whiteBoard.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                firstPoint = new Point();
                firstPoint.setLocation(mouseEvent.getX(), mouseEvent.getY());
                switch (chosenShape) {
                    case "Rectangle":
                        newShape = new WhiteboardRectangle();
                        break;
                    case "Circle":
                        newShape = new WhiteboardCircle();
                        break;
                    case "Line":
                        newShape = new WhiteboardLine();
                        break;
                    default:
                        throw new RuntimeException("Unrecognized shape.");
                }
            }
        });


        whiteBoard.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //draw shape using current mouse event and first point without sending anything to db
                if (whiteBoard.getLayoutBounds().contains(mouseEvent.getX(), mouseEvent.getY())) {
                    drawShape(newShape, mouseEvent.getX(), mouseEvent.getY());
                }
            }
        });

        whiteBoard.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (whiteBoard.getLayoutBounds().contains(mouseEvent.getX(), mouseEvent.getY())) {
                    drawShape(newShape, mouseEvent.getX(), mouseEvent.getY());
                }
                sendCreateShapeRequestAndUpdateShapeList(newShape);
                newShape = null;
                firstPoint = null;
            }
        });
        this.add(whiteBoard, 0, 1);

    }

    private void drawShape(WhiteboardShape shape, double x, double y) {
        shape.setParameters(firstPoint, x, y, shapeToken);
        var children = whiteBoard.getChildren();
        if (!children.contains(shape)) {
            // we need to make sure that every shape we use implements this:
            // javafx.scene.shape.Shape
            if(!fillShape.isSelected())
            {
                shape.whiteboardSetStroke(colorPicker.getValue());
                shape.whiteboardSetFill(Color.WHITE);
            }
            else
                {
                    shape.whiteboardSetStroke(colorPicker.getValue());
                    shape.whiteboardSetFill(colorPicker.getValue());
                }
            children.add((Shape) shape);
        }
    }


    private void drawShapeRectangle(WhiteboardRectangle shape, double x, double y) {
        shape.setParameters(firstPoint, x, y, shapeToken);
        var children = whiteBoard.getChildren();
        if (!children.contains(shape)) {
            // we need to make sure that every shape we use implements this:
            // javafx.scene.shape.Shape
            if (fillShape.isSelected()) {
                Color c = colorPicker.getValue();
                shape.setFill(c);
            } else {
                shape.setFill(Color.WHITE);

            }
            children.add((Shape) shape);
        }
    }

    private void sendCreateShapeRequestAndUpdateShapeList(WhiteboardShape shape) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("owner", owner);
        jsonObj.put("shapeType", chosenShape);
        jsonObj.put("firstPointX", shape.getFirstPoint().getX());
        jsonObj.put("firstPointY", shape.getFirstPoint().getY());
        jsonObj.put("secondPointX", shape.getSecondPoint().getX());
        jsonObj.put("secondPointY", shape.getSecondPoint().getY());
        jsonObj.put("shapeToken", shapeToken);
        jsonObj.put("roomId", roomId);

        // TODO change servlet string

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObj.toString()))

                    .uri(new URI("http://localhost:8081/shape"))
                    .build();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = httpResponseCompletableFuture.get();
            System.out.println(response);
            System.out.println(response.body());
            JSONObject responseJsonObject = new JSONObject(response.body());
            if ((boolean) responseJsonObject.get("shapeAdded")) {
                shapeToken ++;
                shapeList.add(shape);
            } else {
                //TODO handle add shape failed by throwing the correct exception.
                System.out.println("Error");
                throw new RuntimeException("Shape not added");
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void sendWaitingForChangesRequest(String roomId) {
        //add room id to the request body
        JSONObject jsonObj = new JSONObject();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(new URI("http://localhost:8081/waitForChanges?roomId=" + roomId))
                    .build();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseFromServer = httpResponseCompletableFuture.get();
            JSONObject changes = new JSONObject(responseFromServer.body());
            syncpoint = changes.getInt("newSyncpoint");
            drawReturnedChanges(changes.getJSONArray("shapesToDisplay"));
            //sendWaitingForChangesRequest(roomId);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
    }

    private void drawReturnedChanges(JSONArray changes) {
        ArrayList<WhiteboardShape> shapesToDraw = new ArrayList<>(); //all of the shapes that we need to draw - some of which belong to other users.
        ArrayList<WhiteboardShape> ownedShapes = new ArrayList<>(); //these are the shapes this client owns - therefore the client can only redu/undo these shapes

        for (int i = 0; i < changes.length(); i++) {
            var shapeJson = (JSONObject) changes.get(i);
            firstPoint.setLocation(shapeJson.getDouble("firstPointX"), shapeJson.getDouble("firstPointY"));
            double secondPointX = shapeJson.getDouble("secondPointX");
            double secondPointY = shapeJson.getDouble("secondPointY");
            int shapeToken = shapeJson.getInt("shapeToken");
            String owner = shapeJson.getString("owner");

            //set up filters such as fill/color etc

            switch (shapeJson.getString("shapetype")) {
                case "Rectangle":
                    WhiteboardShape shape = new WhiteboardRectangle();
                    shape.setParameters(firstPoint, secondPointX, secondPointY, shapeToken);
                    shapesToDraw.add(shape);
                    if (owner.equals(this.owner)) {
                        ownedShapes.add(shape);
                    }
                    break;
                default:
                    throw new RuntimeException("invalid shape saved in db");

            }

            //we hold two arraylists. one is for shapes we own, after this method is over, we will need to assign that arraylist to shapesList
            // which is held as a state variable. **we must sort it before the assignment: sort on user specific shape token**
            // in addition - we need to sort the entire shapesToDraw arraylist on the NON USER SPECIFIC shape token, and then loop over it and draw each shape.
//            shapesToDraw.sort(() => );
//            for (int j = 0; j < shapesToDraw.size(); j++) {
//                if(shapesToDraw.get(j).)
//            }
//            drawShape();
        }
    }

    public void setRoomId(String roomId)
    {
        this.roomId = roomId;
    }
}
//---------------------------------------------------------------------
// TODO the whiteboard in room page should be bigger, we need to set its maximum size to be the same as its minimum size,
// TODO fix -  circle shape gets out of the whiteBoard
// TODO give whiteboard a black/gray border


//-----------------------------------------------------------------

// TODO we need to add text box shape
//  TODO : add button "clear" to roomPage
// TODO: (this is probably hard) think about adding a free draw option - like paint

//-----------------------------------------------------------------
// TODO register button
// todo add text box;


//TODO STAV:
// 1. set buttons to be fixed size: if it is possible to set size and have the chosen shape diplayed on the button at the same time - great. if not, dont display chosen shape on button.
// 2. sizes - find a way to have the room page be in a different size than the other pages.
//    login/join room/etc should be small. roompage should be big.
//    also, make it so that the roomPage (pane/panel/whatever) cannot change its size - we want it to be fixed.
// 3. put a border on whiteboard. put border on buttons panel.
// 4. circle change implementation: the center of the circle is the middle point between the first and second click,
//    this way we can easily calculate the radius and we will not draw over the whiteboard.
// 5. change colorpicker default color to black
// 6. user registration

//TODO AMIT:
// 1. finish method draw returned shapes in room page. ensure that the shapes are being drawn when user exits and re opens the room.
// 2. ensure join a room and create a room both funciton correctly e.g: check 1. above applies when joining a room
// 3. finish handling long polling - try https://docs.oracle.com/javafx/2/threads/jfxpub-threads.htm example 5
// 4. preparation for redu/undo : add table NonDisplayedShapes in db
// 5. redo and undo - make servlets, ensure shapes are moved from one table to the other...
// 6. add free draw option on panel
// 7. add textbox shape
// 8. add delete shape - think of implementation