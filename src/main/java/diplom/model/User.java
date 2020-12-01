package diplom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    private long id;
    private boolean isModerator;
    private LocalDateTime regTime;
    private String name;
    private String email;
    private String password;
    private String code;
    private String photo;
    private Set<Post> posts;
    private Set<Post> modifiedPosts;

    //==============================================================================

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "is_moderator")
    public boolean isModerator() {
        return isModerator;
    }

    @Column(name = "reg_time")
    public LocalDateTime getRegTime() {
        return regTime;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    public Set<Post> getPosts() {
        return posts;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "moderator")
    public Set<Post> getModifiedPosts() {
        return modifiedPosts;
    }
}
