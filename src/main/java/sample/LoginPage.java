package sample;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.concurrent.atomic.AtomicReference;

public class LoginPage extends GridPane {

    public Button loginButton;
    public AtomicReference<String> inputEmail;
    public AtomicReference<String> inputPassword;
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
//        this.setMinSize(300,300);
//        this.setMaxSize(300,300);
        this.setPadding(new Insets(25, 25, 25, 25));
        Text text = new Text("Login");
        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        this.add(text, 0, 0, 2, 1);
        Label userEmail = new Label("User Email:");
        this.add(userEmail, 0, 1);
        this.emailTextField = new TextField();
        this.add(emailTextField, 1, 1);
        Label pw = new Label("Password:");
        this.add(pw, 0, 2);
        this.passwordField = new PasswordField();
        this.add(passwordField, 1, 2);
        this.loginButton = new Button("Login");
        this.registerButton = new Button("Register");
        //TODO add register button
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        hbBtn.getChildren().add(registerButton);
        this.add(hbBtn, 1, 4);

        AtomicReference<String> email = new AtomicReference<>();
        AtomicReference<String> password = new AtomicReference<>("");
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public PasswordField getPasswordField()
    {
        return passwordField;
    }

    public TextField getEmailTextField()
    {
        return emailTextField;
    }

    public Button getRegisterButton()
    {
        return registerButton;
    }
}
