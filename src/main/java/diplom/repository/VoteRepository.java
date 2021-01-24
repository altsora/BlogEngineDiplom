package diplom.repository;

import diplom.model.User;
import diplom.model.enums.Rating;
import diplom.model.Post;
import diplom.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Deprecated
    @Query("SELECT COUNT(v) FROM Vote v " +
            "WHERE " +
            "   v.post.id = :postId AND " +
            "   v.value = :value")
    int getCountRatingByPostId(
            @Param("postId") long postId,
            @Param("value") Rating value
    );


    int countByPostAndValue(Post post, Rating value);

    @Query("SELECT COUNT(v.value) FROM Vote v " +
            "JOIN Post p ON p.id = v.post.id " +
            "WHERE " +
            "   p.user = :user AND " +
            "   v.value = :value")
    int countByUserAndRating(
            @Param("user") User user,
            @Param("value") Rating value
    );

    int countVotesByValue(Rating value);
}
