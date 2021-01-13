package diplom.repository;

import diplom.enums.ActivityStatus;
import diplom.enums.ModerationStatus;
import diplom.enums.Rating;
import diplom.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    String COUNT_COMMENTS = "countComments";
    String POST_TIME = "time";
    String COUNT_LIKES = "countLikes";

    //------------------------------------------------------------------------------------------------------------------

    @Query("SELECT COUNT(p) FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW()")
    int getTotalCountOfPosts(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus
    );

    @Query(value = "SELECT p, COUNT(c.id) AS " + COUNT_COMMENTS + " " +
            "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW() " +
            "GROUP BY p.id")
    List<Post> findPostsSortedByPopularDesc(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW()")
    List<Post> findPostsSortedByDate(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            Pageable pageable
    );

    @Query("SELECT p, " +
            "(SELECT COUNT(v) AS " + COUNT_LIKES + " FROM Vote v WHERE v.post.id = p.id AND v.value = :value) AS " + COUNT_LIKES +
            " FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW()")
    List<Post> findPostsSortedByBest(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("value") Rating value,
            Pageable pageable
    );
}
