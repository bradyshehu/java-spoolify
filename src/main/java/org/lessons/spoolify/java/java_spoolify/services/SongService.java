package org.lessons.spoolify.java.java_spoolify.services;

import java.util.List;
import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.lessons.spoolify.java.java_spoolify.models.Song;
import org.lessons.spoolify.java.java_spoolify.repositories.AlbumRepository;
import org.lessons.spoolify.java.java_spoolify.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    @Autowired
    AlbumRepository albumRepo;
    @Autowired
    SongRepository songRepo;

    // VARIE INDEX E FILTRI/SORT
    public List<Song> findAll(){
        return songRepo.findAll();
    }
    public List<Song> findAllSortedByName(){
        return songRepo.findAll(Sort.by("name"));
    }
    public List<Song> findByName(String nameToFilter){
        return songRepo.findByNameContainingIgnoreCase(nameToFilter);
    }
    public List<Song> findByGenreId(Integer genreId){
        return songRepo.findBySongGenres_Id(genreId);
    }
    
    public Page<Song> findAll(Pageable pageable){
        return songRepo.findAll(pageable);
    }
    public Page<Song> findByName(String nameToFilter, Pageable pageable){
        return songRepo.findByNameContainingIgnoreCase(nameToFilter, pageable);
    }
    public Page<Song> findByGenreId(Integer genreId, Pageable pageable){
        return songRepo.findBySongGenres_Id(genreId, pageable);
    }


    // SHOW
    public Optional<Song> findById(Integer id){
        return songRepo.findById(id);
    }
    // CREATE
    public Song saveSong (Song songToSave){
        return songRepo.save(songToSave);
    }
    // DELETE
    public void deleteById (Integer id){
        Song songToDelete = songRepo.findById(id).get();
        Album album = songToDelete.getAlbum();
        boolean isLastSong = (album.getSongs().size() == 1);
        songRepo.delete(songToDelete);

        if(isLastSong){
            albumRepo.delete(album);
        }
        }
    }