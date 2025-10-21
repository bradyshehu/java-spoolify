package org.lessons.spoolify.java.java_spoolify.controllersrest;

import java.util.List;
import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.dtos.GenreDto;
import org.lessons.spoolify.java.java_spoolify.mappers.DtoMapper;
import org.lessons.spoolify.java.java_spoolify.models.Genre;
import org.lessons.spoolify.java.java_spoolify.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
@RequestMapping("/api/genres")
public class GenreRestController {
    @Autowired
    GenreService genreService;

    @GetMapping
    public List<GenreDto> index() {
        return DtoMapper.toGenreDtoList(genreService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> show(@PathVariable("id") Integer id) {
        Optional<Genre> genreAtt = genreService.findById(id);
        if(genreAtt.isEmpty()){
            return new ResponseEntity<GenreDto>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<GenreDto>(DtoMapper.toGenreDto(genreAtt.get(), true, true), HttpStatus.OK);
    }
}
