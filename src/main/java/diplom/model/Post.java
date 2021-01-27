package diplom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import diplom.model.enums.ActivityStatus;
import diplom.model.enums.ModerationStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "posts")
@Data
@ToString(exclude = {"comments", "tags"})
@EqualsAndHashCode(exclude = {"comments", "tags"})
public class Post {
    private long id;
    private ActivityStatus activityStatus;
    private ModerationStatus moderationStatus = ModerationStatus.NEW;
    private User moderator;
    private User user;
    private LocalDateTime time;
    private String title;
    private String text;
    private int viewCount;

    private Set<Comment> comments;
    private Set<Tag> tags;

    //------------------------------------------------------------------------------------------------------------------

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "activity_status")
    @Enumerated(EnumType.STRING)
    public ActivityStatus getActivityStatus() {
        return activityStatus;
    }

    @Column(name = "moderation_status")
    @Enumerated(EnumType.STRING)
    public ModerationStatus getModerationStatus() {
        return moderationStatus;
    }

    @JsonBackReference
    @ManyToOne
    public User getModerator() {
        return moderator;
    }

    @JsonBackReference
    @ManyToOne
    public User getUser() {
        return user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    @Column(name = "view_count")
    public int getViewCount() {
        return viewCount;
    }

    @JsonManagedReference
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    public Set<Comment> getComments() {
        return comments;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(name = "tag2post",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    public Set<Tag> getTags() {
        return tags;
    }

    //------------------------------------------------------------------------------------------------------------------

    @Transient
    public void addTags(List<String> tags) {
        if (this.tags == null) {
            this.tags = new HashSet<>();
        }
        for (String tagName : tags) {
            Tag tag = new Tag();
            tag.setName(tagName);
            this.tags.add(tag);
        }
    }
}
