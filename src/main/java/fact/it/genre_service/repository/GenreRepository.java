package fact.it.genre_service.repository;

import fact.it.genre_service.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    Genre findGenreByGenreName(String genreName);
}
