package org.lessons.spoolify.java.java_spoolify.services;

import java.util.List;
import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.Genre;
import org.lessons.spoolify.java.java_spoolify.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    
    @Autowired
    GenreRepository genreRepo;

    public List<Genre> findAll(){
        return genreRepo.findAll();
    }
    public List<Genre> findAllSortedByName(){
        return genreRepo.findAll(Sort.by("name"));
    }
    public Optional<Genre> findById (Integer id){
        return genreRepo.findById(id);
    }
    public Genre saveGenre(Genre genreToSave){
        return genreRepo.save(genreToSave);
    }
    public void deleteById(Integer id){
        Genre genreToDelete = genreRepo.findById(id).get();
        genreRepo.delete(genreToDelete);
    }
}
