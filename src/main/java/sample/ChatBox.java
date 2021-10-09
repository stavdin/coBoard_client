package sample;

import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.json.JSONObject;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class ChatBox extends BorderPane {

    private TextField textField;
    private Button sendMessageButton;
    private ScrollPane scrollPane;
    private HBox addMessageField;
    private String owner;
    private String roomId;
    private VBox vbox;

    public ChatBox() {
        this.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        this.setMinSize(500, 150);
        this.setMaxSize(500, 150);
        configureAddMessageField();
        configureScrollPaneAndVBox();
        this.setBottom(addMessageField);
        this.setTop(scrollPane);
    }

    private void configureAddMessageField() {
        addMessageField = new HBox();

        textField = new TextField();//label
        textField.setMinSize(450, 35);
        textField.setMaxSize(450, 35);

        sendMessageButton = new Button("send");
        sendMessageButton.setMinSize(48, 35);
        sendMessageButton.setMaxSize(48, 35);
        sendMessageButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //when send clicked -
                // take message from text field and send it to server
                // display it in messagePane (left)
                // clean text field
                //vbox.getChildren().add(new MessageText(owner + ": " + textField.getText()));
                String text = textField.getText();
                if (!(text == null || text.isEmpty())) {
                    String message = owner + ": " + textField.getText();
                    vbox.getChildren().add(new MessageText(owner + ": " + textField.getText()));
                    textField.setText("");
                    sendMessageRequest(roomId, message);
                }
            }
        });

        addMessageField.getChildren().addAll(textField, sendMessageButton);
    }

    private void configureScrollPaneAndVBox() {
        scrollPane = new ScrollPane();
        scrollPane.setMinSize(498, 115);
        scrollPane.setMaxSize(498, 115);

        vbox = new VBox();
        vbox.getChildren().addAll(scrollPane);
        vbox.setSpacing(5);
        scrollPane.setContent(vbox);
        vbox.heightProperty().addListener(observable -> scrollPane.setVvalue(1D));

    }

    public void addMessage(MessageText message) {
        vbox.getChildren().add(message);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void sendMessageRequest(String roomId, String message) {
        //rename to sendCreateShapeRequest
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("message", message);
        jsonObj.put("roomId", roomId);

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObj.toString()))

                    .uri(new URI("http://localhost:8081/sendMessage"))
                    .build();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = httpResponseCompletableFuture.get();
            System.out.println(response);
            System.out.println(response.body());
            JSONObject responseJsonObject = new JSONObject(response.body());
            if (!(boolean) responseJsonObject.get("messageSent")) {
                throw new RuntimeException("message was not sent");
            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
