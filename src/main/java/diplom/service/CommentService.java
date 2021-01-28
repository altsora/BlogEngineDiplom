package diplom.service;

import diplom.model.Comment;
import diplom.model.Post;
import diplom.model.User;
import diplom.repository.CommentRepository;
import diplom.repository.PostRepository;
import diplom.request.CommentForm;
import diplom.response.*;
import diplom.service.validation.CommentValidator;
import diplom.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Log4j2
@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;
    private final AuthService authService;
    private final PostRepository postRepository;

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

    public ResponseEntity<AbstractResponse> addComment(CommentForm form) {
        DataBinder dataBinder = new DataBinder(form);
        dataBinder.setValidator(commentValidator);
        dataBinder.validate();

        BindingResult bindingResult = dataBinder.getBindingResult();

        if (!bindingResult.hasErrors()) {
            long commentId = createComment(form);
            return ResponseEntity.ok(ResultResponse.builder().id(commentId).build());
        }
        log.warn("Комментарий не добавлен: есть ошибки");
        ErrorResponse errors = new ErrorResponse();
        ErrorMessage errorMessage = new ErrorMessage();
        bindingResult.getAllErrors().forEach(e -> {
            switch (Objects.requireNonNull(e.getCode())) {
                case "minLength":
                    errors.setText("Текст комментария не задан или слишком короткий.");
                    break;
                case "noParent":
                    errorMessage.setMessage("Пост не существует.");
                    break;
                case "noPost":
                    errorMessage.setMessage("Родительский комментарий не существует.");
                    break;
            }
        });

        if (errorMessage.hasMessage()) {
            return ResponseEntity.badRequest().body(errorMessage);
        }

        return ResponseEntity.badRequest().body(
                ResultResponse.builder()
                        .result(false)
                        .errors(errors)
                        .build()
        );
    }

    //------------------------------------------------------------------------------------------------------------------

    private long createComment(CommentForm form) {
        log.info("Коммент успешно создан.");
        User user = authService.getCurrentUser();
        Post post = postRepository.getOne(form.getPostId());
        Object parentIdObj = form.getParentIdObj();
        String text = form.getText();
        Comment comment = new Comment(post, user, form.getText());
        if (parentIdObj instanceof Integer) {
            long parentId = (int) parentIdObj;
            Comment parent = commentRepository.getOne(parentId);
            comment.setParent(parent);
        }
        return commentRepository.saveAndFlush(comment).getId();
    }
}
