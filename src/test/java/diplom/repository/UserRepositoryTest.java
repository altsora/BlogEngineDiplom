package diplom.repository;

import diplom.Application;
import diplom.model.User;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void check() {
        Assert.assertTrue(true);
    }

    @Test
    public void usersListNotEmpty() {
        Assert.assertFalse(userRepository.findAll().isEmpty());
    }

    @Test
    public void selectUserById() {
        User user = userRepository.findById(4);
        Assert.assertNotNull(user);
        Assert.assertEquals("Павел Дуров", user.getName());
        System.out.println(user);
    }

    @Test
    public void userNotExists() {
        User user = userRepository.findById(-1);
        Assert.assertNull(user);
    }
}