//package sample;
//
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.control.Button;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.text.Font;
//import javafx.scene.text.FontWeight;
//import javafx.scene.text.Text;
//
//public class RegisterPage extends GridPane {
//    public TextField emailTextField;
//    public PasswordField passwordField;
//    public Button registerButton;
//
//    public RegisterPage() {
//        this.setupRegisterPage();
//        registerButton = new Button("register");
//    }
//
//    private void setupRegisterPage(){
//        this.setAlignment(Pos.CENTER);
//        this.setHgap(10);
//        this.setVgap(10);
//        this.setPadding(new Insets(25, 25, 25, 25));
//        Text text = new Text("Register");
//        text.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        this.add(text, 0, 0, 2, 1);
//        Label userEmail = new Label("User Email:");
//
//        this.add(userEmail, 0, 1);
//        this.emailTextField = new TextField();
//        this.add(emailTextField, 1, 1);
//        Label pw = new Label("Password:");
//        this.add(pw, 0, 2);
//        this.passwordField = new PasswordField();
//        this.add(passwordField, 1, 2);
//        this.registerButton = new Button("Register");
//
//
//    }
//}
