package diplom.repository;

import diplom.Application;
import diplom.enums.SettingValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GlobalSettingRepositoryTest {

    @Autowired
    private GlobalSettingRepository globalSettingRepository;

    @Test
    public void repoNotNull() {
        Assert.assertNotNull(globalSettingRepository);
    }

//    @Test
//    public void getSettingsTest() {
//        Map<String, Boolean> map = globalSettingRepository.findAll()
//                .stream()
//                .collect(Collectors.toMap(s -> s.getCode().name(), s -> s.getValue() == SettingValue.YES));
//        System.out.println(map);
//    }
}