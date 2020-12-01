package diplom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "tag2post")
@Data
@EqualsAndHashCode(of = {"post", "tag"})
public class Tag2Post {
    private long id;
    private Post post;
    private Tag tag;

    //------------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "post_id")
    public Post getPost() {
        return post;
    }

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "tag_id")
    public Tag getTag() {
        return tag;
    }
}
