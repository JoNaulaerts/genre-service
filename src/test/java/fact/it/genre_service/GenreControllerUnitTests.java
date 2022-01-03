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

        Genre genre1 = new Genre("Rock");
        Genre genre2 = new Genre("Punk");
        Genre genre3 = new Genre("Metal");
        Genre genre4 = new Genre("Grunge");


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
                .andExpect(jsonPath("$[1].genreName", is("Punk")))
                .andExpect(jsonPath("$[2].genreName", is("Metal")))
                .andExpect(jsonPath("$[3].genreName", is("Grunge")));
    }

    @Test
    public void givenGenre_whenGetGenreByGenreName_thenReturnJsonGenre() throws Exception {


        Genre genre1 = new Genre("Rock");
        Genre genre2 = new Genre("Punk");
        Genre genre3 = new Genre("Metal");
        Genre genre4 = new Genre("Grunge");


//        List<Genre> genreList = new ArrayList<>();
//        genreList.add(genre1);
//        genreList.add(genre2);
//        genreList.add(genre3);
//        genreList.add(genre4);

        given(genreRepository.findGenreByGenreName("Rock")).willReturn(genre1);
        given(genreRepository.findGenreByGenreName("Punk")).willReturn(genre2);
        given(genreRepository.findGenreByGenreName("Metal")).willReturn(genre3);
        given(genreRepository.findGenreByGenreName("Grunge")).willReturn(genre4);

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

//        List<Genre> genreList3 = new ArrayList<>();
//        genreList3.add(genre1);
//        genreList3.add(genre2);
//        genreList3.add(genre3);
//        genreList3.add(genre4);

        given(genreRepository.findGenreByGenreName("K-POP")).willReturn(null);

        mockMvc.perform(get("/genres/{genreName}", "K-POP")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(""));
    }

}