package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.stage.Window;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    public Main() throws URISyntaxException {
    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException, IOException, InterruptedException {
        primaryStage.setTitle("Co - board");
        MainScene scene = new MainScene(new Pane(), 800,800);
        primaryStage.setScene(scene);
//        primaryStage.setMinHeight(800);
//        primaryStage.setMinWidth(800);
//        primaryStage.setMaxHeight(800);
//        primaryStage.setMaxWidth(800);
        primaryStage.show();

    }


    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        launch(args);
    }
}
