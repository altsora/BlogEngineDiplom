package diplom.repository;

import diplom.Application;
import diplom.model.Comment;
import diplom.model.Post;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void commentRepoNotNull() {
        Assert.assertNotNull(commentRepository);
    }

    @Test
    public void commentCountTest() {
        Post post = postRepository.getOne(1L);
        Assert.assertNotNull(post);
        int count = commentRepository.countByPost(post);
        Assert.assertEquals(10, count);
    }

    @Test
    public void findCommentsByPostTest() {
        int expectedSize = 3;
        Post post = postRepository.getOne(8L);
        Sort sort = Sort.by(Sort.Direction.ASC, CommentRepository.COMMENT_TIME);
        List<Comment> comments = commentRepository.findCommentsByPost(post, sort);
        Assert.assertNotNull(comments);
        System.out.println("comments.size() = " + comments.size());
        comments.forEach(System.out::println);
        Assert.assertEquals(expectedSize, comments.size());
        if (comments.size() > 1) {
            Assert.assertTrue("Первый комментарий позже второго",comments.get(0).getTime().isBefore(comments.get(1).getTime()));
        }
    }
}