package fact.it.genre_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.genre_service.model.Genre;
import fact.it.genre_service.controller.Genrecontroller;
import fact.it.genre_service.repository.GenreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreRepository genreRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenGenre_whenGetGenre_thenReturnJsonGenre() throws Exception {

        Genre genre1 = new Genre("Rock", "Rock music is a broad genre of popular music that originated as \"rock and roll\" in the United States in the late 1940s and early 1950s, developing into a range of different styles in the mid-1960s and later, particularly in the United States and the United Kingdom.");
        Genre genre2 = new Genre("Punk rock","Punk rock (or simply punk) is a music genre that emerged in the mid-1970s. Rooted in 1960s garage rock, punk bands rejected the perceived excesses of mainstream 1970s rock.");
        Genre genre3 = new Genre("Heavy Metal","Heavy metal (or simply metal) is a genre of rock music that developed in the late 1960s and early 1970s, largely in the United Kingdom and the United States.");
        Genre genre4 = new Genre("Grunge","Grunge (sometimes referred to as the Seattle sound) is an alternative rock genre and subculture that emerged during the mid-1980s in the American Pacific Northwest state of Washington, particularly in Seattle and nearby towns.");

        List<Genre> genreList = new ArrayList<>();
        genreList.add(genre1);
        genreList.add(genre2);
        genreList.add(genre3);
        genreList.add(genre4);

        given(genreRepository.findAll()).willReturn(genreList);

        mockMvc.perform(get("/genres"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].genreName", is("Rock")))
                .andExpect(jsonPath("$[1].genreName", is("Punk rock")))
                .andExpect(jsonPath("$[2].genreName", is("Heavy Metal")))
                .andExpect(jsonPath("$[3].genreName", is("Grunge")));
    }

    @Test
    public void givenGenre_whenGetGenreByGenreName_thenReturnJsonGenre() throws Exception {

        Genre genre1 = new Genre("Rock", "Rock music is a broad genre of popular music that originated as \"rock and roll\" in the United States in the late 1940s and early 1950s, developing into a range of different styles in the mid-1960s and later, particularly in the United States and the United Kingdom.");
        Genre genre2 = new Genre("Punk rock","Punk rock (or simply punk) is a music genre that emerged in the mid-1970s. Rooted in 1960s garage rock, punk bands rejected the perceived excesses of mainstream 1970s rock.");
        Genre genre3 = new Genre("Heavy Metal","Heavy metal (or simply metal) is a genre of rock music that developed in the late 1960s and early 1970s, largely in the United Kingdom and the United States.");
        Genre genre4 = new Genre("Grunge","Grunge (sometimes referred to as the Seattle sound) is an alternative rock genre and subculture that emerged during the mid-1980s in the American Pacific Northwest state of Washington, particularly in Seattle and nearby towns.");


        given(genreRepository.findGenreByGenreName("Rock")).willReturn(genre1);
        given(genreRepository.findGenreByGenreName("Punk rock")).willReturn(genre2);
        given(genreRepository.findGenreByGenreName("Heavy Metal")).willReturn(genre3);
        given(genreRepository.findGenreByGenreName("Grunge")).willReturn(genre4);

        mockMvc.perform(get("/genres/{genreName}", "Rock"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Rock")));

        mockMvc.perform(get("/genres/{genreName}", "Punk rock"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Punk rock")));

        mockMvc.perform(get("/genres/{genreName}", "Heavy Metal"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Heavy Metal")));

        mockMvc.perform(get("/genres/{genreName}", "Grunge"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Grunge")));
    }

    @Test
    public void givenNoGenre_whenGetGenre_thenStatusNotFound() throws Exception {

        given(genreRepository.findGenreByGenreName("K-POP")).willReturn(null);

        mockMvc.perform(get("/genres/{genreName}", "K-POP")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(""));
    }

}
