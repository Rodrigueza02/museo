package com.museum.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.museum.backend.dto.EmotionRequest;
import com.museum.backend.dto.EmotionResponse;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmotionService {

    private final OkHttpClient http = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    // Stub de obras; reemplazar por repositorio/JPA cuando esté listo.
    private final Map<Long, Artwork> obras = new HashMap<>();

    public EmotionService() {
        obras.put(1L, new Artwork(1L, "La Mona Lisa", "Leonardo da Vinci",
                "Retrato renacentista de una mujer con mirada enigmática."));
        obras.put(2L, new Artwork(2L, "El Grito", "Edvard Munch",
                "Obra expresionista que refleja angustia y tensión emocional."));
    }

    public EmotionResponse generarComentario(EmotionRequest req) {
        Artwork obra = obras.getOrDefault(req.getObraId(),
                new Artwork(req.getObraId(), "Obra desconocida", "Autor desconocido", "Sin descripción."));

        String prompt = buildPrompt(req.getEstadoAnimo(), obra);

        String endpoint = System.getenv("GEMINI_ENDPOINT");
        String apiKey = System.getenv("GEMINI_API_KEY");

        if (endpoint == null || endpoint.isBlank() || apiKey == null || apiKey.isBlank()) {
            String fallback = String.format("Sobre tu estado '%s' y la obra '%s' de %s: respira, observa los detalles y recuerda que el arte puede acompañarte.",
                    req.getEstadoAnimo(), obra.title, obra.author);
            return new EmotionResponse(req.getObraId(), fallback);
        }

        try {
            Map<String, Object> body = new HashMap<>();
            body.put("prompt", prompt);
            body.put("max_tokens", 150);

            String json = mapper.writeValueAsString(body);

            RequestBody requestBody = RequestBody.create(json, MediaType.get("application/json; charset=utf-8"));

            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = http.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String err = response.body() != null ? response.body().string() : "error vacío";
                    String fallback = "No se pudo contactar al servicio de lenguaje: " + err;
                    return new EmotionResponse(req.getObraId(), fallback);
                }
                String respBody = response.body() != null ? response.body().string() : "";
                String comentario = extractTextFromResponse(respBody);
                return new EmotionResponse(req.getObraId(), comentario);
            }
        } catch (IOException e) {
            String fallback = "Ocurrió un error al generar el comentario: " + e.getMessage();
            return new EmotionResponse(req.getObraId(), fallback);
        }
    }

    private String buildPrompt(String estadoAnimo, Artwork obra) {
        return String.format(
                "Eres una IA que responde en español con un comentario breve y empático (2-3 frases) para alguien que se siente '%s'. " +
                "La obra es '%s' de %s. Descripción: %s. Haz un comentario amistoso, creativo y reconfortante, referencia breve a la obra.",
                estadoAnimo, obra.title, obra.author, obra.description);
    }

    private String extractTextFromResponse(String raw) {
        try {
            var node = mapper.readTree(raw);
            if (node.has("text")) return node.get("text").asText();
            if (node.has("output")) return node.get("output").asText();
            if (node.has("choices") && node.get("choices").isArray() && node.get("choices").size() > 0) {
                var first = node.get("choices").get(0);
                if (first.has("text")) return first.get("text").asText();
                if (first.has("message")) return first.get("message").asText();
            }
            return raw;
        } catch (Exception ex) {
            return raw;
        }
    }

    private static class Artwork {
        Long id;
        String title;
        String author;
        String description;
        Artwork(Long id, String title, String author, String description) {
            this.id = id; this.title = title; this.author = author; this.description = description;
        }
    }
}