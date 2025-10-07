package org.lessons.spoolify.java.java_spoolify.repositories;

import java.util.List;

import org.lessons.spoolify.java.java_spoolify.models.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Integer>{
    
    public List<Song> findByNameContainingIgnoreCase(String name);
    // FILTRO PER GENERE (FORSE MEGLIO COL NOME DATO CHE SARÁ UN RequestParam DA URL)
    public List<Song> findBySongGenres_Id(Integer genreId);

    public Page<Song> findByNameContainingIgnoreCase(String name, Pageable pageable);
    public Page<Song> findBySongGenres_Id(Integer genreId, Pageable pageable);;
    
}
