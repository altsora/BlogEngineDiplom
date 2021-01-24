package diplom.repository;

import diplom.Application;
import diplom.model.enums.SettingCode;
import diplom.model.enums.SettingValue;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GlobalSettingRepositoryTest {

    @Autowired
    private GlobalSettingRepository globalSettingRepository;

    @Test
    public void repoNotNull() {
        Assert.assertNotNull(globalSettingRepository);
    }

    @Test
    public void settingExistsTest() {
        boolean valueTrue = globalSettingRepository.existsByCodeAndValue(SettingCode.MULTIUSER_MODE, SettingValue.YES);
        Assert.assertTrue(valueTrue);
        boolean valueFalse = globalSettingRepository.existsByCodeAndValue(SettingCode.MULTIUSER_MODE, SettingValue.NO);
        Assert.assertFalse(valueFalse);
    }
}