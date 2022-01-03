package fact.it.genre_service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.genre_service.model.Genre;
import fact.it.genre_service.repository.GenreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GenreControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GenreRepository genreRepository;

    private Genre genre1 = new Genre("Rock");
    private Genre genre2 = new Genre("Punk");
    private Genre genre3 = new Genre("Metal");
    private Genre genre4 = new Genre("Grunge");

    @BeforeEach
    public void beforeAllTests() {
        genreRepository.deleteAll();
        genreRepository.save(genre1);
        genreRepository.save(genre2);
        genreRepository.save(genre3);
        genreRepository.save(genre4);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        genreRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenGenre_whenGetGenre_thenReturnJsonGenre() throws Exception {

        mockMvc.perform(get("/genres"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].genreName", is("Rock")))
                .andExpect(jsonPath("$[1].genreName", is("Punk")))
                .andExpect(jsonPath("$[2].genreName", is("Metal")))
                .andExpect(jsonPath("$[3].genreName", is("Grunge")));
    }

    @Test
    public void givenGenre_whenGetGenreByGenreName_thenReturnJsonGenre() throws Exception {

        mockMvc.perform(get("/genres/{genreName}", "Rock"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Rock")));

        mockMvc.perform(get("/genres/{genreName}", "Punk"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Punk")));

        mockMvc.perform(get("/genres/{genreName}", "Metal"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Metal")));

        mockMvc.perform(get("/genres/{genreName}", "Grunge"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.genreName", is("Grunge")));
    }

    @Test
    public void givenNoGenre_whenGetGenre_thenStatusNotFound() throws Exception {

        mockMvc.perform(get("/genres/{genreName}", "K-POP")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(""));
    }
}
