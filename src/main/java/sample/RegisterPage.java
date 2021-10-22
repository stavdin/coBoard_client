package sample;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


public class RegisterPage extends GridPane {
    public TextField emailTextField;
    public PasswordField passwordField;
    public TextField nameTextField;
    public Button registerButton;

    public RegisterPage() {
        this.setupRegisterPage();

    }

    private void setupRegisterPage() {
        this.setAlignment(Pos.CENTER);
        this.setHgap(10);
        this.setVgap(10);
        this.setMinSize(400, 500);
        this.setMaxSize(400, 500);
        this.setBorder(new Border(new BorderStroke[]{new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)}));
        BackgroundFill background_fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        this.setBackground(new Background(new BackgroundFill[]{background_fill}));
        this.setAlignment(Pos.TOP_CENTER);
        this.setMargin(this, new Insets(5, 5, 5, 200));
        Label text = new Label("Registration");
        text.setPadding(new Insets(80, 0, 60, 0));
        text.setFont(Font.font("Arial Rounded MT Bold", FontWeight.BOLD, 40));
        text.setTextFill(Color.SLATEGRAY);
        this.add(text, 0, 0, 2, 1);

        Label userName = new Label("User Name");
        this.add(userName, 0, 1);
        nameTextField = new TextField();
        this.add(nameTextField, 1, 1);

        Label userEmail = new Label("User Email:");
        this.add(userEmail, 0, 2);
        this.emailTextField = new TextField();
        this.add(emailTextField, 1, 2);


        Label userPassword = new Label("Password:");
        this.add(userPassword, 0, 3);
        Label addLine = new Label("");
        this.add(addLine, 0, 4);
        this.passwordField = new PasswordField();
        this.add(passwordField, 1, 3);

        this.registerButton = new Button("Register");
        registerButton.setStyle("-fx-base: LIGHTCYAN; -fx-font-size:14");
        registerButton.setMaxSize(90, 40);
        registerButton.setMinSize(90, 40);
        registerButton.setTextFill(Color.GREEN);
        this.add(registerButton, 1, 5);


    }


    public PasswordField getPasswordField() {
        return passwordField;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }

    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getRegisterButton() {
        return registerButton;
    }
}
