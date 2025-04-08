package io.vieira.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vieira.model.PutRequest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MyHTTPClient {
    private static final String BASE_URL = "http://localhost:8081";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public void update(PutRequest putRequest) throws Exception {
        String requestBody = objectMapper.writeValueAsString(putRequest);

        // Build the HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/%s".formatted(BASE_URL, "update")))
                .header("Content-Type", "application/json") // Set content type to JSON
                .POST(HttpRequest.BodyPublishers.ofString(requestBody)) // Attach JSON body
                .build();

        // Send the request and get the response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Print the response status and body
        System.out.println("Response Status Code: " + response.statusCode());
        System.out.println("Response Body: " + response.body());
    }

    public Object get(String key) throws Exception {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("%s/%s/%s".formatted(BASE_URL, "get", key)))
                .GET().build();
        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {
            try {
                return objectMapper.readValue(response.body(), Object.class);
            } catch (JsonParseException e) {
                return response.body();
            }
        } else {
            throw new RuntimeException("HTTP Error: " + response.statusCode());
        }
    }
}
