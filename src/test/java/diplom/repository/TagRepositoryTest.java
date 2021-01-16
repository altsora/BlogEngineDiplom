package diplom.repository;

import diplom.Application;
import diplom.model.Post;
import diplom.model.Tag;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TagRepositoryTest {
    @Autowired
    private TagRepository tagRepository;

    @Test
    public void repNotNull() {
        Assert.assertNotNull(tagRepository);
    }

    @Test
    @Transactional
    public void TagManyToManyPost() {
        Tag tag = tagRepository.findById(3L).get();
        Set<Post> posts = tag.getPosts();
        Assert.assertNotNull(posts);
        Assert.assertFalse(posts.isEmpty());
        Assert.assertEquals(6, posts.size());
        System.out.println("Tag: " + tag.getName());
        posts.forEach(post -> System.out.println("\tid: " + post.getId()));
    }

    @Test
    public void findTagByQuery() {
        List<Tag> tags = tagRepository.findTagsByNameStartingWith("S");
        Assert.assertNotNull(tags);
        tags.forEach(System.out::println);
        Assert.assertEquals(2, tags.size());
    }
}