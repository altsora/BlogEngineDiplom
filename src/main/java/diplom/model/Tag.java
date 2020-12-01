package diplom.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
@ToString(exclude = {"posts"})
@EqualsAndHashCode(exclude = {"posts"})
public class Tag {
    private long id;
    private String name;
    private Set<Tag2Post> posts;

    //------------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "tag")
    public Set<Tag2Post> getPosts() {
        return posts;
    }
}
