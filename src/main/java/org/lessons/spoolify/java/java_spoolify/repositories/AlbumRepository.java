package org.lessons.spoolify.java.java_spoolify.repositories;

import java.util.List;

import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    public List<Album> findByNameContainingIgnoreCase(String name);
    public List<Album> findByAlbumGenres_Id(Integer genreId);

    // PAGINAZIONE

    // Page<Album> findAll(Pageable pageable);
    Page<Album> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Album> findByAlbumGenres_Id(Integer genreId, Pageable pageable);


}
