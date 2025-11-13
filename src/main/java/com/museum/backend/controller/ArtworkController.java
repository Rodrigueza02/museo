// Controller that manages operations related to artworks
package com.museum.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.museum.backend.model.Artwork;
import com.museum.backend.service.ArtworkService;

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
