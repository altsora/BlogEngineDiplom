package diplom.service;

import diplom.model.Post;
import diplom.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    //==================================================================================================================

    public int getCountCommentsByPost(Post post) {
        return commentRepository.countByPost(post);
    }
}
