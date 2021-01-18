package diplom.repository;

import diplom.model.Comment;
import diplom.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    String COMMENT_TIME = "time";

    int countByPost(Post post);

    List<Comment> findCommentsByPost(Post post, Sort sort);
}
