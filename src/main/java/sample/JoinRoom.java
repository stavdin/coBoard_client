package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class JoinRoom extends GridPane {
    public TextField enterRoomNumber;
    public Button btn;

    public JoinRoom() {
        this.setupJoinRoom();
    }

    private void setupJoinRoom() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setPadding(new Insets(25, 25, 25, 25));
        Label roomId = new Label("roomId:");
        this.add(roomId, 0, 1);
        enterRoomNumber = new TextField();
        this.add(enterRoomNumber, 0, 1);
        this.btn = new Button("find ");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        this.add(hbBtn, 1, 4);
        this.add(btn,1,4);

    }

}
