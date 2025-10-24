package org.lessons.spoolify.java.java_spoolify.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AlbumDto {
    private Integer id;
    private String name;
    private String mainArtist;
    private String description;
    private String img;
    private LocalDate releaseDate;
    private Integer totalTracks;
    private BigDecimal totalDuration;
    private List<SongDto> songs;
    private List<GenreDto> genres;

    public AlbumDto() {}

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

    public String getMainArtist() {
        return mainArtist;
    }

    public void setMainArtist(String mainArtist) {
        this.mainArtist = mainArtist;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

public String getImg() {
    if (img.startsWith("http")) return img;
    return "http://localhost:8080/img/" + img;
}
    public void setImg(String img) {
        this.img = img;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getTotalTracks() {
        return totalTracks;
    }

    public void setTotalTracks(Integer totalTracks) {
        this.totalTracks = totalTracks;
    }

    public BigDecimal getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(BigDecimal totalDuration) {
        this.totalDuration = totalDuration;
    }

    public List<SongDto> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDto> songs) {
        this.songs = songs;
    }

    public List<GenreDto> getGenres() {
        return genres;
    }

    public void setGenres(List<GenreDto> genres) {
        this.genres = genres;
    }

    

}
