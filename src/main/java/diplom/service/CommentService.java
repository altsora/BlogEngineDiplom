package diplom.service;

import diplom.model.Comment;
import diplom.model.Post;
import diplom.model.User;
import diplom.repository.CommentRepository;
import diplom.response.CommentResponse;
import diplom.response.UserWithPhotoResponse;
import diplom.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    //==================================================================================================================

    public int getCountCommentsByPost(Post post) {
        return commentRepository.countByPost(post);
    }

    public List<CommentResponse> getCommentResponseByPost(Post post) {
        Sort sort = Sort.by(Sort.Direction.ASC, CommentRepository.COMMENT_TIME);
        List<Comment> commentListRep = commentRepository.findCommentsByPost(post, sort);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (Comment commentRep : commentListRep) {
            User user = commentRep.getUser();
            UserWithPhotoResponse userPhoto = UserWithPhotoResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .photo(user.getPhoto())
                    .build();
            long timestamp = TimeUtil.getTimestamp(commentRep.getTime());
            CommentResponse commentResponse = CommentResponse.builder()
                    .id(commentRep.getId())
                    .text(commentRep.getText())
                    .timestamp(timestamp)
                    .user(userPhoto)
                    .build();
            commentResponseList.add(commentResponse);
        }
        return commentResponseList;
    }
}
