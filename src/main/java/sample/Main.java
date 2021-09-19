package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;


public class Main extends Application {
    Stage primaryStage;



//    @Override
//    public void start(Stage primaryStage) throws URISyntaxException, IOException, InterruptedException {
//        primaryStage.setTitle("Co - board");
//        MainScene scene = new MainScene(new GridPane(),800,800);
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
//        primaryStage.show();
//
//    }

    @Override
    public void start(Stage primaryStage) throws URISyntaxException, IOException, InterruptedException {
        primaryStage.setTitle("Co - board");
        GridPane root = new GridPane();
        StackPane holder = new StackPane();
        Canvas canvas = new Canvas(800, 800);
        holder.getChildren().add(canvas);
        root.getChildren().add(holder);
//        holder.setStyle("-fx-background-color: mediumturquoise");
        holder.setStyle("-fx-background-color: radial-gradient(center 50% 50%, radius 50%, rgb(104, 163, 193) 0%, rgb(23, 56, 99) 100%);");

        MainScene scene = new MainScene(root,800,800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }




    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        launch(args);
    }
}
