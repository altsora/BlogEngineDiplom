package diplom.repository;

import diplom.Application;
import diplom.enums.ActivityStatus;
import diplom.enums.ModerationStatus;
import diplom.model.Post;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static diplom.enums.ActivityStatus.ACTIVE;
import static diplom.enums.ModerationStatus.ACCEPTED;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Test
    public void repNotNull() {
        Assert.assertNotNull(postRepository);
    }

    @Test
    public void getTotalCountOfPosts() {
        int actual = postRepository.getTotalCountOfPosts(ACTIVE, ACCEPTED);
        Assert.assertEquals(11, actual);
    }

    @Test
    public void getPopularPostsTest() {
        int pageNumber = 0 / 10;
        Sort sort = Sort.by(Sort.Direction.DESC, PostRepository.COUNT_COMMENTS);
        Pageable pageable = PageRequest.of(pageNumber, 10, sort);
        List<Post> popularPosts = postRepository.findPopularPosts(ACTIVE, ACCEPTED, pageable);
        Assert.assertNotNull(popularPosts);
        Assert.assertFalse(popularPosts.isEmpty());
        Assert.assertEquals(1L, popularPosts.get(0).getId());
    }
}