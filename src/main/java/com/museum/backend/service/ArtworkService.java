// Service layer for managing artworks
package com.museum.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.museum.backend.model.Artwork;
import com.museum.backend.repository.ArtworkRepository;

@Service
public class ArtworkService {

    private final ArtworkRepository repo;

    public ArtworkService(ArtworkRepository repo) {
        this.repo = repo;
    }

    public List<Artwork> getAllArtworks() {
        return repo.findAll();
    }

    public Artwork getArtworkById(String id) {
        return repo.findById(Long.valueOf(id)).orElse(null);
    }

    public Artwork createArtwork(Artwork artwork) {
        return repo.save(artwork);
    }

    public void deleteArtwork(String id) {
        repo.deleteById(Long.valueOf(id));
    }
}
