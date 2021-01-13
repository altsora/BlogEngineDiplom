package diplom.repository;

import diplom.Application;
import diplom.model.Post;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void init() {
        Assert.assertNotNull(commentRepository);
    }

    @Test
    public void commentCountTest() {
        Post post = postRepository.findById(1L).get();
        Assert.assertNotNull(post);
        int count = commentRepository.countByPost(post);
        Assert.assertEquals(10, count);
    }
}