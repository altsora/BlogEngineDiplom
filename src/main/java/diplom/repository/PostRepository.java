package diplom.repository;

import diplom.enums.ActivityStatus;
import diplom.enums.ModerationStatus;
import diplom.enums.Rating;
import diplom.model.Post;
import diplom.utils.TimeCountWrapper;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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
}
