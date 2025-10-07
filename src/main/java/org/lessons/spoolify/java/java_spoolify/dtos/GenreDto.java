package org.lessons.spoolify.java.java_spoolify.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

// import org.lessons.spoolify.java.java_spoolify.models.Genre;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDto {
    private Integer id;
    private String name;
    private String description;
    private List<SongDto> songs;
    private List<AlbumDto> albums;

    public GenreDto(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }

    public List<AlbumDto> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumDto> albums) {
        this.albums = albums;
    }
    
    
}
