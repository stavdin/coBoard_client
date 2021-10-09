package sample;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class MessageRetrieverService extends Service<String> {

    private String roomId;
    private int syncPoint = 0;

    public MessageRetrieverService(String roomId) {
        this.roomId = roomId;
    }

    protected Task<String> createTask() {
        return new Task<String>() {
            protected String call() throws Exception {
                return sendWaitingForMessagesRequest(roomId);
            }
        };
    }

    public String sendWaitingForMessagesRequest(String roomId) throws Exception {
        //add room id to the request body
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(new URI("http://localhost:8081/waitForMessage?roomId=" + roomId + "&syncPoint=" + this.syncPoint))
                    .build();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseFromServer = httpResponseCompletableFuture.get();
            JSONObject response = new JSONObject(responseFromServer.body());
            var message = response.getString("message");
            this.syncPoint = response.getInt("newSyncpoint");
            return message;
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
            throw interruptedException;
        } catch (ExecutionException executionException) {
            executionException.printStackTrace();
            throw executionException;
        } catch (URISyntaxException uriSyntaxException) {
            uriSyntaxException.printStackTrace();
            throw uriSyntaxException;
        }
    }
}
