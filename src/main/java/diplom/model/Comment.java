package diplom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "comments")
@Data
@ToString(exclude = {"children", "parent"})
@EqualsAndHashCode(exclude = {"children", "parent"})
public class Comment {
    private long id;
    private Comment parent;
    private Post post;
    private User user;
    private LocalDateTime time;
    private String text;

    private Set<Comment> children;

    //------------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @JsonBackReference
    @ManyToOne
    public Comment getParent() {
        return parent;
    }

    @JsonBackReference
    @ManyToOne
    public Post getPost() {
        return post;
    }

    @JsonBackReference
    @ManyToOne
    public User getUser() {
        return user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "parent")
    public Set<Comment> getChildren() {
        return children;
    }
}
