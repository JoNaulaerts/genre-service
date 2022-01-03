package fact.it.genre_service.controller;

import fact.it.genre_service.model.Genre;
import fact.it.genre_service.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class Genrecontroller {

    @Autowired
    private GenreRepository genreRepository;

    @PostConstruct
    public void fillDB() {
        if (genreRepository.count() == 0) {
            genreRepository.save(new Genre("Rock"));
            genreRepository.save(new Genre("Punk"));
            genreRepository.save(new Genre("Metal"));
            genreRepository.save(new Genre("Grunge"));
        }

//        System.out.println("Genre test: " + genreRepository.findGenreByGenreName("Grunge").getGenreName());
    }

    @GetMapping("/genres")
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

    @GetMapping("/genres/{genreName}")
    public Genre getGenresByGenreName(@PathVariable String genreName) {

        Genre genreReturn = genreRepository.findGenreByGenreName(genreName);
        return genreReturn;
    }

//    @PostMapping("/genres")
//    public Genre addGenre(@RequestBody Genre genre) {
//        genreRepository.save(genre);
//        return genre;
//    }
//
//    @PutMapping("/genres")
//    public Genre updateGenre(@RequestBody Genre updatedGenre) {
//        Genre retrievedGenre = genreRepository.findGenreByGenreName(updatedGenre.getGenreName());
//        retrievedGenre.setGenreName(updatedGenre.getGenreName());
//        genreRepository.save(retrievedGenre);
//
//        return retrievedGenre;
//    }
//
//    @DeleteMapping("/genres/{genreName}")
//    public ResponseEntity deleteGenre(@PathVariable String genreName) {
//        Genre genre = genreRepository.findGenreByGenreName(genreName);
//        if (genre != null){
//            genreRepository.delete(genre);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
}
