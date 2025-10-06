package org.lessons.spoolify.java.java_spoolify.repositories;

import org.lessons.spoolify.java.java_spoolify.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer>{
    
}
