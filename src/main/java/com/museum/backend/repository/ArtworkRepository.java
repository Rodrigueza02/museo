// Repository interface for managing artworks
package com.museum.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.museum.backend.model.Artwork;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
}
