package diplom.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import diplom.enums.ActivityStatus;
import diplom.enums.ModerationStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
@Data
public class Post {
    private long id;
    private ActivityStatus activityStatus;
    private ModerationStatus moderationStatus;
    private User moderator;
    private User user;
    private LocalDateTime time;
    private String title;
    private String text;
    private int viewCount;

    public Post() {
        this.moderationStatus = ModerationStatus.NEW;
    }

    //==============================================================================

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
}
