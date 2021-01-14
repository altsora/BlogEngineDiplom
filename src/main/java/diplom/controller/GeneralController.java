package diplom.controller;

import diplom.response.Blog;
import diplom.response.CalendarResponse;
import diplom.service.GlobalSettingService;
import diplom.service.PostService;
import diplom.utils.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static diplom.enums.SettingValue.YES;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GeneralController {
    private final Blog blog;
    private final GlobalSettingService globalSettingService;
    private final PostService postService;
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

    @GetMapping("calendar")
    public CalendarResponse getCalendar(@RequestParam(value = "year", required = false) Integer year) {
        return postService.getCalendar(year);
    }

    @GetMapping(value = "/tag")
    public String getTagList() {
        return null;
    }
}
