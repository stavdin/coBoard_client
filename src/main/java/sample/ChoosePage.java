package sample;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class ChoosePage extends GridPane {
    public Button joinRoomButton;
    public Button createRoomButton;

    public ChoosePage() {
        this.setupChoosePage();
    }

    public void setupChoosePage() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setMinSize(400, 500);
        this.setMaxSize(400, 500);
        this.setMargin(this, new Insets(5, 5, 5, 200));
        this.setBorder(new Border(new BorderStroke[]{new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)}));
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        this.setBackground(new Background(new BackgroundFill[]{background_fill}));
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        Label text = new Label("What would you \n \t \t like to do?");

        text.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 36));
        text.setPadding(new Insets(50,0,90,0));
        //text.setFill(Color.SLATEGRAY);
        text.setTextFill(Color.SLATEGRAY);
        this.add(text, 0, 0, 2, 1);
        joinRoomButton = new Button("join room");
        createRoomButton = new Button("create room");
        joinRoomButton.setStyle("-fx-base: LIGHTCYAN; -fx-font-size:14");
        createRoomButton.setStyle("-fx-base: SEASHELL; -fx-font-size:14");
        joinRoomButton.setMaxSize(100, 40);
        joinRoomButton.setMinSize(100, 40);
        joinRoomButton.setTextFill(Color.GREEN);
        createRoomButton.setTextFill(Color.SIENNA);
        createRoomButton.setMaxSize(100, 40);
        createRoomButton.setMinSize(100, 40);

        HBox btns = new HBox();
        btns.setAlignment(Pos.CENTER);
        btns.setPadding(new Insets(0,0,0,48));
        btns.getChildren().addAll(joinRoomButton, createRoomButton);
        btns.setSpacing(40);
        this.add(btns, 0, 1);
        //this.add(joinRoomButton, 0, 1);
        //this.add(createRoomButton, 1, 1);
    }

}
