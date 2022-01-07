package fact.it.genre_service.repository;

import fact.it.genre_service.model.Genre;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

    Genre findGenreByGenreName(String genreName);
}
