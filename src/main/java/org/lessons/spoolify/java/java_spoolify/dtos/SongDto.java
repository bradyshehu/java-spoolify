package org.lessons.spoolify.java.java_spoolify.dtos;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SongDto {
    
    private Integer id;
    private String name;
    private BigDecimal duration;
    private boolean isExplicit;
    private AlbumDto album;
    private List<GenreDto> genres;

    public SongDto() {}

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

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public boolean getIsExplicit() {
        return isExplicit;
    }

    public void setIsExplicit(boolean isExplicit) {
        this.isExplicit = isExplicit;
    }

    public AlbumDto getAlbum() {
        return album;
    }

    public void setAlbum(AlbumDto album) {
        this.album = album;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    

}
