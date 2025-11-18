package com.museum.backend.controller;

import com.museum.backend.dto.EmotionRequest;
import com.museum.backend.dto.EmotionResponse;
import com.museum.backend.service.EmotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emotions")
public class EmotionController {

    private final EmotionService emotionService;

    public EmotionController(EmotionService emotionService) {
        this.emotionService = emotionService;
    }

    @PostMapping
    public ResponseEntity<EmotionResponse> postEmotion(@RequestBody EmotionRequest req) {
        EmotionResponse resp = emotionService.generarComentario(req);
        return ResponseEntity.ok(resp);
    }
}