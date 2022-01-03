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
            genreRepository.save(new Genre("Rock", "Rock music is a broad genre of popular music that originated as \"rock and roll\" in the United States in the late 1940s and early 1950s, developing into a range of different styles in the mid-1960s and later, particularly in the United States and the United Kingdom."));
            genreRepository.save(new Genre("Heavy Metal","Heavy metal (or simply metal) is a genre of rock music that developed in the late 1960s and early 1970s, largely in the United Kingdom and the United States."));
            genreRepository.save(new Genre("Pop", "Pop is a genre of popular music that originated in its modern form during the mid-1950s in the United States and the United Kingdom."));
            genreRepository.save(new Genre("Blues", "Blues is a music genre and musical form which was originated in the Deep South of the United States around the 1860s by African-Americans from roots in African-American work songs and spirituals."));
            genreRepository.save(new Genre("Punk rock","Punk rock (or simply punk) is a music genre that emerged in the mid-1970s. Rooted in 1960s garage rock, punk bands rejected the perceived excesses of mainstream 1970s rock."));
            genreRepository.save(new Genre("Jazz","Jazz is a music genre that originated in the African-American communities of New Orleans, Louisiana, United States, in the late 19th and early 20th centuries, with its roots in blues and ragtime."));
            genreRepository.save(new Genre("Grunge","Grunge (sometimes referred to as the Seattle sound) is an alternative rock genre and subculture that emerged during the mid-1980s in the American Pacific Northwest state of Washington, particularly in Seattle and nearby towns."));
            genreRepository.save(new Genre("New wave","New wave is a broad music genre that encompasses numerous pop-oriented styles from the late 1970s and the 1980s. It was originally used as a catch-all for the music that emerged after punk rock, including punk itself, but may be viewed retrospectively as a more accessible counterpart of post-punk."));
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
