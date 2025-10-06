package org.lessons.spoolify.java.java_spoolify.services;

import java.util.List;
import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.lessons.spoolify.java.java_spoolify.models.Song;
import org.lessons.spoolify.java.java_spoolify.repositories.AlbumRepository;
import org.lessons.spoolify.java.java_spoolify.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AlbumService {

    @Autowired
    AlbumRepository albumRepo;
    @Autowired
    SongRepository songRepo;

    // VARIE INDEX E FILTRI/SORT
    public List<Album> findAll(){
        return albumRepo.findAll();
    }
    public List<Album> findFilteredByName(String nameToFilter){
        return albumRepo.findByNameContainingIgnoreCase(nameToFilter);
    }
    public List<Album> findAllSortedByDate(){
        return albumRepo.findAll(Sort.by("releaseDate"));
    }
    public List<Album> findByGenreId(Integer genreId){
        return albumRepo.findByAlbumGenres_Id(genreId);
    }
    // SHOW
    public Optional<Album> findById(Integer id){
        return albumRepo.findById(id);
    }
    // CREATE
    public Album saveAlbum (Album albumToSave){
        return albumRepo.save(albumToSave);
    }
    // DELETE
    public void deleteById (Integer id){
        Album albumToDelete = albumRepo.findById(id).get();
        for(Song songToDelete : albumToDelete.getSongs()){
            songRepo.delete(songToDelete);
        }
        albumRepo.delete(albumToDelete);
        }
    }
        
    

