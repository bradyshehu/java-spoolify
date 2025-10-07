package org.lessons.spoolify.java.java_spoolify.mappers;

import java.util.ArrayList;
import java.util.List;

import org.lessons.spoolify.java.java_spoolify.dtos.AlbumDto;
import org.lessons.spoolify.java.java_spoolify.dtos.GenreDto;
import org.lessons.spoolify.java.java_spoolify.dtos.SongDto;
import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.lessons.spoolify.java.java_spoolify.models.Genre;
import org.lessons.spoolify.java.java_spoolify.models.Song;

public class DtoMapper {
    
    // ALBUM MAPPING SEMPLICE
    public static AlbumDto toAlbumDto (Album album){
        if(album == null) return null;
        
        AlbumDto dto = new AlbumDto();

        dto.setId(album.getId());
        dto.setName(album.getName());
        dto.setMainArtist(album.getMainArtist());
        dto.setDescription(album.getDescription());
        dto.setImg(album.getImg());
        dto.setReleaseDate(album.getReleaseDate());
        dto.setTotalDuration(album.getTotalDuration());
        dto.setTotalTracks(album.getTotalTracks());

        return dto;
    }

    // SONG MAPPING SEMPLICE
    public static SongDto toSongDto (Song song){
        if (song == null) return null;

        SongDto dto = new SongDto();

        dto.setId(song.getId());
        dto.setName(song.getName());
        dto.setDuration(song.getDuration());
        dto.setIsExplicit(song.getIsExplicit());

        return dto;
    }
    
    // GENRE MAPPING SEMPLICE
    public static GenreDto toGenreDto (Genre genre){
        if(genre == null) return null;

        GenreDto dto = new GenreDto();

        dto.setId(genre.getId());
        dto.setName(genre.getName());
        dto.setDescription(genre.getDescription());

        return dto;
    }



    // ALBUM CON SONGS E GENRES
    public static AlbumDto toAlbumDto (Album album, boolean includeSongs, boolean includeGenres){        
        AlbumDto dto = toAlbumDto(album);
        if(dto == null) return null;

        if (includeSongs && album.getSongs() != null){
            List<SongDto> songs = new ArrayList<>();
            for (Song song : album.getSongs()){
                songs.add(toSongDto(song));
            }
            dto.setSongs(songs);
        }

        if (includeGenres && album.getAlbumGenres() != null){
            List<GenreDto> genres = new ArrayList<>();
            for (Genre genre : album.getAlbumGenres()){
                genres.add(toGenreDto(genre));
            }
            dto.setGenres(genres);
        }

        return dto;
    }

    // SONG CON ALBUM E GENRES
    public static SongDto toSongDto (Song song, boolean includeAlbum, boolean includeGenres){
        SongDto dto = toSongDto(song);
        if(dto == null) return null;

        if(includeAlbum && song.getAlbum() != null){
            dto.setAlbum(toAlbumDto(song.getAlbum()));
        }

        if(includeGenres && song.getSongGenres() != null){
            List<GenreDto> genres = new ArrayList<>();
            for(Genre genre : song.getSongGenres()){
                genres.add(toGenreDto(genre));
            }
            dto.setGenres(genres);
        }
 
        return dto;
    }


    // GENRE CON ALBUMS E SONGS
    public static GenreDto toGenreDto (Genre genre, boolean includeAlbums, boolean includeSongs){
        GenreDto dto = toGenreDto(genre);
        if(dto == null) return null;

        if(includeAlbums && genre.getAlbums() != null){
            List<AlbumDto> albums = new ArrayList<>();
            for(Album album : genre.getAlbums()){
                albums.add(toAlbumDto(album));
            }
            dto.setAlbums(albums);
        }

        if(includeSongs && genre.getSongs() != null){
            List<SongDto> songs = new ArrayList<>();
            for(Song song : genre.getSongs()){
                songs.add(toSongDto(song));
            }
            dto.setSongs(songs);
        }

        return dto;
    }

    // UTILITY PER INDEX VARIE 

    // ALBUMS INDEX
    public static List<AlbumDto> toAlbumDtoList(List<Album> albums){
        List<AlbumDto> dtos = new ArrayList<>();
        for(Album album : albums){
            dtos.add(toAlbumDto(album));
        }
        return dtos;
    }

    // SONGS INDEX
    public static List<SongDto> toSongDtoList (List<Song> songs){
        List<SongDto> dtos = new ArrayList<>();
        for(Song song : songs){
            dtos.add(toSongDto(song));
        }
        return dtos;
    }

    // GENRE INDEX
    public static List<GenreDto> toGenreDtoList (List<Genre> genres){
        List<GenreDto> dtos = new ArrayList<>();
        for(Genre genre : genres){
            dtos.add(toGenreDto(genre));
        }
        return dtos;
    }
}
