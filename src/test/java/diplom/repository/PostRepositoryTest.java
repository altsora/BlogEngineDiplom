package diplom.repository;

import diplom.Application;
import diplom.enums.Rating;
import diplom.model.Post;
import diplom.utils.TimeUtil;
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
        List<Post> popularPosts = postRepository.findPostsSortedByPopularDesc(ACTIVE, ACCEPTED, pageable);
        Assert.assertNotNull(popularPosts);
        Assert.assertFalse(popularPosts.isEmpty());
        Assert.assertEquals(1L, popularPosts.get(0).getId());

        popularPosts.forEach(System.out::println);
        Assert.assertEquals(1, popularPosts.get(0).getId());
        Assert.assertEquals(4, popularPosts.get(1).getId());
        Assert.assertEquals(7, popularPosts.get(2).getId());
        Assert.assertEquals(8, popularPosts.get(3).getId());
        Assert.assertEquals(9, popularPosts.get(4).getId());
        Assert.assertEquals(10, popularPosts.get(5).getId());
        Assert.assertEquals(11, popularPosts.get(6).getId());
        Assert.assertEquals(12, popularPosts.get(7).getId());
        Assert.assertEquals(13, popularPosts.get(8).getId());
        Assert.assertEquals(14, popularPosts.get(9).getId());
    }

    @Test
    public void findPostsSortedByDate_Recent() {
        int limit = 10;
        int pageNumber = 0 / limit;
        Sort sort = Sort.by(Sort.Direction.DESC, PostRepository.POST_TIME);
        PageRequest pageable = PageRequest.of(pageNumber, limit, sort);
        List<Post> postsSortedByDate = postRepository.findPostsSortedByDate(ACTIVE, ACCEPTED, pageable);
        Assert.assertNotNull(postsSortedByDate);
        Assert.assertFalse(postsSortedByDate.isEmpty());
        Assert.assertTrue(postsSortedByDate.size() > 1);
        Post first = postsSortedByDate.get(0);
        Post second = postsSortedByDate.get(1);
        boolean b = first.getTime().isAfter(second.getTime());
        Assert.assertTrue(b);
        System.out.println("first.getId()  = " + first.getId());
        System.out.println("    first.getTime()  = " + first.getTime());
        System.out.println("second.getId() = " + second.getId());
        System.out.println("    second.getTime() = " + second.getTime());

        postsSortedByDate.forEach(System.out::println);
        Assert.assertEquals(9, postsSortedByDate.get(0).getId());
        Assert.assertEquals(10, postsSortedByDate.get(1).getId());
        Assert.assertEquals(14, postsSortedByDate.get(2).getId());
        Assert.assertEquals(7, postsSortedByDate.get(3).getId());
        Assert.assertEquals(11, postsSortedByDate.get(4).getId());
        Assert.assertEquals(4, postsSortedByDate.get(5).getId());
        Assert.assertEquals(8, postsSortedByDate.get(6).getId());
        Assert.assertEquals(15, postsSortedByDate.get(7).getId());
        Assert.assertEquals(1, postsSortedByDate.get(8).getId());
        Assert.assertEquals(12, postsSortedByDate.get(9).getId());
    }

    @Test
    public void findPostsSortedByDate_Early() {
        int limit = 10;
        int pageNumber = 0 / limit;
        Sort sort = Sort.by(Sort.Direction.ASC, PostRepository.POST_TIME);
        PageRequest pageable = PageRequest.of(pageNumber, limit, sort);
        List<Post> postsSortedByDate = postRepository.findPostsSortedByDate(ACTIVE, ACCEPTED, pageable);
        Assert.assertNotNull(postsSortedByDate);
        Assert.assertFalse(postsSortedByDate.isEmpty());
        Assert.assertTrue(postsSortedByDate.size() > 1);
        Post first = postsSortedByDate.get(0);
        Post second = postsSortedByDate.get(1);
        boolean b = first.getTime().isBefore(second.getTime());
        Assert.assertTrue(b);
        System.out.println("first.getId()  = " + first.getId());
        System.out.println("    first.getTime()  = " + first.getTime());
        System.out.println("second.getId() = " + second.getId());
        System.out.println("    second.getTime() = " + second.getTime());

        postsSortedByDate.forEach(System.out::println);
        Assert.assertEquals(13, postsSortedByDate.get(0).getId());
        Assert.assertEquals(12, postsSortedByDate.get(1).getId());
        Assert.assertEquals(1, postsSortedByDate.get(2).getId());
        Assert.assertEquals(15, postsSortedByDate.get(3).getId());
        Assert.assertEquals(8, postsSortedByDate.get(4).getId());
        Assert.assertEquals(4, postsSortedByDate.get(5).getId());
        Assert.assertEquals(11, postsSortedByDate.get(6).getId());
        Assert.assertEquals(7, postsSortedByDate.get(7).getId());
        Assert.assertEquals(14, postsSortedByDate.get(8).getId());
        Assert.assertEquals(10, postsSortedByDate.get(9).getId());
    }

    @Test
    public void findPostsSortedByBest() {
        int limit = 10;
        int pageNumber = 0 / limit;
        Sort sort = Sort.by(Sort.Direction.DESC, PostRepository.COUNT_LIKES);
        PageRequest pageable = PageRequest.of(pageNumber, limit, sort);
        List<Post> postsSortedByBest = postRepository.findPostsSortedByBest(ACTIVE, ACCEPTED, Rating.LIKE, pageable);
        Assert.assertNotNull(postsSortedByBest);
        Assert.assertFalse(postsSortedByBest.isEmpty());
        Post bestPost = postsSortedByBest.get(0);
        Assert.assertEquals(1L, bestPost.getId());
        System.out.println(bestPost);
    }
}