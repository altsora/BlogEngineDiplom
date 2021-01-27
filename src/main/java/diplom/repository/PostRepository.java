package diplom.repository;

import diplom.model.User;
import diplom.model.enums.ActivityStatus;
import diplom.model.enums.ModerationStatus;
import diplom.model.enums.Rating;
import diplom.model.Post;
import diplom.utils.TimeCountWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

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
    int countPosts(
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

    @Query("SELECT p FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW() AND " +
            "   (p.title LIKE %:query% OR p.text LIKE %:query%)")
    List<Post> findPostsByQuery(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("query") String query,
            Pageable pageable
    );

    @Query("SELECT COUNT(p) FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW() AND " +
            "   (p.title LIKE %:query% OR p.text LIKE %:query%)")
    int getCountPostsByQuery(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("query") String query
    );

    @Query("SELECT YEAR(p.time) AS postYear " +
            "FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW() " +
            "GROUP BY postYear ORDER BY p.time")
    List<Integer> findYearsOfPublication(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus
    );

    @Query("SELECT new diplom.utils.TimeCountWrapper(DATE_FORMAT(p.time, '%Y-%m-%d'), COUNT(p)) " +
            "FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   YEAR(p.time) = :year " +
            "GROUP BY YEAR(p.time), MONTH(p.time), DAYOFMONTH(p.time)")
    List<TimeCountWrapper> getTimeAndCountPosts(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("year") int year
    );

    @Query("SELECT p FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   DATE_FORMAT(p.time, '%Y-%m-%d') = :date " +
            "ORDER BY p.time DESC")
    List<Post> findPostsByDate(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("date") String date,
            Pageable pageable
    );

    @Query("SELECT COUNT(p) FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   DATE_FORMAT(p.time, '%Y-%m-%d') = :date")
    int countPostsByDate(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("date") String date
    );

    @Query("SELECT p FROM Post p " +
            "JOIN p.tags t " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW() AND " +
            "   t.name = :tag " +
            "ORDER BY p.time DESC")
    List<Post> findPostsByTag(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("tag") String tag,
            Pageable pageable
    );

    @Query("SELECT COUNT(p) FROM Post p " +
            "JOIN p.tags t " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.time <= NOW() AND " +
            "   t.name = :tag")
    int countPostsByTag(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("tag") String tag
    );

    @Query(value = "SELECT IFNULL(SUM(p.view_count), 0) FROM posts p WHERE p.user_id = :userId", nativeQuery = true)
    int getTotalViewCountByUser(@Param("userId") long userId);

    @Query("SELECT SUM(p.viewCount) FROM Post p")
    int getTotalViewCount();

    @Query("SELECT MIN(p.time) FROM Post p")
    LocalDateTime getTimeOfFirstPost();

    List<Post> findPostsByActivityStatusAndUser(ActivityStatus status, User user, Pageable pageable);

    int countPostsByActivityStatusAndUser(ActivityStatus status, User user);

    List<Post> findByActivityStatusAndModerationStatusAndUser(
            ActivityStatus activityStatus,
            ModerationStatus moderationStatus,
            User user,
            Pageable pageable
    );

    int countByActivityStatusAndModerationStatusAndUser(
            ActivityStatus activityStatus,
            ModerationStatus moderationStatus,
            User user
    );

    @Query("SELECT p FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.moderator = :moderator")
    List<Post> findPostsByModerator(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("moderator") User moderator,
            Pageable pageable
    );

    @Query("SELECT COUNT(p) FROM Post p " +
            "WHERE " +
            "   p.activityStatus = :activityStatus AND " +
            "   p.moderationStatus = :moderationStatus AND " +
            "   p.moderator = :moderator")
    int countPostsByModerator(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            @Param("moderator") User moderator
    );

    List<Post> findByActivityStatusAndModerationStatus(
            ActivityStatus activityStatus,
            ModerationStatus moderationStatus,
            Pageable pageable
    );

    int countByActivityStatusAndModerationStatus(
            ActivityStatus activityStatus,
            ModerationStatus moderationStatus
    );
}
