package diplom.repository;

import diplom.enums.ActivityStatus;
import diplom.enums.ModerationStatus;
import diplom.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    String COUNT_COMMENTS = "countComments";

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
            "   p.time <= NOW()")
    List<Post> findPopularPosts(
            @Param("activityStatus") ActivityStatus activityStatus,
            @Param("moderationStatus") ModerationStatus moderationStatus,
            Pageable pageable
    );
}
