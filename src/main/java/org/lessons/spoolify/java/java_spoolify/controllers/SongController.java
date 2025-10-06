package org.lessons.spoolify.java.java_spoolify.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.Song;
import org.lessons.spoolify.java.java_spoolify.repositories.SongRepository;
import org.lessons.spoolify.java.java_spoolify.services.GenreService;
import org.lessons.spoolify.java.java_spoolify.services.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/songs")
public class SongController {

    private final SongRepository songRepository;
    @Autowired
    SongService songService;
    @Autowired
    GenreService genreService;

    SongController(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(required = false)String name) {

        List<Song> songs;
        if( name != null && !name.isEmpty()){
            songs = songService.findByName(name);
        }
        else {
            songs = songService.findAll();
        }
        model.addAttribute("songs", songs);

        // DA PREPARARE INDEX CON SEARCHBAR
        return "songs/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Song> songAtt = songService.findById(id);
        if(songAtt.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Canzone non trovata!");
            return "redirect:/albums";
        }
        model.addAttribute("song", songAtt.get());
        
        // DA PREPARARE
        return "songs/show";
    }
    
    
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("song") Song formSong, BindingResult bindingResult, Model model) {
        
        if(bindingResult.hasErrors()){
            model.addAttribute("genres", genreService.findAll());
            return "songs/create-or-edit";
        }
        songService.saveSong(formSong);
        return "redirect:/albums/" + formSong.getAlbum().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {

        Optional<Song> songAtt = songService.findById(id);
        if(songAtt.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Canzone non trovata!");
            return "redirect:/albums";
        }

        model.addAttribute("song", songAtt.get());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("edit", true);
        return "songs/create-or-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("song") Song formSong, 
    BindingResult bindingResult, @PathVariable("id") Integer id, Model model) {
        
        if(bindingResult.hasErrors()){
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("edit", true);
            return "songs/create-or-edit";
        }
        formSong.setId(id);
        songService.saveSong(formSong);
        return "redirect:/albums/" + formSong.getAlbum().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(RedirectAttributes redirectAttributes, @PathVariable("id") Integer id) {
        Optional<Song> songAtt = songService.findById(id);

        if(songAtt.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Canzone non trovata, impossibile eliminare una canzone inesistente!");
            return "redirect:/albums";
        }
        Integer idToRedirect = songAtt.get().getAlbum().getId();
        songRepository.delete(songAtt.get());
        return "redirect:/albums/" + idToRedirect;
    }
}
