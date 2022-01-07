package fact.it.genre_service.model;

import javax.persistence.*;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="genre_name")
    private String genreName;

    @Column(name="description", length = 1024)
    private String description;

    public Genre(String genreName, String description) {
        this.genreName = genreName;
        this.description = description;
    }

    public Genre() {

    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
