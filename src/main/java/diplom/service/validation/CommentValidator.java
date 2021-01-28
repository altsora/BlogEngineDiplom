package diplom.service.validation;

import diplom.model.Comment;
import diplom.model.Post;
import diplom.repository.CommentRepository;
import diplom.repository.PostRepository;
import diplom.request.CommentForm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CommentValidator implements Validator {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Value("${validation.comment.minLength}")
    private int minLength;

    @Override
    public boolean supports(Class<?> clazz) {
        return CommentForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CommentForm form = (CommentForm) target;
        Object parentIdObj = form.getParentIdObj();
        if (form.getText().length() < minLength) {
            errors.reject("minLength");
        }
        if (parentIdObj instanceof Integer) {
            long parentId = (int) parentIdObj;
            Optional<Comment> optionalComment = commentRepository.findById(parentId);
            if (optionalComment.isEmpty()) {
                errors.reject("noParent");
            }
        }
        Optional<Post> optionalPost = postRepository.findById(form.getPostId());
        if (optionalPost.isEmpty()) {
            errors.reject("noPost");
        }
    }

}
