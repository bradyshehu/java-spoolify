package org.lessons.spoolify.java.java_spoolify.controllersrest;

// import java.util.List;
import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.dtos.AlbumDto;
import org.lessons.spoolify.java.java_spoolify.mappers.DtoMapper;
import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.lessons.spoolify.java.java_spoolify.services.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
// import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
@RequestMapping("/api/albums")
public class AlbumRestController {

    @Autowired
    AlbumService albumService;

    @GetMapping
    public Page<AlbumDto> index(
        @RequestParam(required = false)String name, 
        @RequestParam(required = false)Integer genreId,
        @RequestParam(defaultValue = "0") int page, 
        @RequestParam(defaultValue = "12") int size){        

        Pageable pageable = PageRequest.of(page, size);
        Page<Album> albumPage;

        if(name != null && !name.isEmpty()){
            albumPage = albumService.findFilteredByNamePaged(name, pageable);
        } else if(genreId != null){
            albumPage = albumService.findByGenreIdPaged(genreId, pageable);
        } 
        else {
            albumPage = albumService.findAllPaged(pageable);
        }
        return albumPage.map(album -> DtoMapper.toAlbumDto(album));
    }

    @GetMapping("{id}")
    public ResponseEntity<AlbumDto> show(@PathVariable("id") Integer id) {

        Optional<Album> albumAtt = albumService.findById(id);

        if(albumAtt.isEmpty()){
            return new ResponseEntity<AlbumDto> (HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AlbumDto>(DtoMapper.toAlbumDto(albumAtt.get(), true, true), HttpStatus.OK);
    }
    
}
