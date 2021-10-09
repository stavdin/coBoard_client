package sample;


import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
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
    private ColorPicker colorPicker;
    private String chosenShape = "";
    private Point firstPoint = new Point();
    private Pane whiteBoard;
    private GridPane toolBar;
    private WhiteboardShape newShape;
    private String roomId;
    private String owner;
    private CheckBox fillShape;
    private boolean fill;
    private ChatBox chatbox;


    public RoomPage() {
        this.setPadding(new Insets(0, 115, 200, 133)); //center
        //this.setPadding(new Insets(0, 115, 115, 133)); //top
        this.setAlignment(Pos.TOP_LEFT);
        this.setHgap(10);
        this.setVgap(10);
        this.setMinSize(600, 600);
        this.setMaxSize(600, 600);


        configureToolBar();
        configureUndoAndRedo();
        configureChooseShapes();
        configureChooseColor();
        configureFillButton();
        configureWhiteBoard();
        configureChatbox();

    }


    private void configureChatbox() {
        chatbox = new ChatBox();
        this.add(chatbox, 1, 2);
    }

    public void waitForMessages(String roomId) {
        chatbox.setRoomId(roomId);
        MessageRetrieverService messageRetrieverService = new MessageRetrieverService(roomId);
        messageRetrieverService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                String message = messageRetrieverService.getValue();
                String sender = message.substring(0, message.indexOf(':'));
                if (!owner.equals(sender)) {
                    chatbox.addMessage(new MessageText(messageRetrieverService.getValue()));
                }

                messageRetrieverService.restart();
            }
        });
        messageRetrieverService.start();

    }

    private void configureToolBar() {
        toolBar = new GridPane();
        toolBar.setAlignment(Pos.TOP_RIGHT);
        toolBar.setHgap(25);
        toolBar.setBorder(new Border(new BorderStroke(Color.WHITE,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.add(toolBar, 1, 0);

    }

    private void configureFillButton() {
        fillShape = new CheckBox("fill shape");
        fillShape.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                fill = fillShape.isSelected();
            }
        });
        toolBar.add(fillShape, 3, 0);
        fillShape.setTextFill(Color.WHITE);


    }

    private void configureUndoAndRedo() {
        undo = new Button("undo");
        undo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roomId", roomId);
                jsonObject.put("owner", owner);
                HttpClient client = HttpClient.newHttpClient();
                try {
                    HttpRequest request = HttpRequest
                            .newBuilder()
                            .PUT(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))

                            .uri(new URI("http://localhost:8081/undo?roomId=" + roomId + "&owner=" + owner))
                            .build();
                    CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }
            }
        });

        redo = new Button("redo");
        redo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roomId", roomId);
                jsonObject.put("owner", owner);
                HttpClient client = HttpClient.newHttpClient();
                try {
                    HttpRequest request = HttpRequest
                            .newBuilder()
                            .PUT(HttpRequest.BodyPublishers.ofString(jsonObject.toString()))

                            .uri(new URI("http://localhost:8081/redo?roomId=" + roomId + "&owner=" + owner))
                            .build();
                    CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

                } catch (URISyntaxException uriSyntaxException) {
                    uriSyntaxException.printStackTrace();
                }

            }
        });
        toolBar.add(undo, 0, 0);
        toolBar.add(redo, 1, 0);
    }

    private void configureChooseShapes() {
        chooseShape = new MenuButton("choose shape");
        toolBar.add(chooseShape, 2, 0);
        MenuItem rectangle = new MenuItem("Rectangle");
        chooseShape.setText("Rectangle");
        chooseShape.setPrefSize(100, 20);
        chosenShape = "Rectangle";
        rectangle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Rectangle";
                //make sure the size of the button doesn't change
                chooseShape.setPrefSize(100, 20);
                chooseShape.setText("Rectangle");

            }
        });

        MenuItem circle = new MenuItem("Circle");

        circle.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Circle";
                chooseShape.setPrefSize(100, 20);
                chooseShape.setText("Circle");
            }
        });
        MenuItem line = new MenuItem("Line");
        line.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Line";
                chooseShape.setPrefSize(100, 20);
                chooseShape.setText("Line");
            }
        });
        MenuItem textBox = new MenuItem("TextBox");
        textBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "TextBox";
                chooseShape.setPrefSize(100, 20);
                chooseShape.setText("TextBox");
            }
        });

        chooseShape.getItems().add(rectangle);
        chooseShape.getItems().add(circle);
        chooseShape.getItems().add(line);
        chooseShape.getItems().add(textBox);

    }

    private void configureChooseColor() {
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        toolBar.add(colorPicker, 4, 0);
    }


    private void configureWhiteBoard() {

        whiteBoard = new Pane();
        whiteBoard.setMaxSize(500, 500);
        whiteBoard.setMinSize(500, 500);
        //set size
        // create a background fill
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);
        // create Background
        whiteBoard.setBackground(new Background(background_fill));
        whiteBoard.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));        //make sure min size works
        whiteBoard.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                var color = colorPicker.getValue();
                firstPoint.setLocation(mouseEvent.getX(), mouseEvent.getY());
                newShape = createShape(chosenShape);
                if (!fill) {
                    newShape.whiteboardSetStroke(color);
                    newShape.whiteboardSetFill(Color.WHITE);
                } else {
                    newShape.whiteboardSetStroke(color);
                    newShape.whiteboardSetFill(color);
                }
            }
        });


        whiteBoard.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //draw shape using current mouse event and first point without sending anything to db
                if (whiteBoard.getLayoutBounds().contains(mouseEvent.getX(), mouseEvent.getY())) {
                    newShape.setParameters(firstPoint, mouseEvent.getX(), mouseEvent.getY());
                    drawShape(newShape);
                }
            }
        });

        whiteBoard.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            // add pop up when release textbox
            public void handle(MouseEvent mouseEvent) {
                whiteBoard.getChildren().remove(newShape);

                sendCreateShapeRequest(newShape);
            }
        });
        this.add(whiteBoard, 1, 1);

    }

    private void drawShape(WhiteboardShape shape) {
        var children = whiteBoard.getChildren();
        if (!children.contains(shape)) {
            children.add((Shape) shape);
        }
    }


    private void sendCreateShapeRequest(WhiteboardShape shape) {
        //rename to sendCreateShapeRequest
        Color c = colorPicker.getValue();
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("owner", owner);
        jsonObj.put("shapeType", chosenShape);
        jsonObj.put("firstPointX", shape.getFirstPoint().getX());
        jsonObj.put("firstPointY", shape.getFirstPoint().getY());
        jsonObj.put("secondPointX", shape.getSecondPoint().getX());
        jsonObj.put("secondPointY", shape.getSecondPoint().getY());
        jsonObj.put("colorR", c.getRed());
        jsonObj.put("colorB", c.getBlue());
        jsonObj.put("colorG", c.getGreen());
        jsonObj.put("fill", fillShape.isSelected());
        jsonObj.put("roomId", roomId);
        jsonObj.put("enabled", true);

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
//            if ((boolean) responseJsonObject.get("shapeAdded")) {
//                shapeList.add(shape);
//            } else {
//                //TODO handle add shape failed by throwing the correct exception.
//                System.out.println("Error");
//                throw new RuntimeException("Shape not added");
//            }
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
        chatbox.setOwner(owner);
    }


    public void waitForChanges(String roomId) {
        //call to long polling code - client side
        ShapeRetrieverService shapeRetrieverService = new ShapeRetrieverService(roomId);
        shapeRetrieverService.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent t) {
                drawReturnedShapes(shapeRetrieverService.getValue(), shapeRetrieverService.isUndoOrRedo);
                shapeRetrieverService.restart();
            }
        });
        shapeRetrieverService.start();
    }

    private void drawReturnedShapes(JSONArray shapesToDrawJson, boolean isUndoOrRedo) {
        //deserialize JSONArray
        ArrayList<WhiteboardShape> shapesToDraw = new ArrayList<>(); //all of the shapes that we need to draw - some of which belong to other users.
        if (isUndoOrRedo) {
            //on undo or redo - we re draw everything
            whiteBoard.getChildren().clear();
        }

        for (int i = 0; i < shapesToDrawJson.length(); i++) {
            var shapeJson = (JSONObject) shapesToDrawJson.get(i);
            firstPoint.setLocation(shapeJson.getDouble("firstPointX"), shapeJson.getDouble("firstPointY"));
            double secondPointX = shapeJson.getDouble("secondPointX");
            double secondPointY = shapeJson.getDouble("secondPointY");
            boolean fillNewShape = shapeJson.getBoolean("fill");
            double r = shapeJson.getDouble("colorR");
            double g = shapeJson.getDouble("colorG");
            double b = shapeJson.getDouble("colorB");

            Color c = Color.rgb((int) (r * 255), (int) (g * 255), (int) (b * 255));
            //set up filters such as fill/color etc
            WhiteboardShape shapeToDraw = createShape(shapeJson.getString("shapeType"));
            shapeToDraw.setParameters(firstPoint, secondPointX, secondPointY);
            if (!fillNewShape) {
                newShape.whiteboardSetStroke(c);
                newShape.whiteboardSetFill(Color.WHITE);
            } else {
                newShape.whiteboardSetStroke(c);
                newShape.whiteboardSetFill(c);
            }
            shapeToDraw.setParameters(firstPoint, secondPointX, secondPointY);
            shapesToDraw.add(shapeToDraw);
        }

        // the JSONArray is sorted before it is sent to the client - so we do not need to sort here
        for (WhiteboardShape shape : shapesToDraw) {
            drawShape(shape);
        }
    }

    public WhiteboardShape createShape(String shapeString) {
        switch (shapeString) {
            case "Rectangle":
                newShape = new WhiteboardRectangle();
                break;
            case "Circle":
                newShape = new WhiteboardCircle();
                break;
            case "Line":
                newShape = new WhiteboardLine();
                break;
            case "TextBox":
                newShape = new WhiteboardTextbox();
                break;
            default:
                throw new RuntimeException("Unrecognized shape.");
        }
        return newShape;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}


//TODO:
// 1. (LAST) circle implementation change - the center of the circle is the middle point between the first and second click, this way we can easily calculate the radius and we will not draw over the whiteboard.
// 2. (LAST)unit test every single servlet - unit test formatting, etc.
// 3. (IMPORTANT)Textbox with pop - up in shapes.- not fill.
// 4. whenever we get a request in our servlets we check the validity of the parameters passed to us - either in body or url.
// for that we use validation and format checks - should be in app globals.
// ensure every argument we receive (body/url) is checked. if some are not - add validity check.
// for example - syncpoint cannot be negative, string cant be null or include special characters. password should...1

