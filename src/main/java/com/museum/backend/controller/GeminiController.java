package com.museum.backend.controller;

import com.museum.backend.service.GeminiService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin("*")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @GetMapping("/comentario")
    public String generar(
            @RequestParam String estado,
            @RequestParam String obra
    ) throws Exception {

        String prompt = "El usuario está " + estado +
                ". La obra es: " + obra +
                ". Responde con un comentario artístico simpático.";

        return geminiService.generarComentario(prompt);
    }
}
