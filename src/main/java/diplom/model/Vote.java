package diplom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import diplom.model.enums.Rating;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
public class Vote {
    private long id;
    private User user;
    private Post post;
    private LocalDateTime time;
    private Rating value;

    public Vote(User user, Post post, Rating value) {
        this.user = user;
        this.post = post;
        this.value = value;
        this.time = LocalDateTime.now();
    }

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

    //------------------------------------------------------------------------------------------------------------------

    @Transient
    public void replace(Rating newValue) {
        this.value = newValue;
        this.time = LocalDateTime.now();
    }

}
