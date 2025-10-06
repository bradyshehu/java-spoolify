package org.lessons.spoolify.java.java_spoolify.controllers;

import java.util.List;
import java.util.Optional;

import org.lessons.spoolify.java.java_spoolify.models.Album;
import org.lessons.spoolify.java.java_spoolify.models.Song;
import org.lessons.spoolify.java.java_spoolify.services.AlbumService;
import org.lessons.spoolify.java.java_spoolify.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
@RequestMapping("/albums")
public class AlbumController {
    @Autowired
    AlbumService albumService;
    @Autowired
    GenreService genreService;

    @GetMapping
    public String index(Model model, 
        @RequestParam(required = false)String name,
        @RequestParam(required = false)Integer genreId) {

        List<Album> albums;

        if (name != null && !name.isEmpty()){
            albums = albumService.findFilteredByName(name);
        } else if (genreId != null) {
            albums = albumService.findByGenreId(genreId);
        }
        else{
            albums = albumService.findAll();
        }

        model.addAttribute("albums", albums);
        return "albums/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Album> albumAtt = albumService.findById(id);

        if (albumAtt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Album non trovato!");
            return "redirect:/albums";
        }

        model.addAttribute("album", albumAtt.get());
        return "albums/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("album", new Album());
        model.addAttribute("genres", genreService.findAll());
        return "albums/create-or-edit";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Optional<Album> albumAtt = albumService.findById(id);

        if (albumAtt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Album non trovato!");
            return "redirect:/albums";
        }

        model.addAttribute("album", albumAtt.get());
        model.addAttribute("genres", genreService.findAll());
        model.addAttribute("edit", true);
        return "albums/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("album")Album formAlbum, 
    BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()){
            model.addAttribute("genres", genreService.findAll());
            return "albums/create-or-edit";
        }
        albumService.saveAlbum(formAlbum);
        
        return "redirect:/albums";
    }


    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("album")Album formAlbum, 
    BindingResult bindingResult, @PathVariable("id") Integer id, Model model) {
        if(bindingResult.hasErrors()){
            model.addAttribute("genres", genreService.findAll());
            model.addAttribute("edit", true);
            return "albums/create-or-edit";
        }
        formAlbum.setId(id);
        albumService.saveAlbum(formAlbum);
        
        return "redirect:/albums/{id}";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {

        Optional<Album> albumAttempt = albumService.findById(id);

        if(albumAttempt.isEmpty()){
            redirectAttributes.addFlashAttribute("errorMessage", "Album non trovato, é impossibile eliminare qualcosa che non esiste!");
            return "redirect:/albums";
        }
        albumService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Album eliminato con successo!");
        return "redirect:/albums";
    }
    
    @GetMapping("/{id}/songs")
    public String createSong(Model model, @PathVariable("id") Integer albumId, RedirectAttributes redirectAttributes) {
        Song song = new Song();
        Optional<Album> albumAtt = albumService.findById(albumId);
        if (albumAtt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Album non trovato! Impossibile aggiungere una canzone ad un album inesistente.");
            return "redirect:/albums";
        }
        song.setAlbum(albumAtt.get());
        model.addAttribute("song", song);
        model.addAttribute("genres", genreService.findAll());
        return "songs/create-or-edit";
    }
    
}
