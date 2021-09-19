package sample;


import javafx.beans.Observable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.concurrent.atomic.AtomicReference;

public class LoginPage extends GridPane {

    public Button loginButton;
    //    public AtomicReference<String> inputEmail;
//    public AtomicReference<String> inputPassword;
    public TextField emailTextField;
    public PasswordField passwordField;
    public Button registerButton;

    public LoginPage() {
        this.setupLoginPage();
    }


    private void setupLoginPage() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setMinSize(400, 500);
        this.setMaxSize(400, 500);
        this.setMargin(this, new Insets(5, 5, 5, 200));
        Label text = new Label("Login");
        text.setPadding(new Insets(80, 0, 60, 0));
        text.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 70));
        text.setTextFill(Color.SLATEGRAY);
        this.add(text, 0, 0, 2, 1);

        Label userEmail = new Label("User Email:");
        this.add(userEmail, 0, 1);
        this.emailTextField = new TextField();
        this.add(emailTextField, 1, 1);


        Label userPassword = new Label("Password:");
        this.add(userPassword, 0, 2);
        Label addLine = new Label("");
        this.add(addLine, 0, 4);
        this.passwordField = new PasswordField();
        this.add(passwordField, 1, 2);
        this.loginButton = new Button("Login");
        loginButton.setStyle("-fx-base: LIGHTCYAN; -fx-font-size:14");
//        loginButton.setStyle("-fx-font-size:14");

        loginButton.setMaxSize(90, 40);
        loginButton.setMinSize(90, 40);
        loginButton.setTextFill(Color.GREEN);

        this.registerButton = new Button("Register");
        registerButton.setStyle("-fx-base: LIGHTCYAN; -fx-font-size:14");
        registerButton.setMaxSize(90, 40);
        registerButton.setMinSize(90, 40);
        registerButton.setTextFill(Color.GREEN);

        HBox hbBtn = new HBox(10);
        hbBtn.getChildren().add(loginButton);
        hbBtn.getChildren().add(registerButton);
        hbBtn.setSpacing(40);
        hbBtn.setAlignment(Pos.CENTER);
        this.add(hbBtn, 1, 5);

        this.setBorder(new Border(new BorderStroke[]{new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)}));
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        this.setBackground(new Background(new BackgroundFill[]{background_fill}));
        this.setAlignment(Pos.TOP_CENTER);
        AtomicReference<String> email = new AtomicReference<>();
        AtomicReference<String> password = new AtomicReference<>("");
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }
}
