// Controller that manages operations related to artworks
package com.museo.backend.controller;

import com.museo.backend.model.Artwork;
import com.museo.backend.service.ArtworkService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artworks")
public class ArtworkController {

    private final ArtworkService service;

    public ArtworkController(ArtworkService service) {
        this.service = service;
    }

    @GetMapping
    public List<Artwork> getAllArtworks() {
        return service.getAllArtworks();
    }

    @GetMapping("/{id}")
    public Artwork getArtworkById(@PathVariable String id) {
        return service.getArtworkById(id);
    }

    @PostMapping
    public Artwork createArtwork(@RequestBody Artwork artwork) {
        return service.createArtwork(artwork);
    }

    @DeleteMapping("/{id}")
    public void deleteArtwork(@PathVariable String id) {
        service.deleteArtwork(id);
    }
}
