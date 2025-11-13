// Repository interface for managing paintings
package com.museum.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.museum.backend.model.Painting;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
