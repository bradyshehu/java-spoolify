package org.lessons.spoolify.java.java_spoolify.repositories;

import java.util.List;

import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Integer> {

    public List<Album> findByNameContainingIgnoreCase(String name);
    // FILTRO PER GENERE (FORSE MEGLIO COL NOME DATO CHE SARÁ UN RequestParam DA URL)
    public List<Album> findByAlbumGenres_Id(Integer genreId);
}
