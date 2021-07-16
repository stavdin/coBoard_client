package sample;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
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
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        Text text = new Text("Welcome to co - board");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(text, 0, 0, 2, 1);
        joinRoomButton = new Button("join room");
        createRoomButton = new Button("create room");
        this.add(joinRoomButton,0,1);
        this.add(createRoomButton, 1, 1);
    }


}
