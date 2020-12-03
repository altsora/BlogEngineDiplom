package diplom.controller;

import diplom.response.Blog;
import diplom.service.GlobalSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static diplom.enums.SettingValue.YES;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeneralController {
    private final Blog blog;
    private final GlobalSettingService globalSettingService;
    //------------------------------------------------------------------------------------------------------------------

    @GetMapping("init")
    public Blog init() {
        return blog;
    }

    @GetMapping("/settings")
    public Map<String, Boolean> getSettings() {
        Map<String, Boolean> map = new HashMap<>();
        globalSettingService.findAll().forEach(setting -> {
            String code = setting.getCode().name();
            boolean enable = setting.getValue() == YES;
            map.put(code, enable);
        });
        return map;
    }

    @GetMapping(value = "/tag")
    public String getTagList() {
        return null;
    }
}
