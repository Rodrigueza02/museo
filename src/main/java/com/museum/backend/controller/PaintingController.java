// Controller that manages operations related to paintings
package com.museo.backend.controller;

import com.museo.backend.model.Painting;
import com.museo.backend.service.PaintingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/paintings")
public class PaintingController {

    private final PaintingService service;

    public PaintingController(PaintingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Painting> getAllPaintings() {
        return service.getAllPaintings();
    }

    @GetMapping("/{id}")
    public Painting getPaintingById(@PathVariable String id) {
        return service.getPaintingById(id);
    }

    @PostMapping
    public Painting createPainting(@RequestBody Painting painting) {
        return service.createPainting(painting);
    }

    @DeleteMapping("/{id}")
    public void deletePainting(@PathVariable String id) {
        service.deletePainting(id);
    }
}
