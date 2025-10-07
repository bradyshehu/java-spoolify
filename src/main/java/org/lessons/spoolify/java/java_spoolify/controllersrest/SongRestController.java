package org.lessons.spoolify.java.java_spoolify.controllersrest;

import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.dtos.SongDto;
import org.lessons.spoolify.java.java_spoolify.mappers.DtoMapper;
import org.lessons.spoolify.java.java_spoolify.models.Song;
import org.lessons.spoolify.java.java_spoolify.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
@RequestMapping("/api/songs")
public class SongRestController {

    @Autowired
    SongService songService;
    
    @GetMapping()
    public Page<SongDto> index(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) Integer genreId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size     
    ) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songPage;

        if(name != null && !name.isEmpty()){
            songPage = songService.findByName(name, pageable);
        }
        else if(genreId != null){
            songPage = songService.findByGenreId(genreId, pageable);
        }
        else {
            songPage = songService.findAll(pageable);
        }
        return songPage.map(song -> DtoMapper.toSongDto(song));
    }
    @GetMapping("/{id}")
    public ResponseEntity<SongDto> show(@PathVariable("id")Integer id) {
        Optional<Song> songAtt = songService.findById(id);

        if(songAtt.isEmpty()){
            return new ResponseEntity<SongDto>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<SongDto>(DtoMapper.toSongDto(songAtt.get(), true, true), HttpStatus.OK);
    }
    
    

}
