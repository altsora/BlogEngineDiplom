package diplom.repository;

import diplom.enums.Rating;
import diplom.model.Post;
import diplom.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    @Query("SELECT COUNT(v) FROM Vote v " +
            "WHERE " +
            "   v.post.id = :postId AND " +
            "   v.value = :value")
    int getCountRatingByPostId(
            @Param("postId") long postId,
            @Param("value") Rating value
    );


    int countByPostAndValue(Post post, Rating value);

}
