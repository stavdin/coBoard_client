package sample;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;


public class MainScene extends Scene {

    private LoginPage loginPage;
    private ChoosePage choosePage;
    private Pane rootPane;
    private RoomPage roomPage;
    private RegisterPage registerPage;

    public MainScene(Pane rootPane, int width, int height) {
        super(rootPane, width, height);
        this.loginPage = new LoginPage();
        this.rootPane = rootPane;
        this.rootPane.getChildren().add(this.loginPage);
        this.choosePage = new ChoosePage();
        this.roomPage = new RoomPage();
        this.registerPage = new RegisterPage();
        configureActions();

    }


    private void configureActions() {
        setupLoginPageClicked(loginPage.getLoginButton(), loginPage.getEmailTextField(), loginPage.getPasswordField(), loginPage.getRegisterButton());
        setupCreateRoomClicked(choosePage.createRoomButton);
        setupJoinRoomClicked(choosePage.joinRoomButton);
        setUpRegisterButtonClicked(registerPage.registerButton, registerPage.getNameTextField(), registerPage.getEmailTextField(), registerPage.passwordField);
    }

    private void setupLoginPageClicked(Button loginButton, TextField emailField, PasswordField passwordField, Button registerButton) {
        // do not expose all variables in loginPage. instead use getters. this method should only have two parameters - the atomic references.
        // action event
        loginButton.setOnAction(e -> {
            //Retrieving data
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("email", emailField.getText());
            jsonObj.put("password", passwordField.getText());
            HttpClient client = HttpClient.newHttpClient();
            try {
                HttpRequest request = HttpRequest
                        .newBuilder()
                        .POST(HttpRequest.BodyPublishers.ofString(jsonObj.toString()))
                        .uri(new URI("http://localhost:8081/userLogin"))
                        .build();
                CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> response = httpResponseCompletableFuture.get();
                System.out.println(response);
                System.out.println(response.body());
                JSONObject responeJsonObject = new JSONObject(response.body());
                if ((boolean) responeJsonObject.get("userFound")) {
                    roomPage.setOwner(emailField.getText());
                    rootPane.getChildren().add(choosePage);
                    rootPane.getChildren().remove(loginPage);
                } else {
                    System.out.println("Error");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error: your email/password is not correct");
                    alert.showAndWait();
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }

        });
        registerButton.setOnAction(e -> {
            rootPane.getChildren().add(registerPage);
            rootPane.getChildren().remove(loginPage);
        });

    }

    private void setUpRegisterButtonClicked(Button registerButton, TextField name, TextField email, TextField password) {
        registerButton.setOnAction(e -> {
            JSONObject jsonObj = new JSONObject();
                if(name.getText()!= ""&&email.getText()!=""&& password.getText()!="") {
                    jsonObj.put("name", name.getText());
                    jsonObj.put("email", email.getText());
                    jsonObj.put("password", password.getText());

                    HttpClient client = HttpClient.newHttpClient();
                    try {
                        HttpRequest request = HttpRequest
                                .newBuilder()
                                .POST(HttpRequest.BodyPublishers.ofString(jsonObj.toString()))
                                .uri(new URI("http://localhost:8081/createUser"))
                                .build();
                        CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                        HttpResponse<String> response = httpResponseCompletableFuture.get();
                        System.out.println(response);
                        System.out.println(response.body());
                        JSONObject responseFromServer = new JSONObject(response.body());
                        boolean userAdded = (boolean) responseFromServer.get("userAdded");
                        if (userAdded) {
                            rootPane.getChildren().add(loginPage);
                            rootPane.getChildren().remove(registerPage);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Success");
                            alert.setHeaderText(null);
                            alert.setContentText("Success: you are now added to Co-board: " + name);
                            alert.showAndWait();
                            //go to the next room

                        } else {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.initStyle(StageStyle.UTILITY);
                            alert.setTitle("Error");
                            alert.setHeaderText(null);
                            alert.setContentText("la dude");
                            alert.showAndWait();

                        }

                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    } catch (ExecutionException executionException) {
                        executionException.printStackTrace();
                    } catch (URISyntaxException uriSyntaxException) {
                        uriSyntaxException.printStackTrace();
                    }
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.UTILITY);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Error:please fill all the ");
                    alert.showAndWait();
                }
        });
    }


    private void setupCreateRoomClicked(Button button) {
        button.setOnAction(e -> {
            HttpClient client = HttpClient.newHttpClient();
            try {
                HttpRequest request = HttpRequest
                        .newBuilder()
                        .GET()
                        .uri(new URI("http://localhost:8081/createRoom?email=" + loginPage.getEmailTextField().getText()))
                        .build();
                CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                HttpResponse<String> responseFromServer = httpResponseCompletableFuture.get();
                System.out.println(responseFromServer);
                System.out.println(responseFromServer.body());
                JSONObject responseJson = new JSONObject(responseFromServer.body());
                String roomId = (String) responseJson.get("roomId");
                roomPage.setRoomId(roomId);
                rootPane.getChildren().add(roomPage);
                rootPane.getChildren().remove(choosePage);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Successfully connected to room : " + roomId);
                alert.showAndWait();
                roomPage.waitForChanges(roomId);
                roomPage.waitForMessages(roomId);

            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            } catch (ExecutionException executionException) {
                executionException.printStackTrace();
            } catch (URISyntaxException uriSyntaxException) {
                uriSyntaxException.printStackTrace();
            }
        });

    }


    private void setupJoinRoomClicked(Button joinButton) {
        joinButton.setOnAction((e -> {
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setHeaderText("Please enter a RoomId");
            Optional<String> enteredResult = textInputDialog.showAndWait();
            enteredResult.ifPresent(String -> {
                redirectRoom(String);
            });

        }));
    }

    private void redirectRoom(String roomId) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("roomid", roomId);
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(jsonObj.toString()))
                    .uri(new URI("http://localhost:8081/joinRoom?email=" + loginPage.getEmailTextField().getText()))
                    .build();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> response = httpResponseCompletableFuture.get();
            System.out.println(response);
            System.out.println(response.body());
            JSONObject responseFromServer = new JSONObject(response.body());
            boolean roomNumberFound = (boolean) responseFromServer.get("roomNumberFound");
            if (roomNumberFound) {
                roomPage.setRoomId(roomId);
                rootPane.getChildren().add(roomPage);
                rootPane.getChildren().remove(choosePage);
                roomPage.waitForChanges(roomId);
                roomPage.waitForMessages(roomId);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Success: you are now connected to room number: " + roomId);
                alert.showAndWait();
                //go to the next room

            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.initStyle(StageStyle.UTILITY);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Error: Room number does not exists");
                alert.showAndWait();

            }
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
        }
    }

}



