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

public class ShapeRetrieverService extends Service<JSONArray> {

    private int syncPoint = 0;
    private String roomId;
    public boolean isUndo = false;

    public ShapeRetrieverService(String roomId) {
        this.roomId = roomId;
    }

    protected Task<JSONArray> createTask() {
        return new Task<JSONArray>() {
            protected JSONArray call() throws Exception {
                return sendWaitingForChangesRequest(roomId);
            }
        };
    }

    public JSONArray sendWaitingForChangesRequest(String roomId) throws Exception {
        //add room id to the request body
        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpRequest request = HttpRequest
                    .newBuilder()
                    .GET()
                    .uri(new URI("http://localhost:8081/waitForChanges?roomId=" + roomId + "&syncPoint=" + this.syncPoint))
                    .build();
            CompletableFuture<HttpResponse<String>> httpResponseCompletableFuture = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            HttpResponse<String> responseFromServer = httpResponseCompletableFuture.get();
            JSONObject changes = new JSONObject(responseFromServer.body());
            var newSyncpoint = changes.getInt("newSyncpoint");
            if (syncPoint > newSyncpoint) {
                isUndo = true;
            } else {
                isUndo = false;
            }
            syncPoint = changes.getInt("newSyncpoint");
            return changes.getJSONArray("shapesToDisplay");
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
