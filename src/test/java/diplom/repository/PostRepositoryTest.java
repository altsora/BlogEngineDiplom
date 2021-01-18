package diplom.repository;

import diplom.Application;
import diplom.enums.Rating;
import diplom.model.Post;
import diplom.model.Tag;
import diplom.utils.TimeCountWrapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        int actual = postRepository.countPosts(ACTIVE, ACCEPTED);
        Assert.assertEquals(14, actual);
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
        Assert.assertEquals(18, popularPosts.get(6).getId());
        Assert.assertEquals(11, popularPosts.get(7).getId());
        Assert.assertEquals(12, popularPosts.get(8).getId());
        Assert.assertEquals(13, popularPosts.get(9).getId());
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
        Assert.assertEquals(18, postsSortedByDate.get(0).getId());
        Assert.assertEquals(17, postsSortedByDate.get(1).getId());
        Assert.assertEquals(16, postsSortedByDate.get(2).getId());
        Assert.assertEquals(15, postsSortedByDate.get(3).getId());
        Assert.assertEquals(14, postsSortedByDate.get(4).getId());
        Assert.assertEquals(13, postsSortedByDate.get(5).getId());
        Assert.assertEquals(12, postsSortedByDate.get(6).getId());
        Assert.assertEquals(11, postsSortedByDate.get(7).getId());
        Assert.assertEquals(10, postsSortedByDate.get(8).getId());
        Assert.assertEquals(9, postsSortedByDate.get(9).getId());
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
        Assert.assertEquals(1, postsSortedByDate.get(0).getId());
        Assert.assertEquals(4, postsSortedByDate.get(1).getId());
        Assert.assertEquals(7, postsSortedByDate.get(2).getId());
        Assert.assertEquals(8, postsSortedByDate.get(3).getId());
        Assert.assertEquals(9, postsSortedByDate.get(4).getId());
        Assert.assertEquals(10, postsSortedByDate.get(5).getId());
        Assert.assertEquals(11, postsSortedByDate.get(6).getId());
        Assert.assertEquals(12, postsSortedByDate.get(7).getId());
        Assert.assertEquals(13, postsSortedByDate.get(8).getId());
        Assert.assertEquals(14, postsSortedByDate.get(9).getId());
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

    @Test
    public void findPostsByQuery_Title() {
        int expected = 1;
        String query = "Чипирование";

        int limit = 10;
        Sort sort = Sort.by(Sort.Direction.DESC, PostRepository.POST_TIME);
        PageRequest pageable = PageRequest.of(0, limit, sort);
        List<Post> postListRep = postRepository.findPostsByQuery(ACTIVE, ACCEPTED, query, pageable);
        Assert.assertNotNull(postListRep);
        Assert.assertFalse(postListRep.isEmpty());
        Assert.assertEquals(expected, postListRep.size());
        int count = postRepository.getCountPostsByQuery(ACTIVE, ACCEPTED, query);
        Assert.assertEquals(expected, count);
        postListRep.forEach(post -> System.out.println("Title: " + post.getTitle() + ", text: " + post.getText()));
    }

    @Test
    public void findPostsByQuery_Text() {
        int expected = 10;
        String query = "текст_";

        int limit = 10;
        Sort sort = Sort.by(Sort.Direction.DESC, PostRepository.POST_TIME);
        PageRequest pageable = PageRequest.of(0, limit, sort);
        List<Post> postListRep = postRepository.findPostsByQuery(ACTIVE, ACCEPTED, query, pageable);
        Assert.assertNotNull(postListRep);
        Assert.assertFalse(postListRep.isEmpty());
        postListRep.forEach(post -> System.out.println("Title: " + post.getTitle() + ", text: " + post.getText()));
        Assert.assertEquals(expected, postListRep.size());
    }

    @Test
    public void findYearsOfPublicationTest() {
        List<Integer> yearsOfPublication = postRepository.findYearsOfPublication(ACTIVE, ACCEPTED);
        Assert.assertNotNull(yearsOfPublication);
        System.out.println(yearsOfPublication);
        Assert.assertFalse(yearsOfPublication.isEmpty());
        Assert.assertEquals(Integer.valueOf(2016) , yearsOfPublication.get(0));
        Assert.assertEquals(Integer.valueOf(2017) , yearsOfPublication.get(1));
        Assert.assertEquals(Integer.valueOf(2018) , yearsOfPublication.get(2));
        Assert.assertEquals(Integer.valueOf(2021) , yearsOfPublication.get(3));
    }

    @Test
    public void getTimeAndCountPostsTest() {
        List<TimeCountWrapper> timeAndCountPosts = postRepository.getTimeAndCountPosts(ACTIVE, ACCEPTED, 2018);
        timeAndCountPosts.forEach(System.out::println);
        Assert.assertEquals(2, timeAndCountPosts.size());
        Map<String, Long> posts = timeAndCountPosts.stream()
                .collect(Collectors.toMap(TimeCountWrapper::getTime, TimeCountWrapper::getCount));
        posts.forEach((k, v) -> System.out.println("Post time: " + k + ", Count: " + v));
    }

    @Test
    public void findPostsByDateTest() {
        int expected = 2;
        String date = "2016-09-09";
        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = postRepository.findPostsByDate(ACTIVE, ACCEPTED, date, pageable);
        posts.forEach(System.out::println);
        Assert.assertNotNull(posts);
        Assert.assertEquals(expected, posts.size());

        int count = postRepository.countPostsByDate(ACTIVE, ACCEPTED, date);
        Assert.assertEquals(expected, count);

        LocalDateTime timeFirst = posts.get(0).getTime();
        LocalDateTime timeSecond = posts.get(1).getTime();
        Assert.assertTrue(timeFirst.isAfter(timeSecond));
    }

    @Test
    @Transactional
    public void PostManyToManyTagTest() {
        Post post = postRepository.findById(3L).get();
        Assert.assertNotNull(post);
        Set<Tag> tags = post.getTags();
        System.out.println("post.getId() = " + post.getId());
        System.out.println("post.getTags().size() = " + tags.size());
        System.out.println("post.getTags() = " + tags);
        Assert.assertNotNull(tags);
        Assert.assertFalse(tags.isEmpty());
        Assert.assertEquals(3, tags.size());
    }

    @Test
    @Transactional
    public void findPostsByTag() {
        String tag = "Java";
        int expected = 10;

        Pageable pageable = PageRequest.of(0, 10);
        List<Post> posts = postRepository.findPostsByTag(ACTIVE, ACCEPTED, tag, pageable);
        Assert.assertNotNull(posts);
        System.out.println("tag = " + tag);
        System.out.println("posts.size() = " + posts.size());
        posts.forEach(post -> {
            System.out.println("PostId: " + post.getId());
            Set<Tag> tags = post.getTags();
            tags.forEach(t -> System.out.println("\tTagId: " + t.getId() + ", name" + t.getName()));
            // Проверка, что загружаются все тэги для поста, а не только указанный
            if (post.getId() == 1) {
                Assert.assertEquals(6, tags.size());
            }
        });
        Assert.assertEquals(expected, posts.size());
        int count = postRepository.countPostsByTag(ACTIVE, ACCEPTED, tag);
        Assert.assertEquals(expected, count);
    }

    @Test
    @Transactional
    public void getTagsLikeArrayTest() {
        Post post = postRepository.getOne(1L);
        Set<Tag> tags = post.getTags();
        List<String> tagList = tags.stream().map(Tag::getName).collect(Collectors.toList());
        System.out.println(tagList);
        Assert.assertTrue(true);
    }
}