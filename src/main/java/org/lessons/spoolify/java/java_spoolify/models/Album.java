package org.lessons.spoolify.java.java_spoolify.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table( name = "albums")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "This field must be filled")
    @Size(max = 75)
    private String name;

    @NotBlank(message = "This field must be filled")
    @Size(max = 100)
    private String mainArtist;

    @Lob
    @NotBlank(message = "This field must be filled")
    @Size(min = 50, max = 3000)
    private String description;

    @NotBlank(message = "This field must be filled")
    private String img;

    @NotNull(message = "This field must be filled")
    private LocalDate releaseDate;
    
    @OneToMany(mappedBy = "album")
    private List<Song> songs;

    @ManyToMany
    @JoinTable(
        name = "album_genre",
        joinColumns = @JoinColumn(name = "album_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> albumGenres;

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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Genre> getAlbumGenres() {
        return albumGenres;
    }

    public void setAlbumGenres(List<Genre> albumGenres) {
        this.albumGenres = albumGenres;
    }
    public String getImg() {
        return img;
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

    // METODO getTotalDuration PER CALCOLARE DURATA ALBUM IN BASE A DURATA CANZONI
    public BigDecimal getTotalDuration(){
        // BigDecimal totalDuration = BigDecimal.ZERO;
        int totalSeconds = 0;
        for (Song song : this.songs){
            BigDecimal duration = song.getDuration();
            int minutes = duration.intValue();
            int seconds = duration.subtract(new BigDecimal(minutes)).multiply(new BigDecimal(100)).intValue();
            totalSeconds += minutes * 60 + seconds;
        }
        int minutiTotali = totalSeconds / 60;
        int secondiTotali = totalSeconds % 60;
        return new BigDecimal(String.format("%d.%02d", minutiTotali, secondiTotali));
    }

    // METODO getTotalTracks PER CALCOLARE NUMERO DI CANZONI IN BASE A LUNGHEZZA DI songs(o getSongs in un mapping)
    public Integer getTotalTracks(){
        return this.songs.size();
    }
}
