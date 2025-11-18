package com.museum.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeminiService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${gemini.endpoint}")
    private String endpoint;

    @Value("${gemini.api-key}")
    private String apiKey;

    public String generarComentario(String prompt) throws IOException {
        String jsonBody =
                "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + prompt.replace("\"", "'") + "\" }] }] }";

        RequestBody requestBody = RequestBody.create(
                jsonBody,
                MediaType.get("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url(endpoint + "?key=" + apiKey)
                .post(requestBody)
                .addHeader("Content-Type", "application/json")
                .build();

        Response response = httpClient.newCall(request).execute();

        String body = response.body().string();

        return extraerTexto(body);
    }

    private String extraerTexto(String json) throws IOException {
        JsonNode root = objectMapper.readTree(json);
        return root.get("candidates").get(0).get("content").get("parts").get(0).get("text").asText();
    }
}
