// Initial data loading when starting the application
package com.museum.backend.config;

import org.springframework.stereotype.Component;

import com.museum.backend.model.Artwork;
import com.museum.backend.repository.ArtworkRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    private final ArtworkRepository repo;

    public DataLoader(ArtworkRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void load() {
        if (repo.count() == 0) {
            Artwork a1 = new Artwork("artwork_01","The Mona Lisa","Leonardo da Vinci",
                    "Portrait of Lisa Gherardini, better known as Mona Lisa.","https://upload.wikimedia.org/wikipedia/commons/6/6a/Mona_Lisa.jpg","image");
            Artwork a2 = new Artwork("artwork_02","Las Meninas","Diego Velázquez",
                    "Baroque painting showing the Infanta Margarita surrounded by her entourage.","https://upload.wikimedia.org/wikipedia/commons/6/6f/Las_Meninas_01.jpg","image");
            repo.save(a1);
            repo.save(a2);
        }
    }
}
