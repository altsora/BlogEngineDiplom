package diplom.repository;

import diplom.Application;
import diplom.model.User;
import diplom.model.Vote;
import diplom.model.enums.Rating;
import diplom.model.Post;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class VoteRepositoryTest {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

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

    @Test
    public void countVotesByUserTest() {
        User user = userRepository.getOne(1L);
        int count = voteRepository.countByUserAndRating(user, Rating.LIKE);
        Assert.assertEquals(13, count);
        System.out.println("У пользователя id = 1 посты собрали 13 лайков");

        User user2 = userRepository.getOne(3L);
        int countDis = voteRepository.countByUserAndRating(user2, Rating.DISLIKE);
        Assert.assertEquals(4, countDis);
        System.out.println("У пользователя id = 3 посты собрали 4 дизлайка");
    }

    @Test
    public void countLikesTest() {
        int countDis = voteRepository.countVotesByValue(Rating.DISLIKE);
        System.out.println("countDis = " + countDis);
        int countLike = voteRepository.countVotesByValue(Rating.LIKE);
        System.out.println("countLike = " + countLike);
    }

    @Test
    public void getVoteByUserAndPostTest() {
        User user = userRepository.getOne(1L);
        Post post = postRepository.getOne(1L);
        Optional<Vote> voteOptional = voteRepository.findByUserAndPost(user, post);
        Assert.assertTrue(voteOptional.isPresent());
        Vote vote = voteOptional.get();
        System.out.println("vote.getId() = " + vote.getId());
        System.out.println("vote.getValue() = " + vote.getValue());
    }
}