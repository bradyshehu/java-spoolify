package org.lessons.spoolify.java.java_spoolify.controllers;

import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.lessons.spoolify.java.java_spoolify.models.Genre;
import org.lessons.spoolify.java.java_spoolify.models.Song;
import org.lessons.spoolify.java.java_spoolify.services.AlbumService;
import org.lessons.spoolify.java.java_spoolify.services.GenreService;
import org.lessons.spoolify.java.java_spoolify.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;



@Controller
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    GenreService genreService;
    @Autowired
    AlbumService albumService;
    @Autowired
    SongService songService;

    // INDEX
    @GetMapping
    public String index(Model model) {
        model.addAttribute("genres", genreService.findAll());
        return "genres/index";
    }

    // SHOW
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        
        Optional<Genre> genreAtt = genreService.findById(id);
        
        if(genreAtt.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Genere non trovato!");
            return "redirect:/albums";
        }

        model.addAttribute("genre", genreAtt.get());
        return "genres/show";
    }

    // CREATE
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("genre", new Genre());
        return "genres/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("genre") Genre formGenre, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            return "genres/create-or-edit";
        }

        genreService.saveGenre(formGenre);
        return "redirect:/genres";
    }
    
    // EDIT
    @GetMapping("/edit/{id}")
    public String getMethodName(Model model, @PathVariable("id")Integer id, RedirectAttributes redirectAttributes) {
        Optional<Genre> genreAtt = genreService.findById(id);

        if(genreAtt.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Genere non trovato!");
            return "redirect:/albums";
        }
        model.addAttribute("genre", genreAtt.get());
        model.addAttribute("edit", true);

        return "genres/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("genre") Genre formGenre, BindingResult bindingResult, @PathVariable("id")Integer id, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("edit", true);
            return "genres/create-or-edit";
        }

        formGenre.setId(id);
        genreService.saveGenre(formGenre);
        return "redirect:/genres/" + formGenre.getId();
    }
    
    // DELETE
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Optional<Genre> genreAtt = genreService.findById(id);

        if(genreAtt.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Genere non trovato!");
            return "redirect:/albums";
        } 

        Genre genreToDelete = genreAtt.get();

        for( Album albumToRemoveGenreFrom : genreToDelete.getAlbums()){
            albumToRemoveGenreFrom.getAlbumGenres().remove(genreToDelete);
        }
        for( Song songToRemoveGenreFrom : genreToDelete.getSongs()){
            songToRemoveGenreFrom.getSongGenres().remove(genreToDelete);
        }
        
        genreService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Genere eliminato con successo!");
        return "redirect:/albums";
    }
}
