package sample;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

import java.awt.*;


public class RoomPage extends GridPane {
    public Button undo;
    public Button redo;

    public MenuButton chooseShape;
    public MenuButton chooseColor;
    public TextField text;
    private ColorPicker colorPicker;
    private Color selectedColor;
    private String chosenShape = "";
    private boolean fillShape;
    private Pane whiteBoard;
    private GridPane toolBar;


    public RoomPage() {

        this.setPadding(new Insets(25, 25, 25, 25));
        // TODO: ensure size of all windows is big enough.
        // login page size is perfect. the room page size needs to be much bigger and include all of the whiteboard
        // black border for whiteboard
        this.setAlignment(Pos.TOP_RIGHT);
        this.setHgap(10);
        this.setVgap(10);
        this.setMinSize(800, 800);


        configureToolBar();
        configureUndoAndRedo();
        configureChooseShapes();
        configureChooseColor();
        configureFillButton();
        configureWhiteBoard();


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
        Button fillButton = new Button();
        fillButton.setText("Set fill shape");
        fillButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (fillButton.getText().equals("Set fill shape")) {
                    fillShape = true;
                    fillButton.setText("Set empty shape");
                } else {
                    fillShape = false;
                    fillButton.setText("Set fill shape");
                }

            }
        });
        toolBar.add(fillButton, 7, 0);
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
            }
        });

        MenuItem oval = new MenuItem("Oval");
        oval.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Oval";
            }
        });
        MenuItem square = new MenuItem("Square");
        square.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chosenShape = "Square";
            }
        });
        chooseShape.getItems().add(rectangle);
        chooseShape.getItems().add(oval);
        chooseShape.getItems().add(square);

    }

    private void configureChooseColor() {
        colorPicker = new ColorPicker();
        toolBar.add(colorPicker, 8, 0);
        colorPicker.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                selectedColor = colorPicker.valueProperty().get();
            }
        });

    }

    private void configureWhiteBoard() {
        whiteBoard = new Pane();
        //set size
        // create a background fill
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE,
                CornerRadii.EMPTY, Insets.EMPTY);
        // create Background
        whiteBoard.setBackground(new Background(background_fill));

        whiteBoard.setMinSize(200, 200);
        //make sure min size works
        //whiteboard should be positioned at the bottom
        whiteBoard.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
        whiteBoard.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

            }
        });
        this.add(whiteBoard, 0, 1);

    }
}
