package diplom.repository;

import diplom.model.Comment;
import diplom.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    int countByPost(Post post);
}
