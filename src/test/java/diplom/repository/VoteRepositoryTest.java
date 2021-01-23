package diplom.repository;

import diplom.Application;
import diplom.model.enums.Rating;
import diplom.model.Post;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void init() {
        Assert.assertNotNull(voteRepository);
    }

    @Test
    public void countTest() {
        Post post = postRepository.findById(1L).get();
        Assert.assertNotNull(post);
        int count = voteRepository.getCountRatingByPostId(post.getId(), Rating.LIKE);
        Assert.assertEquals(13, count);
        System.out.println("===");
        System.out.println(voteRepository.countByPostAndValue(post, Rating.LIKE));
    }

    @Test
    public void countDislikeTest() {
        Post post = postRepository.findById(11L).get();
        Assert.assertNotNull(post);
        int countDis = voteRepository.countByPostAndValue(post, Rating.DISLIKE);
        Assert.assertEquals(3, countDis);
    }
}