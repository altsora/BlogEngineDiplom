package diplom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import diplom.enums.Rating;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Data
public class Vote {
    private long id;
    private User user;
    private Post post;
    private LocalDateTime time;
    private Rating value;

    //------------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @JsonBackReference
    @ManyToOne
    public User getUser() {
        return user;
    }

    @JsonBackReference
    @ManyToOne
    public Post getPost() {
        return post;
    }

    @Enumerated(EnumType.STRING)
    public Rating getValue() {
        return value;
    }
}
